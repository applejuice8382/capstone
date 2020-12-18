package com.example.capstone2_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

public class DiaryEditActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "192.168.0.5:80";
    private static String TAG = "phptest";

    String diary_no = "";
    private TextView editdate, editwhere;
    private EditText edittitle, editcontent;
    private Button editsavebtn, editdeletebtn;
    private ImageView editimage;
    private int con=0;

    String imgPath;

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

        editimage = (ImageView) findViewById(R.id.editImage);

        Intent intent = getIntent();

        diary_no = intent.getExtras().getString("no");
        String date = intent.getExtras().getString("date");
        String where = intent.getExtras().getString("where");
        String title = intent.getExtras().getString("title");
        String content = intent.getExtras().getString("content");
        String image = intent.getExtras().getString("image");

        Log.e("diary_no", diary_no);
        editdate.setText(date);
        editwhere.setText(where);
        edittitle.setText(title);
        editcontent.setText(content);
        Glide.with(this).load(image).into(editimage);
        imgPath=image;

        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,10);
                con = 1;
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


    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DiaryEditActivity.this,
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){

                    Uri uri= data.getData();
                    if(uri!=null){
                        editimage.setImageURI(uri);

                        imgPath= getRealPathFromUri(uri);
                    }

                }else
                {
                    Toast.makeText(this, "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

    public void clickUpdate(View view) {
        if(con == 1) {
            String diary_title = edittitle.getText().toString();
            String diary_con = editcontent.getText().toString();
            String diaryNo = diary_no;

            Log.e("title: ", diary_title);
            Log.e("con: ", diary_con);
            Log.e("no: ", diaryNo);

            String serverUrl = "http://192.168.0.5:80/diaryedit.php";

            SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DiaryEditActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            });

            smpr.addStringParam("diary_title", diary_title);
            smpr.addStringParam("diary_con", diary_con);
            smpr.addStringParam("diary_no", diaryNo);
            smpr.addFile("img", imgPath);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(smpr);

            onBackPressed();
        }else{
            String diary_title = edittitle.getText().toString();
            String diary_con = editcontent.getText().toString();
            String diaryNo = diary_no;

            String serverUrl = "http://192.168.0.5:80/diaryedit1.php";

            SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DiaryEditActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            });

            smpr.addStringParam("diary_title", diary_title);
            smpr.addStringParam("diary_con", diary_con);
            smpr.addStringParam("diary_no", diaryNo);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(smpr);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

}