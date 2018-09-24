package com.example.vachan.a24frames;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
