package com.example.vachan.a24frames;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.vachan.a24frames.Adapters.MovieFragmentAdapter;
import com.example.vachan.a24frames.database.AppDatabase;
import com.example.vachan.a24frames.database.MovieDao;
import com.example.vachan.a24frames.database.Movies;
import com.example.vachan.a24frames.listeners.MyUndoListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public Toolbar toolbar;
    @BindView(R.id.expandedImage)
    public ImageView backDrop;


    private String id;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MovieFragmentAdapter fragmentPagerAdapter;
    private FloatingActionButton fab;
    private Movies movie;
    private MovieDao movieDao;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        movie = bd.getParcelable("Movie");

        toolbar.setTitle(movie.getTitle());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentPagerAdapter = new MovieFragmentAdapter(getSupportFragmentManager(), movie);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);

        movieDao = AppDatabase.getInstance(getApplicationContext()).movieDao();

        executor = Executors.newFixedThreadPool(2);

        LiveData<List<String>> favIds = movieDao.getIds();
        favIds.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                setFabButton(strings);
            }
        });



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extracting image URL

        Picasso.with(this).load("https://image.tmdb.org/t/p/w780" + movie.getBackdropURL()).into(backDrop);


    }

    private void setFabButton(List<String> ids){
        if(ids.contains(movie.getId())){
            fab.setImageResource(R.drawable.ic_delete_black_24dp);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("movie_value", "The movie is " + movie.getTitle());
                            movieDao.delete(movie);
                        }
                    });
                    fab.setImageResource(R.drawable.ic_fav_icon);
                    /*
                    Snackbar snackView =  Snackbar.make(view, "Movie Removed From Favourites", Snackbar.LENGTH_LONG);
                    snackView.getView().setBackgroundColor(getResources().getColor(R.color.material_light_black));
                    snackView.show(); */
                    // .setAction("Undo", new MyUndoListener()).show();
                }
            });
        }else{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("movie_value", "The movie is " + movie.getTitle());
                            movieDao.insert(movie);
                        }
                    });
                    fab.setImageResource(R.drawable.ic_delete_black_24dp);
                    /*
                    Snackbar snackView =  Snackbar.make(view, "Movie Added to Favourites", Snackbar.LENGTH_LONG);
                    snackView.getView().setBackgroundColor(getResources().getColor(R.color.material_light_black));
                    snackView.show(); */
                    // .setAction("Undo", new MyUndoListener()).show();
                }
            });
        }
    }
}

