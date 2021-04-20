package com.example.botanikapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Utility {
    public static String appfolder;
    static Random rand = new Random();



    public static String getAppDirectory(Activity a){
        PackageManager m = a.getPackageManager();
        String s = a.getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("variables", "Error Package name not found ", e);
        }
        return s;
    }

    public static int getNumberImagesInDirectory(String dir) {
        File f = new File(dir);
        int count = 0;
        for (File file : f.listFiles()) {
            if (file.isFile() &&
                    (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")))  {
                count++;
            }
        }
        return count;
    }
    public static String pathToResource(String in){
        return in.replace('/', '_').replace(' ', '+');
    }

    public static int getNumImagesForPlant(final String in, final String resType, final Context ctx){
        int out = 0;
        while(true){
            String str = in + "_" + out;
            if(getResourceID(str, resType, ctx) == 0){
                break;
            }
            out++;
        }
        return out;
    }

    public final static int getResourceID
            (final String resName, final String resType, final Context ctx)
    {
        int out = 0;
        try {
            final int resourceID =
                    ctx.getResources().getIdentifier(resName, resType,
                            ctx.getApplicationInfo().packageName);
            out = resourceID;

        }
        catch(Exception e) {
        }
            return out; // 0 = ERROR
    }
    public static Vector<String> readFile(String file, String resType, final Context ctx) {
        Vector<String> out = new Vector<String>();
        try {
            InputStream inputStream = ctx.getResources().openRawResource(Utility.getResourceID(file, resType,
                    ctx.getApplicationContext()));;
            Scanner myReader = new Scanner(inputStream);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                out.add(data);
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println(file);
            System.out.println(out);
            System.out.println("No file could be read.");
            e.printStackTrace();
        }
        return out;
    }
    public static Vector<Vector<String>> readTags(final Context ctx){
        Vector<Vector<String>> out = new Vector<Vector<String>>();
        try {
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.tags);
            Scanner myReader = new Scanner(inputStream);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                out.add(new Vector<String>());
                out.lastElement().add(data);
                String file = "tag_" + androidString(data);
                InputStream innerInputStream = ctx.getResources().openRawResource(Utility.getResourceID(file, "raw",
                        ctx.getApplicationContext()));
                Scanner innerReader = new Scanner(innerInputStream);
                while (innerReader.hasNextLine()) {
                    String innerData = innerReader.nextLine();
                    out.lastElement().add(innerData);
                }
                innerReader.close();
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("No file could be read.");
            e.printStackTrace();
        }
        return out;
    }

    public static String androidString(String in){
        return in.replace("ä", "ae").replace("ö","oe").replace("ü", "ue").replace(" ", "_").replace("(", "_").replace(')', '_').replace("-", "_").replace(",", "_").replace('.', '_').replace("ß", "ss").toLowerCase();
    }
}
