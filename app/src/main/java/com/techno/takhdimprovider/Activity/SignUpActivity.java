package com.techno.takhdimprovider.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.techno.takhdimprovider.App.Config;
import com.techno.takhdimprovider.App.GPSTracker;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Utils.Tools;
import com.techno.takhdimprovider.databinding.ActivitySignUpBinding;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import www.develpoeramit.mapicall.ApiCallBuilder;

public class SignUpActivity extends AppCompatActivity {
    public static String cat_id, service_id, cat_name, service_name;
    private Double lat, lng;
    private String final_path = "", final_path2 = "", regId;
    private ActivitySignUpBinding binding;
    private boolean isSelf=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BIndView();
        GPSTracker tracker = new GPSTracker(this);
        lat = tracker.getLatitude();
        lng = tracker.getLongitude();
    }

    private void BIndView() {
        String[] steps = {"Photos", "RP", "Complete"};
        binding.stepsView.setLabels(steps)
                .setBarColorIndicator(getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(getResources().getColor(R.color.white))
                .setLabelColorIndicator(getResources().getColor(R.color.white))
                .setCompletedPosition(0)
                .drawView();
        binding.stepsView2.setLabels(steps)
                .setBarColorIndicator(getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(getResources().getColor(R.color.white))
                .setLabelColorIndicator(getResources().getColor(R.color.white))
                .setCompletedPosition(0)
                .drawView();
        binding.stepsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.stepsView.getCompletedPosition()<2){
                PickImageDialog dialog = PickImageDialog.build(new PickSetup());
                dialog.setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        dialog.dismiss();
                    }
                }).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        if (final_path.equals("")) {
                            final_path = r.getPath();
                            binding.stepsView.setCompletedPosition(1).drawView();
                        }else if (final_path2.equals("")){
                            final_path2=r.getPath();
                            binding.stepsView.setCompletedPosition(2).drawView();
                        }
                    }
                }).show(SignUpActivity.this);
            }
            }
        });  binding.stepsView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.stepsView2.getCompletedPosition()<2){
                PickImageDialog dialog = PickImageDialog.build(new PickSetup());
                dialog.setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        dialog.dismiss();
                    }
                }).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        if (final_path.equals("")) {
                            final_path = r.getPath();
                            binding.stepsView2.setCompletedPosition(1).drawView();
                        }else if (final_path2.equals("")){
                            final_path2=r.getPath();
                            binding.stepsView2.setCompletedPosition(2).drawView();
                        }
                    }
                }).show(SignUpActivity.this);
            }
            }
        });

        binding.imgUser.setOnClickListener(v -> {
            binding.rlUser.setVisibility(View.VISIBLE);
            binding.rlBusiness.setVisibility(View.GONE);
            final_path="";
            final_path2="";
            isSelf=true;
        });
        binding.imgBusiness.setOnClickListener(v -> {
                binding.rlUser.setVisibility(View.GONE);
                binding.rlBusiness.setVisibility(View.VISIBLE);
            final_path="";
            final_path2="";
            isSelf=false;
        });
        binding.btnNext.setOnClickListener(v -> {
            if (ValidationFirst()) {
                regId = FirebaseInstanceId.getInstance().getToken();
                try {
                    GPSTracker gpsTracker = new GPSTracker(this);
                    lat = gpsTracker.getLatitude();
                    lng = gpsTracker.getLongitude();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                signupCall();
            }
        });
        binding.btnRegister.setOnClickListener(v -> {
            if (ValidationLast()) {
                regId = FirebaseInstanceId.getInstance().getToken();
                try {
                    GPSTracker gpsTracker = new GPSTracker(this);
                    lat = gpsTracker.getLatitude();
                    lng = gpsTracker.getLongitude();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                signupCall();
            }
        });
    }

    private boolean ValidationFirst() {
        boolean valid = false;
        if (binding.etName.getText().toString().isEmpty()) {
            binding.etName.setError("Required");
            binding.etName.requestFocus();
        } else if (binding.etRpNumber.getText().toString().isEmpty()) {
            binding.etRpNumber.setError("Required");
            binding.etRpNumber.requestFocus();
        } else if (binding.etEmailId.getText().toString().isEmpty()) {
            binding.etEmailId.setError("Required");
            binding.etEmailId.requestFocus();
        } else if (!Tools.get().isValidEmail(binding.etEmailId.getText().toString())) {
            binding.etEmailId.setError("Email Not valid");
            binding.etEmailId.requestFocus();
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean ValidationLast() {
        boolean valid = false;
        if (binding.etCompanyName.getText().toString().isEmpty()) {
            binding.etCompanyName.setError("Required");
            binding.etCompanyName.requestFocus();
        } else if (binding.etCrNumber.getText().toString().isEmpty()) {
            binding.etCrNumber.setError("Required");
            binding.etCrNumber.requestFocus();
        } else if (binding.etContactNumber.getText().toString().isEmpty()) {
            binding.etContactNumber.setError("Required");
            binding.etContactNumber.requestFocus();
        } else if (binding.etAuthoratyName.getText().toString().isEmpty()) {
            binding.etAuthoratyName.setError("Required");
            binding.etAuthoratyName.requestFocus();
        } else if (binding.etAuthoratyRpNumber.getText().toString().isEmpty()) {
            binding.etAuthoratyRpNumber.setError("Required");
            binding.etAuthoratyRpNumber.requestFocus();
        } else if (binding.etCompanyEmailId.getText().toString().isEmpty()) {
            binding.etCompanyEmailId.setError("Required");
            binding.etCompanyEmailId.requestFocus();
        } else if (!Tools.get().isValidEmail(binding.etCompanyEmailId.getText().toString())) {
            binding.etCompanyEmailId.setError("Email Not valid");
            binding.etCompanyEmailId.requestFocus();
        } else {
            valid = true;
        }
        return valid;
    }

    private HashMap<String, String> getParam() {
        HashMap<String, String> param = new HashMap<>();
        param.put("name", binding.etName.getText().toString());
        param.put("rp_number", binding.etRpNumber.getText().toString());
        param.put("email", isSelf?binding.etEmailId.getText().toString():binding.etCompanyEmailId.getText().toString());
        param.put("company_name", binding.etCompanyName.getText().toString());
        param.put("cr_number", binding.etCrNumber.getText().toString());
        param.put("mobile", isSelf?binding.ccp1.getSelectedCountryCode()+binding.etContactNumber1.getText().toString():binding.ccp.getSelectedCountryCode()+binding.etContactNumber.getText().toString());
        param.put("authority_name", binding.etAuthoratyName.getText().toString());
        param.put("auth_pr_no", binding.etAuthoratyRpNumber.getText().toString());
        param.put("provider_type", isSelf?"Self":"Business");
        param.put("lat", ""+lat);
        param.put("lon", ""+lng);
        param.put("types", "Provider");
        param.put("register_id", ""+regId);

        return param;
    }

    private void signupCall() {
        new ApiCallBuilder().build(this)
                .isShowProgressBar(true)
                .setParam(getParam())
                .setUrl(Config.BASE_URL+"provider_signup")
                .setFile("image", final_path)
                .setFile("rp_image", final_path2)
                .execute(new ApiCallBuilder.onResponse() {
                    @Override
                    public void Success(String response) {
                        Log.e("SignUpResponse","=========>"+response);
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("status").equals("1")) {
                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                Animatoo.animateShrink(SignUpActivity.this);
                                startActivity(new Intent(SignUpActivity.this,AddSparePartActivity.class));
                                finish();
                            } else {
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "" + object.getString("result"), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void Failed(String error) {

                    }
                });
    }

}
