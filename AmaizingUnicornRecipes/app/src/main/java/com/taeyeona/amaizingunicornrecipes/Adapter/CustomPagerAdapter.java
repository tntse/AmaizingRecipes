package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.taeyeona.amaizingunicornrecipes.Fragment.IngredientsFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.InstructionsFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.NutritionFragment;

/**
 * Created by Chau on 11/7/2015.
 */
public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    Bundle bundle;

    public CustomPagerAdapter(FragmentManager fm, Bundle bun) {
        super(fm);
        bundle = bun;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                Fragment ingre = new IngredientsFragment();
                ingre.setArguments(bundle);
                return ingre;
            case 1:
                Fragment instruct = new InstructionsFragment();
                instruct.setArguments(bundle);
                return instruct;
            default:
                Fragment nutri = new NutritionFragment();
                nutri.setArguments(bundle);
                return nutri;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
