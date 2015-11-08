package net.fordok.service.storage;

import net.fordok.service.dto.Run;
import net.fordok.service.dto.Task;
import net.fordok.service.dto.Type;

import java.util.List;

/**
 * Created by fordok on 10/9/2015.
 */
public interface Storage {
    List<Task> getTasks();
    Task getTaskById(String taskId);
    Task saveTask(Task task);
    Task updateTaskById(String taskId, Task changedTask);
    List<Type> getTypes();
    Type getTypeByName(String name);
    Type saveType(Type type);
    Type updateTypeByName(String typeName, Type changedType);
    List<Run> getRuns();
    Run getRunById(String runId);
    Run saveRun(Run run);
    Run updateRunById(String runId, Run changedRun);
}
