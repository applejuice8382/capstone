package com.example.capstone2_v1.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoriteRequest extends StringRequest {
    final static private String URL = "http://192.168.35.21:8070/favorite.php";
    private Map<String, String> parameters;

    public FavoriteRequest(String tour_name, Response.Listener<String> listner) {
        super(Method.POST, URL, listner, null);
        try {
            parameters = new HashMap<>();
            parameters.put("tour_name", tour_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
