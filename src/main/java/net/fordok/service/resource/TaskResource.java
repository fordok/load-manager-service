package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Type;
import net.fordok.service.service.TaskRequest;
import net.fordok.service.service.TaskResponse;
import net.fordok.service.service.TaskStartRequest;
import net.fordok.service.service.TaskStartResponse;
import net.fordok.service.storage.Storage;
import net.fordok.work.HttpWork;
import net.fordok.work.Work;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
        task.setType(storage.getTypeByName(taskRequest.getTaskType()));
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
    @Path("/{taskId}/{action}")
    public TaskStartResponse makeAction(@PathParam("taskId") String taskId, @PathParam("action") String action, TaskStartRequest request) {
        Task task = storage.getTaskById(taskId);
        TaskStartResponse response = new TaskStartResponse();
        Run run = mapRequestToRun(request, task);
        if (action.equals("start")) {
            ConfigurationSystem configurationSystem = extractTaskConfiguration(task, request);
            loadGenerator.start(configurationSystem);
            task.getRuns().add(run);
            run.setTask(task);
            run.setStatus("Running");
        } else if (action.equals("stop")) {
            loadGenerator.stop();
            run.setStatus("Finished");
        }
        response.setMessage("Success");
        storage.updateTaskById(task.getTaskId(), task);
        return response;
    }

    private Run mapRequestToRun(TaskStartRequest request, Task task) {
        Run run = new Run();
        run.setInitialCount(request.getInitialCount());
        run.setTotalCount(request.getTotalCount());
        run.setPeriod(request.getPeriod());
        run.setRampUp(request.getRampUp());
        run.setStartTs(request.getStartTs());
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
