package com.tonictrader.tonictrades.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.base.BaseFragment;
import com.tonictrader.tonictrades.callback.NewsListener;
import com.tonictrader.tonictrades.databinding.FragmentNewsBinding;
import com.tonictrader.tonictrades.news.adapter.NewsAdapter;
import com.tonictrader.tonictrades.news.adapter.NewsTagAdapter;
import com.tonictrader.tonictrades.news.model.NewsData;
import com.tonictrader.tonictrades.news.repository.NewsRepository;
import com.tonictrader.tonictrades.news.viewmodel.NewsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment implements NewsAdapter.onClick {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentNewsBinding binding;
    private Context context;
    private FragmentNewsBinding dataBinding;
    private NewsViewModel newsViewModel;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onNewsReadMore(int position, String newsUrl) {
        //Pass the Intent to Open the URL in Browser default of the app.
        try {
            String url = newsUrl;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }catch (Exception e){

            showToast("Oops Something Went Wrong.");
        }



    }

    public NewsFragment() {}
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        dataBinding.setViewmodel(newsViewModel);
        newsViewModel.init(context);
        //initView();
        initViewRepository();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater,container,false);
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false);

        initViewRepository();


        return binding.getRoot();
    }
    private void initViewRepository(){
        showLoader();
        NewsListener newsListener = new NewsListener() {
            @Override
            public void onSuccessResponse(NewsData newsData) {
                hideLoader();
                if(newsData.getStories().size()>0){
                    setNewsAdapter(newsData);

                }else {
                    hideLoader();
                    binding.recvNews.setVisibility(View.GONE);
                    showToast("Could not get Any News at the Moment.");
                }
            }

            @Override
            public void onError(String error) {
                hideLoader();
                showToast(error);

            }
        };
        NewsRepository newsRepository = new NewsRepository();
        newsRepository.getNews(context,"en",20,newsListener);






    }
    private void initView(){
        newsViewModel.getIsFailed().observe(this, s -> {
            hideLoader();
            showToast(s);
        });
        newsViewModel.getIsConnecting().observe(this, aBoolean -> {
            showLoader();
        });
        newsViewModel.getNews("en",5);
        newsViewModel.getNewsResponse().observe(this, new Observer<NewsData>() {
            @Override
            public void onChanged(NewsData newsData) {
                hideLoader();
                if(newsData.getStories().size()>0){
                    setNewsAdapter(newsData);

                }else {
                    hideLoader();
                    binding.recvNews.setVisibility(View.GONE);
                    showToast("Could not get Any News at the Moment.");
                }
            }
        });



    }
    private void setNewsAdapter(NewsData newsData){
        binding.recvNews.setAdapter(new NewsAdapter(context,this::onNewsReadMore,newsData.getStories()));
        binding.recvNews.setLayoutManager(new LinearLayoutManager(context));




    }

}