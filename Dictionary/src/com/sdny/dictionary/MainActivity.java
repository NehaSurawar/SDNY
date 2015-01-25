package com.sdny.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button start = (Button) findViewById(R.id.start);
	    start.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            Intent wordListenerService = new Intent(MainActivity.this, WordListener.class);
	            startService(wordListenerService);
	        }
	    });
	}


    private String url = "https://glosbe.com/gapi/translate?from=pol&dest=eng&format=json&pretty=true&phrase=";
    private HandleJSON obj;

    public String getMeaning(String input){
        String finalUrl = url + input;
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON();

        while(obj.parsingComplete);
        return obj.getTextMeaning();
    }
}

