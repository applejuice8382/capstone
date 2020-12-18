package com.example.capstone2_v1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

public class DiaryInsertActivity extends AppCompatActivity {

    private EditText ediary_title, emem_id, ediary_con, etour_no;
    private TextView mTextViewResult;
    private ImageView diaryImage;

    String imgPath;

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
        diaryImage = (ImageView)findViewById(R.id.diaryImage);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        diaryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });


        //동적퍼미션 작업
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10 :
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){

                    Uri uri= data.getData();
                    if(uri!=null){
                        diaryImage.setImageURI(uri);

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

    public void clickUpload(View view) {

        String diary_title = ediary_title.getText().toString();
        String mem_id = emem_id.getText().toString();
        String diary_con = ediary_con.getText().toString();
        String tour_no = etour_no.getText().toString();

        String serverUrl="http://192.168.0.5:80/diaryinsert.php";

        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DiaryInsertActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("diary_title",diary_title);
        smpr.addStringParam("mem_id",mem_id);
        smpr.addStringParam("diary_con",diary_con);
        smpr.addStringParam("tour_no",tour_no);
        smpr.addFile("img",imgPath);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}
