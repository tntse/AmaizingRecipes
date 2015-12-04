package com.taeyeona.amaizingunicornrecipes.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.dbHandler;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomastse on 11/17/15.
 */
public class ProfileFragment extends Fragment {
    private SQLiteDatabase database;
    private dbHandler handler;
    private String[] allColumns = { handler.COLUMN_ID,
            handler.COLUMN_TITLE };
    private ListView favoritesList;
    private EditText deleteInput;
    private FavoritesPage fav;
    private Favorites datasource;
    private String title;
    private TextView name;
    private TextView email;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayAdapter<FavoritesPage> adapter;
    private List<FavoritesPage> emptyFavorites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        // editor = sharedPreferences.edit();

        name = (TextView) view.findViewById(R.id.user_name);
        email = (TextView) view.findViewById(R.id.user_email);

        name.setText(sharedPreferences.getString("Name", getString(R.string.default_name)));
        email.setText(sharedPreferences.getString("Email", getString(R.string.default_email)));

        datasource = new Favorites(getContext());
        fav = new FavoritesPage();
        favoritesList = (ListView) view.findViewById(R.id.favorites_list);
        datasource.open();

        List<FavoritesPage> values = datasource.getAllFavorites();
        if(values.isEmpty()){
            emptyFavorites = new ArrayList<FavoritesPage>();
            FavoritesPage temp = new FavoritesPage();
            temp.setTitle("You do not have any Favorite Recipes yet!\n Go Search for some.");
            emptyFavorites.add(temp);
            adapter = new ArrayAdapter<FavoritesPage>(getContext(), android.R.layout.simple_expandable_list_item_1, emptyFavorites);
        }else {
             adapter = new ArrayAdapter<FavoritesPage>(getContext(),
                    android.R.layout.simple_list_item_1, values);
        }

        favoritesList.setAdapter(adapter);
        favoritesList.isClickable();
        favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();

                Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();

            }
        });

    }


}
