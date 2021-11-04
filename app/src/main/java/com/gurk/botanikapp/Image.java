package com.gurk.botanikapp;

import android.os.Parcelable;

public interface Image extends Parcelable {
    public String getTitle();
    public String getDescription();
    public String getPath();
    public String getThumbPath();

}