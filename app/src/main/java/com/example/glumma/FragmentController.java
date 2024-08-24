package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentController extends AppCompatActivity implements TimePickerFragment.TimePickerListener {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment_controller);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Load the medication
        ReplaceFragment(new MedicationFragment());

        // This is for the navigation bottom
        ImageButton home = findViewById(R.id.imageButton7);
        home.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Home button clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, dashboard.class);
            startActivity(intent);
            finish();
        });

        ImageButton trackme = findViewById(R.id.imageButton10);
        trackme.setOnClickListener(v -> {
            // Do something in response to button click
            intent = new Intent(this, TrackMe.class);
            startActivity(intent);
            finish();
        });

        ImageButton StepCounter = findViewById(R.id.imageButton11);
        StepCounter.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Step counter button clicked", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, StepController.class);
            startActivity(intent);
            finish();
        });

        ImageButton Reminder = findViewById(R.id.imageButton9);
        Reminder.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "You are already in the medication reminder ", Toast.LENGTH_SHORT).show();
            ReplaceFragment(new MedicationFragment());
        });
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        // Do something with the time chosen by the user
        SetTimer setTimer = (SetTimer) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (setTimer != null) {
            setTimer.updateTimeDisplay(hour, minute);
        }



    }
}