package volk.gantt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import volk.gantt.model.Link;
import volk.gantt.model.Task;

public class PertLogicService {

    private List<Link> links;
    private List<Task> tasks;
    private final TaskService taskService;
    private final LinkService linkService;

    @Autowired
    public PertLogicService(TaskService taskService, LinkService linkService) {
        this.taskService = taskService;
        this.linkService = linkService;
    }

    public void calculatePert() {
        List<Task> sortedTasks = new ArrayList<Task>();
        List<Task> poolTasks = this.taskService.findAllTasks();
        List<Link> poolLinks = this.linkService.findAllLinks();

        this.tasks = this.topologicalSortTasks(poolTasks, poolLinks, sortedTasks);
        this.links = this.linkService.findAllLinks();
        for (Task task : this.tasks) {
            this.resetTask(task);
            for (Link link : this.links) {
                if (task.getId() == link.getToTask()) {
                    Task fromTask = this.taskService.findTaskById(link.getFromTask());
                    task.setStartAsap(this.calculateStartAsap(fromTask, task));
                }
            }
            task.setEndAsap(calculateEndAsap(task));
        }
        Collections.reverse(this.tasks);
        for (Task task : this.tasks) {
            boolean flag = false;
            for (Link link : links) {
                if (task.getId() == link.getFromTask()) {
                    flag = true;
                    Task toTask = this.taskService.findTaskById(link.getToTask());
                    task.setEndLatest(this.calculateEndLatest(task, toTask));
                }
            }
            if (!flag) {
                task.setEndLatest(task.getEndAsap());
            }
            task.setStartLatest(this.calculateStartLatest(task));
            task.setMarginTotal(this.calculateMarginT(task));
            this.taskService.updateTask(task);
        }
    }

    // private void printTask(Task task) {
    // System.out.println("ID: " + task.getId() + ", NAME: " + task.getName() + ",
    // START ASAP: " + task.getStartAsap()
    // + ", DURATION: " + task.getDuration() + ", END ASAP:" + task.getEndAsap());
    // }

    private int calculateStartAsap(Task fromTask, Task task) {
        int startAsap = fromTask.getEndAsap();
        if (task.getStartAsap() > startAsap) {
            return task.getStartAsap();
        } else {
            return startAsap;
        }
    }

    private int calculateStartLatest(Task task) {
        int startLatest = task.getEndLatest() - task.getDuration();
        return startLatest;
    }

    private int calculateEndAsap(Task task) {
        int endAsap = task.getDuration() + task.getStartAsap();
        return endAsap;
    }

    private int calculateEndLatest(Task task, Task toTask) {
        int endLatest = toTask.getStartLatest();
        if ((task.getEndLatest() < endLatest) && task.getEndLatest() != 0) {
            return task.getEndLatest();
        } else {
            return endLatest;
        }
    }

    private int calculateMarginT(Task task) {
        int marginT = task.getEndLatest() - task.getEndAsap();
        return marginT;
    }

    private void resetTask(Task task) {
        task.setMarginTotal(0);
        task.setMarginFree(0);
        task.setEndAsap(0);
        task.setEndLatest(0);
        task.setStartAsap(0);
        task.setStartLatest(0);
    }

    private List<Task> topologicalSortTasks(List<Task> poolTasks, List<Link> poolLinks, List<Task> sortedTasks) {
        while (!poolTasks.isEmpty()) {
            List<Task> tasksToRemove = new ArrayList<Task>();
            List<Link> linksToRemove = new ArrayList<Link>();
            for (Task task : poolTasks) {
                boolean flag = false;
                for (Link link : poolLinks) {
                    if (link.getToTask() == task.getId()) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    sortedTasks.add(task);
                    tasksToRemove.add(task);
                    for (Link link : poolLinks) {
                        if (link.getFromTask() == task.getId()) {
                            linksToRemove.add(link);
                        }
                    }
                }
            }
            poolTasks.removeAll(tasksToRemove);
            poolLinks.removeAll(linksToRemove);
            // this.printTestSort(poolTasks, poolLinks, sortedTasks);
            this.topologicalSortTasks(poolTasks, poolLinks, sortedTasks);
        }
        return sortedTasks;
    }

    // private void printTestSort(List<Task> poolTasks, List<Link> poolLinks,
    // List<Task> sortedTasks) {
    // System.out.print("CONTENU DE LA POOLTASKS:\n");
    // poolTasks.forEach((Task task) -> {
    // System.out.println("ID: " + task.getId() + ", NAME: " + task.getName());
    // });
    // System.out.println("\n");
    // System.out.print("CONTENU DE LA POOLLINKS:\n");
    // poolLinks.forEach((Link link) -> {
    // System.out.println(
    // "ID: " + link.getId() + " FROM TASK: " + link.getFromTask() + " TO TASK: " +
    // link.getToTask()
    // + ", ");
    // });
    // System.out.println("\n");
    // System.out.print("CONTENU DE LA SORTEDTASKS:\n");
    // sortedTasks.forEach((Task task) -> {
    // System.out.println("ID: " + task.getId() + ", NAME: " + task.getName());
    // });
    // System.out
    // .println("<----------------------------------------------------------------------------------->\n");
    // }
}
