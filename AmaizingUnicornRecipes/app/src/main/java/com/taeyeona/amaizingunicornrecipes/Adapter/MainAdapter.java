package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.taeyeona.amaizingunicornrecipes.Fragment.ProfileFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.PantryFragment;

/**
 * Created by thomastse on 11/17/15.
 */
public class MainAdapter extends FragmentStatePagerAdapter {
    Bundle bundle;

    public MainAdapter(FragmentManager fm, Bundle bun) {
        super(fm);
        bundle = bun;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            default:
                return new ProfileFragment();
            case 1:
                return new PantryFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
