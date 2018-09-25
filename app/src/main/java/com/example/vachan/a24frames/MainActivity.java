package com.example.vachan.a24frames;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vachan.a24frames.Adapters.RecyclerViewAdapter;
import com.example.vachan.a24frames.model.MovieResults;
import com.example.vachan.a24frames.model.Movies;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myrv;
    private int checked = 0;
    private ArrayList<String> posterURL = new ArrayList<String>();
    private RecyclerViewAdapter myAdapter;
    private static Retrofit retrofit;
    private ArrayList<Movies> results;

    private MovieAPIClient client;
    private Call<MovieResults> call;
    public String MovieAPIKey;

    /*
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = new ArrayList<Movies>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#1976d2"));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Popular Movies");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        // Recycler View related stuff
        myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        myrv.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        myAdapter = new RecyclerViewAdapter(MainActivity.this, results);
        myrv.setAdapter(myAdapter);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                //specify json converter
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                //specify base URL for the API
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        MovieAPIKey = BuildConfig.ApiKey;
        client = retrofit.create(MovieAPIClient.class);


        if(savedInstanceState != null){
            checked = savedInstanceState.getInt("CHECKED_VALUE");
        }

        switch (checked){
            case 0:
                results.clear();
                call = client.getPopularMovies(MovieAPIKey);
                getMovieResults(call);
                break;
            case 1:
                results.clear();
                call = client.getPopularMovies(MovieAPIKey);
                getMovieResults(call);
                break;
            case 2:
                results.clear();
                call = client.getTopRatedMovies(MovieAPIKey);
                getMovieResults(call);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        switch (checked){
            case 0:
                menu.findItem(R.id.PopularMovies).setChecked(true);
                break;
            case 1:
                menu.findItem(R.id.PopularMovies).setChecked(true);
                break;
            case 2:
                menu.findItem(R.id.TopRated).setChecked(true);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("CHECKED_VALUE", checked);
        super.onSaveInstanceState(outState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.PopularMovies){
            checked = 1;
        }else{
            checked = 2;
        }

        switch (item.getItemId()) {
            case R.id.PopularMovies:
                if(item.isChecked()){
                    return false;
                }
                else{
                    item.setChecked(true);
                    results.clear();
                    call = client.getPopularMovies(MovieAPIKey);
                    getMovieResults(call);
                }
            case R.id.TopRated:
                if(item.isChecked()){
                    return false;
                }
                else{
                    item.setChecked(true);
                    results.clear();
                    call = client.getTopRatedMovies(MovieAPIKey);
                    getMovieResults(call);

                }
        }
        return (super.onOptionsItemSelected(item));
    }

    public boolean getMovieResults(Call<MovieResults> call){
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                results.addAll(response.body().getMoviesList());
                for(int i = 0; i < results.size(); i++){
                    posterURL.add(results.get(i).getImageUrl());
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });
        return true;
    }
}




