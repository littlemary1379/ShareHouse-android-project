package com.mary.sharehouseproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LendhouseFragAdapter extends FragmentPagerAdapter {

    private List<Fragment> lendhouseFragLists=new ArrayList<>();

    public LendhouseFragAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment frag){
        lendhouseFragLists.add(frag);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lendhouseFragLists.get(position);
    }

    @Override
    public int getCount() {
        return lendhouseFragLists.size();
    }
}
