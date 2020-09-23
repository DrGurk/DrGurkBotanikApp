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
    public static Vector<PlantInfo> plantInfos;

    public static void initialize(Vector<Vector<String>> tags, Vector<String> tagdata, final Context ctx){
        initialized = true;

        insertTags(tags);
        plantInfos = PlantInfo.getPlantInfos(tagdata, ctx);
    }

    private static void insertTags(Vector<Vector<String>> in){
        for(Vector<String> vs : in){
            Tag t = new Tag();
            t.name = vs.elementAt(0);
            for(int i = 1; i < vs.size(); i++){
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
        int rng = -1;
        rng = rand.nextInt(plantInfos.size());

        correct = plantInfos.elementAt(rng).name;
        strtag = plantInfos.elementAt(rng).primTag;
        out.answers.add(correct);

        Tag tag = new Tag();

        for(Tag t: tags){
            if(strtag.equals(t.name)){
                tag = t;
                break;
            }
        }
        if(tag.holders.size() < 4){
            System.err.println("WARNING: Less than four plants in primary category!");
            return out;
        }
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
        Collections.shuffle(out.answers);
        out.correct = out.answers.indexOf(correct);
        return out;
    }
}
