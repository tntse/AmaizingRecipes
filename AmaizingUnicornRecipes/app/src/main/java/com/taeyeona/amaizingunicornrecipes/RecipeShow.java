package com.taeyeona.amaizingunicornrecipes;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import android.support.v4.view.ViewPager;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Chau on 10/13/2015.
 */
public class RecipeShow extends AppCompatActivity{

    private ImageView image;
    private ImageLoader imgLoader = VolleySingleton.getInstance(this).getImageLoader();
    private CustomPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        final String img = getIntent().getStringExtra("Picture");
        Bundle bundle = new Bundle();
        bundle.putString("Title", getIntent().getStringExtra("Title"));
        if(getIntent().getStringExtra("API").equals("Food2Fork")){
            bundle.putString("RecipeID", getIntent().getStringExtra("RecipeID"));
            bundle.putString("API", "Food2Fork");
        }else{
            bundle.putString("API", "Edamam");
        }

        image = (ImageView) findViewById(R.id.imageView2);

        imgLoader.get(img, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                image.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), bundle);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        
    }
}