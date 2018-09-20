package com.example.vachan.a24frames;

import com.google.gson.annotations.SerializedName;

public class Movies {

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



    public Movies(String plot, String title, String release_date, String imageUrl, String backdropURL, String rating) {
        this.plot = plot;
        this.title = title;
        this.release_date = release_date;
        this.imageUrl = imageUrl;
        this.backdropURL = backdropURL;
        this.rating = rating;

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
        return rating + "/10";
    }

    public String getImageUrl() {
        return "https://image.tmdb.org/t/p/w780" + imageUrl ;
    }

    public String getBackdropURL() {
        return "https://image.tmdb.org/t/p/w780" + backdropURL;
    }
}