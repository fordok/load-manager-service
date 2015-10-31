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
}
