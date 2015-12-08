package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.MainActivity;
import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.RecipeAdapter;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
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
        return inflater.inflate(R.layout.fragment_recipe_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Make a SharedPreferences object to get the global SharedPreferences so that we could see if we need
        //to use the Food2Fork search or the Edamam search based on preferences
        sharedPreferences = getContext().getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        //Use a Map data structure to get all of the shared preferences in one object
        Map<String, ?> preferencesMap = sharedPreferences.getAll();
        boolean searchEdamam = preferencesMap.containsValue(true);

        manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        if(!(manager instanceof IngredientsManager)) manager = new IngredientsManager(manager);

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

        String ingredients = "";

        ArrayList<String> searchIngredients = PantryListAdapter.getSelected();
        if(searchIngredients != null)
            ingredients += searchIngredients.toString().trim();

        String searchQuery = ((MainActivity)getActivity()).getBundle().getString("SearchQuery");
        if(searchQuery != null && !searchQuery.equals(""))
            ingredients += "," + searchQuery;


        /* Replace special characters with their htmls equivalent */
        ingredients = ingredients.replace("\n", ",");
        ingredients = ingredients.replace(", ", ","); // Remove comma-trailing spaces
        ingredients = ingredients.replace(" ", "%20"); // Replace spaces with html code
        ingredients = ingredients.replace("[", "");
        ingredients = ingredients.replace("]", "");

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
                    Auth.EDAMAM_ID, ingredients, "", null, null, "0", "100", "", "", null, null, null, "", healthArray, dietArray);
            jsonRequest.sendResponse(getActivity().getApplicationContext(), new JSONRequest.VolleyCallBack() {
                @Override
                public void onSuccess() {
                    JSONObject response = jsonRequest.getResponse();
                    /* Check to see if something was returned */
                    if (response == null) {
                        text.setText(R.string.no_recipe_results);
                    } else {
                        parseEdamamResponse(jsonRequest.getResponse());
                        progress.setVisibility(View.INVISIBLE);
                        if (recipeList.size() == 0) {
                            text.setText(R.string.no_recipe_results);
                        }
                        //Populates the RecyclerView list with the recipe search list
                        recAdapt.setList(recipeList);
                        recAdapt.setListener(new RecipeAdapter.CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent = new Intent(getActivity(), RecipeShow.class);
                                Recipes currentRecipe = recipeList.get(position);

                                ArrayList<Integer> arryListTotals = currentRecipe.getDailyTotals();
                                int dailyTotals[] = new int[arryListTotals.size()];
                                int z = 0;
                                for (Integer curIntObj: arryListTotals){
                                    dailyTotals[z++] = Math.round(curIntObj.intValue());
                                }

                                intent.putExtra("Picture", currentRecipe.getImageUrl());
                                intent.putExtra("Title", currentRecipe.getTitle());
                                intent.putExtra("RecipeID", currentRecipe.getRecipeId());
                                intent.putExtra("Ingredients", currentRecipe.getIngredients().toArray(new String[0]));
                                intent.putExtra("Nutrients", currentRecipe.getNutrients().toArray(new String[0]));
                                intent.putExtra("SourceUrl", currentRecipe.getSourceUrl());
                                intent.putExtra("SourceName", currentRecipe.getPublisher());
                                intent.putExtra("Totals", dailyTotals);
                                intent.putExtra("API", "Edamam");
                                startActivity(intent);
                            }
                        });
                        listview.setAdapter(recAdapt);
                    }
                    progress.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onFailure(){
                    progress.setVisibility(View.INVISIBLE);
                    text.setText("It seems that we cannot display the recipe due some kind of error."+"\n"+
                    "Please email us at AmaizingUnicornRecipes@gmail.com to fix this problem");
                }
            });

        } else {
            //Create food2fork response and send the response to the API
            jsonRequest.createResponse(Auth.URL, Auth.STRING_KEY, Auth.F2F_Key, "", "",
                    ingredients, "", "", "", "", "", "", "", null, 0.0, 0.0, "", null, null);
            jsonRequest.sendResponse(getActivity().getApplicationContext(), new JSONRequest.VolleyCallBack() {
                @Override
                public void onSuccess() {
                    JSONObject response = jsonRequest.getResponse();
                    /* Check to see if something was returned */
                    if (response == null) {
                        text.setText(R.string.no_recipe_results);
                    } else {
                        parseResponse(response);
                        if (recipeList.size() == 0) {
                            text.setText(R.string.no_recipe_results);
                        }
                        //Populates the RecyclerView list with the recipe search list
                        recAdapt.setList(recipeList);
                        recAdapt.setListener(new RecipeAdapter.CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Recipes currentRecipe = recipeList.get(position);
                                Intent intent = new Intent(getActivity(), RecipeShow.class);
                                intent.putExtra("Picture", currentRecipe.getImageUrl());
                                intent.putExtra("RecipeID", currentRecipe.getRecipeId());
                                intent.putExtra("Title", currentRecipe.getTitle());
                                intent.putExtra("SourceUrl", currentRecipe.getSourceUrl());
                                intent.putExtra("SourceName", currentRecipe.getPublisher());
                                intent.putExtra("API", "Food2Fork");
                                startActivity(intent);
                            }
                        });
                        listview.setAdapter(recAdapt);
                    }
                    progress.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onFailure(){
                    progress.setVisibility(View.INVISIBLE);
                    text.setText("It seems that we cannot display the recipe due to some kind of error."+"\n"+
                            "Please email us at AmaizingUnicornRecipes@gmail.com to fix this problem.");
                }
            });
        }
        PantryListAdapter.clearList();
    }

    /**
     * Parses the response from the JSONRequest createResponse and sendResponse.
     *
     * @param response The JSONObject retrieved from response from sendResponse
     */
    private void parseResponse(JSONObject response) {
        try {
            JSONArray arrayRecipe = response.getJSONArray(Auth.endpointRecipe.KEY_RECIPES);
            for (int i = 0; i < arrayRecipe.length(); i++) {
                JSONObject object = arrayRecipe.getJSONObject(i);
                recipeList.add(convertRecipes(object));
            }
        } catch (JSONException e) {
            text.setText("Apologies; it seems we cannot get the list of recipes");
        }
    }

    /**
     * Parses the response from the JSONRequest createResponse and sendResponse.
     *
     * @param response The JSONObject retrieved from response from sendResponse
     */
    private void parseEdamamResponse(JSONObject response) {
        try {
            JSONArray arrayRecipe = response.getJSONArray(Auth.endpointRecipe.HITS);
            for (int i = 0; i < arrayRecipe.length(); i++) {
                JSONObject object = arrayRecipe.getJSONObject(i).getJSONObject("recipe");
                recipeList.add(convertEdamamRecipes(object));
            }
        } catch (JSONException e) {
            text.setText("Apologies; it seems we cannot get the list of recipes");
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
                obj.getString(Auth.endpointRecipe.KEY_PUBLISHER),
                obj.getString(Auth.endpointRecipe.KEY_F2F_URL),
                obj.getString(Auth.endpointRecipe.KEY_TITLE),
                obj.getString(Auth.endpointRecipe.KEY_SOURCE_URL),
                obj.getString(Auth.endpointRecipe.KEY_F2FID),
                obj.getString(Auth.endpointRecipe.KEY_IMAGE_URL),
                obj.getDouble(Auth.endpointRecipe.KEY_SOCIAL_RANK),
                obj.getString(Auth.endpointRecipe.KEY_PUBLISHER_URL));
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
        int servings = obj.getInt("yield");
        if(servings > 0) {
            recipe.setServings(servings);
        }

        JSONArray jsonArr = obj.getJSONArray("ingredientLines");
        for (int i = 0; i < jsonArr.length(); i++) {
            String ingredient = jsonArr.getString(i);
            recipe.setIngredients(ingredient);
        }

        JSONArray jsonNutrientArr = obj.getJSONArray("digest");
        ArrayList<Integer> dailyTotals = new ArrayList<>();
        for (int i = 0; i < jsonNutrientArr.length(); i++) {
            JSONObject nutritionObj = jsonNutrientArr.getJSONObject(i);
            String label = nutritionObj.getString("label");
            String total = nutritionObj.getString("daily");
            String unit = nutritionObj.getString("unit");

            recipe.setNutrientList(label + " " + Integer.toString(Math.round(Float.parseFloat(total))) + " " + unit);
            Integer dailyTotal = new Integer(Math.round(Float.parseFloat(total))/recipe.getServings());
            dailyTotals.add(dailyTotal);
        }
        recipe.setDailyTotals(dailyTotals);

        return recipe;
    }
}
