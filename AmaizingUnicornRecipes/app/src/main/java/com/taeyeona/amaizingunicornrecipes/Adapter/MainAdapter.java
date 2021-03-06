package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.taeyeona.amaizingunicornrecipes.Fragment.ProfileFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.PantryFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.RecipeSearchFragment;

/**
 * Created by thomastse on 11/17/15.
 */
public class MainAdapter extends FragmentStatePagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     *
     * @param position position of viewPager
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch(position){
            default:
                return new ProfileFragment();
            case 1:
                return new PantryFragment();
            case 2:
                return new RecipeSearchFragment();
        }
    }

    /**
     *
     * @param object Fragment in the viewpager
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return 3;
    }
}
