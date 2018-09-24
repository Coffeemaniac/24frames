package com.example.vachan.a24frames;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieReviewFragment extends Fragment {


    @BindView(R.id.reviewsList)
    public RecyclerView rv;
    public ReviewListAdapter reviewAdapter;
    public ArrayList<Review> reviews;


    public MovieReviewFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_review_fragment, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Movies movie = getArguments().getParcelable("movie");

        String id = movie.getId();

        reviews = new ArrayList<>();

        rv = (RecyclerView) view.findViewById(R.id.reviewsList);
        reviewAdapter = new ReviewListAdapter(getContext(), reviews);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(reviewAdapter);

        String MovieAPIKey = BuildConfig.ApiKey;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                //specify json converter
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                //specify base URL for the API
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        MovieAPIClient client = retrofit.create(MovieAPIClient.class);
        Call<ReviewsList> callReviews = client.getReviews(id, MovieAPIKey);

        callReviews.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                reviews.addAll(response.body().getReviewList());
                Log.v("results", "the value is " + reviews.size());
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewsList> call, Throwable t) {

            }
        });

    }

    public static Fragment newInstance(Movies movie) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }

 /*   MovieInfoFragment fragment = new MovieInfoFragment();
    Bundle args = new Bundle();
        args.putBundle("bundle", bd);
        fragment.setArguments(args);
        return fragment; */
}

/*
MovieAPIKey = BuildConfig.ApiKey;
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                retrofit = new Retrofit.Builder()
                        //specify json converter
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        //specify base URL for the API
                        .baseUrl("https://api.themoviedb.org/3/")
                        .build();

                client = retrofit.create(MovieAPIClient.class);
                id = (String) bd.getString("id");
                callReviews = client.getReviews(id, MovieAPIKey);




                callReviews.enqueue(new Callback<ReviewsList>() {
@Override
public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
        reviews.addAll(response.body().getReviewList());
        Log.v("results", "the value is " + reviews.get(0).getContent());
        }

@Override
public void onFailure(Call<ReviewsList> call, Throwable t) {

        }
        });
 */