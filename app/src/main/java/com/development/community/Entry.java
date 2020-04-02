package com.development.community;

import androidx.annotation.NonNull;
import android.location.Location;

import java.io.Serializable;
import java.util.Calendar;

public class Entry implements Serializable {

    private Date date;
    private Time time;
    private String destination;
    private String task;


    public Entry(){
        Calendar calendar = Calendar.getInstance();
        this.date = new Date();
        this.time = new Time();
        this.destination = "";
        this.task = "";
    }

    public Entry(Date date, Time time, String destination, String task){
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.task = task;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    @NonNull
    @Override
    public String toString(){
        return "Task: "+ task + ", Date: "+date.toString() + ", Time: "+time.toString() +
                ", Location: "+ destination;
    }
}
