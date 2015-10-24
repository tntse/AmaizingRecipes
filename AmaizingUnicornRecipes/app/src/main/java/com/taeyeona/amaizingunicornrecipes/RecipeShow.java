package com.taeyeona.amaizingunicornrecipes;

        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.text.method.ScrollingMovementMethod;
        import android.util.Log;
        import android.view.View;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

/**
 * Created by Chau on 10/13/2015.
 */
public class RecipeShow extends AppCompatActivity{

    ProgressBar progress;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);
        Log.d("", "I made it");
        String title = getIntent().getStringExtra("Title");

        title = title.replace(" ", "%20");
        Log.d("", "Title:" + title);

        String returnStringArray[] = new String[1000];
        progress = (ProgressBar) findViewById(R.id.progressBar2);


        final JSONRequest edamamRequest = new JSONRequest();
        edamamRequest.createResponse(Auth.EDAMAM_URL, "app_key", Auth.EDAMAM_KEY, "app_id",
                Auth.EDAMAM_ID, title,"", null, null, "0", "1", "", "", "", 0.0, 0.0);

        edamamRequest.sendResponse(getApplicationContext());


        final String nutritionList[] = new String[100];


        final TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setMovementMethod(new ScrollingMovementMethod());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                progress.setVisibility(View.INVISIBLE);
                final JSONObject response = edamamRequest.getResponse();


                        /* Process Array into Stringbuilder for output */
                StringBuilder output = new StringBuilder();

                try {
                    JSONArray arrayTitle = response.getJSONArray(Keys.endpointRecipe.HITS);
                    Log.d("", "arrayTitle: " + arrayTitle);
                    JSONObject recipeObj = arrayTitle.getJSONObject(0).getJSONObject("recipe");

                    JSONArray digestObj = recipeObj.getJSONArray("digest");
                            /* Set Recipe title as first item in array */
                    nutritionList[0] = recipeObj.getString("label");

                    for (int i = 0; i < digestObj.length(); i++) {
                        JSONObject nubit = (JSONObject) digestObj.get(i); // NUtritional BIT
                        nutritionList[i + 1] = nubit.getString("label") + " " + nubit.getString("total")
                                + " " + nubit.getString("unit");
                    }

                    for(String s : nutritionList) {
                        if(s != null && !s.isEmpty()) {
                            output.append(s);
                        }
                    }

                    textView.setText(output.toString());
                } catch (JSONException ex)

                {

                }

            }

        }, 10000);

    }
}