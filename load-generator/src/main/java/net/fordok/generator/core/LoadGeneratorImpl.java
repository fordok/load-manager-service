package net.fordok.generator.core;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.fordok.generator.actors.ClusterListener;
import net.fordok.generator.actors.Master;
import net.fordok.generator.messages.CommandsManage;
import net.fordok.service.dto.Run;

/**
 * User: Fordok
 * Date: 6/24/2015
 * Time: 11:15 PM
 */
public class LoadGeneratorImpl implements LoadGenerator {

    private ActorSystem actorSystem;
    private ActorRef master;

    @Override
    public void init() {
        String port = "2551";
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).withFallback(
                ConfigFactory.load());
        actorSystem = ActorSystem.create("loadGenerator", config);
        master = actorSystem.actorOf(Props.create(Master.class));
        actorSystem.actorOf(Props.create(ClusterListener.class));
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
