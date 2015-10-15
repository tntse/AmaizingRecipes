package com.taeyeona.amaizingunicornrecipes;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau on 9/27/2015.
 */
public class RecipeSearch extends AppCompatActivity{

    private RecyclerView listview;
    private List<Recipes> list = new ArrayList<Recipes>();
    private RecipeAdapter recAdapt;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        progress = (ProgressBar) findViewById(R.id.progressBar);

        final JSONParse prse = new JSONParse(getIntent().getStringExtra("Ingredients"));
        prse.sendJSONRequest();
        list = prse.getList();

        listview = (RecyclerView) findViewById(R.id.list);
        listview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recAdapt = new RecipeAdapter(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                progress.setVisibility(View.INVISIBLE);
                recAdapt.setList(list);
                listview.setAdapter(recAdapt);
            }

        }, 2000);

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
