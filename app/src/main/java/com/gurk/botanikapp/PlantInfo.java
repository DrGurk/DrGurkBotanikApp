package com.gurk.botanikapp;

import android.content.Context;

import java.util.Vector;

/**PlantInfo
 *  stores informations about plants including tags
 *  tags are strings that represent to which family/group of plants this one belongs to
 *
 *  it might be good to split the latin term and german term, currently they are combined
 *
 *  boolean init - used to check if initialization is finished
 *  tags - a vector of tags for this plant
 *  triviaQuestions - a vector of triviaQuestions associated with this plant
 *  numImages - the number of images for this plant
 *
 *  currently the way i'm having the number determined is a bit sketchy,
 *  also getPlantInfos doesn't have to be here
 *
 */
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

    /** addQuestions
     * @param in string representing questions (found in raw/questions_foo). Usually there is one right and 3 wrong answers. you
     *           can extend it to have a greater variety of wrong answers it has to be formatted
     *           like this:
     *           Question
     *           Correct Answer
     *           Wrong Anwser
     *           Wrong Anwser
     *           Wrong Anwser
     *           Optional Wrong Answer
     *           ...
     *           Q
     *           Next Question
     *           ...
     */
    public void addQuestions(Vector<String> in){
        for(int i = 0; i < in.size() - 4; i++){
            TriviaQuestionData t = new TriviaQuestionData();
            t.question = in.elementAt(i++);
            t.correctAnswer = in.elementAt(i++);
            while((i < in.size()) && (in.elementAt(i).compareTo("Q") != 0)){
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
