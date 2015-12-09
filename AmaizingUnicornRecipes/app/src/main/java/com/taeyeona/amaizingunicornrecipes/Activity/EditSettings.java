package com.taeyeona.amaizingunicornrecipes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Adapter.EditSettingsAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.Adapter.ToggleDrawerAdapter;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;
import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Manages the EditFavorites, EditProfile, and EditPantry fragments.
 *
 * @author Thomas Tse
 * @author HaoXian Zheng
 * @author David Chau
 * @since 2015-11-24
 *
 */
public class EditSettings extends AppCompatActivity {

    //DrawerLayout , prefListView , and prefListName manages preference drawer
    private DrawerLayout drawerLayout;
    private ListView prefListView;

    private ListView navDrawer;

    private ViewPager mViewPager;
    private FragmentSwitcherManager fragSwitcher;
    private Bundle bun;

    /**
     * Initializes all components within the layout.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        bun = savedInstanceState;
        if(bun == null){
            bun = new Bundle();
            bun.putInt("Current", getIntent().getIntExtra("Open",0));
        }
        loadAdapters();

        //Create drawer adapter to toggle search preferences with right side drawer
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_v2);
        String[] prefListName = ProfileHash.getSearchSettings();
        String[] navDrawerNames = getResources().getStringArray(R.array.drawer_list);
        prefListView = (ListView)findViewById((R.id.pref_drawer_right));
        prefListView.setAdapter(new ToggleDrawerAdapter(this, prefListName));


        //Create Navigation Drawer for left side for button to open
        navDrawer = (ListView)findViewById(R.id.nav_drawer_left);
        navDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navDrawerNames));

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
        navDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(navDrawer);
                switch(position){
                    case 0:
                    case 1:
                    case 2:
                        Intent intent = new Intent(EditSettings.this, MainActivity.class);
                        intent.putExtra("Open", position);
                        startActivity(intent);
                        finish();
                        break;
                    case 3:
                    case 4:
                    case 5:
                        fragSwitcher.setPage(position-3);
                        break;
                    case 6:
                        drawerLayout.openDrawer(prefListView);
                        break;
                }
            }
        });

        // Set the text above the back button
        TextView txtView = (TextView) findViewById(R.id.main_settings_text);
        txtView.setText("Go Back");

        // Set the Activity Title text
        TextView title = (TextView) findViewById(R.id.main_title_text);
        title.setText("Edit Settings");

        // Set the back button image and function
        ImageButton imgButton = (ImageButton) findViewById(R.id.main_settings_button);
        imgButton.setBackgroundResource(R.drawable.pizza);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        bun.putInt("Current", mViewPager.getCurrentItem());
    }

    /**
     *
     */
    private void loadAdapters(){
        EditSettingsAdapter editSettingsAdapter = new EditSettingsAdapter(getSupportFragmentManager(), bun);
        mViewPager = (ViewPager) findViewById(R.id.main_pages);
        mViewPager.setAdapter(editSettingsAdapter);

        if(fragSwitcher == null){
            fragSwitcher = new FragmentSwitcherManager(mViewPager, 1);

            Button button;
            View view;

            button = (Button) findViewById(R.id.main_button_1);
            button.setText("Favorites");
            view = findViewById(R.id.main_bar_1);
            fragSwitcher.add(button, view);

            button = (Button) findViewById(R.id.main_button_2);
            button.setText("General");
            view = findViewById(R.id.main_bar_2);
            fragSwitcher.add(button, view);

            button = (Button) findViewById(R.id.main_button_3);
            button.setText("Pantry");
            view = findViewById(R.id.main_bar_3);
            fragSwitcher.add(button, view);

        }else{
            fragSwitcher.setViewPager(mViewPager);
        }
        fragSwitcher.setPage(bun.getInt("Current"));
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                SharedPreferences sharedPref = getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPref.edit();
                edit.putString("Picture", imgDecodableString);
                edit.commit();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Open", 0);
                startActivity(intent);

            } else {
                Toast.makeText(this, "You haven't picked an image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "We couldn't connect with your chosen gallery", Toast.LENGTH_LONG)
                    .show();
        }
    }

}
