package com.example.vachan.a24frames.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.vachan.a24frames.database.Movies;

public class MovieFragmentAdapter extends FragmentPagerAdapter {

    public Movies movie;
    public static final String INFO_TITLE = "Info";
    public static final String REVIEWS_TITLE = "reviews";


    public MovieFragmentAdapter(FragmentManager fm, Movies movie) {
        super(fm);
        this.movie = movie;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return INFO_TITLE;
        }

        if(position == 1){
            return REVIEWS_TITLE;
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
