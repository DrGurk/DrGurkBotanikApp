package com.gurk.botanikapp;

import android.app.Activity;
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

import java.util.Random;
import java.util.Vector;

/** FourChoiceQuizActivity
 * misleading name, actually this handles all cases
 */
public class FourChoiceQuizActivity extends Activity {

    int mode = -1;//0 = 4c, 1 = trivia, 2 = multianswer, 3 = multiimage
    int gameMode = 0;//0 = normal, 1 = blitz, 2 = survival

    int correctAnswer;

    int currentQuestion;

    boolean[] arrAnswers = new boolean[8];
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    //Extra Buttons for other quizzes 
    //Button btn5;
    //Button btn6;
    Button confirm;

    Button result;
    ImageView imageView;

    MultiAnswerQuestion multiAnswerQuestion;
    TriviaQuestion triviaQuestion;

    TextView questionTV;

    TextView failedTV;

    ProgressBar progressBar;

    //default button color
    Drawable d;

    CountDownTimer timer;
    int timerCount = 0;
    int timerTime = 0;
    int timerFull = 0;
    int timerMax = 0;

    int timerDelta = 1000;
    int failTries = 0;

    int lives = 3;
    boolean failed = false;


    String lastPlant;
    int lastImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startQuiz();
        gameMode = QuizMaster.gameMode;
    }

    private void setupProgressBar(){
        setupProgressBar(timerFull);
    }
    private void setupProgressBar(int maxTime){
        progressBar = (ProgressBar) findViewById(R.id.time_progress);
        progressBar.setMax(maxTime);
        progressBar.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void initFourChoice(){
        timerTime = getTimer();
        timerMax = timerTime * timerDelta;
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
        timerTime = getTimer();
        timerMax = timerTime * timerDelta;
        setContentView(R.layout.quiz_multi2);
        questionTV = (TextView) findViewById(R.id.triviaQuestion2);
        btn1 = (Button) findViewById(R.id.button11);
        btn2 = (Button) findViewById(R.id.button12);

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




        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitArray();
            }
        });
        
        
    }

    private void initTrivia(){
        timerTime = getTimer(); //todo magic number
        timerMax = timerTime * timerDelta;
        setContentView(R.layout.quiz_trivia);

        btn1 = (Button) findViewById(R.id.choice_button_1);
        btn2 = (Button) findViewById(R.id.choice_button_2);
        btn3 = (Button) findViewById(R.id.choice_button_3);
        btn4 = (Button) findViewById(R.id.choice_button_4);

        questionTV = (TextView) findViewById(R.id.triviaText);
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

    private void clearButtonsMulti(){
        btn1.setBackgroundColor(Color.TRANSPARENT);
        btn2.setBackgroundColor(Color.TRANSPARENT);
        btn1.setBackground(d);
        btn2.setBackground(d);
    }

    /** setMode
     * calls init functions based on mode
     * @param in mode
     */
    private void setMode(int in){
        switch(in){
            case 0:
                if(mode != in){
                    initFourChoice();
                }
                break;
            case 1:
                if(mode != in){
                    initTrivia();
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

    /** submitArray
     * submits array stored in arrAnswers to a multiAnswerQuestion
     */
    private void submitArray(){
        ResultChecker checkResult = multiAnswerQuestion.check(arrAnswers);
        if(checkResult.check()){
            QuizMaster.correctQuestions++;
            QuizMaster.score += Math.max(Math.min(timerMax/timerDelta - timerCount, 5),0);
        }else{
            failed = true;
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
    }
    private void flip(int i){
        arrAnswers[i] = !arrAnswers[i];
        updateButtonColors();
    }

    /** updateButtonColors
     * for multianswer buttons
     */
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

    }

    /** commonSetup
     * setup performed for all quiz types
     */
    private void commonSetup(){

        progressBar = (ProgressBar) findViewById(R.id.time_progress);


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
        startTimer(timerTime* ((mode==2) ? 2: 1)*timerDelta - (timerCount*timerDelta));
    }

    public void startQuiz(){
        timerTime = getTimer();
        timerFull = timerTime*timerDelta;

        QuizMaster.newGame(getApplicationContext());
        currentQuestion = -1;
        nextQuestion();

    }

    public void startTimer(){
        startTimer(timerTime*timerDelta);
    }

    public void  startTimer(int time){
        if(timer != null){
            timer.cancel();
        }
        timerCount = 0;
        timer = new CountDownTimer(time, timerDelta) {

            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) millisUntilFinished);
                if((int) millisUntilFinished < progressBar.getMax()/2){
                    progressBar.getProgressDrawable().setColorFilter(
                            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                }
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

    /** createFourChoiceQuestion
     * creates FourChoiceQuestion from quizmaster and maps it to the UI
     */
    private void createFourChoiceQuestion(){

        imageView = (ImageView) findViewById(R.id.main_image_view);
        Random rnd = new Random();
        FourChoiceQuestion question = QuizMaster.getFourChoiceQuestion(getApplicationContext());
        correctAnswer = question.correct;
        String plant = question.answers.elementAt(correctAnswer).toLowerCase(); //todo check
        int noImages = question.numImages;
        lastImg = rnd.nextInt(noImages);
        final String str = plant + "_" + lastImg;
        System.out.println(str);
        imageView.setImageDrawable
                (
                        getResources().getDrawable(Utility.getResourceID(Utility.androidString(str), "drawable",
                                getApplicationContext()))
                );
        btn1.setBackground(d);
        btn2.setBackground(d);
        btn3.setBackground(d);
        btn4.setBackground(d);


        lastPlant = Utility.androidString(question.answers.elementAt(correctAnswer));


        if(question.answers.size() < 4){
            createFourChoiceQuestion();
            return;
        }

        /*while(question.answers.size() < 4){
            question.answers.add("Error");
        }*/
        btn1.setText(Utility.splitPar(question.answers.elementAt(0)));
        btn2.setText(Utility.splitPar(question.answers.elementAt(1)));
        btn3.setText(Utility.splitPar(question.answers.elementAt(2)));
        btn4.setText(Utility.splitPar(question.answers.elementAt(3)));



    }

    private void createTriviaQuestion(){
        if(lastPlant != null){
            triviaQuestion = QuizMaster.getRandomTriviaQuestion();
            correctAnswer = triviaQuestion.correct;
            btn1.setText(triviaQuestion.answers.elementAt(0));
            btn2.setText(triviaQuestion.answers.elementAt(1));
            btn3.setText(triviaQuestion.answers.elementAt(2));
            btn4.setText(triviaQuestion.answers.elementAt(3));
            questionTV.setText(triviaQuestion.question);

        }else{
            nextQuestion();
        }

    }

    private void createMultiTriviaQuestion(){
        clearButtonsMulti();
        for(int i = 0; i < arrAnswers.length ; i++){
            arrAnswers[i] = false;
        }

        multiAnswerQuestion = QuizMaster.getMultiAnswerQuestion(getApplicationContext());
        questionTV.setText(multiAnswerQuestion.question);

        if(multiAnswerQuestion.data.size() > 1){
            btn1.setText(Utility.splitPar(multiAnswerQuestion.data.elementAt(0).str));
            btn2.setText(Utility.splitPar(multiAnswerQuestion.data.elementAt(1).str));
        }

    }

    private void createMultiImageQuestion(){

    }

    /** submitAnswer
     *  submits answer and colors the buttons accordingly for .5 sec
     *  you get to try again, but you will get no points for this one
     * @param answer
     */
    private void submitAnswer(int answer){
        if(answer == correctAnswer){
            if(failTries == 0) {
                QuizMaster.correctQuestions++;
                QuizMaster.score += timerMax / timerDelta - timerCount;
            }
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
            failed = true;
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
            if(failTries++ > 0) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        nextQuestion();
                    }
                }, 500);
            }

        }
    }


    private void resultsPage(){
        Intent challengeIntent = new Intent(this, ResultsActivity.class);
        startActivity(challengeIntent);
        finish();

    }


    /** nextQuestion
     * prepares stuff for the next question
     * - increases counter for survival
     * - resets the failed bool
     * - if it was failed previously, shows you the description of that plant
     * - if it has reached numQuestions it ends, otherwise a new question will be generated
     */
    private void nextQuestion(){
        if(gameMode == 2){
            QuizMaster.numQuestionsSurvival++;
            if(failed){
                if(--lives == 0 ){
                    resultsPage();
                    return;
                }else{
                Toast.makeText(getApplicationContext(),"Noch " + lives + " Versuch(e)!",Toast.LENGTH_SHORT).show();}
            }
        }
        failed = false;
        if(failTries > 0 && mode != 2){
            setContentView(R.layout.failed_layout);
            btn1 = (Button) findViewById(R.id.failButton);
            failedTV = (TextView) findViewById(R.id.failText);
            Vector<String> desc = Utility.readFile("description_" + lastPlant, "raw", getApplicationContext());
            failedTV.setText(desc.elementAt(0));

            imageView = (ImageView) findViewById(R.id.main_image_view);

            final String str = lastPlant + "_" + lastImg;
            imageView.setImageDrawable
                    (
                            getResources().getDrawable(Utility.getResourceID(Utility.androidString(str), "drawable",
                                    getApplicationContext()))
                    );

            mode = -1;
            failTries = 0;
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextQuestion();
                }
            });

            return;
        }
        if(++currentQuestion >= QuizMaster.questionTypes.size()){
            if(gameMode == 2){
                currentQuestion = 0;
            }else {
                resultsPage();
                return;
            }
        }

        else {
            int qType = QuizMaster.questionTypes.elementAt(currentQuestion);
            setMode(qType);
            prepareQuestion();
            int time = getTimer() * timerDelta * ((mode == 2) ? 2 : 1);
            startTimer(time);
        }
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
        finish();
    }

    /** getTimer
     * returns timer for quiz type
     * you can customize the amount if you intend to implement multi image questions, as you will
     * need more time to think
     * @return
     */
    private int getTimer(){
        switch(gameMode){
            case 0: return 20;
            case 1: return 10;
            case 2: return 20;
            default: return 0;
        }
    }


}
