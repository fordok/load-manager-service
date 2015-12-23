package net.fordok.service.dto;

import java.util.Map;

/**
 * Created by fordok on 12/23/2015.
 */
public class RunParams {
    private String type;
    private Map<String,String> params;

    public RunParams() {
    }

    public RunParams(String type, Map<String, String> params) {
        this.type = type;
        this.params = params;
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
