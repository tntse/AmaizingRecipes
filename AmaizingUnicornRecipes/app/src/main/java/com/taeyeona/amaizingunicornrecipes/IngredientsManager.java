package com.taeyeona.amaizingunicornrecipes;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Ingredients Manager maintains the list of Ingredients that the user possesses.
 * @author Thomas Tse
 * @version 1.1
 * @since 2015-11-07
 */
public class IngredientsManager implements Set<String> {
    /**
     * Contains the list of Ingredients.
     */
    private ArrayList<String> list;


    /**
     *  Default constructor.
     */
    public IngredientsManager(){
        list = new ArrayList<String>();
    }

    /**
     * Takes in a String representation of the Ingredients List to maintain.
     * @param setList
     */
    public IngredientsManager(ArrayList<String> setList){
        list = setList;
    }

    /**
     * Takes a Set of Strings and turns it into an IngredientManager.
     * @param other
     */
    public IngredientsManager(Set<String> other){
        list = new ArrayList<>();
        list.addAll(other);
    }

    /**
     * Adds an ingredient to the list.
     * @param s Ingredient String to add to the List
     * @return  true if the String is added successfully, otherwise false.
     */
    @Override
    public boolean add(String s) {
        return list.add(s);
    }

    /**
     * Adds all of the specified Collection to the list of Ingredients
     * @param collection a Collection of String objects to be added to the list of Ingredients.
     * @return true if successful, otherwise false.
     */
    @Override
    public boolean addAll(Collection<? extends String> collection) {
        return list.addAll(collection);
    }

    /**
     * Clears the list of Ingredients.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Checks the list for the specified Object.
     * @param o Object to check for in the list of Ingredients.
     * @return true if the object is in the list, otherwise false.
     */
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    /**
     * Checks the list for all Objects in a collection.
     * @param collection Object containing a list of Ingredients to check for.
     * @return true if the list contains all Objects in the collection, otherwise false.
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    /**
     * Compares this Object and another object.
     * @param o Object to be compared to.
     * @return true if the other object is an instance of IngredientsManager and if they have the same list of ingredients, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        IngredientsManager other = o instanceof IngredientsManager ? ((IngredientsManager) o) : null;
        if(other == null)
            return false;
        return other.containsAll(list);
    }

    /**
     * Returns the hash code value for this list.
     * @return The Ingredient list's hash code.
     */
    @Override
    public int hashCode() {
        return list.hashCode();
    }

    /**
     * Checks if the current list is empty.
     * @return true if the list is empty, otherwise false.
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return list's iterator.
     */
    @NonNull
    @Override
    public Iterator<String> iterator() {
        return list.iterator();
    }

    /**
     * Removes an object from the list.
     * @param o Object to be removed.
     * @return true if o is removed, otherwise false.
     */
    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    /**
     * Removes a list of objects from the list.
     * @param collection list of objects to be removed.
     * @return true if the list is removed, otherwise false.
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    /**
     * Removes all elements in the list except for those listed in the collection.
     * @param collection A list of objects to retain.
     * @return true if successful, otherwise false.
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    /**
     * Returns the size of the list.
     * @return the size of the list.
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Converts the list of String to an Array.
     * @return An array of Strings containing the list of Ingredients.
     */
    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * Adds elements to the given Array of type T.
     * @param ts An Array to add elements.
     * @param <T> Array Type
     * @return an Array which contains previous elements in ts and the ingredients.
     */
    @NonNull
    @Override
    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    /**
     * Checks which ingredients are missing from recipe.
     * @param ingredientList A recipe's ingredient list.
     * @return An array containing the list of ingredients not contained in the Pantry.
     */
    public String[] findMissingIngredients(String[] ingredientList){
        ArrayList<String> ret = new ArrayList<String>();
        boolean found;

        for(int i = 0; i < ingredientList.length; i++){
            found = false;
            for(int j = 0; j < list.size() || !found; j++)
                if(ingredientList[i].contains(list.get(i)))
                    found = true;
            if(!found)
                ret.add(ingredientList[i]);
        }

        return (String[]) ret.toArray();
    }

    /**
     * Returns the list as a String.
     * @return  A String containing each item in the list.
     */
    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < list.size(); i++){
            ret += list.get(i).toString() + "\n";
        }
        return ret;
    }
}
