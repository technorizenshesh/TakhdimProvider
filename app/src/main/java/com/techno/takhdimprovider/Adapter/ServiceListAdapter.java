package com.techno.takhdimprovider.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;
import com.techno.takhdimprovider.Activity.ServiceListActivity;
import com.techno.takhdimprovider.Activity.SignUpActivity;
import com.techno.takhdimprovider.R;
import com.techno.takhdimprovider.Result.CategoryListResult;
import com.techno.takhdimprovider.Result.ServiceListResult;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    private Activity activity;
    private List<ServiceListResult> result;

    public ServiceListAdapter(Activity activity, List<ServiceListResult> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_name.setText(result.get(position).getName());
        Picasso.with(activity).load(result.get(position).getImagepath()).error(R.drawable.plumbing).into(holder.img_cat);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img_cat;
        TextView txt_name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_cat = itemView.findViewById(R.id.img_cat);
            txt_name = itemView.findViewById(R.id.txt_name);
        }

        @Override
        public void onClick(View view) {
            SignUpActivity.service_id = result.get(getAdapterPosition()).getId();
            SignUpActivity.service_name = result.get(getAdapterPosition()).getName();
//            SignUpActivity.txt_cat.setText("" + result.get(getAdapterPosition()).getName());
            Animatoo.animateShrink(activity);
            activity.finish();
        }
    }
}