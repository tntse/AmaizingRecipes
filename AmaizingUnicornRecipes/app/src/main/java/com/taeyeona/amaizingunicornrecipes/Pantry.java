package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Hao on 9/26/2015.
 */


public class Pantry extends Activity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry);

        //typeCast .xml returns view while pantry_GridView is gridView obj
        GridView pantry_GridView;
        pantry_GridView = (GridView) findViewById(R.id.pantry_gridview);
        pantry_GridView.setAdapter(new ImageAdapter(this));




    }

    public class ImageAdapter extends BaseAdapter{

        private Context myContext;

        public ImageAdapter(Context c){

            myContext = c;
        }
        private Integer[] ingredientImg = {
                R.drawable.test_1,R.drawable.test_2,R.drawable.test_3,
                R.drawable.test_4,R.drawable.test_5,R.drawable.test_6,
                R.drawable.test_7,R.drawable.test_8,R.drawable.test_9,
                R.drawable.test_10,R.drawable.test_11,R.drawable.test_12,
                R.drawable.test_13, R.drawable.test_14,R.drawable.text_15,
                R.drawable.text_16, R.drawable.test_17,R.drawable.test_18,
                R.drawable.test_1,R.drawable.test_2,R.drawable.test_3,
                R.drawable.test_4,R.drawable.test_5,R.drawable.test_6,
                R.drawable.test_7,R.drawable.test_8,R.drawable.test_9,
                R.drawable.test_10,R.drawable.test_11,R.drawable.test_12,
                R.drawable.test_13, R.drawable.test_14,R.drawable.text_15,
                R.drawable.text_16, R.drawable.test_17,R.drawable.test_18,
        };

        public int getCount() {
            return ingredientImg.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(myContext);

            //scale and padd gridview elements
            imageView.setLayoutParams(new GridView.LayoutParams(85,85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(ingredientImg[position]);
            return imageView;
        }

    }


}
