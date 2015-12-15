package com.taeyeona.amaizingunicornrecipes.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.taeyeona.amaizingunicornrecipes.R;
import com.taeyeona.amaizingunicornrecipes.Recipes;
import com.taeyeona.amaizingunicornrecipes.VolleySingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau on 10/11/2015.
 */

/**
 * <h>RecipeAdapter class populates RecyclerView with templates of ViewHolderRecipes</h>
 *
 * Cited from https://www.youtube.com/watch?v=I2eYBtLWGzc and https://developer.android.com/training/material/lists-cards.html
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolderRecipes> {

    private LayoutInflater layoutInflater;
    private List<Recipes> list = new ArrayList<Recipes>();
    private VolleySingleton volley;
    private ImageLoader img;
    private CustomItemClickListener listener;

    public RecipeAdapter(Context pContext){
        layoutInflater = LayoutInflater.from(pContext);
        volley = VolleySingleton.getInstance(pContext);
        img = volley.getImageLoader();
    }

    /**
     * <p>Inflates the entire recycler view layout</p>
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public ViewHolderRecipes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_recipe, parent, false);
        final ViewHolderRecipes viewRecipes = new ViewHolderRecipes(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onItemClick(v, viewRecipes.getAdapterPosition());
            }
        });
        return viewRecipes;
    }

    /**
     * <p>Binds a single object from the list to the template layout</p>
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolderRecipes holder, int position) {
        final Recipes reci = list.get(position);
        holder.title.setText(reci.getTitle());
        holder.publisher.setText(reci.getSocialRank().toString());
        img.get(reci.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                holder.thumbnail.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                holder.thumbnail.setImageResource(R.drawable.amaizing);
            }
        });

    }

    /**
     * <p>Gets the size of the list</p>
     *
     * @return Returns the total number of items in the data set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * <p>Sets the list to populate the recycler view</p>
     *
     * @param l The list of recipes
     */
    public void setList(List<Recipes> l){
        list = l;
        notifyItemChanged(0, l.size());
    }

    /**
     * <p>Sets the custom listener to listen to touches made on the screen</p>
     *
     * @param itemClickListener Listener to allow each item to know it's being clicked on
     */
    public void setListener(CustomItemClickListener itemClickListener){
        listener = itemClickListener;
    }

    /**
     * <h>ViewHolderRecipes class sets up one Recipe of the list for RecyclerView</h>
     */
    public static class ViewHolderRecipes extends RecyclerView.ViewHolder{
        private ImageView thumbnail;
        private TextView title;
        private TextView publisher;

        public ViewHolderRecipes(View itemView){
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.recipeThumbnail);
            title = (TextView) itemView.findViewById(R.id.recipetitle);
            publisher = (TextView) itemView.findViewById(R.id.publisher);
        }
    }

    /**
     * <h>CustomItemClickListener interface is an interface to know when the item is clicked on</h>
     */
    public interface CustomItemClickListener {
        public void onItemClick(View v, int position);
    }
}
