package net.fordok.generator.core;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
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
        actorSystem = ActorSystem.create("loadGenerator");
        master = actorSystem.actorOf(Props.create(Master.class));
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
