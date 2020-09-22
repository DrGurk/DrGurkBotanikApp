package com.example.botanikapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class FileFinder {
    static String[] getList(String dir,final String[] filter) throws IOException {
        String out = "";

        //Creating a File object for directory
        File directoryPath = new File(dir);

        FilenameFilter textFilefilter = new FilenameFilter(){
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                for(String s : filter){
                    String tmp = s;
                    if(s.charAt(0) != '.'){
                        tmp = "." + s;
                    }
                    if(lowercaseName.endsWith(tmp)){
                        return true;
                    }
                }
                return false;
            }
        };
        //List of all the text files
        String filesList[] = directoryPath.list(textFilefilter);
        System.out.println("List of the text files in the specified directory:");
        for(String fileName : filesList) {
            System.out.println(fileName);
        }
        return filesList;
    }
    static Vector<String> readFileNoSpace(String dir) {//TODO: change to inputfile stream if it doesnt work
        Vector<String> out = new Vector<String>();
        try {
            File myObj = new File(dir);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                out.add(data.replaceAll("( |\t)", ""));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file could be read.");
            e.printStackTrace();
        }
        return out;
    }

}
