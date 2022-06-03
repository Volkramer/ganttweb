package volk.gantt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import volk.gantt.model.Link;
import volk.gantt.model.Task;
import volk.gantt.service.LinkService;
import volk.gantt.service.TaskService;

public class PertLogic {

    private List<Link> links;
    private List<Task> tasks;
    private final TaskService taskService;
    private final LinkService linkService;

    @Autowired
    public PertLogic(TaskService taskService, LinkService linkService) {
        this.taskService = taskService;
        this.linkService = linkService;
        this.links = linkService.findAllLink();
        this.tasks = taskService.findAllTasks();
    }

    public void calculatePert() {
        List<Task> sortedTasks = new ArrayList<Task>();
        List<Task> poolTasks = this.tasks;
        List<Link> poolLinks = this.links;

        resetTasks();
        this.tasks = topologicalSortTasks(poolTasks, poolLinks, sortedTasks);
        this.tasks.forEach((Task task) -> {
            this.links.forEach((Link link)->{
                if (link.getFromTask() == task.getId()){
                    calcStartAsap(Task task, Link link)
                }    
            });
        });
    }

    private void calcStartAsap(Task task, Link link) {
        task.setStartAsap(startAsap);
    }

    private void calcEndAsap(Task task) {
        task.setEndAsap(task.getDuration() + task.getStartAsap());
    }

    private void resetTasks() {
        this.tasks.forEach((Task task) -> {
            task.setMarginTotal(0);
            task.setMarginFree(0);
            task.setEndAsap(0);
            task.setEndLatest(0);
            task.setStartAsap(0);
            task.setStartLatest(0);
        });
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
            // printTestSort(poolTasks, poolLinks, sortedTasks);
            topologicalSortTasks(poolTasks, poolLinks, sortedTasks);
        }
        return sortedTasks;
    }

    private void printTestSort(List<Task> poolTasks, List<Link> poolLinks, List<Task> sortedTasks) {
        System.out.print("CONTENU DE LA POOLTASKS:\n");
        poolTasks.forEach((Task task) -> {
            System.out.println("ID: " + task.getId() + ", NAME: " + task.getName());
        });
        System.out.println("\n");
        System.out.print("CONTENU DE LA POOLLINKS:\n");
        poolLinks.forEach((Link link) -> {
            System.out.println(
                    "ID: " + link.getId() + " FROM TASK: " + link.getFromTask() + " TO TASK: " + link.getToTask()
                            + ", ");
        });
        System.out.println("\n");
        System.out.print("CONTENU DE LA SORTEDTASKS:\n");
        sortedTasks.forEach((Task task) -> {
            System.out.println("ID: " + task.getId() + ", NAME: " + task.getName());
        });
        System.out
                .println("<----------------------------------------------------------------------------------->\n");
    }
}
