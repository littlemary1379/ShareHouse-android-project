package com.mary.sharehouseproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MypageFragAdapter extends FragmentPagerAdapter {

    private List<Fragment> mypageFragLists=new ArrayList<>();

    public MypageFragAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment frag){
        mypageFragLists.add(frag);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mypageFragLists.get(position);
    }

    @Override
    public int getCount() {
        return mypageFragLists.size();
    }
}
