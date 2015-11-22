package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.Adapter.RecipeAdapter;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
import com.taeyeona.amaizingunicornrecipes.Keys;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;
import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Chau on 11/21/2015.
 */
public class RecipeSearchFragment extends Fragment {

    private RecyclerView listview;
    private List<Recipes> recipeList = new ArrayList<>();
    private RecipeAdapter recAdapt;
    private ProgressBar progress;
    private TextView text;
    private SharedPreferences sharedPreferences;
    private Set<String> manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recipe_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Make a SharedPreferences object to get the global SharedPreferences so that we could see if we need
        //to use the Food2Fork search or the Edamam search based on preferences
        sharedPreferences = getContext().getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        //Use a Map data structure to get all of the shared preferences in one object
        Map<String, ?> preferencesMap = sharedPreferences.getAll();
        boolean searchEdamam = preferencesMap.containsValue(true);
        manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());

        //Made a progress bar to have the user wait for the recipe search to come back
        //Made a TextView to show if there's no list to come back
        progress = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        text = (TextView) getActivity().findViewById(R.id.textView4);
        //Made a JSONRequest object to do the request calling
        final JSONRequest jsonRequest = new JSONRequest();

        //Made a RecyclerView for the showing of the list of recipes retrieved from the response
        listview = (RecyclerView) getActivity().findViewById(R.id.list);
        listview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recAdapt = new RecipeAdapter(getActivity().getApplicationContext());

        ArrayList<String> health = new ArrayList<>();
        ArrayList<String> diet = new ArrayList<>();

        String ingredients = manager.toString();
        /* Replace special characters with their htmls equivalent */
        ingredients.replace(", ", ","); // Remove comma-trailing spaces
        ingredients.replace(" ", "%20"); // Replace spaces with html code

        if (searchEdamam) {
            String collection[] = ProfileHash.getSearchSettings();
            for (int i = 0; i < collection.length; i++) {
                Boolean checked = sharedPreferences.getBoolean("Search" + collection[i], false);
                if (checked) {
                    String currentSetting = collection[i].toString().toLowerCase();
                    if (i < 5) {
                        diet.add(currentSetting);
                    } else {
                        if (currentSetting.equals("no-sugar")) {
                            health.add("low-sugar");
                        } else {
                            health.add(currentSetting);
                        }
                    }
                }
            }

            String healthArray[] = health.toArray(new String[health.size()]);
            String dietArray[] = diet.toArray(new String[diet.size()]);

            jsonRequest.createResponse(Auth.EDAMAM_URL, "app_key", Auth.EDAMAM_KEY, "app_id",
                    Auth.EDAMAM_ID, ingredients, "", null, null, "", "", "", "", null, null, null, "", healthArray, dietArray);
            jsonRequest.sendResponse(getActivity().getApplicationContext());
            //Create a handler for a background thread that waits until another background thread,
            //the API call, comes back with the JSON parsed and ready.
            //Cited from http://stackoverflow.com/questions/14186846/delay-actions-in-android
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    JSONObject response = jsonRequest.getResponse();
                    /* Check to see if something was returned */
                    if (response == null) {
                        text.setText("No results found, try changing your search settings.");
                    } else {
                        parseEdamamResponse(jsonRequest.getResponse());
                        progress.setVisibility(View.INVISIBLE);
                        if (recipeList.size() == 0) {
                            text.setText("No results found, try changing your search settings.");
                        }
                        //Populates the RecyclerView list with the recipe search list
                        recAdapt.setList(recipeList);
                        recAdapt.setListener(new RecipeAdapter.CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent = new Intent(getActivity(), RecipeShow.class);
                                intent.putExtra("Picture", recipeList.get(position).getImageUrl());
                                intent.putExtra("Title", recipeList.get(position).getTitle());
                                intent.putExtra("RecipeID", recipeList.get(position).getRecipeId());
                                intent.putExtra("Ingredients", recipeList.get(position).getIngredients().toArray(new String[0]));
                                intent.putExtra("Nutrients", recipeList.get(position).getNutrients().toArray(new String[0]));
                                intent.putExtra("API", "Edamam");
                                startActivity(intent);
                            }
                        });
                        listview.setAdapter(recAdapt);
                    }
                    progress.setVisibility(View.INVISIBLE);
                }
            }, 7000);


        } else {

            //Create food2fork response and send the response to the API
            jsonRequest.createResponse(Auth.URL, Auth.STRING_KEY, Auth.F2F_Key, "", "",
                    ingredients, "", "", "", "", "", "", "", null, 0.0, 0.0, "", null, null);
            jsonRequest.sendResponse(getActivity().getApplicationContext());

            //Create a handler for a background thread that waits until another background thread,
            //the API call, comes back with the JSON parsed and ready.
            //Cited from http://stackoverflow.com/questions/14186846/delay-actions-in-android
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    JSONObject response = jsonRequest.getResponse();
                    /* Check to see if something was returned */
                    if (response == null) {
                        text.setText("No results found, try changing your search settings");
                    } else {
                        parseResponse(response);
                        if (recipeList.size() == 0) {
                            text.setText("No results found, try changing your search settings.");
                        }
                        //Populates the RecyclerView list with the recipe search list
                        recAdapt.setList(recipeList);
                        recAdapt.setListener(new RecipeAdapter.CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "You've clicked on "
                                        + recipeList.get(position).getTitle(), Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(getActivity(), RecipeShow.class);
                                intent.putExtra("Picture", recipeList.get(position).getImageUrl());
                                intent.putExtra("RecipeID", recipeList.get(position).getRecipeId());
                                intent.putExtra("Title", recipeList.get(position).getTitle());
                                intent.putExtra("API", "Food2Fork");
                                startActivity(intent);
                            }
                        });
                        listview.setAdapter(recAdapt);
                    }
                    progress.setVisibility(View.INVISIBLE);
                }

            }, 7000);
        }
    }

    /**
     * Parses the response from the JSONRequest createResponse and sendResponse.
     *
     * @param response The JSONObject retrieved from response from sendResponse
     */
    private void parseResponse(JSONObject response) {
        try {
            JSONArray arrayRecipe = response.getJSONArray(Keys.endpointRecipe.KEY_RECIPES);
            for (int i = 0; i < arrayRecipe.length(); i++) {
                JSONObject object = arrayRecipe.getJSONObject(i);
                recipeList.add(convertRecipes(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the response from the JSONRequest createResponse and sendResponse.
     *
     * @param response The JSONObject retrieved from response from sendResponse
     */
    private void parseEdamamResponse(JSONObject response) {
        try {
            JSONArray arrayRecipe = response.getJSONArray(Keys.endpointRecipe.HITS);
            for (int i = 0; i < arrayRecipe.length(); i++) {
                JSONObject object = arrayRecipe.getJSONObject(i).getJSONObject("recipe");
                recipeList.add(convertEdamamRecipes(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to convert the Food2Fork JSONObject into Recipe Objects
     *
     * @param obj The JSONObject to be parsed into Recipes object
     * @return Returns the parsed Recipes object
     */
    private final Recipes convertRecipes(JSONObject obj) throws JSONException {
        return new Recipes(
                obj.getString(Keys.endpointRecipe.KEY_PUBLISHER),
                obj.getString(Keys.endpointRecipe.KEY_F2F_URL),
                obj.getString(Keys.endpointRecipe.KEY_TITLE),
                obj.getString(Keys.endpointRecipe.KEY_SOURCE_URL),
                obj.getString(Keys.endpointRecipe.KEY_F2FID),
                obj.getString(Keys.endpointRecipe.KEY_IMAGE_URL),
                obj.getDouble(Keys.endpointRecipe.KEY_SOCIAL_RANK),
                obj.getString(Keys.endpointRecipe.KEY_PUBLISHER_URL));
    }

    /**
     * Helper method to convert the Edamam JSONObject into Recipe Objects
     *
     * @param obj The JSONObject to be parsed into Recipes object
     * @return Returns the parsed Recipes object
     */
    private final Recipes convertEdamamRecipes(JSONObject obj) throws JSONException {
        Recipes recipe = new Recipes(
                obj.getString("source"),
                obj.getString("uri"),
                obj.getString("label"),
                obj.getString("url"),
                obj.getString("uri"),
                obj.getString("image"),
                0.0,
                "");
        JSONArray jsonArr = obj.getJSONArray("ingredientLines");
        for (int i = 0; i < jsonArr.length(); i++) {
            String ingredient = jsonArr.getString(i);
            recipe.setIngredients(ingredient);
        }

        JSONArray jsonNutrientArr = obj.getJSONArray("digest");
        for (int i = 0; i < jsonNutrientArr.length(); i++) {
            JSONObject nutritionObj = jsonNutrientArr.getJSONObject(i);
            String label = nutritionObj.getString("label");
            String total = nutritionObj.getString("total");
            String unit = nutritionObj.getString("unit");

            recipe.setNutrients(label + " " + total + " " + unit);
        }
        return recipe;
    }

}
