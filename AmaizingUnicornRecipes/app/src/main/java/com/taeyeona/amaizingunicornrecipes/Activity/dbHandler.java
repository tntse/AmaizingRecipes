package com.taeyeona.amaizingunicornrecipes.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import com.taeyeona.amaizingunicornrecipes.FavoritesPage;

import java.util.ArrayList;

/**
 * Database handler class
 * <p/>
 * <p> Creates a handle to create, store, and call from the database file favorites.db.
 * Has globals: DATABASE_VERSION, DATABASE_NAME, TABLE_FAVORITES, COLUMN_ID,
 * COLUMN_INGREDIENTNAME to store relative fields. Connects to Ingredients class in order
 * to organize ingredients object.</p>
 *
 * @author Anna Sever
 * @version 1.0
 */
public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "favorites.db";
    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PICTURE = "picture";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RECIPEID = "rid";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_NUTRIENTS = "nutrients";
    public static final String COLUMN_SOURCEURL = "sourceUrl";
    public static final String COLUMN_SOURCENAME = "sourceName";
    public static final String COLUMN_API = "api";


//
//    intent.putExtra("Picture", currentRecipe.getImageUrl());
//    intent.putExtra("Title", currentRecipe.getTitle());
//    intent.putExtra("RecipeID", currentRecipe.getRecipeId());
//    intent.putExtra("Ingredients", currentRecipe.getIngredients().toArray(new String[0]));
//    intent.putExtra("Nutrients", currentRecipe.getNutrients().toArray(new String[0]));
//    intent.putExtra("SourceUrl", currentRecipe.getSourceUrl());
//    intent.putExtra("SourceName", currentRecipe.getPublisher());
////    intent.putExtra("API", "Edamam");


    /**
     * Creates a basic handle for the database.
     *
     * @param context //     * @param name
     *                //     * @param factory
     *                //     * @param version
     *                <p/>
     *                public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
     *                super(context, DATABASE_NAME, factory, DATABASE_VERSION);
     *                }
     *                <p/>
     *                /**
     *                ListView updtated constructor for dbHandler
     * @param context
     */
    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Override for onCreate so query is created when opened
     *
     * @param db A SQLite database object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_FAVORITES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT," +
                COLUMN_PICTURE + " TEXT," +
                COLUMN_INGREDIENTS + " TEXT," +
                COLUMN_NUTRIENTS + " TEXT," +
                COLUMN_RECIPEID + " TEXT," +
                COLUMN_SOURCENAME + " TEXT," +
                COLUMN_SOURCEURL + " TEXT," +
                COLUMN_API + " TEXT" + ")";

        db.execSQL(create_table);

    }

    /**
     * Reads from db file when app reopened.
     *
     * @param db         SQLiteDatabase object
     * @param oldVersion int current table version
     * @param newVersion int updated table version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);

    }

    /**
     * Adds a row to table
     *
     * @param recipe Ingredients Used to store new ingredient
     */

    public void addRecipe(String recipe, String rid, String picture,
                          String sourceUrl, String sourceName, String nutrients,
                          String ingredients, String api) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_API, api);
        values.put(COLUMN_TITLE, recipe);
        values.put(COLUMN_PICTURE, picture);
        values.put(COLUMN_SOURCENAME, sourceName);
        values.put(COLUMN_SOURCEURL, sourceUrl);
        values.put(COLUMN_RECIPEID, rid);

        if (api.equals("Edamam")) {
            values.put(COLUMN_NUTRIENTS, nutrients);
            values.put(COLUMN_INGREDIENTS, ingredients);
        } else if (api.equals("Food2Fork")) {
            values.put(COLUMN_NUTRIENTS, " ");
            values.put(COLUMN_INGREDIENTS, " ");
        }

        long rowID = 0;
        rowID = db.insert(TABLE_FAVORITES, null, values);
        Log.isLoggable("dbhandler", (int) rowID);
        db.close();
    }

    /**
     * Delete a ingredient from a database.
     *
     * @param id Id of recipe to delete
     */
    public void deleteRecipe(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_ID + "=\""
                + id + "\";");
    }

    /**
     * Print out database as a string storage/passing
     *
     * @return dbString Database stored as a string.
     */
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * FROM " + TABLE_FAVORITES + " WHERE 1";

        //cursor point to a location in results
        Cursor cursor = db.rawQuery(query, null);

        //move to the first row in results
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("title")) != null) {
                dbString += cursor.getString(cursor.getColumnIndex("title"));
                dbString += "\n";
            }
            cursor.moveToNext();
        }
        db.close();
        return dbString;
    }

//    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
//        return getReadableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
//    }

    public String[] getAllTitles() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_TITLE + " FROM " + TABLE_FAVORITES;
        String dbString = "";
        int cursorpos;

        //Make cursor to go through all titles
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            cursorpos = cursor.getPosition();
            if (cursor.getString(0) != null) {
                dbString += cursor.getString(0);
                dbString += ",";
            }
            cursor.move(1);
        }
        db.close();
        return dbString.split(",");
    }

    /**
     * Selects a row from table and returns it as a FavoritesPage object.
     *
     * @param title Parameter for selecting a row
     * @return
     */
    public FavoritesPage getRowInDatabase(String title) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_TITLE + "=\"" + title + "\"";
        String dbString = "";
        FavoritesPage ret = null;


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            ret = new FavoritesPage();
            ret.setId(Long.parseLong(cursor.getString(0)));
            ret.setTitle(cursor.getString(1));
            ret.setPicture(cursor.getString(2));
            ret.setIngredientList(cursor.getString(3));
            ret.setNutrients(cursor.getString(4));
            ret.setRecipeId(cursor.getString(5));
            ret.setSourceName(cursor.getString(6));
            ret.setSourceUrl(cursor.getString(7));
            ret.setApi(cursor.getString(8));
        }
        db.close();
        return ret;
    }

    public boolean containsRecipe(String recipe_id, String api) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_RECIPEID + " FROM " + TABLE_FAVORITES;
        String apiQuery = "SELECT " + COLUMN_API + " FROM " + TABLE_FAVORITES;

        Cursor cursor = db.rawQuery(query, null);
        Cursor apiCursor = db.rawQuery(apiQuery, null);

        cursor.moveToFirst();
        apiCursor.moveToFirst();

        boolean flag = false;

        while (!cursor.isAfterLast()) {
            if (cursor.getString(0).equals(recipe_id) && apiCursor.getString(0).equals(api)) {
                flag =true;
            }
            apiCursor.move(1);
            cursor.move(1);
        }

        cursor.close();
        apiCursor.close();
        db.close();

        return flag;
    }
}
