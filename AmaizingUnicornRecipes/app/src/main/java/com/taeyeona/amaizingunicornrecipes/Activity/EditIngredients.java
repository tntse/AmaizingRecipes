package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.Set;

/**
 * Edit Ingredients Activity
 *
 * @author Thomas Tse
 * @version 1.0
 * @since 2015-11-11
 */
public class EditIngredients extends Activity {
    private EditText editText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private IngredientsManager manager;
    private TextView ingredientsList;

    /**
     * Sets all private variables and displays the current list of Ingredients
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in. Otherwise null
     */
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

    /**
     * Removes the specified ingredient from the list of ingredients.
     * @param view
     */
    public void deleteButtonClicked(View view){
        String toBeDeleted = editText.getText().toString().toLowerCase();
        if(manager.remove(toBeDeleted)) {
            editor.putStringSet("Ingredients", manager);
            editor.commit();
            updateList();
        }else{
            Toast.makeText(this, "Ingredient does not exist.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Adds the specified ingredient to the current list of ingredients.
     * @param view
     */
    public void addButtonClicked(View view){
        String toBeAdded = editText.getText().toString().toLowerCase();
        if(!manager.contains(toBeAdded)) {
            manager.add(toBeAdded);
            editor.putStringSet("Ingredients", manager);
            editor.commit();
            updateList();
        }else{
            Toast.makeText(this, "Ingredient exists already.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates the TextView in the activity with the current list of ingredients.
     */
    public void updateList(){
        ingredientsList.setText("Ingredients: \n" + manager.toString());
    }


}
