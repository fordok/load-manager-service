package net.fordok.service.storage;

import net.fordok.service.dto.Session;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.TaskType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by fordok on 10/8/2015.
 */
public class StorageImpl implements Storage {
    private static List<Session> sessions = new ArrayList<Session>();
    private static List<Task> tasks = new ArrayList<Task>();

    public StorageImpl() {
        Session session1 = new Session("session1");
        session1.setSessionId(UUID.randomUUID().toString());
        session1.setStartTs(new Date());
        session1.setStatus("Finished");
        Session session2 = new Session("session2");
        session2.setSessionId(UUID.randomUUID().toString());
        session2.setStartTs(new Date());
        session2.setStatus("Finished");
        sessions.add(session1);
        sessions.add(session2);

        TaskType taskType = new TaskType();
        taskType.setName("HttpWork");
        taskType.setTaskTypeId(UUID.randomUUID().toString());

        Task task1 = new Task("test1");
        task1.setTaskId(UUID.randomUUID().toString());
        task1.setStartTs(new Date());
        task1.setStopTs(new Date());
        task1.setStatus("Finished");
        task1.setInitialCount(100);
        task1.setTotalCount(100);
        task1.setPeriod(1000);
        task1.setTaskType(taskType);
        tasks.add(task1);

    }

    @Override
    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public Session getSessionById(String sessionId) {
        for (Session session : sessions) {
            if (session.getSessionId().equals(sessionId)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public Session saveSession(Session session) {
        sessions.add(session);
        return session;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Task> getTasksBySessionId(String sessionId) {
        return getSessionById(sessionId).getTasks();
    }

    @Override
    public Task getTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public Task addTaskForSessionId(String sessionId, Task task) {
        for (Session session : sessions) {
            if (session.getSessionId().equals(sessionId)) {
                session.getTasks().add(task);
                return task;
            }
        }
        return null;
    }

    @Override
    public Task saveTask(Task task) {
        tasks.add(task);
        return task;
    }
}
