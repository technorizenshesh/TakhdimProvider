package com.techno.takhdimprovider.NewDesign;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.techno.takhdimprovider.Activity.AccountActivity;
import com.techno.takhdimprovider.Activity.ChatActivity;
import com.techno.takhdimprovider.Activity.HelpActivity;
import com.techno.takhdimprovider.Activity.LoginActivity;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.databinding.FragmentProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.techno.takhdimprovider.App.MySharedPref.getData;
import static com.techno.takhdimprovider.App.MySharedPref.saveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


    private FragmentProfileBinding binding;
    private String user_id;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        binding.tvEditProfile.setOnClickListener(v->startActivity(new Intent(getActivity(), AccountActivity.class)));
        binding.layChatWithUs.setOnClickListener(v->startActivity(new Intent(getActivity(), ChatActivity.class)));
        binding.layAboutUs.setOnClickListener(v->startActivity(new Intent(getActivity(), HelpActivity.class)));
        binding.layEmail.setOnClickListener(v->SendEmail());
        binding.layRateUs.setOnClickListener(v->launchMarket());
        binding.imgLogout.setOnClickListener(v->Logout());
        user_id = getData(getContext(), "user_id", "");
        get_profile();

    }
    private void get_profile() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_profile(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {
                            object = object.getJSONObject("result");
                            Picasso.with(getActivity()).load(object.getString("image")).error(R.drawable.user_new).into(binding.userImage);
                            binding.tvUserName.setText(object.getString("company_name"));
//                            ed_email.setText(object.getString("email"));
                            binding.tvMobileNo.setText("Mobile Number :"+object.getString("mobile"));
//                            ed_address.setText(object.getString("address"));
                        } else {
                            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } else ;
                    // AppConfig.showToast("server error");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void SendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","support@takhdim.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
    void Logout(){
        saveData(getContext(), "ldata", null);
        saveData(getContext(), "user_id", null);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
