package com.example.botanikapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;

public class Utility {
    public static String appfolder;




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
}
