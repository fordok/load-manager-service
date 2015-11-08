package net.fordok.generator.configuration;

import net.fordok.generator.work.Work;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 2:53 PM
 */
public class ConfigurationSystem {

    private int workersCount;
    private int period;
    private int rampUp;
    private Work work;

    public ConfigurationSystem() {}

    public ConfigurationSystem(int workersCount, int period, int rampUp, Work work) {
        this.workersCount = workersCount;
        this.period = period;
        this.rampUp = rampUp;
        this.work = work;
    }

    public int getWorkersCount() {
        return workersCount;
    }

    public void setWorkersCount(int workersCount) {
        this.workersCount = workersCount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getRampUp() {
        return rampUp;
    }

    public void setRampUp(int rampUp) {
        this.rampUp = rampUp;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "ConfigurationSystem{" +
                "workersCount=" + workersCount +
                ", period=" + period +
                ", rampUp=" + rampUp +
                ", net.fordok.generator.work=" + work +
                '}';
    }
}
