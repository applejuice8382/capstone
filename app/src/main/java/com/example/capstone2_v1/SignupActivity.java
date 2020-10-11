package com.example.capstone2_v1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText inputId;
    private EditText inputPw;
    private EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        inputId = (EditText) findViewById(R.id.inputid);
        inputPw = (EditText) findViewById(R.id.inputpw);
        inputName = (EditText) findViewById(R.id.inputname);

        final String id = inputId.getText().toString();
        final String pw = inputPw.getText().toString();
        final String name = inputName.getText().toString();

        Button signupBtn = (Button) findViewById(R.id.buttonsignup);
        signupBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                insertMember(id, pw, name);
            }
        });
    }

    private void insertMember(final String id, final String pw, final String name){
        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignupActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Log.d("Tag : ", s); // php에서 가져온 값을 최종 출력함
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params){
                try{
                    String id = (String) params[0];
                    String pw = (String) params[1];
                    String name = (String) params[2];

                    String link = "http://127.0.0.1/InsertMember.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += URLEncoder.encode("pw","UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");
                    data += URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoInput(true);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                    outputStreamWriter.write(data);
                    outputStreamWriter.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Log.d("tag : ", sb.toString()); // php에서 결과값을 리턴
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(id, pw, name);
    }
}
