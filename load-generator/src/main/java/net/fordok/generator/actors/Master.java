package net.fordok.generator.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import net.fordok.generator.messages.CommandsManage;
import net.fordok.generator.messages.WorkRun;
import net.fordok.generator.work.Delay;
import net.fordok.generator.work.Http;
import net.fordok.generator.work.Work;
import net.fordok.service.dto.Run;
import net.fordok.service.dto.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Fordok
 * Date: 1/1/2015
 * Time: 6:16 PM
 */
public class Master extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private List<ActorRef> workers = new ArrayList<>();
    private Map<String,Class> taskRunToExecutorMap = new HashMap<>();
    private Run run = null;
    private ActorRef stats = null;

    @Override
    public void preStart() throws Exception {
        stats = getContext().actorOf(Props.create(StatsAggregator.class));
        taskRunToExecutorMap.put("sequence", WorkerSequence.class);
        taskRunToExecutorMap.put("scheduler", WorkerScheduler.class);
        taskRunToExecutorMap.put("random", WorkerRandom.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Received : " + message);
        if (message instanceof CommandsManage) {
            if (message instanceof CommandsManage.Start) {
                run = ((CommandsManage.Start) message).getRun();
                killAndClearWorkers();
                taskRunToExecutorMap.forEach(this::populateWorkersList);
            }
            if (message instanceof CommandsManage.Stop) {
                killAndClearWorkers();
            }
        }
    }

    private void populateWorkersList(String filterName, Class classIns) {
        Map<Integer,Work> workList = new HashMap<>();
        Map<String,String> runParams = new HashMap<>();
        run.getTasks().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getType().equals(filterName))
                .forEach(entry -> {
                    if (entry.getValue().getParams() != null) {
                        runParams.putAll(entry.getValue().getParams());
                    }
                    workList.put(entry.getKey(), convertTaskToWork(entry.getValue().getTask()));
                });
        log.info("work " + filterName + " list size : " + workList.size());
        if (workList.size() > 0) {
            for (int i = 1; i <= run.getTotalCount(); i++) {
                ActorRef worker = getContext().actorOf(Props.create(classIns, i, new WorkRun(workList, runParams), stats));
                workers.add(worker);
            }
        }
    }

    private Work convertTaskToWork(Task task) {
        if (task.getType().getName().equals("Http")) {
            return new Http(task.getParams());
        } else if (task.getType().getName().equals("Delay")) {
            return new Delay(task.getParams());
        } else {
            return null;
        }
    }

    private void killAndClearWorkers() {
        for (ActorRef worker : workers) {
            worker.tell(PoisonPill.getInstance(), getSelf());
        }
        workers.clear();
    }
}
