package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thomastse on 11/8/15.
 */
public class ProfileAdapter extends BaseExpandableListAdapter {
    private HashMap<String, List<String>> hashList;
    private List<String> settings;
    private Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ProfileAdapter(Context ctx, HashMap<String, List<String>> hash, List<String> labels){
        this.context = ctx;
        this.hashList = hash;
        this.settings = labels;
        sharedPreferences = context.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public int getGroupCount() {
        return settings.size();
    }

    @Override
    public int getChildrenCount(int category) {
        return hashList.get(settings.get(category)).size();
    }

    @Override
    public Object getGroup(int category) {
        return settings.get(category);
    }

    @Override
    public Object getChild(int category, int setting) {
        return hashList.get(settings.get(category)).get(setting);
    }

    @Override
    public long getGroupId(int category) {
        return category;
    }

    @Override
    public long getChildId(int category, int setting) {
        return setting;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int category, boolean isExpanded, View convertView, ViewGroup category_view) {
        String category_name = (String) getGroup(category);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.profile_category_layout, category_view, false );
        }
        TextView text = (TextView) convertView.findViewById(R.id.profile_category);
        text.setText(category_name);
        text.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    @Override
    public View getChildView(int category, int setting, boolean last_setting,
                             View setting_view, ViewGroup category_view) {

        String setting_name = (String)getChild(category, setting);
        if(setting_view == null){
            LayoutInflater inflator = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch(category){
                case 2:
                    setting_view = inflator.inflate(R.layout.profile_editable_text_layout, category_view, false);
                    break;
                case 1:
                    setting_view = inflator.inflate(R.layout.profile_toggle_button_layout, category_view, false);
                    break;
                case 0:
                    setting_view = inflator.inflate(R.layout.profile_toggle_button_layout, category_view, false);
                    break;
            }
        }

        if(category == 2){
            TextView profile_field = (TextView) setting_view.findViewById(R.id.profile_textview);
            profile_field.setText(setting_name + ":");
            EditText editText = (EditText) setting_view.findViewById(R.id.profile_edittext);
            editText.setText(sharedPreferences.getString(setting_name, "Default"));
            editText.setContentDescription(setting_name);
            editText.setOnEditorActionListener(new EditTextWatcher());

        }else if(category == 1 || category == 0){
            TextView toggleText = (TextView) setting_view.findViewById(R.id.toggle_text_layout);
            ToggleButton toggleButton = (ToggleButton) setting_view.findViewById(R.id.search_toggle_button);

            toggleText.setText(setting_name + ":");

            toggleButton.setChecked(sharedPreferences.getBoolean("Search" + setting_name, false));
            toggleButton.setContentDescription("Search" + setting_name);

            toggleButton.setOnClickListener(new ToggleClick());

        }else{

        }



        return setting_view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class EditTextWatcher implements TextView.OnEditorActionListener, TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText textView = (EditText) editable;
            editor.putString(textView.getContentDescription().toString(), textView.getText().toString());
            editor.commit();
        }

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            editor.putString(textView.getContentDescription().toString(), textView.getText().toString());
            editor.commit();
            return false;
        }
    }
    class ToggleClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            ToggleButton clicked = (ToggleButton)view;
            editor.putBoolean(clicked.getContentDescription().toString(), clicked.isChecked());
            editor.commit();
        }
    }

}


