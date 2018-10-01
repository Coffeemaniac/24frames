package com.example.vachan.a24frames.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.graphics.Movie;
import android.support.annotation.NonNull;

import com.example.vachan.a24frames.BuildConfig;
import com.example.vachan.a24frames.MainActivity;
import com.example.vachan.a24frames.MovieAPIClient;
import com.example.vachan.a24frames.NetworkUtil;
import com.example.vachan.a24frames.database.AppDatabase;
import com.example.vachan.a24frames.database.MovieDao;
import com.example.vachan.a24frames.database.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivityViewModel extends ViewModel {

    private Call<MovieResults> popularCall;
    private Call<MovieResults> topRatedCall;
    private MutableLiveData<List<Movies>> popularData;
    private MutableLiveData<List<Movies>> topRatedData;
    private MovieDao movieDao;

    public MainActivityViewModel() {
        String API_KEY = BuildConfig.ApiKey;
       MovieAPIClient client = NetworkUtil.getService();
       popularCall = client.getPopularMovies(API_KEY);
       topRatedCall = client.getTopRatedMovies(API_KEY);
    }

    public MutableLiveData<List<Movies>> getPopularMovies() {
        if(popularData == null) {
            popularData = new MutableLiveData<>();
            popularCall.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    popularData.setValue(response.body().getMoviesList());
                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                }
            });
        }
        return popularData;
    }

    public MutableLiveData<List<Movies>> getTopRatedMovies() {
        if(topRatedData == null) {
            topRatedData = new MutableLiveData<>();
            topRatedCall.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    topRatedData.setValue(response.body().getMoviesList());
                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {

                }
            });
        }
        return topRatedData;
    }

    public LiveData<List<Movies>> getFavMovies(Application application) {
        if(movieDao == null){
            movieDao = AppDatabase.getInstance(application).movieDao();
        }
        return movieDao.getAll();
    }
}

