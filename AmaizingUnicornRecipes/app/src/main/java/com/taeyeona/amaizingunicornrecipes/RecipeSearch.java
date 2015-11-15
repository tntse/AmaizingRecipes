package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David Chau
 * @author Chris Lang
 * @version 1.0
 */
public class RecipeSearch extends AppCompatActivity {

    private RecyclerView listview;
    private List<Recipes> recipeList = new ArrayList<>();
    private RecipeAdapter recAdapt;
    private ProgressBar progress;
    private TextView text;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        final JSONObject response;

        //Make a SharedPreferences object to get the global SharedPreferences so that we could see if we need
        //to use the Food2Fork search or the Edamam search based on preferences
        sharedPreferences = this.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        //Use a Map data structure to get all of the shared preferences in one object
        Map<String, ?> preferencesMap = sharedPreferences.getAll();
        boolean searchEdamam = preferencesMap.containsValue(true);

        //Made a progress bar to have the user wait for the recipe search to come back
        //Made a TextView to show if there's no list to come back
        progress = (ProgressBar) findViewById(R.id.progressBar);
        text = (TextView) findViewById(R.id.textView4);
        //Made a JSONRequest object to do the request calling
        final JSONRequest jsonRequest = new JSONRequest();

        //Made a RecyclerView for the showing of the list of recipes retrieved from the response
        listview = (RecyclerView) findViewById(R.id.list);
        listview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recAdapt = new RecipeAdapter(getApplicationContext());

        ArrayList<String> health = new ArrayList<>();
        ArrayList<String> diet = new ArrayList<>();

        String ingredients = getIntent().getStringExtra("Ingredients");
        /* Replace special characters with their htmls equivalent */
        ingredients.replace(" ", "%20"); // Replace spaces

        if (searchEdamam) {
            HashMap<String, List<String>> profileHash = ProfileHash.getProfileHash();
            String collection[] = profileHash.get("Search Settings").toArray(new String[0]);
            for (int i = 0; i < collection.length; i++){
                Boolean checked = sharedPreferences.getBoolean("Search" + collection[i], false);
                if(checked){
                    String currentSetting = collection[i].toString().toLowerCase();
                    Log.d("AAA", "Current Setting: " + currentSetting);
                    if(i < 5){
                        diet.add(currentSetting);
                    }else {
                        if(currentSetting.equals("no-sugar")){
                            health.add("low-sugar");
                        }else {
                            health.add(currentSetting);
                        }
                    }
                }
            }

            String healthArray[] = health.toArray(new String[health.size()]);
            String dietArray[] = diet.toArray(new String[diet.size()]);

            jsonRequest.createResponse(Auth.EDAMAM_URL, "app_key", Auth.EDAMAM_KEY, "app_id",
                    Auth.EDAMAM_ID, ingredients, "", null, null, "", "", "", "", null, null, null, "", healthArray, dietArray);
            jsonRequest.sendResponse(getApplicationContext());
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
                        text.setText("No results found, try changing your settings or search.");
                    } else {
                        parseEdamamResponse(jsonRequest.getResponse());
                        progress.setVisibility(View.INVISIBLE);
                        if (recipeList.size() == 0) {
                            text.setText("No searches found");
                        }
                        //Populates the RecyclerView list with the recipe search list
                        recAdapt.setList(recipeList);
                        recAdapt.setListener(new CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent intent = new Intent(RecipeSearch.this, RecipeShow.class);
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
            jsonRequest.sendResponse(getApplicationContext());

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
                        text.setText("No results found, try changing your settings or search.");
                    } else {
                        parseResponse(response);
                        if (recipeList.size() == 0) {
                            text.setText("No searches found");
                        }
                        //Populates the RecyclerView list with the recipe search list
                        recAdapt.setList(recipeList);
                        recAdapt.setListener(new CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Toast toast = Toast.makeText(getApplicationContext(), "You've clicked on "
                                        + recipeList.get(position).getTitle(), Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(RecipeSearch.this, RecipeShow.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        JSONArray jsonArr= obj.getJSONArray("ingredientLines");
        for(int i=0;i<jsonArr.length(); i++) {
            String ingredient = jsonArr.getString(i);
            recipe.setIngredients(ingredient);
        }

        JSONArray jsonNutrientArr= obj.getJSONArray("digest");
        for(int i=0;i<jsonNutrientArr.length(); i++) {
            JSONObject nutritionObj = jsonNutrientArr.getJSONObject(i);
            String label = nutritionObj.getString("label");
            String total = nutritionObj.getString("total");
            String unit = nutritionObj.getString("unit");

            recipe.setNutrients(label + " " + total + " " + unit);
        }
        return recipe;
    }

}