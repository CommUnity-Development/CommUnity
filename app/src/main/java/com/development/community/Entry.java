package com.development.community;

import android.location.Location;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

public class Entry implements Serializable {

    private Date date;
    private Time time;
    private CommUnityLocation destination;
    private String task;
    private String clientUsername;
    private String clientUID;
    private int status; // 0: Not signed up, 1: In progress, 2: Completed
    private String serverUsername;
    private String serverUID;


    public Entry(){
        this.status = 0;
        this.date = new Date();
        this.time = new Time();
        this.destination = null;
        this.task = "";
        this.clientUID = null;
        this.serverUsername = null;
        this.serverUID = null;
        this.clientUsername = null;
    }

    public Entry(Date date, Time time, CommUnityLocation destination, String task, String clientUsername, String clientUID, int status,
                 String serverUsername, String serverUID){
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.task = task;
        this.clientUsername = clientUsername;
        this.status = status;
        this.clientUID = clientUID;
        this.serverUID = serverUID;
        this.serverUsername = serverUsername;
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

    public CommUnityLocation getDestination() {
        return destination;
    }

    public void setDestination(CommUnityLocation destination) {
        this.destination = destination;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String username) {
        this.clientUsername = username;
    }


    @NonNull
    @Override
    public String toString(){
        return "Task: "+ task + ", Date: "+date.toString() + ", Time: "+time.toString() +
                ", Location: "+ destination + ", Client Username: "+clientUsername;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClientUID() {
        return clientUID;
    }

    public void setClientUID(String clientUID) {
        this.clientUID = clientUID;
    }

    public String getServerUsername() {
        return serverUsername;
    }

    public void setServerUsername(String serverUsername) {
        this.serverUsername = serverUsername;
    }

    public String getServerUID() {
        return serverUID;
    }

    public void setServerUID(String serverUID) {
        this.serverUID = serverUID;
    }
}
