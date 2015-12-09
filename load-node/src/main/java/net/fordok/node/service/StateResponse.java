package net.fordok.node.service;

/**
 * Created by fordok on 12/10/2015.
 */
public class StateResponse {
    private String state;
    private String ts;

    public StateResponse() {
    }

    public StateResponse(String state, String ts) {
        this.state = state;
        this.ts = ts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
