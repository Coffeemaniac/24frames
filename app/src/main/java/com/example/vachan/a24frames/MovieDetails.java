package com.example.vachan.a24frames;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {
   /* @BindView(R.id.imageView)
    ImageView posterImage;
    @BindView(R.id.textView2)
    TextView titleView;
    @BindView(R.id.textView5)
    TextView rating;
    @BindView(R.id.textView7)
    TextView releaseDate;
    @BindView(R.id.textView10)
    TextView plot;
    @BindView(R.id.trailer)
    Button button; */
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.expandedImage)
    ImageView backDrop;


    private Call<Videos> callVideos;
    private Call<ReviewsList> callReviews;
    private static Retrofit retrofit;
    private MovieAPIClient client;
    public static String MovieAPIKey;
    private String youtube_url;

    private ArrayList<Trailer> trailers;
    private List<Review> review;

    private String id;
    TabLayout tabLayout;
    ViewPager viewPager;
    MovieFragmentAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String title = (String) bd.get("title");
        toolbar.setTitle(title);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentPagerAdapter = new MovieFragmentAdapter(getSupportFragmentManager(),bd);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        review = new ArrayList<Review>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extracting image URL

        String backdrop = (String) bd.get("backdrop");
        Picasso.with(this).load(backdrop).into(backDrop);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Movie has been added to your fav list", Snackbar.LENGTH_LONG)
                        .setAction("Undo", null).show();
            }
        });



        // callReviews = client.getReviews(id, MovieAPIKey);


      /* Review callback */

    /* callReviews.enqueue(new Callback<ReviewsList>() {
         @Override
         public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
             review.addAll(response.body().getReviewList());
            // plot.setText(review.get(0).getContent());
         }

         @Override
         public void onFailure(Call<ReviewsList> call, Throwable t) {

         }
     }); */


    }
}

