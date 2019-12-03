package com.techno.takhdimprovider.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdimprovider.Adapter.CategoryListAdapter;
import com.techno.takhdimprovider.Adapter.ServiceListAdapter;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Response.CategoryListResponse;
import com.techno.takhdimprovider.Response.ServiceListResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private RecyclerView RV_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        SignUpActivity.cat_id = getIntent().getExtras().get("cat_id").toString();
        SignUpActivity.cat_name = getIntent().getExtras().get("cat_name").toString();
        findId();
        img_back.setOnClickListener(this);
        getcategory_list();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        RV_service = findViewById(R.id.RV_service);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }


    private void getcategory_list() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ServiceListActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().services_list(SignUpActivity.cat_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {
                            Gson gson = new Gson();
                            ServiceListResponse requestListResponse = gson.fromJson(responseData, ServiceListResponse.class);
                            RV_service.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ServiceListActivity.this);
                            RV_service.setLayoutManager(layoutManager);
                            RV_service.setItemAnimator(new DefaultItemAnimator());
                            ServiceListAdapter adapter = new ServiceListAdapter(ServiceListActivity.this, requestListResponse.getResult());
                            RV_service.setAdapter(adapter);
                        } else {
                            Toast.makeText(ServiceListActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
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
                progressDialog.dismiss();
                Toast.makeText(ServiceListActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
