package com.example.capstone2_v1.searchfragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.capstone2_v1.MainActivity;
import com.example.capstone2_v1.R;
import com.example.capstone2_v1.SearchMenu;
import com.example.capstone2_v1.TourDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.ContentValues.TAG;

public class Fragment1 extends Fragment {

    String myJSON;

    JSONArray tours = null;
    private static int REQUEST_ACCESS_FINE_LOCATION = 1000;

    private static final String TAG_RESULTS = "result";

    private static final String TAG_JSON="result";
    private static final String TAG_NAME = "tour_name";
    private static final String TAG_ADD = "tour_add";


    String mJsonString;

    ArrayList<HashMap<String, String>> tourList;

    ListView list;
    TextView mTextViewResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.tourtab,container,false);
        list =  v.findViewById(R.id.listview);
        tourList = new ArrayList<HashMap<String, String>>();
        TextView mTextViewResult = v.findViewById(R.id.textView_main_result);

        getData("http://192.168.35.21:8070/tour.php"); //수정 필요

        final EditText editsearch = v.findViewById(R.id.editSearch);
        ImageView searchbtn = v.findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tourList.clear();

                GetData task = new GetData();
                task.execute( editsearch.getText().toString());

            }
        });

        tourList = new ArrayList<>();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textviewName = (TextView) view.findViewById(R.id.name);
                Bundle bundle = new Bundle();
                bundle.putString("name", textviewName.getText().toString());

                Log.d(this.getClass().getName(), bundle.toString());


                Intent intent = new Intent(getActivity(), TourDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return v;

    }


    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];

            String serverURL = "http://192.168.35.21:8070/toursearch2.php";
            String postParameters = "tour_name=" + searchKeyword1 ;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Log.d(this.getClass().getName(),mJsonString);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_ADD);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_ADD, address);

                tourList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), tourList, R.layout.tourlistview,
                    new String[]{TAG_NAME, TAG_ADD},
                    new int[]{R.id.name, R.id.address}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    protected void showList () {
        try {
            JSONObject jsonObj;
            jsonObj = new JSONObject(myJSON);
            tours = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < tours.length(); i++) {
                JSONObject c = tours.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_NAME, name);
                persons.put(TAG_ADD, address);

                tourList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), tourList, R.layout.tourlistview,
                    new String[]{TAG_NAME, TAG_ADD},
                    new int[]{R.id.name, R.id.address}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData (String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}