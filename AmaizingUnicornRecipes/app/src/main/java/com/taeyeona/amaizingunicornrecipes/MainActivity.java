package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public  class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //EULA FOR NEW USERS
        new Eula(this).show();

        //set up button sound
        final MediaPlayer kitty = MediaPlayer.create(this,R.raw.kitty);

        //created buttons to reference each activity
        Button pantry = (Button)findViewById(R.id.goToPantry);
        Button profile = (Button)findViewById(R.id.profile);

        //the pantry button listens for onClick and Intent references me to another activity

        //start pantry onClickListner
        pantry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                kitty.start();
                Intent intent = new Intent(MainActivity.this, Pantry.class);
                startActivity(intent);

            }
        });
        //End onclick to pantry

        //start profile onClickListner
        profile.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                kitty.start();
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
