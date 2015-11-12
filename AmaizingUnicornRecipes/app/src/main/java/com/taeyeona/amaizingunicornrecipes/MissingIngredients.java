package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by tricianemiroff on 11/5/15.
 */
public class MissingIngredients extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_ingredients);
        Log.d(MissingIngredients.class.getSimpleName(), "I'm here");

        Button maps = (Button) findViewById(R.id.button3);

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissingIngredients.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
