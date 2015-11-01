package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.String;


/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 *
 */
public class Favorites extends Activity {


    RecipeShow recipeObject;
    String title;
    TextView favoritesList;
    dbHandler handler;

    /**
     * Pulls up saved database state onCreate
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        favoritesList = (TextView) findViewById(R.id.favoritesList);
        handler = new dbHandler(this, null, null, 1);
        printDatabase();
    }

    /**
     * Passes ingredient to handler to store in database
     * @param view the current view of device
     */
    public void addButtonClicked(View view){
        title = recipeObject.sendTitle();
        handler.addRecipe(title);
        printDatabase();
    }

    /**
     * Deletes an row from database
     *
     * Currently not implemented as there is no delete button yet
     * @param view current device view
     */
    public void deleteButtonClicked(View view) {
        title = recipeObject.sendTitle();
        handler.deleteIngredient(title);
        printDatabase();
    }

    /**
     * Uses handle to get and print database
     */
    public void printDatabase(){
        String dbString = handler.databaseToString();
        favoritesList.setText(dbString);
    }
}
