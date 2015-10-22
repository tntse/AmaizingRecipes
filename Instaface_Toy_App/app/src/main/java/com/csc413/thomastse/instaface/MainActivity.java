package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


/*
 * Created by thomastse on 10/13/15.
 */
public class MainActivity extends Activity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instagramui);

        // grab imageView
        iv = (ImageView) findViewById(R.id.ImageView);

    }

    // function to move to Settings Activity
    public void moveToSettings(View view){
        Intent toSettings = new Intent(MainActivity.this, Settings.class);
        startActivity(toSettings);
    }

    public void takePhoto(View view){

        // Creates an Intent to get a picture.
        /* MediaStore.ACTION_IMAGE_CAPTURE calls the native camera application to take a photo
         *
         * After the result is obtained it is passed to on Activity Result
         */
        Intent callCameraApplicationIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCameraApplicationIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if( requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();

            //display the image back in the application
            Bundle extraData = data.getExtras();
            Bitmap bmp = (Bitmap) extraData.get("data");
            iv.setImageBitmap(bmp);
        }

    }

    public void toAuthorization(View view){
        Intent toAuth = new Intent(MainActivity.this, Authorization.class);
        startActivity(toAuth);
    }

}
