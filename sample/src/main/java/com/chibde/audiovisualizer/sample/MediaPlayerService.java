package com.chibde.audiovisualizer.sample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * MediaPlayerService for handling the audio
 *
 * Modified by Kenneth Mallabo 03/04/23.
 */
public class MediaPlayerService extends Service {
    // Constant variables to use in Intent filters
    public static final String INTENT_FILTER = "MediaPlayerServiceIntentFilter";
    public static final String INTENT_AUDIO_SESSION_ID = "intent_audio_session_id";

    // Instance variables
    private IBinder mediaPlayerServiceBinder = new MediaPlayerServiceBinder();
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize media player with audio file from resources
        mediaPlayer = MediaPlayer.create(this, R.raw.train_pass_by_03);
        mediaPlayer.setLooping(false);


        Intent intent = new Intent(INTENT_FILTER); //put the same message as in the filter you used in the activity when registering the receiver
        intent.putExtra(INTENT_AUDIO_SESSION_ID, mediaPlayer.getAudioSessionId());
        // Send audio session id through
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Return the instance of the binder
        return mediaPlayerServiceBinder;
    }

    public void replay() {
        if (mediaPlayer != null) {
            // Set current position to the beginning of the audio file
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // Called when all clients have disconnected from the service
        return true;    // This service will continue running until stopped explicitly
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release resources of media player
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Binder class to return the service instance
    public class MediaPlayerServiceBinder extends Binder {
        MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }
}
