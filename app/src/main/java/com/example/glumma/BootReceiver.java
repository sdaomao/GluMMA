package com.example.glumma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import information.TimeData;

public class BootReceiver extends BroadcastReceiver {
    private Handler handler;
    private Runnable checkAlarmsRunnable;
    private List<TimeData> timeDataList;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Reload and set alarms here
            Log.d("BootReceiver", "Boot completed received");
            reloadAlarms(context);

            // Start the periodic check
            handler = new Handler();
            checkAlarmsRunnable = new Runnable() {
                @Override
                public void run() {
                    checkAlarms(context);
                    // Re-run this runnable every 10 seconds
                    handler.postDelayed(this, 10000); // 10000 ms = 10 seconds
                }
            };
            handler.postDelayed(checkAlarmsRunnable, 10000); // Start after 10 seconds
        }
    }

    private void reloadAlarms(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Reminder", Context.MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");
        timeDataList = new ArrayList<>(); // Initialize the list to store TimeData objects

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
                timeDataList.add(timeData); // Add the TimeData object to the list
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkAlarms(Context context) {
        String currentTime = getCurrentTime();
        for (TimeData timeData : timeDataList) {
            if (timeData.isEnabled() && timeData.getTime().equals(currentTime)) {
                Log.d("CheckAlarms", "Triggering alarm for matching time: " + timeData.getTime());
                AlarmUtils.setAlarm(context, timeData);
                break; // Optionally, break if you only want to trigger one alarm per check
            }
        }
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
