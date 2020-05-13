package com.development.community;

import java.util.ArrayList;

/**
 * A class which is used to store data that will need to be accessed across the app
 */
public class Controller {
    private static final String[] monthsShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October","November", "December"};
    private static final String[] statuses = {"Not Signed Up", "In Progress", "Complete"};
    private static boolean restarted = false;

    public static void setRestarted(boolean newState){
        restarted = newState;
    }

    public static boolean getRestarted(){
        return restarted;
    }

    public static String[] getMonthsShort() { return monthsShort; }

    public static String[] getMonths() { return months; }

    public static String[] getStatuses() { return statuses; }

    private ArrayList<Entry> entries;

    private ArrayList<User> users;

    private ArrayList<Message> messages;

    public ArrayList<Entry> getEntries(){
        return entries;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

}
