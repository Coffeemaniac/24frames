package com.example.vachan.a24frames.model;

import com.example.vachan.a24frames.database.Movies;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

