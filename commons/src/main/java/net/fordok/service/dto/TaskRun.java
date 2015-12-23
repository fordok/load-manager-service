package net.fordok.service.dto;

/**
 * Created by fordok on 11/15/2015.
 */
public class TaskRun {

    private String taskRunId;
    private Task task;
    private RunParams runParams;

    public TaskRun() {
    }

    public TaskRun(String taskRunId, Task task, RunParams runParams) {
        this.taskRunId = taskRunId;
        this.task = task;
        this.runParams = runParams;
    }

    public String getTaskRunId() {
        return taskRunId;
    }

    public void setTaskRunId(String taskRunId) {
        this.taskRunId = taskRunId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public RunParams getRunParams() {
        return runParams;
    }

    public void setRunParams(RunParams runParams) {
        this.runParams = runParams;
    }
}
