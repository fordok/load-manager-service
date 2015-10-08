package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Status;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by fordok on 7/4/2015.
 */
@Path("/manage")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
public class ManagerResource {
    private final LoadGenerator loadGenerator;
    private static final DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public ManagerResource(LoadGenerator loadGenerator) {
        this.loadGenerator = loadGenerator;
    }

    @GET
    @Path("/{action}")
    public Status action(@PathParam("action") String action) {
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

    @POST
    @Path("/configuration")
    public ConfigurationSystem setConfiguration(ConfigurationSystem configuration) {
        loadGenerator.setConfiguration(configuration);
        return loadGenerator.getConfiguration();
    }

    @GET
    @Path("/configuration")
    public ConfigurationSystem getConfigurationSystem() {
        return loadGenerator.getConfiguration();
    }
}
