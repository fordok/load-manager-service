package net.fordok.service.service;

/**
 * Created by fordok on 10/25/2015.
 */
public class TaskStartResponse {
    private String runId;
    private String resultId;
    private String message;

    public TaskStartResponse() {
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
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
