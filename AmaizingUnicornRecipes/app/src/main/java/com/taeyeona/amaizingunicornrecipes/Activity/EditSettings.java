package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.taeyeona.amaizingunicornrecipes.Adapter.CustomPagerAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.EditSettingsAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditSettings extends AppCompatActivity {

    private ViewPager mViewPager;
    private FragmentSwitcherManager fragSwitcher;
    private EditSettingsAdapter editSettingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        editSettingsAdapter = new EditSettingsAdapter(getSupportFragmentManager(), savedInstanceState);
        mViewPager = (ViewPager) findViewById(R.id.main_pages);
        mViewPager.setAdapter(editSettingsAdapter);
        fragSwitcher = new FragmentSwitcherManager(mViewPager);

        Button button;
        View view;

        button = (Button) findViewById(R.id.main_button_1);
        button.setText("Favorites");
        view = findViewById(R.id.main_bar_1);
        fragSwitcher.add(button, view);

        button = (Button) findViewById(R.id.main_button_2);
        button.setText("User Info");
        view = findViewById(R.id.main_bar_2);
        fragSwitcher.add(button, view);

        button = (Button) findViewById(R.id.main_button_3);
        button.setText("Pantry");
        view = findViewById(R.id.main_bar_3);
        fragSwitcher.add(button, view);
        
    }
}
