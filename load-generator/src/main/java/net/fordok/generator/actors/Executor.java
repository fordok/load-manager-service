package net.fordok.generator.actors;

import akka.actor.UntypedActor;
import net.fordok.generator.messages.WorkResult;
import net.fordok.generator.work.Work;

/**
 * User: fordok
 * Date: 6/25/2015
 */
public class Executor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Work) {
            Work work = (Work)message;
            WorkResult result = work.doWork();
            getSender().tell(result, getSelf());
        }
    }
}
