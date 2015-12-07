package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.taeyeona.amaizingunicornrecipes.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by HaoXian on 11/16/2015.
 *
 * Thomas Tse helped a lot!
 */
public class ToggleDrawerAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ToggleDrawerAdapter(Context context, String []string) {
        super(context, R.layout.profile_toggle_button_layout, string);

        sharedPreferences = context.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View theView = inflater.inflate(R.layout.profile_toggle_button_layout, parent, false);
        String settingName = getItem(position);
        TextView text = (TextView) theView.findViewById(R.id.toggle_text_layout);

        text.setText(settingName +  ":" );
        ToggleButton toggleButton = (ToggleButton) theView.findViewById(R.id.search_toggle_button);
        toggleButton.setChecked(sharedPreferences.getBoolean("Search" + settingName, false));
        toggleButton.setContentDescription("Search" + settingName);

        toggleButton.setOnClickListener(this);

        return theView;
    }

    @Override
    public void onClick(View v) {
        ToggleButton clicked = (ToggleButton)v;
        editor.putBoolean(clicked.getContentDescription().toString(), clicked.isChecked());
        editor.commit();
    }
}
