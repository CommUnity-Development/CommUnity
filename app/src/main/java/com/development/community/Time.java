package com.development.community;

public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute, int period){
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
