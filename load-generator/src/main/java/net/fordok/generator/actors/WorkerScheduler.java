package net.fordok.generator.actors;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.PoisonPill;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.messages.WorkRun;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by fordok on 11/14/2015.
 */
public class WorkerScheduler extends Worker {

    public WorkerScheduler(int id, WorkRun workRun, ActorRef stats) {
        super(id, workRun, stats);
        initSchedulerWithPeriod(Integer.valueOf(workRun.getRunParams().get("periodMin")));
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
            doWork(workRun.getWorkList().iterator().next());
        }
    }

    private Cancellable initSchedulerWithPeriod(long period) {
        return context().system().scheduler().schedule(Duration.Zero(),
                Duration.create(period, TimeUnit.MILLISECONDS), getSelf(), "Tick",
                getContext().system().dispatcher(), null);
    }
}
