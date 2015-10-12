package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau on 10/11/2015.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolderRecipes> {

    private LayoutInflater layoutInflater;
    private List<Recipes> list = new ArrayList<Recipes>();
    private VolleySingleton volley;
    private ImageLoader img;

    public RecipeAdapter(Context pContext){
        layoutInflater = LayoutInflater.from(pContext);
        volley = VolleySingleton.getInstance(pContext);
        img = volley.getImageLoader();
    }
    @Override
    public ViewHolderRecipes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_recipe, parent, false);
        ViewHolderRecipes viewRecipes = new ViewHolderRecipes(view);
        return viewRecipes;
    }

    @Override
    public void onBindViewHolder(final ViewHolderRecipes holder, int position) {
        Recipes reci = list.get(position);
        holder.title.setText(reci.getTitle());
        holder.publisher.setText(reci.getSocialRank().toString());
        img.get(reci.getImageUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                holder.thumbnail.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Recipes> l){
        list = l;
        notifyItemChanged(0, l.size());
    }

    static class ViewHolderRecipes extends RecyclerView.ViewHolder{
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
}
