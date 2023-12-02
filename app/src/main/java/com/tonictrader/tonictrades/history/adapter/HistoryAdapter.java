package com.tonictrader.tonictrades.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tonictrader.tonictrades.admin.model.TradeHistoryModel;
import com.tonictrader.tonictrades.databinding.HistoryListBinding;
import com.tonictrader.tonictrades.history.model.HistoryModel;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.myViewHolder> {
    private Context context;
    private ArrayList<TradeHistoryModel.Data> historyModels;

    private onClick onClick;

    public HistoryAdapter(Context context, ArrayList<TradeHistoryModel.Data> historyModels, onClick onClick) {
        this.context = context;
        this.historyModels = historyModels;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryListBinding binding = HistoryListBinding.inflate(LayoutInflater.from(context),parent,false);

        return new myViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        TradeHistoryModel.Data data = historyModels.get(position);

        String title, category, strikePrice, Target, Stoploss;

        title = data.getTitle();
        category = data.getCategory();
        strikePrice = data.getStrikePrice();
        Target = data.getTarget();
        Stoploss = data.getStopLoss();

        if(AppUtils.isNullEmpty(title)){
            holder.binding.textTitle.setText("");

        }else {
            holder.binding.textTitle.setText(title);
        }
        if(AppUtils.isNullEmpty(category)){
            holder.binding.textCategory.setText("");

        }else {
            holder.binding.textCategory.setText(category);
        }
        if(AppUtils.isNullEmpty(strikePrice)){
            holder.binding.StrikePrice.setText("");
        }else {
            holder.binding.StrikePrice.setText(strikePrice);
        }
        if(AppUtils.isNullEmpty(Target)){
            holder.binding.Target.setText("");
        }else {
            holder.binding.Target.setText(Target);
        }
        if(AppUtils.isNullEmpty(Stoploss)){
            holder.binding.Stoploss.setText("");
        }else {
            holder.binding.Stoploss.setText(Stoploss);
        }
        if(AppUtils.isNullEmpty(data.getTradeDate())){

            holder.binding.tvTradeDate.setText("");
        }else {
            holder.binding.tvTradeDate.setText(data.getTradeDate());
        }
        if(AppUtils.isNullEmpty(data.getTradeMessage())){
           holder.binding.tradeMessageLayout.setVisibility(View.GONE);
        }
        holder.binding.tradeMessageLayout.setOnClickListener(v->{
            if (!AppUtils.isNullEmpty(data.getTradeMessage())){
                onClick.onTradeInfoClick(data);
            }else{
                onClick.onTradeInfoClick(null);
            }

        });





    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{


        HistoryListBinding binding;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = HistoryListBinding.bind(itemView);
        }
    }

    public interface onClick{
//        void onTradeInfoClick(String tradeMessage,String tradeUrl);

        void onTradeInfoClick(TradeHistoryModel.Data data);
    }

}
