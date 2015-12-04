package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;

import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;

/**
 * Created by chris on 11/9/15.
 */
public class FavoritesPage {

    String title;
    String sourceName;
    String sourceUrl;
    String picture;
    String recipeId;
    String ingredientList;
    String nutrients;
    private long id;
    dbHandler handler;
    String api;

    public FavoritesPage() {
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

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

    public String[] getIngredientList() {
        return convertStringToArray(ingredientList);
    }

    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String[] getNutrients() {
        return convertStringToArray(nutrients);
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    /**
     * Default constructor for FavoritesPage. Sets handler default.
     * @param context
     */
    public FavoritesPage(Context context) {
        handler = new dbHandler(context);
    }


    public FavoritesPage(String[] dataFieldsToSet){

        setId(Long.parseLong(dataFieldsToSet[0]));
        setTitle(dataFieldsToSet[1]);
        setPicture(dataFieldsToSet[2]);
        setIngredientList(dataFieldsToSet[3]);
        setNutrients(dataFieldsToSet[4]);
        setRecipeId(dataFieldsToSet[5]);
        setSourceName(dataFieldsToSet[6]);
        setSourceUrl(dataFieldsToSet[7]);
        setApi(dataFieldsToSet[8]);

    }

    //order: _id, title, picture, ingredients, nutrients, recipeid, sourcename, sourceurl, api

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
     * Used by Favorites to display as ListView
     * @return String title
     */
    @Override
    public String toString() {
        return title;
    }

    public static String[] convertStringToArray(String str) {
        String strSeparator = ",";
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
