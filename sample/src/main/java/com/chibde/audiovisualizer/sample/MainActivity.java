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

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.chibde.audiovisualizer.sample.visualizer.ActivityTutorialVideo;
import com.chibde.audiovisualizer.sample.visualizer.BarVisualizerActivity;
import com.chibde.audiovisualizer.sample.visualizer.CircleBarVisualizerActivity;
import com.chibde.audiovisualizer.sample.visualizer.CircleVisualizerActivity;
import com.chibde.audiovisualizer.sample.visualizer.LineBarVisualizerActivity;
import com.chibde.audiovisualizer.sample.visualizer.LineVisualizerActivity;
import com.chibde.audiovisualizer.sample.visualizer.QuizVisualizerActivity;
import com.chibde.audiovisualizer.sample.visualizer.SquareBarVisualizerActivity;
import com.chibde.visualizer.SquareBarVisualizer;

// This class is the main activity of the application
// Modified by Kenneth Mallabo 18/03/23
public class MainActivity extends AppCompatActivity {
    // This method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sets the layout for the activity
    }

    // These methods start the activities that show different types of visualizers
    public void line(View view) {
        startActivity(LineVisualizerActivity.class);
    }

    public void bar(View view) {
        startActivity(BarVisualizerActivity.class);
    }

    public void circle(View view) {
        startActivity(CircleVisualizerActivity.class);
    }

    public void circleBar(View view) {
        startActivity(CircleBarVisualizerActivity.class);
    }

    public void lineBar(View view) {
        startActivity(LineBarVisualizerActivity.class);
    }

    public void service(View view) {
        startActivity(ServiceExampleActivity.class);
    }

    public void square(View view) {
        startActivity(SquareBarVisualizerActivity.class);
    }

    // This method starts an activity using a class object
    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    // This method starts the quiz activity
    public void quizStart(View view) {
        startActivity(QuizVisualizerActivity.class);
    }

    // This method starts the tutorial video activity
    public void dopplerVideo(View view) {startActivity(ActivityTutorialVideo.class);
    }
}
