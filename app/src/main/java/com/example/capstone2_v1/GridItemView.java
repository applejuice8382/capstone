package com.example.capstone2_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridItemView extends LinearLayout {
    TextView textView;

    public GridItemView(Context context){
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_custome, this, true);

        textView = (TextView) findViewById(R.id.gridviewtxt);
    }


    public void setName(String name) {
        textView.setText(name);
    }

}
