package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.service.dto.Status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by fordok on 7/4/2015.
 */
@Path("/")
public class StatusResource {

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Timed
    public Status getStatus() {
        return new Status("OK", new Date());
    }
}
