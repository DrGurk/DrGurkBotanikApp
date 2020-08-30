package com.example.botanikapp;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends Activity {

    public static final String PREFS_NAME = "better_life" ;
    public static final String PREFS_HIGH_SCORE = "playerHighScore" ;

    TextView startTV;
    TextView highScoreTV;
    TextView homeTV;
    ImageView mainBackgroundIV;

    SharedPreferences storage;

    RelativeLayout mainLayout;

    public static ArrayList images;

    static int difficulty;

    public static int playerHighScore=0;


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();

        //mainLayout = (RelativeLayout) findViewById(R.id.home_main_activity_layout);
        //startTV = (TextView) findViewById(R.id.home_start_text_view);
        //highScoreTV = (TextView) findViewById(R.id.home_high_score_text_view);
        //homeTV = (TextView) findViewById(R.id.home_more_text_view);
        //mainBackgroundIV = (ImageView) findViewById(R.id.home_background_image);

        // Restore preferences
        storage = getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = storage.edit();

        //set difficulty
        difficulty = 1;
        // Set the date of the first launch
        Long firstLaunchDate = storage.getLong("firstLaunchDate", 0);
        if (firstLaunchDate == 0) {
            firstLaunchDate = System.currentTimeMillis();
            editor.putLong("firstLaunchDate", firstLaunchDate);
            editor.commit();
        }

        ImageItem img;

        images = new ArrayList();

        //list images and them to the images array
        try {
            String[] files = getAssets().list("aimages");
            for (String file : files) {
                img = new ImageItem();
                //i3.setName("Love");
                String[] parts = file.split("\\.");
                img.setName(parts[0].replaceAll("-", " "));
                img.setPath("aimages/"+file);
                images.add(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Handler to update UI after the backend thread

        playerHighScore = storage.getInt(PREFS_HIGH_SCORE, 0);

        highScoreTV.setText(String.valueOf(playerHighScore * 1000) + "XP");

        startTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewGame();

            }
        });

        homeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFunctions.openMore(context);
            }
        });


        //picks a random number for the home background
        int randomBackground = 0 + (int)(Math.random() * ((images.size() - 1) + 1));
/*
        try
        {
            // get input stream
            InputStream ims = getAssets().open(images.get(randomBackground).getPath());
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mainBackgroundIV.setImageDrawable(d);

        }
        catch(IOException ex)
        {
            return;
        */
    }



    private void initNewGame(){

        Intent challengeIntent = new Intent(this , FourChoiceQuizActivity.class);
        challengeIntent.putExtra("EXTRA_DIFFICULTY", difficulty);

        this.startActivity(challengeIntent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}