package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Activity.EditSettings;
import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.MainActivity;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryGridViewAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by thomastse on 11/17/15.
 */
public class PantryFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private EditText input;
    private Button searchButton;
    private Button toEditIngredients;
    private Set<String> manager;
    private PantryListAdapter pantryListAdapter;
    private ListView list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);

        manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        if(!(manager instanceof IngredientsManager))
            manager = new IngredientsManager(manager);

        pantryListAdapter = new PantryListAdapter(getContext(), (String [])manager.toArray());

        list = (ListView) view.findViewById(R.id.pantry_list);
        list.setAdapter(pantryListAdapter);

        input = (EditText) view.findViewById(R.id.pantry_edit_text);
        input.setHint(getString(R.string.enter_search_query));

        searchButton = (Button) view.findViewById(R.id.pantry_right_button);
        searchButton.setText(getString(R.string.search_for_recipe));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = pantryListAdapter.getSelected();
                String query = input.getText().toString().trim();
                if(query.equals(getString(R.string.enter_search_query)))
                    query = "";
                // TODO: link this button with Recipe Search Fragment
                ((MainActivity)getActivity()).addData(list);
                ((MainActivity)getActivity()).addData(query);
                ((MainActivity)getActivity()).addData(true);
                ((ViewPager)getActivity().findViewById(R.id.main_pages)).setCurrentItem(2);
            }
        });

        toEditIngredients = (Button) view.findViewById(R.id.pantry_left_button);
        toEditIngredients.setText(getString(R.string.edit_ingredients));
        toEditIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditSettings.class);
                intent.putExtra("Open", 2);
                startActivity(intent);
            }
        });
    }
}
