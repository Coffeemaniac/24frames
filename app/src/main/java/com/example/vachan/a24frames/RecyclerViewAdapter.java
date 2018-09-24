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
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private ArrayList<Movies> mData ;
    private EventListener eventListener;

    public interface EventListener {
        void movieClicked(Intent intent, ImageView imageView);
    }


    public RecyclerViewAdapter(Context mContext, ArrayList<Movies> mData, EventListener eventListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.eventListener = eventListener;
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

        Picasso.with(mContext).load(mData.get(position).getImageUrl())
                .into(holder.img_book_thumbnail);

        holder.img_book_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MovieDetails.class);
                Movies movie = mData.get(position);
                intent.putExtra("Movie", movie);

                // start the activity
                eventListener.movieClicked(intent, holder.img_book_thumbnail);


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

