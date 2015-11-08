package net.fordok.generator.configuration;

import akka.actor.ActorRef;
import net.fordok.generator.work.Work;

/**
 * User: Fordok
 * Date: 1/4/2015
 * Time: 9:10 PM
 */
public class ConfigurationWorker {
    private ActorRef statsActor;
    private long period;
    private Work work;

    public ConfigurationWorker(long period, Work work, ActorRef statsActor) {
        this.period = period;
        this.work = work;
        this.statsActor = statsActor;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public ActorRef getStatsActor() {
        return statsActor;
    }

    public void setStatsActor(ActorRef statsActor) {
        this.statsActor = statsActor;
    }

    @Override
    public String toString() {
        return "ConfigurationWorker{" +
                "period=" + period +
                ", net.fordok.generator.work=" + work +
                '}';
    }
}
