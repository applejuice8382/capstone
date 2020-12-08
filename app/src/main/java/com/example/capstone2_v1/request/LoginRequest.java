package com.example.capstone2_v1.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://192.168.35.21:8070/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String mem_id, String mem_pw, Response.Listener<String> listner) {
        super(Method.POST, URL, listner, null);
        try {
            parameters = new HashMap<>();
            parameters.put("mem_id", mem_id);
            parameters.put("mem_pw", mem_pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}

