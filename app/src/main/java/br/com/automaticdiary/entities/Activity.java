package br.com.automaticdiary.entities;

import java.time.Instant;
import java.util.Calendar;

import br.com.automaticdiary.enums.ActivityCategory;

public class Activity {

    private Long id;
    private String title;
    private Double performance;
    private Calendar start;
    private Calendar finish;
    private Double score;
    private String description;
    private String category;

    public Activity(){

    }

    public Activity(String title, Double performance, Calendar start, Calendar finish, Double score, String description, String category) {
        this.title = title;
        this.performance = performance;
        this.start = start;
        this.finish = finish;
        this.score = score;
        this.description = description;
        this.category = category;
    }

    public Activity(Long id, String title, Double performance, Calendar start, Calendar finish, Double score, String description, String category) {
        this.id = id;
        this.title = title;
        this.performance = performance;
        this.start = start;
        this.finish = finish;
        this.score = score;
        this.description = description;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getFinish() {
        return finish;
    }

    public void setFinish(Calendar finish) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
