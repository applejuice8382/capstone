package com.example.capstone2_v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText inputId, inputPw, inputNick;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        inputId = (EditText) findViewById(R.id.inputid);
        inputPw = (EditText) findViewById(R.id.inputpw);
        inputNick = (EditText) findViewById(R.id.inputnick);

        signupButton  = (Button) findViewById(R.id.buttonsignup);

        signupButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                signup();
            }
        });
    }
    private void signup(){
        final String mem_id = inputId.getText().toString();
        final String mem_pw = inputPw.getText().toString();
        final String mem_nick = inputNick.getText().toString();

        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"회원가입 실패!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"회원가입 처리시 에러발생!",Toast.LENGTH_SHORT).show();
                return;
            }
        };


        SignupRequest signupRequest = new SignupRequest(mem_id, mem_pw, mem_nick, responseListner,errorListener);
        signupRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(signupRequest);

    }


}
