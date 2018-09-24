package com.example.vachan.a24frames;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFragmentAdapter extends FragmentPagerAdapter {

    public Movies movie;


    public MovieFragmentAdapter(FragmentManager fm, Movies movie) {
        super(fm);
        this.movie = movie;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Info";
        }

        if(position == 1){
            return "Reviews";
        }

        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return MovieInfoFragment.newInstance(movie);
            case 1:
                return MovieReviewFragment.newInstance(movie);
        }
        return null;
    }

    /* returns the number of fragments to the viewPager */
    @Override
    public int getCount() {
        return 2;
    }
}
