package com.example.vachan.a24frames.model;

import com.google.gson.annotations.SerializedName;


public class Trailer {

    private String id;
    private String key;
    public static final String youtube_url = "https://img.youtube.com/vi/";

    public Trailer(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getThumbnail() {
        return youtube_url + key + "/0.jpg";
    }

    public String getVideoKey(){
        return youtube_url + key;
    }
}
