package com.techno.takhdimprovider.Activity;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthProvider;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdimprovider.App.MySharedPref.getData;

public class AddPartDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private String part_id, user_id, cname, warrenty, price, details;
    private ImageView img_back;
    private EditText edt_cname, edt_warrenty, edt_price, edt_details;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part_details);
        findId();
        user_id = getData(AddPartDetailsActivity.this, "user_id", "");
        part_id = getIntent().getExtras().getString("part_id");
        img_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        edt_cname = findViewById(R.id.edt_cname);
        btn_save = findViewById(R.id.btn_save);

        edt_cname = findViewById(R.id.edt_cname);
        edt_warrenty = findViewById(R.id.edt_warrenty);
        edt_price = findViewById(R.id.edt_price);
        edt_details = findViewById(R.id.edt_details);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == btn_save) {
            vailidate();
        }
    }

    private void vailidate() {
        cname = edt_cname.getText().toString();
        warrenty = edt_warrenty.getText().toString();
        price = edt_price.getText().toString();
        details = edt_details.getText().toString();

        if (cname.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Company Name", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (warrenty.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Warrenty", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (price.equalsIgnoreCase("")) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter Price", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            add_partsCall();
        }
    }



    private void add_partsCall() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(AddPartDetailsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().add_parts(user_id, part_id, cname, warrenty, price, details);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("signup" + object);
                        if (object.getString("status").equals("1")) {
                            Toast.makeText(AddPartDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } else {
                        Log.e("Error", "" + response);
                        Log.e("Error", "" + response.body());
                        Log.e("Error", "" + response.errorBody());
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Server Error", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Check Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }


}
