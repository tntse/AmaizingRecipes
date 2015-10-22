package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.hardware.camera2.*;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by thomastse on 10/13/15.
 */
public class InstagramUI extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // InstagramInterface instaface = new InstagramInterface()
        setContentView(R.layout.display_settings);

        String url = getIntent().getStringExtra("URL");
        TextView tv = (TextView) findViewById(R.id.display_text_view);
        tv.setText(url);

    }


}
