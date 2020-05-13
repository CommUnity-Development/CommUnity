package com.development.community;

import java.util.ArrayList;

/**
 * A class which is used to store data that will need to be accessed across the app
 */
public class Controller {
    public static String[] monthsShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October","November", "December"};
    public static String[] statuses = {"Not Signed Up", "In Progress", "Complete"};
    public static boolean restarted = false;

    public ArrayList<Entry> entries;

    public ArrayList<User> users;

    public ArrayList<Message> messages;

}
