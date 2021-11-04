package com.gurk.botanikapp;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Image{
    String name;
    String description;
    String path;
    public ImageItem() {
        super();
    }    public ImageItem(String name, String description, String path) {
        super();
        this.name = name;
        this.description = description;
        this.path = path;
    }    public String getName() {
        return name;
    }    public void setName(String name) {
        this.name = name;
    }    public void setDescription(String description) {
        this.description = description;
    }    public void setPath(String path) {
        this.path = path;
    }
    @Override
    public String getTitle() {

        return name;
    }

    @Override
    public String getThumbPath() {

        return null;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getPath() {
        return path;
    }

    public ImageItem(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.path = in.readString();
    }
    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(path);
    }
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
