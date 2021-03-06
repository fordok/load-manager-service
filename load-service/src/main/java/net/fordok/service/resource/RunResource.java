package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.generator.core.LoadGenerator;
import net.fordok.service.dto.Run;
import net.fordok.service.service.RunActionResponse;
import net.fordok.service.service.RunRequest;
import net.fordok.service.service.RunResponse;
import net.fordok.service.storage.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by fordok on 11/7/2015.
 */

@Path("/runs")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class RunResource {
    private final Storage storage;
    private final LoadGenerator loadGenerator;

    public RunResource(Storage storage, LoadGenerator loadGenerator) {
        this.storage = storage;
        this.loadGenerator = loadGenerator;
    }

    @GET
    public List<Run> getRuns() {
        return storage.getRuns();
    }

    @GET
    @Path("/{runId}")
    public Run getRunById(@PathParam("runId") String runId) {
        return storage.getRunById(runId);
    }

    @POST
    public RunResponse createRun(RunRequest request){
        Run run = new Run();
        run.setName(request.getName());
        run.setInitialCount(request.getInitialCount());
        run.setTotalCount(request.getTotalCount());
        run.setRampUp(request.getRampUp());
        run.setTasks(request.getTasks());
        run.setStartTs(request.getStartTs());
        run.setStopTs(request.getStopTs());
        run.setStatus("new");
        run = storage.saveRun(run);
        RunResponse response = new RunResponse();
        response.setMessage("success");
        response.setRunId(run.getRunId());
        response.setResultId(UUID.randomUUID().toString());
        return response;
    }

    @GET
    @Path("/{runId}/start")
    public RunActionResponse start(@PathParam("runId") String runId) {
        Function<Run,Run> startFunc = run -> {
            loadGenerator.start(run);
            run.setStatus("Running");
            return run;
        };
        return makeRunAction(runId, startFunc);
    }

    @GET
    @Path("/{runId}/stop")
    public RunActionResponse stop(@PathParam("runId") String runId) {
        Function<Run,Run> stopFunc = run -> {
            loadGenerator.stop();
            run.setStatus("Finished");
            return run;
        };
        return makeRunAction(runId, stopFunc);
    }

    private RunActionResponse makeRunAction(String runId, Function function) {
        Run run = storage.getRunById(runId);
        RunActionResponse response = new RunActionResponse();
        if (run == null) {
            response.setMessage("error");
            return response;
        } else {
            function.apply(run);
            response.setMessage("success");
            response.setResultId(UUID.randomUUID().toString());
            return response;
        }
    }

    @PUT
    @Path("{runId}")
    public Run updateRun(@PathParam("runId") String runId, Run changedRun){
        return storage.updateRunById(runId, changedRun);
    }
}
