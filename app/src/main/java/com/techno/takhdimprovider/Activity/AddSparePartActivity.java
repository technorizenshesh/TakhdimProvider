package com.techno.takhdimprovider.Activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdimprovider.Adapter.AllOrderAdapter;
import com.techno.takhdimprovider.Adapter.CategoryListPartsAdapter;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Response.CategoryListResponse;
import com.techno.takhdimprovider.Response.OrderResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSparePartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private RecyclerView Rvcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spare_part);
        findId();
        img_back.setOnClickListener(this);
        getService();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        Rvcat = findViewById(R.id.Rvcat);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }

    // AppConfig.showToast("server error");
    private void getService() {
        Call<ResponseBody> call = AppConfig.loadInterface().category();
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
                            CategoryListResponse requestListResponse = gson.fromJson(responseData, CategoryListResponse.class);
                            Rvcat.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddSparePartActivity.this);
                            Rvcat.setLayoutManager(layoutManager);
                            Rvcat.setItemAnimator(new DefaultItemAnimator());
                            CategoryListPartsAdapter adapter = new CategoryListPartsAdapter(AddSparePartActivity.this, requestListResponse.getResult());
                            Rvcat.setAdapter(adapter);
                        } else {

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
                Toast.makeText(AddSparePartActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
