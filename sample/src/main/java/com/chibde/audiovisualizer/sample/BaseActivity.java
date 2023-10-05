/*
* Copyright (C) 2017 Gautam Chibde
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.chibde.audiovisualizer.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * BaseActivity that contains common code for all visualizers
 *
 * Created by gautam chibde on 18/11/17.
 * Modified by Kenneth Mallabo 11/04/23.
 */
abstract public class BaseActivity extends AppCompatActivity {
    // Request code for audio permission
    public static final int AUDIO_PERMISSION_REQUEST_CODE = 102;

    // Permissions required for recording audio
    public static final String[] WRITE_EXTERNAL_STORAGE_PERMS = {
            Manifest.permission.RECORD_AUDIO
    };

    // MediaPlayer for playing audio
    protected MediaPlayer mediaPlayer;

    // MediaRecorder for recording audio
    private MediaRecorder recorder;

    // Name of the file used for recording
    private String fileName;

    // Tag used for logging
    private static final String LOG_TAG = "AudioRecordTest";

    // Flag to indicate if there is a preexisting record
    private int recordFlag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";
        // Set the layout for the activity
        if (getLayout() != 0) {
            setContentView(getLayout());
        } else {
            throw new NullPointerException("Provide layout file for the activity");
        }
        // Set up the action bar
        setActionBar();
        // Initialize the activity
        initialize();
    }

    // Method to initialize the activity
    private void initialize() {
        // Request permission to record audio if it has not been granted
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(WRITE_EXTERNAL_STORAGE_PERMS, AUDIO_PERMISSION_REQUEST_CODE);
        } else {
            setPlayer();
        }
    }

    // Method to set up the action bar
    private void setActionBar() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // Method to set up the MediaPlayer for playing audio
    private void setPlayer() {
        if (recordFlag == 0){           // if there is no preexisting record
            mediaPlayer = MediaPlayer.create(this, R.raw.train_pass_by_03);
            mediaPlayer.setLooping(false);
            init();
        }
        else {                          // if there is a preexisting record
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(fileName);
                mediaPlayer.prepare();
                mediaPlayer.setLooping(false);
                mediaPlayer.start();

            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
            init();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop and release the MediaPlayer if it is playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    // Method to handle permission request results
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AUDIO_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setPlayer();
            } else {
                this.finish();
            }
        }
    }

    // Method to handle play/pause button clicks
    public void playPauseBtnClicked(ImageButton btnPlayPause) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_play_red_48dp));
            } else {
                mediaPlayer.start();
                btnPlayPause.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_pause_red_48dp));
            }
        }
    }

    // Method to handle start recording button clicks
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        recorder.start();
    }

    // Method to handle stop recording button clicks
    public void stopRecording(View view) {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            recordFlag = 1;     // flag to signify there is a record
            setPlayer();        // update the player with the recorder
        }

    }

    // This method is called when the record button is clicked
    public void onRecord(View view) {
        startRecording();   // Starts the recording process
    }

    // Returns the layout resource ID for the activity
    // Subclasses must override this method to provide a layout for the activity
    protected int getLayout() {
        return 0;
    }

    // Subclasses must override this method to perform initialization tasks for the activity
    protected abstract void init();
}


