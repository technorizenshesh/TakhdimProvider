package com.techno.takhdimprovider.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techno.takhdimprovider.Adapter.CategoryListPartsAdapter;
import com.techno.takhdimprovider.Adapter.SelectListPartsAdapter;
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

public class SelectPartsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView Rvparts;
    private ImageView img_back;
    private String cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parts);
        findId();
        cat_id = getIntent().getExtras().getString("cat_id");
        img_back.setOnClickListener(this);
        getparts();
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        Rvparts = findViewById(R.id.Rvparts);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }


    // AppConfig.showToast("server error");
    private void getparts() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_parts(cat_id);
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
                            Rvparts.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectPartsActivity.this);
                            Rvparts.setLayoutManager(layoutManager);
                            Rvparts.setItemAnimator(new DefaultItemAnimator());
                            SelectListPartsAdapter adapter = new SelectListPartsAdapter(SelectPartsActivity.this, requestListResponse.getResult());
                            Rvparts.setAdapter(adapter);
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
                Toast.makeText(SelectPartsActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
