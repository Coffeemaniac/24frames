package com.example.vachan.a24frames.model;

import com.google.gson.annotations.SerializedName;


public class Trailer {

    private String id;
    private String key;
    public final String youtube_url = "http://www.youtube.com/watch?v=";

    public Trailer(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getThumbnail() {
        return "https://img.youtube.com/vi/" + key + "0.jpg";
    }

    public String getVideoKey(){
        return youtube_url + key;
    }
}
