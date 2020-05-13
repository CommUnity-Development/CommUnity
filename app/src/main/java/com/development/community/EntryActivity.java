package com.development.community;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The activity in which users create entries (tasks)
 */
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
    private boolean usingCurrentLocation = false;


    public static final String CHANNEL_ID = "community";
    private static final String CHANNEL_NAME = "CommUnity";
    private static final String CHANNEL_DESC = "CommUnity Notifications";

    private String token;



    //TODO: Implement an AutoCompleteSupportFragment for the location picker

    /**
     * Runs when the activity is opened and allows the users to fill out data fields for the entry.
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);

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
                        dateButton.setText(getString(R.string.chosen_date,Controller.getMonthsShort()[month], day, year));
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

                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    assert lm != null;
                    Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    try {
                        assert l != null;
                        addressTextBox.setText(getAddressFromLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
                    } catch (IOException e) {
                        Toast.makeText(EntryActivity.this, "Unable to access location", Toast.LENGTH_SHORT).show();
                    }

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
                if(selectedDate == null || selectedTime==null || taskTextBox.getText().toString().equals("") || (addressTextBox.getText().toString().equals("") && !usingCurrentLocation)) Toast.makeText(EntryActivity.this,
                        "Make sure to fill out all fields", Toast.LENGTH_LONG).show();
                else {


                        if (getLocationFromLatLng(addressTextBox.getText().toString(), getLatLngFromAddress(EntryActivity.this, addressTextBox.getText().toString())) != null) {
                            intent.putExtra("Date", selectedDate);
                            intent.putExtra("Time", selectedTime);
                            intent.putExtra("Task", taskTextBox.getText().toString());
                            intent.putExtra("Location", addressTextBox.getText().toString());
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                Log.i("TAG", user.getUid());
                                databaseReference.push().setValue(new Entry(selectedDate, selectedTime, getLocationFromLatLng(addressTextBox.getText().toString(), getLatLngFromAddress(EntryActivity.this, addressTextBox.getText().toString())),
                                        taskTextBox.getText().toString(), user.getDisplayName(), user.getUid(), 0,
                                        null, null));
                                Toast.makeText(EntryActivity.this, "Successfully Added Task", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                displayNotification(taskTextBox.getText().toString());
                            } else {
                                Toast.makeText(EntryActivity.this, "You are not signed in", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(EntryActivity.this, "Invalid Location", Toast.LENGTH_LONG).show();
                        }


                }
            }
        });



    }


    /**
     * Displays a push notification on the user's device
     * @param info The description for the notificaiton
     */
    private void displayNotification(String info){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Task successfully added").setContentText(info).setPriority(0).setSmallIcon(R.drawable.ic_person_black_24dp);

        NotificationManagerCompat notificationMC = NotificationManagerCompat.from(this);

        notificationMC.notify(1,mBuilder.build());

    }

    /**
     * Checks if location access is granted
     * @return true if location is granted, false otherwise
     */
    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    // Source: https://stackoverflow.com/questions/33865445/gps-location-provider-requires-access-fine-location-permission-for-android-6-0
    /**
     * Runs once the user decides to grant or not to grant fine location permission
     * @param requestCode the code for the request (100 for the Find Location Permission)
     * @param permissions an array of permissions
     * @param grantResults an array of the granted permission results
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    /**
     * Converts an address to a latitude and longitude value and returns a LatLng object
     * @param context The context for the activity
     * @param strAddress The address as a string
     * @return The address as a LatLng object
     */
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

    /**
     * Converts a LatLng and an address to a CommUnityLocation object
     * @param address The address (as a string)
     * @param l The latitude and longitude (as a LatLng object)
     * @return a CommUnityLocation object containing the address and latlng values
     */
    public CommUnityLocation getLocationFromLatLng(String address, LatLng l){
        if(l==null){
//            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            return null;
        }
        CommUnityLocation a = new CommUnityLocation(address);
        a.setLatitude(l.getLat());
        a.setLongitude(l.getLng());
        return a;
    }

    // Source: https://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
    public String getAddressFromLatLng(LatLng l) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(l.getLat(), l.getLng(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        return address;
    }
}
