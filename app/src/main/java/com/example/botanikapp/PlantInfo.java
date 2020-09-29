package com.example.botanikapp;

import android.content.Context;

import java.util.Vector;

public class PlantInfo {
    public String name;
    public String primTag;
    public Vector<TriviaQuestionData> triviaQuestions = new Vector<TriviaQuestionData>();
    public int numImages;

    public PlantInfo(){};
    public static Vector<PlantInfo> getPlantInfos(Vector<String> in, final Context ctx){
        Vector<PlantInfo> out = new Vector<PlantInfo>();
        for(String s: in){
            PlantInfo pi = new PlantInfo();
            String[] split = s.split(",");
            if(split.length == 2){
                pi.name = split[0];
                pi.primTag = split[1];
                pi.numImages = Utility.getNumImagesForPlant(pi.name, "drawable", ctx);
                out.add(pi);
            }
            Vector<String> vSQuestions = Utility.readFile("questions_" + pi.name.toLowerCase(), "raw", ctx);
            pi.addQuestions(vSQuestions);
        }

        return out;
    }

    public void addQuestions(Vector<String> in){
        for(int i = 0; i < in.size() - 4; i++){
            TriviaQuestionData t = new TriviaQuestionData();
            t.question = in.elementAt(i++);
            t.correctAnswer = in.elementAt(i++);
            while((i < in.size()) && !in.elementAt(i).equals("Q")){
                t.wrongAnswers.add(in.elementAt(i++));
            }
            triviaQuestions.add(t);
        }
    }
    public TriviaQuestion getRandomTriviaQuestion(){
        int rng = Utility.rand.nextInt(triviaQuestions.size());
        TriviaQuestion t = new TriviaQuestion(triviaQuestions.elementAt(rng));
        return t;
    }
    /*
    PlantInfo(String in){
        Vector<String> data = FileFinder.readFile("data/primary_tags");
        for(String s: data){
            String[] split = s.split(",");
            if(split.length == 2 && split[0].toLowerCase().equals(in.toLowerCase())){
                name = split[0];
                primTag = split[1];
            }
        }

    }*/
}
