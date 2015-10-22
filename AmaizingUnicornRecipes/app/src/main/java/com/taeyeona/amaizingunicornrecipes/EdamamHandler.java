package com.taeyeona.amaizingunicornrecipes;

import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.chris.edamam.Auth;import com.example.chris.edamam.JSONRequest;import com.example.chris.edamam.Keys;import com.example.chris.edamam.R;import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;import java.lang.Override;import java.lang.Runnable;import java.lang.String;import java.lang.StringBuilder;

/**
 * Created by chris on 10/21/15.
 */
public class EdamamHandler {

    public String[] getNutrition(){
        String returnStringArray[] = new String[1000];


        String q = ET.getText().toString().replace(" ", "%20");

        final JSONRequest edamamRequest = new JSONRequest();
        edamamRequest.createResponse(Auth.EDAMAM_URL, "app_key", Auth.EDAMAM_KEY, "app_id",
                Auth.EDAMAM_ID, q, null, null, "0", "1");

        MainActivity.context = getApplicationContext();
        edamamRequest.sendResponse(MainActivity.context);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                String nutritionList[] = new String[100];
                progress.setVisibility(View.INVISIBLE);

                TextView textView = (TextView) findViewById(R.id.textView2);

                textView.setMovementMethod(new ScrollingMovementMethod());

                        /* Process Array into Stringbuilder for output */
                StringBuilder output = new StringBuilder();

                JSONObject response = edamamRequest.getResponse();

                try{
                    JSONArray arrayTitle = response.getJSONArray(Keys.endpointRecipe.HITS);
                    JSONObject recipeObj = arrayTitle.getJSONObject(0).getJSONObject("recipe");

                    JSONArray digestObj = recipeObj.getJSONArray("digest");
                            /* Set Recipe title as first item in array */
                    nutritionList[0] = recipeObj.getString("label");

                    for (int i = 0; i < digestObj.length(); i++) {
                        JSONObject nubit = (JSONObject) digestObj.get(i); // NUtritional BIT
                        nutritionList[i + 1] = nubit.getString("label") + " " + nubit.getString("total")
                                + " " + nubit.getString("unit");
                    }

                }catch(JSONException ex)

                {

                }


            }

        });
        return returnStringArray;
    }
}

