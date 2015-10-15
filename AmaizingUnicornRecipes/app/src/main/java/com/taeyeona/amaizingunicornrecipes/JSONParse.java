package com.taeyeona.amaizingunicornrecipes;

import android.util.Log;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chau on 9/27/2015.
 */

//http://food2fork.com/api/search?key={API_KEY}&q=shredded%20chicken
//http://food2fork.com/api/get
public class JSONParse {
    private List<Recipes> recipeList;
    private String endUrl;
    private static final String TAG = JSONParse.class.getSimpleName();

    public JSONParse(){
        recipeList = new ArrayList<Recipes>();
        endUrl = "";
    }

    public JSONParse(String ur){
        recipeList = new ArrayList<Recipes>();
        endUrl = ur;
    }

    public void sendJSONRequest(){

        final String URL = urlBuilder("List", 0);


        JsonObjectRequest jsObjectReq = new JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

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
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.getMessage());
                }
            }
        );

        VolleySingleton.getInstance().addToRequestQueue(jsObjectReq);
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

    public List<Recipes> getList(){
        return recipeList;
    }

    private String urlBuilder(String req, int i){
        if(req.equals("Single")){
            return Auth.GET_URL + Auth.CHAR_QUESTION + Auth.STRING_KEY + Auth.CHAR_EQUALS + Auth.F2F_Key + Auth.CHAR_AND
                    + "rId" + Auth.CHAR_EQUALS + recipeList.get(i).getRecipeId();
        }else{
            return Auth.URL + Auth.CHAR_QUESTION + Auth.STRING_KEY + Auth.CHAR_EQUALS + Auth.F2F_Key
                    + Auth.CHAR_AND + Auth.CHAR_Q + Auth.CHAR_EQUALS + endUrl;
        }

    }
}
