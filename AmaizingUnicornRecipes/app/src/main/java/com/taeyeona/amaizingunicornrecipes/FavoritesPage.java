package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;

import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;

/**
 * Created by chris on 11/9/15.
 */
public class FavoritesPage {

    String recipeID;
    String title;
    String sourceName;
    String sourceUrl;
    String picture;
    String recipeId;
    String ingredientList;
    String nutrients;
    private long id;
    dbHandler handler;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getNutrients() {
        return nutrients;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    /**
     * Default constructor for FavoritesPage. Sets handler default.
     * @param context
     */
    public FavoritesPage(Context context) {
        handler = new dbHandler(context, null, null, 1);
    }

    public FavoritesPage(){}


    /**
     * Getter for handler instance
     * @return dbHandler handler
     */
    public dbHandler getHandler() {
        return handler;
    }

    public void setHandler(dbHandler handler) {
        this.handler = handler;
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
