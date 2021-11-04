package com.gurk.botanikapp;

import java.util.Random;
import java.util.Vector;

public class FourChoiceQuestion implements Question {
    String imagePath;
    Vector<String> answers = new Vector<String>();
    int correct;
    int numImages;

    public void createQuestion(String dir, String[] stuff, Tag tag){
        Random rand = new Random();
        String[] textFilter = {"txt"};

        String[] filter = {".jpg", ".jpeg", ".png"};
        try {
            String[] name = FileFinder.getList(dir, textFilter);
            if(name.length < 1){
                System.err.println("No descriptive txt file found!");
                return;
            }
            QuestionInfo info = new QuestionInfo(FileFinder.readFileNoSpace(name[0]));
            String[] imgPaths = FileFinder.getList(dir, filter);
            int numImgs = imgPaths.length;
            int curImg = rand.nextInt(numImgs);
            imagePath = imgPaths[curImg];
        } catch(java.io.IOException e){
            System.err.println("IO ERROR! Reading images failed!");
        }

    }
}
