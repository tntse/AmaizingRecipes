package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.Activity.EditSettings;
import com.taeyeona.amaizingunicornrecipes.Activity.MainActivity;
import com.taeyeona.amaizingunicornrecipes.Adapter.PantryListAdapter;
import com.taeyeona.amaizingunicornrecipes.IngredientsManager;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by thomastse on 11/17/15.
 */
public class PantryFragment extends Fragment
{
    private SharedPreferences sharedPreferences;
    private EditText input;
    private Button searchButton;
    private Button toEditIngredients;
    private Set<String> manager;
    private PantryListAdapter pantryListAdapter;
    private ListView list;

    private TextView tut_swipe;
    private TextView tut_check_items;
    private Button tut_button;
    private ImageView tut_Image;

    private GestureDetectorCompat gestureDetector;

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
                if (query.equals(getString(R.string.enter_search_query)))
                    query = "";
                ((MainActivity) getActivity()).addData(list);
                ((MainActivity) getActivity()).addData(query);
                ((MainActivity) getActivity()).addData(true);
                // ((ViewPager)getActivity().findViewById(R.id.main_pages)).setCurrentItem(2);
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

         tut_swipe = (TextView)view.findViewById(R.id.tutorial_swipe_to_search);
        tut_check_items = (TextView)view.findViewById(R.id.tutorial_check_items);
        tut_button = (Button)view.findViewById(R.id.tutorial_got_it);
        tut_Image = (ImageView)view.findViewById(R.id.tutorial_swipe_image);


        tut_Image.setVisibility(View.INVISIBLE);
        tut_swipe.setVisibility(View.INVISIBLE);
        tut_check_items.setVisibility(View.INVISIBLE);
        tut_button.setVisibility(View.INVISIBLE);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isNotFirstRun = sharedPreferences.getBoolean("isNotFirstRun", false);

        if (!isNotFirstRun) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isNotFirstRun", true);
            editor.commit();

            tut_Image.setVisibility(View.VISIBLE);
            tut_swipe.setVisibility(View.VISIBLE);
            tut_check_items.setVisibility(View.VISIBLE);
            tut_button.setVisibility(View.VISIBLE);

            tut_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tut_button.setVisibility(View.INVISIBLE);
                    tut_check_items.setVisibility(View.INVISIBLE);
                    tut_swipe.setVisibility(View.INVISIBLE);
                    tut_Image.setVisibility(View.INVISIBLE);
                }
            });

        }

    }


}


