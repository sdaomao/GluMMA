package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Profiler_2 extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button nextButton;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiler_2);

        radioGroup = findViewById(R.id.radioGroup); // Replace with your actual RadioGroup id
        nextButton = findViewById(R.id.button); // Replace with your actual Next button id
        skipButton = findViewById(R.id.button2); // Replace with your actual Next button id

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    String selectedType = radioButton.getText().toString();

                    // Navigate to Profiler_3 for any selected type
                    startActivity(new Intent(Profiler_2.this, Profiler_3.class));
                } else {
                    // No radio button is selected, show a message
                    Toast.makeText(Profiler_2.this, "Please select a diabetes type", Toast.LENGTH_SHORT).show();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(Profiler_2.this, dashboard.class));
            }
        });
    }
}
