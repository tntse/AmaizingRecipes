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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.EditSettings;
import com.taeyeona.amaizingunicornrecipes.Activity.MainActivity;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.Set;

/**
 * Created by thomastse on 11/17/15.
 */
public class PantryFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ListView list;
    private EditText input;
    private PantryListAdapter pantryListAdapter;
    private TextView nullText;

    private TextView tutSwipe;
    private TextView tutCheckItems;
    private Button tutButton;
    private ImageView tutImage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);

        Set<String> manager = sharedPreferences.getStringSet("Ingredients", new IngredientsManager());
        if(!(manager instanceof IngredientsManager))
            manager = new IngredientsManager(manager);

        if(manager.isEmpty()) {
            nullText = (TextView) getActivity().findViewById(R.id.emptyPantryText);
            nullText.setText(getString(R.string.empty_pantry));
        }else{
            pantryListAdapter = new PantryListAdapter(getContext(), (String[]) manager.toArray());
            list = (ListView) view.findViewById(R.id.pantry_list);
            list.setAdapter(pantryListAdapter);

        }

        input = (EditText) view.findViewById(R.id.pantry_edit_text);
        input.setHint(getString(R.string.enter_search_query));

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button searchButton = (Button) view.findViewById(R.id.pantry_right_button);
        searchButton.setText(getString(R.string.search_for_recipe));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = input.getText().toString().trim();
                if (query.equals(getString(R.string.enter_search_query)))
                    query = "";
                ((MainActivity)getActivity()).addData(pantryListAdapter.getSelected());
                ((MainActivity) getActivity()).addData(query);
                //((MainActivity) getActivity()).addData();

                ((ViewPager)getActivity().findViewById(R.id.main_pages)).setCurrentItem(2);
            }
        });

        Button toEditIngredients = (Button) view.findViewById(R.id.pantry_left_button);
        toEditIngredients.setText(getString(R.string.edit_ingredients));
        toEditIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditSettings.class);
                intent.putExtra("Open", 2);
                startActivity(intent);
            }
        });

        tutSwipe = (TextView)view.findViewById(R.id.tutorial_swipe_to_search);
        tutCheckItems = (TextView)view.findViewById(R.id.tutorial_check_items);
        tutButton = (Button)view.findViewById(R.id.tutorial_got_it);
        tutImage = (ImageView)view.findViewById(R.id.tutorial_swipe_image);

        boolean isFirstRun = sharedPreferences.getBoolean("isFirstPantryRun", true);

        if (isFirstRun) {
            tutImage.setVisibility(View.VISIBLE);
            tutSwipe.setVisibility(View.VISIBLE);
            tutCheckItems.setVisibility(View.VISIBLE);
            tutButton.setVisibility(View.VISIBLE);
            nullText.setVisibility(View.INVISIBLE);

            tutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstPantryRun", false);
                    editor.commit();
                    tutButton.setVisibility(View.INVISIBLE);
                    tutCheckItems.setVisibility(View.INVISIBLE);
                    tutSwipe.setVisibility(View.INVISIBLE);
                    tutImage.setVisibility(View.INVISIBLE);
                    nullText.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}


