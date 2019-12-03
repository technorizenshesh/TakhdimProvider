package com.techno.takhdimprovider.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdimprovider.Adapter.AllOrderAdapter;
import com.techno.takhdimprovider.Adapter.CompleteOrderAdapter;
import com.techno.takhdimprovider.Adapter.ProgressOrderAdapter;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Response.OrderResponse;

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
public class CompleteOrderFragment extends Fragment {

    private View view;
    private RecyclerView RV_complete;
    private LinearLayout lay_notfounfd;
    private String user_id;

    public CompleteOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complete_order, container, false);

        findId();
        user_id = getData(getActivity(), "user_id", "");
        getOrderlist();
        return view;
    }


    private void findId() {
        RV_complete = view.findViewById(R.id.RV_complete);
        lay_notfounfd = view.findViewById(R.id.lay_notfounfd);
    }


    private void getOrderlist() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_Provider_request(user_id, "Accept");
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
                            if (requestListResponse.getResult()!=null) {
                                RV_complete.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                RV_complete.setLayoutManager(layoutManager);
                                RV_complete.setItemAnimator(new DefaultItemAnimator());
                                CompleteOrderAdapter adapter = new CompleteOrderAdapter(getActivity(), requestListResponse.getResult());
                                RV_complete.setAdapter(adapter);
                            }else {
                                Snackbar snackbar = Snackbar.make(view, "Not Found", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                lay_notfounfd.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(view, "Not Found", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            lay_notfounfd.setVisibility(View.VISIBLE);
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
}
