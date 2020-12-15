package com.example.capstone2_v1.mypagefragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstone2_v1.R;

/**
 * A simple {@link Fragment} subclass.

 */
public class MypageDiaryFragment extends Fragment {



    public MypageDiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage_diary, container, false);
    }
}