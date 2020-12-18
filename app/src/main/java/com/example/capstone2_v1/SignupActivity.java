package com.example.capstone2_v1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import com.android.volley.toolbox.Volley;
import com.example.capstone2_v1.request.SignupRequest;
import com.example.capstone2_v1.request.ValidateRequest;

import org.json.JSONException;
import org.json.JSONObject;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText inputId, inputPw, inputPwCk, inputNick;
    Button signupButton, vaildateButton;
    ImageView setImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        inputId = (EditText) findViewById(R.id.inputid);
        inputPw = (EditText) findViewById(R.id.inputpw);
        inputPwCk = (EditText) findViewById(R.id.inputpwck);
        inputNick = (EditText) findViewById(R.id.inputnick);
        signupButton = (Button) findViewById(R.id.buttonsignup);
        vaildateButton = (Button) findViewById(R.id.validateButton);
        setImage = (ImageView) findViewById(R.id.setImage);

        vaildateButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        signupButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        inputPwCk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (inputPw.getText().toString().equals(inputPwCk.getText().toString())) {
                    setImage.setImageResource(R.drawable.ic_baseline_check_24);
                } else {
                    setImage.setImageResource(R.drawable.ic_baseline_clear_24);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void signup() {
        final String mem_id = inputId.getText().toString();
        final String mem_pw = inputPw.getText().toString();
        final String mem_pwck = inputPwCk.getText().toString();
        final String mem_nick = inputNick.getText().toString();

        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (mem_id.equals("")) {
                        Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else if(mem_pw.equals("")) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!mem_pw.equals(mem_pwck)){
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (mem_nick.equals("")) {
                        Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (success) {
                            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "회원가입 처리시 에러발생!", Toast.LENGTH_SHORT).show();
                return;
            }
        };


        SignupRequest signupRequest = new SignupRequest(mem_id, mem_pw, mem_nick, responseListner, errorListener);
        signupRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(signupRequest);

    }

    private void validate() {
        boolean validate = false;
        String mem_id = inputId.getText().toString();
        if (validate) {
            return;
        }
        if (mem_id.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setMessage("아이디를 입력하세요");
            builder.setPositiveButton("확인", null);
            builder.create();
            builder.show();
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setMessage("사용할 수 있는 아이디입니다.");
                        builder.setPositiveButton("확인", null);
                        builder.create();
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setMessage("사용할 수 없는 아이디입니다.");
                        builder.setNegativeButton("확인", null);
                        builder.create();
                        builder.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(mem_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        queue.add(validateRequest);

    }


}
