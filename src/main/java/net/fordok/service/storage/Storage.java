package net.fordok.service.storage;

import net.fordok.service.dto.Session;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.TaskType;

import java.util.List;

/**
 * Created by fordok on 10/9/2015.
 */
public interface Storage {
    List<Session> getSessions();
    Session getSessionById(String sessionId);
    Session saveSession(Session session);
    List<Task> getTasks();
    List<Task> getTasksBySessionId(String sessionId);
    Task getTaskById(String taskId);
    Task addTaskForSessionId(String sessionId, Task task);
    Task saveTask(Task task);
    Task updateTaskById(String taskId, Task changedTask);
    TaskType getTaskTypeByName(String name);
}
