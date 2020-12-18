package com.example.capstone2_v1.adapter;


import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone2_v1.R;
import com.example.capstone2_v1.item.TourData;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<TourData> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<TourData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView country;


        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.country = (TextView) view.findViewById(R.id.address);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tourlistview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.name.setText(mList.get(position).getTour_name());
        viewholder.country.setText(mList.get(position).getTour_add());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}