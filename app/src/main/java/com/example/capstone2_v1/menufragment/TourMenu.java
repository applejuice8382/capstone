package com.example.capstone2_v1.menufragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.capstone2_v1.R;
import com.example.capstone2_v1.TourDetailActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TourMenu extends Fragment {

    String myJSON;

    private static int REQUEST_ACCESS_FINE_LOCATION = 1000;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "tour_name";
    private static final String TAG_ADD = "tour_add";

    JSONArray tours = null;

    ArrayList<HashMap<String, String>> tourList;

    ListView list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tour, container, false);

        list = (ListView) view.findViewById(R.id.listview);
        tourList = new ArrayList<HashMap<String, String>>();
        getData("http://172.30.1.29:80/tour.php"); //수정 필요

//////        카카오지도
//        MapView mapView = new MapView(getActivity());
//        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.MapView);
//        mapViewContainer.addView(mapView);
//
//        // 중심점 변경 - 예제 좌표는 서울 남산
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304), true);
//
//        // 줌 레벨 변경
//        mapView.setZoomLevel(4, true);
//
//        //마커 찍기
//        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304);
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("Default Marker");
//        marker.setTag(0);
//        marker.setMapPoint(MARKER_POINT);
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);
//
//
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textviewName = (TextView)view.findViewById(R.id.name);
                Bundle bundle = new Bundle();
                bundle.putString( "name", textviewName.getText().toString());

                Log.d(this.getClass().getName(), bundle.toString());


                Intent intent = new Intent(getActivity(), TourDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return view;
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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(getActivity(), TourActivity.class );
//        intent.putExtra("id", datas.get(position).id);
//        startActivity(intent);
//    }

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


