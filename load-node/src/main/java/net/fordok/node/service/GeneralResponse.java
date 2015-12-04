package net.fordok.node.service;

/**
 * Created by fordok on 12/4/2015.
 */
public class GeneralResponse {
    private String message;

    public GeneralResponse() {
    }

    public GeneralResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
