package com.example.pokersessiontracker;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Statistics");

        RadioButton cashToggle = findViewById(R.id.cashToggle);
        RadioButton tournToggle = findViewById(R.id.tournToggle);
        LinearLayout cashLayout = findViewById(R.id.cashStatsLayout);
        LinearLayout tournLayout = findViewById(R.id.tournStatsLayout);

        cashToggle.setOnClickListener(v -> {
            cashLayout.setVisibility(View.VISIBLE);
            tournLayout.setVisibility(View.GONE);
        });

        tournToggle.setOnClickListener(v -> {
            cashLayout.setVisibility(View.GONE);
            tournLayout.setVisibility(View.VISIBLE);
        });

        PokerSessionDatabase db = new PokerSessionDatabase(this);

// Cash stats
        StatsResult cashStats = db.getCashStats();
        ((TextView) findViewById(R.id.cashProfit)).setText("Total Profit: $" + cashStats.totalProfit);
        ((TextView) findViewById(R.id.cashWins)).setText("Winning Sessions: " + cashStats.winsOrCashes);
        ((TextView) findViewById(R.id.cashLosses)).setText("Losing Sessions: " + cashStats.lossesOrROI);

// Tournament stats
        StatsResult tournStats = db.getTournamentStats();
        ((TextView) findViewById(R.id.tournProfit)).setText("Total Profit: $" + tournStats.totalProfit);
        ((TextView) findViewById(R.id.tournROI)).setText("ROI: " + tournStats.lossesOrROI + "%");
        ((TextView) findViewById(R.id.tournCashes)).setText("Total Cashes: " + tournStats.winsOrCashes);





    }
}