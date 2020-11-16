package com.example.capstone2_v1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone2_v1.R;
import com.example.capstone2_v1.item.DiaryListViewItem;

import java.util.ArrayList;

public class DiaryListViewAdapter extends BaseAdapter {

    private ArrayList<DiaryListViewItem> listViewItemlist = new ArrayList<DiaryListViewItem>();

    public DiaryListViewAdapter() {
    }

    @Override
    public int getCount(){
        return listViewItemlist.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diarylistview, parent, false);
        }

        TextView dateView = (TextView) convertView.findViewById(R.id.diarydate);
        TextView whereView = (TextView) convertView.findViewById(R.id.diarywhere);
        TextView titleView = (TextView) convertView.findViewById(R.id.diarytitle);
        TextView contentView = (TextView) convertView.findViewById(R.id.diarycontent);

        DiaryListViewItem listViewItem = listViewItemlist.get(position);

        dateView.setText(listViewItem.getDate());
        whereView.setText(listViewItem.getWhere());
        titleView.setText(listViewItem.getTitle());
        contentView.setText(listViewItem.getContent());

        LinearLayout cmdArea = (LinearLayout)convertView.findViewById(R.id.cmdArea);

        cmdArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), listViewItemlist.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position){
        return listViewItemlist.get(position);
    }

    public void addItem(String date, String where, String title, String content){
        DiaryListViewItem item = new DiaryListViewItem();

        item.setDate(date);
        item.setWhere(where);
        item.setTitle(title);
        item.setContent(content);

        listViewItemlist.add(item);
    }
}
