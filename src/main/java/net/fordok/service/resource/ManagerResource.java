package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by fordok on 7/4/2015.
 */
@Path("/manage")
public class ManagerResource {
    private final LoadGenerator loadGenerator;

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
        }
        return new Status("OK", new Date());
    }
}
