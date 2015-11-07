package net.fordok.service.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by fordok on 10/9/2015.
 */
public class Task {

    private String name;
    private String taskId;
    private Type type;
    private Map<String, String> params;
    private Map<String, String> inputData;
    private Map<String, String> outputData;
    private String body;

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getInputData() {
        return inputData;
    }

    public void setInputData(Map<String, String> inputData) {
        this.inputData = inputData;
    }

    public Map<String, String> getOutputData() {
        return outputData;
    }

    public void setOutputData(Map<String, String> outputData) {
        this.outputData = outputData;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
