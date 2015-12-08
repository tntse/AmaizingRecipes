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
    private ArrayList<Integer> dailyTotals;
    private String nutrient;
    private long id;
    private dbHandler handler;
    private String api;
    private String ingredientList;
    private int servings = 1;

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
        dailyTotals = new ArrayList<Integer>();
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

    /**
     *
     * @return The list of ingredients as a List<String>
     */
    public List<String> getIngredients(){
        return ingredients;
    }

    /**
     *
     * @param ingredient The ingredients to be added into the List<Sring>
     */

    public void setIngredients(String ingredient){
        ingredients.add(ingredient);
    }

    /**
     *
     * @return The list of nutrients as a List<String>
     */

    public List<String> getNutrients(){
        return nutrients;
    }

    /**
     *
     * @param nutrient The nutrient to be added into the List<String>
     */

    public void setNutrientList(String nutrient){
        nutrients.add(nutrient);
    }

    /**
     *
     * @param nutrients The list of nutrients as a string
     */

    public void setNutrients(String nutrients){
        this.nutrient = nutrients;
    }

    /**
     *
     * @return The String Array converted from the nutrient string
     */
    public String[] getNutrientsArray() {
        return convertStringToArray(nutrient);
    }

    /**
     *
     * @return The String Array converted from the ingredientList string
     */
    public String[] getIngredientList() {
        return convertStringToArray(ingredientList);
    }

    /**
     *
     * @param ingredientList The list of ingredients in the format of a string
     */
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
     * @return The amount of servings that the recipe yields
     */

    public int getServings(){ return servings; }

    /**
     * @param serves The amount of servings that the recipe yields
     */

    public void setServings(int serves){ this.servings = serves; }

    /**
     * @return The percent daily values for the recipe
     */

    public ArrayList<Integer> getDailyTotals() {
        return dailyTotals;
    }

    /**
     *
     * @param dailyTotals The percent daily values for the recipe
     */

    public void setDailyTotals(ArrayList<Integer> dailyTotals) {
        this.dailyTotals = dailyTotals;
    }

    /**
     * Getter for handler instance
     * @return dbHandler handler
     */
    public dbHandler getHandler() {
        return handler;
    }

    /**
     *
     * @param handler The database handler
     */
    public void setHandler(dbHandler handler) {
        this.handler = handler;
    }

    /**
     *
     * @return The API used to get the recipe
     */
    public String getApi() {
        return api;
    }

    /**
     *
     * @param api The api used to get the recipe
     */
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

    /**
     *
     * @param str Either the ingredients or nutrients string
     * @return The String array converted from the string parameter
     */
    public static String[] convertStringToArray(String str) {
        String strSeparator = ",";
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
