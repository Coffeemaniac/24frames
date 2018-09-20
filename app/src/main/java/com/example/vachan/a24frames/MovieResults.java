package com.example.vachan.a24frames;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResults {
    @SerializedName("results")
    List<Movies> moviesList;

    public MovieResults(List<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    public List<Movies> getMoviesList() {
        return moviesList;
    }
}