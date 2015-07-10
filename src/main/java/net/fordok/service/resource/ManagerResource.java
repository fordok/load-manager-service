package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Status;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by fordok on 7/4/2015.
 */
@Path("/manage")
public class ManagerResource {
    private final LoadGenerator loadGenerator;
    private static final DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public ManagerResource(LoadGenerator loadGenerator) {
        this.loadGenerator = loadGenerator;
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Timed
    @Path("/{action}")
    public Status start(@PathParam("action") String action) {
        if (action.equals("start")) {
            loadGenerator.start();
        } else if (action.equals("stop")) {
            loadGenerator.stop();
        } else if (action.equals("suspend")) {
            loadGenerator.suspend();
        } else if (action.equals("resume")) {
            loadGenerator.resume();
        }
        return new Status("OK", formatter.print(new DateTime().withZone(DateTimeZone.UTC)));
    }
}
