package net.fordok.generator.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.messages.WorkRun;
import net.fordok.generator.work.Work;

import java.util.Random;

/**
 * Created by fordok on 11/17/2015.
 */
public class WorkerRandom extends Worker {

    public WorkerRandom(int id, WorkRun workRun, ActorRef stats) {
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
        Random r = new Random();
        int min = 1;
        int max = workRun.getWorkList().size();
        int taskIndex =  r.nextInt((max - min) + 1) + min;
        return workRun.getWorkList().get(taskIndex);
    }
}
