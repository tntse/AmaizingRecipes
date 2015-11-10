package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by thomastse on 10/22/15.
 */
public class SearchToggleAdapter extends ArrayAdapter<String> implements View.OnClickListener{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SearchToggleAdapter(Context context, String[] list) {
        super(context, R.layout.profile_toggle_button_layout, list);
        sharedPreferences = context.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View theView = inflater.inflate(R.layout.profile_toggle_button_layout, parent, false);
        String searchOption = getItem(position);
        TextView toggleText = (TextView) theView.findViewById(R.id.toggle_text_layout);
        ToggleButton toggleButton = (ToggleButton) theView.findViewById(R.id.search_toggle_button);

        toggleText.setText(searchOption + ":");

        toggleButton.setChecked(sharedPreferences.getBoolean("Search" + searchOption, false));
        toggleButton.setContentDescription("Search" + searchOption);

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
