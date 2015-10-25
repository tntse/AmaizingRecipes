/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple YouTube Android API demo application which shows how to create a simple application that
 * displays a YouTube Video in a {@link YouTubePlayerView}.
 * <p>
 * Note, to use a {@link YouTubePlayerView}, your activity must extend {@link YouTubeBaseActivity}.
 */
public class Player extends YouTubeFailureRecoveryActivity {

    // hold parsed title, hold class, hold list of parsed obj

    private static Context context;
    private String vid;
    private StringBuilder titleList = new StringBuilder();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        //parse data with passed string
        final JSONRequest jsonRequest = new JSONRequest();

        /**
         * URL = baseUrl + Auth.CHAR_QUESTION + strKey + Auth.CHAR_EQUALS + appKey + Auth.CHAR_AND
         + strAppId + Auth.CHAR_EQUALS + appId + Auth.CHAR_AND + Auth.CHAR_Q + Auth.CHAR_EQUALS
         + endUrl + from + Auth.CHAR_EQUALS + fromNum + Auth.CHAR_AND + to + Auth.CHAR_EQUALS
         + toNum + Auth.CHAR_AND + part + Auth.CHAR_AND + maxResult;
         * */
        jsonRequest.createResponse("https://www.googleapis.com/youtube/v3/search", "key",
                "AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI",null,null, getIntent().getStringExtra("formattedSt"), "",
                 Auth.PART_SNIPPET, "1",null,null, "", "", "", 0.0, 0.0);

        //"https://www.googleapis.com/youtube/v3/search?key=AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI&=&q=cookie=&=&part=snippet&maxResults=1




        jsonRequest.sendResponse(getApplicationContext());
        //final JSONObject response = jsonRequest.getResponse();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //problem statement
                parseJSON(jsonRequest.getResponse());
                vid = titleList.toString();
            }
        }, 5000);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Auth.DEVELOPER_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player,
                                        final boolean wasRestored) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!wasRestored) {
                    player.cueVideo(vid);
                }
            }
        }, 5000);
        //reference strings.xml for video
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    private void parseJSON(JSONObject pResponse){
        try{
            JSONArray items = pResponse.getJSONArray(Keys.endpointRecipe.KEY_items);
            JSONObject id = items.getJSONObject(0);
            //inside first array element
            JSONObject idObj = id.getJSONObject(Keys.endpointRecipe.KEY_id);
            titleList.append(idObj.getString(Keys.endpointRecipe.KEY_VideoId));


        }catch(JSONException ex){

        }
    }

}
