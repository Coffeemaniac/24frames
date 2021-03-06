package com.example.vachan.a24frames.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vachan.a24frames.MainActivity;
import com.example.vachan.a24frames.MovieDetails;
import com.example.vachan.a24frames.R;
import com.example.vachan.a24frames.database.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private ArrayList<Movies> mData ;
    private MainActivity.callBack listener;


    public static final String MOVIE_KEY = "movie";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w780";


    public RecyclerViewAdapter(Context mContext, ArrayList<Movies> mData, MainActivity.callBack listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.itemview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Picasso.with(mContext).load(IMAGE_URL + mData.get(position).getImageUrl())
                .into(holder.img_book_thumbnail);

       holder.img_book_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MovieDetails.class);
                Movies movie = mData.get(position);
                intent.putExtra(MOVIE_KEY, movie);
                listener.doSomething(intent, holder.img_book_thumbnail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_book_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.itemview);
        }
    }


}

