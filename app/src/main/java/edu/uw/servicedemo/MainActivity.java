package edu.uw.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private static final String TAG = "**MAIN**";

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);


    }

    //when "Start" button is pressed
    public void handleStart(View v){
        Log.i(TAG, "Start pressed");

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
        finish();

    }

    //when "Stop" button is pressed
    public void handleStop(View v){
        Log.i(TAG, "Stop pressed");

        Intent intent = new Intent(MainActivity.this,MusicService.class);
        stopService(intent);

    }

    MusicService mService;
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.LocalBinder binder = (MusicService.LocalBinder)service;

        mService = binder.getService();
        Log.i(TAG, ""+mService.sayHello());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}


