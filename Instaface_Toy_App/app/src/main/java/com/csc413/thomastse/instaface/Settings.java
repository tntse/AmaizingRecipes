package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.SharedPreferences;

/**
 * Created by thomastse on 10/13/15.
 */

public class Settings extends Activity {
    private ToggleButton glutenFreeToggle;
    private ToggleButton vegetarianToggle;
    private ToggleButton veganToggle;
    private EditText username;
    private EditText email;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_pane);


        // grab the widgets
        veganToggle = (ToggleButton) findViewById(R.id.vegan_toggle);
        vegetarianToggle = (ToggleButton) findViewById(R.id.vegetarian_toggle);
        glutenFreeToggle = (ToggleButton) findViewById(R.id.gluten_free_toggle);
        username = (EditText) findViewById(R.id.name_edit_text);
        email = (EditText) findViewById(R.id.email_edit_text);
        /*
        // set up database
        if(SettingsDatabase.data == null)
            new SettingsDatabase();

        // set data based on database values
        veganToggle.setChecked(SettingsDatabase.data.searchVegan);
        vegetarianToggle.setChecked(SettingsDatabase.data.searchVegetarian);
        glutenFreeToggle.setChecked(SettingsDatabase.data.searchGlutenFree);
        username.setText(SettingsDatabase.data.name);
        email.setText(SettingsDatabase.data.email);
        */

        sharedPreferences = getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        username.setText(sharedPreferences.getString("Name","Eve Apple"));
        email.setText(sharedPreferences.getString("Email", "IAteTheApple@example.com"));
        veganToggle.setChecked(sharedPreferences.getBoolean("ToggleVegan", false));
        vegetarianToggle.setChecked(sharedPreferences.getBoolean("ToggleVegetarian", false));
        glutenFreeToggle.setChecked(sharedPreferences.getBoolean("ToggleGlutenFree", false));

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

        veganToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("ToggleVegan", veganToggle.isChecked());
                editor.commit();
            }
        });

        vegetarianToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("ToggleVegetarian", vegetarianToggle.isChecked());
                editor.commit();
            }
        });

        glutenFreeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("ToggleGlutenFree", glutenFreeToggle.isChecked());
                editor.commit();
            }
        });

    }

    public void saveSettings(View view){

        // Toast.makeText(Settings.this, "go button clicked!", Toast.LENGTH_SHORT).show();

        /* save settings in database
        SettingsDatabase.data.name = username.getText().toString();
        SettingsDatabase.data.email = email.getText().toString();
        SettingsDatabase.data.searchVegan = veganToggle.isChecked();
        SettingsDatabase.data.searchGlutenFree = glutenFreeToggle.isChecked();
        SettingsDatabase.data.searchVegetarian = vegetarianToggle.isChecked();
        */

        // move to the display page
        Intent displaySettings = new Intent(Settings.this, DisplaySettings.class);
        startActivity(displaySettings);
    }
}
