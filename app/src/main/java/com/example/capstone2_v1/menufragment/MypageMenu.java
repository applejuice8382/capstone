package com.example.capstone2_v1.menufragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.capstone2_v1.FavoriteList;
import com.example.capstone2_v1.FriendList;
import com.example.capstone2_v1.MainActivity;
import com.example.capstone2_v1.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MypageMenu extends Fragment {

    private FriendList friendList = new FriendList();
    private FavoriteList favoriteList = new FavoriteList();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage, container, false);

        Button btnfavorite = view.findViewById(R.id.buttonfavorite);
        Button btnfriend = view.findViewById(R.id.buttonfriend);

        View.OnClickListener btnListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonfavorite:
                        Log.v("태그", "메시지");
                        ((MainActivity) getActivity()).replaceFragment(1);
                        break;
                    case R.id.buttonfriend:
                        ((MainActivity) getActivity()).replaceFragment(2);
                        break;
                    default:
                        break;
                }
            }
        };
        btnfavorite.setOnClickListener(btnListener);
        btnfriend.setOnClickListener(btnListener);

        return view;
    }


}


