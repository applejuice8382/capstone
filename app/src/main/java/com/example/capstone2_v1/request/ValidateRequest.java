package com.example.capstone2_v1.request;

import android.app.DownloadManager;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.request.StringRequest;


import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private String URL="http://192.168.35.21:8070/userValidate.php";
    private Map<String, String> map;

    public ValidateRequest(String mem_id, Response.Listener<String>listener){
        super(Request.Method.POST, URL,listener,null);

        map = new HashMap<>();
        map.put("mem_id",mem_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
