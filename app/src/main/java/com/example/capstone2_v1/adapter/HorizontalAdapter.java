package com.example.capstone2_v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone2_v1.R;
import com.example.capstone2_v1.TourDetailActivity;
import com.example.capstone2_v1.menufragment.TourMenu;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {

    private ArrayList<HorizontalData> HorizontalDatas;
    private Context mContext;


    public void setData(ArrayList<HorizontalData> list){
        HorizontalDatas = list;
    }
    HorizontalViewHolder holder;
    String imgPath;
    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 사용할 아이템의 뷰를 생성해준다.
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizon_recycler_items, parent, false);

        holder = new HorizontalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, final int position) {
        HorizontalData data = HorizontalDatas.get(position);

        holder.description.setText(data.getText());

        Glide.with(holder.icon.getContext()).load(data.getImgPath()).into(holder.icon);

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
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(holder.icon.getContext(), uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

    @Override
    public int getItemCount() {
        return HorizontalDatas.size();
    }

}




