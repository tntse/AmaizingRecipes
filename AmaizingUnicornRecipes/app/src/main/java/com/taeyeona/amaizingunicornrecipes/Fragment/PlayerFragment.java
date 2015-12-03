package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.taeyeona.amaizingunicornrecipes.Activity.Pantry;
import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
import com.taeyeona.amaizingunicornrecipes.Keys;
import com.taeyeona.amaizingunicornrecipes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;

public class PlayerFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    final private String mVideoId = "xhUoBKhPq14";
    final private String devKey = "AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI";
    private String vid;
    private StringBuilder titleList = new StringBuilder();
    String st;

    public static PlayerFragment newInstance(final String videoId) {
        final PlayerFragment youTubeFragment = new PlayerFragment();
        final Bundle bundle = new Bundle();
        youTubeFragment.setArguments(bundle);
        return youTubeFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        st = getActivity().getIntent().getStringExtra("Title");
        st = st.replaceAll(" ","+");
        st=st+"+tutorial";

        //parse data with passed string
        //Crated JSONrequest
        final JSONRequest jsonRequest = new JSONRequest();
        //Create response
        jsonRequest.createResponse("https://www.googleapis.com/youtube/v3/search", "key",
                "AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI",null,null, st, "",
                Auth.PART_SNIPPET, "1",null,null, "", "", "", 0.0, 0.0, "", null, null);
        //send jsonrequest
        jsonRequest.sendResponse(getContext(), new JSONRequest.VolleyCallBack() {
            @Override
            public void onSuccess() {
                parseJSON(jsonRequest.getResponse());
                vid = titleList.toString();
            }
        });

        initialize(devKey, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

        if (mVideoId != null) {
            if (restored) {
                youTubePlayer.play();
            } else {
                youTubePlayer.loadVideo(vid);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), 1).show();
        } else {
            //Handle the failure
            Toast.makeText(getActivity(), "BAD", Toast.LENGTH_LONG).show();
        }
    }

    private void parseJSON(JSONObject pResponse){
        try{

            JSONArray items = pResponse.getJSONArray(Keys.endpointRecipe.KEY_items);
            JSONObject id = items.getJSONObject(0);
            //inside first array element
            JSONObject idObj = id.getJSONObject(Keys.endpointRecipe.KEY_id);
            titleList.append(idObj.getString(Keys.endpointRecipe.KEY_VideoId));
            //if no video tutorials found
            if (titleList==null){

                Toast.makeText(getContext(),"No Video Tutorials Found",Toast.LENGTH_LONG).show();
                Intent notFound = new Intent(getActivity(),RecipeShow.class);
                startActivity(notFound);
            }
        }catch(JSONException ex){

        }
    }
}