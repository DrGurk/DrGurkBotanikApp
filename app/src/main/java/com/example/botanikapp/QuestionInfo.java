package com.example.botanikapp;

import java.util.LinkedList;
import java.util.Vector;

public class QuestionInfo {
    String name;
    String[] tags;

    QuestionInfo(Vector<String> in){
        for(int i = 0; i < in.size(); i++){
            if(in.elementAt(i).toLowerCase().equals("name")){
                i++;
                if(i < in.size()){
                    name = in.elementAt(i);
                }
                else{
                    System.err.println("ERROR Question Info creation (name) Index out of bounds!");
                }
            }
            if(in.elementAt(i).toLowerCase().equals("tags")){
                i++;
                if(i < in.size()){
                    tags = in.elementAt(i).split(",");
                }
                else{
                    System.err.println("ERROR Question Info creation (tags) Index out of bounds!");
                }
            }
        }
    }
}
