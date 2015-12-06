package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;

import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.Recipes;


/**
 * Created by annasever on 11/15/15.
 */
public class FavoritesAdapter extends Activity {
    private Favorites datasource;
    String title;
    TextView favoritesList;
    EditText deleteInput;
    Recipes fav;
    Button delete;


    private DrawerLayout drawerLayout;
    private ListView navListView;
    private String[] navListName;


    private SQLiteDatabase database;
    private dbHandler handler;
    private String[] allColumns = { handler.COLUMN_ID,
            handler.COLUMN_TITLE };

    /**
     * Pulls up saved database state onCreate
     *
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites);

            /**
             * Saved variables for drawerListView and drawerListNames,
             * navListName are array items in strings.xml
             * navListView is the list to be adapter for the listnme to be viewable
             * in simple list item format
             */
            drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
            navListName = getResources().getStringArray(R.array.drawer_list);

            navListView = (ListView) findViewById((R.id.nav_drawer));
            navListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navListName));
            navListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

            fav = new Recipes(getApplicationContext());
            deleteInput = (EditText) findViewById(R.id.deleteField);
            favoritesList = (TextView) findViewById(R.id.favoritesList);
    }

//    @Override
//    protected void onResume() {
//        datasource.open();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        datasource.close();
//        super.onPause();
//    }

}
