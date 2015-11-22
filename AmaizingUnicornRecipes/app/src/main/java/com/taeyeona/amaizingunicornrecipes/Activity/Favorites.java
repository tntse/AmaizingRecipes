package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taeyeona.amaizingunicornrecipes.FavoriteObjHandler;
import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 *
 */
public class Favorites extends Activity implements AdapterView.OnItemClickListener {


    EditText deleteInput;
    FavoriteObjHandler fav;
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
    public void storeRecipe(String title, String recipeID){
        handler.addRecipe(title, recipeID);
    }

    /**
     * Gets database on open
     * @throws SQLException
     */
    public void open() {
        database = handler.getWritableDatabase();

    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        fav = new FavoriteObjHandler();
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
//        fav = new FavoriteObjHandler(getApplicationContext());
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
     * @param title
     * @return
     */
    public FavoriteObjHandler createTitle(String title) {
        ContentValues values = new ContentValues();
        values.put(dbHandler.COLUMN_TITLE, title);
        long insertId = database.insert(dbHandler.TABLE_FAVORITES, null,
                values);
        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
                allColumns, dbHandler.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FavoriteObjHandler newTitle = cursorToTitle(cursor);
        cursor.close();
        return newTitle;
    }

    /**
     * Sets the recipe id
     * @param r_id
     * @return
     */
    public FavoriteObjHandler createRecipeID(String r_id) {
        ContentValues values = new ContentValues();
        values.put(dbHandler.COLUMN_RID, r_id);
        long insertId = database.insert(dbHandler.TABLE_FAVORITES, null,
                values);
        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
                allColumns, dbHandler.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FavoriteObjHandler newRID = cursorToID(cursor);
        cursor.close();
        return newRID;
    }

    /**
     * Moves cursor to title
     * @param cursor
     * @return
     */
    private FavoriteObjHandler cursorToTitle(Cursor cursor) {
        FavoriteObjHandler title = new FavoriteObjHandler();
        title.setId(cursor.getLong(0));
        title.setTitle(cursor.getString(1));
        return title;
    }

    /**
     * Moves cursor to recipe id
     * @param cursor
     * @return
     */
    private FavoriteObjHandler cursorToID(Cursor cursor) {
        FavoriteObjHandler id = new FavoriteObjHandler();
        id.setId(cursor.getLong(0));
        id.setRecipeID(cursor.getString(1));
        return id;
    }

    public List<FavoriteObjHandler> getAllFavorites() {
        List<FavoriteObjHandler> favorites = new ArrayList<FavoriteObjHandler>();

        Cursor cursor = database.query(dbHandler.TABLE_FAVORITES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavoriteObjHandler favorite = cursorToTitle(cursor);
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
     * @param item FavoriteObjHandler object to delete
     */
    public void deleteFavorite(FavoriteObjHandler item) {
        long id = item.getId();
        handler.deleteRecipe(id);
    }

    public void searchFavorite(FavoriteObjHandler favorite) {
        String title = favorite.getTitle();
        Intent intent = new Intent(Favorites.this, RecipeSearch.class).putExtra("Ingredients", title);
        startActivity(intent);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        <item>Main Menu</item> 0
//        <item>Ingredients Page</item>> 1
//        <item>Search Recipe</item> 2
//        <item>Edit Profile</item> 3
//        <item>Favorites Page</item> 4

        if(position==0){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        }else if(position==1){
            Intent intent = new Intent(this,EditIngredients.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        }else if(position==2) {
            Intent intent = new Intent(this, Pantry.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);


        }else if(position == 3){
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        }else if(position==4){
            Intent intent = new Intent(this,Favorites.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
        }

    }
}
