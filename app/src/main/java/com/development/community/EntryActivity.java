package com.development.community;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class EntryActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private EditText addressTextBox;
    private Button currentLocationButton;
    private EditText taskTextBox;
    private Button submitButton;

    //TODO: Implement an AutoCompleteSupportFragment for the location picker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        timeButton = findViewById(R.id.TimeButton);

        dateButton = findViewById(R.id.DateButton);

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
                        if(chosenHour > 12) {
                            chosenHour -=12;
                            period = "PM";
                        }
                        else if(chosenHour == 12){
                            period = "PM";
                        }
                        else if(chosenHour == 0){
                            chosenHour = 12;
                        }

                        timeButton.setText( "Chosen Time: " + chosenHour + ":" + minuteString + " " + period);

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
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateButton.setText("Date Chosen: "+ Controller.monthsShort[i1] + " " + i2 + ", " + i);
                    }
                }, year, month, day);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });


    }
}
