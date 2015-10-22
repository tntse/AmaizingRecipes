package com.csc413.thomastse.instaface;

import android.content.Context;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;


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

        final String URL = instagramUrlBuilder();
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
                            It may be better to have multiple if to handle multiply JSON request for different API's
                            CallBacks may be preferred with-in here, this where you would make the "call" to the "callback."

                         */

                        Log.d(TAG, response.toString());

                        try{

                            JSONArray arrayTitle = response.getJSONArray(Keys.InstagramKeys.KEY_AUTHORIZE);
                            for(int i = 0; i<arrayTitle.length(); i++){
                                JSONObject recip = arrayTitle.getJSONObject(i);
                                String title = recip.getString(Keys.InstagramKeys.KEY_CLIENT_ID);
                                titleList.append(title+"\n");
                            }

                        }catch(JSONException ex){

                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                    }
                }
        );

        VolleySingleton.getInstance(pContext).addToRequestQueue(jsObjectReq);
    }

    public StringBuilder getTitleList(){

         return titleList;

    }

    /*
     * Parameter will be modified to include ingredients
     * */
    private String instagramUrlBuilder(){
        return Auth.INSTAGRAM_URL + Auth.STRING_OATH + Auth.CHAR_FORWARD_SLASH + Auth.STRING_AUTHORIZE + Auth.CHAR_FORWARD_SLASH
                + Auth.CHAR_QUESTION + Auth.STRING_CLIENT_ID  +  Auth.CHAR_EQUALS + Auth.INSTAGRAM_CLIENT_ID + Auth.CHAR_AND
                + Auth.STRING_REDIRECT_URI + Auth.CHAR_EQUALS + Auth.INSTAGRAM_REDIRECT_URI + Auth.CHAR_AND + Auth.STRING_RESPONSE_TYPE
                + Auth.CHAR_EQUALS + Auth.STRING_CODE;
    }
}
