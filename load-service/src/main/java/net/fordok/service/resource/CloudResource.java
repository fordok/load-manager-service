package net.fordok.service.resource;

import com.amazonaws.services.ec2.model.Instance;
import com.codahale.metrics.annotation.Timed;
import net.fordok.aws.service.CloudManager;
import net.fordok.service.service.CommandRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by fordok on 12/2/2015.
 */
@Path("/instances")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class CloudResource {
    private final CloudManager cloudManager;

    public CloudResource(CloudManager cloudManager) {
        this.cloudManager = cloudManager;
    }

    @GET
    @Path("launch")
    public List<Instance> launchInstances(@QueryParam("count") String count) {
        return cloudManager.launchInstances(Integer.valueOf(count));
    }

    @GET
    public List<Instance> getAllInstancesInfo() {
        return cloudManager.getAllInstancesInfo();
    }

    @GET
    @Path("terminate")
    public void terminateAllInstances() {
        cloudManager.terminateAllInstances();
    }

    @GET
    @Path("{instanceId}/terminate")
    public void terminateByInstanceId(@PathParam("instanceId") String instanceId) {
        cloudManager.terminateInstance(instanceId);
    }

    @GET
    @Path("start")
    public void startGeneratorServiceForInstances() {
        String startNodeServiceCmd = "bash start.sh";
        cloudManager.executeCommandForInstances(startNodeServiceCmd);
    }

    @GET
    @Path("exec")
    public void executeCommandForInstances(CommandRequest command) {
        cloudManager.executeCommandForInstances(command.getBody());
    }

    @GET
    @Path("{instanceId}/start")
    public void startGeneratorServiceForInstanceId(@PathParam("instanceId") String instanceId) {
        String startNodeServiceCmd = "bash start.sh";
        cloudManager.executeCommandForInstance(startNodeServiceCmd, instanceId, false);
    }

    @POST
    @Path("{instanceId}/exec/")
    public void executeCommandForInstanceId(@PathParam("instanceId") String instanceId, CommandRequest command) {
        cloudManager.executeCommandForInstance(command.getBody(), instanceId, command.isPrintResult());
    }
}
