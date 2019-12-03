package com.techno.takhdimprovider.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdimprovider.App.AppConfig;
import com.techno.takhdimprovider.Fragment.AllOrderkFragment;
import com.techno.takhdimprovider.Fragment.CompleteOrderFragment;
import com.techno.takhdimprovider.Fragment.InProgressOrderFragment;
import com.techno.takhdimprovider.Fragment.NewOrderFragment;
import com.techno.takhdimprovider.NewDesign.FragmentHome;
import com.techno.takhdimprovider.NewDesign.FragmentProfile;
import com.techno.takhdimprovider.NewDesign.FragmentRequest;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.databinding.ActivityHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdimprovider.App.MySharedPref.getData;
import static com.techno.takhdimprovider.App.MySharedPref.saveData;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        getWindow().setBackgroundDrawableResource(R.drawable.bglight);
        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        BindView();
        FragTrans(new FragmentHome());
    }

    private void BindView() {
        binding.navBottom.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void onBackPressed() {
       if (getSupportFragmentManager().getBackStackEntryCount()>1){
           getSupportFragmentManager().popBackStack();
       }else {
           System.exit(0);
       }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            FragTrans(new FragmentHome());
        } else if (id == R.id.nav_profile) {
            FragTrans(new FragmentProfile());
        } else if (id == R.id.nav_request) {
            FragTrans(new FragmentRequest());
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(HomeActivity.this, AddSparePartActivity.class));
        } else if (id == R.id.nav_my_parts) {
            startActivity(new Intent(HomeActivity.this, MyPartsListActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        }else if (id == R.id.nav_partsbooking) {
            startActivity(new Intent(HomeActivity.this, ViewbookedsparepartsActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(HomeActivity.this, HelpActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        } else if (id == R.id.nav_logout) {
            saveData(getApplicationContext(), "ldata", null);
            saveData(getApplicationContext(), "user_id", null);
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
            finish();
        }else if (id==R.id.nav_chat){
            startActivity(new Intent(HomeActivity.this, MessagesActivity.class));
            Animatoo.animateShrink(HomeActivity.this);
        }
        return true;
    }


    public void FragTrans(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
