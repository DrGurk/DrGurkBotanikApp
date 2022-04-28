package com.gurk.botanikapp;

/** MultiAnswerData
 *  contains strings and bools for multi answers, which can be shuffled
 */
public class MultiAnswerData {
    public String str;
    public boolean bool;

    MultiAnswerData(){};
    MultiAnswerData(String s, boolean b){
        str = s;
        bool = b;
    }

    @Override
    public boolean equals(Object other){
        MultiAnswerData d = (MultiAnswerData) other;
        return str.equals(d.str);
    }
}
