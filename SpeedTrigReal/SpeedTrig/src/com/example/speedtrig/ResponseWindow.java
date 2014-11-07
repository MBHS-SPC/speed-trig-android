package com.example.speedtrig;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResponseWindow extends Activity {
	
	TextView question;
	TextView responseCopy;
	String questionVal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response_window);
		question = (TextView) findViewById(R.id.question);
		responseCopy = (TextView) findViewById(R.id.response);
		questionVal = getIntent().getStringExtra(RegularTrig.EXTRA_QUESTION);
		question.setText(questionVal);
        responseCopy.setText(getIntent().getStringExtra(RegularTrig.EXTRA_RESPONSE));
	}
	
	public void updateEverything(View v){
		appendButton(v.getId());
		RegularTrig.responses.remove(questionVal);
		RegularTrig.responses.put(questionVal, responseCopy.getText().toString());
	}
	
	public void appendButton(int id){
		String textToAppend = (String) ((Button) findViewById(id)).getText();
		responseCopy.append(textToAppend);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.response_window, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onPause(){
		super.onPause();
		finish();
	}

    public void submitData(View view){
        finish();
    }
}
