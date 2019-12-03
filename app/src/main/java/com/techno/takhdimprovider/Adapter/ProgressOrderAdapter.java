package com.techno.takhdimprovider.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdimprovider.Activity.InvoiceActivity;
import com.techno.takhdimprovider.Activity.OrdeDetailsActivity;
import com.techno.takhdimprovider.Activity.RepotrOrederActivity;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Result.OrderResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProgressOrderAdapter extends RecyclerView.Adapter<ProgressOrderAdapter.ViewHolder> {
    private Activity activity;
    private List<OrderResult> result;

    public ProgressOrderAdapter(Activity activity, List<OrderResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_order_item_view, parent, false);
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

        holder.btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, InvoiceActivity.class));
                Animatoo.animateShrink(activity);
            }
        });



        holder.txt_username.setText(result.get(position).getUsersDetails().getUsername());
        holder.txt_sname.setText(result.get(position).getServiceDetails().getName());
        holder.txt_date_time.setText(result.get(position).getTime()+" , "+result.get(position).getDate());

        holder.txt_order_id.setText("Order. tak-"+result.get(position).getId());
        holder.txt_status.setText(result.get(position).getStatus());
        holder.txt_address.setText(result.get(position).getAddress());
        Picasso.with(activity).load(result.get(position).getServiceDetails().getImage()).error(R.drawable.plumbing).into(holder.img_service);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_detail, btn_report, btn_complete;
        CircleImageView img_service;
        TextView txt_date_time, txt_order_id, txt_sname, txt_status, txt_address, txt_username;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            btn_detail = itemView.findViewById(R.id.btn_detail);
            btn_report = itemView.findViewById(R.id.btn_report);
            btn_complete = itemView.findViewById(R.id.btn_complete);

            img_service = itemView.findViewById(R.id.img_service);
            txt_date_time = itemView.findViewById(R.id.txt_date_time);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_sname = itemView.findViewById(R.id.txt_sname);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_username = itemView.findViewById(R.id.txt_username);

        }

        @Override
        public void onClick(View view) {

        }
    }
}