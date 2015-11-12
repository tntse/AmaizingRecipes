package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;

/**
 * Created by chris on 11/9/15.
 */
public class FavoritesPage {

    String title;
    dbHandler handler;

    public FavoritesPage(Context context) {
        handler = new dbHandler(context, null, null, 1);
    }

    /**
     * Passes recipe title to handler to store in database
     * @param title recipe title
     */
    public void storeRecipe(String title){
        handler.addRecipe(title);
    }

    public dbHandler getHandler() {
        return handler;
    }
}
