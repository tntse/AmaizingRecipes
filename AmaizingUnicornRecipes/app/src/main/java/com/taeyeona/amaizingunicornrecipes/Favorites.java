package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
    EditText deleteInput;


    /**
     * Pulls up saved database state onCreate
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        deleteInput = (EditText) findViewById(R.id.deleteField);
        favoritesList = (TextView) findViewById(R.id.favoritesList);
        handler = new dbHandler(this, null, null, 1);
        printDatabase();

        favoritesList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favoritesList.setTextColor(Color.CYAN);
//                Intent intent = new Intent(Favorites.this, RecipeSearch.class);
//                startActivity(intent);
            }
        });
    }

    /**
     * Passes recipe title to handler to store in database
     * @param title recipe title
     */
    public void storeRecipe(String title){
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

        String deleteText = deleteInput.getText().toString();
        handler.deleteRecipe(deleteText);
        printDatabase();
    }

    /**
     * Uses handle to get and print database
     */
    public void printDatabase(){
        String dbString = handler.databaseToString();
        favoritesList.setText(dbString);
        deleteInput.setText("");
    }


}
