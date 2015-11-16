package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.List;

import static com.taeyeona.amaizingunicornrecipes.R.layout.favorites;

/**
 * Created by annasever on 11/15/15.
 */
public class FavoritesAdapter extends ListActivity {
    private Favorites datasource;
    String title;
    ListView favoritesList;
    EditText deleteInput;
    FavoritesPage fav;
    Button delete;


    /**
     * Pulls up saved database state onCreate
     *
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(favorites);
        fav = new FavoritesPage();
        deleteInput = (EditText) findViewById(R.id.deleteField);
        delete = (Button) findViewById(R.id.deleteButton);
        favoritesList = (ListView) findViewById(R.id.list);

        datasource = new Favorites(this);
        datasource.open();

        List<FavoritesPage> values = datasource.getAllFavorites();

        ArrayAdapter<FavoritesPage> adapter = new ArrayAdapter<FavoritesPage>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressWarnings("unchecked")
                ArrayAdapter<FavoritesPage> adapter = (ArrayAdapter<FavoritesPage>) getListAdapter();
                FavoritesPage favorite = null;
                if (getListAdapter().getCount() > 0) {
                    favorite = (FavoritesPage) getListAdapter().getItem(0);
                    datasource.deleteFavorite(favorite);
                    adapter.remove(favorite);
                }

                adapter.notifyDataSetChanged();
                deleteInput.setText("");
            }
        });

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
