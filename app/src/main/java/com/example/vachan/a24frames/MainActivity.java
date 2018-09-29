package com.example.vachan.a24frames;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    private RecyclerView.LayoutManager mLayoutManager;

    private MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = new ArrayList<Movies>();

        popularMovies = new ArrayList<Movies>();
        topRatedMovies = new ArrayList<Movies>();
        favouriteMovies = new ArrayList<Movies>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#1976d2"));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Popular Movies");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //get MovieDAO
        movieDao = AppDatabase.getInstance(getApplicationContext()).movieDao();


        // Recycler View related stuff
        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            mLayoutManager = new GridLayoutManager(this,2);

        }else{
            mLayoutManager = new GridLayoutManager(this,3);
        }

        myrv.setLayoutManager(mLayoutManager);
        myAdapter = new RecyclerViewAdapter(MainActivity.this, results);
        myrv.setAdapter(myAdapter);

        MainActivityViewModel mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        if(savedInstanceState != null){
            checked = savedInstanceState.getInt("CHECKED_VALUE");
            Log.v("inSavedInstance", "The value of checked is " + checked);

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

        final LiveData<List<Movies>> favMovies = movieDao.getAll();
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

        Log.v("onCreateOptionsMenu", "The value of checked is " + checked);
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
        outState.putInt("CHECKED_VALUE", checked);
        super.onSaveInstanceState(outState);
    }

    private void displayResults(){
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
                    checked = 2;
                    item.setChecked(true);
                    results.clear();
                    results.addAll(favouriteMovies);
        }
        myAdapter.notifyDataSetChanged();
        return (super.onOptionsItemSelected(item));
    }
}




