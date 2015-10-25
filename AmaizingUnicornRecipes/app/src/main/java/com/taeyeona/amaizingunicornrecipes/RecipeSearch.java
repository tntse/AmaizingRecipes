package com.taeyeona.amaizingunicornrecipes;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau on 9/27/2015.
 */
public class RecipeSearch extends AppCompatActivity{

    private RecyclerView listview;
    private List<Recipes> recipeList = new ArrayList<Recipes>();
    private RecipeAdapter recAdapt;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        final JSONRequest par = new JSONRequest();
        par.createResponse(Auth.URL, Auth.STRING_KEY, Auth.F2F_Key, "", "",
                getIntent().getStringExtra("Ingredients"),"", "", "", "", "", "", "", "", 0.0, 0.0);
        par.sendResponse(getApplicationContext());

        listview = (RecyclerView) findViewById(R.id.list);
        listview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recAdapt = new RecipeAdapter(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                parseResponse(par.getResponse());
                progress.setVisibility(View.INVISIBLE);
                recAdapt.setList(recipeList);
                recAdapt.setListener(new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Toast toast = Toast.makeText(getApplicationContext(), "You've clicked on "
                                + recipeList.get(position).getTitle(), Toast.LENGTH_SHORT);
                        toast.show();
                        Log.d("", "Hey yall");
                        Intent intent = new Intent(RecipeSearch.this, RecipeShow.class).putExtra("Title", recipeList.get(position).getTitle());
                        Log.d("", "After set intent, before start activity");
                        startActivity(intent);
                    }
                });
                listview.setAdapter(recAdapt);
            }

        }, 7000);

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

    private void parseResponse(JSONObject response){
        try{
            JSONArray arrayRecipe = response.getJSONArray(Keys.endpointRecipe.KEY_RECIPES);
            for(int i = 0; i<arrayRecipe.length(); i++){
                JSONObject object = arrayRecipe.getJSONObject(i);
                recipeList.add(convertRecipes(object));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private final Recipes convertRecipes(JSONObject obj) throws JSONException{
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
}