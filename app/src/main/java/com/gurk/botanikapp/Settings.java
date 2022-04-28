package com.gurk.botanikapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Settings file for settings/storage
 * currently unused
 *
 * int unlocks can be used to unlock short videos or new game modes
 *
 * i thought a "hardcore mode" with only the latin terms would be fun
 */
public class Settings implements java.io.Serializable{
    String name;
    int unlocks;

    void serialize(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/settings.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /settings.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    Settings deSerialize(String filepath){
        Settings s = new Settings();
        try {
            FileInputStream fileIn = new FileInputStream("/settings.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (Settings) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            s.name = "Botanik Enthusiast";
            s.unlocks = 0;
            return s;
        } catch (ClassNotFoundException c) {
            System.out.println("Settings class not found");
            c.printStackTrace();
            return s;
        }
        return s;
    }
}
