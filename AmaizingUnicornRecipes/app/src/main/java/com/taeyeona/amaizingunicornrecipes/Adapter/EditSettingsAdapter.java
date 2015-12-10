package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.taeyeona.amaizingunicornrecipes.Fragment.EditFavoritesFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.EditPantryFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.EditUserInfoFragment;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditSettingsAdapter extends FragmentStatePagerAdapter {
    private Bundle bundle;

    public EditSettingsAdapter(FragmentManager fm, Bundle bun) {
        super(fm);
        bundle = bun;
    }

    /**
     * adapter used to show the fragments from the viewpager  in EditSetting Activity
     *
     * @param position Position of fragment in viewpager
     * @return Fragment to be visible
     *
     */
    @Override
    public Fragment getItem(int position) {
        switch(position){
            default:
                return new EditFavoritesFragment();
            case 1:
                return new EditUserInfoFragment();
            case 2:
                return new EditPantryFragment();
        }
    }

    /**
     * the number of fragments in the view pager in EditSettings Activity
     *
     * @return
     */
    @Override
    public int getCount() {
        return 3;
    }
}