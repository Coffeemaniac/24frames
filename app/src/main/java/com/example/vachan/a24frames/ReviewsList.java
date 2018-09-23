package com.example.vachan.a24frames;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsList {

    @SerializedName("results")
    List<Review> reviewList;

    public ReviewsList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
