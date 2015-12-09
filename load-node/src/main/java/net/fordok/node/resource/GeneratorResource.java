package net.fordok.node.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.generator.core.LoadGenerator;
import net.fordok.node.service.GeneralResponse;
import net.fordok.node.service.InitRequest;
import net.fordok.node.service.StateResponse;
import net.fordok.service.dto.Run;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by fordok on 12/4/2015.
 */
@Path("/generator")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class GeneratorResource {

    private final LoadGenerator loadGenerator;
    private static final DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    public GeneratorResource(LoadGenerator loadGenerator) {
        this.loadGenerator = loadGenerator;
    }

    @POST
    @Path("/init")
    public GeneralResponse init(InitRequest request) {
        loadGenerator.init(request.getHost(), request.getPort(), request.getSeeds());
        return new GeneralResponse("success");
    }

    @POST
    @Path("/start")
    public GeneralResponse start(Run run) {
        loadGenerator.start(run);
        return new GeneralResponse("success");
    }

    @GET
    @Path("/stop")
    public GeneralResponse stop() {
        loadGenerator.stop();
        return new GeneralResponse("success");
    }

    @GET
    @Path("/state")
    public StateResponse status() {
        return new StateResponse(loadGenerator.getState(), formatter.print(new DateTime().withZone(DateTimeZone.UTC)));
    }
}
