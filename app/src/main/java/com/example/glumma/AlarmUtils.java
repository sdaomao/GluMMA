package com.example.glumma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.Calendar;

import information.TimeData;

public class AlarmUtils {
    public static void setAlarm(Context context, TimeData timeData) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!canScheduleExactAlarms(context)) {
                Log.w("AlarmUtils", "Exact alarm permission is not granted.");
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                context.startActivity(intent);
                return;
            }
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("vibrate", timeData.isVibrate());
        intent.putExtra("notification", timeData.isNotification());
        intent.putExtra("label", timeData.getLabel());

        Calendar alarmTime = parseTime(timeData.getTime());
        int requestCode = (int) (alarmTime.getTimeInMillis() / 1000);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        if (alarmManager != null) {
            Log.d("AlarmUtils", "Setting alarm at: " + alarmTime.getTime().toString());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
        } else {
            Log.e("AlarmUtils", "AlarmManager is null");
        }
    }

    private static boolean canScheduleExactAlarms(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            return alarmManager.canScheduleExactAlarms();
        }
        return true; // For versions below Android 12
    }

    private static Calendar parseTime(String time) {
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

        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        return calendar;
    }
}
