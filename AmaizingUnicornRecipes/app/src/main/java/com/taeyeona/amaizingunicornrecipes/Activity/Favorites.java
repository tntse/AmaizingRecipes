package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.taeyeona.amaizingunicornrecipes.FavoritesPage;
import com.taeyeona.amaizingunicornrecipes.R;


/**
 * Database intermediary for favorites page
 *
 * @author Anna Sever
 * @version 1.0
 *
 */
public class Favorites extends Activity implements AdapterView.OnItemClickListener {

    TextView favoritesList;
    EditText deleteInput;
    FavoritesPage fav;

    private DrawerLayout drawerLayout;
    private ListView navListView;
    private String[] navListName;


    /**
     * Pulls up saved database state onCreate
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        /**
         * Saved variables for drawerListView and drawerListNames,
         * navListName are array items in strings.xml
         * navListView is the list to be adapter for the listnme to be viewable
         * in simple list item format
         */
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        navListName = getResources().getStringArray(R.array.drawer_list);

        navListView = (ListView)findViewById((R.id.nav_drawer));
        navListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navListName));
        navListView.setOnItemClickListener(this);

        fav = new FavoritesPage(getApplicationContext());
        deleteInput = (EditText) findViewById(R.id.deleteField);
        favoritesList = (TextView) findViewById(R.id.favoritesList);
        printDatabase();


        favoritesList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favoritesList.setTextColor(Color.CYAN);
//                Intent intent = new Intent(Favorites.this, RecipeSearch.class);
//                startActivity(intent);
            }
        });
    }

    /**
     * Uses handle to get and print database
     */
    public void printDatabase(){
        String dbString = fav.getHandler().databaseToString();
        favoritesList.setText(dbString);
        deleteInput.setText("");
    }

    /**
     * Deletes an row from database
     *
     * Currently not implemented as there is no delete button yet
     * @param view current device view
     */
    public void deleteButtonClicked(View view) {

        String deleteText = deleteInput.getText().toString();
        fav.getHandler().deleteRecipe(deleteText);
        printDatabase();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        <item>Main Menu</item> 0
//        <item>Ingredients Page</item>> 1
//        <item>Search Recipe</item> 2
//        <item>Edit Profile</item> 3
//        <item>Favorites Page</item> 4

        if(position==0){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        }else if(position==1){
            Intent intent = new Intent(this,EditIngredients.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        }else if(position==2) {
            Intent intent = new Intent(this, Pantry.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);


        }else if(position == 3){
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        }else if(position==4){
            Intent intent = new Intent(this,Favorites.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
        }

    }

}
