package com.example.capstone2_v1.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends StringRequest {

    private final static String URL = "http://172.30.1.33:8070/Signup.php";
    private Map<String, String> parameters;

    public SignupRequest(String mem_id, String mem_pw, String mem_nick, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, URL, listener, errorListener);

        parameters = new HashMap<>();
        parameters.put("mem_id", mem_id);
        parameters.put("mem_pw", mem_pw);
        parameters.put("mem_nick", mem_nick);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return parameters;
    }
}
