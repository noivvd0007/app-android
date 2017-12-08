package com.vuvannoi.ndapptracnghiem.Main_App.view.Activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vuvannoi.ndapptracnghiem.Main_App.model.Video;
import com.vuvannoi.ndapptracnghiem.R;


public class PlayVideoYoutube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String YOUTUBE_APP_KEY = "AIzaSyCB59pO7FTdpna3aHMICxqKLtIKjvNH1q8";
    private String VIDEO_ID;
    private String TITLE_VIDEO;
    YouTubePlayerView youTubeView;
    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_youtube);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        txtName = (TextView) findViewById(R.id.txtName);
        youTubeView.initialize(YOUTUBE_APP_KEY, PlayVideoYoutube.this);
        Bundle bundle = getIntent().getExtras();
        Video video = (Video) bundle.getSerializable("Video");
        TITLE_VIDEO = video.getTitle();
        txtName.setText(TITLE_VIDEO);
        VIDEO_ID = video.getUrlVideo();
        Toast.makeText(this, VIDEO_ID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.setShowFullscreenButton(true);
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String error = "Không thể load video! Kiểm tra Internet và ứng dụng Youtube trên máy của bạn!";
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
