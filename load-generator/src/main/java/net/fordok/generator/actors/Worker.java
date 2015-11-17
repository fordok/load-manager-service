package net.fordok.generator.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.messages.WorkRun;
import net.fordok.generator.work.Work;
import scala.concurrent.duration.Duration;

import java.io.IOException;

/**
 * Created by fordok on 11/17/2015.
 */
public class Worker extends UntypedActor {
    protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    protected WorkRun workRun = null;
    protected ActorRef stats = null;
    protected int id;

    public Worker(int id, WorkRun workRun, ActorRef stats) {
        this.id = id;
        this.stats = stats;
        this.workRun = workRun;
        log.info("Worker with id : " + id + " was created, run params : " + workRun.getRunParams() + " work list : " + workRun.getWorkList());
    }

    @Override
    public void onReceive(Object message) throws Exception {
    }

    protected void doWork(Work work) {
        ActorRef executor = getContext().actorOf(Props.create(WorkerExecutor.class));
        if (work == null) {
            log.debug("work is null!!!!");
        } else {
            executor.tell(work, getSelf());
        }
    }

    protected static SupervisorStrategy strategy = new OneForOneStrategy(10,
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
