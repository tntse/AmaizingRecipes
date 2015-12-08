package com.taeyeona.amaizingunicornrecipes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.Adapter.MainAdapter;
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

    //Left side navigation drawer
    private ListView navDrawer;

    private MainAdapter theMainAdapter;
    private ViewPager   theViewPager;
    private FragmentSwitcherManager fragmentSwitcher;
    private Bundle bun;
    private RecipeSearchFragment recipeSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bun = savedInstanceState;
        if(bun == null){
            bun = new Bundle();
            bun.putInt("Current", 1);
        }
        setContentView(R.layout.activity_main_v2);
        String[] navDrawerNames = getResources().getStringArray(R.array.drawer_list);

        //EULA FOR NEW USERS
        new Eula(this).show();


        //Create drawer adapter to toggle search preferences with right side drawer
        /**
         * An array of preference names are saved from ProfileHash.getSearchSettings
         * then the array is loaded into the drawer to be togglable
         *
         * @author Benson
         */
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_v2); //right
        String[] prefListName = ProfileHash.getSearchSettings();
        prefListView = (ListView)findViewById((R.id.pref_drawer_right)); //right
        prefListView.setAdapter(new ToggleDrawerAdapter(this, prefListName));

        //Create Navigation Drawer for left side for button to open
        /**
         * The left side drawer hold navigation button towards other activities in the app,
         * an array of names of the activities we can navigate to are loaded by the adapter
         * and set in the navDrawer(left hand drawer)
         *
         * @author Benson
         */
        navDrawer = (ListView)findViewById(R.id.nav_drawer_left);
        navDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navDrawerNames));


        navDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *
             * A switch statement is used to manage the navigation drawer to
             * determine where the drawer brings you depending on the item
             * in the array selected onClick.
             *
             * @author Thomas, Benson
             * @param parent AdapterView
             * @param view View to adapt
             * @param position position of array list
             * @param id items in list
             *

             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(navDrawer);
                switch(position){
                    case 0:
                    case 1:
                    case 2:
                        fragmentSwitcher.setPage(position);
                        break;
                    case 3:
                    case 4:
                    case 5:
                        Intent intent = new Intent(MainActivity.this, EditSettings.class);
                        intent.putExtra("Open", position - 3);
                        startActivity(intent);
                        break;
                    case 6:
                        drawerLayout.openDrawer(prefListView);
                        break;
                }
            }
        });

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
                drawerLayout.openDrawer(navDrawer);
            }
        });
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadAdapters();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        bun.putInt("Current", theViewPager.getCurrentItem());
    }

    /**
     * loadAdapter keeps track of which fragment is selected on our viewPager
     * when user swipes to new fragment in viewPager we change
     * the current fragment the user is viewing and changes
     * the navigation bar's visibility to show the user which view should be visible
     *
     */
    private void loadAdapters(){
        if(recipeSearchFragment == null)
            recipeSearchFragment = new RecipeSearchFragment();

        theMainAdapter = new MainAdapter(getSupportFragmentManager());
        theViewPager = (ViewPager) findViewById(R.id.main_pages);
        theViewPager.setAdapter(theMainAdapter);

        theViewPager.setOffscreenPageLimit(3);

        if(fragmentSwitcher == null) {
            fragmentSwitcher = new FragmentSwitcherManager(theViewPager, 0);

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
        }

        fragmentSwitcher.setPage(bun.getInt("Current"));
    }

    /**
     * get the bundle created on mainActivity during onCreate
     * @return
     */
    public Bundle getBundle(){
        return bun;
    }

    /**
     * pass in String variable then adds the string to the Bundle
     * @param data
     */
    public void addData(String data){
        bun.putString("SearchQuery", data);
    }

    /**
     * passes in String array list , then adds the list into the bundle
     * @param data
     *
     */
    public void addData(ArrayList<String> data){
        bun.putStringArrayList("SearchIngredients", data);
    }
}
