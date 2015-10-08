package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.service.dto.Status;
import net.fordok.work.HttpWork;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by fordok on 7/4/2015.
 */
@Path("/status")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class StatusResource {

    private static final DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    @GET
    public Status getStatus() {
        return new Status("OK", formatter.print(new DateTime().withZone(DateTimeZone.UTC)));
    }
}
