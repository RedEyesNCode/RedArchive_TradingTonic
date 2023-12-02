package com.tonictrader.tonictrades.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.databinding.NewsListBinding;
import com.tonictrader.tonictrades.news.model.NewsData;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context context;
    private onClick onClick;
    private ArrayList<NewsData.Story> stories;

    public NewsAdapter(Context context, onClick onClick, ArrayList<NewsData.Story> stories) {
        this.context = context;
        this.onClick = onClick;
        this.stories = stories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsListBinding binding =  NewsListBinding.inflate(LayoutInflater.from(context),parent,false);


        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        NewsData.Story story = stories.get(position);
        ArrayList<String> newsTags =  new ArrayList<>();
        String title, description, image, SiteName;
        title = story.getTitle();
        description = story.getDescription();
        image = story.getFaviconUrl();

        SiteName = story.getSite();
        if (AppUtils.isNullEmpty(title)){

            holder.binding.newsTitle.setText("");
        }else {
            holder.binding.newsTitle.setText(title);
        }
        if(AppUtils.isNullEmpty(description)){
            holder.binding.tvNewsDescription.setText("");
        }else {

            holder.binding.tvNewsDescription.setText(description);
        }
        if(AppUtils.isNullEmpty(SiteName)){
            holder.binding.textSite.setText("");
        }else {
            holder.binding.textSite.setText(SiteName);

        }
        Glide.with(context).load(story.getFaviconUrl()).placeholder(R.drawable.ic_news_mini).into(holder.binding.ivNews);
        holder.binding.ivDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.ivDropDown.setVisibility(View.GONE);
                holder.binding.ivHide.setVisibility(View.VISIBLE);
                holder.binding.moreNewsLayout.setVisibility(View.VISIBLE);
            }
        });
        holder.binding.ivHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.ivDropDown.setVisibility(View.VISIBLE);
                holder.binding.ivHide.setVisibility(View.GONE);
                holder.binding.moreNewsLayout.setVisibility(View.GONE);
            }
        });
        holder.binding.btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onNewsReadMore(position,story.getUrl());
            }
        });
        if(story.getTags().size()>0){
            newsTags.addAll(story.getTags());
            holder.binding.recvTags.setAdapter(new NewsTagAdapter(context,newsTags));
            holder.binding.recvTags.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        }





    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        NewsListBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NewsListBinding.bind(itemView);
        }
    }

    public interface onClick{
        void onNewsReadMore(int position, String newsUrl);
    }


}
