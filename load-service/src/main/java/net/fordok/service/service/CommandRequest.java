package net.fordok.service.service;

/**
 * Created by fordok on 12/10/2015.
 */
public class CommandRequest {
    private String body;
    private boolean printResult;

    public CommandRequest() {
    }

    public CommandRequest(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPrintResult() {
        return printResult;
    }

    public void setPrintResult(boolean printResult) {
        this.printResult = printResult;
    }
}
