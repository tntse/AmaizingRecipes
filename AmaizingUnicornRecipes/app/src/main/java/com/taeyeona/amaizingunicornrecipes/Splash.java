package com.taeyeona.amaizingunicornrecipes;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.taeyeona.amaizingunicornrecipes.Activity.MainActivity;

/**
 * Created by Hao on 9/26/2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //set content view to splash
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final Animation anima = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation anim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        final MediaPlayer kitty = MediaPlayer.create(this, R.raw.kitty);


        iv.startAnimation(anima);
        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation){
                kitty.start();
                iv.startAnimation(anim2);
                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        //pause for 3 seconds
       /* Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();*/

    }//end pause

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
