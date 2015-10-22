package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.content.SharedPreferences;

/**
 * Created by thomastse on 10/14/15.
 */
public class DisplaySettings extends Activity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.display_settings);

        TextView display = (TextView) findViewById(R.id.display_text_view);
        SharedPreferences sharedPreferences = getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);

        /* get Database info
        String displaytext = SettingsDatabase.data.toString();
        */

        String displaytext = "Name: " + sharedPreferences.getString("Name", "Eve Apple") + "\n";
        displaytext += "Email: " + sharedPreferences.getString("Email", "IAteTheApple@example.com") + "\n";
        displaytext += "Search Vegan? " + onOff(sharedPreferences.getBoolean("ToggleVegan", false)) + "\n";
        displaytext += "Search Vegetarian? " + onOff(sharedPreferences.getBoolean("ToggleVegetarian", false)) + "\n";
        displaytext += "Search GlutenFree? " + onOff(sharedPreferences.getBoolean("ToggleGlutenFree", false)) + "\n";

        // display data
        display.setText(displaytext);

    }

    public String onOff(boolean bool){
        if(bool)
            return "On";
        return "Off";
    }

}
