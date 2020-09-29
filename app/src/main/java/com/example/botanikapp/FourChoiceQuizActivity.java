package com.example.botanikapp;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.Random;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

public class FourChoiceQuizActivity extends Activity {

    public final String TAG = "QuizApp";

    ImageView mainIV;

    public static ArrayList imagesShuffled;

    int correctAnswer;

    int currentQuestion;

    boolean[] arrAnswers = new boolean[8];
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    //Extra Buttons for other quizzes 
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button confirm;
    ImageView imageView;

    MultiAnswerQuestion multiAnswerQuestion;
    TriviaQuestion triviaQuestion;

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
        //initFourChoice();
        initMATrivia();
        timerTime = (3/1) * 7000;
        timerFull = timerTime;
        progressBar.setMax(timerFull);

        startQuiz2();

    }


    private void initFourChoice(){
        setContentView(R.layout.quiz_normal);

        btn1 = (Button) findViewById(R.id.choice_button_1);
        btn2 = (Button) findViewById(R.id.choice_button_2);
        btn3 = (Button) findViewById(R.id.choice_button_3);
        btn4 = (Button) findViewById(R.id.choice_button_4);

        progressBar = (ProgressBar) findViewById(R.id.time_progress);

        commonSetup();


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

    }

    private void initMATrivia(){
        setContentView(R.layout.quiz_multi2);
        btn1 = (Button) findViewById(R.id.button11);
        btn2 = (Button) findViewById(R.id.button12);
        btn3 = (Button) findViewById(R.id.button21);
        btn4 = (Button) findViewById(R.id.button22);
        btn5 = (Button) findViewById(R.id.button31);
        btn6 = (Button) findViewById(R.id.button32);
        btn7 = (Button) findViewById(R.id.button41);
        btn8 = (Button) findViewById(R.id.button42);
        confirm = (Button) findViewById(R.id.button9);

        progressBar = (ProgressBar) findViewById(R.id.time_progress);
        
        commonSetup();

        clearButtonsMulti();
        
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(0);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(2);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(3);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(4);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(5);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(6);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(7);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitArray();
            }
        });
        
        
    }

    private void clearButtonsMulti(){
        btn1.setBackgroundColor(Color.TRANSPARENT);
        btn2.setBackgroundColor(Color.TRANSPARENT);
        btn3.setBackgroundColor(Color.TRANSPARENT);
        btn4.setBackgroundColor(Color.TRANSPARENT);
        btn5.setBackgroundColor(Color.TRANSPARENT);
        btn6.setBackgroundColor(Color.TRANSPARENT);
        btn7.setBackgroundColor(Color.TRANSPARENT);
        btn8.setBackgroundColor(Color.TRANSPARENT);
        btn1.setBackground(d);
        btn2.setBackground(d);
        btn3.setBackground(d);
        btn4.setBackground(d);
        btn5.setBackground(d);
        btn6.setBackground(d);
        btn7.setBackground(d);
        btn8.setBackground(d);
    }
    private void submitArray(){
        boolean checkResult = multiAnswerQuestion.check(arrAnswers);
        if(checkResult){
            Toast.makeText(this, "Richtig " + playerScore, Toast.LENGTH_SHORT).show();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                public void run() {
                    createMultiTriviaQuestion();
                }
            }, 500);

    }
    private void flip(int i){
        arrAnswers[i] = !arrAnswers[i];
        updateButtonColors();
    }
    private void updateButtonColors(){
        if(arrAnswers[0]){
            btn1.setBackgroundColor(Color.BLUE);
        }
        else{
            btn1.setBackground(d);
            Toast.makeText(this, "Toasty ", Toast.LENGTH_SHORT).show();
        }
        if(arrAnswers[1]){
            btn2.setBackgroundColor(Color.BLUE);
        }
        else{
            btn2.setBackgroundColor(Color.TRANSPARENT);
        }
        if(arrAnswers[2]){
            btn3.setBackgroundColor(Color.BLUE);
        }
        else{
            btn3.setBackgroundColor(Color.TRANSPARENT);
        }
        if(arrAnswers[3]){
            btn4.setBackgroundColor(Color.BLUE);
        }
        else{
            btn4.setBackgroundColor(Color.TRANSPARENT);
        }
        if(arrAnswers[4]){
            btn5.setBackgroundColor(Color.BLUE);
        }
        else{
            btn5.setBackgroundColor(Color.TRANSPARENT);
        }
        if(arrAnswers[5]){
            btn6.setBackgroundColor(Color.BLUE);
        }
        else{
            btn6.setBackgroundColor(Color.TRANSPARENT);
        }
        if(arrAnswers[6]){
            btn7.setBackgroundColor(Color.BLUE);
        }
        else{
            btn7.setBackgroundColor(Color.TRANSPARENT);
        }
        if(arrAnswers[7]){
            btn8.setBackgroundColor(Color.BLUE);
        }
        else{
            btn8.setBackgroundColor(Color.TRANSPARENT);
        }

    }
    private void commonSetup(){


        timerTV = (TextView) findViewById(R.id.timer_text_view);

        //Get the default background color
        d = btn1.getBackground();
        
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
        createFourChoiceQuestion();
        startTimer(timerTime * 100);

    }
    public void startQuiz2(){

        //Get All the file names in an array
        currentQuestion = 0;

        createMultiTriviaQuestion();
        startTimer(timerTime * 100);

    }


    public void  startTimer(int time){
        if(timer != null){
            timer.cancel();
        }
        timerCount = 0;
        timer = new CountDownTimer(time, timerDelta) {

            public void onTick(long millisUntilFinished) {
                //timerTV.setText(getString(R.string.timer_text) +" "+ millisUntilFinished / timerDelta);
                //progressBar.setProgress(timerTime - (timerCount*timerDelta));
                progressBar.setProgress((int) millisUntilFinished);
                timerCount++;
            }

            public void onFinish() {
                //timerTV.setText("done!");
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


    private void createFourChoiceQuestion(){

        imageView = (ImageView) findViewById(R.id.main_image_view);
        Random rnd = new Random();
        FourChoiceQuestion question = QuizMaster.getFourChoiceQuestion(getApplicationContext());
        correctAnswer = question.correct;
        String plant = question.answers.elementAt(correctAnswer).toLowerCase();
        int noImages = Utility.getNumImagesForPlant(plant, "drawable", getApplicationContext());
        final String str = plant + "_" + rnd.nextInt(noImages);
        imageView.setImageDrawable
                (
                        getResources().getDrawable(Utility.getResourceID(str, "drawable",
                                getApplicationContext()))
                );
        btn1.setBackground(d);
        btn2.setBackground(d);
        btn3.setBackground(d);
        btn4.setBackground(d);

        while(question.answers.size() < 4){
            question.answers.add("Error");
        }
        btn1.setText(question.answers.elementAt(0));
        btn2.setText(question.answers.elementAt(1));
        btn3.setText(question.answers.elementAt(2));
        btn4.setText(question.answers.elementAt(3));

        startTimer(timerTime);

    }

    private void createTriviaQuestion(){

    }

    private void createMultiTriviaQuestion(){
        clearButtonsMulti();
        for(int i = 0; i < arrAnswers.length ; i++){
            arrAnswers[i] = false;
        }

        Random rnd = new Random();
        multiAnswerQuestion = QuizMaster.getMultiAnswerQuestion(getApplicationContext());

        if(multiAnswerQuestion.data.size() > 7){
            btn1.setText(multiAnswerQuestion.data.elementAt(0).str);
            btn2.setText(multiAnswerQuestion.data.elementAt(1).str);
            btn3.setText(multiAnswerQuestion.data.elementAt(2).str);
            btn4.setText(multiAnswerQuestion.data.elementAt(3).str);
            btn5.setText(multiAnswerQuestion.data.elementAt(4).str);
            btn6.setText(multiAnswerQuestion.data.elementAt(5).str);
            btn7.setText(multiAnswerQuestion.data.elementAt(6).str);
            btn8.setText(multiAnswerQuestion.data.elementAt(7).str);
        }

    }

    private void createMultiImageQuestion(){

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
                        createFourChoiceQuestion();
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
                    createFourChoiceQuestion();
                }
            }, 500);

        }

        //scoreTV.setText(getString(R.string.score_text)+playerScore);


    }


    private void resultsPage(){

        String msg = "";

        /*
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
*/
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
