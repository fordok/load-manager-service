package net.fordok.service.service;

import net.fordok.service.dto.TaskRun;

import java.util.Date;
import java.util.List;

/**
 * Created by fordok on 11/7/2015.
 */
public class RunRequest {

    private String name;
    private int initialCount;
    private int totalCount;
    private int rampUp;
    private String runType;
    private List<TaskRun> tasks;
    private Date startTs;
    private Date stopTs;

    public RunRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getRampUp() {
        return rampUp;
    }

    public void setRampUp(int rampUp) {
        this.rampUp = rampUp;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public List<TaskRun> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskRun> tasks) {
        this.tasks = tasks;
    }

    public Date getStartTs() {
        return startTs;
    }

    public void setStartTs(Date startTs) {
        this.startTs = startTs;
    }

    public Date getStopTs() {
        return stopTs;
    }

    public void setStopTs(Date stopTs) {
        this.stopTs = stopTs;
    }
}
