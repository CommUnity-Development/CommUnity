package com.development.community;

import java.util.Calendar;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;

/**
 * Class that stores Time values for the entries
 */
public class Time implements Serializable, Comparable<Time> {
    private int hour;
    private int minute;

    /**
     * Default Constructor
     */
    Time(){
        Calendar calendar = Calendar.getInstance();
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    /**
     * Parameter Constructor
     * @param hour the hour of the day
     * @param minute the minute of the day
     */
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

    @NonNull
    @Override
    public String toString(){
        String minuteString = String.valueOf(minute);
        if(minute < 10) minuteString = "0"+minuteString;
        return hour + ":" + minuteString;
    }

    @Override
    public int compareTo(Time o) {
        if(hour > o.getHour()) return 1;
        else if(hour < o.getHour()) return -1;
        else{
            return Integer.compare(minute, o.getMinute());
        }
    }
}
