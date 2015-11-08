package net.fordok.service.dto;

import java.util.Date;

/**
 * Created by fordok on 10/9/2015.
 */
public class Result {
    private String resultId;
    private Date startTs;
    private Date stopTs;

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public Date getStartTs() {
        return startTs;
    }

    public void setStartTs(Date startTs) {
        this.startTs = startTs;
    }

    public Date getStopTs() {
        return stopTs;
    }

    public void setStopTs(Date stopTs) {
        this.stopTs = stopTs;
    }
}
