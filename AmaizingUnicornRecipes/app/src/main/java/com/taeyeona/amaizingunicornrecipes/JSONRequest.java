package com.taeyeona.amaizingunicornrecipes;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Chau on 10/17/2015.
 */
public class  JSONRequest {

    private static final String TAG = JSONRequest.class.getSimpleName();
    private JSONObject jsRequest;
    private String URL;

    public JSONRequest(){
    }

    //https://api.edamam.com/search?from=0&to=1&q=chicken&app_id=4f2b1b73&app_key=bb6d714aa9393e1e22555b633eee4de4
    //http://food2fork.com/api/search?key=50ad55b48d8dbd791d8b69af229adeca&q=shredded%20chicken
    //https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=cute+cats&key=AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void createResponse(String baseUrl, String keyStr, String appKey, String idStr, String appId,
                               String q, String radiusValue, String part, String maxResults, String from, String to,
                               String typesValue, String sensorValue, String location, Double lat, Double lng,
                               String recipeID, String[] health, String[] diet){
        /**
         *  Builds the URL which will make the GET request for an api call.
         *
         *  @param baseUrl The api specified absolute url where calls will be requested, often ends
         *                 in search and does not include the question mark.
         *  @param keyStr The string that specifies your api key. i.e key or app_key.
         *  @param appKey Your api key.
         *  @param idStr The string that specifies your idStr. i.e. id or app_id.
         *               Can be null.
         *  @param appId Your api id.
         *               Can be null.
         *  @param q The query text for the api call. Can be a comma delimited list for some api's.
         *  @param part The part of the response requested.
         *              Can be null.
         *  @param maxResults The maximum desired results. Does not include the = or preceding string.
         *                    Can be null.
         *  @param from The starting index of the desired results to be returned.
         *              Can be null.
         *  @param to The ending index of the desired results to be returned.
         *            Can be null.
         *  @param typesValue Something something
         *                    Can be null.
         *  @param sensorValue Something something
         *                     Can be null.
         *  @param location Something something.
         *                  Can be null.
         *  @param lat Something something
         *             Can be 0.0.
         *  @param lng Something something
         *             Can be 0.0
         *  @param recipeID The ID for a recipe, from Food2Fork API
         *                  Can be null.
         *
         *  @param health An array of strings holding health labels to be used
         *                to filter recipe results
         *
         *  @param diet An array of strings holding diet labels to be used
         *              to filter recipe results
         *
         */

        URL = baseUrl + Auth.CHAR_QUESTION;

        if(!keyStr.isEmpty() && !appKey.isEmpty()){
            URL += Auth.CHAR_AND + keyStr + Auth.CHAR_EQUALS + appKey;
        }

        if((idStr != null && appId != null) && (!idStr.isEmpty() && !appId.isEmpty())){
            URL += Auth.CHAR_AND + idStr + Auth.CHAR_EQUALS + appId;
        }

        if(part != null && !part.isEmpty()){
            URL += Auth.CHAR_AND + part;
        }

        if(!q.isEmpty()){
            URL += Auth.CHAR_AND + Auth.CHAR_Q + Auth.CHAR_EQUALS + q;
        }

        if(from != null && !from.isEmpty()){
            URL += Auth.CHAR_AND + "from" + Auth.CHAR_EQUALS + from;
        }

        if(to != null && !to.isEmpty()){
            URL += Auth.CHAR_AND + "to" + Auth.CHAR_EQUALS + to;
        }

        if(maxResults != null && !maxResults.isEmpty()){
            URL += Auth.CHAR_AND + "maxResults" + Auth.CHAR_EQUALS + maxResults;
        }

        if(radiusValue != null && !radiusValue.isEmpty()){
            URL += Auth.CHAR_AND + "radius" + Auth.CHAR_EQUALS + radiusValue;
        }

        if(typesValue != null && !typesValue.isEmpty()){
            URL += Auth.CHAR_AND + "types" + Auth.CHAR_EQUALS + typesValue;
        }

        if(sensorValue != null && !sensorValue.isEmpty()){
            URL += Auth.CHAR_AND + "sensor" + Auth.CHAR_EQUALS + sensorValue;
        }

        if(location != null){
            URL += Auth.CHAR_AND + location + Auth.CHAR_EQUALS + lat + "," + lng;
        }

        if(recipeID != null && recipeID != ""){
            URL += Auth.CHAR_AND + "rId" + Auth.CHAR_EQUALS + recipeID;
        }

        if(baseUrl == "https://api.edamam.com/search" && recipeID != ""){
            URL += Auth.CHAR_AND + "r" + Auth.CHAR_EQUALS + recipeID;
        }

        if(health != null && health.length > 0){
            for(int i = 0; i < health.length; i++){
                URL += Auth.CHAR_AND + "health" + Auth.CHAR_EQUALS + health[i];
            }
        }

        if(diet != null && diet.length > 0){
            for(int i = 0; i < diet.length; i++){
                URL += Auth.CHAR_AND + "diet" + Auth.CHAR_EQUALS + diet[i];
            }
        }
        Log.d("AAA", "URL: " + URL);

    }

    /**
     * Sends the call to the API
     *
     * @param pContext The Context object from the activity that called this method
     *
     */
    public void sendResponse(Context pContext){
        Log.d("AAA", "URL: " + URL);
        JsonObjectRequest jsObjectReq = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        jsRequest = (JSONObject) response;
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "ERR" + error.getMessage());
            }
        }
        );

        VolleySingleton.getInstance(pContext).addToRequestQueue(jsObjectReq);
    }

    /**
     * @return the JSONObject response from the API
     */
    public JSONObject getResponse(){
        return jsRequest;
    }

}