package com.tonictrader.tonictrades.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonictrader.tonictrades.databinding.NewsTagsBinding;

import java.util.ArrayList;

public class NewsTagAdapter extends RecyclerView.Adapter<NewsTagAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> strings;

    public NewsTagAdapter( Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsTagsBinding binding = NewsTagsBinding.inflate(LayoutInflater.from(context),parent,false);

        return  new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvTag.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        NewsTagsBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = NewsTagsBinding.bind(itemView);
        }
    }

}
