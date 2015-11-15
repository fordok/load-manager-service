package net.fordok.service.dto;

import java.util.Map;

/**
 * Created by fordok on 11/15/2015.
 */
public class TaskRun {

    private Task task;
    private String type;
    private Map<String,String> params;

    public TaskRun() {
    }

    public TaskRun(Task task, String type, Map<String, String> params) {
        this.task = task;
        this.type = type;
        this.params = params;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
