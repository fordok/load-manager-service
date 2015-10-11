package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.service.dto.Session;
import net.fordok.service.dto.Task;
import net.fordok.service.storage.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by fordok on 10/9/2015.
 */

@Path("/sessions")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class SessionResource {

    private final Storage storage;

    public SessionResource(Storage storage) {
        this.storage = storage;
    }

    @GET
    public List<Session> getSessions() {
        return storage.getSessions();
    }

    @GET
    @Path("/{sessionId}")
    public Session getSessionById(@PathParam("sessionId") String sessionId) {
        return storage.getSessionById(sessionId);
    }

    @POST
    public Session setSession(Session session){
        session.setSessionId(UUID.randomUUID().toString());
        session.setStartTs(new Date());
        session.setStatus("Active");
        return storage.saveSession(session);
    }

    @GET
    @Path("/{sessionId}/tasks")
    public List<Task> getTasksBySessionId(@PathParam("sessionId") String sessionId) {
        return storage.getTasksBySessionId(sessionId);
    }

    @GET
    @Path("/{sessionId}/tasks/{taskId}")
    public Session getTaskByIdAndSessionId(@PathParam("sessionId") String sessionId, @PathParam("taskId") String taskId) {
        return storage.getSessionById(sessionId);
    }
}
