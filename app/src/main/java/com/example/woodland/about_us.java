package com.example.woodland;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class about_us extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        videoView=findViewById(R.id.vvVideoView);

        String vedioPath="android.resource://"+getPackageName()+"/raw/video";

        videoView.setVideoPath(vedioPath);
        videoView.start();

        MediaController mediaController = new MediaController(about_us.this);

        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.requestFocus();


    }
}