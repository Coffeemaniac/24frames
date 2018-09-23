package com.example.vachan.a24frames;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Movies> mData ;


    public RecyclerViewAdapter(Context mContext, List<Movies> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.itemview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.with(mContext).load(mData.get(position).getImageUrl())
                .into(holder.img_book_thumbnail);

        holder.img_book_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(mContext, MovieDetails.class);

                //TODO: Create a parcelable
                // passing data to the book activity
                Movies movie = mData.get(position);
                intent.putExtra("Thumbnail", movie.getImageUrl());
                intent.putExtra("backdrop", movie.getBackdropURL());

                intent.putExtra("title", movie.getTitle());
                intent.putExtra("rating", movie.getRating());
                intent.putExtra("release", movie.getRelease_date());
                intent.putExtra("plot", movie.getPlot());
                intent.putExtra("id", movie.getId());
                // start the activity
                mContext.startActivity(intent);

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

