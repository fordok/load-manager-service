package net.fordok.service.service;

/**
 * Created by fordok on 10/25/2015.
 */
public class TaskStartResponse {
    private String taskRunId;
    private String resultId;
    private String message;

    public TaskStartResponse() {
    }

    public String getTaskRunId() {
        return taskRunId;
    }

    public void setTaskRunId(String taskRunId) {
        this.taskRunId = taskRunId;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
