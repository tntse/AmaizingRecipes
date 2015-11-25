package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by tricianemiroff on 11/5/15.
 */
public class MissingIngredients extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_ingredients);
        Log.d(MissingIngredients.class.getSimpleName(), "I'm here");

        String ingredients = this.getIntent().getStringExtra("Ingredients");
        SharedPreferences sharedPreferences = this.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);

        String[] rawIngredients = ingredients.split("\n");
        Log.d(MissingIngredients.class.getSimpleName(), "raw Ingredients are: " + rawIngredients.toString());

        Set<String> manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        Log.d(MissingIngredients.class.getSimpleName(), "manager instance of IngredientsManager: " + (manager instanceof IngredientsManager));
        if(!(manager instanceof IngredientsManager)){

            manager = new IngredientsManager(manager);
        }

        Log.d(MissingIngredients.class.getSimpleName(), "manager " + manager);
        String[] missingIngredients = ((IngredientsManager)manager).findMissingIngredients(rawIngredients);

        ListView theListView = (ListView) findViewById(R.id.missing_ingre_list);

        theListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, missingIngredients));

        Button maps = (Button) findViewById(R.id.find_store_button);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissingIngredients.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}
