package com.example.glumma;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.content.SharedPreferences;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.glumma", MODE_PRIVATE);
                boolean disclaimerAccepted = sharedPreferences.getBoolean("disclaimer_accepted", false);
                String name = sharedPreferences.getString("name", null);
                String height = sharedPreferences.getString("height", null);
                String weight = sharedPreferences.getString("weight", null);
                String gender = sharedPreferences.getString("gender", null);

                if (disclaimerAccepted) {

                    if (name == null || height == null || weight == null || gender == null) {
                        Intent profiler1Intent = new Intent(SplashScreen.this, Profiler_1.class);
                        startActivity(profiler1Intent);
                        finish();
                        return;
                    }
                    else {
                        Intent homeIntent = new Intent(SplashScreen.this, dashboard.class);
                        startActivity(homeIntent);
                        finish();
                    }
                } else {
                    Intent disclaimerIntent = new Intent(SplashScreen.this, DisclaimerActivity.class);
                    startActivity(disclaimerIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}