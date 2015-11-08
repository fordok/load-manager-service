package net.fordok.generator.core;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import net.fordok.generator.actors.Master;
import net.fordok.generator.configuration.ConfigurationSystem;
import net.fordok.generator.messages.CommandsManage;

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
    public void start(ConfigurationSystem confSystem) {
        master.tell(confSystem, ActorRef.noSender());
        master.tell(new CommandsManage.Start(), ActorRef.noSender());
    }

    @Override
    public void stop() {
        master.tell(new CommandsManage.Stop(), ActorRef.noSender());
    }
}
