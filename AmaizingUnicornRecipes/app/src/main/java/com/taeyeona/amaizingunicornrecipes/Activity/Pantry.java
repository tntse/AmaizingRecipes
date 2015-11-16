package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Created by Hao on 9/26/2015.
 */


public class Pantry extends Activity
{
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry);
        final MediaPlayer kitty = MediaPlayer.create(this,R.raw.kitty);


        // typeCast .xml returns view while pantry_GridView is gridView obj
        GridView pantry_GridView;
        pantry_GridView = (GridView) findViewById(R.id.pantry_gridview);
        // pantry_GridView.setAdapter(new ImageAdapter(this));


        Button search = (Button) findViewById(R.id.button);
        Button favorites = (Button) findViewById(R.id.favorites);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et = (EditText) findViewById(R.id.editText);
                String st = parseString(et.getText().toString()).trim();
                if(st.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "You entered empty, don't do that"
                    , Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Intent intent = new Intent(Pantry.this, RecipeSearch.class).putExtra("Ingredients", st);
                    startActivity(intent);
                }

            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Pantry.this, Favorites.class);
                startActivity(intent);
            }
        });


    }

    private String parseString(String s){
        String st = "";
        for(int i = 0; i<s.length(); i++){
            if(s.charAt(i) == ' '){
                if(s.charAt(i-1) == ','){
                    st = st+s.charAt(i+1);
                    i++;
                }else{
                    st = st+"%20";
                }
            }else{
                st = st+s.charAt(i);
            }
        }
        return st;
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

            //scale and pad gridview elements
            imageView.setLayoutParams(new GridView.LayoutParams(85,85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,2,2,2);
            imageView.setImageResource(ingredientImg[position]);
            return imageView;
        }

    }


}
