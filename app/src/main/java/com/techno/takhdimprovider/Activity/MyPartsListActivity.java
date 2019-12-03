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
import com.techno.takhdimprovider.Adapter.MyPartsListAdapter;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Response.CategoryListResponse;
import com.techno.takhdimprovider.Response.MyPartsResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdimprovider.App.MySharedPref.getData;

public class MyPartsListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private RecyclerView Rvmypart;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_parts_list);
        findID();
        user_id = getData(MyPartsListActivity.this, "user_id", "");
        img_back.setOnClickListener(this);
        getmyParts();
    }

    private void findID() {
        img_back = findViewById(R.id.img_back);
        Rvmypart = findViewById(R.id.Rvmypart);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }


    private void getmyParts() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_my_parts(user_id);
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
                            MyPartsResponse requestListResponse = gson.fromJson(responseData, MyPartsResponse.class);

                            Rvmypart.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyPartsListActivity.this);
                            Rvmypart.setLayoutManager(layoutManager);
                            Rvmypart.setItemAnimator(new DefaultItemAnimator());
                            MyPartsListAdapter adapter = new MyPartsListAdapter(MyPartsListActivity.this, requestListResponse.getResult());
                            Rvmypart.setAdapter(adapter);

                        } else {
                        //    Toast.makeText(MyPartsListActivity.this, "", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyPartsListActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
