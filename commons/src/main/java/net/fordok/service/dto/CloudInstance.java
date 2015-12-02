package net.fordok.service.dto;

import java.io.Serializable;

/**
 * Created by fordok on 12/2/2015.
 */
public class CloudInstance implements Serializable {
    private String instanceId;
    private String innerIp;
    private String publicIp;
    private String publicDns;
    private String state;

    public CloudInstance(String instanceId, String innerIp, String publicIp, String publicDns, String state) {
        this.instanceId = instanceId;
        this.innerIp = innerIp;
        this.publicIp = publicIp;
        this.publicDns = publicDns;
        this.state = state;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPublicDns() {
        return publicDns;
    }

    public void setPublicDns(String publicDns) {
        this.publicDns = publicDns;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
