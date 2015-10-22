package com.taeyeona.amaizingunicornrecipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.chris.edamam.R;
import java.lang.Override;import java.lang.String;

public class EdamamActivity extends AppCompatActivity {

    ProgressBar progress;

    public static Context context;
    private static final String TAG = EdamamActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);

        Button newButton = (Button) findViewById(R.id.button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                /* When User clicks Go! button, hide the keyboard */
                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                // final JSONParse prse = new JSONParse(ET.getText().toString().replace(" ", "%20"));

                //https://api.edamam.com/search?from=0&to=1&q=chicken&app_id=4f2b1b73&app_key=bb6d714aa9393e1e22555b633eee4de4

/*                private void createResponse(String baseUrl, String strKey, String strAppId,
                                        String from, String to, String appId, String appKey, String endUrl,
                                        String fromNum, String toNum, String part, String maxResult){*/


                    String[] nutritionalArray = nutritionList;
                    for(
                    int i = 0; i < nutritionList.length;i++)

                    {
                        if (nutritionalArray[i] != null) {
                            output.append(nutritionalArray[i]);
                            output.append("\n");
                        }
                    }

                    textView.setText(output.toString());
                }

            }

            ,2000);


        }
    }

    );

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
