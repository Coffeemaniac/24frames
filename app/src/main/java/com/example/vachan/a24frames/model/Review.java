package com.example.vachan.a24frames.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable {

    public static final Parcelable.Creator CREATOR = new
            Parcelable.Creator(){
                public Review createFromParcel(Parcel in){
                    return new Review(in);
                }

                public Review[] newArray(int size){
                    return new Review[size];
                }
            };

    @SerializedName("author")
    public String name;

    public String content;

    public Review(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }


    // parcelable contructor
    public Review(Parcel in){
        this.name = in.readString();
        this.content = in.readString();

    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.name);
        dest.writeString(this.content);
    }

    @Override
    public String toString() {
        return "Review{" +
                "name='" + name + '\'' +
                ", content'" + content + '\'' +
                + '}';
    }
}
