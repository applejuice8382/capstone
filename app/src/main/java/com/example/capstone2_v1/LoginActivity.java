package com.example.capstone2_v1;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText idText, passwordText;
    Button loginButton, signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent splash = new Intent(this, SplashMain.class);
        startActivity(splash);

        idText =(EditText)findViewById(R.id.idText);
        passwordText =(EditText)findViewById(R.id.pswordText);

        loginButton = (Button)findViewById(R.id.loginButton);
        signupButton = (Button)findViewById(R.id.signupButton);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

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

    }

}


