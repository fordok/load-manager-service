package net.fordok.service.resource;

import com.amazonaws.services.ec2.model.Instance;
import com.codahale.metrics.annotation.Timed;
import net.fordok.aws.service.CloudManager;
import net.fordok.service.service.CommandRequest;
import net.fordok.service.service.GeneralResponse;

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
    public GeneralResponse terminateAllInstances() {
        cloudManager.terminateAllInstances();
        return new GeneralResponse("success");
    }

    @GET
    @Path("{instanceId}/terminate")
    public GeneralResponse terminateByInstanceId(@PathParam("instanceId") String instanceId) {
        cloudManager.terminateInstance(instanceId);
        return new GeneralResponse("success");
    }

    @GET
    @Path("start")
    public GeneralResponse startGeneratorServiceForInstances() {
        String startNodeServiceCmd = "bash start.sh";
        cloudManager.executeCommandForInstances(startNodeServiceCmd);
        return new GeneralResponse("success");
    }

    @POST
    @Path("exec")
    public GeneralResponse executeCommandForInstances(CommandRequest command) {
        cloudManager.executeCommandForInstances(command.getBody());
        return new GeneralResponse("success");
    }

    @GET
    @Path("{instanceId}/start")
    public GeneralResponse startGeneratorServiceForInstanceId(@PathParam("instanceId") String instanceId) {
        String startNodeServiceCmd = "bash start.sh";
        cloudManager.executeCommandForInstance(startNodeServiceCmd, instanceId, false);
        return new GeneralResponse("success");
    }

    @POST
    @Path("{instanceId}/exec/")
    public GeneralResponse executeCommandForInstanceId(@PathParam("instanceId") String instanceId, CommandRequest command) {
        cloudManager.executeCommandForInstance(command.getBody(), instanceId, command.isPrintResult());
        return new GeneralResponse("success");
    }
}
