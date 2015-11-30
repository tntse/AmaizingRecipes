package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private Bundle bun;

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

        TextView txtView = (TextView) findViewById(R.id.main_settings_text);
        txtView.setText("Go Back");


        TextView title = (TextView) findViewById(R.id.main_title_text);
        title.setText("Edit Settings");

        ImageButton imgButton = (ImageButton) findViewById(R.id.main_settings_button);
        imgButton.setBackgroundResource(R.drawable.pizza);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        bun.putInt("Current", mViewPager.getCurrentItem());
    }

    private void loadAdapters(){
        editSettingsAdapter = new EditSettingsAdapter(getSupportFragmentManager(), bun);
        mViewPager = (ViewPager) findViewById(R.id.main_pages);
        mViewPager.setAdapter(editSettingsAdapter);

        if(fragSwitcher == null){
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

        }else{
            fragSwitcher.setViewPager(mViewPager);
        }

        fragSwitcher.setPage(bun.getInt("Current"));
    }
}
