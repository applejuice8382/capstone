package com.example.capstone2_v1.menufragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.capstone2_v1.adapter.DiaryListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class DiaryMenu extends ListFragment {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NO = "d.diary_no";
    private static final String TAG_TIME = "d.diary_time";
    private static final String TAG_NAME = "t.tour_name";
    private static final String TAG_TITLE = "d.diary_title";
    private static final String TAG_CON = "d.diary_con";
    private static final String TAG_IMAGE = "d.imgPath";

    JSONArray diaries = null;

    ArrayList<HashMap<String, String>> diaryList;

    ListView list;
    DiaryListViewAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new DiaryListViewAdapter();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showList() {

                try {
                    setListAdapter(adapter);
                    JSONObject jsonObj;
                    jsonObj = new JSONObject(myJSON);
                    diaries = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < diaries.length(); i++) {
                        JSONObject c = diaries.getJSONObject(i);
                        String no = c.getString(TAG_NO);
                        String time = c.getString(TAG_TIME);
                        String name = c.getString(TAG_NAME);
                        String title = c.getString(TAG_TITLE);
                        String con = c.getString(TAG_CON);
                        String image = "http://192.168.0.5:80/" + c.getString(TAG_IMAGE);
                        Log.e("image", image);

                        adapter.addItem(no, time, name, title, con, image);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");

                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new DiaryListViewAdapter();
        getData("http://192.168.0.5:80/diary.php");
    }
}
