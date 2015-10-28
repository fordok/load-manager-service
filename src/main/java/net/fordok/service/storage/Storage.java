package net.fordok.service.storage;

import net.fordok.service.dto.Session;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.Type;

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
    Type getTypeByName(String name);
    List<Type> getTypes();
    Type saveType(Type type);
    Type updateTypeByName(String typeName, Type changedType);
}
