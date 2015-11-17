package net.fordok.generator;

import net.fordok.generator.core.LoadGenerator;
import net.fordok.generator.core.LoadGeneratorImpl;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.TaskRun;
import net.fordok.service.dto.Type;

import java.util.*;

/**
 * User: Fordok
 * Date: 1/1/2015
 * Time: 6:13 PM
 */
public class Runner {
    private static Map<String,Task> tasks = new HashMap<>();
    private static Map<String,Type> types = new HashMap<>();
    private static Map<String,Run> runs = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

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

        Map<Integer,TaskRun> taskMap = new HashMap<>();

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

        TaskRun taskRun = new TaskRun(task, "sequence", null);

        Task taskScheduler = new Task("test1");
        taskScheduler.setTaskId(UUID.randomUUID().toString());
        taskScheduler.setType(type);
        Map<String,String> paramsScheduler = new HashMap<>();
        paramsScheduler.put("url", "http://rambler.ru");
        paramsScheduler.put("method", "GET");
        taskScheduler.setParams(paramsScheduler);
        taskScheduler.setInputData(null);
        taskScheduler.setOutputData(null);
        taskScheduler.setBody("some body");

        Map<String,String> parameters = new HashMap<>();
        parameters.put("periodMin", "50");
        parameters.put("periodMax", "50");

        TaskRun taskRunScheduler = new TaskRun(taskScheduler, "scheduler", parameters);

        Task taskDelay = new Task("test1");
        taskDelay.setTaskId(UUID.randomUUID().toString());
        taskDelay.setType(typeDelay);
        Map<String,String> paramsDelay = new HashMap<>();
        paramsDelay.put("delayMin", "1000");
        paramsDelay.put("delayMax", "1000");
        taskDelay.setParams(paramsDelay);

        TaskRun taskRunDelay = new TaskRun(taskDelay, "sequence", null);

        tasks.put(task.getTaskId(), task);
        taskMap.put(1, taskRun);
        tasks.put(taskDelay.getTaskId(), task);
        taskMap.put(2, taskRunDelay);
        tasks.put(taskScheduler.getTaskId(), task);
        taskMap.put(-1, taskRunScheduler);

        Run run = new Run();
        run.setRunId(UUID.randomUUID().toString());
        run.setName("Test run");
        run.setInitialCount(1);
        run.setTotalCount(1);
        run.setRampUp(1000);
        run.setStatus("Finished");
        run.setRunId(UUID.randomUUID().toString());
        run.setStartTs(new Date());
        run.setStopTs(new Date());
        run.setTasks(taskMap);
        run.setResultId(UUID.randomUUID().toString());
        run.setRunType("sequence");
        runs.put(run.getRunId(), run);

        LoadGenerator loadGenerator = new LoadGeneratorImpl();
        loadGenerator.init();
        loadGenerator.start(run);
        Thread.sleep(10000);
        loadGenerator.stop();
    }
}
