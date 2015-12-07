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

    @Override
    public int getCount() {
        return 3;
    }
}