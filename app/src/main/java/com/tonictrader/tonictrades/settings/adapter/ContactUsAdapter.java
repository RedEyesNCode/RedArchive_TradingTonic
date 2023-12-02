package com.tonictrader.tonictrades.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonictrader.tonictrades.databinding.ContactusListBinding;
import com.tonictrader.tonictrades.settings.model.ContactUsData;

import java.util.ArrayList;

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.MyViewHolder> {
    private ArrayList<ContactUsData> contactUsData;
    private Context context;
    private onClicked onClicked;

    public ContactUsAdapter(ArrayList<ContactUsData> contactUsData, Context context, onClicked onClicked) {
        this.contactUsData = contactUsData;
        this.context = context;
        this.onClicked = onClicked;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ContactusListBinding binding = ContactusListBinding.inflate(LayoutInflater.from(context),parent,false);


        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactUsData contactUsDataReal = contactUsData.get(position);
        holder.binding.tvContactName.setText(contactUsDataReal.getSocialName());
        //holder.binding.ivContact.setImageResource(contactUsDataReal.getImagePath());
        holder.binding.shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked.onContactClick(position,contactUsDataReal.getSocialName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactUsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        ContactusListBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ContactusListBinding.bind(itemView);
        }
    }

    public interface onClicked{
        void onContactClick(int position, String contactName);
    }

}
