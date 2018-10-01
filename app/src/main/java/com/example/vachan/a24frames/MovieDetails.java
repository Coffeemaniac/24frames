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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.vachan.a24frames.Adapters.MovieFragmentAdapter;
import com.example.vachan.a24frames.database.AppDatabase;
import com.example.vachan.a24frames.database.MovieDao;
import com.example.vachan.a24frames.database.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.expandedImage)
    public ImageView backDrop;


    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w780";
    public static final String MOVIE_KEY = "movie";


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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        movie = bd.getParcelable(MOVIE_KEY);

        getSupportActionBar().setTitle(movie.getTitle());

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

        //extracting image URL
        Picasso.with(this).load(IMAGE_URL + movie.getBackdropURL()).into(backDrop);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
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
                            movieDao.delete(movie);
                        }
                    });
                    fab.setImageResource(R.drawable.ic_fav_icon);
                    Snackbar snackView =  Snackbar.make(view,R.string.fab_removed, Snackbar.LENGTH_LONG);
                    snackView.getView().setBackgroundColor(getResources().getColor(R.color.material_light_black));
                    snackView.show();
                }
            });
        }else{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            movieDao.insert(movie);
                        }
                    });
                    fab.setImageResource(R.drawable.ic_delete_black_24dp);
                    Snackbar snackView =  Snackbar.make(view,R.string.fab_add , Snackbar.LENGTH_LONG);
                    snackView.getView().setBackgroundColor(getResources().getColor(R.color.material_light_black));
                    snackView.show();
                }
            });
        }
    }
}

