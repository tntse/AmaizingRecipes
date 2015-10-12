package com.taeyeona.amaizingunicornrecipes;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau on 9/27/2015.
 */
public class RecipeSearch extends AppCompatActivity{

    private RecyclerView listview;
    private List<Recipes> list = new ArrayList<Recipes>();
    private RecipeAdapter recAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        Button but = (Button) findViewById(R.id.button2);

        final JSONParse prse = new JSONParse(getIntent().getStringExtra("Ingredients"), getApplicationContext());
        prse.sendJSONRequest();
        list = prse.getList();

        listview = (RecyclerView) findViewById(R.id.list);
        listview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recAdapt = new RecipeAdapter(getApplicationContext());
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recAdapt.setList(list);
                listview.setAdapter(recAdapt);
            }
        });

        //Figure out a way to have a timer so that I don't need to have a button and wait a few seconds
        //before clicking that button

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
