package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditPantryFragment extends Fragment {
    private SharedPreferences.Editor edit;
    private EditText input;
    private Set<String> manager;
    private PantryListAdapter pantryListAdapter;
    private ListView list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        list = (ListView) view.findViewById(R.id.pantry_list);

        manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        if(!(manager instanceof IngredientsManager))
            manager = new IngredientsManager(manager);

        getListAndSetAdapter();

        input = (EditText) view.findViewById(R.id.pantry_edit_text);
        input.setHint(getString(R.string.enter_ingre_name));

        Button addButton = (Button) view.findViewById(R.id.pantry_right_button);
        addButton.setText(getString(R.string.add_ingre));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = input.getText().toString().trim();
                if (!temp.equals(getString(R.string.enter_ingre_name)) && !temp.equals("")) {
                    if (!manager.contains(temp)) {
                        manager.add(temp);
                        edit.putStringSet("Ingredients", manager);
                        edit.commit();
                        input.setText("");
                        getListAndSetAdapter();
                        list.setSelection(pantryListAdapter.getCount()-1);
                    }
                }
            }
        });

        Button deleteSelected = (Button) view.findViewById(R.id.pantry_left_button);
        deleteSelected.setText(getString(R.string.delete_selected));
        deleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!manager.isEmpty()) {
                    ArrayList<String> selected = pantryListAdapter.getSelected();
                    manager.removeAll(selected);
                    edit.putStringSet("Ingredients", manager);
                    edit.commit();
                    getListAndSetAdapter();
                }
            }
        });

    }

    /**
     *
     */
    private void getListAndSetAdapter(){
       String[] emptyManager = {getString(R.string.empty_pantry)};

        if(manager.isEmpty()){
            list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, emptyManager));
        }else {
            pantryListAdapter = new PantryListAdapter(getContext(), (String[]) manager.toArray());
            list.setAdapter(pantryListAdapter);
        }
    }
}
