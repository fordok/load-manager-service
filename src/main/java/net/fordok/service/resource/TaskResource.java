package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.service.dto.Task;
import net.fordok.service.storage.Storage;

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

    public TaskResource(Storage storage) {
        this.storage = storage;
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
        task.setStatus("Active");
        return storage.saveTask(task);
    }
}
