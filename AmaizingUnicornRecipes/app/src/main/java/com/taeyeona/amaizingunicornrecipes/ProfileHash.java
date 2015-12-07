package com.taeyeona.amaizingunicornrecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Puts all the settings into a HashMap.
 * @author Thomas Tse
 * @since 2015-11-08.
 */
public class ProfileHash {
    /**
     * A list of potential search modifications.
     */
    private static String[] searchSettings = {"High-Protein", "Low-Carb", "Low-Fat", "Balanced Diet", "Low-Sodium",
            "High-Fiber", "No-sugar", "Sugar-Conscious", "Gluten-Free", "Vegetarian", "Vegan",
            "Paleo", "Wheat-Free", "Dairy-Free", "Egg Free", "Soy Free", "Fish Free",
            "ShellFish Free", "Tree Nut Free", "Low Potassium", "Alcohol Free", "Kidney Friendly",
            "Peanut Free"};

    /**
     * Returns a HashMap with settings data.
     * @return a filled HashMap containing the settings data.
     */
    public static HashMap<String, List<String>> getProfileHash(){
        HashMap<String, List<String>> ret = new HashMap<String, List<String>>();
        List<String> textFields = new ArrayList<String>();
        List<String> searchToggleFields = new ArrayList<String>();
        List<String> searchMapsFields = new ArrayList<String>();
        List<String> uploadPicture = new ArrayList<String>();

        textFields.add("Name");
        textFields.add("Email");

        for(int i = 0; i < searchSettings.length; i++)
            searchToggleFields.add(searchSettings[i]);

        searchMapsFields.add("5 mi");
        searchMapsFields.add("10 mi");
        searchMapsFields.add("15 mi");

        uploadPicture.add("Upload Picture Here");


        ret.put("Grocery Store Search Radius", searchMapsFields);
        ret.put("Search Settings", searchToggleFields);
        ret.put("Picture Upload", uploadPicture);
        ret.put("User Info", textFields);

        return ret;
    }

    /**
     * Gets a list of potential search modifications.
     * @return searchSettings
     */
    public static String[] getSearchSettings(){
        return searchSettings;
    }

}
