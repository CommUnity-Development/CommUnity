package com.development.community;

import java.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;

public class Time implements Serializable {
    private int hour;
    private int minute;


    Time(){
        Calendar calendar = Calendar.getInstance();
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString(){
        return hour + ":" + minute;
    }
}
