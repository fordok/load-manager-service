package net.fordok.generator.work;

import net.fordok.generator.messages.WorkResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Fordok
 * Date: 2/15/2015
 * Time: 6:55 PM
 */
public class HttpWork implements Work {

    private static Logger log = LoggerFactory.getLogger(HttpWork.class);

    private final Map<String, String> inputParams;

    public HttpWork(Map<String, String> inputParams) {
        this.inputParams = inputParams;
    }

    @Override
    public WorkResult doWork() {
        WorkResult result = new WorkResult(this.getClass().getSimpleName());
        try {
            result.setStartTs(System.currentTimeMillis());
            URL obj = new URL(inputParams.get("url"));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(inputParams.get("method"));
            int responseCode = con.getResponseCode();
            Map<String,String> output = new HashMap<>();
            output.put("code", String.valueOf(responseCode));
            result.setOutput(output);
            result.setEndTs(System.currentTimeMillis());
        } catch (IOException e) {
            result.setError(e.getClass() + " - " + e.getMessage());
            result.setEndTs(System.currentTimeMillis());
        }
        return result;
    }

}
