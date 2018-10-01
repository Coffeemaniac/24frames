package com.example.vachan.a24frames.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachan.a24frames.BuildConfig;
import com.example.vachan.a24frames.MovieAPIClient;
import com.example.vachan.a24frames.NetworkUtil;
import com.example.vachan.a24frames.R;
import com.example.vachan.a24frames.database.Movies;
import com.example.vachan.a24frames.model.Trailer;
import com.example.vachan.a24frames.model.Videos;
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
   public TextView titleView;
   @BindView(R.id.textView5)
   public TextView rating;
   @BindView(R.id.textView7)
   public TextView releaseDate;
   @BindView(R.id.textView10)
   public TextView plot;
   @BindView(R.id.trailer)
   public Button button;
   @BindView(R.id.imageView)
   public ImageView posterImage;
   @BindView(R.id.trailerRecyclerView)
   public RecyclerView trailerRecyclerView;

    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w780";
    public static final String MOVIE_KEY = "movie";
    public static final String RATING_STRING = "/10";



    private Call<Videos> callVideos;

    private static Retrofit retrofit;
    private MovieAPIClient client;
    public static String MovieAPIKey;
    private String youtube_url;
    private Movies movie;
    private movieTrailerAdapter trailerAdapter;

    private ArrayList<Trailer> trailers;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        trailers = new ArrayList<>();

        trailerAdapter = new movieTrailerAdapter(getContext(), trailers);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trailerRecyclerView.setAdapter(trailerAdapter);

        movie = getArguments().getParcelable(MOVIE_KEY);


        titleView.setText(movie.getTitle());
        rating.setText(movie.getRating() + RATING_STRING);
        releaseDate.setText(movie.getRelease_date());
        plot.setText(movie.getPlot());

        Picasso.with(getActivity()).load(IMAGE_URL + movie.getImageUrl()).into(posterImage);

        MovieAPIKey = BuildConfig.ApiKey;

        client = NetworkUtil.getService();
        callVideos = client.getVideos(movie.getId(), MovieAPIKey);

        callVideos.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                trailers.addAll(response.body().getTrailerList());
                if(trailers.size() == 0){
                    youtube_url = null;
                    button.setAlpha(0.0f);
                }else{
                    youtube_url = trailers.get(0).getVideoKey();
                    button.setAlpha(1.0f);
                }
                trailerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Toast.makeText(getContext(), R.string.network_failure, Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, youtube_url);
                startActivity(Intent.createChooser(shareIntent, "Share link using"));
            }
        });
    }

    public static Fragment newInstance(Movies movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_KEY, movie);
        fragment.setArguments(args);
        return fragment;
    }
}
