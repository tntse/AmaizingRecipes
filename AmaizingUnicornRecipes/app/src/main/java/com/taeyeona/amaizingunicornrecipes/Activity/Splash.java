package com.taeyeona.amaizingunicornrecipes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.taeyeona.amaizingunicornrecipes.R;

/**
 * Created by Hao on 9/26/2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set content view to activity_splash
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView iv = (ImageView) findViewById(R.id.splashImage);

        final Animation fadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        final Animation rotation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);

        final MediaPlayer kitty = MediaPlayer.create(this, R.raw.kitty);

        /*start animation and override listeners for intent and additional functions
         *
         */

        iv.startAnimation(fadeIn);
        iv.startAnimation(rotation);


        /**
         * Spins the corn on the Splash page
         * @author Benson
         */
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                kitty.start();
//                iv.startAnimation(fadeIn);
                finish();
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

    }//end pause

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
