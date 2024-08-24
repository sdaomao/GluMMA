package com.example.glumma;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.TimerAdapter;
import information.TimeData;

public class Reminders extends Fragment implements TimerAdapter.OnDeleteClickListener, TimerAdapter.EnableListener {

    private RecyclerView recyclerView;
    private TimerAdapter timeAdapter;
    private List<TimeData> timeDataList;

    public Reminders() {
        // Required empty public constructor
    }

    public static Reminders newInstance(String param1, String param2) {
        Reminders fragment = new Reminders();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                replaceFragment(new MedicationFragment());
            }
        });

        Button button = view.findViewById(R.id.button11);
        button.setOnClickListener(v -> replaceFragment(new SetTimer()));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        timeDataList = new ArrayList<>();
        timeAdapter = new TimerAdapter(timeDataList, this, this);
        recyclerView.setAdapter(timeAdapter);

        loadData();

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void onEnableClick(int position) {
        TimeData selectedTimeData = timeDataList.get(position);

        // Toggle the enabled state
        boolean newEnabledState = !selectedTimeData.isEnabled();

        // Create a new TimeData object with the updated state
        TimeData updatedTimeData = new TimeData(
                selectedTimeData.getTime(),
                selectedTimeData.getLabel(),
                selectedTimeData.isVibrate(),
                newEnabledState, // Toggle enabled state
                selectedTimeData.isNotification(),
                selectedTimeData.isRepeat(),
                selectedTimeData.getRepeatDays()
        );

        // Update the item in the list
        timeDataList.set(position, updatedTimeData);

        // Save changes to SharedPreferences
        saveData();

        // Set or cancel the alarm based on the updated state
        if (newEnabledState) {
            setAlarm(updatedTimeData);
        } else {
            cancelAlarm(updatedTimeData);
        }

        // Notify the adapter of data changes
        timeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteClick(int position) {
        // Remove the item from the list
        timeDataList.remove(position);

        // Save changes to SharedPreferences
        saveData();

        // Notify the adapter of data changes
        timeAdapter.notifyDataSetChanged();

        Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Reminder", MODE_PRIVATE);
            String existingData = sharedPreferences.getString("data", "[]");
            Log.d("LoadData", "Existing data: " + existingData);

            // First, clear all existing alarms
            cancelAllAlarms();

            String currentTime = getCurrentTime();

            try {
                JSONArray dataArray = new JSONArray(existingData);
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonData = dataArray.getJSONObject(i);
                    String time = jsonData.getString("time");
                    String label = jsonData.getString("label");
                    boolean vibrate = jsonData.getBoolean("vibration");
                    boolean enabled = jsonData.getBoolean("alarm");
                    boolean notification = jsonData.getBoolean("notification");
                    boolean repeat = jsonData.getBoolean("everyday");

                    String repeatDays = "";
                    if (repeat) {
                        repeatDays = "Everyday";
                    } else {
                        if (jsonData.getBoolean("monday")) repeatDays += "Mon, ";
                        if (jsonData.getBoolean("tuesday")) repeatDays += "Tue, ";
                        if (jsonData.getBoolean("wednesday")) repeatDays += "Wed, ";
                        if (jsonData.getBoolean("thursday")) repeatDays += "Thu, ";
                        if (jsonData.getBoolean("friday")) repeatDays += "Fri, ";
                        if (jsonData.getBoolean("saturday")) repeatDays += "Sat, ";
                        if (jsonData.getBoolean("sunday")) repeatDays += "Sun, ";
                        if (!repeatDays.isEmpty()) {
                            repeatDays = repeatDays.substring(0, repeatDays.length() - 2); // Remove trailing comma
                        }
                    }

                    TimeData timeData = new TimeData(time, label, vibrate, enabled, notification, repeat, repeatDays);
                    timeDataList.add(timeData);

                    // Set alarms only for enabled TimeData that matches the current time
                    if (timeData.isEnabled() && timeData.getTime().equals(currentTime)) {
                        Log.d("LoadData", "Setting alarm for matching time: " + timeData.getTime());
                        setAlarm(timeData);
                    }
                }

                timeAdapter.notifyDataSetChanged();
                Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void cancelAllAlarms() {
        Context context = getContext();
        if (context == null) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        for (TimeData timeData : timeDataList) {
            Calendar alarmTime = parseTime(timeData.getTime());
            int requestCode = (int) (alarmTime.getTimeInMillis() / 1000);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
            );

            try {
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                    Log.d("CancelAllAlarms", "Canceled alarm for: " + timeData.getTime());
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

// This is for the save data
    private void saveData() {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Reminder", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            JSONArray dataArray = new JSONArray();
            for (TimeData timeData : timeDataList) {
                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("time", timeData.getTime());
                    jsonData.put("label", timeData.getLabel());
                    jsonData.put("vibration", timeData.isVibrate());
                    jsonData.put("alarm", timeData.isEnabled());
                    jsonData.put("notification", timeData.isNotification());
                    jsonData.put("everyday", timeData.isRepeat());

                    // Add repeat days if not repeating everyday
                    if (!timeData.isRepeat()) {
                        jsonData.put("monday", timeData.getRepeatDays().contains("Mon"));
                        jsonData.put("tuesday", timeData.getRepeatDays().contains("Tue"));
                        jsonData.put("wednesday", timeData.getRepeatDays().contains("Wed"));
                        jsonData.put("thursday", timeData.getRepeatDays().contains("Thu"));
                        jsonData.put("friday", timeData.getRepeatDays().contains("Fri"));
                        jsonData.put("saturday", timeData.getRepeatDays().contains("Sat"));
                        jsonData.put("sunday", timeData.getRepeatDays().contains("Sun"));
                    } else {
                        jsonData.put("monday", false);
                        jsonData.put("tuesday", false);
                        jsonData.put("wednesday", false);
                        jsonData.put("thursday", false);
                        jsonData.put("friday", false);
                        jsonData.put("saturday", false);
                        jsonData.put("sunday", false);
                    }

                    dataArray.put(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            editor.putString("data", dataArray.toString());
            editor.apply();
        }
    }

    // This is for the set alarm
    void setAlarm(TimeData timeData) {
        Context context = getContext();
        if (context == null) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("vibrate", timeData.isVibrate());
        intent.putExtra("notification", timeData.isNotification());
        intent.putExtra("label", timeData.getLabel());

        Calendar alarmTime = parseTime(timeData.getTime());

        // Log the alarm time
        Log.d("SetAlarm", "Setting alarm for: " + alarmTime.getTime().toString());

        int requestCode = (int) (alarmTime.getTimeInMillis() / 1000);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        try {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
                Log.d("SetAlarm", "Alarm set successfully.");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // This is for the cancel time
    private void cancelAlarm(TimeData timeData) {
        Context context = getContext();
        if (context == null) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("vibrate", timeData.isVibrate());
        intent.putExtra("notification", timeData.isNotification());
        intent.putExtra("label", timeData.getLabel());

        Calendar alarmTime = parseTime(timeData.getTime());

        // Use the same requestCode as when setting the alarm
        int requestCode = (int) (alarmTime.getTimeInMillis() / 1000);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        try {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.d("CancelAlarm", "Alarm canceled for: " + timeData.getTime());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // This is for the time
    private Calendar parseTime(String time) {
        Calendar calendar = Calendar.getInstance();
        String[] timeParts = time.split(" ");
        String[] timeNumbers = timeParts[0].split(":");
        int hour = Integer.parseInt(timeNumbers[0]);
        int minute = Integer.parseInt(timeNumbers[1]);

        if (timeParts.length > 1 && timeParts[1].equalsIgnoreCase("PM") && hour != 12) {
            hour += 12;
        } else if (timeParts.length > 1 && timeParts[1].equalsIgnoreCase("AM") && hour == 12) {
            hour = 0;
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Ensure the calendar time is set to the current date
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        return calendar;
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Format the time as "hh:mm AM/PM"
        String amPm = hour >= 12 ? "PM" : "AM";
        hour = (hour > 12) ? hour - 12 : hour;
        hour = (hour == 0) ? 12 : hour; // Midnight should be 12 AM

        return String.format("%02d:%02d %s", hour, minute, amPm);
    }
}
