package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by thomastse on 10/14/15.
 */
public class DisplaySettings extends Activity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.display_settings);

        TextView display = (TextView) findViewById(R.id.display_text_view);

        // get Database info
        String displaytext = SettingsDatabase.data.toString();

        // display data
        display.setText(displaytext);

    }

    public String onOff(boolean bool){
        if(bool)
            return "On";
        return "Off";
    }

}
