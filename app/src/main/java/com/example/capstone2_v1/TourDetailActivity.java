package com.example.capstone2_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class TourDetailActivity extends AppCompatActivity {

    private static final String TAG_ADDRESS1 = "http://192.168.35.21:8070/tourdetail.php";
    private static final String TAG_ADDRESS2 = "http://192.168.35.21:8070/recommend.php";
    private static final String TAG_ADDRESS3 = "http://192.168.35.21:8070/favorite.php";
    private static final String TAG = "phptest";
    private static final String TAG_NAME = "tour_name";
    private static final String TAG_TEL = "tour_tel";
    private static final String TAG_TIME = "tour_time";
    private static final String TAG_ADD = "tour_add";
    private static final String TAG_CON = "tour_con";
    private static final String TAG_NO = "tour_no";
    private static final String TAG_FAVORITE = "favorite";

    private Map<String, String> parameters;


    private TextView tourname, tourtel, touradd, tourcon, favorite1, tourtime;
    private ImageView lineheart, tourimage;

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
        tourtime = (TextView) findViewById(R.id.tourtime);
        touradd = (TextView) findViewById(R.id.touradd);
        tourcon = (TextView) findViewById(R.id.tourcon);
        lineheart = (ImageView) findViewById(R.id.lineheart);
        tourimage = (ImageView) findViewById(R.id.tourimage);

        Intent intent = getIntent();
        final String tour_name = intent.getExtras().getString("name");
        GetDataJSON1 task1 = new GetDataJSON1();
        GetDataJSON1 task2 = new GetDataJSON1();
        final GetDataJSON2 task3 = new GetDataJSON2();
        try {
            task1.execute(TAG_ADDRESS1, tour_name).get();
            task2.execute(TAG_ADDRESS2, tour_name);


        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        lineheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    lineheart.setImageResource(R.drawable.heart);
                    task3.execute(TAG_ADDRESS3, tour_name).get();

                }catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



    }
    class GetDataJSON1 extends AsyncTask<String, Void, String> {
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
                String time = c.getString(TAG_TIME);
                String con = c.getString(TAG_CON);
                String image = "http://192.168.35.21:8070/" + c.getString("imgPath");

                tourname.setText(name);
                tourtel.setText(tel);
                tourtime.setText(time);
                touradd.setText(add);
                tourcon.setText(con);

                Glide.with(tourimage.getContext()).load(image).into(tourimage);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class GetDataJSON2 extends AsyncTask<String, Void, String> {
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
                String favorite = c.getString(TAG_FAVORITE);

                lineheart.setImageResource(R.drawable.heart);

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



