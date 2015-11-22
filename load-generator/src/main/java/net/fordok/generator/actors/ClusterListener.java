package net.fordok.generator.actors;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by fordok on 11/21/2015.
 */
public class ClusterListener extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void preStart() {
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof MemberUp) {
            MemberUp mUp = (MemberUp) message;
            log.info("Member is Up: {}", mUp.member());
        } else if (message instanceof UnreachableMember) {
            UnreachableMember mUnreachable = (UnreachableMember) message;
            log.info("Member detected as unreachable: {}", mUnreachable.member());
        } else if (message instanceof MemberRemoved) {
            MemberRemoved mRemoved = (MemberRemoved) message;
            log.info("Member is Removed: {}", mRemoved.member());
        } else if (message instanceof MemberEvent) {
        } else {
            unhandled(message);
        }
    }
}
