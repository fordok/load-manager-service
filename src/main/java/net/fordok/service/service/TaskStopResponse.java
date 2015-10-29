package net.fordok.service.service;

/**
 * Created by fordok on 10/30/2015.
 */
public class TaskStopResponse {
    private String resultId;
    private String message;

    public TaskStopResponse() {
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
