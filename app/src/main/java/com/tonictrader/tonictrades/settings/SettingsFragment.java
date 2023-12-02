package com.tonictrader.tonictrades.settings;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonictrader.tonictrades.sharedPreferences.AppSession;
import com.tonictrader.tonictrades.BuildConfig;
import com.tonictrader.tonictrades.R;
import com.tonictrader.tonictrades.auth.login.LoginActivity;
import com.tonictrader.tonictrades.base.BaseFragment;
import com.tonictrader.tonictrades.databinding.FragmentSettingsBinding;
import com.tonictrader.tonictrades.settings.adapter.ContactUsAdapter;
import com.tonictrader.tonictrades.settings.model.ContactUsData;
import com.tonictrader.tonictrades.sharedPreferences.Constant;
import com.tonictrader.tonictrades.utils.CommonDialog;
import com.tonictrader.tonictrades.utils.CommonInteractionDialog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends BaseFragment implements CommonInteractionDialog.onClicked, ContactUsAdapter.onClicked {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentSettingsBinding binding;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private CommonInteractionDialog commonInteractionDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onContactClick(int position, String contactName) {
        if(position==0){
            //Instagram
            Uri uri = Uri.parse("https://www.instagram.com/trading_tonics/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/trading_tonics/")));
            }


        }else if(position==1){
            //Whatsapp
            String url = "https://api.whatsapp.com/send?phone="+"+91 6261319133";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }else if(position==2){
            //Youtube
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "-eo3Wbs4FmM&list=RD-eo3Wbs4FmM"));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + "-eo3Wbs4FmM&list=RD-eo3Wbs4FmM"));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }


        }else if(position==3){
            //Telegram
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://telegram.me/RedEyesNCode"));
            final String appName = "org.telegram.messenger";
            try {
                if (isAppAvailable(context, appName)){
                    i.setPackage(appName);
                }

            } catch (Exception e) {}
            startActivity(i);

        }else {
            showToast("Something Went Wrong !.");
        }

    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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

        binding = FragmentSettingsBinding.inflate(inflater,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        showLoader();
        databaseReference = firebaseDatabase.getReference("LiveMessage");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hideLoader();
                HashMap<String, String> hashMap = (HashMap<String, String>) snapshot.getValue();
                binding.serverStatus.setText("Server Status : "+hashMap.get("ServerStatus"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                hideLoader();
            }
        });


        setContactAdapter();
         commonInteractionDialog = new CommonInteractionDialog(context,this,container);
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
        binding.resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if(email.isEmpty()){
                    CommonDialog commonDialog = new CommonDialog(context,container);
                    commonDialog.showDialog("Cannot Get Email address for Verification \n Try again later.");

                }else {
                    forgotPassword(email,container);
                }
            }
        });
        binding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.recvContactUs.getVisibility()==View.VISIBLE){

                    binding.downArrow.setRotation(360f);
                    binding.recvContactUs.setVisibility(View.GONE);

                }else {
                    binding.recvContactUs.setVisibility(View.VISIBLE);
                    binding.downArrow.setRotation(180f);

                }
            }
        });
        binding.shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        String url = "https://tonictrading.wordpress.com/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                   /* Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose one"));*/
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });


        return binding.getRoot();

    }
    private  void forgotPassword(String email,ViewGroup viewGroup){
        showLoader();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    hideLoader();
                    CommonDialog commonDialog = new CommonDialog(context,viewGroup);
                    commonDialog.showDialog("Password Reset Link has been \n sent to "+email);

                }else {
                    hideLoader();
                    showToast("Error Sending Reset Password Link to Email Please Try Again !");
                }
            }
        });






    }
    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }


    @Override
    public void onYesClicked() {
        //Logout user.
        FirebaseAuth.getInstance().signOut();
        Intent backLogin = new Intent(context, LoginActivity.class);
        AppSession.getInstance(context).setValue(Constant.IS_USER_LOGIN,"no");
        backLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backLogin);
    }

    @Override
    public void onNoClicked() {

    }
    private void setContactAdapter(){
        ArrayList<ContactUsData> contactUsDataArrayList = new ArrayList<>();
        contactUsDataArrayList.add(new ContactUsData("Instagram", R.drawable.ic_insta));
        contactUsDataArrayList.add(new ContactUsData("Whatsapp",R.drawable.ic_whatsapp));
        contactUsDataArrayList.add(new ContactUsData("Youtube",R.drawable.youtube));
        contactUsDataArrayList.add(new ContactUsData("Telegram",R.drawable.telegram));
        binding.recvContactUs.setAdapter(new ContactUsAdapter(contactUsDataArrayList,context,this::onContactClick));
        binding.recvContactUs.setLayoutManager(new LinearLayoutManager(context));
    }
    private void showLogoutDialog(){
        commonInteractionDialog.showCommonInteractiveDialgog("Are you sure \n you want to logout ?");
    }
}