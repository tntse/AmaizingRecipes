package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;

import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau on 10/5/2015.
 */
public class Recipes {

    private String publisher;
    private String f2fUrl;
    private String title;
    private String sourceUrl;
    private String recipeId;
    private String imageUrl;
    private Double socialRank;
    private String publisherUrl;
    private List<String> ingredients;
    private List<String> nutrients;
    private String nutrient;
    private long id;
    private dbHandler handler;
    private String api;
    private String ingredientList;

    public Recipes(){
    }

    public Recipes(String pub, String ftfUrl, String titl, String srcUrl, String id,
                   String imgUrl, Double rank, String pubUrl){
        publisher = pub;
        f2fUrl = ftfUrl;
        title = titl;
        sourceUrl = srcUrl;
        recipeId = id;
        imageUrl = imgUrl;
        socialRank = Math.floor(rank * 100) / 100;
        publisherUrl = pubUrl;
        ingredients = new ArrayList<String>();
        nutrients = new ArrayList<String>();
    }

    public Recipes(Context context) {
        handler = new dbHandler(context);
    }


    public Recipes(String[] dataFieldsToSet){

        setId(Long.parseLong(dataFieldsToSet[0]));
        setTitle(dataFieldsToSet[1]);
        setImageUrl(dataFieldsToSet[2]);
        setIngredientList(dataFieldsToSet[3]);
        setNutrients(dataFieldsToSet[4]);
        setRecipeId(dataFieldsToSet[5]);
        setPublisher(dataFieldsToSet[6]);
        setSourceUrl(dataFieldsToSet[7]);
        setApi(dataFieldsToSet[8]);

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
     * @return The publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher The publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return The f2fUrl
     */
    public String getF2fUrl() {
        return f2fUrl;
    }

    /**
     * @param f2fUrl The f2f_url
     */
    public void setF2fUrl(String f2fUrl) {
        this.f2fUrl = f2fUrl;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The sourceUrl
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * @param sourceUrl The source_url
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * @return The recipeId
     */
    public String getRecipeId() {
        return recipeId;
    }

    /**
     * @param recipeId The recipe_id
     */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public List<String> getIngredients(){
        return ingredients;
    }

    public void setIngredients(String ingredient){
        ingredients.add(ingredient);
    }

    public List<String> getNutrients(){
        return nutrients;
    }

    public void setNutrientList(String nutrient){
        nutrients.add(nutrient);
    }

    public void setNutrients(String nutrients){
        this.nutrient = nutrients;
    }

    public String[] getNutrientsArray() {
        return convertStringToArray(nutrient);
    }

    public void setNutrientsArray(String nutrients) {
        this.nutrient = nutrients;
    }

    public String[] getIngredientList() {
        return convertStringToArray(ingredientList);
    }

    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * @return The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return The socialRank
     */
    public Double getSocialRank() {
        return socialRank;
    }

    /**
     * @param socialRank The social_rank
     */
    public void setSocialRank(Double socialRank) {
        this.socialRank = socialRank;
    }

    /**
     * @return The publisherUrl
     */
    public String getPublisherUrl() {
        return publisherUrl;
    }

    /**
     * @param publisherUrl The publisher_url
     */
    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

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

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
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
