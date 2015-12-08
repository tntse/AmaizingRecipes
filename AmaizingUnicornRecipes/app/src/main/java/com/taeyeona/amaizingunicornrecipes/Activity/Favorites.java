package com.taeyeona.amaizingunicornrecipes.Activity;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.taeyeona.amaizingunicornrecipes.Recipes;
import com.taeyeona.amaizingunicornrecipes.Recipes;

/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 */
public class Favorites {

    private String title;
    private String picture;

    private dbHandler handler;
    private String[] allColumns = {dbHandler.COLUMN_ID,
            dbHandler.COLUMN_TITLE, dbHandler.COLUMN_PICTURE, dbHandler.COLUMN_INGREDIENTS, dbHandler.COLUMN_NUTRIENTS,
            dbHandler.COLUMN_RECIPEID, dbHandler.COLUMN_SOURCENAME, dbHandler.COLUMN_SOURCEURL, dbHandler.COLUMN_API};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
     *
     */
    public String[] getTitlesFromDB() {
        return handler.getAllTitles();
    }

    /**
     * Closes handler instance
     */
    public void close() {
        handler.close();
    }

    /**
     * Deletes an row from database
     * <p/>
     * Currently not implemented as there is no delete button yet
     *
     * @param item Recipes object to delete
     */
    public void deleteFavorite(Recipes item) {
        long id = item.getId();
        handler.deleteRecipe(id);
    }

    /**
     *
     * @return
     */
    public String getTitle(){
        return title;
    }

    /**
     *
     * @param recipes
     */
    public void setTitle(Recipes recipes) {
       title = recipes.getTitle();
    }

    /**
     *
     * @return
     */
    public String getPicture () {
        return picture;
    }

    /**
     *
     * @param recipeTitleToSearch
     * @return
     */
    public Recipes searchFavorite(String recipeTitleToSearch) {
        Recipes recipeToSearchFor = handler.getRowInDatabase(recipeTitleToSearch);
        return recipeToSearchFor;
    }

    /**
     * Passes recipe id to database handler to check if recipe has been stored.
     * @param recipe_id Recipe to check.
     * @return Boolean for whether stored or not.
     */
    public boolean checkIfFavorited(String recipe_id, String api) {
        if(handler.containsRecipe(recipe_id, api)) {
            return true;
        } else {
            return false;
        }
    }
}
