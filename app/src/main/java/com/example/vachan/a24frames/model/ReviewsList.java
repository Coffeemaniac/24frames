package com.example.vachan.a24frames.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewsList {

    @SerializedName("results")
    ArrayList<Review> reviewList;

    public ReviewsList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }
}
