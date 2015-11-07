package net.fordok.service.storage;

import net.fordok.service.dto.Task;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Type;

import java.util.*;

/**
 * Created by fordok on 10/8/2015.
 */
public class StorageImpl implements Storage {
    private static Map<String,Task> tasks = new HashMap<>();
    private static Map<String,Type> types = new HashMap<>();
    private static Map<String,Run> runs = new HashMap<>();

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

        Type typeDelay = new Type();
        typeDelay.setName("Delay");
        List<String> inputParamsDelay = new ArrayList<>();
        inputParamsDelay.add("delayMin");
        inputParamsDelay.add("delayMax");
        typeDelay.setInputParams(inputParamsDelay);
        types.put(typeDelay.getName(), typeDelay);

        Map<Integer,Task> taskMap = new HashMap<>();

        Task task = new Task("test1");
        task.setTaskId(UUID.randomUUID().toString());
        task.setType(type);
        Map<String,String> params = new HashMap<>();
        params.put("url", "http://google.com");
        params.put("method", "GET");
        task.setParams(params);
        Map<String,String> inputData = new HashMap<>();
        inputData.put("login", "admin");
        inputData.put("password", "test");
        task.setInputData(inputData);
        Map<String,String> outputData = new HashMap<>();
        outputData.put("key", "testkey");
        task.setOutputData(outputData);
        task.setBody("some body");
        tasks.put(task.getTaskId(), task);
        taskMap.put(1, task);

        Run run = new Run();
        run.setRunId(UUID.randomUUID().toString());
        run.setName("Test run");
        run.setInitialCount(1);
        run.setTotalCount(10);
        run.setRampUp(1000);
        run.setStatus("Finished");
        run.setRunId(UUID.randomUUID().toString());
        run.setStartTs(new Date());
        run.setStopTs(new Date());
        run.setTasks(taskMap);
        run.setResultId(UUID.randomUUID().toString());
        run.setRunType("sequence");
        runs.put(run.getRunId(), run);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    @Override
    public Task getTaskById(String taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Task saveTask(Task task) {
        task.setTaskId(UUID.randomUUID().toString());
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
        return new ArrayList<Type>(types.values());
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

    @Override
    public List<Run> getRuns() {
        return new ArrayList<Run>(runs.values());
    }

    @Override
    public Run getRunById(String runId) {
        return runs.get(runId);
    }

    @Override
    public Run saveRun(Run run) {
        run.setRunId(UUID.randomUUID().toString());
        return runs.put(run.getRunId(), run);
    }

    @Override
    public Run updateRunById(String runId, Run changedRun) {
        return runs.put(runId, changedRun);
    }
}
