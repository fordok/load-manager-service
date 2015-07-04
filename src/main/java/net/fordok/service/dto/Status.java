package net.fordok.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by fordok on 7/4/2015.
 */
public class Status {
    private String status;
    private Date ts;

    public Status(String status, Date ts) {
        this.status = status;
        this.ts = ts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty
    public Date getTs() {
        return ts;
    }

    @JsonProperty
    public void setTs(Date ts) {
        this.ts = ts;
    }
}
