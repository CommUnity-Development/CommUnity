package com.development.community;

import androidx.annotation.NonNull;

import java.util.Calendar;

import java.io.Serializable;

public class Date implements Serializable {
    private int month;
    private int day;
    private int year;


    Date(){
        Calendar calendar = Calendar.getInstance();
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
    }

    Date(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
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



    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    @Override
    public String toString(){
        return Controller.monthsShort[month] + " " + day + ", " + year;
    }
}
