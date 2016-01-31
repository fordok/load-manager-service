package net.fordok.node.service;

import java.util.List;

/**
 * Created by fordok on 12/4/2015.
 */
public class InitRequest {

    private String host;
    private String port;
    private List<String> seeds;
    private String resultUrl;

    public InitRequest() {
    }

    public InitRequest(String host, String port, List<String> seeds, String resultUrl) {
        this.host = host;
        this.port = port;
        this.seeds = seeds;
        this.resultUrl = resultUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
}
