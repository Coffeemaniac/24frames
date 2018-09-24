package com.example.vachan.a24frames;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieInfoFragment extends Fragment {

   @BindView(R.id.textView2)
   TextView titleView;
   @BindView(R.id.textView5)
   TextView rating;
   @BindView(R.id.textView7)
   TextView releaseDate;
   @BindView(R.id.textView10)
   TextView plot;
   @BindView(R.id.trailer)
   Button button;
   @BindView(R.id.imageView)
   ImageView posterImage;

    private Call<Videos> callVideos;

    private static Retrofit retrofit;
    private MovieAPIClient client;
    public static String MovieAPIKey;
    private String youtube_url;
    private Movies movie;

    private ArrayList<Trailer> trailers;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment r
        return inflater.inflate(R.layout.movie_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        movie = getArguments().getParcelable("movie");

        trailers = new ArrayList<Trailer>();

        titleView.setText(movie.getTitle());
        rating.setText(movie.getRating());
        releaseDate.setText(movie.getRelease_date());
        plot.setText(movie.getPlot());

        Picasso.with(getActivity()).load(movie.getImageUrl()).into(posterImage);

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
        callVideos = client.getVideos(movie.getId(), MovieAPIKey);

        callVideos.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                trailers.addAll(response.body().getTrailerList());
                youtube_url = trailers.get(0).getVideoKey();
                button.setAlpha(1.0f);
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_url)));

            }
        });
    }

    public static Fragment newInstance(Movies movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }
}
