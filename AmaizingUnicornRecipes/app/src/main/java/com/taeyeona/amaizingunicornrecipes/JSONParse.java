package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.util.Log;
import java.lang.*;
import android.content.Intent;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
    private StringBuilder titleList;
    private String endUrl;
    private static final String TAG = JSONParse.class.getSimpleName();

    public JSONParse(){
        titleList = new StringBuilder();
        endUrl = "";
    }

    public JSONParse(String ur){
        titleList = new StringBuilder();
        endUrl = ur;
    }

    public void sendJSONRequest(Context pContext){

        final String URL = urlBuilder();
        RequestQueue queue = VolleySingleton.getInstance(pContext).getRequestQueue();

        JsonObjectRequest jsObjectReq = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                /**
                 This is what you would do to hand the passed in JSON object i.e. GSON or JSONArrays.
                 It may be better to have multitple if to handle multiply JSON request for differnet API's
                 CallBacks may be prefered with-in here, this where you would make the "call" to the "callback."

                 */
                Log.d(TAG, response.toString());
                try{
                    JSONArray arrayTitle = response.getJSONArray(Keys.endpointRecipe.KEY_RECIPES);
                    for(int i = 0; i<arrayTitle.length(); i++){
                        JSONObject recip = arrayTitle.getJSONObject(i);
                        String title = recip.getString(Keys.endpointRecipe.KEY_TITLE);
                        titleList.append(title+"\n");
                    }
                }catch(JSONException ex){

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
            }
        });

        VolleySingleton.getInstance(pContext).addToRequestQueue(jsObjectReq);
    }

    public StringBuilder getTitleList(){
        return titleList;
    }
    /*
     * Parameter will be modified to include ingredients
     * */
    private String urlBuilder(){
        return Auth.URL + Auth.CHAR_QUESTION + Auth.STRING_KEY + Auth.CHAR_EQUALS + Auth.F2F_Key
                + Auth.CHAR_AND + Auth.CHAR_Q + Auth.CHAR_EQUALS + endUrl;
    }
}
