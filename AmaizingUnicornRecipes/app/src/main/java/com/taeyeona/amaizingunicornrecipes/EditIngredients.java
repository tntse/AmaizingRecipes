package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Set;

/**
 * Created by thomastse on 11/7/15.
 */
public class EditIngredients extends Activity {
    EditText editText;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    IngredientsManager manager;
    TextView ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_ingredients);

        sharedPreferences = getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Set<String> set = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        manager = new IngredientsManager(set);
        editText = (EditText) findViewById(R.id.ingredientInput);
        ingredientsList = (TextView) findViewById(R.id.ingredientList);
        updateList();


    }

    public void deleteButtonClicked(View view){
        String toBeDeleted = editText.getText().toString().toLowerCase();
        manager.remove(toBeDeleted);
        editor.putStringSet("Ingredients", manager);
        editor.commit();
        updateList();
    }

    public void addButtonClicked(View view){
        String toBeAdded = editText.getText().toString().toLowerCase();
        if(!manager.contains(toBeAdded)) {
            manager.add(toBeAdded);
            editor.putStringSet("Ingredients", manager);
            editor.commit();
            updateList();
        }
    }

    public void updateList(){
        ingredientsList.setText("Ingredients: \n" + manager.toString());
    }


}
