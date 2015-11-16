package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taeyeona.amaizingunicornrecipes.FavoritesPage;

/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 *
 */
public class Favorites extends Activity {



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
}
