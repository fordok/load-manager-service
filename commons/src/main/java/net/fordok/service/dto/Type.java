package net.fordok.service.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by fordok on 10/9/2015.
 */
public class Type {
    private String name;
    private List<String> inputParams;
    private List<String> outputParams;

    public Type() {
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
