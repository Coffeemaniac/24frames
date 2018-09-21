package com.example.vachan.a24frames;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieAPIClient {

    @GET("movie/popular")
    Call<MovieResults> getPopularMovies (@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResults> getTopRatedMovies (@Query("api_key") String apiKey);
}
