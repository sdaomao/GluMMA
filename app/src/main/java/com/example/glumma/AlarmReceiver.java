package com.example.glumma;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean vibrate = intent.getBooleanExtra("vibrate", false);
        boolean notification = intent.getBooleanExtra("notification", false);
        String label = intent.getStringExtra("label");

        if (vibrate) {
            Intent dialogIntent = new Intent(context, AlarmDialogActivity.class);
            dialogIntent.putExtra("vibrate", vibrate);
            dialogIntent.putExtra("label", label);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(dialogIntent);
        }

        if (notification) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                    .setSmallIcon(R.drawable.baseline_lightbulb_outline_24)
                    .setContentTitle("Reminders")
                    .setContentText(label)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(1, builder.build());
            }
        }
    }
}
