package br.com.automaticdiary.resources;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import br.com.automaticdiary.entities.Activity;

public class SystemAD {

    public static Activity selectedActivity;
    public static Activity lastRegisteredActivity;
    public static Double defaultPoint= 20.0;


    public static Activity toActivity(Long id, String title, Double performance, Calendar start, Calendar finish, Double score, String description, String category){
        return new Activity(id, title,performance,start, finish, score, description, category);
    }
    public static Activity toActivity(Long id, String title, Double performance, Long start, Long finish, Double score, String description, String category){
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar finishDate = Calendar.getInstance();
        finishDate.setTimeInMillis(finish);
        return new Activity(id, title,performance, startDate, finishDate, score, description, category);
    }

    public static  String getDate(Calendar calendar){
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return ((dayOfMonth < 10) ? "0" : "") + dayOfMonth + "/" + ((month < 10) ? "0" : "") + (month + 1) + "/" + year;
    }

    public static  String getDate(int dayOfMonth, int month, int year){

        return ((dayOfMonth < 10) ? "0" : "") + dayOfMonth + "/" + ((month < 10) ? "0" : "") + (month + 1) + "/" + year;
    }

    public static  String getTime(Calendar calendar){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return ((hour < 10) ? "0" : "") + hour + ":" + ((minute < 10) ? "0" : "") + minute;
    }

    public static String getTime(int hour, int minute){
        return ((hour < 10) ? "0" : "") + hour + ":" + ((minute < 10) ? "0" : "") + minute;
    }

    public static String getPerformanceText(Double performance){
        int hours = performance.intValue();
        Double minuteAux = performance - (double) hours;
        int minute = (int)(minuteAux * 60.0);
        return ((hours < 10) ? "0" : "") + hours + "h" + ((minute < 10) ? "0" : "") + minute;
    }

    public static String getPerformanceText(Calendar start, Calendar finish){
        long diffMilisseconds = finish.getTimeInMillis() - start.getTimeInMillis();

        long performanceHours = TimeUnit.MILLISECONDS.toHours(diffMilisseconds);
        long performanceMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMilisseconds) - TimeUnit.HOURS.toMinutes(performanceHours);

        return performanceHours + "h" + ((performanceMinutes < 10) ? "0" : "") + performanceMinutes;
    }

    public static Double getPerformance(String text){
        String[] filter = text.split("h");
        Double hour = Double.parseDouble(filter[0]);
        Double minute = Double.parseDouble(filter[1]);

        Double decimalPart = minute/60.0;
        return hour + decimalPart;
    }

    public static Double getPerformance(Calendar start, Calendar finish){
        long diffMilisseconds = finish.getTimeInMillis() - start.getTimeInMillis();

        return (diffMilisseconds / (1000.0 * 60.0 * 60.0));
    }

    public static Double getScore(Double performance){
        return performance * defaultPoint;
    }
}
