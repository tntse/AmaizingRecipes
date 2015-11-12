package com.taeyeona.amaizingunicornrecipes;

import android.graphics.Bitmap;

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


}
