package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.EditSettings;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditPantryFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private EditText input;
    private Button searchButton;
    private Button toEditIngredients;
    private Set<String> manager;
    private PantryListAdapter pantryListAdapter;
    private ListView list;
    TextView nullText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        nullText = (TextView) getActivity().findViewById(R.id.emptyPantryText);

        manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        if(!(manager instanceof IngredientsManager))
            manager = new IngredientsManager(manager);

        if(manager.isEmpty()){
            nullText.setText("Your Pantry is empty; add an ingredient!");
        }

        pantryListAdapter = new PantryListAdapter(getContext(), (String [])manager.toArray());

        list = (ListView) view.findViewById(R.id.pantry_list);
        list.setAdapter(pantryListAdapter);

        input = (EditText) view.findViewById(R.id.pantry_edit_text);

        input.setHint(getString(R.string.enter_ingre_name));

        searchButton = (Button) view.findViewById(R.id.pantry_right_button);
        searchButton.setText(getString(R.string.add_ingre));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = input.getText().toString().trim();
                if (!temp.equals(getString(R.string.enter_ingre_name))) {
                    if (!manager.contains(temp)) {
                        manager.add(temp);
                        edit.putStringSet("Ingredients", manager);
                        edit.commit();
                        if(!manager.isEmpty()){
                            nullText.setText("");
                        }
                        pantryListAdapter = new PantryListAdapter(getContext(), (String[])manager.toArray());
                        list.setAdapter(pantryListAdapter);
                    }
                }
            }
        });

        toEditIngredients = (Button) view.findViewById(R.id.pantry_left_button);
        toEditIngredients.setText(getString(R.string.delete_selected));
        toEditIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selected = pantryListAdapter.getSelected();
                manager.removeAll(selected);
                edit.putStringSet("Ingredients", manager);
                edit.commit();
                pantryListAdapter = new PantryListAdapter(getContext(), (String[])manager.toArray());
                if(manager.isEmpty()){
                    nullText.setText("Your Pantry is empty; add an ingredient!");
                }
                list.setAdapter(pantryListAdapter);
            }
        });

    }
}
