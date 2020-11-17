package com.example.capstone2_v1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TourActivity extends AppCompatActivity {

    String Tourname;

    String myJSON;

    private Map<String, String> parameters;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "tour_name";
    private static final String TAG_TEL = "tour_tel";
    private static final String TAG_ADD = "tour_add";
    private static final String TAG_CON = "tour_con";

    JSONArray tours = null;

    ArrayList<HashMap<String, String>> tourdetailList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourdetail);


        Intent intent = getIntent();
        Tourname = intent.getStringExtra("name");

        Log.d(this.getClass().getName(), Tourname);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list = (ListView) findViewById(R.id.tourdetailv);
        tourdetailList = new ArrayList<HashMap<String, String>>();

        parameters = new HashMap<>();
        parameters.put("tour_name", Tourname);

        Log.d(this.getClass().getName(), "파라미터:" + parameters.put("tour_name", Tourname));

        getData("http://172.30.1.33:8070/tourdetail.php");



    }

    protected void showList () {
        try {
            JSONObject jsonObj;
            jsonObj = new JSONObject(myJSON);
            tours = jsonObj.getJSONArray(TAG_RESULTS);


            JSONObject c = tours.getJSONObject(1);
            String name = c.getString(TAG_NAME);
            String tel = c.getString(TAG_TEL);
            String address = c.getString(TAG_ADD);
            String con = c.getString(TAG_CON);

            HashMap<String, String> persons = new HashMap<String, String>();

            persons.put(TAG_NAME, name);
            persons.put(TAG_TEL, tel);
            persons.put(TAG_ADD, address);
            persons.put(TAG_CON, con);

            tourdetailList.add(persons);


            ListAdapter adapter = new SimpleAdapter(
                    this, tourdetailList, R.layout.tourdetaillistview,
                    new String[]{TAG_NAME, TAG_TEL, TAG_ADD, TAG_CON},
                    new int[]{R.id.tourname, R.id.tourtel, R.id.touradd, R.id.tourcon}
            );

            list.setAdapter(adapter);

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

    public boolean onOptionsItemSelected(MenuItem item ){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
