package com.taeyeona.amaizingunicornrecipes;

/**
 * Ingredients class to format storage in database.
 * @author Anna Sever
 */
public class Ingredients {

    private int _id;
    private String _ingredientName;

    /**
     * Default constructor for ingredient list
     */
    public Ingredients(){
    }


    /**
     * Sets the ingredient name.
     * @param _ingredientName
     */
    public Ingredients(String _ingredientName) {

        this._ingredientName = _ingredientName;
    }

    /**
     * Sets ingredient id.
     * @param _id
     */
    public void setId(int _id) {

        this._id = _id;
    }

    /**
     * Sets the ingredient name.
     * @param _ingredientName
     */
    public void setIngredientName(String _ingredientName) {

        this._ingredientName = _ingredientName + "\n";
    }

    /**
     * Returns ingredient id.
     * @return _id
     */
    public int getId() {

        return _id;
    }

    /**
     * Returns ingredient name.
     * @return _ingredientName Name of stored ingredient.
     */
    public String getIngredientName() {

        return _ingredientName;
    }
}
