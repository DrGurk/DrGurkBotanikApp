package com.gurk.botanikapp;

import java.util.Vector;

/** MultiAnswerQuestion
 * implementation of a question holding a vector of MultiAnswerData
 *
 * Multianswer questions are the same as tag questions
 * the idea was that multiple answers were correct, similar to the "which of these contains a car"
 * captchas
 *
 * feedback was bad, so we reduced it to 2 answers
 *
 * probably just make it more similar to the captcha and "select all trees" with images would be
 * a nice change
 */
public class MultiAnswerQuestion implements Question {

    public Vector<MultiAnswerData> data = new Vector<MultiAnswerData>();
    public String question;
    @Override
    public void createQuestion(String dir, String[] stuff, Tag tag) {

    }

    /** check
     *  checks how many of the answers in "in" are correct
     * @param in boolean[]
     * @return ResultChecker (wrapper for boolean[]) used to determine the color we have to draw
     *         for each button after the answer
     */
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
