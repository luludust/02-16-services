package edu.uw.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by iguest on 2/16/16.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    private static final String TAG = "MUSIC";

    private MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Service created");
    }

    private int NOTIFICATION_ID = 1;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String songName = "Verdi";
        mediaPlayer = MediaPlayer.create(this, R.raw.verdi_la_traviata_brindisi_mit);
        mediaPlayer.setOnCompletionListener(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                                    .addAction(android.R.drawable.ic_media_play, "Music Player", pendingIntent)
                                    .setContentText("Now playing: "+songName)
                                    .setOngoing(true)
                                    .build();
        startForeground(NOTIFICATION_ID, notification); //make this a foreground service!

        mediaPlayer.start();

        super.onStartCommand(intent, flags, startId);
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);

        Log.i(TAG, "Stopping music");

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public String sayHello() {
        return "Hello World";
    }

    private final IBinder mLocalBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    class LocalBinder extends Binder {
        //return the current service
        public MusicService getService() {
            return MusicService.this;
        }
    }

}
