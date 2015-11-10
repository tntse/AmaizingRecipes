package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


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
    EditText deleteInput;
    FavoritesPage fav;


    /**
     * Pulls up saved database state onCreate
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        fav = new FavoritesPage(getApplicationContext());
        deleteInput = (EditText) findViewById(R.id.deleteField);
        favoritesList = (TextView) findViewById(R.id.favoritesList);
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
     * Uses handle to get and print database
     */
    public void printDatabase(){
        String dbString = fav.getHandler().databaseToString();
        favoritesList.setText(dbString);
        deleteInput.setText("");
    }

    /**
     * Deletes an row from database
     *
     * Currently not implemented as there is no delete button yet
     * @param view current device view
     */
    public void deleteButtonClicked(View view) {

        String deleteText = deleteInput.getText().toString();
        fav.getHandler().deleteRecipe(deleteText);
        printDatabase();
    }

}
