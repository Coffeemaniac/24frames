package com.example.vachan.a24frames.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos {

    @SerializedName("results")
    List<Trailer> TrailerList;


    public Videos(List<Trailer> trailerList) {
        TrailerList = trailerList;
    }

    public List<Trailer> getTrailerList() {
        return TrailerList;
    }

}
