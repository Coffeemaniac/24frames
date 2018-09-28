package com.example.vachan.a24frames;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {

    private static Retrofit retrofit=null;
    private static String BASE_URL = "http://api.themoviedb.org/";

    public static MovieAPIClient getService(){

        if(retrofit==null){


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(MovieAPIClient.class);
    }
}
