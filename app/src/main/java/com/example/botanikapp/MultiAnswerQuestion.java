package com.example.botanikapp;

import java.util.Vector;

public class MultiAnswerQuestion implements Question {

    public Vector<MultiAnswerData> data = new Vector<MultiAnswerData>();
    public String question;
    @Override
    public void createQuestion(String dir, String[] stuff, Tag tag) {

    }

    public boolean check(boolean[] in){
        if(in.length < data.size()){
            return false;
        }
        for(int i = 0; i < data.size(); i++){
            if(in[i] != data.elementAt(i).bool){
                return false;
            }
        }
        return true;
    }
}
