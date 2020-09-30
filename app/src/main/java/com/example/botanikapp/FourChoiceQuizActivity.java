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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

public class FourChoiceQuizActivity extends Activity {

    public final String TAG = "QuizApp";

    ImageView mainIV;

    int mode = -1;//0 = 4c, 1 = trivia, 2 = multianswer, 3 = multiimage

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

    Button result;
    ImageView imageView;

    MultiAnswerQuestion multiAnswerQuestion;
    TriviaQuestion triviaQuestion;

    TextView questionTV;
    TextView timerTV;
    TextView resultTV;

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
    int timerMax = 0;

    int timerDelta = 1000;
    int failTries = 0;

    int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startQuiz();
    }

    private void setupProgressBar(){
        setupProgressBar(timerFull);
    }
    private void setupProgressBar(int maxTime){
        progressBar = (ProgressBar) findViewById(R.id.time_progress);
        progressBar.setMax(maxTime);
    }

    private void initFourChoice(){
        timerTime = 7; //todo magic number
        setContentView(R.layout.quiz_normal);

        btn1 = (Button) findViewById(R.id.choice_button_1);
        btn2 = (Button) findViewById(R.id.choice_button_2);
        btn3 = (Button) findViewById(R.id.choice_button_3);
        btn4 = (Button) findViewById(R.id.choice_button_4);

        setupProgressBar();
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
        timerTime = 7 * 2; //todo magic number
        setContentView(R.layout.quiz_multi2);
        questionTV = (TextView) findViewById(R.id.triviaQuestion2);
        btn1 = (Button) findViewById(R.id.button11);
        btn2 = (Button) findViewById(R.id.button12);
        btn3 = (Button) findViewById(R.id.button21);
        btn4 = (Button) findViewById(R.id.button22);
        btn5 = (Button) findViewById(R.id.button31);
        btn6 = (Button) findViewById(R.id.button32);
        btn7 = (Button) findViewById(R.id.button41);
        btn8 = (Button) findViewById(R.id.button42);
        confirm = (Button) findViewById(R.id.button9);

        setupProgressBar(timerFull * 2);


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

    private void setMode(int in){
        switch(in){
            case 0:
                if(mode != in){
                    initFourChoice();
                }
                break;
            case 1:
                if(mode != in){

                }
                break;
            case 2:
                if(mode != in){
                    initMATrivia();
                }
                break;
            default:
        }
        mode = in;
    }

    private void prepareQuestion(){
        switch(mode){
            case 0:
                createFourChoiceQuestion();
                break;
            case 1:
                createTriviaQuestion();
                break;
            case 2:
                createMultiTriviaQuestion();
                break;
            default:
        }
    }
    private void submitArray(){
        ResultChecker checkResult = multiAnswerQuestion.check(arrAnswers);
        if(checkResult.check()){
            QuizMaster.correctQuestions++;
            QuizMaster.score += timerMax - timerCount;
        }
        colorMultiButtonSubmit(checkResult.arr);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                public void run() {
                    nextQuestion();
                }
            }, 500);

    }
    
    private void colorMultiButtonSubmit(boolean[] in){
        if(in[0]){
            btn1.setBackgroundColor(Color.RED);
        }
        else{
            btn1.setBackgroundColor(Color.GREEN);
        }
        if(in[1]){
            btn2.setBackgroundColor(Color.RED);
        }
        else{
            btn2.setBackgroundColor(Color.GREEN);
        }
        if(in[2]){
            btn3.setBackgroundColor(Color.RED);
        }
        else{
            btn3.setBackgroundColor(Color.GREEN);
        }
        if(in[3]){
            btn4.setBackgroundColor(Color.RED);
        }
        else{
            btn4.setBackgroundColor(Color.GREEN);
        }
        if(in[4]){
            btn5.setBackgroundColor(Color.RED);
        }
        else{
            btn5.setBackgroundColor(Color.GREEN);
        }
        if(in[5]){
            btn6.setBackgroundColor(Color.RED);
        }
        else{
            btn6.setBackgroundColor(Color.GREEN);
        }
        if(in[6]){
            btn7.setBackgroundColor(Color.RED);
        }
        else{
            btn7.setBackgroundColor(Color.GREEN);
        }
        if(in[7]){
            btn8.setBackgroundColor(Color.RED);
        }
        else{
            btn8.setBackgroundColor(Color.GREEN);
        }
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
        }
        if(arrAnswers[1]){
            btn2.setBackgroundColor(Color.BLUE);
        }
        else{
            btn2.setBackground(d);
        }
        if(arrAnswers[2]){
            btn3.setBackgroundColor(Color.BLUE);
        }
        else{
            btn3.setBackground(d);
        }
        if(arrAnswers[3]){
            btn4.setBackgroundColor(Color.BLUE);
        }
        else{
            btn4.setBackground(d);
        }
        if(arrAnswers[4]){
            btn5.setBackgroundColor(Color.BLUE);
        }
        else{
            btn5.setBackground(d);
        }
        if(arrAnswers[5]){
            btn6.setBackgroundColor(Color.BLUE);
        }
        else{
            btn6.setBackground(d);
        }
        if(arrAnswers[6]){
            btn7.setBackgroundColor(Color.BLUE);
        }
        else{
            btn7.setBackground(d);
        }
        if(arrAnswers[7]){
            btn8.setBackgroundColor(Color.BLUE);
        }
        else{
            btn8.setBackground(d);
        }

    }
    private void commonSetup(){

        progressBar = (ProgressBar) findViewById(R.id.time_progress);

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
        timerTime = 7000;
        timerFull = timerTime;

        QuizMaster.newGame(5);
        currentQuestion = -1;
        nextQuestion();

    }

    public void startTimer(){
        startTimer(timerTime);
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
        int noImages = Utility.getNumImagesForPlant(plant, "drawable", getApplicationContext());//todo: change this
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
        questionTV.setText(multiAnswerQuestion.question);

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
            QuizMaster.correctQuestions++;
            QuizMaster.score += timerMax - timerCount;
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
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    nextQuestion();
                }
            }, 500);
        }else{
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
                    nextQuestion();
                }
            }, 500);

        }
    }


    private void resultsPage(){
        setContentView(R.layout.result_layout);
        resultTV = findViewById(R.id.resultText);
        resultTV.setText("Punkte: " + QuizMaster.score + "\n Richtige Fragen: " + QuizMaster.correctQuestions + "/" + QuizMaster.questionTypes.size());
        Button backBtn = findViewById(R.id.result_button);
        backBtn.setText("Hauptmenue");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenu();
            }
        });

    }

    private void nextQuestion(){
        if(++currentQuestion >= QuizMaster.questionTypes.size()){
            resultsPage();
            return;
        }
        int qType = QuizMaster.questionTypes.elementAt(currentQuestion);
        setMode(qType);
        prepareQuestion();
        startTimer();
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

    private void mainMenu(){
        Intent challengeIntent = new Intent(this , MainActivity.class);
        startActivity(challengeIntent);
    }
}
