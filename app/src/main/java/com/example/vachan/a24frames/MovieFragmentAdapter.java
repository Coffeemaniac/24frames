package com.example.vachan.a24frames;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieFragmentAdapter extends FragmentPagerAdapter {
   // List<Review> reviews;
    Bundle bd;
    public MovieFragmentAdapter(FragmentManager fm,Bundle bundle) {
        super(fm);
       // this.reviews = review;
        this.bd = bundle;

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
                return MovieInfoFragment.newInstance(bd); // new MovieInfoFragment
            case 1:
                return new MovieReviewFragment();
        }
        return null;
    }

    /* returns the number of fragments to the viewPager */
    @Override
    public int getCount() {
        return 2;
    }
}
