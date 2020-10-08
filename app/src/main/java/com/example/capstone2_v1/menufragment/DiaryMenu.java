package com.example.capstone2_v1.menufragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.example.capstone2_v1.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DiaryMenu extends Fragment {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_TIME = "d.diary_time";
    private static final String TAG_NAME = "t.tour_name";
    private static final String TAG_TITLE = "d.diary_title";
    private static final String TAG_CON = "d.diary_con";

    JSONArray diaries = null;

    ArrayList<HashMap<String, String>> diaryList;

    ListView list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary, container, false);

        list = (ListView) view.findViewById(R.id.diarylistview);
        diaryList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.35.21:8070/diary.php"); //수정 필요

        return view;
    }

    protected void showList () {
        try {
            JSONObject jsonObj;
            jsonObj = new JSONObject(myJSON);
            diaries = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < diaries.length(); i++) {
                JSONObject c = diaries.getJSONObject(i);
                String time = c.getString(TAG_TIME);
                String name = c.getString(TAG_NAME);
                String title = c.getString(TAG_TITLE);
                String con = c.getString(TAG_CON);

                HashMap<String, String> diarys = new HashMap<String, String>();

                diarys.put(TAG_TIME, time);
                diarys.put(TAG_NAME, name);
                diarys.put(TAG_TITLE, title);
                diarys.put(TAG_CON, con);

                diaryList.add(diarys);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), diaryList, R.layout.diarylistview,
                    new String[]{TAG_TIME, TAG_NAME, TAG_TITLE, TAG_CON},
                    new int[]{R.id.diarydate, R.id.diarywhere, R.id.diarytitle, R.id.diarycontent}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData (String url) {
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
}


