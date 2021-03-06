package com.taeyeona.amaizingunicornrecipes.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.EditSettings;
import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.Recipes;


/**
 * Created by thomastse on 11/17/15.
 */
public class ProfileFragment extends Fragment {
    private ListView favoritesList;
    private Favorites datasource;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> adapter;
    private boolean emptyFavoritesFlag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView profileImage = (ImageView) getActivity().findViewById(R.id.user_picture);
        sharedPreferences = getContext().getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        if(sharedPreferences.contains("Picture")){
            profileImage.setImageBitmap(BitmapFactory.decodeFile(sharedPreferences.getString("Picture", "")));
        }else{
            profileImage.setImageResource(R.drawable.amaizeing);
        }

        TextView name = (TextView) view.findViewById(R.id.user_name);
        TextView email = (TextView) view.findViewById(R.id.user_email);

        name.setText(sharedPreferences.getString("Name", getString(R.string.default_name)));
        email.setText(sharedPreferences.getString("Email", getString(R.string.default_email)));

        datasource = new Favorites(getContext());
        favoritesList = (ListView) view.findViewById(R.id.favorites_list);
        String[] values = datasource.getTitlesFromDB();

        if(values.length == 1){
            if(values[0].equals("")){
                values[0] = "You currently do not have any favorites. Search for some recipes to add to your favorites!";
                emptyFavoritesFlag = true;
            }
        }

        adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1, values);
        favoritesList.setAdapter(adapter);

        if(!emptyFavoritesFlag){
            favoritesList.setClickable(true);
            favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Recipes favoritesToSearch = datasource.searchFavorite(adapter.getItem(position));

                    Intent intent = new Intent(getActivity(), RecipeShow.class);
                    intent.putExtra("Picture", favoritesToSearch.getImageUrl());
                    intent.putExtra("Title", favoritesToSearch.getTitle());
                    intent.putExtra("RecipeID", favoritesToSearch.getRecipeId());
                    intent.putExtra("SourceUrl", favoritesToSearch.getSourceUrl());
                    intent.putExtra("SourceName", favoritesToSearch.getPublisher());
                    intent.putExtra("API", favoritesToSearch.getApi());

                    if(favoritesToSearch.getApi().equals("Edamam")) {
                        intent.putExtra("Ingredients", favoritesToSearch.getIngredientList());
                        intent.putExtra("Nutrients", favoritesToSearch.getNutrientsArray());
                        intent.putExtra("Totals", favoritesToSearch.getDailyTotals());
                    }

                    startActivity(intent);
                }
            });

        }


        Button editProfile = (Button) getActivity().findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(getActivity(), EditSettings.class);
                intentProfile.putExtra("Open", 1);

                startActivity(intentProfile);
            }
        });
    }
}
