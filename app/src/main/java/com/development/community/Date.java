package com.development.community;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Calendar;

import java.io.Serializable;

/**
 * A class which stores dates and allows them to be compared
 */
public class Date implements Serializable, Comparable<Date> {
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

    @Override
    public int compareTo(Date b){
        return (year - b.getYear())*365 + toInt() - b.toInt();
    }

    public static int daysBetween(Date a, Date b){
        return -((a.getYear() - b.getYear())*365 + a.toInt() - b.toInt());
    }

    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new Date(month, day, year);
    }

    public static int daysFromToday(Date a){
        return daysBetween(getCurrentDate(), a);
    }

    public int toInt(){
        if(month == 0){
            return day;
        }else if(month == 1){
            return 31 + day;
        }else if(month == 2){
            return 31 + 28 + day;
        }else if(month == 3){
            return 31 + 28 + 31 + day;
        }else if(month == 4){
            return 31 + 28 + 31 + 30 + day;
        }else if(month == 5){
            return 31 + 28 + 31 + 30 + 31 + day;
        }else if(month == 6){
            return 31 + 28 + 31 + 30 + 31 + 30 + day;
        }else if(month == 7) {
            return 31 + 28 + 31 + 30 + 31 + 30 + 31 + day;
        }else if(month == 8){
            return 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + day;
        }else if(month == 9){
            return 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + day;
        }else if(month == 10){
            return 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + day;
        }else if(month == 11){
            return 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + day;
        }
        return 0;
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
