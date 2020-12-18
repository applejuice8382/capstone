package com.example.capstone2_v1.menufragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.capstone2_v1.FavoriteList;
import com.example.capstone2_v1.FriendList;
import com.example.capstone2_v1.LoginActivity;
import com.example.capstone2_v1.MainActivity;
import com.example.capstone2_v1.R;
import com.example.capstone2_v1.mypagefragment.PageAdapter;
import com.example.capstone2_v1.request.PreferenceManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class MypageMenu extends Fragment {
    private Context mContext;
    private FriendList friendList = new FriendList();
    private FavoriteList favoriteList = new FavoriteList();
    TextView profileName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend, container, false);

        ViewPager2 viewPager2 = view.findViewById(R.id.mypageviewpager);
        viewPager2.setAdapter(new PageAdapter(getActivity()));

        profileName = view.findViewById(R.id.profileName);


        final TabLayout tabLayout = view.findViewById(R.id.mypagetab);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 0:{
                        tab.setIcon(R.drawable.diary);
                        break;
                    }
                    case 1:{
                        tab.setIcon(R.drawable.friend);
                        break;
                    }
//                    case 2: {
//                        tab.setIcon(R.drawable.ic_lineheart);
//                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
//                        badgeDrawable.setBackgroundColor(
//                                ContextCompat.getColor(getActivity(), R.color.colorAccent)
//                        );
//                        badgeDrawable.setVisible(true);
//                        break;
//                    }
//                    case 3:{
//                        tab.setIcon(R.drawable.review);
//                        break;
//                    }

                }
            }
        }
        );
        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                badgeDrawable.setVisible(false);
            }
        });

//        Button btnfavorite = view.findViewById(R.id.buttonfavorite);
//        Button btnfriend = view.findViewById(R.id.buttonfriend);
//
//        View.OnClickListener btnListener = new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.buttonfavorite:
//                        Log.v("태그", "메시지");
//                        ((MainActivity) getActivity()).replaceFragment(1);
//                        break;
//                    case R.id.buttonfriend:
//                        ((MainActivity) getActivity()).replaceFragment(2);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        btnfavorite.setOnClickListener(btnListener);
//        btnfriend.setOnClickListener(btnListener);

        return view;
    }


}


