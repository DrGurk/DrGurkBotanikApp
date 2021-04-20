package com.example.botanikapp;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.KeyEvent;
import android.graphics.Color;
import android.content.Intent;

import android.view.View;

import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.InputStream;

public class ResultsActivity extends Activity {


    Context context = this;
    TextView resultTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        resultTV = findViewById(R.id.resultText);
        int numQuestions = QuizMaster.gameMode == 2 ? QuizMaster.numQuestionsSurvival : QuizMaster.questionTypes.size();
        resultTV.setText("Punkte: " + QuizMaster.score + "\n Richtige Fragen: " + QuizMaster.correctQuestions + "/" + numQuestions);
        Button backBtn = findViewById(R.id.result_button);
        backBtn.setText("Hauptmenü");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenu();
            }
        });



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
