package com.example.vachan.a24frames;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView posterImage;
    @BindView(R.id.textView2)
    TextView titleView;
    @BindView(R.id.textView5)
    TextView rating;
    @BindView(R.id.textView7)
    TextView releaseDate;
    @BindView(R.id.textView10)
    TextView plot;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.expandedImage)
    ImageView backDrop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String title = (String) bd.get("title");
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extracting image URL
        String poster = (String) bd.get("Thumbnail");
        String backdrop = (String) bd.get("backdrop");
        String ratingValue = (String) bd.get("rating");
        String releaseValue = (String) bd.get("release");
        String plotValue = (String) bd.get("plot");

        titleView.setText("" + title);

        rating.setText(ratingValue);
        releaseDate.setText(releaseValue);
        plot.setText(plotValue);


        Picasso.with(this).load(poster).into(posterImage);
        Picasso.with(this).load(backdrop).into(backDrop);

    }

}

