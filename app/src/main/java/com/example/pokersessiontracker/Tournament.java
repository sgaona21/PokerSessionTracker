// Steven Gaona, CIS165DA 20747, STE2342585 //

package com.example.pokersessiontracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Tournament extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tournament);

        EditText nameInput = findViewById(R.id.tournNameInput);
        EditText dateInput = findViewById(R.id.tournDateInput);
        EditText buyInInput = findViewById(R.id.tournBuyInInput);
        EditText cashOutInput = findViewById(R.id.tournCashOutInput);
        Button addBtn = findViewById(R.id.addTournButton);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Log New Tournament");

        dateInput.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Tournament.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String dateStr = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        dateInput.setText(dateStr);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        addBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            String date = dateInput.getText().toString();
            String buyInStr = buyInInput.getText().toString();
            String cashOutStr = cashOutInput.getText().toString();

            if (name.isEmpty() || date.isEmpty() || buyInStr.isEmpty() || cashOutStr.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int buyIn = Integer.parseInt(buyInStr);
            int cashOut = Integer.parseInt(cashOutStr);

            PokerSessionDatabase dbHelper = new PokerSessionDatabase(this);
            dbHelper.insertSession(
                    "tournament",
                    name,
                    null,
                    buyIn,
                    cashOut,
                    null,
                    null,
                    date
            );

            Toast.makeText(this, "Tournament saved!", Toast.LENGTH_SHORT).show();
            finish();

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}