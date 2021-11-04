package com.gurk.botanikapp;

public class ResultChecker {
    boolean[] arr = new boolean[8];
    public boolean check(){
        for(int i = 0; i < arr.length; i++){
            if(arr[i]){
                return false;
            }
        }
        return true;
    }
}
