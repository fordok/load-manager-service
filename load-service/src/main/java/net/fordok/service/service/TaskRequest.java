package net.fordok.service.service;

import net.fordok.service.dto.Type;

import java.util.Map;

/**
 * Created by fordok on 10/25/2015.
 */
public class TaskRequest {

    private String name;
    private Type type;
    private Map<String,String> params;
    private Map<String,String> inputData;
    private Map<String,String> outputData;

    public TaskRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "TaskRequest{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", params=" + params +
                ", inputData=" + inputData +
                ", outputData=" + outputData +
                '}';
    }
}
