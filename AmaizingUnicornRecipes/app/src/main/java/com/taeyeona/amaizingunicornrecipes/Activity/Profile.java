package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.taeyeona.amaizingunicornrecipes.Adapter.ProfileAdapter;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Thomas Tse
 * @version 2.0
 * @since 2015-09-26
 */
public class Profile extends Activity {

    /**
     * Sets all private variables and displays the current settings.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in. Otherwise null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.profile_v2);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.profile_items);
        HashMap<String, List<String>> profileHash = ProfileHash.getProfileHash();
        expandableListView.setAdapter(new ProfileAdapter(this, profileHash, new ArrayList<String>(profileHash.keySet())));

    }

/*
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String[] searchSettings = {"High-Protein", "Low-Carb", "Low-Fat", "Balanced Diet", "Low-Sodium",
            "High-Fiber", "No-sugar", "Sugar-Conscious", "Gluten-Free", "Vegetarian", "Vegan",
            "Paleo", "Wheat-Free", "Dairy-Free", "Egg Free", "Soy Free", "Fish Free",
            "ShellFish Free", "Tree Nut Free", "Low Potassium", "Alcohol Free", "Kidney Friendly",
            "Peanut Free"};
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        sharedPreferences = getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        username = (EditText) findViewById(R.id.name_edit_text);
        email = (EditText) findViewById(R.id.email_edit_text);
        username.setText(sharedPreferences.getString("Name", "Acorn Amaizing"));
        email.setText(sharedPreferences.getString("Email", "ThisJokeIsCorny@example.com"));

        ListAdapter toggleAdapter = new SearchToggleAdapter(this, searchSettings);
        ListView toggleListView = (ListView) findViewById(R.id.toggle_list_view);



        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor.putString("Name", username.getText().toString());
                editor.commit();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor.putString("Email", email.getText().toString());
                editor.commit();
            }
        });

        toggleListView.setAdapter(toggleAdapter);
    }

    */
}
