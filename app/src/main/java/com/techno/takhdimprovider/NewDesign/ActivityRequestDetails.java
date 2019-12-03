package com.techno.takhdimprovider.NewDesign;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.techno.takhdimprovider.NewDesign.Adapters.Pager;
import com.techno.takhdimprovider.NewDesign.Models.ModelPager;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.databinding.ActivityRequestDetailsBinding;

import java.util.ArrayList;

public class ActivityRequestDetails extends AppCompatActivity {
    private ActivityRequestDetailsBinding binding;
    private ArrayList<ModelPager> arrayList=new ArrayList<>();
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =DataBindingUtil.setContentView(this, R.layout.activity_request_details);
        ID=getIntent().getExtras().getString("id");
        BindView();
    }

    private void BindView() {
        arrayList.add(new ModelPager("Details",new FragmentRequestDetails(ID)));
        arrayList.add(new ModelPager("Message",new FragmentRequestMessage(ID)));
        binding.viewpager.setAdapter(new Pager(getSupportFragmentManager(),arrayList));
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.imgBack.setOnClickListener(v->finish());
    }

}
