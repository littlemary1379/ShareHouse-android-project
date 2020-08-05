package com.mary.sharehouseproject.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HouseManualFragAdapter extends FragmentPagerAdapter {

    private List<Fragment> houseManualFragLists=new ArrayList<>();

    public HouseManualFragAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment frag){
        houseManualFragLists.add(frag);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return houseManualFragLists.get(position);
    }

    @Override
    public int getCount() {
        return houseManualFragLists.size();
    }
}
