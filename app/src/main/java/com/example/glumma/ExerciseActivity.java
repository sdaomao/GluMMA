package com.example.glumma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.ExerciseAdapter;
import information.ExerciseData;
import information.Pressuredata;

public class ExerciseActivity extends AppCompatActivity {
    private Intent intent;

    private RecyclerView recyclerView;


    private ExerciseAdapter adapter;
    private List<ExerciseData> exerciseDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Add the following code snippet to the onCreate method
        // to initialize the RecyclerView and set the adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseDataList= loadInformation();
        adapter = new ExerciseAdapter(exerciseDataList);
        recyclerView.setAdapter(adapter);

        ImageButton glucosebutton = findViewById(R.id.imageButton);
        glucosebutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Glucose Page", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, TrackMe.class);
            startActivity(intent);
            finish();
        });

        ImageButton pressurebutton = findViewById(R.id.imageButton2);
        pressurebutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Pressure Page", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, PressureActivity.class);
            startActivity(intent);
            finish();

        });

        ImageButton weightbutton = findViewById(R.id.imageButton3);
        weightbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Weight Page", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, WeightActivity.class);
            startActivity(intent);
            finish();

        });


        ImageButton foodbutton = findViewById(R.id.imageButton4);
        foodbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Food Page", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
            finish();


        });

        ImageButton exercisebutton = findViewById(R.id.imageButton5);
        exercisebutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "You are already in exercise page", Toast.LENGTH_SHORT).show();

        });


        ImageButton time = findViewById(R.id.imageButton6);
        time.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), " Lab Result Page", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, LabActivity.class);
            startActivity(intent);
            finish();

        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            // Do something in response to button click
            load();
        });

        // This is are for the navigation bottom
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
          intent = new Intent(this, FragmentController.class);
            startActivity(intent);
            finish();
        });
    }
    private List<ExerciseData> loadInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        List<ExerciseData> list = new ArrayList<>();
        String data = sharedPreferences.getString("data", "[]");
        try {
            JSONArray dataArray = new JSONArray(data);
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject obj = dataArray.getJSONObject(i);
                String time = obj.getString("time");
                String filename = obj.getString("filename");
                String period = obj.getString("period");
                String glucose = obj.getString("glucose");
                String systolic = obj.getString("systolic");
                String diastolic = obj.getString("diastolic");
                String weight = obj.getString("weight");
                String food = obj.getString("food");
                String exercise = obj.getString("exercise");
                String notes = obj.getString("notes");
                String times = obj.getString("times");
                String dateday = obj.getString("dateday");

                ExerciseData information = new ExerciseData(exercise,dateday);
                list.add(information);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    void load(){
        intent = new Intent(this, AddInformation.class);
        startActivity(intent);
        finish();
    }
}