package net.fordok.generator.actors;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import net.fordok.generator.configuration.ConfigurationWorker;
import net.fordok.generator.messages.CommandsManage;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.work.Delay;
import net.fordok.generator.work.Http;
import net.fordok.generator.work.Work;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Task;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 2:50 PM
 */
public class Worker extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private Cancellable scheduler = null;
    private Map<Integer,Work> workList = null;
    private ActorRef stats = null;
    private int taskIndex = 1;
    private int id;

    public Worker(int id, Map<Integer,Work> workList, ActorRef stats) {
        this.id = id;
        this.workList = workList;
        this.stats = stats;
        log.info("Worker with id : " + id + " was created");
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
        if (taskIndex == workList.size()) {
            taskIndex = 1;
        } else {
            taskIndex++;
        }
        return workList.get(taskIndex);
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
