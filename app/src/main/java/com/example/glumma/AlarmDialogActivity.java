package com.example.glumma;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AlarmDialogActivity extends Activity {
    private boolean isAlarmStopped = false;
    private Vibrator vibrator;
    private AlertDialog dialog;
    private final Handler handler = new Handler();
    private final Runnable cancelVibrationRunnable = () -> {
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        finish(); // Close the activity
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create notification channel
        NotificationHelper.createNotificationChannel(this);

        boolean vibrate = getIntent().getBooleanExtra("vibrate", false);
        String label = getIntent().getStringExtra("label");

        // Start sending notifications every 5 seconds for 30 seconds
        startNotificationLoop(label);

        if (vibrate) {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null) {
                long[] pattern = {0, 1000, 1000}; // Delay, Vibrate, Sleep
                vibrator.vibrate(pattern, 0); // Start the vibration with an infinite loop
            }
        }

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoglayout, null);

        // Set the dialog message dynamically
        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
        dialogMessage.setText(label);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView); // Set the custom layout as the view
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

        // Handle the button click event
        dialogView.findViewById(R.id.dialog_button).setOnClickListener(v -> {
            stopVibrationAndClose();
        });

        // Cancel vibration after 30 seconds if not stopped earlier
        handler.postDelayed(cancelVibrationRunnable, 10000);
    }

    // This will start the notification loop
    private void startNotificationLoop(String label) {
        final int notificationInterval = 1000; // 5 seconds
        final int maxNotifications = 2; // 30 seconds / 5 seconds = 6 times

        for (int i = 1; i <= maxNotifications; i++) {
            handler.postDelayed(() -> NotificationHelper.sendNotification(this, "Reminder", label), i * notificationInterval);
        }
    }

    private void stopVibrationAndClose() {
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        // Mark the alarm as stopped
        isAlarmStopped = true;
        // Save to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("AlarmPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isAlarmStopped", isAlarmStopped);
        editor.apply();
        // Cancel any further notifications
        NotificationHelper.cancelNotification(this);

        // Stop any further alarm triggers
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0, // Use the same requestCode you used to set the alarm
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        handler.removeCallbacks(cancelVibrationRunnable);
        finish(); // Close the activity
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ensure dialog is dismissed and vibrator is stopped to avoid leaks
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        handler.removeCallbacks(cancelVibrationRunnable);
    }
}
