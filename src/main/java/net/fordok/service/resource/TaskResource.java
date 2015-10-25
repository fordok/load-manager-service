package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Configuration;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.TaskRun;
import net.fordok.service.dto.TaskType;
import net.fordok.service.service.TaskRequest;
import net.fordok.service.service.TaskResponse;
import net.fordok.service.service.TaskStartRequest;
import net.fordok.service.service.TaskStartResponse;
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
        task.setTaskType(storage.getTaskTypeByName(taskRequest.getTaskType()));
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
        TaskRun taskRun = new TaskRun();
        taskRun.setInitialCount(request.getInitialCount());
        taskRun.setTotalCount(request.getTotalCount());
        taskRun.setPeriod(request.getPeriod());
        taskRun.setRampUp(request.getRampUp());
        taskRun.setStartTs(request.getStartTs());
        taskRun.setStopTs(request.getStopTs());
        taskRun.setTaskId(task.getTaskId());
        taskRun.setTaskRunId(UUID.randomUUID().toString());
        if (action.equals("start")) {
            ConfigurationSystem configurationSystem = extractTaskConfiguration(task, request);
            loadGenerator.setConfiguration(configurationSystem);
            loadGenerator.start();
            task.getTaskRuns().add(taskRun);
            response.setStatus("Running");
            taskRun.setStatus("Running");
        } else if (action.equals("stop")) {
            loadGenerator.stop();
            response.setStatus("Finished");
            taskRun.setStatus("Finished");
        } else if (action.equals("suspend")) {
            loadGenerator.suspend();
            response.setStatus("Suspended");
        } else if (action.equals("resume")) {
            loadGenerator.resume();
            response.setStatus("Running");
        }
        response.setMessage("Success");
        storage.updateTaskById(task.getTaskId(), task);
        return response;
    }

    private ConfigurationSystem extractTaskConfiguration(Task task, TaskStartRequest request) {
        ConfigurationSystem configurationSystem = new ConfigurationSystem();
        configurationSystem.setWorkersCount(request.getTotalCount());
        configurationSystem.setPeriod(request.getPeriod());
        Work work = null;
        if (task.getTaskType() != null) {
            TaskType taskType = task.getTaskType();
            if (taskType.getName().equals("Http")) {
                work = new HttpWork(task.getName(), task.getParams().get("url"), task.getParams().get("method"));
            }
        }
        configurationSystem.setWork(work);
        return configurationSystem;
    }
}
