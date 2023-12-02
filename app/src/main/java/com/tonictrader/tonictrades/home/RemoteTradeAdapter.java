package com.tonictrader.tonictrades.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonictrader.tonictrades.databinding.RemoteTradeListBinding;
import com.tonictrader.tonictrades.home.model.RemoteTradesModel;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.List;

public class RemoteTradeAdapter extends RecyclerView.Adapter<RemoteTradeAdapter.Myviewholder> {

    private Context context;
    private List<RemoteTradesModel> remoteTradesModels;
    private onClicked onClicked;

    public RemoteTradeAdapter(Context context, List<RemoteTradesModel> remoteTradesModels, RemoteTradeAdapter.onClicked onClicked) {
        this.context = context;
        this.remoteTradesModels = remoteTradesModels;
        this.onClicked = onClicked;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RemoteTradeListBinding binding = RemoteTradeListBinding.inflate(LayoutInflater.from(context));
        return new Myviewholder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        RemoteTradesModel remoteTradesModel = remoteTradesModels.get(position);


        holder.binding.textTitle.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getTitle()));

        holder.binding.mainCard.setOnClickListener(v->{
            onClicked.onRemoteTradeInfoClick(remoteTradesModel);
        });
        holder.binding.textCategory.setText(AppUtils.checkIfNullTextView(remoteTradesModel.getCategory()));



    }

    @Override
    public int getItemCount() {
        return remoteTradesModels.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{

        RemoteTradeListBinding binding;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            binding = RemoteTradeListBinding.bind(itemView);
        }
    }

    public interface onClicked{
        void onRemoteTradeInfoClick(RemoteTradesModel remoteTradesModel);

    }

}
