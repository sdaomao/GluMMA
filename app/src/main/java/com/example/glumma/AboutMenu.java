package com.example.glumma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button diabetes = findViewById(R.id.button15);
        diabetes.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, AboutDiabetesActivity.class);
            startActivity(intent);
            finish();

        });

        Button typesOfDiabetes = findViewById(R.id.button16);
        typesOfDiabetes.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, TypesOfDiabetes.class);
            startActivity(intent);
            finish();

        });

        Button OralDiabetesMedication = findViewById(R.id.button18);
        OralDiabetesMedication.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, AboutOralDiabetes.class);
            startActivity(intent);
            finish();

        });

        Button  InsulinDiabetesMedication = findViewById(R.id.button19);
        InsulinDiabetesMedication.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, TypesOfInsulin.class);
            startActivity(intent);
            finish();

        });

        Button symptoms = findViewById(R.id.button20);
        symptoms.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, Symptoms.class);
            startActivity(intent);
            finish();

        });

        Button prevention = findViewById(R.id.button21);
        prevention.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, PreventionAndTreatment.class);
            startActivity(intent);
            finish();

        });

        ImageButton home = findViewById(R.id.imageButton15);
        home.setOnClickListener( v ->{
            Intent intent = new Intent(AboutMenu.this, dashboard.class);
            startActivity(intent);
            finish();
        });

    }
}