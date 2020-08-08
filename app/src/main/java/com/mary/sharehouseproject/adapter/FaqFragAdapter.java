package com.mary.sharehouseproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FaqFragAdapter extends FragmentPagerAdapter {

    private List<Fragment> faqFragments=new ArrayList<>();

    public FaqFragAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment){
        faqFragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return faqFragments.get(position);
    }

    @Override
    public int getCount() {
        return faqFragments.size();
    }
}
