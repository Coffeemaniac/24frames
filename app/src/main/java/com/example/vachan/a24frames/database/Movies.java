package com.example.vachan.a24frames.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favourite_movies")
public class Movies implements Parcelable{


    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("overview")
    private String plot;
    private String title;
    private String release_date;

    @SerializedName("poster_path")
    private String imageUrl;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("backdrop_path")
    private String backdropURL;

    public static final Parcelable.Creator CREATOR = new
            Parcelable.Creator(){
                public Movies createFromParcel(Parcel in){
                    return new Movies(in);
                }

                public Movies[] newArray(int size){
                    return new Movies[size];
                }
            };



    public Movies(String plot, String title, String release_date, String imageUrl, String backdropURL, String rating, String id) {
        this.plot = plot;
        this.title = title;
        this.release_date = release_date;
        this.imageUrl = imageUrl;
        this.backdropURL = backdropURL;
        this.rating = rating;
        this.id = id;

    }


    public String getPlot() {
        return plot;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl ;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public String getId() { return id; }

    //Parcelable constructor
    public Movies(Parcel in){
        this.plot = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.imageUrl = in.readString();
        this.backdropURL = in.readString();
        this.rating = in.readString();
        this.id  = in.readString();
    }

    @Override
    public int describeContents(){

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.plot);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.imageUrl);
        dest.writeString(this.backdropURL);
        dest.writeString(this.rating );
        dest.writeString(this.id);
    }

}
