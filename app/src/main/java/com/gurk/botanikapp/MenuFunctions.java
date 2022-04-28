package com.gurk.botanikapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

/** MenuFunctions
 *  currenty empty
 */

public class MenuFunctions {

    public static boolean openMore(Context context){
        return true;
    }

    private static boolean MyStartActivity(Intent aIntent, Context context) {
        try
        {
            context.startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}