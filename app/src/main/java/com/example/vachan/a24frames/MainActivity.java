package com.example.vachan.a24frames;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vachan.a24frames.Adapters.RecyclerViewAdapter;
import com.example.vachan.a24frames.database.AppDatabase;
import com.example.vachan.a24frames.database.MovieDao;
import com.example.vachan.a24frames.model.MainActivityViewModel;
import com.example.vachan.a24frames.database.Movies;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView myrv;
    private int checked = 0;
    private RecyclerViewAdapter myAdapter;
    private ArrayList<Movies> results;
    private ArrayList<Movies> popularMovies;
    private ArrayList<Movies> topRatedMovies;
    private ArrayList<Movies> favouriteMovies;
    private MainActivityViewModel mViewModel;

    private ProgressBar progressBar;
    private TextView textView;

    private RecyclerView.LayoutManager mLayoutManager;
    private MovieDao movieDao;

    public static final String title = "Popular Movies";
    public static final String CHECKED_KEY = "CHECKED_VALUE";

    public interface callBack {
        void doSomething(Intent intent, ImageView imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = new ArrayList<Movies>();

        popularMovies = new ArrayList<>();
        topRatedMovies = new ArrayList<>();
        favouriteMovies = new ArrayList<>();


        getSupportActionBar().setTitle(title);


        progressBar = findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        textView = findViewById(R.id.noItems);

        // Recycler View related stuff
        myrv = findViewById(R.id.recyclerview_id);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            mLayoutManager = new GridLayoutManager(this,2);

        }else{
            mLayoutManager = new GridLayoutManager(this,3);
        }

        myrv.setLayoutManager(mLayoutManager);
        myAdapter = new RecyclerViewAdapter(MainActivity.this, results, new callBack() {
            @Override
            public void doSomething(Intent intent, ImageView imageView) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, imageView, "poster");
                startActivity(intent, options.toBundle());
            }
        });
        myrv.setAdapter(myAdapter);
        myrv.setHasFixedSize(true);


       mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        if(savedInstanceState != null){
            checked = savedInstanceState.getInt(CHECKED_KEY);

        }

        MutableLiveData<List<Movies>> popularMoviesdata = mViewModel.getPopularMovies();
        MutableLiveData<List<Movies>> topRatedMoviesdata = mViewModel.getTopRatedMovies();

        popularMoviesdata.observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                popularMovies.addAll(movies);
                displayResults();
            }
        });

        topRatedMoviesdata.observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                topRatedMovies.addAll(movies);
                displayResults();
            }
        });

        final LiveData<List<Movies>> favMovies = mViewModel.getFavMovies(getApplication());
        favMovies.observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                favouriteMovies.clear();
                favouriteMovies.addAll(movies);
                displayResults();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        switch (checked){
            case 0:
                menu.findItem(R.id.PopularMovies).setChecked(true);
                break;
            case 1:
                menu.findItem(R.id.TopRated).setChecked(true);
                break;
            case 2:
                menu.findItem(R.id.Favourite).setChecked(true);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CHECKED_KEY, checked);
        super.onSaveInstanceState(outState);
    }

    private void displayResults(){
            if(popularMovies.size() != 0){
                progressBar.setVisibility(View.GONE);
                myrv.setVisibility(View.VISIBLE);
            }
            results.clear();
            if(checked == 0){
                results.addAll(popularMovies);
            }
            else if (checked == 1){
                results.addAll(topRatedMovies);
            }else{
                results.addAll(favouriteMovies);
            }
            myAdapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        textView.setVisibility(View.GONE);
        myrv.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            case R.id.PopularMovies:
                checked = 0;
                if(item.isChecked()){
                    return false;
                }
                else{
                    item.setChecked(true);
                    results.clear();
                    results.addAll(popularMovies);
                    break;
                }
            case R.id.TopRated:
                checked = 1;
                if(item.isChecked()){
                    return false;
                }
                else{
                    item.setChecked(true);
                    results.clear();
                    results.addAll(topRatedMovies);
                    break;
                }
            case R.id.Favourite:
                if(favouriteMovies.size() == 0){
                    myrv.setVisibility(View.GONE);
                    textView.setText(R.string.empty_fav_list);
                    textView.setVisibility(View.VISIBLE);
                }else{
                    textView.setVisibility(View.GONE);
                    myrv.setVisibility(View.VISIBLE);
                }
                    checked = 2;
                    item.setChecked(true);
                    results.clear();
                    results.addAll(favouriteMovies);
        }
        myAdapter.notifyDataSetChanged();
        return (super.onOptionsItemSelected(item));
    }
}




