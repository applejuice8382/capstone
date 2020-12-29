package com.example.capstone2_v1.chat;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstone2_v1.Public.Se_Application;
import com.example.capstone2_v1.R;
import com.example.capstone2_v1.chat.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final List<ChatMsgVO> mValues;

    public ChatAdapter(List<ChatMsgVO> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_chat_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ChatMsgVO vo = mValues.get(position);
        if (mValues.get(position).getUserid().equals("lsy123")) {
            holder.other_cl.setVisibility(View.GONE);
            holder.my_cl.setVisibility(View.VISIBLE);

            holder.date_tv2.setText(vo.getCrt_dt());
            holder.content_tv2.setText(vo.getContent1());
        }else
        {
            holder.other_cl.setVisibility(View.VISIBLE);
            holder.my_cl.setVisibility(View.GONE);

            holder.userid_tv.setText(vo.getUserid());
            holder.date_tv.setText(vo.getCrt_dt());
            holder.content_tv.setText(vo.getContent1());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout my_cl, other_cl;
        public TextView userid_tv, date_tv, content_tv, date_tv2, content_tv2;

        public ViewHolder(View view) {
            super(view);
            my_cl = view.findViewById(R.id.my_cl);
            other_cl = view.findViewById(R.id.other_cl);
            userid_tv = view.findViewById(R.id.userid_tv);
            date_tv = view.findViewById(R.id.date_tv);
            content_tv = view.findViewById(R.id.content_tv);
            date_tv2 = view.findViewById(R.id.date_tv2);
            content_tv2 = view.findViewById(R.id.content_tv2);

        }

    }
}