package net.fordok.generator.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.configuration.ConfigurationSystem;
import net.fordok.generator.configuration.ConfigurationWorker;
import net.fordok.generator.messages.CommandsManage;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fordok
 * Date: 1/1/2015
 * Time: 6:16 PM
 */
public class Master extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private List<ActorRef> workers = new ArrayList<ActorRef>();
    private ActorRef stats = null;
    private ConfigurationSystem conf = null;

    @Override
    public void preStart() throws Exception {
        stats = getContext().actorOf(Props.create(StatsAggregator.class));
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Received : " + message);
        if (message instanceof ConfigurationSystem) {
            conf = (ConfigurationSystem)message;
            killAndClearWorkers();
            for (int i = 1; i <= conf.getWorkersCount(); i++) {
                ConfigurationWorker confWorker = new ConfigurationWorker(conf.getPeriod(), conf.getWork(), stats);
                workers.add(getContext().actorOf(Props.create(Worker.class, i, confWorker)));
            }
        } else if (message instanceof CommandsManage) {
            for (ActorRef worker : workers) {
                worker.tell(message, getSelf());
            }
            if (message instanceof CommandsManage.Stop) {
                killAndClearWorkers();
            }
        }
    }

    private void killAndClearWorkers() {
        for (ActorRef worker : workers) {
            worker.tell(PoisonPill.getInstance(), getSelf());
        }
        workers.clear();
    }
}
