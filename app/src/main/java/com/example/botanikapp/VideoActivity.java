package com.example.botanikapp;

import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.KeyEvent;
import android.graphics.Color;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;
public class VideoActivity extends Activity {
/*
    Context context = this;
    TextView resultTV;
    MediaSessionCompat mediaSession;
    PlaybackStateCompat.Builder stateBuilder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a MediaSessionCompat
        mediaSession = new MediaSessionCompat(this, LOG_TAG);

        // Enable callbacks from MediaButtons and TransportControls
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setState(stateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller
        mediaSession.setCallback(new MySessionCallback());

        // Create a MediaControllerCompat
        MediaControllerCompat mediaController =
                new MediaControllerCompat(this, mediaSession);

        MediaControllerCompat.setMediaController(this, mediaController);
    }


    private void mainMenu() {
        Intent challengeIntent = new Intent(this, MainActivity.class);
        startActivity(challengeIntent);
    }

    /**
     * Override the back button
     */

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }


}
