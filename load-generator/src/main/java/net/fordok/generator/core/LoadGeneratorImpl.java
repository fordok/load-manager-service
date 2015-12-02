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

    private ActorSystem actorSystem;
    private ActorRef master;
    private ActorRef clusterListener;

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
//        actorSystem.scheduler().schedule(Duration.Zero(),
//                Duration.create(1000, TimeUnit.MILLISECONDS), clusterListener, new ClusterMessage("test"),
//                actorSystem.dispatcher(), null);
    }

    @Override
    public void start(Run run) {
        master.tell(new CommandsManage.Start(run), ActorRef.noSender());
    }

    @Override
    public void stop() {
        master.tell(new CommandsManage.Stop(), ActorRef.noSender());
    }
}
