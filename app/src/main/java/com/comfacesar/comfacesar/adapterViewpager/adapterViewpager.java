package com.comfacesar.comfacesar.adapterViewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class adapterViewpager extends FragmentPagerAdapter {

    private List<Fragment> listFragment;
    //private List<String> listTitles;

    public adapterViewpager(FragmentManager fragmentManager, List<Fragment> listFragment) {
        super(fragmentManager);
        this.listFragment = listFragment;
        //this.listTitles = listTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}