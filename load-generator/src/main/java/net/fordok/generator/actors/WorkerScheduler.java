package net.fordok.generator.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.messages.WorkRun;
import net.fordok.generator.work.Work;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by fordok on 11/14/2015.
 */
public class WorkerScheduler extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private WorkRun workRun = null;
    private ActorRef stats = null;
    private Cancellable scheduler = null;
    private int id;

    public WorkerScheduler(int id, WorkRun workRun, ActorRef stats) {
        this.id = id;
        this.stats = stats;
        this.workRun = workRun;
        log.info("WorkerScheduler with id : " + id + " was created, run params : " + workRun.getRunParams() + " work list : " + workRun.getWorkList());
        scheduler = initSchedulerWithPeriod(Integer.valueOf(workRun.getRunParams().get("periodMin")));
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof WorkResult) {
            if (message instanceof WorkResult) {
                getSender().tell(PoisonPill.getInstance(), getSelf());
                stats.tell(message, getSelf());
            }
        } else if (message.equals("Tick")) {
            //todo make complicated get
            doWork(workRun.getWorkList().values().iterator().next());
        }
    }

    private void doWork(Work work) {
        ActorRef executor = getContext().actorOf(Props.create(WorkerExecutor.class));
        if (work == null) {
            log.debug("work is null!!!!");
        } else {
            executor.tell(work, getSelf());
        }
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(10,
            Duration.create("1 minute"),
            t -> {
                if (t instanceof IOException) {
                    return SupervisorStrategy.stop();
                } else {
                    return SupervisorStrategy.escalate();
                }
            }
    );

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    private Cancellable initSchedulerWithPeriod(long period) {
        return getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(period, TimeUnit.MILLISECONDS), getSelf(), "Tick",
                getContext().system().dispatcher(), null);
    }
}
