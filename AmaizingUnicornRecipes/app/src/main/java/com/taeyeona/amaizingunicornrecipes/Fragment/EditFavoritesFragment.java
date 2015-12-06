package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditFavoritesFragment extends Fragment {
    private Favorites favoritesData;
    private ArrayAdapter<String> adapter;
    private List<FavoritesPage> emptyFavorites;
    private String[] values;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_favorites, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesData = new Favorites(getContext());
        final ListView favoritesList = (ListView) getActivity().findViewById(R.id.edit_favorites_list);
        values = favoritesData.getTitlesFromDB();
        emptyFavorites = new ArrayList<FavoritesPage>();
        if(values.length<1){
            String[] emptyString = {"YOU DO NOT HAVE FAVORITES U:"};
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, emptyString);

        }else{
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
        }
        favoritesList.setAdapter(adapter);
        favoritesList.setClickable(true);
        favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Highlight!", Toast.LENGTH_SHORT).show();
                FavoritesPage favoritesToDelete = favoritesData.searchFavorite(adapter.getItem(position));
                emptyFavorites.add(favoritesToDelete);

            }
        });

        Button deleteFavorites = (Button)getActivity().findViewById(R.id.delete_favorites);
        deleteFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<emptyFavorites.size(); i++){
                    favoritesData.deleteFavorite(emptyFavorites.get(i));
                }
                if(values.length < 1){
                    String[] emptyString = {"YOU DO NOT HAVE FAVORITES U:"};
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, emptyString);
                }else{
                    adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, favoritesData.getTitlesFromDB());
                }
                favoritesList.setAdapter(adapter);
            }
        });

    }

}
