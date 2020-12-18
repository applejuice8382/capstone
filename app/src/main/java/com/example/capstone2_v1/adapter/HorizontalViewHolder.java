package com.example.capstone2_v1.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone2_v1.R;

import androidx.recyclerview.widget.RecyclerView;

class HorizontalViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView description;

    public TextView img;


    public HorizontalViewHolder(View itemView) {
        super(itemView);

        icon = (ImageView) itemView.findViewById(R.id.horizon_icon);
        description = (TextView) itemView.findViewById(R.id.horizon_description);

    }
}
