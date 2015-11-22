package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.FavoriteObjHandler;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.List;


/**
 * Created by annasever on 11/15/15.
 */
public class FavoritesAdapter extends Activity {
    private Favorites datasource;
    String title;
    ListView favoritesList;
    EditText deleteInput;
    FavoriteObjHandler fav;
    Button delete;


    /**
     * Pulls up saved database state onCreate
     *
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        Log.d("meow", "displaying le favorites page.");
        fav = new FavoriteObjHandler();
        deleteInput = (EditText) findViewById(R.id.deleteField);
        delete = (Button) findViewById(R.id.deleteButton);
        favoritesList = (ListView) findViewById(R.id.nav_drawer);

        datasource = new Favorites(this);
        datasource.open();

        List<FavoriteObjHandler> values = datasource.getAllFavorites();

        ArrayAdapter<FavoriteObjHandler> adapter = new ArrayAdapter<FavoriteObjHandler>(this,
                android.R.layout.simple_list_item_1, values);
        favoritesList.setAdapter(adapter);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressWarnings("unchecked")
                ArrayAdapter<FavoriteObjHandler> adapter = (ArrayAdapter<FavoriteObjHandler>) favoritesList.getAdapter();
                FavoriteObjHandler favorite = null;
                if (favoritesList.getAdapter().getCount() > 0) {
                    favorite = (FavoriteObjHandler) favoritesList.getAdapter().getItem(0);
                    datasource.deleteFavorite(favorite);
                    adapter.remove(favorite);
                }

                adapter.notifyDataSetChanged();
                deleteInput.setText("");
            }
        });


        Log.d("meow", "stupid on item click listener");
        favoritesList.isClickable();
        favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d("meow", "something was clicked.");
                String item = ((TextView) view).getText().toString();

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });
        Log.d("meow", "the listener made it");
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
