package net.fordok.service.service;

import java.util.List;

/**
 * Created by fordok on 10/26/2015.
 */
public class TypeRequest {
    private String name;
    private List<String> inputParams;
    private List<String> outputParams;

    public TypeRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInputParams() {
        return inputParams;
    }

    public void setInputParams(List<String> inputParams) {
        this.inputParams = inputParams;
    }

    public List<String> getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(List<String> outputParams) {
        this.outputParams = outputParams;
    }
}
