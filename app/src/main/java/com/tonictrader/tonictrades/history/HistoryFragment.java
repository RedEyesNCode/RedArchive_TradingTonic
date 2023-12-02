package com.tonictrader.tonictrades.history;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.admin.model.TradeHistoryModel;
import com.tonictrader.tonictrades.base.BaseFragment;
import com.tonictrader.tonictrades.callback.TradeHistoryListener;
import com.tonictrader.tonictrades.databinding.BottomSheetTradeInfoBinding;
import com.tonictrader.tonictrades.databinding.FragmentHistoryBinding;
import com.tonictrader.tonictrades.history.adapter.HistoryAdapter;
import com.tonictrader.tonictrades.history.historyRepository.HistoryRepository;
import com.tonictrader.tonictrades.utils.AppUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends BaseFragment implements HistoryAdapter.onClick {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Context context;
    private FragmentHistoryBinding binding;
    private HistoryRepository historyRepository;
    private ArrayList<TradeHistoryModel.Data> historyData = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;

    @Override
    public void onTradeInfoClick(TradeHistoryModel.Data data) {
        if(data!=null){
            showBottomSheetTradeInfo(data);
        }else{

            showSnackBar(binding.getRoot(),context,"Error getting trade information.");

        }

    }

    private void showBottomSheetTradeInfo(TradeHistoryModel.Data data) {
        BottomSheetTradeInfoBinding bindingBottomSheet = BottomSheetTradeInfoBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(bindingBottomSheet.getRoot());
        bindingBottomSheet.closeBottomSheet.setOnClickListener(v->{
            bottomSheetDialog.dismiss();
        });


        bindingBottomSheet.tvTradeCategory.setText(AppUtils.checkIfNullTextView(data.getTitle()+" "+data.getCategory()));
        bindingBottomSheet.btnStrikePrice.setText(AppUtils.checkIfNullTextView(data.getStrikePrice()));
        bindingBottomSheet.btnStopLoss.setText(AppUtils.checkIfNullTextView(data.getStopLoss()));
        bindingBottomSheet.btnTarget.setText(AppUtils.checkIfNullTextView(data.getTarget()));
        bindingBottomSheet.tradeMessage.setText(data.getTradeMessage());
        bottomSheetDialog.show();
        Glide.with(context).load(data.getTradeImage()).placeholder(ContextCompat.getDrawable(context,R.drawable.feed_image)).into(bindingBottomSheet.ivTradeImage);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    public HistoryFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater,container,false);

        getHistory();
        return  binding.getRoot();
    }
    private void getHistory(){


        historyRepository = new HistoryRepository();
        showLoader();
        TradeHistoryListener tradeHistoryListener = new TradeHistoryListener() {
            @Override
            public void onSuccessResponse(TradeHistoryModel tradeHistoryModel) {

                hideLoader();
                if(tradeHistoryModel!=null){
                    if(tradeHistoryModel.getData().size()!=0){
                        setHistoryAdapter(tradeHistoryModel.getData());
                        binding.recvHistoryTrade.setVisibility(View.VISIBLE);
                        binding.noHistoryLayout.setVisibility(View.GONE);

                    }else {
                        binding.recvHistoryTrade.setVisibility(View.GONE);
                        binding.noHistoryLayout.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onError(String error) {

                hideLoader();
            }
        };

        historyRepository.getHistory(context,tradeHistoryListener);



    }
    private void setHistoryAdapter(ArrayList<TradeHistoryModel.Data> data){
        historyData.addAll(data);

        binding.recvHistoryTrade.setAdapter(new HistoryAdapter(context,historyData,this));
        binding.recvHistoryTrade.setLayoutManager(new LinearLayoutManager(context));
        binding.recvHistoryTrade.scrollToPosition(data.size()-1);
        binding.recvHistoryTrade.scrollToPosition(data.size()-1);




    }
}