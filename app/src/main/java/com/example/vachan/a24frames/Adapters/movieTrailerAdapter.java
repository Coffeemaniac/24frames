package com.example.vachan.a24frames.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vachan.a24frames.R;
import com.example.vachan.a24frames.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class movieTrailerAdapter extends RecyclerView.Adapter<movieTrailerAdapter.MyViewHolder> {

    private Context mContext ;
    private ArrayList<Trailer> mData ;

    public movieTrailerAdapter(Context mContext, ArrayList<Trailer> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public movieTrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.movie_trailer,parent, false);
        return new movieTrailerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieTrailerAdapter.MyViewHolder holder, final int position) {
        Picasso.with(mContext).load(mData.get(position).getThumbnail()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getVideoKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.play_button)
        ImageButton play;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
