package com.taeyeona.amaizingunicornrecipes.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.Adapter.MainAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.ToggleDrawerAdapter;
import com.taeyeona.amaizingunicornrecipes.Eula;
import com.taeyeona.amaizingunicornrecipes.Fragment.RecipeSearchFragment;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;


public  class MainActivity extends AppCompatActivity{


    //DrawerLayout , prefListView , and prefListName manages preference drawer
    private DrawerLayout drawerLayout;
    private ListView prefListView;
    private String[] prefListName;

    private MainAdapter theMainAdapter;
    private ViewPager   theViewPager;
    private FragmentSwitcherManager fragmentSwitcher;
    private Bundle bun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bun = savedInstanceState;
        if(bun == null){
            bun = new Bundle();
        }
        setContentView(R.layout.activity_main_v2);

        //EULA FOR NEW USERS
        new Eula(this).show();


        //Create drawer adapter to toggle search preferences with right side drawer

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_v2);
        prefListName = ProfileHash.getSearchSettings();
        prefListView = (ListView)findViewById((R.id.pref_drawer_right));
        prefListView.setAdapter(new ToggleDrawerAdapter(this, prefListName));


        loadAdapters();

        TextView title = (TextView) findViewById(R.id.main_title_text);
        title.setText(getString(R.string.app_name));
        TextView settingsLabel = (TextView) findViewById(R.id.main_settings_text);
        settingsLabel.setText(getString(R.string.settings));
        ImageButton imgButton = (ImageButton) findViewById(R.id.main_settings_button);
        imgButton.setBackgroundResource(R.drawable.donut_gear_v2);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettingsActivity = new Intent(MainActivity.this, EditSettings.class);
                startActivity(toSettingsActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdapters();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bun.putInt("Current", theViewPager.getCurrentItem());
    }

    private void loadAdapters(){
        theMainAdapter = new MainAdapter(getSupportFragmentManager());
        theViewPager = (ViewPager) findViewById(R.id.main_pages);
        theViewPager.setAdapter(theMainAdapter);
        theViewPager.addOnPageChangeListener(new PageListener());
        if(fragmentSwitcher == null) {
            fragmentSwitcher = new FragmentSwitcherManager(theViewPager);

            Button button;
            View view;

            button = (Button) findViewById(R.id.main_button_1);
            button.setText("Profile");
            view = findViewById(R.id.main_bar_1);
            view.setVisibility(View.INVISIBLE);
            fragmentSwitcher.add(button, view);

            button = (Button) findViewById(R.id.main_button_2);
            button.setText("Pantry");
            view = findViewById(R.id.main_bar_2);
            view.setVisibility(View.INVISIBLE);
            fragmentSwitcher.add(button, view);

            button = (Button) findViewById(R.id.main_button_3);
            button.setText("Search Recipe");
            view = findViewById(R.id.main_bar_3);
            view.setVisibility(View.INVISIBLE);
            fragmentSwitcher.add(button, view);

        }else {
            fragmentSwitcher.setViewPager(theViewPager);
            fragmentSwitcher.setPage(bun.getInt("Current"));
        }

    }

    public Bundle getBundle(){
        return bun;
    }

    public void addData(String data){
        bun.putString("SearchQuery", data);
    }

    public void addData(ArrayList<String> data){
        bun.putStringArrayList("SearchIngredients", data);
    }

    public void addData(boolean buttonPress){
        bun.putBoolean("Button", buttonPress);
    }

    class PageListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageSelected ( int position){
                /*
                 * Problems: Ingredients only change on button press and not swipe
                 */
            if (position == 2) {
                theMainAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels){}
        @Override
        public void onPageScrollStateChanged ( int state){}
    }

}
