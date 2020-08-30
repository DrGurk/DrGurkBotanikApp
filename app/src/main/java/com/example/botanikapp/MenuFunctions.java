package com.example.botanikapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class MenuFunctions {

    public static boolean openMore(Context context){
/*
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse("market://developer?id=" + context.getResources().getString(R.string.developer)));
        if (MyStartActivity(intent, context) == false) {

            intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id="+context.getResources().getString(R.string.developer)));
            if (MyStartActivity(intent, context) == false) {

                Toast.makeText(context, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }
        }*/
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