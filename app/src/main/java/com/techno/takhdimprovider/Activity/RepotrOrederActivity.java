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

import com.squareup.picasso.Picasso;
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

public class RepotrOrederActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private EditText edt_name, edt_email, edt_desc;
    private Button btn_submit;
    private String request_id, user_id, name, email, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repotr_oreder);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        findID();
        request_id = getIntent().getExtras().getString("request_id");
        user_id = getData(RepotrOrederActivity.this, "user_id", "");
        img_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void findID() {
        img_back = findViewById(R.id.img_back);

        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_desc = findViewById(R.id.edt_desc);
        btn_submit = findViewById(R.id.btn_submit);
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
        name = edt_name.getText().toString();
        email = edt_email.getText().toString();
        desc = edt_desc.getText().toString();
        if (name.equalsIgnoreCase("")) {
            edt_name.setError("Enter Name");
        } else if (email.equalsIgnoreCase("")) {
            edt_email.setError("Enter email");
        } else {
            addReportCall();
        }
    }


    private void addReportCall() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(RepotrOrederActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().add_report(user_id, request_id, name, email, desc);
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
                            Toast.makeText(RepotrOrederActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RepotrOrederActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RepotrOrederActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
