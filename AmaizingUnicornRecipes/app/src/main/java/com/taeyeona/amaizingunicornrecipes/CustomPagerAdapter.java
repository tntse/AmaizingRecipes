package com.taeyeona.amaizingunicornrecipes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Chau on 11/7/2015.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    // This method returns the fragment associated with
    // the specified position.
    //
    // It is called when the Adapter needs a fragment
    // and it does not exists.
    public Fragment getItem(int position) {

        Log.d(CustomPagerAdapter.class.getSimpleName(), new Integer(position).toString());
        switch(position){
            case 0:
                return new IngredientsFragment();
            case 1:
                return new InstructionsFragment();
            default:
                return new NutritionFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
