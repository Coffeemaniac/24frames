package com.example.vachan.a24frames;

import com.example.vachan.a24frames.model.MovieResults;
import com.example.vachan.a24frames.model.ReviewsList;
import com.example.vachan.a24frames.model.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieAPIClient {

    @GET("movie/popular")
    Call<MovieResults> getPopularMovies (@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResults> getTopRatedMovies (@Query("api_key") String apiKey);

    //TODO: Create data model class for reviewsList and review
    @GET("movie/{id}/reviews")
    Call<ReviewsList> getReviews(@Path("id") String id, @Query("api_key") String apiKey);

    //TODO: Create Data model class for Videos and trailers
    @GET("movie/{id}/videos")
    Call <Videos> getVideos(@Path("id") String id, @Query("api_key") String apiKey);
}
