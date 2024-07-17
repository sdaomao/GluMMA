package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RelativeLayout trackbutton = findViewById(R.id.trackme);
        trackbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Track me button clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(dashboard.this, TrackMe.class));
        });

        RelativeLayout stepbutton = findViewById(R.id.stepcounter);
        stepbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Step counter button clicked", Toast.LENGTH_SHORT).show();
        });

        RelativeLayout reminderbutton = findViewById(R.id.medication);
        reminderbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Medication reminder button clicked", Toast.LENGTH_SHORT).show();
        });

        RelativeLayout aboutbutton = findViewById(R.id.aboutdiabetes);
        aboutbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "About button clicked", Toast.LENGTH_SHORT).show();
        });
    }
}