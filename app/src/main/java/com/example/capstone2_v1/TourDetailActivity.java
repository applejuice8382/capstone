package com.example.capstone2_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class TourDetailActivity extends AppCompatActivity {

    private static final String TAG_ADDRESS = "http://192.168.35.21:8070/tourdetail.php";
    private static final String TAG = "phptest";
    private static final String TAG_NAME = "tour_name";
    private static final String TAG_TEL = "tour_tel";
    private static final String TAG_ADD = "tour_add";
    private static final String TAG_CON = "tour_con";


    private TextView tourname, tourtel, touradd, tourcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourdetail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(getApplicationContext(), SearchMenu.class);
                startActivity(intent);

                return true;

            }
        });
        tourname = (TextView) findViewById(R.id.tourname);
        tourtel = (TextView) findViewById(R.id.tourtel);
        touradd = (TextView) findViewById(R.id.touradd);
        tourcon = (TextView) findViewById(R.id.tourcon);

        Intent intent = getIntent();
        String tour_name = intent.getExtras().getString("name");
        GetDataJSON task = new GetDataJSON();
        try {
            task.execute(TAG_ADDRESS, tour_name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    class GetDataJSON extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String uri = (String) params[0];
                String tour_name = (String) params[1];
                String link = uri +"?tour_name=" + tour_name;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

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
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject c = jsonArray.getJSONObject(0);
                String name = c.getString(TAG_NAME);
                String tel = c.getString(TAG_TEL);
                String add = c.getString(TAG_ADD);
                String con = c.getString(TAG_CON);
                tourname.setText(name);
                tourtel.setText(tel);
                touradd.setText(add);
                tourcon.setText(con);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


