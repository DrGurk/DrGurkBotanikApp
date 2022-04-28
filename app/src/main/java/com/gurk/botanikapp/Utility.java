package com.gurk.botanikapp;

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

/** Utility
 *  utility functions such as string manipluation to cope with having everything top level
 */
public class Utility {
    public static String appfolder;
    static Random rand = new Random();


    /** splitPar
     *  returns the german part of a plant/file name
     * @param in plant/file name
     * @return name
     */
    public static String splitPar(String in){
        String[] strs= in.split("\\(");
        String s = strs[0];
        s = s.substring(0, s.length() - 1);
        System.out.println(s);
        return s;
    }
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

    /** getNumImagesForPlant
     *  returns the number of images for a plant
     *  workaround for having top level images only
     *  
     *  note that it only checks consecutively, if you have images ending in 0, 1 and 3, it will
     *  only count 0 and 1, and not find 3 because 2 is missing
     *  
     * @param in plant name
     * @param resType has to be "raw"
     * @param ctx
     * @return number of images for plant
     */
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

    public final static int getResourceID(final String resName, final String resType, final Context ctx) {
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

    /** readFile
     * splits file into string vectors for each line
     */
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

    /** readTags
     *  reads all tags, holders and the tag question from all tag_ files
     *  file structure:
     *            Tag display name (e.g. Wiesenpflanzen)
     *            Question
     *            Right answer
     *            Wrong answer
     *            Wrong answer
     *            Wrong answer
     *            Optional wrong answer
     *            ...
     * @return
     */
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

    /** androidString
     * converts umlauts and other nasty symbols that can interfere with android
     */
    public static String androidString(String in){
        return in.replace("ä", "ae").replace("ö","oe").replace("ü", "ue").replace(" ", "_").replace("(", "_").replace(')', '_').replace("-", "_").replace(",", "_").replace('.', '_').replace("ß", "ss").toLowerCase();
    }
}
