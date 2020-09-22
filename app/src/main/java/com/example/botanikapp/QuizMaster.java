package com.example.botanikapp;

import android.content.Context;

import com.example.botanikapp.Tag;

import java.util.Vector;

public class QuizMaster {
    static boolean initialized = false;
    static Vector<Tag> tags = new Vector<Tag>();
    public static Vector<PlantInfo> plantInfos;

    static void initialize(Vector<String> tagdata, final Context ctx){
        initialized = true;

        plantInfos = PlantInfo.getPlantInfos(tagdata, ctx);
    }
}
