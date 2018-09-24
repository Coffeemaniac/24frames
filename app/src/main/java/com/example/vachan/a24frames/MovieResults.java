package com.example.vachan.a24frames;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResults {
    @SerializedName("results")
    ArrayList<Movies> moviesList;

    public MovieResults(ArrayList<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    public ArrayList<Movies> getMoviesList() {
        return moviesList;
    }
}