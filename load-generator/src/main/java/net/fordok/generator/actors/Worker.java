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
import java.util.concurrent.TimeUnit;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 2:50 PM
 */
public class Worker extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private Cancellable scheduler = null;
    private Run run = null;
    private ActorRef stats = null;
    private int taskIndex = 1;

    private int id;

    public Worker(int id, Run run, ActorRef stats) {
        this.id = id;
        this.run = run;
        this.stats = stats;
        log.info("Worker with id : " + id + " was created");
        doWork();
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof WorkResult) {
            getSender().tell(PoisonPill.getInstance(), getSelf());
            stats.tell(message, getSelf());
            if (taskIndex == run.getTasks().size()) {
                taskIndex = 1;
            } else {
                taskIndex++;
            }
            doWork();
        } else if (message.equals("Tick")) {
            doWork();
        }
    }

    private void doWork() {
        Task task = run.getTasks().get(taskIndex);
        ActorRef executor = getContext().actorOf(Props.create(WorkerExecutor.class));
        Work work = convertTaskToWork(task);
        if (work == null) {
            log.debug("work is null!!!!");
        } else {
            executor.tell(work, getSelf());
        }
    }

    private Work convertTaskToWork(Task task) {
        if (task.getType().getName().equals("Http")) {
            return new Http(task.getParams());
        } else if (task.getType().getName().equals("Delay")) {
            return new Delay(task.getParams());
        } else {
            return null;
        }
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
