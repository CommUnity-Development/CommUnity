package com.development.community;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Controller {
    public static String[] monthsShort = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October","November", "December"};
    public static String[] statuses = {"Not Signed Up", "In Progress", "Complete"};

    //TODO: Implement Updating Database
//    public boolean updateDatabase(Date date, Time time, String destination, String task, String username, int signedUp){
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("test");
//
//    }
}
