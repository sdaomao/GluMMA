package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DisclaimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_disclaimer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button cancel = findViewById(R.id.button14);
        cancel.setOnClickListener(v -> finish());

        Button proceed = findViewById(R.id.button12);
        proceed.setOnClickListener(v -> {
            // Save the disclaimer acceptance
            getSharedPreferences("com.example.glumma", MODE_PRIVATE).edit().putBoolean("disclaimer_accepted", true).apply();

            Intent homeIntent = new Intent(DisclaimerActivity.this, Profiler_1.class);
            startActivity(homeIntent);

            finish();
        });
    }
}