package com.gurk.botanikapp;

import android.app.Activity;
import android.view.KeyEvent;

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
