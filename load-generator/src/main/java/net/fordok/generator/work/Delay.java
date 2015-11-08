package net.fordok.generator.work;

import net.fordok.generator.messages.WorkResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by fordok on 11/8/2015.
 */
public class Delay implements Work {

    private static Logger log = LoggerFactory.getLogger(Delay.class);

    private final Map<String, String> params;

    public Delay(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public WorkResult doWork() {
        return null;
    }
}
