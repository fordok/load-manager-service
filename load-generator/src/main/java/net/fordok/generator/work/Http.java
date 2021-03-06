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
public class Http implements Work {

    private static Logger log = LoggerFactory.getLogger(Http.class);

    private final String id;
    private final Map<String,String> params;

    public Http(String id, Map<String,String> inputParams) {
        this.id = id;
        this.params = inputParams;
    }

    @Override
    public WorkResult doWork() {
        WorkResult result = new WorkResult(id, this.getClass().getSimpleName());
        try {
            result.setStartTs(System.currentTimeMillis());
            URL obj = new URL(params.get("url"));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(params.get("method"));
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
