package br.com.automaticdiary.entities;

import java.time.Instant;

import br.com.automaticdiary.enums.ActivityCategory;

public class Activity {

    private String title;
    private Double performance;
    private Instant start;
    private Instant finish;
    private Double score;
    private String description;
    private ActivityCategory category;

    public Activity(){

    }

    public Activity(String title, Double performance, Instant start, Instant finish, Double score, String description, ActivityCategory category) {
        this.title = title;
        this.performance = performance;
        this.start = start;
        this.finish = finish;
        this.score = score;
        this.description = description;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getFinish() {
        return finish;
    }

    public void setFinish(Instant finish) {
        this.finish = finish;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }
}
