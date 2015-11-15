package net.fordok.generator.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.messages.WorkRun;
import net.fordok.generator.work.Work;
import scala.concurrent.duration.Duration;

import java.io.IOException;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 2:50 PM
 */
public class WorkerSequence extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private WorkRun workRun;
    private ActorRef stats = null;
    private int taskIndex = 1;
    private int id;

    public WorkerSequence(int id, WorkRun workRun, ActorRef stats) {
        this.id = id;
        this.workRun = workRun;
        this.stats = stats;
        log.info("WorkerSequence with id : " + id + " was created, run params : " + workRun.getRunParams() + " work list : " + workRun.getWorkList());
        doWork(getNextWork());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof WorkResult) {
            getSender().tell(PoisonPill.getInstance(), getSelf());
            stats.tell(message, getSelf());
            doWork(getNextWork());
        }
    }

    private Work getNextWork() {
        if (taskIndex == workRun.getWorkList().size()) {
            taskIndex = 1;
        } else {
            taskIndex++;
        }
        return workRun.getWorkList().get(taskIndex);
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
}
