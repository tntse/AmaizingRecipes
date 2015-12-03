package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;



public class FavoritesAdapter extends BaseAdapter {
    Activity context;
    String titles[];


    public FavoritesAdapter(Context context, String[] list) {
        super();
        Log.d("favoritesAdapter", "made it to adapter");

        this.context = (Activity) context;
        this.titles = list;
    }

    private class ViewHolder {
        TextView txtViewTitle;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        Log.d("favoritesAdapter", "getting view pre if statement");
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.favorites, null);
            holder = new ViewHolder();
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.favoritesList);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewTitle.setText(titles[position]);
//        holder.txtViewTitle.setOnClickListener(this);

        Log.d("favoritesAdapter", "successfully returned view");
        return convertView;

    }

//    @Override
//    public void onClick(View view) {
//        CheckBox check = (CheckBox)view;
//        if(check.isChecked()){
//      //      selected.add(check.getText().toString());
//        }else{
//      //      selected.remove(check.getText().toString());
//        }
//    }
}