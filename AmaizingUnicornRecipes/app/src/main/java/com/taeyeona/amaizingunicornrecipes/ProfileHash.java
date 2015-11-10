package com.taeyeona.amaizingunicornrecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thomastse on 11/8/15.
 */
public class ProfileHash {
    private static String[] searchSettings = {"High-Protein", "Low-Carb", "Low-Fat", "Balanced Diet", "Low-Sodium",
            "High-Fiber", "No-sugar", "Sugar-Conscious", "Gluten-Free", "Vegetarian", "Vegan",
            "Paleo", "Wheat-Free", "Dairy-Free", "Egg Free", "Soy Free", "Fish Free",
            "ShellFish Free", "Tree Nut Free", "Low Potassium", "Alcohol Free", "Kidney Friendly",
            "Peanut Free"};

    public static HashMap<String, List<String>> getProfileHash(){
        HashMap<String, List<String>> ret = new HashMap<String, List<String>>();
        List<String> textFields = new ArrayList<String>();
        List<String> searchToggleFields = new ArrayList<String>();
        List<String> searchMapsFields = new ArrayList<String>();

        textFields.add("Name");
        textFields.add("Email");

        for(int i = 0; i < searchSettings.length; i++)
            searchToggleFields.add(searchSettings[i]);

        searchMapsFields.add("5 mi");
        searchMapsFields.add("10 mi");
        searchMapsFields.add("15 mi");

        ret.put("User Info", textFields);
        ret.put("Search Settings", searchToggleFields);
        ret.put("Grocery Store Search Radius", searchMapsFields);

        return ret;
    }

    public static List<String> getProfileKeys(){
        List<String> ret = new ArrayList<String>();
        ret.add("User Info");
        ret.add("Search Settings");
        ret.add("Grocery Store Search Radius");

        return ret;
    }
}
