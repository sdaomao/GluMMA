package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class StepController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step_controller);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        replaceFragment(new StepCounter());

        ImageButton home = findViewById(R.id.imageButton7);
        home.setOnClickListener(v -> {
            stopCounterInFragment();
            Intent intent = new Intent(StepController.this, dashboard.class);
            startActivity(intent);
            finish();
        });

        ImageButton Step = findViewById(R.id.imageButton11);
        Step.setOnClickListener(v -> {
            replaceFragment(new StepCounter());
        });

        ImageButton Report = findViewById(R.id.imageButton9);
        Report.setOnClickListener(v -> {
            stopCounterInFragment();
            replaceFragment(new StepReport());
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private void stopCounterInFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof StepCounter) {
            ((StepCounter) currentFragment).stopCounter();
        }
    }
}