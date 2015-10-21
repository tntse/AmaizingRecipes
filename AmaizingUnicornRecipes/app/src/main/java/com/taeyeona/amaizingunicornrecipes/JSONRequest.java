package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Chau on 10/17/2015.
 */
public class JSONRequest {

    private static final String TAG = JSONRequest.class.getSimpleName();
    private JSONObject jsRequest;
    private String URL;

    public JSONRequest(){
    }

    //https://api.edamam.com/search?from=0&to=1&q=chicken&app_id=4f2b1b73&app_key=bb6d714aa9393e1e22555b633eee4de4
    //http://food2fork.com/api/search?key={API_KEY}&q=shredded%20chicken
    //https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=cute+cats&key=AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI
    public void createResponse(String baseUrl, String strKey, String strAppId,
                                String from, String to, String appId, String appKey, String endUrl,
                                String fromNum, String toNum, String part, String maxResult){

        URL = baseUrl + Auth.CHAR_QUESTION + strKey + Auth.CHAR_EQUALS + appKey + Auth.CHAR_AND
                + strAppId + Auth.CHAR_EQUALS + appId + Auth.CHAR_AND + Auth.CHAR_Q + Auth.CHAR_EQUALS
                + endUrl + Auth.CHAR_AND + from + Auth.CHAR_EQUALS + fromNum + Auth.CHAR_AND + to + Auth.CHAR_EQUALS
                + toNum + Auth.CHAR_AND + part + Auth.CHAR_AND + maxResult;
    }

    public void sendResponse(Context pContext){

        JsonObjectRequest jsObjectReq = new JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    jsRequest = response;
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.getMessage());
                 }
            }
        );

        VolleySingleton.getInstance(pContext).addToRequestQueue(jsObjectReq);
    }

    public JSONObject getResponse(){
        return jsRequest;
    }

}
