package com.techno.takhdimprovider.NewDesign;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdimprovider.Activity.HomeActivity;
import com.techno.takhdimprovider.Activity.LoginActivity;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.databinding.FragmentHomeBinding;

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
public class FragmentHome extends Fragment {


    private FragmentHomeBinding binding;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        getProfile();
        BindView();
        return binding.getRoot();
    }

    private void BindView() {
        binding.cardWorkRequest.setOnClickListener(v->((HomeActivity)getActivity()).FragTrans(new FragmentRequest()));

    }

    private void getProfile() {
        String userID=getData(getActivity(), "user_id", null);
        Call<ResponseBody> call = AppConfig.loadInterface().get_profile(userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        Log.e("UserDetails","==========>"+object);
                        if (object.getString("status").equals("1")) {
                            object = object.getJSONObject("result");
                            binding.tvCompanyName.setText(object.getString("company_name"));
                            binding.tvCompanyAddress.setText(object.getString("address"));
                            binding.tvMobile.setText("Register Mobile Number : "+object.getString("mobile"));
                            Picasso.with(getActivity()).load(object.getString("image")).placeholder(R.drawable.setting_logo).into(binding.imgCompanyLogo);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
