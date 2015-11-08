package net.fordok.service.service;

/**
 * Created by fordok on 11/8/2015.
 */
public class RunResponse {

    private String message;
    private String runId;
    private String resultId;

    public RunResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
