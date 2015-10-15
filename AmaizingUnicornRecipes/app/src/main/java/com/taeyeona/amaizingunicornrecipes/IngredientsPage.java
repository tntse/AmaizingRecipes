package com.taeyeona.amaizingunicornrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Main Activity for database toy app
 *
 * @author Anna Sever
 * @version 1.0
 *
 */
public class MainActivity extends AppCompatActivity {


    EditText input;
    TextView ingredientList;
    dbHandler handler;

    /**
     * Pulls up saved database state onCreate
     * @param savedInstanceState instance of device state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * TODO Need to create EditText field for input to be used. Doing so makes error go away.
         * TODO Need to create TextView for ingredientList. Doing so makes error go away.
         */
        input = (EditText) findViewById(R.id.input);
        ingredientList = (TextView) findViewById(R.id.ingredientList);
        handler = new dbHandler(this, null, null, 1);
        printDatabase();
    }

    /**
     * Passes ingredient to handler to store in database
     * @param view the current view of device
     */
    public void addButtonClicked(View view){
        Ingredients ingredient = new Ingredients(input.getText().toString());
        handler.addIngredient(ingredient);
        printDatabase();
    }

    /**
     * Deletes an ingredient from database
     * @param view current device view
     */
    public void deleteButtonClicked(View view) {
        String inputText = input.getText().toString();
        handler.deleteIngredient(inputText);
        printDatabase();
    }

    /**
     * Uses handle to get and print database
     */
    public void printDatabase(){
        String dbString = handler.databaseToString();
        ingredientList.setText(dbString);
        input.setText("");
    }
}
