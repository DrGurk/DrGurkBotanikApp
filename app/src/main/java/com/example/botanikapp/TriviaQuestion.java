package com.example.botanikapp;

import java.util.Collections;
import java.util.Vector;

public class TriviaQuestion implements Question {

    String question;
    Vector<String> answers;
    int correct;
    @Override
    public void createQuestion(String dir, String[] stuff, Tag tag) {

    }

    public TriviaQuestion(TriviaQuestionData in){
        if (in.wrongAnswers.size() < 3){
            question = "Too few answers";
            answers.add("");
            answers.add("");
            answers.add("");
            answers.add("");
            return;
        }
        question = in.question;

        answers.add(in.correctAnswer);
        while(true){
            int rng = Utility.rand.nextInt(in.wrongAnswers.size());
            if(!answers.contains(in.wrongAnswers.elementAt(rng))) {
                answers.add(in.wrongAnswers.elementAt(rng));
            }
            if(answers.size() >= 3){
                break;
            }
        }
        correct = 0;
    }

    public void shuffle(){
        shuffle(0);
    }
    public void shuffle(int i){
        String tmp = answers.elementAt(i);
        Collections.shuffle(answers);
        correct = answers.indexOf(tmp);
    }
}
