package com.techno.takhdimprovider.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdimprovider.Adapter.AllOrderAdapter;
import com.techno.takhdimprovider.Adapter.NewOrderAdapter;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.NewDesign.ActivityRequestDetails;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Response.OrderResponse;
import com.techno.takhdimprovider.Result.OrderResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdimprovider.App.MySharedPref.getData;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderFragment  extends Fragment {

    private View view;
    private RecyclerView RV_All;
    private String user_id, status;
    private LinearLayout lay_notfounfd;

    public NewOrderFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public NewOrderFragment(String status) {
        this.status = status;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_order, container, false);
        findId();
        user_id = getData(getActivity(), "user_id", "");
        Log.e("Status", "----------->" + status);
        Log.e("User_id", "---------->" + user_id);
        getOrderlist();
        return view;
    }

    private void findId() {
        RV_All = view.findViewById(R.id.RV_new);
        lay_notfounfd = view.findViewById(R.id.lay_notfounfd);
    }


    private void getOrderlist() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_Provider_request(user_id, status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {
                            Gson gson = new Gson();
                            OrderResponse requestListResponse = gson.fromJson(responseData, OrderResponse.class);
                            RV_All.setHasFixedSize(true);
                            NewOrderAdapter adapter = new NewOrderAdapter(getActivity(), requestListResponse.getResult());
                            adapter.CallBack(NewOrderFragment.this::View);
                            RV_All.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            RV_All.setAdapter(adapter);
                        }
                    }
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


    public void View(OrderResult result) {
        Intent intent = new Intent(getActivity(), ActivityRequestDetails.class);
        intent.putExtra("id", result.getId());
        startActivity(intent);
    }
}
