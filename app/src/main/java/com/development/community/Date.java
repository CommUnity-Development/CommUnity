package com.development.community;

import android.annotation.NonNull;

public class Date {
    private int month;
    private int day;

    // Not sure if we'll use this yet, but it might be helpful.
    public enum Months{
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    public Date(int month, int day){
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