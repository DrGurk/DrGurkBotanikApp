package com.gurk.botanikapp;

import java.util.Vector;

public class MultiAnswerQuestion implements Question {

    public Vector<MultiAnswerData> data = new Vector<MultiAnswerData>();
    public String question;
    @Override
    public void createQuestion(String dir, String[] stuff, Tag tag) {

    }

    public ResultChecker check(boolean[] in){
        ResultChecker out = new ResultChecker();
        if(in.length < data.size()){
            out.arr[0] = true;
            return out;
        }
        for(int i = 0; i < data.size(); i++){
            out.arr[i] = in[i] ^ data.elementAt(i).bool;
        }
        return out;
    }
}
