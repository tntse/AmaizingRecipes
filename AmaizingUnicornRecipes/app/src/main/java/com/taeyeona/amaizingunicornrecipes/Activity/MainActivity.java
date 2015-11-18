package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.taeyeona.amaizingunicornrecipes.Adapter.CustomPagerAdapter;
import com.taeyeona.amaizingunicornrecipes.Adapter.MainAdapter;
import com.taeyeona.amaizingunicornrecipes.Eula;
import com.taeyeona.amaizingunicornrecipes.R;

import java.util.ArrayList;


public  class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    //variables for drawer list view and elements of listview items
    private DrawerLayout drawerLayout;
    private ListView navListView;
    private String[] navListName;

//    //test
//    private MyAdapter myAdapter;

    private MainAdapter theMainAdapter;
    private ViewPager   theViewPager;

    private ActivePage active;
    private ArrayList<Button> buttons;
    private ArrayList<View> buttonHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        buttons = new ArrayList<Button>();
        buttons.add((Button) findViewById(R.id.main_profile_button));
        buttons.add((Button) findViewById(R.id.main_pantry_button));
        buttons.add((Button)findViewById(R.id.main_search_button));

        buttonHighlights = new ArrayList<View>();
        buttonHighlights.add(findViewById(R.id.main_profile_bar));
        buttonHighlights.add(findViewById(R.id.main_pantry_bar));
        buttonHighlights.add(findViewById(R.id.main_search_bar));

        active = new ActivePage(buttons.get(0), buttonHighlights.get(0));


        for(int i = 0; i < 3; i ++){
            buttons.get(i).setOnClickListener(this);
        }

        theMainAdapter = new MainAdapter(getSupportFragmentManager(), savedInstanceState);
        theViewPager = (ViewPager) findViewById(R.id.main_pages);
        theViewPager.setAdapter(theMainAdapter);

        theViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                 @Override
                 public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                 }

                 @Override
                 public void onPageSelected(int position) {
                     active.getButton().setTypeface(null, Typeface.NORMAL);
                     active.getView().setVisibility(View.INVISIBLE);

                     active.setButton(buttons.get(position));
                     active.setView(buttonHighlights.get(position));
                 }

                 @Override
                 public void onPageScrollStateChanged(int state) {

                 }
        });



//        //test
//        myAdapter = new MyAdapter(this);
//        navListView.setAdapter(myAdapter);


        /**
         * Saved variables for drawerListView and drawerListNames,
         * navListName are array items in strings.xml
         * navListView is the list to be adapter for the listnme to be viewable
         * in simple list item format
         *
         drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
         navListName = getResources().getStringArray(R.array.drawer_list);

         navListView = (ListView)findViewById((R.id.nav_drawer));
         navListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,navListName));
         navListView.setOnItemClickListener(this);





         //EULA FOR NEW USERS
         new Eula(this).show();

         //set up button sound
         //final MediaPlayer kitty = MediaPlayer.create(this,R.raw.kitty);

         //created buttons to reference each activity
         Button pantry = (Button)findViewById(R.id.goToPantry);
         Button profile = (Button)findViewById(R.id.profile);
         Button toEditIngredients = (Button) findViewById(R.id.to_edit_ingredients_button);

         //the pantry button listens for onClick and Intent references me to another activity

         //start pantry onClickListner
         pantry.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View view) {
        //kitty.start();
        Intent intent = new Intent(MainActivity.this, Pantry.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

        }
        });
         //End onclick to pantry

         //start profile onClickListner
         profile.setOnClickListener(new View.OnClickListener()
         {
         @Override public void onClick(View view) {
         //kitty.start();
         Intent intent = new Intent(MainActivity.this, Profile.class);
         startActivity(intent);
         overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
         }
         });

         //Edit Ingredients
         toEditIngredients.setOnClickListener(new View.OnClickListener(){
         public void onClick(View view){
         //kitty.start();
         Intent intent = new Intent(MainActivity.this, EditIngredients.class);
         startActivity(intent);
         overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

         }
         });*/
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

    /**
     * @param parent
     * @param view
     * @param position
     * @param id       OnItemClick added for Drawer list View
     *                 goes to different activities in app
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        <item>Main Menu</item> 0
//        <item>Ingredients Page</item>> 1
//        <item>Search Recipe</item> 2
//        <item>Edit Profile</item> 3
//        <item>Favorites Page</item> 4

        if (position == 0) {
        } else if (position == 1) {
            Intent intent = new Intent(this, EditIngredients.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        } else if (position == 2) {
            Intent intent = new Intent(this, Pantry.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);


        } else if (position == 3) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);


        } else if (position == 4) {
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
        }

    }

    @Override
    public void onClick(View view) {
        int index = buttons.indexOf((Button)view);
        active.getButton().setTypeface(null, Typeface.NORMAL);
        active.getView().setVisibility(View.INVISIBLE);

        active.setButton(buttons.get(index));
        active.setView(buttonHighlights.get(index));
        theViewPager.setCurrentItem(index);
    }

    class ActivePage {

        private Button button;
        private View view;

        public ActivePage(Button button, View view){
            setButton(button);
            setView(view);
        }

        public Button getButton() {
            return button;
        }

        public View getView() {
            return view;
        }

        public void setButton(Button button) {
            this.button = button;
            button.setTypeface(null, Typeface.BOLD);
        }

        public void setView(View view) {
            this.view = view;
            view.setVisibility(View.VISIBLE);
        }

    }
}
