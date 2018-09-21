package com.example.vachan.a24frames;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView myrv;
    int i = 20;
    ArrayList<String> posterURL = new ArrayList<String>();
    RecyclerViewAdapter myAdapter;
    private static Retrofit retrofit;
    private List<Movies> results;

    private MovieAPIClient client;
    private Call<MovieResults> call;
    private String MovieAPIKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = new ArrayList<Movies>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#1976d2"));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Popular Movies");


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
        call = client.getPopularMovies(MovieAPIKey);
        getMovieResults(call);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.PopularMovies).setChecked(true);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
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




