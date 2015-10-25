package net.fordok.service.storage;

import net.fordok.service.dto.Session;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.TaskRun;
import net.fordok.service.dto.TaskType;

import java.util.*;

/**
 * Created by fordok on 10/8/2015.
 */
public class StorageImpl implements Storage {
    private static List<Session> sessions = new ArrayList<Session>();
    private static Map<String,Task> tasks = new HashMap<String,Task>();
    private static List<TaskType> taskTypes = new ArrayList<TaskType>();

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
        taskType.setName("Http");
        taskType.setTaskTypeId(UUID.randomUUID().toString());
        List<String> inputParams = new ArrayList<String>();
        inputParams.add("url");
        inputParams.add("method");
        taskType.setInputParams(inputParams);
        taskTypes.add(taskType);

        Task task = new Task("test1");
        task.setTaskId(UUID.randomUUID().toString());
        task.setTaskType(taskType);
        Map<String,String> params = new HashMap<String,String>();
        params.put("url", "http://google.com");
        params.put("method", "GET");
        task.setParams(params);

        TaskRun taskRun = new TaskRun();
        taskRun.setInitialCount(1);
        taskRun.setStatus("Finished");
        taskRun.setTaskId(task.getTaskId());
        taskRun.setTaskRunId(UUID.randomUUID().toString());
        taskRun.setStartTs(new Date());
        taskRun.setStopTs(new Date());
        taskRun.setTotalCount(1);
        taskRun.setPeriod(1000);

        List<TaskRun> taskRuns = new ArrayList<TaskRun>();
        taskRuns.add(taskRun);
        task.setTaskRuns(taskRuns);
        tasks.put(task.getTaskId(), task);
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
        return new ArrayList<Task>(tasks.values());
    }

    @Override
    public List<Task> getTasksBySessionId(String sessionId) {
        return getSessionById(sessionId).getTasks();
    }

    @Override
    public Task getTaskById(String taskId) {
        return tasks.get(taskId);
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
        task.setTaskId(UUID.randomUUID().toString());
        if (task.getTaskRuns() == null) {
            task.setTaskRuns(new ArrayList<TaskRun>());
        }
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Task updateTaskById(String taskId, Task changedTask) {
        tasks.put(taskId, changedTask);
        return changedTask;
    }

    @Override
    public TaskType getTaskTypeByName(String name) {
        for (TaskType taskType : taskTypes) {
            if (taskType.getName().equals(name)) {
                return taskType;
            }
        }
        return null;
    }
}
