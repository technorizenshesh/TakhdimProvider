package com.techno.takhdimprovider.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdimprovider.App.MySharedPref.getData;

public class MakeOfferActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private String request_id, user_id, cost, time, desc;
    private EditText edt_cost, edt_time, edt_desc;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        findId();
        request_id = getIntent().getExtras().getString("request_id");
        user_id = getData(MakeOfferActivity.this, "user_id", "");
        img_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        btn_submit = findViewById(R.id.btn_submit);

        edt_cost = findViewById(R.id.edt_cost);
        edt_time = findViewById(R.id.edt_time);
        edt_desc = findViewById(R.id.edt_desc);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == btn_submit) {
            vailidate();
        }
    }

    private void vailidate() {
        cost = edt_cost.getText().toString();
        time = edt_time.getText().toString();
        desc = edt_time.getText().toString();

        if (cost.equalsIgnoreCase("")) {
            edt_cost.setError("Enter Cost");
        } else if (time.equalsIgnoreCase("")) {
            edt_time.setError("Enter Time");
        } else if (desc.equalsIgnoreCase("")) {
            edt_desc.setError("Enter Details");
        } else {
            addOfferCall();
        }
    }


    private void addOfferCall() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MakeOfferActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().add_request_offer(user_id, request_id, cost, time, desc);
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
                            Toast.makeText(MakeOfferActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(MakeOfferActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MakeOfferActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
