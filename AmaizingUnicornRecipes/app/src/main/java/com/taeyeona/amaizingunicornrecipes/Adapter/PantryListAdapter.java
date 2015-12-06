package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import com.taeyeona.amaizingunicornrecipes.R;
import java.util.ArrayList;


/**
 * Created by thomastse on 11/27/15.
 */
public class PantryListAdapter extends ArrayAdapter<String> implements View.OnClickListener{
    ArrayList<String> selected;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public PantryListAdapter(Context context, String[] list) {
        super(context,R.layout.favorites, list);
        selected = new ArrayList<String>();
        sharedPref = context.getSharedPreferences("AmaizingPrefs", Context.MODE_PRIVATE);
    }

    public ArrayList<String> getSelected(){
        return selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View theView = inflater.inflate(R.layout.checklist, parent, false);
        CheckBox check = (CheckBox) theView.findViewById(R.id.checkBox);
        check.setText(getItem(position));
        check.setOnClickListener(this);


        return theView;
    }

    @Override
    public void onClick(View view) {
        editor = sharedPref.edit();
        CheckBox check = (CheckBox)view;

        if(check.isChecked()){
            selected.add(check.getText().toString());
            editor.putString("CheckMarkedIngredients", selected.toString());
            editor.commit();
        }else{
            selected.remove(check.getText().toString());
        }
    }
}
