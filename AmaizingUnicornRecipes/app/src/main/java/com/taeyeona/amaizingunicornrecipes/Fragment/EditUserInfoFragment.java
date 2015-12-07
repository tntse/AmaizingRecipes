package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.taeyeona.amaizingunicornrecipes.Adapter.ProfileAdapter;
import com.taeyeona.amaizingunicornrecipes.ProfileHash;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thomastse on 11/24/15.
 */
public class EditUserInfoFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_v2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableListView expandableListView = (ExpandableListView) getActivity().findViewById(R.id.profile_items);
        HashMap<String, List<String>> profileHash = ProfileHash.getProfileHash();
        expandableListView.setAdapter(new ProfileAdapter(getContext(), profileHash, new ArrayList<String>(profileHash.keySet()), getArguments()));
        expandableListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }
}
