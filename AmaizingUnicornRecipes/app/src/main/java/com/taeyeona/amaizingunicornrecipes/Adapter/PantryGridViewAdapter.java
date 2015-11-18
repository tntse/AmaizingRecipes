package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ToggleButton;

import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Created by thomastse on 11/18/15.
 */
public class PantryGridViewAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    public PantryGridViewAdapter(Context context, String[] ingredients) {
        super(context, R.layout.toggle_button, ingredients);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View theView = inflater.inflate(R.layout.toggle_button, parent, false);
        String ingredient = getItem(position);
        ToggleButton toggle = (ToggleButton) theView.findViewById(R.id.toggle_button);
        toggle.setText(ingredient);
        toggle.setContentDescription(ingredient);
        toggle.setOnClickListener(this);

        return theView;
    }

    @Override
    public void onClick(View view) {
        ToggleButton button = (ToggleButton) view;
        button.setText(button.getContentDescription());
    }
}
