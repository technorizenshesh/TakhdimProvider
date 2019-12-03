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
import com.techno.takhdimprovider.Adapter.AllOrderAdapter;
import com.techno.takhdimprovider.Adapter.CategoryListAdapter;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Response.CategoryListResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private RecyclerView RV_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        findId();
        img_back.setOnClickListener(this);
        getservice_category();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        RV_cat = findViewById(R.id.RV_cat);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }


    private void getservice_category() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CategoryListActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().category();
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
                            CategoryListResponse requestListResponse = gson.fromJson(responseData, CategoryListResponse.class);
                            RV_cat.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryListActivity.this);
                            RV_cat.setLayoutManager(layoutManager);
                            RV_cat.setItemAnimator(new DefaultItemAnimator());
                            CategoryListAdapter adapter = new CategoryListAdapter(CategoryListActivity.this, requestListResponse.getResult());
                            RV_cat.setAdapter(adapter);
                        } else {
                            Toast.makeText(CategoryListActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CategoryListActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
