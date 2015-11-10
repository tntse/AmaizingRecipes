package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thomastse on 11/7/15.
 */
public class EditIngredients extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_v2);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.profile_items);
        HashMap<String, List<String>> profileHash = ProfileHash.getProfileHash();
        expandableListView.setAdapter(new ProfileAdapter(this, profileHash, (List<String>) profileHash.keySet()));

    }


}
