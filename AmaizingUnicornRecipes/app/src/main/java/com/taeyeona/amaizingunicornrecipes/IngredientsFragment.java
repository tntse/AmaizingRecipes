package com.taeyeona.amaizingunicornrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Chau on 11/7/2015.
 */
public class IngredientsFragment extends Fragment {

    StringBuilder ingredients = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = (TextView) getActivity().findViewById(R.id.textView5);
        final TextView text2 = (TextView) getActivity().findViewById(R.id.textView8);
        text.setText(getArguments().getString("Title"));

        if(getArguments().getString("API").equals("Food2Fork")){
            final JSONRequest request = new JSONRequest();
            request.createResponse(Auth.GET_URL, Auth.STRING_KEY, Auth.F2F_Key, "", "",
                    "", "", "", "", "", "", "", "", "", 0.0, 0.0, getArguments().getString("RecipeID"));
            request.sendResponse(getActivity().getApplicationContext());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONObject response = request.getResponse();
                    try {
                        JSONObject recipe = response.getJSONObject("recipe");
                        Log.d(IngredientsFragment.class.getSimpleName(), recipe.toString());
                        JSONArray ingredientsList = recipe.getJSONArray("ingredients");
                        Log.d(IngredientsFragment.class.getSimpleName(), ingredientsList.toString());
                        for (int i = 0; i < ingredientsList.length(); i++) {
                            ingredients.append(ingredientsList.get(i));
                            ingredients.append('\n');
                        }
                        text2.setText(ingredients.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);

        }else{
            text2.setText("LOL EDAMAM FOOD STUFFS");
        }

        Button but = (Button) getActivity().findViewById(R.id.button4);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MissingIngredients.class);
                startActivity(intent);

            }
        });

    }
}
