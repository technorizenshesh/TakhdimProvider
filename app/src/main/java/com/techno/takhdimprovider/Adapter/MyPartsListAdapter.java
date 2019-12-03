package com.techno.takhdimprovider.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Result.MyPartsResult;

import java.util.List;


public class MyPartsListAdapter extends RecyclerView.Adapter<MyPartsListAdapter.ViewHolder> {
    private Activity activity;
    private List<MyPartsResult> result;

    public MyPartsListAdapter(Activity activity, List<MyPartsResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_parts_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txt_name.setText(result.get(position).getCompanyName());
        holder.txt_price.setText("Price : " + result.get(position).getPrice() + " USD");
        holder.txt_warrenty.setText("Warrenty : " + result.get(position).getWarranty() + " Months");
        holder.txt_detl.setText("Details : "+result.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_price, txt_warrenty, txt_detl;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_warrenty = itemView.findViewById(R.id.txt_warrenty);
            txt_detl = itemView.findViewById(R.id.txt_detl);
        }

        @Override
        public void onClick(View view) {

        }
    }
}