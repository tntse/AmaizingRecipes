package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Created by Hao on 11/14/2015.
 */
public class NavigationDrawAdapter extends ArrayAdapter<String> {

    private String[] navNames;
    private Integer[] navImage;

    public NavigationDrawAdapter(Context context, String[]navNames, Integer[] navImage ) {

        super(context, R.layout.custom_listview_drawer, navNames);

        this.navNames = navNames;
        this.navImage = navImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater navInflater = LayoutInflater.from(getContext());
        View customView = navInflater.inflate(R.layout.custom_listview_drawer, null, false);

        TextView navText = (TextView) customView.findViewById(R.id.customTextView);
        ImageView navImg = (ImageView) customView.findViewById(R.id.customImageView);

        navText.setText(navNames[position]);
        navImg.setImageResource(navImage[position]);
        return customView;
    }
}
