package com.example.pokersessiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Poker Session Tracker");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);



        Button logSessionBtn = findViewById(R.id.newSessionButton);
        logSessionBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewSession.class);
            startActivity(intent);
        });

        Button viewLoggedSessionsButton = findViewById(R.id.loggedSessionsButton);
        viewLoggedSessionsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoggedSessions.class);
            startActivity(intent);
        });

        Button viewStatisticsButton = findViewById(R.id.statisticsButton);
        viewStatisticsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Statistics.class);
            startActivity(intent);
        });

        PokerSessionDatabase db = new PokerSessionDatabase(this);
        db.insertDummySessions();

        Button tournamentCalendarButton = findViewById(R.id.tournamentCalendarButton);

        tournamentCalendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("url", "https://www.talkingstickresort.com/phoenix-scottsdale-casino/poker/arena-poker-room-tournaments/");
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}