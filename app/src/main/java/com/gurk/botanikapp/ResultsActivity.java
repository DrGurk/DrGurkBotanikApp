package com.gurk.botanikapp;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.KeyEvent;
import android.content.Intent;

import android.view.View;

/** shows the result screen
 *  currently the score is combined with the QuizMaster score field (time bonus) and the amount
 *  of correct answered questions
 */
public class ResultsActivity extends Activity {


    Context context = this;
    TextView resultTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        resultTV = findViewById(R.id.resultText);
        int numQuestions = QuizMaster.gameMode == 2 ? QuizMaster.numQuestionsSurvival : QuizMaster.questionTypes.size();
        resultTV.setText("Zeitbonus: " + QuizMaster.score/2 + "\n Richtige Fragen: " + QuizMaster.correctQuestions + "/" + numQuestions +"(" + QuizMaster.correctQuestions*5 + " Punkte)\n Gesamt: " +  (QuizMaster.score/2 + QuizMaster.correctQuestions * 5) +" Punkte");
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
        finish();
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
