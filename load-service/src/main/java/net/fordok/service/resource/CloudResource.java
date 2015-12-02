package net.fordok.service.resource;

import com.amazonaws.services.ec2.model.Instance;
import com.codahale.metrics.annotation.Timed;
import net.fordok.aws.service.CloudManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by fordok on 12/2/2015.
 */
@Path("/cloud")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class CloudResource {
    private final CloudManager cloudManager;

    public CloudResource(CloudManager cloudManager) {
        this.cloudManager = cloudManager;
    }

    @GET
    @Path("/launch")
    public List<Instance> launchInstances(@QueryParam("count") String count) {
        return cloudManager.launchInstances(Integer.valueOf(count));
    }

    @GET
    @Path("terminate")
    public void terminateAllInstances() {
        cloudManager.terminateAllInstances();
    }
}
