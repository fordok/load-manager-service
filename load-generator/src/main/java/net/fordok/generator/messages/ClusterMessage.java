package net.fordok.generator.messages;

import java.io.Serializable;

/**
 * Created by fordok on 11/26/2015.
 */
public class ClusterMessage implements Serializable {
    private String content;

    public ClusterMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
