package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
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
public class Favorites extends Activity implements AdapterView.OnItemClickListener {


    private Favorites datasource;
    String title;
    ListView favoritesList;
    EditText deleteInput;
    FavoritesPage fav;
    Button delete;

    private DrawerLayout drawerLayout;
    private ListView navListView;
    private String[] navListName;


    private SQLiteDatabase database;
    private dbHandler handler;
    private String[] allColumns = { handler.COLUMN_ID,
            handler.COLUMN_TITLE };


    /**
     * Creates a handler for fetching title from db
     * @param context
     */
    public Favorites(Context context) {

        handler = new dbHandler(context);
    }

    /**
     * Passes recipe title to handler to store in database
     * @param title recipe title
     */
    public void storeRecipe(String title){
        handler.addRecipe(title);
    }

    /**
     * Gets database on open
     * @throws SQLException
     */
    public void open() {
        database = handler.getWritableDatabase();

//        @Override
//        protected void onCreate (Bundle savedInstanceState){
//
//
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.favorites);
//
//            /**
//             * Saved variables for drawerListView and drawerListNames,
//             * navListName are array items in strings.xml
//             * navListView is the list to be adapter for the listnme to be viewable
//             * in simple list item format
//             */
//            drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
//            navListName = getResources().getStringArray(R.array.drawer_list);
//
//            navListView = (ListView) findViewById((R.id.nav_drawer));
//            navListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navListName));
//            navListView.setOnItemClickListener(this);
//
//            fav = new FavoritesPage(getApplicationContext());
//            deleteInput = (EditText) findViewById(R.id.deleteField);
//            favoritesList = (TextView) findViewById(R.id.favoritesList);
//        }
    }

    /**
     * Closes handler instance
     */
    public void close() {
        handler.close();
    }

    /**
     * Creates a new title
     * @param title
     * @return
     */
    public FavoritesPage createTitle(String title) {
        ContentValues values = new ContentValues();
        values.put(dbHandler.COLUMN_TITLE, title);
        long insertId = database.insert(dbHandler.TABLE_FAVORITES, null,
                values);
        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
                allColumns, dbHandler.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FavoritesPage newTitle = cursorToTitle(cursor);
        cursor.close();
        return newTitle;
    }

    /**
     * Sets the cursor and data fields to title
     * @param cursor
     * @return
     */
    private FavoritesPage cursorToTitle(Cursor cursor) {
        FavoritesPage title = new FavoritesPage();
        title.setId(cursor.getLong(0));
        title.setTitle(cursor.getString(1));
        return title;
    }

    public List<FavoritesPage> getAllFavorites() {
        List<FavoritesPage> favorites = new ArrayList<FavoritesPage>();

        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavoritesPage favorite = cursorToTitle(cursor);
            favorites.add(favorite);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return favorites;
    }

    /**
     * Deletes an row from database
     *
     * Currently not implemented as there is no delete button yet
     * @param item FavoritesPage object to delete
     */
    public void deleteFavorite(FavoritesPage item) {
        long id = item.getId();
        handler.deleteRecipe(id);
    }
/*
    public void searchFavorite(FavoritesPage favorite) {
        String title = favorite.getTitle();

        Intent intent = new Intent(Favorites.this, RecipeSearch_.class).putExtra("Ingredients", title);
        startActivity(intent);
    }*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



        if(position==0){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        }else if(position==1){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        }else if(position==2) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);


        }else if(position == 3){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        }else if(position==4){
            Intent intent = new Intent(this,Favorites.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
        }

    }
}
