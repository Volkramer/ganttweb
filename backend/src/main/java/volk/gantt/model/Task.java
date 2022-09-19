package volk.gantt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, updatable = true)
    private String name;
    @Column(nullable = false, updatable = true)
    private int duration;
    private int marginTotal;
    private int marginFree;
    private int startAsap;
    private int startLatest;
    private int endAsap;
    private int endLatest;

    public Task() {
        super();
    }

    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.startAsap = 0;
        this.endAsap = 0;
        this.marginFree = 0;
        this.marginTotal = 0;
        this.endLatest = 0;
        this.startLatest = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMarginTotal() {
        return marginTotal;
    }

    public void setMarginTotal(int marginTotal) {
        this.marginTotal = marginTotal;
    }

    public int getMarginFree() {
        return marginFree;
    }

    public void setMarginFree(int marginFree) {
        this.marginFree = marginFree;
    }

    public int getStartAsap() {
        return startAsap;
    }

    public void setStartAsap(int startAsap) {
        this.startAsap = startAsap;
    }

    public int getStartLatest() {
        return startLatest;
    }

    public void setStartLatest(int startLatest) {
        this.startLatest = startLatest;
    }

    public int getEndAsap() {
        return endAsap;
    }

    public void setEndAsap(int endAsap) {
        this.endAsap = endAsap;
    }

    public int getEndLatest() {
        return endLatest;
    }

    public void setEndLatest(int endLatest) {
        this.endLatest = endLatest;
    }
}
