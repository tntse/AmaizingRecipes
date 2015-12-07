package com.taeyeona.amaizingunicornrecipes.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.taeyeona.amaizingunicornrecipes.Adapter.CustomPagerAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.Adapter.ToggleDrawerAdapter;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;

import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.VolleySingleton;


/**
 * Created by Chau on 10/13/2015.
 */
public class RecipeShow extends AppCompatActivity{

    //DrawerLayout , prefListView , and prefListName manages preference drawer
    private DrawerLayout drawerLayout;
    private Bitmap theImage;
    private ListView navDrawer;
    private ListView prefListView;
    private FragmentSwitcherManager fragSwitcher;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        ImageLoader imgLoader = VolleySingleton.getInstance(this).getImageLoader();

        //Create drawer adapter to toggle search preferences with right side drawer
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_v2);
        String[] prefListName = ProfileHash.getSearchSettings();
        String[] navDrawerNames = getResources().getStringArray(R.array.drawer_list);
        prefListView = (ListView)findViewById((R.id.pref_drawer_right));
        prefListView.setAdapter(new ToggleDrawerAdapter(this, prefListName));

        navDrawer = (ListView)findViewById(R.id.nav_drawer_left);
        navDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navDrawerNames));
        navDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(navDrawer);
                switch(position){
                    case 0:
                    case 1:
                    case 2:
                        Intent intent = new Intent(RecipeShow.this, MainActivity.class);
                        intent.putExtra("Open", position - 3);
                        startActivity(intent);
                        finish();
                        break;
                    case 3:
                    case 4:
                    case 5:
                        Intent intent2 = new Intent(RecipeShow.this, EditSettings.class);
                        intent2.putExtra("Open", position - 3);
                        startActivity(intent2);
                        finish();
                        break;
                    case 6:
                        drawerLayout.openDrawer(prefListView);
                        break;
                }
            }
        });

        final String img = getIntent().getStringExtra("Picture");
        Bundle bundle = new Bundle();
        bundle.putString("Title", getIntent().getStringExtra("Title"));
        bundle.putString("SourceName", getIntent().getStringExtra("SourceName"));
        bundle.putString("SourceUrl", getIntent().getStringExtra("SourceUrl"));
        bundle.putString("Picture", getIntent().getStringExtra("Picture"));

        if(getIntent().getStringExtra("API").equals("Food2Fork")){
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putString("API", "Food2Fork");
        }else{
            bundle.putString("API", "Edamam");
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putStringArray("Ingredients", getIntent().getStringArrayExtra("Ingredients"));
            bundle.putStringArray("Nutrients", getIntent().getStringArrayExtra("Nutrients"));
            bundle.putIntArray("Totals", getIntent().getIntArrayExtra("Totals"));
        }

        imgLoader.get(img, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                theImage = imageContainer.getBitmap();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        bundle.putParcelable("BMP", theImage);
        TextView title = (TextView) findViewById(R.id.main_title_text);
        title.setText(getIntent().getStringExtra("Title"));
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), bundle);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_pages);
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        fragSwitcher = new FragmentSwitcherManager(viewPager, 2);

        Button button;
        View view;

        button = (Button) findViewById(R.id.main_button_1);
        button.setText("Ingredients");
        view = findViewById(R.id.main_bar_1);
        fragSwitcher.add(button, view);

        button = (Button) findViewById(R.id.main_button_2);
        button.setText("Instructions");
        view = findViewById(R.id.main_bar_2);
        fragSwitcher.add(button, view);

        button = (Button) findViewById(R.id.main_button_3);
        button.setText("Nutrition Info");
        view = findViewById(R.id.main_bar_3);
        fragSwitcher.add(button, view);

        TextView txtView = (TextView) findViewById(R.id.main_settings_text);
        txtView.setText("Go Back");

        ImageButton imgButton = (ImageButton) findViewById(R.id.main_settings_button);
        imgButton.setBackgroundResource(R.drawable.pizza);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}