package com.techno.takhdimprovider.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techno.takhdimprovider.R;

import static com.techno.takhdimprovider.App.MySharedPref.getData;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityCompat.requestPermissions(this, mPermission, REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAccepted=false;
        for (int i:grantResults){
            isAccepted=i==0;
            if (!isAccepted)break;
        }
        if (isAccepted){
            Handlers();
        }else {
            ActivityCompat.requestPermissions(this, mPermission, REQUEST_CODE_PERMISSION);
        }
    }
    void Handlers(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String ldata = getData(SplashActivity.this, "ldata", null);
                if (ldata != null) {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    Animatoo.animateCard(SplashActivity.this);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    Animatoo.animateCard(SplashActivity.this);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
