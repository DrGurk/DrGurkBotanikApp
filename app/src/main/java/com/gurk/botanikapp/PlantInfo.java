package com.gurk.botanikapp;

import android.content.Context;

import java.util.Vector;

public class PlantInfo {
    public boolean init = true;
    public String name;
    public Vector<String> tags = new Vector<String>();
    public Vector<TriviaQuestionData> triviaQuestions = new Vector<TriviaQuestionData>();
    public int numImages;

    public PlantInfo(){};
    public static void getPlantInfos(Vector<String> in, final Context ctx){
        Vector<PlantInfo> out = new Vector<PlantInfo>();
        for(String s: in){
            Vector<String> plants = Utility.readFile("tag_" + Utility.androidString(s), "raw", ctx);
            for(int i = 1; i < plants.size();i++) {
                String str = plants.elementAt(i);
                PlantInfo pi = QuizMaster.getPlant(str);
                pi.tags.add(s);
                if(pi.init){
                    pi.name = str;

                    Vector<String> vSQuestions = Utility.readFile("questions_" + Utility.androidString(pi.name), "raw", ctx);
                    pi.addQuestions(vSQuestions);
                    pi.numImages = Utility.getNumImagesForPlant(Utility.androidString(pi.name), "drawable", ctx);
                    if(pi.numImages > 0) {
                        QuizMaster.plantInfos.add(pi);
                    }
                }
            }
        }
    }
    public String getName(){
        String strs[] = name.split("\\(");
        return strs[0];
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
