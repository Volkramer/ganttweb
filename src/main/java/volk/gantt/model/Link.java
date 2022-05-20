package volk.gantt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Link implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private int id;
    @Column(nullable = false, updatable = true)
    private int fromTask;
    @Column(nullable = false, updatable = true)
    private int toTask;

    public Link() {
        super();
    }

    public Link(int fromTask, int toTask) {
        this.fromTask = fromTask;
        this.toTask = toTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromTask() {
        return fromTask;
    }

    public void setFromTask(int fromTask) {
        this.fromTask = fromTask;
    }

    public int getToTask() {
        return toTask;
    }

    public void setToTask(int toTask) {
        this.toTask = toTask;
    }

}
