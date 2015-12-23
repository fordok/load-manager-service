package net.fordok.generator.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.messages.WorkRun;
import net.fordok.generator.work.Work;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 2:50 PM
 */
public class WorkerSequence extends Worker {
    private int taskIndex = 1;

    public WorkerSequence(int id, WorkRun workRun, ActorRef stats) {
        super(id, workRun, stats);
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
        if (taskIndex == workRun.getWorkList().size() - 1) {
            taskIndex = 0;
        } else {
            taskIndex++;
        }
        return workRun.getWorkList().get(taskIndex);
    }
}
