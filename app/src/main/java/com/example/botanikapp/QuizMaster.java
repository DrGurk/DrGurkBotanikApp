package com.example.botanikapp;

import android.content.Context;

import com.example.botanikapp.Tag;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class QuizMaster {

    static Random rand = new Random();
    static boolean initialized = false;
    static Vector<Tag> tags = new Vector<Tag>();
    public static Vector<PlantInfo> plantInfos = new Vector<PlantInfo>();
    public static Vector<PlantInfo> plantsWithQuestions = new Vector<PlantInfo>();
    static Vector<Integer> questionTypes = new Vector<Integer>();


    static int gameMode = 0;

    public static int score = 0;
    public static int correctQuestions = 0;
    public static int numQuestionsSurvival = 0;


    public static void prepareQuestions(Vector<Integer> in){
        for(Integer i : in){
            int tmp = i.intValue();
            tmp = tmp < 0 ? 0: tmp > 3 ? 3 : tmp;
            questionTypes.add(tmp);
        }
    }
    //public static void newGame(Context ctx){
     //   newGame(10, ctx);
    //}

    public static void initializeTriviaQuestions(final Context ctx){
        for(PlantInfo pi : plantInfos){
            if (pi.triviaQuestions.size() > 0){
                plantsWithQuestions.add(pi);
            }
        }
    }
    public static void newGame( Context ctx){
        score = 0;
        correctQuestions = 0;
        questionTypes.clear();
        Vector<String> questionTypes = Utility.readFile("fragentypen", "raw",  ctx);
        Vector<Integer> ints = new Vector<Integer>();
            for (String s : questionTypes) {
                    ints.add(Integer.parseInt(s));
            }

        prepareQuestions(ints);
    }
    public static void initialize(final Context ctx){
        initialized = true;

        insertTags(Utility.readTags(ctx));
        System.out.println("Plant read");
        PlantInfo.getPlantInfos(Utility.readFile("tags", "raw",ctx), ctx);
        initializeTriviaQuestions(ctx);
    }


    private static void insertTags(Vector<Vector<String>> in){
        for(Vector<String> vs : in){
            Tag t = new Tag();
            t.name = vs.elementAt(0);
            t.question = vs.elementAt(1);
            for(int i = 2; i < vs.size(); i++){
                t.holders.add(vs.elementAt(i));
            }
            tags.add(t);
        }
    }
    public static FourChoiceQuestion getFourChoiceQuestion(final Context ctx){
        int[] answers = new int[4];
        FourChoiceQuestion out = new FourChoiceQuestion();
        String correct;
        String strtag;
        String sectag;
        int rng = -1;
        rng = rand.nextInt(plantInfos.size());
        PlantInfo pi;
        do{
            pi = plantInfos.elementAt(rng);
            out.numImages = pi.numImages;}
        while(out.numImages < 1);
        correct = pi.name;
        strtag = pi.tags.elementAt(rand.nextInt(pi.tags.size()));

        out.answers.add(correct);

        Tag tag = new Tag();

        for(Tag t: tags){
            if(strtag.equals(t.name)){
                tag = t;
                break;
            }
        }
        if(tag.holders.size() < 4){
            Tag secondtag = tags.elementAt(rand.nextInt(tags.size()));
            for(String s: tag.holders){
                if(!out.answers.contains(s)){
                    out.answers.add(s);
                }
            }
            int remainingQuestions = 4 - out.answers.size();
            for(int i = 0; i < remainingQuestions; i++){
                out.answers.add(secondtag.holders.elementAt(rand.nextInt(secondtag.holders.size())));
            }


        }
        else{
            for(int i = 1; i < 4; i++){
                while(true) {
                    rng = rand.nextInt(tag.holders.size());
                    String s = tag.holders.elementAt(rng);
                    if(!out.answers.contains(s)){
                        out.answers.add(s);
                        break;
                    }

                }
            }
            while(out.answers.size() < 4){
                out.answers.add("Error");
            }
        }
        Collections.shuffle(out.answers);
        out.correct = out.answers.indexOf(correct);
        return out;
    }

    public static TriviaQuestion getTriviaQuestion(String in){

        PlantInfo plantInfo = new PlantInfo();
        for(PlantInfo pi : plantInfos){
            if(pi.name.equals(in)){
                plantInfo = pi;
                break;
            }
        }
        if(plantInfo.triviaQuestions.size() == 0){
            return null;
        }
        int rng = rand.nextInt(plantInfo.triviaQuestions.size());
        TriviaQuestionData triviaQuestionData = plantInfo.triviaQuestions.elementAt(rng);
        TriviaQuestion out = new TriviaQuestion(triviaQuestionData);
        out.shuffle();
        return out;
    }
    public static TriviaQuestion getRandomTriviaQuestion(){

        PlantInfo plantInfo = plantsWithQuestions.elementAt(rand.nextInt(plantsWithQuestions.size()));

        int rng = rand.nextInt(plantInfo.triviaQuestions.size());
        TriviaQuestionData triviaQuestionData = plantInfo.triviaQuestions.elementAt(rng);
        TriviaQuestion out = new TriviaQuestion(triviaQuestionData);
        out.shuffle();
        return out;
    }

    public static PlantInfo getPlant(String name){
        for (PlantInfo pi: plantInfos){
            if(pi.name.equals(name)){
                return pi;
            }
        }
        return new PlantInfo();
    }
    public static MultiAnswerQuestion getMultiAnswerQuestion(final Context ctx){
        MultiAnswerQuestion out = new MultiAnswerQuestion();
        int numTrueAnswers = rand.nextInt(2) + 1;
        int rng = rand.nextInt(QuizMaster.tags.size());

        Vector<Integer> added = new Vector<Integer>();
        Tag tag = tags.elementAt(rng);
        while(tag.holders.size() < 4 || tag.question.equals("null")){
            tag = tags.elementAt(rand.nextInt(QuizMaster.tags.size()));
        }
        Vector<MultiAnswerData> dataVector = new Vector<MultiAnswerData>();
        int timeout = 0;
        for(int i = 0; i < numTrueAnswers; i++){
            rng = rand.nextInt(tag.holders.size());
            if(!added.contains(rng)) {
                added.add(rng);
                MultiAnswerData d = new MultiAnswerData(tag.holders.elementAt(rng), true);
                dataVector.add(d);
            } else{
                i--;
                if(timeout++ > 100){
                    for(int j = dataVector.size(); j < 8; j++){
                        MultiAnswerData to = new MultiAnswerData("Timeout", true);
                        dataVector.add(to);
                    }
                    break;
                }

            }
        }
        out.question = tag.question;

        int numFalseAnswers = 6 - numTrueAnswers;
        timeout = 0;
        for(int i = 0; i < numFalseAnswers; i++){
            rng = rand.nextInt(QuizMaster.plantInfos.size());
            MultiAnswerData d = new MultiAnswerData(QuizMaster.plantInfos.elementAt(rng).name, false);
            if(!dataVector.contains(d) && !tag.holders.contains(d.str)){
                dataVector.add(d);
            } else{
                i--;
                if(timeout++ > 100){
                    for(int j = dataVector.size(); j < 8; j++){
                        MultiAnswerData to = new MultiAnswerData("Timeout", true);
                        dataVector.add(to);
                    }
                    break;
                }
            }
        }

        Collections.shuffle(dataVector);
        out.data = dataVector;
        return out;

    }
    private static void processTimeout(){

    }
}
