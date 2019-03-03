package com.comfacesar.comfacesar.adapterViewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.comfacesar.comfacesar.fragment.AlertTempranaFragment;
import com.comfacesar.comfacesar.fragment.HomeFragment;
import com.comfacesar.comfacesar.fragment.UbicacionFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter{
    private Context context;
    private Fragment fragment_anterior;
    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                HomeFragment.fragmentConsultarNoticiasActivo = false;
                fragment = new AlertTempranaFragment();
                break;
            case 3:
                HomeFragment.fragmentConsultarNoticiasActivo = false;
                fragment = new UbicacionFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "HOME";
            case 1:
                return "ASESORIAS";
            case 2:
                return "ALERTA";
            case 3:
                return "SEDES";
        }
        return null;
    }
}