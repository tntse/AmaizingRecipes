package com.csc413.thomastse.instaface;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;



/**
 * Created by thomastse on 10/16/15.
 */
public class Authorization extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        webView = (WebView) findViewById(R.id.webView);
        Button back_button = (Button) findViewById(R.id.back_button);

        Uri uri = Uri.parse("https://api.instagram.com/oauth/authorize/?client_id=887c2d2a90e14b338717884773dc019c&redirect_uri=http://instagram.com/&response_type=code");
        //Intent browser = new Intent(Intent.ACTION_VIEW, uri);
        //startActivity(browser);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(uri.toString());

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_code = webView.getUrl();
                Intent prevIntent = new Intent(Authorization.this, InstagramUI.class).putExtra("URL", url_code);
                startActivity(prevIntent);
            }
        });
    }
}
