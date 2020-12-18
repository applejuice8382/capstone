package com.example.capstone2_v1.mypagefragment;

import android.support.v4.os.IResultReceiver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MypageDiaryFragment();
            default:
                return new MypageFriendFragment();
//            case 2:
//                return new MypageFavoriteFragment();
//            default:
//                return new MypageReviewFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
