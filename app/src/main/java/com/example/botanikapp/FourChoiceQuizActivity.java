package com.example.botanikapp;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.InputStream;

public class FourChoiceQuizActivity extends Activity {

    public final String TAG = "QuizApp";

    ImageView mainIV;

    public static ArrayList imagesShuffled;

    int correctAnswer;

    int currentQuestion;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    ImageView imageView;

    TextView timerTV;
    TextView scoreTV;

    ProgressBar progressBar;

    //default button color
    Drawable d;
    Color bg_col;
    ColorDrawable buttonColor;
    int buttonColorId;

    int playerScore = 0;

    CountDownTimer timer;
    int timerCount = 0;
    int timerTime = 0;
    int timerFull = 0;

    int timerDelta = 1000;

    int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_normal);

        //Bundle extras = getIntent().getExtras();

        //Toast.makeText(this, "Toasty", Toast.LENGTH_SHORT).show();
        //mainIV = (ImageView) findViewById(R.id.main_image_view);
        btn1 = (Button) findViewById(R.id.choice_button_1);
        btn2 = (Button) findViewById(R.id.choice_button_2);
        btn3 = (Button) findViewById(R.id.choice_button_3);
        btn4 = (Button) findViewById(R.id.choice_button_4);

        progressBar = (ProgressBar) findViewById(R.id.time_progress);


        timerTV = (TextView) findViewById(R.id.timer_text_view);
        //scoreTV = (TextView) findViewById(R.id.score_text_view);

        //Get the default background color
        d = btn1.getBackground();
        //buttonColor = (ColorDrawable) btn1.getBackground();
        //buttonColorId = buttonColor.getColor();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer(0);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer(1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer(2);
            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer(3);
            }
        });


        //Randomize the order of the images in the array
        long seed = System.nanoTime();


        timerTime = (3/1) * 10000;
        timerFull = timerTime;
        progressBar.setMax(timerFull);

        startQuiz();

    }





    @Override
    protected void onPause() {
        timer.cancel();
        super.onPause();

    }


    @Override
    protected void onResume() {
        super.onResume();
        timer.cancel();
        startTimer(timerTime - (timerCount*timerDelta));
    }

    public void startQuiz(){

        //Get All the file names in an array
        currentQuestion = 0;
        createQuestion();
        startTimer(timerTime * 100);

    }


    public void  startTimer(int time){
        timer = new CountDownTimer(time, timerDelta) {

            public void onTick(long millisUntilFinished) {
                timerTV.setText(getString(R.string.timer_text) +" "+ millisUntilFinished / timerDelta);
                progressBar.setProgress(timerTime - (timerCount*timerDelta));
                timerCount++;
            }

            public void onFinish() {
                timerTV.setText("done!");
                progressBar.setProgress(0);
//                resultsPage();
            }
        }.start();
    }

    /**
     * Override the back button
     */
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }


    private void createQuestion(){


        int randomFileIndex;
        /*
        btn1.setBackgroundColor(buttonColorId);
        btn2.setBackgroundColor(buttonColorId);
        btn3.setBackgroundColor(buttonColorId);
        btn4.setBackgroundColor(buttonColorId);

         */

        //int drawableResourceId = this.getResources().getIdentifier("eiche.jpg", "drawable", this.getPackageName());
        imageView = (ImageView) findViewById(R.id.main_image_view);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.eiche));
        btn1.setBackground(d);
        btn2.setBackground(d);
        btn3.setBackground(d);
        btn4.setBackground(d);


        //picks a random number for the answer
        correctAnswer = 0 + (int)(Math.random() * ((3 - 0) + 1));

        //correctAnswer = ++correctAnswer % 4;

        //create an array of answers from file names
        /*
        ArrayList answers = new ArrayList();
        List categoryList = new ArrayList();

        for (int subArrayFlag = 0; subArrayFlag < imagesShuffled.size();subArrayFlag++){
            if(imagesShuffled.get(currentQuestion).getName().substring(0,1)
                    .equalsIgnoreCase(imagesShuffled.get(subArrayFlag).getName().substring(0, 1))){
                categoryList.add(subArrayFlag);

            }
        }

        //get 3 random answers and add it to the array
        for (int i = 0 ; i < 4 ;i++ ){ if (i == correctAnswer){ answers.add(imagesShuffled.get(currentQuestion).getName()); }else { do { randomFileIndex = (int) (Math.random() * categoryList.size()); } while ( categoryList.get(randomFileIndex) == currentQuestion && categoryList.size() > 0 );

            answers.add(imagesShuffled.get(categoryList.get(randomFileIndex)).getName());
        }

        }


        try
        {
            // get input stream
            InputStream ims = getAssets().open(imagesShuffled.get(currentQuestion).getPath());
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mainIV.setImageDrawable(d);
        }
        catch(IOException ex)
        {
            return;
        }

         */

        /*
        btn1.setText(answers.get(0).substring(2));
        btn2.setText(answers.get(1).substring(2));
        btn3.setText(answers.get(2).substring(2));
        btn4.setText(answers.get(3).substring(2));
         */
        //Toast.makeText(this, "" + correctAnswer, Toast.LENGTH_SHORT).show();
        switch(correctAnswer){
            case 0:
                //Toast.makeText(this, "Bing", Toast.LENGTH_SHORT).show();
                btn1.setText("Eiche");
                btn2.setText("Buche");
                btn3.setText("Erle");
                btn4.setText("Birke");
                break;
            case 1:
                //Toast.makeText(this, "Bung", Toast.LENGTH_SHORT).show();
                btn2.setText("Eiche");
                btn1.setText("Buche");
                btn4.setText("Erle");
                btn3.setText("Birke");
                break;
            case 2:
                //Toast.makeText(this, "Bang", Toast.LENGTH_SHORT).show();
                btn3.setText("Eiche");
                btn4.setText("Buche");
                btn1.setText("Erle");
                btn2.setText("Birke");
                break;
            case 3:
                //Toast.makeText(this, "Beng", Toast.LENGTH_SHORT).show();
                btn4.setText("Eiche");
                btn3.setText("Buche");
                btn2.setText("Erle");
                btn1.setText("Birke");
                break;
            default:
                btn1.setText("Something");
                btn2.setText("Went");
                btn3.setText("Wrong");
                btn4.setText("Whoops");
        }
    }

    private void submitAnswer(int answer){


        if(answer == correctAnswer){
            currentQuestion++;
            playerScore++;


            if (currentQuestion == 10){
                //Show the dialog
                resultsPage();

            }else{

                switch (answer) {
                    case 0:
                        btn1.setBackgroundColor(Color.GREEN);
                        break;
                    case 1:
                        btn2.setBackgroundColor(Color.GREEN);
                        break;
                    case 2:
                        btn3.setBackgroundColor(Color.GREEN);
                        break;
                    case 3:
                        btn4.setBackgroundColor(Color.GREEN);
                        break;
                }

                final int finalAnswer = answer;

                //Toast.makeText(this, "Richtig " + playerScore, Toast.LENGTH_SHORT).show();
                // SLEEP 2 SECONDS HERE ...
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        createQuestion();
                    }
                }, 500);

            }
        }else{
            playerScore--;
            switch (answer) {
                case 0:
                    btn1.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    btn2.setBackgroundColor(Color.RED);
                    break;
                case 2:
                    btn3.setBackgroundColor(Color.RED);
                    break;
                case 3:
                    btn4.setBackgroundColor(Color.RED);
                    break;
                default:
                    break;
            }
            //Toast.makeText(this, "Falsch " + playerScore, Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    createQuestion();
                }
            }, 500);

        }

        //scoreTV.setText(getString(R.string.score_text)+playerScore);


    }


    private void resultsPage(){

        String msg = "";

        currentQuestion = 0;

        if(timerTime > timerCount){
            msg = "Congrats!";
        }

        timer.cancel();

        Intent resultIntent = new Intent(this , ResultsActivity.class);
        resultIntent.putExtra("EXTRA_PLAYER_SCORE", playerScore);
        resultIntent.putExtra("EXTRA_TIMER_COUNT", timerCount);
        resultIntent.putExtra("EXTRA_TIMER_TIME", timerTime);

        this.startActivity(resultIntent);

        finish();
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
