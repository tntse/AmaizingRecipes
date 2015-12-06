package com.taeyeona.amaizingunicornrecipes.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.FavoritesAdapter;
import com.taeyeona.amaizingunicornrecipes.Activity.RecipeShow;
import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomastse on 11/17/15.
 */
public class ProfileFragment extends Fragment {
    // private SQLiteDatabase database;
    // private dbHandler handler;
    // private String[] allColumns = { handler.COLUMN_ID, handler.COLUMN_TITLE };
    private ListView favoritesList;
    private EditText deleteInput;
    private FavoritesPage fav;
    private Favorites datasource;
    // private String title;
    private TextView name;
    private TextView email;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayAdapter<String> adapter;
    private List<FavoritesPage> emptyFavorites;
    private ImageView profileImage;

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
     //   fav = new FavoritesPage();
        favoritesList = (ListView) view.findViewById(R.id.favorites_list);
        //datasource.open();
        String[] values = datasource.getTitlesFromDB();
        if(values.length < 1){
            emptyFavorites = new ArrayList<FavoritesPage>();
       //     FavoritesPage temp = new FavoritesPage();
//            temp.setTitle("You do not have any Favorite Recipes yet!\n Go Search for some.");
//            temp.setHandler(null);
//            temp.setId(-1);
//            temp.setIngredientList("");
//            temp.setNutrients("");
//            temp.setPicture("");
//            temp.setRecipeId("");
//            temp.setSourceName("");
//            temp.setSourceUrl("");
//            temp.setApi("");
//            emptyFavorites.add(temp);
            String[] emptyString = {"YOU DO NOT HAVE FAVORITES U:"};

            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, emptyString);//new FavoritesAdapter(getContext(), emptyString);
        }else {
             adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);//new FavoritesAdapter(getContext(), values);
        }
        Log.d("favoritesAdapter", "set adapter properly");
        favoritesList.setAdapter(adapter);
        favoritesList.setClickable(true);
        //crashes after here :(
        Log.d("favoritesAdapter", "set adapter pre on click");
        favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FavoritesPage favoritesToSearch = datasource.searchFavorite(adapter.getItem(position));

                Intent intent = new Intent(getActivity(), RecipeShow.class);
                intent.putExtra("Picture", favoritesToSearch.getPicture());
                intent.putExtra("Title", favoritesToSearch.getTitle());
                intent.putExtra("RecipeID", favoritesToSearch.getRecipeId());
                intent.putExtra("SourceURL", favoritesToSearch.getSourceUrl());
                intent.putExtra("SourceName", favoritesToSearch.getSourceName());
                intent.putExtra("API", favoritesToSearch.getApi());

                if(favoritesToSearch.getApi().equals("Edamam")) {
                    intent.putExtra("Ingredients", favoritesToSearch.getIngredientList());
                    intent.putExtra("Nutrients", favoritesToSearch.getNutrients());
                }

                startActivity(intent);
  //              Log.d("favoritesAdapter", "made it to the on item click listener");


            }
        });


    }

}
