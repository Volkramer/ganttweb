package volk.gantt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, updatable = false)
    private Long fromTask;
    @Column(nullable = false, updatable = false)
    private Long toTask;

    public Link() {
        super();
    }

    public Link(Long fromTask, Long toTask) {
        this.fromTask = fromTask;
        this.toTask = toTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromTask() {
        return fromTask;
    }

    public void setFromTask(Long fromTask) {
        this.fromTask = fromTask;
    }

    public Long getToTask() {
        return toTask;
    }

    public void setToTask(Long toTask) {
        this.toTask = toTask;
    }

}
