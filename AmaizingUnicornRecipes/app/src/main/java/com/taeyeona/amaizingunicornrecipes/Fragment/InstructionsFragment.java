package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ViewDragHelper;
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
                        Elements instructions = null;
                        Element curInstruction = null;
                        Character firstChar;
                        int i = 0;
                        switch (sourceName) {
                            case "closet cooking":
                            case "group recipes":
                                instructions = doc.select(".instructions li");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(i + ". " + curInstruction.text() + "\n\n");
                                }
                                break;
                            case "the pioneer woman":
                                instructions = doc.select(".panel:contains(instructions) > * > .panel-body");
                                instructionLines.append(instructions.text());
                                break;
                            case "two peas and their pod":
                            case "bigoven":
                                instructions = doc.select(".instructions p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "101 cookbooks":
                                instructions = doc.select("#recipe blockquote ~ p");
                                for (i = 0; i < instructions.size() - 1; i++) {
                                    curInstruction = instructions.get(i);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "whats gaby cooking":
                            case "my baking addiction":
                            case "simply recipes":
                            case "pbs food":
                                instructions = doc.select(".instructions li, .instructions p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "all recipes":
                            case "allrecipes":
                                instructions = doc.select(".recipe-directions__list[itemprop=\"recipeInstructions\"] li");
                                for (i = 1; i <= instructions.size(); i++) {
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
                            case "cooking channel":
                                instructions = doc.select(".instructions");
                                instructionLines.append(instructions.text());
                                break;
                            case "my recipes":
                                instructions = doc.select(".field-instructions p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "food network":
                                instructions = doc.select(".directions p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "eatingwell":
                            case "cookstr":
                            case "good housekeeping":
                            case "food.com":
                            case "food & wine":
                            case "leite's culinaria":
                            case "fine cooking":
                            case "chow":
                            case "no recipes":
                            case "food republic":
                                instructions = doc.select("[itemprop=\"recipeInstructions\"] li, [itemprop=\"recipeInstructions\"] p:not(.nutrition):not(.photo-credit)");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "food52":
                            case "simple-nourished-living.com":
                            case "honest cooking":
                            case "bon appetit":
                                instructions = doc.select("[itemprop=\"recipeInstructions\"]");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "bbc good food":
                                instructions = doc.select(".recipe-method li");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "epicurious":
                                instructions = doc.select(".preparation-steps li");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "serious eats":
                                instructions = doc.select(".recipe-procedure");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "cooking with amy":
                                instructions = doc.select(".post-body");
                                instructionLines.append(instructions.text());
                                break;
                            case "martha stewart":
                            case "whole living":
                                instructions = doc.select(".directions-list li, .recipe-step-item");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "foodista":
                                instructions = doc.select(".field-name-field-rec-steps .step-body");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "zester daily":
                                instructions = doc.select("#content-inside p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "saveur":
                                instructions = doc.select(".instruction");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "whole foods":
                                instructions = doc.select(".field-name-field-recipe-directions .field-item");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "goop":
                                instructions = doc.select(".recipe-content p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "the kitchn":
                                instructions = doc.select("#recipe p");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "www.greenthickies.com":
                                instructions = doc.select(".entry-content p:gt(28)");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            case "smitten kitchen":
                                instructions = doc.select(".entry p:gt(15):not(.postmetadata)");
                                for (i = 1; i <= instructions.size(); i++) {
                                    curInstruction = instructions.get(i - 1);
                                    instructionLines.append(curInstruction.text() + "\n\n");
                                }
                                break;
                            default:
                                instructionLines.append(sourceName);
                                break;
                        }
                        if (instructions != null && instructions.size() == 0) {
                            text.setText(R.string.recipe_instructions_not_found);
                        } else {
                            text.setText(instructionLines.toString());
                            Log.d(TAG, sourceName.toUpperCase() + ": " + sourceUrl);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Handle error
                        text.setText(R.string.recipe_instructions_not_found);
                    }
                }
        );
        queue.add(stringRequest);

        /**
         *
         * Button but manages the hide and reveal of the youtube fragment
         * OnClick controls add,show,hide player fragment
         *
         *
         */
            but = (Button) getActivity().findViewById(R.id.vid_tutor_button);
            but.setOnClickListener(new View.OnClickListener() {
            final Fragment playerFragAlpha = new PlayerFragment();

                boolean open = true;
                boolean firstOpen = true;
            @Override
            public void onClick(View v) {

                if(firstOpen)
                {
                    addFragment(playerFragAlpha);
                    firstOpen = false;
                }

                if (open)
                {
                    open = false;
                    but.setText("GO BACK");

                    showFragment(playerFragAlpha);
                }else{
                    open = true;
                    but.setText("VIDEO TUTORIAL");
                    hideFragment(playerFragAlpha);

                }
            }
        });

    }
    /**
     * @author HaoXian
     * @param pFragment
     *
     * Shows the fragment when the user previously chose to hide the player fragment
     * now reveals the player fragment to continue to video
     *
     */
    public void showFragment(Fragment pFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
//        transaction.add(R.id.overlay_fragment_container, pFragment);
        transaction.show(pFragment);
        // Commit the transaction
        transaction.commit();
    }

    /**
     * @author HaoXian
     * @param pFragment
     *
     * The fragment containing YouTube player , works with a slide in animation when
     * the fragment is to be hidden be the user, and reveals the main layout, instructions
     */
    public void hideFragment(Fragment pFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //the underfragment enters,exit
        transaction.setCustomAnimations(R.anim.slide_top_in, R.anim.slide_top_out);//works
        transaction.hide(pFragment);
        // Commit the transaction
        transaction.commit();
    }


    /**
     * @author HaoXian
     * @param pFragment
     *
     * The fragment containing YouTube player , works with a slide in animation when
     * the fragment is added (overlays) the current layout
     */
    public void addFragment(Fragment pFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //the underfragment enters,exit
//        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        transaction.add(R.id.overlay_fragment_container, pFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
