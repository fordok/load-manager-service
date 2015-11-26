package net.fordok.generator.actors;

import akka.actor.ActorSelection;
import akka.actor.RootActorPath;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.Member;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.messages.ClusterMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fordok on 11/21/2015.
 */
public class ClusterListener extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    Cluster cluster = Cluster.get(getContext().system());
    List<Member> members = new ArrayList<>();

    @Override
    public void preStart() {
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            log.info("Message from " + sender().path().toString() + " content : " + message);
        } else if (message instanceof ClusterMessage) {
            ClusterMessage clusterMessage = (ClusterMessage)message;
            members.forEach(member -> pathOf(member).tell(clusterMessage.getContent(), getSelf()));
        } else if (message instanceof MemberUp) {
            MemberUp mUp = (MemberUp) message;
            members.add(mUp.member());
            log.info("Member is Up: {}", mUp.member());
        } else if (message instanceof UnreachableMember) {
            UnreachableMember mUnreachable = (UnreachableMember) message;
            log.info("Member detected as unreachable: {}", mUnreachable.member());
        } else if (message instanceof MemberRemoved) {
            MemberRemoved mRemoved = (MemberRemoved) message;
            members.removeIf(member -> member.address().hostPort().equals(mRemoved.member().address().hostPort()));
            log.info("Member is Removed: {}", mRemoved.member());
        } else if (message instanceof MemberEvent) {
        } else {
            unhandled(message);
        }
    }

    private ActorSelection pathOf(Member member) {
        RootActorPath rootActorPath = new RootActorPath(member.address(), "");
        ActorSelection actorSelection = context().actorSelection(rootActorPath + "/user/" + self().path().name());
        return actorSelection;
    }
}
