package com.development.community;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class EntryActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private EditText addressTextBox;
    private Button currentLocationButton;
    private EditText taskTextBox;
    private Button submitButton;


    private Date selectedDate;
    private Time selectedTime;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private static final String CHANNEL_ID = "community";
    private static final String CHANNEL_NAME = "CommUnity";
    private static final String CHANNEL_DESC = "CommUnity Notifications";



    //TODO: Implement an AutoCompleteSupportFragment for the location picker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        timeButton = findViewById(R.id.TimeButton);
        dateButton = findViewById(R.id.DateButton);
        submitButton = findViewById(R.id.submitButton);
        addressTextBox = findViewById(R.id.addressTextBox);
        currentLocationButton = findViewById(R.id.currentLocationButton);
        taskTextBox = findViewById(R.id.taskTextBox);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tasks");



        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(EntryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int chosenHour, int chosenMinute) {
                        String period = "AM";
                        String minuteString = String.valueOf(chosenMinute);
                        if(minuteString.length()==1) minuteString = "0"+minuteString;
                        int normHour = chosenHour;
                        if(normHour > 12) {
                            normHour -=12;
                            period = "PM";
                        }
                        else if(normHour == 12){
                            period = "PM";
                        }
                        else if(normHour == 0){
                            normHour = 12;
                        }
                        timeButton.setText(getString(R.string.chosen_time, normHour, minuteString, period));
                        selectedTime = new Time(chosenHour, chosenMinute);

                    }
                }, hour, minute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EntryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateButton.setText(getString(R.string.chosen_date,Controller.monthsShort[month], day, year));
                        selectedDate = new Date(month, day, year);
                    }
                }, year, month, day);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement Current Location Button
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                if(selectedDate == null || selectedTime==null || taskTextBox.getText().toString().equals("") || addressTextBox.getText().toString().equals("")) Toast.makeText(EntryActivity.this,
                        "Make sure to fill out all fields", Toast.LENGTH_LONG).show();
                else {
                    if(getLocationFromLatLng(addressTextBox.getText().toString(),getLatLngFromAddress(EntryActivity.this,addressTextBox.getText().toString())) != null){
                        intent.putExtra("Date", selectedDate);
                        intent.putExtra("Time", selectedTime);
                        intent.putExtra("Task", taskTextBox.getText().toString());
                        intent.putExtra("Location", addressTextBox.getText().toString());
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser user = auth.getCurrentUser();
                        if(user != null) {
                            Log.i("TAG", user.getUid());
                            databaseReference.push().setValue(new Entry(selectedDate, selectedTime, getLocationFromLatLng(addressTextBox.getText().toString(),getLatLngFromAddress(EntryActivity.this,addressTextBox.getText().toString())),
                                    taskTextBox.getText().toString(), user.getDisplayName(), user.getUid(), 0,
                                    null, null));
                            Toast.makeText(EntryActivity.this, "Successfully Added Task", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            displayNotification();
                        }
                        else{
                            Toast.makeText(EntryActivity.this, "You are not signed in", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(EntryActivity.this, "Invalid Location", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }


    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Task successfully added").setPriority(0).setSmallIcon(R.drawable.ic_person_black_24dp);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(this);

        notificationMC.notify(1,mBuilder.build());

    }

    public LatLng getLatLngFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public CommUnityLocation getLocationFromLatLng(String address, LatLng l){
        if(l==null){
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            return null;
        }
        CommUnityLocation a = new CommUnityLocation(address);
        a.setLatitude(l.getLat());
        a.setLongitude(l.getLng());
        return a;
    }
}
