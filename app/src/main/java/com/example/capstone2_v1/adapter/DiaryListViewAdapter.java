package com.example.capstone2_v1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone2_v1.EditDiaryActivity;
import com.example.capstone2_v1.R;
import com.example.capstone2_v1.insert.InsertDiary;
import com.example.capstone2_v1.item.DiaryListViewItem;
import com.example.capstone2_v1.menufragment.DiaryMenu;

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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diarylistview, parent, false);
        }

        TextView dateView = (TextView) convertView.findViewById(R.id.diarydate);
        TextView whereView = (TextView) convertView.findViewById(R.id.diarywhere);
        TextView titleView = (TextView) convertView.findViewById(R.id.diarytitle);
        final TextView contentView = (TextView) convertView.findViewById(R.id.diarycontent);

        final DiaryListViewItem listViewItem = listViewItemlist.get(position);

        dateView.setText(listViewItem.getDate());
        whereView.setText(listViewItem.getWhere());
        titleView.setText(listViewItem.getTitle());
        contentView.setText(listViewItem.getContent());

        final LinearLayout cmdArea = (LinearLayout)convertView.findViewById(R.id.cmdArea);

//        cmdArea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), listViewItemlist.get(pos).toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        cmdArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("수정하시겠습니까?")
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context, EditDiaryActivity.class);
                                intent.putExtra("no",listViewItemlist.get(pos).getNo());
                                intent.putExtra("date",listViewItemlist.get(pos).getDate());
                                intent.putExtra("where",listViewItemlist.get(pos).getWhere());
                                intent.putExtra("title", listViewItemlist.get(pos).getTitle());
                                intent.putExtra("content",listViewItemlist.get(pos).getContent());
                                context.startActivity(intent);
                            }
                        })
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                                Log.e("취소","취소");
                            }
                        }).show();

            return false;
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

    public void addItem(String no, String date, String where, String title, String content){
        DiaryListViewItem item = new DiaryListViewItem();

        item.setNo(no);
        item.setDate(date);
        item.setWhere(where);
        item.setTitle(title);
        item.setContent(content);

        listViewItemlist.add(item);
    }
}
