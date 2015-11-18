package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taeyeona.amaizingunicornrecipes.Activity.Player;
import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Created by Chau on 11/7/2015.
 */


public class InstructionsFragment extends Fragment {

    Button but;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instructions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        but = (Button) getActivity().findViewById(R.id.button2);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Player.class);
                intent.putExtra("Title", getArguments().getString("Title"));

                startActivity(intent);
            }
        });

    }
}
