package com.taeyeona.youtubeapi;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hao on 10/11/2015.
 */
public class ParseYoutube {


    private StringBuilder titleList;
    private String endUrl;
    private static final String TAG = ParseYoutube.class.getSimpleName();
    private String formattedSearchString;
    private String returnId;

    //*
    //  two contructors, one empty one with param String
    // */
    public ParseYoutube(){
        titleList = new StringBuilder();
        endUrl = "";
    }

    public ParseYoutube(String ur){
        formattedSearchString = ur;
        titleList = new StringBuilder();

    }

    //**
    //
    // Create method for JSONParse
    //@params Context
    // */

    public void sendJSONRequest(Context context){

        //create URL builder to GET request
        final String URL = urlBuilder();

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
                        try{
                                JSONArray items = response.getJSONArray(KEYS.endpointRecipe.KEY_items);
                                JSONObject id = items.getJSONObject(0);
                            //inside first array element
                                JSONObject idObj = id.getJSONObject(KEYS.endpointRecipe.KEY_id);
                                //This works, kinda
                                titleList.append(idObj.getString(KEYS.endpointRecipe.KEY_videoID));

                                //returnId = idObj.getString(KEYS.endpointRecipe.KEY_videoID);

                        }catch(JSONException ex){

                            Log.d(TAG, "Exception");
                        }

                    }/////End onResponse
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
            }
        });/////////////// END json obj request


        VolleySingleton.getInstance(context).addToRequestQueue(jsObjectReq);
    }

    public StringBuilder getTitleList(){
        return titleList;
    }
    //haev string as param?

    public String urlBuilder(){

        return Auth.URL + Auth.CHAR_QUESTION + Auth.PART_SNIPPET + Auth.CHAR_AND + Auth.MAX_RESULTS
                + Auth.CHAR_AND + Auth.CHAR_Q +Auth.CHAR_EQUALS + formattedSearchString + "+tutorial" + Auth.CHAR_AND +
                Auth.STRING_KEY + Auth.CHAR_EQUALS + Auth.DEVELOPER_KEY ;
    }

    public String getReturnId()
    {
        return returnId;
    }
}// fix RECIPE NAME for url builder to input

//https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=cute+cats&key=AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI


