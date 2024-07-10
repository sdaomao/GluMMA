package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.TextUtils;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profiler_1 extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiler_1);

        editTextName = findViewById(R.id.editTextText);
        editTextHeight = findViewById(R.id.editTextNumberDecimal);
        editTextWeight = findViewById(R.id.editTextNumberDecimal1);
        radioGroupGender = findViewById(R.id.radioGroupGender);

        Button nextButton = findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    proceedToProfiler2();
                } else {
                    Toast.makeText(Profiler_1.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput() {
        String name = editTextName.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();

        return !TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(height) &&
                !TextUtils.isEmpty(weight) &&
                radioGroupGender.getCheckedRadioButtonId() != -1;
    }

    private void proceedToProfiler2() {
        Intent intent = new Intent(Profiler_1.this, Profiler_2.class);
        startActivity(intent);
        // Optionally, you can finish() Profiler1Activity to prevent going back.
        finish();
    }
}
