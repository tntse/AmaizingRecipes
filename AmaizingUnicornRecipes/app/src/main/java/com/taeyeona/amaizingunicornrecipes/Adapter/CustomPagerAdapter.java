package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.taeyeona.amaizingunicornrecipes.Fragment.IngredientsFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.InstructionsFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.NutritionFragment;
import com.taeyeona.amaizingunicornrecipes.Fragment.PlayerFragment;

/**
 * Created by Chau on 11/7/2015.
 */
public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    private Bundle bundle;

    public CustomPagerAdapter(FragmentManager fm, Bundle bun) {
        super(fm);
        bundle = bun;
    }

    /**
     * Manages how the viewpager will appear on screen
     *
     * @param position The position related to the tabs
     * @return Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                Fragment ingre = new IngredientsFragment();
                ingre.setArguments(bundle);
                return ingre;
            case 1:
                try{
                    Fragment instruct = new InstructionsFragment();
                    instruct.setArguments(bundle);
                    return instruct;
                }catch(Exception e){

                }
            default:
                Fragment nutri = new NutritionFragment();
                nutri.setArguments(bundle);
                return nutri;
        }
    }

    /**
     * @return Returns the number of fragments in the adapter
     */
    @Override
    public int getCount() {
        return 3;
    }
}
