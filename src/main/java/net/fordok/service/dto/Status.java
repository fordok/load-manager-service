package net.fordok.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by fordok on 7/4/2015.
 */
public class Status {
    private String status;
    private String ts;

    public Status(String status, String ts) {
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
    public String getTs() {
        return ts;
    }

    @JsonProperty
    public void setTs(String ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}
