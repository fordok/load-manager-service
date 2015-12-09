package net.fordok.generator.core;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import net.fordok.generator.actors.ClusterListener;
import net.fordok.generator.actors.Master;
import net.fordok.generator.messages.CommandsManage;
import net.fordok.service.dto.Run;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fordok
 * Date: 6/24/2015
 * Time: 11:15 PM
 */
public class LoadGeneratorImpl implements LoadGenerator {

    public static final String STATE_NEW = "new";
    public static final String STATE_INITIALIZED = "initialized";
    public static final String STATE_RUNNING = "running";
    public static final String STATE_STOPPED = "stopped";

    private ActorSystem actorSystem;
    private ActorRef master;
    private ActorRef clusterListener;
    private String state = STATE_NEW;

    @Override
    public void init(String host, String port, List<String> seedsIps) {
        List<String> seeds = new ArrayList<>();
        seedsIps.forEach(ip -> seeds.add(String.format("akka.tcp://loadGenerator@%s:%s", ip, port)));
        Config config = ConfigFactory.empty()
                .withValue("akka.remote.netty.tcp.hostname", ConfigValueFactory.fromAnyRef(host))
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(port))
                .withValue("akka.cluster.seed-nodes", ConfigValueFactory.fromIterable(seeds))
                .withFallback(ConfigFactory.load());
        actorSystem = ActorSystem.create("loadGenerator", config);
        master = actorSystem.actorOf(Props.create(Master.class));
        clusterListener = actorSystem.actorOf(Props.create(ClusterListener.class));
        state = STATE_INITIALIZED;
    }

    @Override
    public void start(Run run) {
        master.tell(new CommandsManage.Start(run), ActorRef.noSender());
        state = STATE_RUNNING;
    }

    @Override
    public void stop() {
        master.tell(new CommandsManage.Stop(), ActorRef.noSender());
        state = STATE_STOPPED;
    }

    @Override
    public String getState() {
        return state;
    }
}
