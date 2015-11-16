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
        StringTokenizer tokenizer = new StringTokenizer(ingredients, "\n");
        ArrayList<String> ingredientsList = new ArrayList<String>();
        while(tokenizer.hasMoreTokens()){
            ingredientsList.add(tokenizer.nextToken().trim());
        }
        String[] rawIngredients = (String[]) ingredientsList.toArray();

        Set<String> manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());

        if(!(manager instanceof IngredientsManager)){
            manager = new IngredientsManager(manager);
        }

        String[] missingIngredients = ((IngredientsManager)manager).findMissingIngredients(rawIngredients);

        ListView theListView = (ListView) findViewById(R.id.listView);

        theListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, missingIngredients));

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
