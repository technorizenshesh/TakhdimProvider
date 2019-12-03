package com.techno.takhdimprovider.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.techno.takhdimprovider.Activity.InvoiceActivity;
import com.techno.takhdimprovider.Activity.OrdeDetailsActivity;
import com.techno.takhdimprovider.Activity.RepotrOrederActivity;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Result.OrderResult;

import java.util.List;

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.ViewHolder> {
    private Activity activity;
    private List<OrderResult> result;

    public CompleteOrderAdapter(Activity activity, List<OrderResult> result) {
        this.activity = activity;
        this.result=result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_order_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,RepotrOrederActivity.class);
                i.putExtra("request_id",""+result.get(position).getId());
                activity.startActivity(i);
                Animatoo.animateShrink(activity);
            }
        });

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,OrdeDetailsActivity.class);
                i.putExtra("request_id",""+result.get(position).getId());
                activity.startActivity(i);
                Animatoo.animateShrink(activity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_detail, btn_report;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            btn_detail = itemView.findViewById(R.id.btn_detail);
            btn_report = itemView.findViewById(R.id.btn_report);
        }

        @Override
        public void onClick(View view) {

        }
    }
}