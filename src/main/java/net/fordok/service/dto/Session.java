package net.fordok.service.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by fordok on 10/8/2015.
 */
public class Session {
    private String sessionId;
    private String name;
    private String status;
    private List<Task> tasks;
    private Date start_ts;
    private Date end_ts;
    private Result result;

    public Session() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getStart_ts() {
        return start_ts;
    }

    public void setStart_ts(Date start_ts) {
        this.start_ts = start_ts;
    }

    public Date getEnd_ts() {
        return end_ts;
    }

    public void setEnd_ts(Date end_ts) {
        this.end_ts = end_ts;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
