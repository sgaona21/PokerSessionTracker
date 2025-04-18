package com.example.pokersessiontracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class CashGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cash_game);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Log New Cash Game");

        Spinner blindsSpinner = findViewById(R.id.blindsSpinner);

        String[] blindOptions = {"1 / 2", "3 / 5", "5 / 10"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                blindOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blindsSpinner.setAdapter(adapter);

        EditText startTimeInput = findViewById(R.id.startTimeInput);
        EditText endTimeInput = findViewById(R.id.endTimeInput);

// Shared listener method
        View.OnClickListener timePickerListener = view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CashGame.this,
                    (dateView, selectedYear, selectedMonth, selectedDay) -> {
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                CashGame.this,
                                (timeView, selectedHour, selectedMinute) -> {
                                    Calendar cal = Calendar.getInstance();
                                    cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                                    cal.set(Calendar.MINUTE, selectedMinute);

                                    String formattedTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.getTime());

                                    String dateTime = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear + " " + formattedTime;
                                    ((EditText) view).setText(dateTime);
                                },
                                hour, minute, false
                        );

                        timePickerDialog.show();
                    },
                    year, month, day
            );
            datePickerDialog.show();
        };

        startTimeInput.setOnClickListener(timePickerListener);
        endTimeInput.setOnClickListener(timePickerListener);
    }
}