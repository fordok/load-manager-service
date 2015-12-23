package net.fordok.generator.messages;

import net.fordok.generator.work.Work;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by fordok on 11/15/2015.
 */
public class WorkRun implements Serializable {
    private List<Work> workList;
    private Map<String,String> runParams;

    public WorkRun(List<Work> workList, Map<String, String> runParams) {
        this.workList = workList;
        this.runParams = runParams;
    }

    public List<Work> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Work> workList) {
        this.workList = workList;
    }

    public Map<String, String> getRunParams() {
        return runParams;
    }

    public void setRunParams(Map<String, String> runParams) {
        this.runParams = runParams;
    }
}
