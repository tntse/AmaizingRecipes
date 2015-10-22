package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Hao on 9/26/2015.
 */
public class Profile extends Activity {

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
}
