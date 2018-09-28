package com.example.vachan.a24frames;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.vachan.a24frames.Adapters.MovieFragmentAdapter;
import com.example.vachan.a24frames.database.AppDatabase;
import com.example.vachan.a24frames.database.MovieDao;
import com.example.vachan.a24frames.database.Movies;
import com.example.vachan.a24frames.listeners.MyUndoListener;
import com.squareup.picasso.Picasso;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        final Movies movie = bd.getParcelable("Movie");

        toolbar.setTitle(movie.getTitle());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentPagerAdapter = new MovieFragmentAdapter(getSupportFragmentManager(), movie);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        final MovieDao movieDao = AppDatabase.getInstance(getApplicationContext()).movieDao();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //extracting image URL

        Picasso.with(this).load("https://image.tmdb.org/t/p/w780" + movie.getBackdropURL()).into(backDrop);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieDao.insert(movie);
               Snackbar snackView =  Snackbar.make(view, "Movie Added to Favourites", Snackbar.LENGTH_LONG);
                snackView.getView().setBackgroundColor(getResources().getColor(R.color.material_light_black));
                snackView.setAction("Undo", new MyUndoListener()).show();
                       // .setAction("Undo", new MyUndoListener()).show();
            }
        });

    }
}

