package com.example.glumma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DisclaimerFragment extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_disclaimer);

        Button cancel = findViewById(R.id.button14);
        cancel.setOnClickListener(v -> {
                    Intent dashboardIntent = new Intent(DisclaimerFragment.this, dashboard.class);
                    startActivity(dashboardIntent);
                    finish();
                });

        Button proceed = findViewById(R.id.button12);
        proceed.setOnClickListener(v -> {
            // Save the disclaimer acceptance
           Intent dashboardIntent = new Intent(DisclaimerFragment.this, AboutMenu.class);
              startActivity(dashboardIntent);


            finish();
        });

    }
}