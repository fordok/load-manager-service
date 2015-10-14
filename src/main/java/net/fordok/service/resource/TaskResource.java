package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.configuration.ConfigurationSystem;
import net.fordok.core.LoadGenerator;
import net.fordok.service.dto.Configuration;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.TaskType;
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
    public Task setTask(Task task){
        task.setTaskId(UUID.randomUUID().toString());
        task.setStartTs(new Date());
        task.setStatus("New");
        return storage.saveTask(task);
    }

    @PUT
    @Path("/{taskId}/{action}")
    public Task makeAction(@PathParam("taskId") String taskId, @PathParam("action") String action) {
        Task task = storage.getTaskById(taskId);
        if (action.equals("start")) {
            ConfigurationSystem configurationSystem = extractTaskConfiguration(task);
            loadGenerator.setConfiguration(configurationSystem);
            loadGenerator.start();
            task.setStatus("Running");
        } else if (action.equals("stop")) {
            loadGenerator.stop();
            task.setStatus("Finished");
        } else if (action.equals("suspend")) {
            loadGenerator.suspend();
            task.setStatus("Suspended");
        } else if (action.equals("resume")) {
            loadGenerator.resume();
            task.setStatus("Running");
        }
        storage.saveTask(task);
        return task;
    }

    private ConfigurationSystem extractTaskConfiguration(Task task) {
        ConfigurationSystem configurationSystem = new ConfigurationSystem();
        configurationSystem.setWorkersCount(task.getTotalCount());
        configurationSystem.setPeriod(task.getPeriod());
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
