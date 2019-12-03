package com.techno.takhdimprovider.NewDesign;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techno.takhdimprovider.Fragment.NewOrderFragment;
import com.techno.takhdimprovider.NewDesign.Adapters.Pager;
import com.techno.takhdimprovider.NewDesign.Models.ModelPager;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.databinding.FragmentRequestBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRequest extends Fragment {
    private FragmentRequestBinding binding;
    private ArrayList<ModelPager>arrayList=new ArrayList<>();
    public FragmentRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_request, container, false);
        BindView();
        return binding.getRoot();
    }
    private void BindView() {
        arrayList.add(new ModelPager("New",new NewOrderFragment("Pending")));
        arrayList.add(new ModelPager("Schedule",new NewOrderFragment("Accept")));
        arrayList.add(new ModelPager("Completed",new NewOrderFragment("Complete")));
        arrayList.add(new ModelPager("Canceled",new NewOrderFragment("Cancel")));
        binding.viewpager.setAdapter(new Pager(getChildFragmentManager(),arrayList));
        binding.tabs.setupWithViewPager(binding.viewpager);
    }

}
