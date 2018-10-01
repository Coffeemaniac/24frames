package com.example.vachan.a24frames.Adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachan.a24frames.BuildConfig;
import com.example.vachan.a24frames.MovieAPIClient;
import com.example.vachan.a24frames.NetworkUtil;
import com.example.vachan.a24frames.R;
import com.example.vachan.a24frames.database.Movies;
import com.example.vachan.a24frames.model.Review;
import com.example.vachan.a24frames.model.ReviewsList;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieReviewFragment extends Fragment {


    @BindView(R.id.reviewsList)
    public RecyclerView rv;
    @BindView(R.id.progressbar)
    public ProgressBar progressBar;
    @BindView(R.id.message)
    public TextView textView;
    public ReviewListAdapter reviewAdapter;
    public ArrayList<Review> reviews;

    public static final String MOVIE_KEY = "movie";


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

        Movies movie = getArguments().getParcelable(MOVIE_KEY);

        String id = movie.getId();

        reviews = new ArrayList<>();

        rv =  view.findViewById(R.id.reviewsList);
        reviewAdapter = new ReviewListAdapter(getContext(), reviews);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(reviewAdapter);

        String MovieAPIKey = BuildConfig.ApiKey;
        MovieAPIClient client = NetworkUtil.getService();
        Call<ReviewsList> callReviews = client.getReviews(id, MovieAPIKey);

        progressBar.setVisibility(View.VISIBLE);
        callReviews.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                reviews.addAll(response.body().getReviewList());
                if(reviews.size() == 0){
                        textView.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);
                }else{
                    reviewAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ReviewsList> call, Throwable t) {
                Toast.makeText(getContext(), R.string.network_failure, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static Fragment newInstance(Movies movie) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_KEY, movie);
        fragment.setArguments(args);
        return fragment;
    }

}
