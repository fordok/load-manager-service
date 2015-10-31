package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Type;
import net.fordok.service.service.*;
import net.fordok.service.storage.Storage;
import net.fordok.work.HttpWork;
import net.fordok.work.Work;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by fordok on 10/11/2015.
 */

@Path("/tasks")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class TaskResource {

    private final Storage storage;
    private final LoadGenerator loadGenerator;

    public TaskResource(Storage storage, LoadGenerator loadGenerator) {
        this.storage = storage;
        this.loadGenerator = loadGenerator;
    }

    @GET
    public List<Task> getTasks() {
        return storage.getTasks();
    }

    @GET
    @Path("/{taskId}")
    public Task getTaskById(@PathParam("taskId") String taskId) {
        return storage.getTaskById(taskId);
    }

    @POST
    public TaskResponse createTask(TaskRequest taskRequest){
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setParams(taskRequest.getParams());
        task.setType(taskRequest.getType());
        task = storage.saveTask(task);
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setMessage("Success");
        taskResponse.setTaskId(task.getTaskId());
        return taskResponse;
    }

    @PUT
    @Path("/{taskId}")
    public Task updateTask(@PathParam("taskId") String taskId, Task changedTask) {
        return storage.updateTaskById(taskId, changedTask);
    }

    @PUT
    @Path("/{taskId}/start")
    public TaskStartResponse taskStart(@PathParam("taskId") String taskId, TaskStartRequest request) {
        Task task = storage.getTaskById(taskId);
        Run run = mapRequestToRun(request, task);
        ConfigurationSystem configurationSystem = extractTaskConfiguration(task, request);
        loadGenerator.start(configurationSystem);
        run.setStatus("Running");
        task.getRuns().add(run);
        storage.updateTaskById(task.getTaskId(), task);
        TaskStartResponse response = new TaskStartResponse();
        response.setMessage("Success");
        return response;
    }

    @PUT
    @Path("/{taskId}/stop")
    public TaskStopResponse taskStop(@PathParam("taskId") String taskId) {
        Task task = storage.getTaskById(taskId);
        loadGenerator.stop();
        List<Run> runs = task.getRuns();
        for (Run run : runs) {
            if (run.getStatus().equals("Running")) {
                run.setStopTs(new Date());
                run.setStatus("Finished");
            }
        }
        task.setRuns(runs);
        storage.updateTaskById(taskId, task);
        TaskStopResponse response = new TaskStopResponse();
        response.setMessage("Success");
        return response;
    }

    private Run mapRequestToRun(TaskStartRequest request, Task task) {
        Run run = new Run();
        run.setInitialCount(request.getInitialCount());
        run.setTotalCount(request.getTotalCount());
        run.setPeriod(request.getPeriod());
        run.setRampUp(request.getRampUp());
        if (request.getStartTs() == null) {
            run.setStartTs(new Date());
        } else {
            run.setStartTs(request.getStartTs());
        }
        run.setStopTs(request.getStopTs());
        run.setTaskId(task.getTaskId());
        run.setRunId(UUID.randomUUID().toString());
        return run;
    }

    private ConfigurationSystem extractTaskConfiguration(Task task, TaskStartRequest request) {
        ConfigurationSystem configurationSystem = new ConfigurationSystem();
        configurationSystem.setWorkersCount(request.getTotalCount());
        configurationSystem.setPeriod(request.getPeriod());
        Work work = null;
        if (task.getType() != null) {
            Type type = task.getType();
            if (type.getName().equals("Http")) {
                work = new HttpWork(task.getParams());
            }
        }
        configurationSystem.setWork(work);
        return configurationSystem;
    }
}
