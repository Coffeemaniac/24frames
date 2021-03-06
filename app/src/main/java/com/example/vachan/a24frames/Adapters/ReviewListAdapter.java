package com.example.vachan.a24frames.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vachan.a24frames.R;
import com.example.vachan.a24frames.model.Review;

import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.MyViewHolder> {
    private Context mContext ;
    private ArrayList<Review> mData;

    public ReviewListAdapter(Context context, ArrayList<Review> data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public ReviewListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.reviewcardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.MyViewHolder holder, int position) {
        holder.authorTv.setText(mData.get(position).getName());
        holder.infoTv.setText(mData.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView infoTv;
        TextView authorTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            infoTv = (TextView) itemView.findViewById(R.id.info_text);
            authorTv = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
