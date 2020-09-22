package com.example.botanikapp;

import android.content.Context;

import java.io.FileReader;
import java.util.Vector;

public class PlantInfo {
    public String name;
    public String primTag;
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
        }
        return out;
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
