package com.example.glumma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AlarmDialogActivity extends Activity {
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

        boolean vibrate = getIntent().getBooleanExtra("vibrate", false);
        String label = getIntent().getStringExtra("label");

        if (vibrate) {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null) {
                long[] pattern = {0, 1000, 1000}; // Delay, Vibrate, Sleep
                vibrator.vibrate(pattern, 0); // Start the vibration
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
            if (vibrator != null) {
                vibrator.cancel();
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            finish(); // Close the activity
        });

        // Cancel vibration after 10 seconds if not stopped earlier
        handler.postDelayed(cancelVibrationRunnable, 10000);
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
