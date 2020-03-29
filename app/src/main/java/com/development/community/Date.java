package com.development.community;

import android.os.Parcelable;

import java.io.Serializable;

public class Date implements Serializable {
    private int month;
    private int day;

    // Not sure if we'll use this yet, but it might be helpful.

    Date(int month, int day, int year){
        this.month = month;
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public String toString(){
        return "Month: "+month + ", Day: "+day;
    }
}
