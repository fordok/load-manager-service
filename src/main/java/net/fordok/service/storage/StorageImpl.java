package net.fordok.service.storage;

import net.fordok.service.dto.Task;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Type;

import java.util.*;

/**
 * Created by fordok on 10/8/2015.
 */
public class StorageImpl implements Storage {
    private static Map<String,Task> tasks = new HashMap<String,Task>();
    private static Map<String,Type> types = new HashMap<String,Type>();

    public StorageImpl() {

        Type type = new Type();
        type.setName("Http");
        List<String> inputParams = new ArrayList<>();
        inputParams.add("url");
        inputParams.add("method");
        type.setInputParams(inputParams);
        List<String> outputParams = new ArrayList<>();
        outputParams.add("code");
        type.setOutputParams(outputParams);
        types.put(type.getName(), type);

        Task task = new Task("test1");
        task.setTaskId(UUID.randomUUID().toString());
        task.setType(type);
        Map<String,String> params = new HashMap<>();
        params.put("url", "http://google.com");
        params.put("method", "GET");
        task.setParams(params);

        Run run = new Run();
        run.setRunId(UUID.randomUUID().toString());
        run.setInitialCount(1);
        run.setStatus("Finished");
        run.setTaskId(task.getTaskId());
        run.setRunId(UUID.randomUUID().toString());
        run.setStartTs(new Date());
        run.setStopTs(new Date());
        run.setTotalCount(1);
        run.setPeriod(1000);

        List<Run> runs = new ArrayList<>();
        runs.add(run);
        task.setRuns(runs);
        tasks.put(task.getTaskId(), task);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTaskById(String taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Task saveTask(Task task) {
        task.setTaskId(UUID.randomUUID().toString());
        if (task.getRuns() == null) {
            task.setRuns(new ArrayList<Run>());
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
    public List<Type> getTypes() {
        return new ArrayList<>(types.values());
    }

    @Override
    public Type getTypeByName(String name) {
        return types.get(name);
    }

    @Override
    public Type saveType(Type type) {
        types.put(type.getName(), type);
        return type;
    }

    @Override
    public Type updateTypeByName(String typeName, Type changedType) {
        return types.put(typeName, changedType);
    }
}
