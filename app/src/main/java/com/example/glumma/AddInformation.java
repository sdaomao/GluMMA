package com.example.glumma;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddInformation extends AppCompatActivity {

    private Uri imageuri;

    private int PICK_IMAGE_REQUEST = 1;

    private TextView timetext;

    private TextView filenametext;

    private Spinner periodspinner;

    private EditText glucosetext;

    private EditText systolictext;

    private EditText diastolictext;

    private EditText weighttext;

    private EditText foodtext;

    private EditText exercisetext;

    private EditText notestext;

    private ProgressDialog progressBar;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.infomations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the widgets reference from XML layout
        timetext = findViewById(R.id.timetext);
        filenametext = findViewById(R.id.text19);
        periodspinner = findViewById(R.id.spinner);
        glucosetext = findViewById(R.id.editTextText);
        systolictext = findViewById(R.id.sytolictext);
        diastolictext = findViewById(R.id.dialostictext);
        weighttext = findViewById(R.id.weightext);
        foodtext = findViewById(R.id.foodtext);
        exercisetext = findViewById(R.id.exercisetext);
        notestext = findViewById(R.id.labresulttext);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = time.format(formatter);

            LocalDate date = LocalDate.now();
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

            timetext.setText(dayOfWeek + "," + date.toString() + "," + formattedTime);
        }

        // This is for the adapter and with the list 
        ArrayList<String> shopList = new ArrayList<>();
        shopList.add("Before Lunch");
        shopList.add("After Lunch");
        shopList.add("During Lunch");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinnerxml, shopList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodspinner.setAdapter(adapter);


        // This is for the done button
        Button donebutton = findViewById(R.id.button4);
        donebutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Done button clicked", Toast.LENGTH_SHORT).show();
            String time = timetext.getText().toString();
            String filename = filenametext.getText().toString();
            String period = periodspinner.getSelectedItem().toString();
            String glucose = glucosetext.getText().toString();
            String systolic = systolictext.getText().toString();
            String diastolic = diastolictext.getText().toString();
            String weight = weighttext.getText().toString();
            String food = foodtext.getText().toString();
            String exercise = exercisetext.getText().toString();
            String notes = notestext.getText().toString();

            // Check if any of the fields are empty
            if (time.isEmpty() || filename.isEmpty() || period.isEmpty() || glucose.isEmpty() || systolic.isEmpty() || diastolic.isEmpty() || weight.isEmpty() || food.isEmpty() || exercise.isEmpty() || notes.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Do something
                saveInformationAndImage(imageuri, time, filename, period, glucose, systolic, diastolic, weight, food, exercise, notes);

            }

        });

        // This is for the import button
        Button importbutton = findViewById(R.id.button3);
        importbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Import button clicked", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(AddInformation.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddInformation.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                openFileChooser();
            }
        });

        // This is for the cancel button
        Button cancelbutton = findViewById(R.id.button5);
        cancelbutton.setOnClickListener(v -> {
            // Do something in response to button click
            Toast.makeText(getApplicationContext(), "Cancel button clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TrackMe.class);
            startActivity(intent);
            finish();

        });
    }


    @SuppressLint("IntentReset")
    private void openFileChooser() {
        // Create an intent to open the file chooser
        Intent intent = new Intent();

        // Set the type of file to be selected
        intent.setType("image/*");

        // Set the action to get content
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // Start the activity for result
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the request code is the PICK_IMAGE_REQUEST, the result is OK, and data is not null
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();

            String fileName = null;
            if (imageuri.getScheme().equals("content")) {
                try (Cursor cursor = getContentResolver().query(imageuri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        fileName = cursor.getString(columnIndex);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (imageuri.getScheme().equals("file")) {
                File file = new File(imageuri.getPath());
                fileName = file.getName();
            }

            if (fileName != null) {
                filenametext.setText(fileName);
                Toast.makeText(this, "Image Imported: " + fileName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to get file name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Get the file extension of the image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void saveInformationAndImage(Uri imageuri, String time, String filename, String period, String glucose, String systolic, String diastolic, String weight, String food, String exercise, String notes) {
        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Saving Information");
        progressBar.setMessage("Please wait...");
        progressBar.show();
        progressBar.setCanceledOnTouchOutside(false);

        SharedPreferences sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");
        JSONArray dataArray;
        try {
            dataArray = new JSONArray(existingData);
        } catch (JSONException e) {
            dataArray = new JSONArray();
        }

        JSONObject newData = new JSONObject();
        try {
            newData.put("time", time);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate today = LocalDate.now();
                String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
                System.out.println(dayOfWeek);
                newData.put("dateday",dayOfWeek+","+today.toString());
                newData.put("filename", filename);
                newData.put("period", period);
                newData.put("glucose", glucose);
                newData.put("systolic", systolic);
                newData.put("diastolic", diastolic);
                newData.put("weight", weight);
                newData.put("food", food);
                newData.put("exercise", exercise);
                newData.put("notes", notes);
                newData.put("times", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                dataArray.put(newData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", dataArray.toString());
        editor.apply();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information Saved");
        builder.setMessage("The saved information is: \n" +
                "Time: " + time + "\n" +
                "Filename: " + filename + "\n" +
                "Period: " + period + "\n" +
                "Glucose: " + glucose + "\n" +
                "Systolic: " + systolic + "\n" +
                "Diastolic: " + diastolic + "\n" +
                "Weight: " + weight + "\n" +
                "Food: " + food + "\n" +
                "Exercise: " + exercise + "\n" +
                "Notes: " + notes);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
        saveImageToExternalStorage(imageuri, filename);
    }

    private void saveImageToExternalStorage(Uri imageUri, String filename) {
        executorService.execute(() -> {
            if (imageUri != null) {
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File imageFile = new File(storageDir, filename + ".jpg");

                try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
                     OutputStream outputStream = new FileOutputStream(imageFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Image annd information saved successfully", Toast.LENGTH_SHORT).show();
                        clearfields();
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show());
                } finally {
                    runOnUiThread(() -> progressBar.dismiss());
                }
            } else {
                runOnUiThread(() -> {
                    progressBar.dismiss();
                    Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Clear all fields
    public void clearfields(){
        timetext.setText("");
        filenametext.setText("");
        glucosetext.setText("");
        systolictext.setText("");
        diastolictext.setText("");
        weighttext.setText("");
        foodtext.setText("");
        exercisetext.setText("");
        notestext.setText("");
        periodspinner.setSelection(0);
        imageuri = null;
    }

}