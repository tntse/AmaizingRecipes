package com.taeyeona.amaizingunicornrecipes.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.Recipes;


/**
 * Created by thomastse on 11/17/15.
 */
public class ProfileFragment extends Fragment {
    private ListView favoritesList;
    private Favorites datasource;
    private TextView name;
    private TextView email;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> adapter;
    private ImageView profileImage;
    private boolean emptyFavoritesFlag = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = (ImageView) getActivity().findViewById(R.id.user_picture);
        sharedPreferences = getContext().getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("Picture")){
            profileImage.setImageBitmap(BitmapFactory.decodeFile(sharedPreferences.getString("Picture", "")));
        }

        name = (TextView) view.findViewById(R.id.user_name);
        email = (TextView) view.findViewById(R.id.user_email);

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

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
        Log.d("favoritesAdapter", "set adapter properly");
        favoritesList.setAdapter(adapter);

        if(!emptyFavoritesFlag){
            favoritesList.setClickable(true);
            Log.d("favoritesAdapter", "set adapter pre on click");
            favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Recipes favoritesToSearch = datasource.searchFavorite(adapter.getItem(position));

                    Intent intent = new Intent(getActivity(), RecipeShow.class);
                    intent.putExtra("Picture", favoritesToSearch.getImageUrl());
                    Log.d("Profile", favoritesToSearch.getImageUrl());
                    intent.putExtra("Title", favoritesToSearch.getTitle());
                    intent.putExtra("RecipeID", favoritesToSearch.getRecipeId());
                    intent.putExtra("SourceURL", favoritesToSearch.getSourceUrl());
                    intent.putExtra("SourceName", favoritesToSearch.getPublisher());
                    intent.putExtra("API", favoritesToSearch.getApi());

                    if(favoritesToSearch.getApi().equals("Edamam")) {
                        intent.putExtra("Ingredients", favoritesToSearch.getIngredientList());
                        intent.putExtra("Nutrients", favoritesToSearch.getNutrientsArray());
                    }

                    startActivity(intent);

                }
            });


        }
    }

}
