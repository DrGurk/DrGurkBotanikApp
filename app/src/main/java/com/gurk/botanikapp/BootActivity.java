package com.gurk.botanikapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

/** BootActivity
 * sleeps for a bit to show sponsor screen
 * starts main activity
 *
 */

public class BootActivity extends Activity {


    Context context = this;
    TextView resultTV;

    ImageView iV1;
    ImageView iV2;
    ImageView iV3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot_layout);
        iV1 = (ImageView) findViewById(R.id.sponsor1);
        iV2 = (ImageView) findViewById(R.id.sponsor2);
        iV3 = (ImageView) findViewById(R.id.sponsor3);

        iV1.setImageDrawable
                (
                        getResources().getDrawable(Utility.getResourceID("sponsor1", "drawable",
                                getApplicationContext()))
                );
        iV2.setImageDrawable
                (
                        getResources().getDrawable(Utility.getResourceID("sponsor2", "drawable",
                                getApplicationContext()))
                );
        iV3.setImageDrawable
                (
                        getResources().getDrawable(Utility.getResourceID("sponsor3", "drawable",
                                getApplicationContext()))
                );

        QuizMaster.initialize(getApplicationContext());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                mainMenu();
            }
        }, 2000);


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
