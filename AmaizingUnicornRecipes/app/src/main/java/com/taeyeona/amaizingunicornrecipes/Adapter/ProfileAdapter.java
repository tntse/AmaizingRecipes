package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.taeyeona.amaizingunicornrecipes.Activity.EditSettings;
import com.taeyeona.amaizingunicornrecipes.Auth;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.HashMap;
import java.util.List;

/**
 * An adapter for the Profile Items.
 * @author Thomas Tse
 * @version 1.2
 * @since 2015-11-08
 */
public class ProfileAdapter extends BaseExpandableListAdapter {
    private HashMap<String, List<String>> hashList;
    private List<String> settings;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Bundle bun;
    private LayoutInflater inflater;

    /**
     * The Constructor for this Adapter.
     *
     * @param ctx The Context calling the Adapter.
     * @param hash  A HashList to adapt.
     * @param labels  A key list for the HashMap.
     */
    public ProfileAdapter(Context ctx, HashMap<String, List<String>> hash, List<String> labels, Bundle bundle){
        this.context = ctx;
        this.hashList = hash;
        this.settings = labels;
        sharedPreferences = context.getSharedPreferences(Auth.SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        bun = bundle;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /**
     * Gets number of Setting Categories.
     * @return number of categories.
     */
    @Override
    public int getGroupCount() {
        return settings.size();
    }

    /**
     * Gets number of settings within a category.
     * @param category A specific category.
     * @return number of settings within the given category.
     */
    @Override
    public int getChildrenCount(int category) {
        return hashList.get(settings.get(category)).size();
    }

    /**
     * Gets specified category.
     * @param category The category number in the hashlist.
     * @return The name of the category.
     */
    @Override
    public Object getGroup(int category) {
        return settings.get(category);
    }

    /**
     * Gets a specific child from the hashlist.
     * @param category The category in which to pull from the hashlist.
     * @param setting The position of the child in the hashlist.
     * @return The name of the specific child from the list.
     */
    @Override
    public Object getChild(int category, int setting) {
        return hashList.get(settings.get(category)).get(setting);
    }

    /**
     * Gets an id for the specific group.
     * @param category the specific group.
     * @return the category number.
     */
    @Override
    public long getGroupId(int category) {
        return category;
    }

    /**
     * Gets the id  for the specific child.
     * @param category the specific group
     * @param setting  the specific child from that group
     * @return the setting number from the group.
     */
    @Override
    public long getChildId(int category, int setting) {
        return setting;
    }

    /**
     * Lets the User know that the Ids are static
     * @return true
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Returns the View for the Setting Categories.
     * @param category The position of the category.
     * @param isExpanded true if the category is expanded, otherwise false.
     * @param convertView view to be converted to.
     * @param category_view
     * @return View Object with data.
     */
    @Override
    public View getGroupView(int category, boolean isExpanded, View convertView, ViewGroup category_view) {
        String category_name = (String) getGroup(category);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.profile_category_layout, category_view, false );
        TextView text = (TextView) convertView.findViewById(R.id.profile_category);
        text.setText(category_name);
        text.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    /**
     * Returns a View for the specific search settings.
     * @param category The position of the Group in the List.
     * @param setting The position of the setting in the Group's list.
     * @param last_setting Is this the last setting in the Group;s list.
     * @param setting_view The View for the setting.
     * @param category_view The Group's view.
     * @return A View Object with Data.
     */
    @Override
    public View getChildView(int category, int setting, boolean last_setting,
                             View setting_view, ViewGroup category_view) {

        String setting_name = (String)getChild(category, setting);
        Log.d(ProfileAdapter.class.getSimpleName(), Integer.toString(category));
        Log.d(ProfileAdapter.class.getSimpleName(), Integer.toString(setting)+setting_name);
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String group = (String)getGroup(category);

        if(group.equals("User Info") ){
            setting_view = inflater.inflate(R.layout.profile_editable_text_layout, category_view, false);
            TextView profile_field = (TextView) setting_view.findViewById(R.id.profile_textview);
            profile_field.setText(setting_name + ":");
            EditText editText = (EditText) setting_view.findViewById(R.id.profile_edittext);
            if(sharedPreferences.getString(setting_name, "").equals("")) {
                if (setting_name.equals("Name")) {
                    editText.setHint(context.getString(R.string.default_name));
                } else {
                    editText.setHint(context.getString(R.string.default_email));
                }
            }
            else {
                editText.setText(sharedPreferences.getString(setting_name, ""));
            }
            editText.setContentDescription(setting_name);
            editText.setOnEditorActionListener(new EditTextWatcher());

        }else if(group.equals("Grocery Store Search Radius")){
            setting_view = inflater.inflate(R.layout.profile_editable_text_layout, category_view, false);

            TextView profile_field = (TextView) setting_view.findViewById(R.id.profile_textview);
            profile_field.setText(setting_name + ":");

            EditText editText = (EditText) setting_view.findViewById(R.id.profile_edittext);
            editText.setHint("1.75");
            editText.setContentDescription("Radius");
            editText.setText(sharedPreferences.getString("Radius", ""));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    Log.d("ProfileAdapter", textView.getText().toString());
                    String str = textView.getText().toString().trim();
                    if(str.equals("")){
                        editor.remove("Radius");
                    }else{
                        editor.putString("Radius", str);
                    }
                    editor.commit();
                    return true;
                }
            });


        }else if(group.equals("Picture Upload")){

            setting_view = inflater.inflate(R.layout.profile_upload_picture, category_view, false);
            Button imageUploadButton = (Button) setting_view.findViewById(R.id.button);
            imageUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    ((EditSettings)context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
                }
            });

        }else{
            setting_view = inflater.inflate(R.layout.profile_toggle_button_layout, category_view, false);
            TextView toggleText = (TextView) setting_view.findViewById(R.id.toggle_text_layout);
            ToggleButton toggleButton = (ToggleButton) setting_view.findViewById(R.id.search_toggle_button);
         //   Log.d("profile", "making " + setting_name + "toggle button made");
            toggleText.setText(setting_name + ":");
          //  Log.d("profile", setting_name + "toggle button made");
            toggleButton.setChecked(sharedPreferences.getBoolean("Search" + setting_name, false));
            toggleButton.setContentDescription("Search" + setting_name);

            toggleButton.setOnClickListener(new ToggleClick());
        }



        return setting_view;
    }

    /**
     * Tells the User that there are no sub settings.
     * @param category Specific Category
     * @param setting Specific Setting in the Category
     * @return false.
     */
    @Override
    public boolean isChildSelectable(int category, int setting) {
        return true;
    }

    /**
     * Class to watch Edit text fields in the settings.
     * @author Thomas Tse
     * @since 2015-11-09
     * @version 1.0
     */
    class EditTextWatcher implements TextView.OnEditorActionListener{

        /**
         * Method to handle if the text is changed in the EditText.
         * @param textView The specific TextView which had data edited.
         * @param code A signature integer that describes what kind of event occurred.
         * @param keyEvent What kind of event occurred.
         * @return true
         */
        @Override
        public boolean onEditorAction(TextView textView, int code, KeyEvent keyEvent) {
            editor.putString(textView.getContentDescription().toString(), textView.getText().toString());
            editor.commit();
            Log.d("ProfileAdapter", "Editor edits.");
            return true;
        }
    }

    /**
     * Class to watch ToggleButtons in the settings.
     * @author Thomas Tse
     * @since 2015-11-09
     * @version 1.0
     */
    class ToggleClick implements View.OnClickListener{

        /**
         * Method to handle the Click action.
         * <p>
         *     Pushes the value of the clicked toggle button to SharedPreferences.
         * </p>
         * @param view The specific View which was clicked.
         *
         */
        @Override
        public void onClick(View view) {
            ToggleButton clicked = (ToggleButton)view;
            editor.putBoolean(clicked.getContentDescription().toString(), clicked.isChecked());
            editor.commit();
        }
    }
}


