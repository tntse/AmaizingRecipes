package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;

import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;

/**
 * Created by chris on 11/9/15.
 */
public class FavoriteObjHandler {

    String recipeID;
    String title;
    private long id;
    dbHandler handler;

    /**
     * Default constructor for FavoriteObjHandler. Sets handler default.
     * @param context
     */
    public FavoriteObjHandler(Context context) {
        handler = new dbHandler(context, null, null, 1);
    }

    public FavoriteObjHandler(){}


    /**
     * Getter for handler instance
     * @return dbHandler handler
     */
    public dbHandler getHandler() {
        return handler;
    }

    /**
     * Getter for id
     * @return long id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for title
     * @return string title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets recipe id
     * @return recipeID
     */
    public String getRecipeID() {
        return recipeID;
    }

    /**
     * Sets recipe id
     * @param recipeID String
     */
    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    /**
     * Used by Favorites to display as ListView
     * @return String title
     */
    @Override
    public String toString() {
        return title;
    }
}
