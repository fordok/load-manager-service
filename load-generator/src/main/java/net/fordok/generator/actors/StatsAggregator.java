package net.fordok.generator.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.messages.WorkResult;

/**
 * User: Fordok
 * Date: 2/24/2015
 * Time: 12:07 AM
 */
public class StatsAggregator extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof WorkResult) {
            WorkResult result = (WorkResult)message;
            log.info("Received : " + (WorkResult)message + " lag : " + (result.getEndTs() - result.getStartTs()));
        }
    }
}
