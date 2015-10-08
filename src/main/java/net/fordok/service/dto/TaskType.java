package net.fordok.service.dto;

import java.util.List;

/**
 * Created by fordok on 10/9/2015.
 */
public class TaskType {
    private String taskTypeId;
    private String name;
    private List<String> params;

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

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
