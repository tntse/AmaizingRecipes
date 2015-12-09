package com.taeyeona.amaizingunicornrecipes.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeyeona.amaizingunicornrecipes.Activity.Favorites;
import com.taeyeona.amaizingunicornrecipes.Activity.MissingIngredients;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.JSONRequest;
import com.taeyeona.amaizingunicornrecipes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chau on 11/7/2015.
 */
public class IngredientsFragment extends Fragment {
    private StringBuilder ingredients = new StringBuilder();

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileURI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients_v2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView recipeImage = (ImageView) view.findViewById(R.id.recipe_picture);
        recipeImage.setImageBitmap((Bitmap)getArguments().getParcelable("BMP"));

        final TextView ingredients_list = (TextView) getActivity().findViewById(R.id.ingredients_list);
        // TODO: move this into RecipeAPIsInterface
        if(getArguments().getString("API").equals("Food2Fork")){
            final JSONRequest request = new JSONRequest();
            request.createResponse(Auth.GET_URL, Auth.STRING_KEY, Auth.F2F_Key, "", "",
                    "", "", "", "", "", "", "", "", "", 0.0, 0.0, getArguments().getString("RecipeID"), null, null);
            request.sendResponse(getActivity().getApplicationContext(), new JSONRequest.VolleyCallBack() {
                @Override
                public void onSuccess() {
                    JSONObject response = request.getResponse();
                    try {
                        JSONObject recipe = response.getJSONObject("recipe");
                        JSONArray ingredientsList = recipe.getJSONArray("ingredients");
                        for (int i = 0; i < ingredientsList.length(); i++) {
                            ingredients.append(ingredientsList.getString(i));
                            ingredients.append('\n');
                        }
                        //Putting a setText here because if put outside, it won't show F2F's ingredient list
                        ingredients_list.setText(ingredients.toString());
                        ingredients_list.setMovementMethod(new ScrollingMovementMethod());
                    } catch (JSONException e) {
                        ingredients_list.setText("Sorry, we could not fetch your ingredients due to some unforeseeable error. Please contact us at AmaizingUnicornRecipes@gmail.com");
                    }
                }
                @Override
                public void onFailure(){
                    ingredients_list.setText("Sorry, we could not load your ingredients due to some app error, please contact us at AmaizingUnicornRecipes@gmail.com");
                }
            });
        }else{
            String[] ingredientLines = getArguments().getStringArray("Ingredients");
            for (int i = 0; i < ingredientLines.length; i++) {
                ingredients.append(ingredientLines[i]);
                ingredients.append('\n');
            }
            ingredients_list.setText("Ingredients:\n" + ingredients.toString());
            ingredients_list.setMovementMethod(new ScrollingMovementMethod());
        }

        Button find_missing_button = (Button) getActivity().findViewById(R.id.find_missing_button);
        find_missing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MissingIngredients.class);
                intent.putExtra("Ingredients", ingredients.toString());
                startActivity(intent);
            }
        });

        CheckBox fav = (CheckBox) getActivity().findViewById(R.id.star_button);
        final Favorites favObj = new Favorites(getActivity().getApplicationContext());

        if(favObj.checkIfFavorited(getArguments().getString("RecipeID"), getArguments().getString("API"))) {
            fav.setChecked(true);
        }

        fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String ingredientList = "";
                String nutrientList = "";
                String dailyTotals = "";
                if(getArguments().getString("API").equals("Edamam")){
                    ingredientList = convertArrayToString(getArguments().getStringArray("Ingredients"));
                    nutrientList = convertArrayToString(getArguments().getStringArray("Nutrients"));
                    dailyTotals = convertIntArrayToString(getArguments().getIntArray("Totals"));
                }
                favObj.storeRecipe(getArguments().getString("Title"),
                        getArguments().getString("RecipeID"), getArguments().getString("Picture"),
                        getArguments().getString("SourceUrl"), getArguments().getString("SourceName"),
                        nutrientList, ingredientList, getArguments().getString("API"), dailyTotals);

            }
        });
        Button sharingButton = (Button) getActivity().findViewById(R.id.sharing1);
        sharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        sharingButton.setBackgroundResource(R.drawable.ic_action_share_dark);

    }

    private void selectImage(){
        final CharSequence[] items = {"Share Photo", "Share Recipe", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose how you would like to share your recipe");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Share Photo")) {
                    imageShare();
                } else if (items[which].equals("Share Recipe")) {
                    shareRecipe();
                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void shareRecipe(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recipe");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, getActivity().getIntent().getExtras().getString("Title"));

        startActivity(Intent.createChooser(sharingIntent, "Share recipe via"));
    }

    private void shareImage(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recipe: " + getActivity().getIntent().getExtras().getString("Title"));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, fileURI);

        startActivity(Intent.createChooser(sharingIntent, "Share photo via"));
    }

    private void imageShare(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileURI = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileURI);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Amaizing Unicorn");

        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath()
                    + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath()
                    + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        try{
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                if(resultCode == Activity.RESULT_OK){
                    //picture taken
                    takeAnother();
                } else {
                    Toast.makeText(getContext(), "Error: Result code is not RESULT_OK", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeAnother(){
        try{
            new AlertDialog.Builder(getContext()).setTitle("Amaizing Unicorn")
                    .setMessage("Do you want to take another picture or share this one?")
                    .setPositiveButton("Take Another", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            imageShare();
                        }
                    })
                    .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            shareImage();
                        }
                    }).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param array The String Array to be converted into a string
     * @return A string, either the nutrients or ingredients
     */
    public static String convertArrayToString(String[] array){
        String str = "";
        String strSeparator = ", ";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }

    /**
     *
     * @param array The String Array to be converted into a string
     * @return A string, either the nutrients or ingredients
     */
    public static String convertIntArrayToString(int[] array){
        String str = "";
        String strSeparator = ",";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
}