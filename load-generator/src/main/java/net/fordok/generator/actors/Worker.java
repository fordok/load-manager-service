package net.fordok.generator.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import net.fordok.generator.configuration.ConfigurationWorker;
import net.fordok.generator.messages.CommandsManage;
import net.fordok.generator.messages.WorkResult;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 2:50 PM
 */
public class Worker extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private Cancellable scheduler = null;
    private ConfigurationWorker conf = null;

    private int id;

    public Worker(int id, ConfigurationWorker conf) {
        this.id = id;
        this.conf = conf;
        log.info("Worker with id : " + id + " was created");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ConfigurationWorker) {
            conf = (ConfigurationWorker) message;
            log.info("Worker with id : " + id + " received net.fordok.generator.configuration : " + conf);
        } else if (message instanceof CommandsManage.Start) {
            if (conf == null) {
                log.error("No net.fordok.generator.configuration for worker! : " + id);
            } else {
                scheduler = initSchedulerWithPeriod(conf.getPeriod());
            }
        } else if (message instanceof CommandsManage.Suspend) {
            scheduler.cancel();
        } else if (message instanceof CommandsManage.Resume) {
            scheduler = initSchedulerWithPeriod(conf.getPeriod());
        } else if (message instanceof WorkResult) {
            getSender().tell(PoisonPill.getInstance(), getSelf());
            conf.getStatsActor().tell(message, getSelf());
        } else if (message.equals("Tick")) {
            doWork();
        }
    }

    private void doWork() {
        ActorRef executor = getContext().actorOf(Props.create(WorkerExecutor.class));
        executor.tell(conf.getWork(), getSelf());
    }

    private Cancellable initSchedulerWithPeriod(long period) {
        return getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(period, TimeUnit.MILLISECONDS), getSelf(), "Tick",
                getContext().system().dispatcher(), null);
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(10,
            Duration.create("1 minute"),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    if (t instanceof IOException) {
                        return SupervisorStrategy.stop();
                    } else {
                        return SupervisorStrategy.escalate();
                    }
                }
            }
    );

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
