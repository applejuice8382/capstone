package com.example.capstone2_v1.insert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstone2_v1.MainActivity;
import com.example.capstone2_v1.R;
import com.example.capstone2_v1.SearchMenu;
import com.example.capstone2_v1.menufragment.DiaryMenu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InsertDiary extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.35.21:8070";
    private static String TAG = "phptest";

    private EditText ediary_title, emem_id, ediary_con, etour_no;
    private Button insert;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diaryinsert);

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

        ediary_title = (EditText)findViewById(R.id.add_diary_title);
        emem_id = (EditText)findViewById(R.id.add_id);
        ediary_con = (EditText)findViewById(R.id.add_content);
        etour_no = (EditText) findViewById(R.id.add_where);
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        insert = (Button)findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diary_title = ediary_title.getText().toString();
                String mem_id = emem_id.getText().toString();
                String diary_con = ediary_con.getText().toString();
                String tour_no = etour_no.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/diaryinsert.php", mem_id, tour_no, diary_title, diary_con);

                ediary_title.setText("");
                ediary_con.setText("");
                emem_id.setText("");
                etour_no.setText("");

                onBackPressed();

            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InsertDiary.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String mem_id = (String)params[1];
            String tour_no = (String)params[2];
            String diary_title = (String)params[3];
            String diary_con = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "mem_id=" + mem_id + "&tour_no=" + tour_no + "&diary_title=" + diary_title + "&diary_con=" + diary_con;


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
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
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
        getMenuInflater().inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
