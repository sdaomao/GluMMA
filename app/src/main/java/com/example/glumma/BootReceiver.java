package com.example.glumma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import information.TimeData;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Reload and set alarms here
            Log.d("BootReceiver", "Boot completed received");
            reloadAlarms(context);
        }
    }

    private void reloadAlarms(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Reminder", Context.MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");
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
                if (timeData.isEnabled()) {
                    AlarmUtils.setAlarm(context, timeData);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
