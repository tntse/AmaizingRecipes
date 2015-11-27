package com.taeyeona.amaizingunicornrecipes.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.taeyeona.amaizingunicornrecipes.Adapter.CustomPagerAdapter;
import com.taeyeona.amaizingunicornrecipes.FavoriteObjHandler;
import com.taeyeona.amaizingunicornrecipes.Adapter.FragmentSwitcherManager;
import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.VolleySingleton;


/**
 * Created by Chau on 10/13/2015.
 */
public class RecipeShow extends AppCompatActivity{


    // private ImageView image;
    private ImageLoader imgLoader = VolleySingleton.getInstance(this).getImageLoader();
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private FragmentSwitcherManager fragSwitcher;
    private Bitmap theImage;

    FavoriteObjHandler favoriteObj;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);


        final String img = getIntent().getStringExtra("Picture");
        Bundle bundle = new Bundle();
        bundle.putString("Title", getIntent().getStringExtra("Title"));

        if(getIntent().getStringExtra("API").equals("Food2Fork")){
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putString("API", "Food2Fork");
        }else{
            bundle.putString("API", "Edamam");
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putStringArray("Ingredients", getIntent().getStringArrayExtra("Ingredients"));
            bundle.putStringArray("Nutrients", getIntent().getStringArrayExtra("Nutrients"));
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
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), bundle);
        mViewPager = (ViewPager) findViewById(R.id.main_pages);
        mViewPager.setAdapter(mCustomPagerAdapter);
        fragSwitcher = new FragmentSwitcherManager(mViewPager);

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
        
    }

}