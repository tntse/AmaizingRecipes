package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ViewDragHelper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.taeyeona.amaizingunicornrecipes.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Chau on 11/7/2015.
 */


public class InstructionsFragment extends Fragment {
    String TAG = "Chris";
    Button but;
    Document doc;
    StringBuilder instructionLines = new StringBuilder();
    private ViewDragHelper vdh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_instructions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {




        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String sourceUrl = getArguments().getString("SourceUrl");
        final String sourceName = getArguments().getString("SourceName").toLowerCase().trim();
        final TextView text = (TextView) getActivity().findViewById(R.id.instruct_text);
        text.setMovementMethod(new ScrollingMovementMethod());

//        try {
//            doc = Jsoup.connect(sourceUrl).get();
//        } catch (IOException e) {
//            Log.d("Error", e.getMessage());
//        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, sourceUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data) {
                        Document doc = Jsoup.parse(data);
                        Elements instructions;
                        Element curInstruction = null;
                        Character firstChar;
                        int i = 0;
                        switch (sourceName) {
                            case "closet cooking":
                                instructions = doc.select(".instructions li");
                                for(i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i-1);
                                    instructionLines.append(i + ". " + curInstruction.text() + "\n\n");
                                }
                                break;
                            case "the pioneer woman":
                                instructions = doc.select(".panel:contains(instructions) > * > .panel-body");
                                instructionLines.append(instructions.text());
                                break;
                            case "two peas and their pod":
                                instructions = doc.select(".instructions p");
                                for(i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i-1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "101 cookbooks":
                                instructions = doc.select("#recipe blockquote ~ p");
                                for(i = 0; i < instructions.size() - 1; i++){
                                    curInstruction = instructions.get(i);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "whats gaby cooking":
                            case "my baking addiction":
                            case "simply recipes":
                                instructions = doc.select(".instructions li, .instructions p");
                                for(i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                        instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "all recipes":
                                instructions = doc.select(".recipe-directions__list[itemprop=\"recipeInstructions\"] li");
                                for(i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    firstChar = curInstruction.text().charAt(0);
                                    if (firstChar >= '0' && firstChar <= '9') {
                                        /* Can assume that the recipe contains numbers already */
                                        instructionLines.append(curInstruction.text() + "\n\n");
                                    } else {
                                        /* Recipe does not contain numbers so append them */
                                        instructionLines.append(i + ". " + curInstruction.text() + "\n\n");
                                    }
                                }
                                break;
                            default:
                                instructionLines.append(sourceName);
                                break;
                        }

                        text.setText(instructionLines.toString());
                        Log.d(TAG, sourceName.toUpperCase() + ": " + sourceUrl);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle error
                    }
                }
        );
        queue.add(stringRequest);


//        but = (Button) getActivity().findViewById(R.id.vid_tutor_button);
//
//        but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), );
//                intent.putExtra("Title", getArguments().getString("Title"));
//
//                startActivity(intent);
//            }
//        });




        // replace button with swipe fragment up
        but = (Button) getActivity().findViewById(R.id.vid_tutor_button);
        but.setOnClickListener(new View.OnClickListener() {
            Fragment playerFrag = new PlayerFragment();
            String flag;
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Player_.class);
//                intent.putExtra("Title", getArguments().getString("Title"));
//                startActivity(intent);

                flag = but.getText().toString();

                if(flag == "VIDEO TUTORIAL") {

                    but.setText("GO BACK");
//
                    addFragment(playerFrag);
                }
                else{
                    but.setText("VIDEO TUTORIAL");
                    removeFragment(playerFrag);
                }
            }
        });


    }

    public void addFragment(Fragment pFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        transaction.add(R.id.overlay_fragment_container, pFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
    public void removeFragment(Fragment pFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //the underfragment enters,exit
//        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
//        transaction.setCustomAnimations(R.anim.slide_top_in, R.anim.slide_top_out); //maybe
        transaction.setCustomAnimations(R.anim.slide_top_in, R.anim.slide_top_out);



        transaction.remove(pFragment);
        // Commit the transaction
        transaction.commit();
    }
}
