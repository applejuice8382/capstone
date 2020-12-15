package com.example.capstone2_v1.mypagefragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.capstone2_v1.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MypageFriendFragment extends Fragment {

    String myJSON;

    JSONArray users = null;

    private static final String TAG_RESULTS = "result";

    private static final String TAG_JSON="result";
    private static final String TAG_NAME = "mem_id";
    private static final String TAG_NICK = "mem_nick";


    ArrayList<HashMap<String, String>> userList;

    ListView list;

    public MypageFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage_friend, container, false);

        list = (ListView) view.findViewById(R.id.listview);
        userList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.35.21:8070/user.php"); //수정 필요

        return view;
    }

    protected void showList() {
        try {
            JSONObject jsonObj;
            jsonObj = new JSONObject(myJSON);
            users = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < users.length(); i++) {
                JSONObject c = users.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String nick = c.getString(TAG_NICK);


                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_NAME, name);
                persons.put(TAG_NICK, nick);


                userList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), userList, R.layout.userlistview,
                    new String[]{TAG_NAME, TAG_NICK},
                    new int[]{R.id.mem_id, R.id.mem_nick}
            );

            list.setAdapter(adapter);
            setListViewHeightBasedOnChildren(list);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData(String url) {
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }
}