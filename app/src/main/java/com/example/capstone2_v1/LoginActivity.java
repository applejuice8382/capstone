package com.example.capstone2_v1;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone2_v1.request.LoginRequest;
import com.example.capstone2_v1.request.PreferenceManager;

import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public EditText idText;
    public EditText passwordText;
    Button loginButton, signupButton;
    private CheckBox cb_save;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;

        idText =(EditText)findViewById(R.id.idText);
        passwordText =(EditText)findViewById(R.id.pswordText);

        loginButton = (Button)findViewById(R.id.loginButton);
        signupButton = (Button)findViewById(R.id.signupButton);
        cb_save = (CheckBox)findViewById(R.id.checkBox);


        boolean boo = PreferenceManager.getBoolean(mContext,"check"); //로그인 정보 기억하기 체크 유무 확인
         if(boo){ // 체크가 되어있다면 아래 코드를 수행 //저장된 아이디와 암호를 가져와 셋팅한다.
              idText.setText(PreferenceManager.getString(mContext, "id"));
              passwordText.setText(PreferenceManager.getString(mContext, "pw"));
              cb_save.setChecked(true); //체크박스는 여전히 체크 표시 하도록 셋팅
              }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mem_id=idText.getText().toString();
                final String mem_pw=passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String mem_id = jsonObject.getString("mem_id");
                                String mem_nick = jsonObject.getString("mem_nick");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("mem_id", mem_id);
                                intent.putExtra("mem_nick", mem_nick);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.");
                                builder.setNegativeButton("다시 시도", null);
                                builder.create();
                                builder.show();
                            }
                        } catch (Exception e) {

                        }
                    }
                };

                LoginRequest loginRequest= new LoginRequest(mem_id, mem_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });



        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        //로그인 기억하기 체크박스 유무에 따른 동작 구현

        cb_save.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // 체크박스 체크 되어 있으면
                    // editText에서 아이디와 암호 가져와 PreferenceManager에 저장한다.
                    PreferenceManager.setString(mContext, "id", idText.getText().toString()); //id 키값으로 저장
                    PreferenceManager.setString(mContext, "pw", passwordText.getText().toString()); //pw 키값으로 저장
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked()); //현재 체크박스 상태 값 저장
                } else { //체크박스가 해제되어있으면
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked()); //현재 체크박스 상태 값 저장
                    PreferenceManager.clear(mContext); //로그인 정보를 모두 날림
                }
            }
        }) ;


    }

}


