package br.com.automaticdiary.resources;

import java.util.Calendar;

import br.com.automaticdiary.entities.Activity;

public class SystemAD {

    public static Activity selectedActivity;
    public static Activity lastRegisteredActivity;

    public static Activity toActivity(Integer id, String title, Double performance, Calendar start, Calendar finish, Double score, String description, String category){
        return new Activity(id, title,performance,start, finish, score, description, category);
    }
    public static Activity toActivity(Integer id, String title, Double performance, Long start, Long finish, Double score, String description, String category){
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar finishDate = Calendar.getInstance();
        finishDate.setTimeInMillis(finish);
        return new Activity(id, title,performance, startDate, finishDate, score, description, category);
    }


}
