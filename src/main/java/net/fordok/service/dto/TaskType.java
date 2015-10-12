package net.fordok.service.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by fordok on 10/9/2015.
 */
public class TaskType {
    private String taskTypeId;
    private String name;
    private Map<String, String> params;

    public TaskType() {
    }

    public String getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
