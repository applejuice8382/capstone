package com.example.capstone2_v1.searchfragment;

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

public class Fragment1 extends Fragment {
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "tour_name";
    private static final String TAG_ADD = "tour_add";

    JSONArray tours = null;

    ArrayList<HashMap<String, String>> tourList;

    ListView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.tourtab,container,false);

        list = (ListView) v.findViewById(R.id.listview);
        tourList = new ArrayList<HashMap<String, String>>();
        getData("http://10.20.55.177:8070/tour.php"); //수정 필요

        return v;

    }

    protected void showList () {
        try {
            JSONObject jsonObj;
            jsonObj = new JSONObject(myJSON);
            tours = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < tours.length(); i++) {
                JSONObject c = tours.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_NAME, name);
                persons.put(TAG_ADD, address);

                tourList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), tourList, R.layout.tourlistview,
                    new String[]{TAG_NAME, TAG_ADD},
                    new int[]{R.id.name, R.id.address}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getData (String url){
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