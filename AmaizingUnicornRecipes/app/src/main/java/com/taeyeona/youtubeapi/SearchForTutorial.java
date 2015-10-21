package com.taeyeona.youtubeapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Hao on 10/11/2015.
 */
public class SearchForTutorial extends AppCompatActivity {

    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_tutorial);


        Button newButton3 = (Button) findViewById(R.id.searchButton);
        newButton3.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                //save a format the search input
                searchBar = (EditText)findViewById(R.id.tutorialName);
                String st = searchBar.getText().toString();
                st = st.replaceAll(" ","+");




                //moving onto new activity, passing the formatted string to new actiity
                Intent intent = new Intent(SearchForTutorial.this, Player.class).putExtra("formattedSt", st);
                startActivity(intent);
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
