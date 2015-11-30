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

import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.Adapter.MainAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.ToggleDrawerAdapter;
import com.taeyeona.amaizingunicornrecipes.Eula;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;


public  class MainActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener,*/{


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
        settingsLabel.setText("Settings");
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
        theMainAdapter = new MainAdapter(getSupportFragmentManager(), bun);
        theViewPager = (ViewPager) findViewById(R.id.main_pages);
        theViewPager.setAdapter(theMainAdapter);
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

    /*        //test
        myAdapter = new MyAdapter(this);
       navListView.setAdapter(myAdapter);


        /**
         * Saved variables for drawerListView and drawerListNames,
         * navListName are array items in strings.xml
         * navListView is the list to be adapter for the listnme to be viewable
         * in simple list item format
         *
         drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
         navListName = getResources().getStringArray(R.array.drawer_list);

         navListView = (ListView)findViewById((R.id.nav_drawer));
         navListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,navListName));
         navListView.setOnItemClickListener(this);

         //set up button sound
         //final MediaPlayer kitty = MediaPlayer.create(this,R.raw.kitty);

         //created buttons to reference each activity
         Button pantry = (Button)findViewById(R.id.goToPantry);
         Button profile = (Button)findViewById(R.id.profile);
         Button toEditIngredients = (Button) findViewById(R.id.to_edit_ingredients_button);

         //the pantry button listens for onClick and Intent references me to another activity

         //start pantry onClickListner
         pantry.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View view) {
        //kitty.start();
        Intent intent = new Intent(MainActivity.this, Pantry.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

        }
        });
         //End onclick to pantry

         //start profile onClickListner
         profile.setOnClickListener(new View.OnClickListener()
         {
         @Override public void onClick(View view) {
         //kitty.start();
         Intent intent = new Intent(MainActivity.this, Profile.class);
         startActivity(intent);
         overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
         }
         });

         //Edit Ingredients
         toEditIngredients.setOnClickListener(new View.OnClickListener(){
         public void onClick(View view){
         //kitty.start();
         Intent intent = new Intent(MainActivity.this, EditIngredients.class);
         startActivity(intent);
         overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

         }
         });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param parent
     * @param view
     * @param position
     * @param id       OnItemClick added for Drawer list View
     *                 goes to different activities in app
     */

}
