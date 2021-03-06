package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.service.dto.Task;
import net.fordok.service.service.TaskRequest;
import net.fordok.service.service.TaskResponse;
import net.fordok.service.storage.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public TaskResponse createTask(TaskRequest taskRequest){
        System.out.println(taskRequest);
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setParams(taskRequest.getParams());
        task.setType(taskRequest.getType().getName());
        task.setInputData(taskRequest.getInputData());
        task.setOutputData(taskRequest.getOutputData());
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
}
