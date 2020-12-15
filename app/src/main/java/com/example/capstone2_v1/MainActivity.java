package com.example.capstone2_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.capstone2_v1.insert.InsertDiary;
import com.example.capstone2_v1.menufragment.ChatMenu;
import com.example.capstone2_v1.menufragment.DiaryMenu;
import com.example.capstone2_v1.menufragment.MapMenu;
import com.example.capstone2_v1.menufragment.MypageMenu;
import com.example.capstone2_v1.menufragment.TourMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private DiaryMenu DiaryMenu = new DiaryMenu();
    private MypageMenu MypageMenu = new MypageMenu();
    private TourMenu TourMenu = new TourMenu();
    private ChatMenu ChatMenu = new ChatMenu();


    private Context mContext;
    private FloatingActionButton fab_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바 뒤로가기
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.drawable.ic_search);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Context mContext = getApplicationContext();

        fab_main = (FloatingActionButton) findViewById(R.id.fab_main);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsertDiary.class);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, DiaryMenu).commitAllowingStateLoss();

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(getApplicationContext(), SearchMenu.class);
                startActivity(intent);

                return true;

            }
        });

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, DiaryMenu).commitAllowingStateLoss();
                        break;
                    }

                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, TourMenu).commitAllowingStateLoss();
                        break;
                    }

                    case R.id.navigation_menu4: {
                        transaction.replace(R.id.frame_layout, ChatMenu).commitAllowingStateLoss();
                        break;
                    }

                    case R.id.navigation_menu5: {
                        transaction.replace(R.id.frame_layout, MypageMenu).commitAllowingStateLoss();
                        break;
                    }

                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    public void replaceFragment(int usage){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (usage){
            case 1:
                fragmentTransaction.replace(R.id.frame_layout, new FavoriteList());
                fragmentTransaction.addToBackStack(null).commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.frame_layout, new FriendList());
                fragmentTransaction.addToBackStack(null).commit();
                break;
            default:
                break;

        }
    }




}