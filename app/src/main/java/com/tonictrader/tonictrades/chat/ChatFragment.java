package com.tonictrader.tonictrades.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.base.BaseFragment;
import com.tonictrader.tonictrades.chat.adapter.ChatAdapter;
import com.tonictrader.tonictrades.chat.model.ChatMessage;
import com.tonictrader.tonictrades.databinding.FragmentChatBinding;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ChatAdapter mAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private FragmentChatBinding binding;
    private DatabaseReference mFirebaseRef;
    private ChatViewmodel chatViewmodel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        chatViewmodel = new ViewModelProvider(this).get(ChatViewmodel.class);
        chatViewmodel.init(context);
        binding.setViewmodel(chatViewmodel);
        attachObservers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        // Inflate the layout for this fragment
        initChatAdapter();
        initClicks();
        initFirebaseChat();

        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK ) {
            try {
                chatViewmodel.uploadImageToEC2(AppUtils.getFile(context,data.getData()));

            }catch (IOException e){
                e.printStackTrace();
                showToast("Parse Error");
            }

        }else if (resultCode == ImagePicker.RESULT_ERROR) {
            showToast("Error Please pick the image again");
        } else {
            showToast("No Image was selected.");
        }
    }

    private void attachObservers(){
        chatViewmodel.getIsConnecting().observe(getViewLifecycleOwner(), aBoolean -> {
            showLoader();
        });
        chatViewmodel.getIsFailed().observe(getViewLifecycleOwner(), s -> {
            hideLoader();
//            showToast(s);
        });
        chatViewmodel.observeImageUploadResponse().observe(getViewLifecycleOwner(), commonStatusMessageResponse -> {
            hideLoader();
            showToast(commonStatusMessageResponse.getMessage());
            binding.edtMessage.setText(commonStatusMessageResponse.getMessage());
            if(!binding.edtMessage.getText().toString().isEmpty()){
                binding.btnSend.performClick();
            }
        });
    }
    private void initClicks() {

        binding.btnSend.setOnClickListener(v->{
            hideLoader();
            if(!binding.edtMessage.getText().toString().isEmpty()){
                mFirebaseRef.push().setValue(new ChatMessage(binding.edtMessage.getText().toString(),"CUSTOMER"));
                binding.edtMessage.setText("");
            }else{
                showToast("Please enter a message first.");
            }


        });

        binding.ivSendMedia.setOnClickListener(v->{
            pickImage();
        });



    }
    private void pickImage(){
        ImagePicker.with(this)

                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();


    }
    private void initFirebaseChat() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mFirebaseRef = database.getReference("CHAT");
//        mFirebaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    ChatMessage university = postSnapshot.getValue(ChatMessage.class);
//
//                    chatMessages.add(university);
//                    mAdapter.notifyDataSetChanged();
//
//                    // here you can access to name property like university.name
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                showToast("The read failed: " + databaseError.getMessage());
//            }
//        });
        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null && snapshot.getValue() != null) {
                    try {
                        ChatMessage model = snapshot.getValue(ChatMessage.class);
                        chatMessages.add(model);
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception ex) {
                        showToast(ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initChatAdapter(){
        mAdapter = new ChatAdapter(getContext(),chatMessages);

        binding.recvChat.setAdapter(mAdapter);
        binding.recvChat.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}