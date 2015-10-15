package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by thomastse on 10/13/15.
 */

public class Settings extends Activity {
    private ToggleButton glutenFreeToggle;
    private ToggleButton vegetarianToggle;
    private ToggleButton veganToggle;
    private EditText username;
    private EditText email;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_pane);

        // grab the widgets
        veganToggle = (ToggleButton) findViewById(R.id.vegan_toggle);
        vegetarianToggle = (ToggleButton) findViewById(R.id.vegetarian_toggle);
        glutenFreeToggle = (ToggleButton) findViewById(R.id.gluten_free_toggle);
        username = (EditText) findViewById(R.id.name_edit_text);
        email = (EditText) findViewById(R.id.email_edit_text);

        // set up database
        if(SettingsDatabase.data == null)
            new SettingsDatabase();

        // set data based on database values
        veganToggle.setChecked(SettingsDatabase.data.searchVegan);
        vegetarianToggle.setChecked(SettingsDatabase.data.searchVegetarian);
        glutenFreeToggle.setChecked(SettingsDatabase.data.searchGlutenFree);
        username.setText(SettingsDatabase.data.name);
        email.setText(SettingsDatabase.data.email);
    }

    public void saveSettings(View view){

        // Toast.makeText(Settings.this, "go button clicked!", Toast.LENGTH_SHORT).show();

        // save settings in database
        SettingsDatabase.data.name = username.getText().toString();
        SettingsDatabase.data.email = email.getText().toString();
        SettingsDatabase.data.searchVegan = veganToggle.isChecked();
        SettingsDatabase.data.searchGlutenFree = glutenFreeToggle.isChecked();
        SettingsDatabase.data.searchVegetarian = vegetarianToggle.isChecked();

        // move to the display page
        Intent displaySettings = new Intent(Settings.this, DisplaySettings.class);
        startActivity(displaySettings);
    }
}
