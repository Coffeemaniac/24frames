package com.example.vachan.a24frames;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    private String name;

    private String content;

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

}
