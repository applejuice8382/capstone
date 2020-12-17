package com.example.capstone2_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditDiaryActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "192.168.35.21:8070";
    private static String TAG = "phptest";

    private TextView editdate, editwhere;
    private EditText edittitle, editcontent;
    private Button editsavebtn, editdeletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarydetail);
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

        editdate = (TextView) findViewById(R.id.editdate);
        editwhere = (TextView) findViewById(R.id.editwhere);

        edittitle = (EditText) findViewById(R.id.edittitle);
        editcontent = (EditText) findViewById(R.id.editcontent);

        editsavebtn = (Button) findViewById(R.id.editsavebtn);
        editdeletebtn = (Button) findViewById(R.id.editdeletebtn);

        Intent intent = getIntent();

        final String diary_no = intent.getExtras().getString("no");
        String date = intent.getExtras().getString("date");
        String where = intent.getExtras().getString("where");
        String title = intent.getExtras().getString("title");
        String content = intent.getExtras().getString("content");

        Log.e("diary_no", diary_no);
        editdate.setText(date);
        editwhere.setText(where);
        edittitle.setText(title);
        editcontent.setText(content);


        editsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diary_title = edittitle.getText().toString();
                String diary_con = editcontent.getText().toString();

                UpdateData task = new UpdateData();
                task.execute("http://" + IP_ADDRESS + "/diaryupdate.php", diary_title, diary_con, diary_no);

                onBackPressed();
            }
        });

        editdeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteData task1 = new DeleteData();
                task1.execute("http://" + IP_ADDRESS + "/diarydelete.php",diary_no);

                onBackPressed();
            }

        });

    }

    class UpdateData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(EditDiaryActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "POST response  - " + result);
            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String diary_title = (String) params[1];
            String diary_con = (String) params[2];
            String diary_no = (String) params[3];

            String serverURL = (String) params[0];
            String postParameters = "diary_title=" + diary_title + "&diary_con=" + diary_con + "&diary_no=" + diary_no;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "UpdateData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(EditDiaryActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "GET response  - " + result);
            progressDialog.dismiss();

        }


        @Override
        protected String doInBackground(String... params) {

            try {
                String uri = (String) params[0];
                String diary_no = (String) params[1];
                String link = uri + "/?diary_no=" + diary_no;
                URL url = new URL(link);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String json;

                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                Log.e("??","??");

                return sb.toString().trim();
            } catch (Exception e) {
                Log.e("!!","!!");
                return null;
            }

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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