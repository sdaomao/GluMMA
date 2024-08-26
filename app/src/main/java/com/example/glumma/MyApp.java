package com.example.glumma;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import information.TimeData;

public class MyApp extends Application {
    private Handler handler;
    private Runnable checkAlarmsRunnable;
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createNotificationChannel(this);


    }

}