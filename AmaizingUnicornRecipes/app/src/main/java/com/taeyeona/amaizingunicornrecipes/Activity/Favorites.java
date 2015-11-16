package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 *
 */
public class Favorites extends Activity {

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
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Favorites.this, RecipeSearch.class);
//                Cursor theCursor = ((SimpleCursorAdapter)((TextView) favoritesList).getAdapter()).getCursor();
//                String selection = theCursor.getString(theCursor.getColumnIndex("favorites.title"));
//                intent.putExtra("Ingredients", selection);
                startActivity(intent);
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
