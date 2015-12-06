package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.Recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditFavoritesFragment extends Fragment {
    private Favorites favoritesData;
    private ArrayAdapter<String> adapter;
    private String[] values;
    private boolean emptyFavoritesFlag = false;
    private ArrayList<String> selected;
    private ListView favoritesList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_favorites, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesData = new Favorites(getContext());
        favoritesList = (ListView) getActivity().findViewById(R.id.edit_favorites_list);

        getFavoritesAndSetAdapter();

        Button deleteFavorites = (Button)getActivity().findViewById(R.id.delete_favorites);
        deleteFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emptyFavoritesFlag){
                    selected = ((PantryListAdapter)adapter).getSelected();

                    for(int i = 0; i < selected.size(); i ++){
                        Recipes favoritesToDelete = favoritesData.searchFavorite(adapter.getItem(i));
                        favoritesData.deleteFavorite(favoritesToDelete);
                    }

                    getFavoritesAndSetAdapter();
                }
            }
        });

    }

    private void getFavoritesAndSetAdapter(){

        values = favoritesData.getTitlesFromDB();
        if(values.length == 1 && values[0].equals("")){
                values[0] = "You currently do not have any favorites. Search for some recipes to add to your favorites!";
                emptyFavoritesFlag = true;
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
        }else {
            adapter = new PantryListAdapter(getContext(), values);
        }
        favoritesList.setAdapter(adapter);
    }

}
