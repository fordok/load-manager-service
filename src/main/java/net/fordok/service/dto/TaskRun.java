package net.fordok.service.dto;

import java.util.Date;

/**
 * Created by fordok on 10/25/2015.
 */
public class TaskRun {

    private Task task;
    private String taskRunId;
    private String taskId;
    private String resultId;
    private String status;
    private int initialCount;
    private int totalCount;
    private int period;
    private int rampUp;
    private Date startTs;
    private Date stopTs;

    public TaskRun() {
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTaskRunId() {
        return taskRunId;
    }

    public void setTaskRunId(String taskRunId) {
        this.taskRunId = taskRunId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
