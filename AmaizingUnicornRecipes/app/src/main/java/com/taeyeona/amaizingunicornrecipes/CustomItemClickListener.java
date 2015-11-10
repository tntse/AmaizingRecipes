package com.taeyeona.amaizingunicornrecipes;

import android.view.View;

/**
 * Created by Chau on 10/22/2015.
 */

/**
 * An interface to be used for RecyclerView clicking
 *
 * Cited from https://gist.github.com/riyazMuhammad/1c7b1f9fa3065aa5a46f
 */
public interface CustomItemClickListener {
    public void onItemClick(View v, int position);
}
