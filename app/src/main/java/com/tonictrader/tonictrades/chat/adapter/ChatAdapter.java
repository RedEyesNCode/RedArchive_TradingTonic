package com.tonictrader.tonictrades.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.chat.model.ChatMessage;
import com.tonictrader.tonictrades.databinding.IncomingMessageLayoutBinding;
import com.tonictrader.tonictrades.databinding.OutgoingMessageLayoutBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {


    private Context context;
    private List<ChatMessage> chatMessages;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            IncomingMessageLayoutBinding binding = IncomingMessageLayoutBinding.inflate(LayoutInflater.from(context),parent,false);
            return new ReceivedMessageHolder(binding.getRoot());


        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            OutgoingMessageLayoutBinding binding = OutgoingMessageLayoutBinding.inflate(LayoutInflater.from(context),parent,false);
            return new OutgoingMessageHolder(binding.getRoot());




        }

        return null;



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((ReceivedMessageHolder) holder).bindInGoing(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((OutgoingMessageHolder) holder).bindOutGoing(message);
        }
    } @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage =  chatMessages.get(position);

        if (chatMessage.getMessageUser().equals("CUSTOMER")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder{

        private IncomingMessageLayoutBinding binding;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            binding = IncomingMessageLayoutBinding.bind(itemView);

        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder{


        IncomingMessageLayoutBinding incomingMessageBinding;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            incomingMessageBinding = IncomingMessageLayoutBinding.bind(itemView);
        }

        public void bindInGoing(ChatMessage chatMessage){
            if(URLUtil.isValidUrl(chatMessage.getMessageText())){
                incomingMessageBinding.tvMessage.setVisibility(View.GONE);
                incomingMessageBinding.chatMedia.setVisibility(View.VISIBLE);
                Glide.with(context).load(chatMessage.getMessageText()).placeholder(ContextCompat.getDrawable(context, R.drawable.feed_image)).into(incomingMessageBinding.chatMedia);

            }else{
                incomingMessageBinding.tvMessage.setVisibility(View.VISIBLE);
                incomingMessageBinding.chatMedia.setVisibility(View.GONE);
                incomingMessageBinding.tvMessage.setText(chatMessage.getMessageText());
            }

        }

    }
    public class OutgoingMessageHolder extends RecyclerView.ViewHolder{
        OutgoingMessageLayoutBinding outgoingMessageBinding;

        public OutgoingMessageHolder(@NonNull View itemView ) {
            super(itemView);
            outgoingMessageBinding = OutgoingMessageLayoutBinding.bind(itemView);
        }

        public void bindOutGoing(ChatMessage chatMessage){
            if(URLUtil.isValidUrl(chatMessage.getMessageText())){
                outgoingMessageBinding.tvMessage.setVisibility(View.GONE);
                outgoingMessageBinding.chatMedia.setVisibility(View.VISIBLE);
                Glide.with(context).load(chatMessage.getMessageText()).placeholder(ContextCompat.getDrawable(context, R.drawable.feed_image)).into(outgoingMessageBinding.chatMedia);

            }else{
                outgoingMessageBinding.tvMessage.setVisibility(View.VISIBLE);
                outgoingMessageBinding.chatMedia.setVisibility(View.GONE);
                outgoingMessageBinding.tvMessage.setText(chatMessage.getMessageText());
            }

        }

    }
}
