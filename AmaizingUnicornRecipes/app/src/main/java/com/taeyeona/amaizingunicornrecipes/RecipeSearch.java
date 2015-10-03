package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

/**
 * Created by Chau on 9/27/2015.
 */
public class RecipeSearch extends AppCompatActivity{
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        final JSONParse prse = new JSONParse(getIntent().getStringExtra("Ingredients"));
        RecipeSearch.context = getApplicationContext();
        prse.sendJSONRequest(RecipeSearch.context);

        Button newButton3 = (Button) findViewById(R.id.button3);
        newButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = (TextView) findViewById(R.id.textView2);
                textView.setText(prse.getTitleList().toString());
            }
        });
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
