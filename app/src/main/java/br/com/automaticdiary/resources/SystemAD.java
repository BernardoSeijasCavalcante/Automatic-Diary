package br.com.automaticdiary.resources;

import java.time.Instant;
import java.util.Calendar;

import br.com.automaticdiary.entities.Activity;
import br.com.automaticdiary.enums.ActivityCategory;

public class SystemAD {

    public static Activity toActivity(Integer id, String title, Double performance, Calendar start, Calendar finish, Double score, String description, String category){
        return new Activity(id, title,performance,start, finish, score, description, category);
    }


}
