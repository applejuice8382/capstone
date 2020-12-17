package com.example.capstone2_v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone2_v1.R;
import com.example.capstone2_v1.TourDetailActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {

    private ArrayList<HorizontalData> HorizontalDatas;
    private Context mContext;

    public void setData(ArrayList<HorizontalData> list){
        HorizontalDatas = list;
    }


    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 사용할 아이템의 뷰를 생성해준다.
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizon_recycler_items, parent, false);

        HorizontalViewHolder holder = new HorizontalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, final int position) {
        HorizontalData data = HorizontalDatas.get(position);

        holder.description.setText(data.getText());
        holder.icon.setImageResource(data.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();


                Bundle bundle = new Bundle();
                bundle.putString("name", HorizontalDatas.get(position).getText());

                Intent intent = new Intent(v.getContext(), TourDetailActivity.class);

                intent.putExtra("name", bundle);
                Log.d(this.getClass().getName(),HorizontalDatas.get(position).getText());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return HorizontalDatas.size();
    }

}




