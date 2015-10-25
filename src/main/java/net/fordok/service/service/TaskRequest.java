package net.fordok.service.service;

import java.util.Map;

/**
 * Created by fordok on 10/25/2015.
 */
public class TaskRequest {
    private String name;
    private String taskType;
    private Map<String,String> params;

    public TaskRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
