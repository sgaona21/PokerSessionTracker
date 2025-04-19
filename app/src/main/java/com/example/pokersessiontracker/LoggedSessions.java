// Steven Gaona, CIS165DA 20747, STE2342585 //

package com.example.pokersessiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Objects;

public class LoggedSessions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logged_sessions);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Logged Sessions");
        ListView sessionListView = findViewById(R.id.sessionListView);
        PokerSessionDatabase dbHelper = new PokerSessionDatabase(this);
        ArrayList<SessionItem> sessionData = dbHelper.getAllSessions();

        SessionAdapter adapter = new SessionAdapter(this, sessionData, dbHelper);
        sessionListView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}