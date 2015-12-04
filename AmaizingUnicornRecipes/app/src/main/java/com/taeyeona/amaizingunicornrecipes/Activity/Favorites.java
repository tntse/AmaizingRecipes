package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 */
public class Favorites {

    String title;
    String picture;

    private dbHandler handler;
    private String[] allColumns = {dbHandler.COLUMN_ID,
            dbHandler.COLUMN_TITLE, dbHandler.COLUMN_PICTURE, dbHandler.COLUMN_INGREDIENTS, dbHandler.COLUMN_NUTRIENTS,
            dbHandler.COLUMN_RECIPEID, dbHandler.COLUMN_SOURCENAME, dbHandler.COLUMN_SOURCEURL, dbHandler.COLUMN_API};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
//
//    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//    COLUMN_TITLE + " TEXT " +
//    COLUMN_PICTURE + " TEXT " +
//    COLUMN_INGREDIENTS + " TEXT " +
//    COLUMN_NUTRIENTS + " TEXT " +
//    COLUMN_RECIPEID + " TEXT " +
//    COLUMN_SOURCENAME + " TEXT " +
//    COLUMN_SOURCEURL + " TEXT " + ");";

    /**
     * Creates a handler for fetching title from db
     *
     * @param context
     */
    public Favorites(Context context) {

        handler = new dbHandler(context);
    }

    /**
     * Passes recipe title to handler to store in database
     *
     * @param
     */

    public void storeRecipe(String recipe, String rid, String picture,
                            String sourceUrl, String sourceName, String nutrients,
                            String ingredients, String api) {

        Log.d("Favorites", "Got in");

        boolean flag = false;

        String[] titles = handler.getAllTitles();

        for (int i = 0; i < handler.getAllTitles().length; i++) {
            if (titles[i].equals(recipe)) {
                flag = true;
            }
        }

        if (!flag) {
            Log.d("Favorites", "Got in 2");
            handler.addRecipe(recipe, rid, picture, sourceUrl, sourceName, nutrients, ingredients, api);
        }
    }

    /**
     * Gets
     *
     * @throws SQLException
     */
//    public void open() {
//        database = handler.getWritableDatabase();
//    }

    public String[] getTitlesFromDB() {
        return handler.getAllTitles();
    }
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


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        fav = new FavoritesPage();
//        deleteInput = (EditText) findViewById(R.id.deleteField);
//        delete = (Button) findViewById(R.id.deleteButton);
//        favoritesList = (ListView) findViewById(R.id.nav_drawer);
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.favorites);
//
//        /**
//         * Saved variables for drawerListView and drawerListNames,
//         * navListName are array items in strings.xml
//         * navListView is the list to be adapter for the listnme to be viewable
//         * in simple list item format
//         */
//        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
//        navListName = getResources().getStringArray(R.array.drawer_list);
//
//        navListView = (ListView)findViewById((R.id.nav_drawer));
//        navListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navListName));
//        navListView.setOnItemClickListener(this);
//
//        fav = new FavoritesPage(getApplicationContext());
//        deleteInput = (EditText) findViewById(R.id.deleteField);
//        favoritesList = (TextView) findViewById(R.id.favoritesList);
//
//        }


    /**
     * Closes handler instance
     */
    public void close() {
        handler.close();
    }

    /**
     * Creates a new title
     *
     * @param title
     * @return
     */
//    public FavoritesPage createTitle(String title) {
//        ContentValues values = new ContentValues();
//        values.put(dbHandler.COLUMN_TITLE, title);
//        long insertId = database.insert(dbHandler.TABLE_FAVORITES, null,
//                values);
//        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
//                allColumns, dbHandler.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        FavoritesPage newTitle = cursorToTitle(cursor);
//        cursor.close();
//        return newTitle;
//    }

    /**
     * Sets the recipe id
     *
     * @param r_id
     * @return
     */
//    public FavoritesPage createRecipeID(String r_id) {
//        ContentValues values = new ContentValues();
//        values.put(dbHandler.COLUMN_RECIPEID, r_id);
//        long insertId = database.insert(dbHandler.TABLE_FAVORITES, null,
//                values);
//        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
//                allColumns, dbHandler.COLUMN_ID + " = " + insertId, null,
//                null, null, null);
//        cursor.moveToFirst();
//        FavoritesPage newRID = cursorToID(cursor);
//        cursor.close();
//        return newRID;
//    }

    /**
     * Moves cursor to title
     *
     * @param cursor
     * @return
     */
//    private FavoritesPage cursorToTitle(Cursor cursor) {
//        FavoritesPage title = new FavoritesPage();
//        title.setId(cursor.getLong(0));
//        title.setTitle(cursor.getString(1));
//        return title;
//    }

    /**
     * Moves cursor to recipe id
     *
     * @param cursor
     * @return
     */
//    private FavoritesPage cursorToID(Cursor cursor) {
//        FavoritesPage id = new FavoritesPage();
//        id.setId(cursor.getLong(0));
//        id.setRecipeId(cursor.getString(1));
//        return id;
//    }

//    public List<FavoritesPage> getAllFavorites() {
//        List<FavoritesPage> favorites = new ArrayList<FavoritesPage>();
//
//        Cursor cursor = this.database.query(dbHandler.TABLE_FAVORITES, allColumns, null, null, null, null, null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            FavoritesPage favorite = cursorToTitle(cursor);
//            favorites.add(favorite);
//            cursor.moveToNext();
//        }
//        // make sure to close the cursor
//        cursor.close();
//        return favorites;
//    }

    /**
     * Deletes an row from database
     * <p/>
     * Currently not implemented as there is no delete button yet
     *
     * @param item FavoritesPage object to delete
     */
    public void deleteFavorite(FavoritesPage item) {
        long id = item.getId();
        handler.deleteRecipe(id);
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(FavoritesPage favoritesPage) {
       title = favoritesPage.getTitle();
    }

    public String getPicture () {
        return picture;
    }

    public FavoritesPage searchFavorite(String recipeTitleToSearch) {

        FavoritesPage recipeToSearchFor = handler.getRowInDatabase(recipeTitleToSearch);
        return recipeToSearchFor;

//        String[] ingredient = convertStringToArray(favorite.getIngredientList());
//        String[] nutrient = convertStringToArray(favorite.getNutrients());

        //  String title = favorite.getTitle();


      //  startActivity(intent);
    }
}
