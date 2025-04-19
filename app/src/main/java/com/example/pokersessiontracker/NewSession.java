// Steven Gaona, CIS165DA 20747, STE2342585 //

package com.example.pokersessiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Objects;

public class NewSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_session);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Log New Session");

        Button newCashGameButton = findViewById(R.id.newCashGameButton);
        newCashGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(NewSession.this, CashGame.class);
            startActivity(intent);
        });

        Button newTournamentButton = findViewById(R.id.newTournamentButton);
        newTournamentButton.setOnClickListener(view -> {
            Intent intent = new Intent(NewSession.this, Tournament.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}