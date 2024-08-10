package com.example.glumma;

import static android.content.Context.MODE_PRIVATE;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class StepCounter extends Fragment implements SensorEventListener {

    private static final String TAG = "StepCounter";
    private static final int ACTIVITY_RECOGNITION_REQUEST_CODE = 1;
    private static final int STEPS_PER_MILE = 2000;
    private static final double CALORIES_PER_STEP = 0.04;

    private TextView stepCount, milesCount, minutesCount, caloriesCount;
    private boolean start = false;
    private boolean save = false;
    private double minutes = 0;
    private int steps = 0;
    private int initialStepCount = 0;

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private Handler handler;
    private Runnable runnable;

    public StepCounter() {
        // Required empty public constructor
    }

    public static StepCounter newInstance(String param1, String param2) {
        StepCounter fragment = new StepCounter();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACTIVITY_RECOGNITION}, ACTIVITY_RECOGNITION_REQUEST_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_counter, container, false);

        stepCount = view.findViewById(R.id.tex2);
        milesCount = view.findViewById(R.id.textView26);
        minutesCount = view.findViewById(R.id.textView16);
        caloriesCount = view.findViewById(R.id.textView190);

        ImageButton stepButton = view.findViewById(R.id.imageButton8);
        stepButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Step counter button clicked", Toast.LENGTH_SHORT).show();
            if (!start) {
                stepButton.setImageResource(R.drawable.pause);
                startCounter();
                start = true;
                save = true;
            } else {
                stepButton.setImageResource(R.drawable.play);
                pauseCounter();
                start = false;
            }
        });

        Button SaveButton = view.findViewById(R.id.button6);
        SaveButton.setOnClickListener(v -> {
           if (!save) {
               Toast.makeText(requireContext(), "Start counter first", Toast.LENGTH_SHORT).show();
           } else {
               SaveCounter();
               save = false;
           }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (stepSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (start && event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (initialStepCount == 0) {
                initialStepCount = (int) event.values[0];
            }

            int currentStepCount = (int) event.values[0];
            int newSteps = currentStepCount - initialStepCount;
            double newMiles = newSteps / (double) STEPS_PER_MILE;
            double newCalories = newSteps * CALORIES_PER_STEP;

            animateTextView(stepCount, steps, newSteps);
            animateTextView(milesCount, Double.parseDouble(milesCount.getText().toString()), newMiles);
            animateTextView(caloriesCount, Double.parseDouble(caloriesCount.getText().toString()), newCalories);

            steps = newSteps;

            Log.d(TAG, "Steps: " + steps + ", Miles: " + newMiles + ", Calories: " + newCalories);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can handle sensor accuracy changes here if needed
    }

    private void startCounter() {
        if (handler == null) {
            handler = new Handler();
            runnable = new Runnable() {
                int seconds = 0;

                @Override
                public void run() {
                    if (start) {
                        seconds++;
                        if (seconds >= 60) {
                            minutes++;
                            seconds = 0;
                        }
                        int hours = (int) minutes / 60;
                        int remainingMinutes = (int) minutes % 60;
                        String time = hours + "h " + remainingMinutes + "m " + seconds + "s";
                        getActivity().runOnUiThread(() -> minutesCount.setText(time));
                        handler.postDelayed(this, 1000);
                    }
                }
            };
            handler.post(runnable);
        } else {
            handler.post(runnable);
        }
    }

    private void pauseCounter() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void SaveCounter() {
        Context context = getContext(); // Get context from the Fragment
      if(handler != null) {
        handler.removeCallbacks(runnable);
        handler = null;
      }
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("StepData", MODE_PRIVATE);
            String existingData = sharedPreferences.getString("data", "[]");
            JSONArray dataArray;
            try {
                dataArray = new JSONArray(existingData);
            } catch (JSONException e) {
                dataArray = new JSONArray();
            }

            JSONObject newData = new JSONObject();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate today = LocalDate.now();
                    LocalTime time = LocalTime.now();
                    String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
                    newData.put("Day", dayOfWeek);
                    newData.put("Date", today.toString());
                    newData.put("time", time.toString()); // Convert LocalTime to String
                    newData.put("Steps", steps);
                    newData.put("Miles", Double.parseDouble(milesCount.getText().toString()));
                    newData.put("Calories", Double.parseDouble(caloriesCount.getText().toString()));
                    newData.put("minutes", minutesCount.getText().toString());
                    dataArray.put(newData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("data", dataArray.toString());
            editor.apply();

            Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
            minutesCount.setText("0h 0m");
            stepCount.setText("0");
            caloriesCount.setText("0.00");
        } else {
            // Handle the case where context is null if necessary
            Toast.makeText(getContext(), "Context is null", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACTIVITY_RECOGNITION_REQUEST_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(getContext(), "Permission for Activity Recognition is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void animateTextView(TextView textView, double startValue, double endValue) {
        ValueAnimator animator = ValueAnimator.ofFloat((float) startValue, (float) endValue);
        animator.setDuration(500); // duration of the animation in milliseconds
        animator.addUpdateListener(animation -> textView.setText(String.format("%.2f", (float) animation.getAnimatedValue())));
        animator.start();
    }

    private void animateTextView(TextView textView, int startValue, int endValue) {
        ValueAnimator animator = ValueAnimator.ofInt(startValue, endValue);
        animator.setDuration(500); // duration of the animation in milliseconds
        animator.addUpdateListener(animation -> textView.setText(String.valueOf(animation.getAnimatedValue())));
        animator.start();
    }
}
