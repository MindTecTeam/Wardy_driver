package com.invictos.ssid.wardy_driver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ssid on 15/07/17.
 */

public class Request_Service extends Service {
    @Nullable
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = Request_Service.class.getName();
    android.os.Handler customHandler = new android.os.Handler();
    int a = 1;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.schedule(task,0,5000);
    }
}