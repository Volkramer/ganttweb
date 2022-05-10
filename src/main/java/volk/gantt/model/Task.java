package volk.gantt.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private int id;
    @Column(nullable = false, updatable = true)
    private String name;
    private int nbr;
    @Column(nullable = false, updatable = true)
    private Duration duration;
    private Duration marginTotal;
    private Duration marginFree;
    private Date startAsap;
    private Date startLatest;
    private Date endAsap;
    private Date endLatest;

    public Task(String name, int nbr, int duration) {
        this.name = name;
        this.nbr = nbr;
        this.duration = Duration.ofDays(duration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getMarginTotal() {
        return marginTotal;
    }

    public void setMarginTotal(Duration marginTotal) {
        this.marginTotal = marginTotal;
    }

    public Duration getMarginFree() {
        return marginFree;
    }

    public void setMarginFree(Duration marginFree) {
        this.marginFree = marginFree;
    }

    public Date getStartAsap() {
        return startAsap;
    }

    public void setStartAsap(Date startAsap) {
        this.startAsap = startAsap;
    }

    public Date getStartLatest() {
        return startLatest;
    }

    public void setStartLatest(Date startLatest) {
        this.startLatest = startLatest;
    }

    public Date getEndAsap() {
        return endAsap;
    }

    public void setEndAsap(Date endAsap) {
        this.endAsap = endAsap;
    }

    public Date getEndLatest() {
        return endLatest;
    }

    public void setEndLatest(Date endLatest) {
        this.endLatest = endLatest;
    }
}