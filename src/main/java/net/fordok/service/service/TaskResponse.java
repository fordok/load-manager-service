package net.fordok.service.service;

/**
 * Created by fordok on 10/25/2015.
 */
public class TaskResponse {
    private String message;
    private String taskId;

    public TaskResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
