package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryGridViewAdapter;
import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.List;
import java.util.Set;

/**
 * Created by thomastse on 11/17/15.
 */
public class PantryFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText input;
    private Button addButton;
    private GridView inGridients;
    private Set<String> manager;
    private PantryGridViewAdapter gridManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        input = (EditText)view.findViewById(R.id.pantry_item_edit_text);
        addButton = (Button) view.findViewById(R.id.pantry_add_button);
        inGridients = (GridView) view.findViewById(R.id.pantry_grid_view);

        manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());

        if(!(manager instanceof IngredientsManager))
            manager = new IngredientsManager(manager);

        gridManager = new PantryGridViewAdapter(getContext(), (String[])manager.toArray());

        inGridients.setAdapter(gridManager);

        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String inputText = input.getText().toString().trim();

                if(inputText.equals(""))
                    Toast.makeText(getContext(), "Please donut attempt to add a Null String Cheese.", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContext(), "Input: " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                    if(!manager.contains(inputText)) {
                        manager.add(inputText);
                        editor.putStringSet("Ingredients", manager);
                        editor.commit();
                        gridManager = new PantryGridViewAdapter(getContext(), (String[]) manager.toArray());
                        inGridients.setAdapter(gridManager);
                    }

                }
            }
        });




    }
}
