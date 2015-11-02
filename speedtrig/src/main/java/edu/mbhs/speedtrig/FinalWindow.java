package edu.mbhs.speedtrig;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.mbhs.speedtrig.util.QuestionSolver;

public class FinalWindow extends Activity {

    private MediaPlayer streetFailed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_window);
        streetFailed = MediaPlayer.create(this,R.raw.street_failed);
		String[][] results;
		results = getResults();
		((TextView) findViewById(R.id.entry1)).setText(results[0][0]);
		((TextView) findViewById(R.id.entry2)).setText(results[1][0]);
		((TextView) findViewById(R.id.entry3)).setText(results[2][0]);
		((TextView) findViewById(R.id.entry4)).setText(results[3][0]);
		((TextView) findViewById(R.id.entry5)).setText(results[4][0]);
		((TextView) findViewById(R.id.entry6)).setText(results[5][0]);
		((TextView) findViewById(R.id.entry7)).setText(results[6][0]);
		((TextView) findViewById(R.id.entry8)).setText(results[7][0]);
		((TextView) findViewById(R.id.entry9)).setText(results[8][0]);
		((TextView) findViewById(R.id.entry10)).setText(results[9][0]);
		((TextView) findViewById(R.id.entry11)).setText(results[10][0]);
		((TextView) findViewById(R.id.entry12)).setText(results[11][0]);

        ((TextView) findViewById(R.id.entry1_2)).setText(results[0][1]);
        ((TextView) findViewById(R.id.entry2_2)).setText(results[1][1]);
        ((TextView) findViewById(R.id.entry3_2)).setText(results[2][1]);
        ((TextView) findViewById(R.id.entry4_2)).setText(results[3][1]);
        ((TextView) findViewById(R.id.entry5_2)).setText(results[4][1]);
        ((TextView) findViewById(R.id.entry6_2)).setText(results[5][1]);
        ((TextView) findViewById(R.id.entry7_2)).setText(results[6][1]);
        ((TextView) findViewById(R.id.entry8_2)).setText(results[7][1]);
        ((TextView) findViewById(R.id.entry9_2)).setText(results[8][1]);
        ((TextView) findViewById(R.id.entry10_2)).setText(results[9][1]);
        ((TextView) findViewById(R.id.entry11_2)).setText(results[10][1]);
        ((TextView) findViewById(R.id.entry12_2)).setText(results[11][1]);

        ((TextView) findViewById(R.id.entry1_3)).setText(results[0][2]);
        ((TextView) findViewById(R.id.entry2_3)).setText(results[1][2]);
        ((TextView) findViewById(R.id.entry3_3)).setText(results[2][2]);
        ((TextView) findViewById(R.id.entry4_3)).setText(results[3][2]);
        ((TextView) findViewById(R.id.entry5_3)).setText(results[4][2]);
        ((TextView) findViewById(R.id.entry6_3)).setText(results[5][2]);
        ((TextView) findViewById(R.id.entry7_3)).setText(results[6][2]);
        ((TextView) findViewById(R.id.entry8_3)).setText(results[7][2]);
        ((TextView) findViewById(R.id.entry9_3)).setText(results[8][2]);
        ((TextView) findViewById(R.id.entry10_3)).setText(results[9][2]);
        ((TextView) findViewById(R.id.entry11_3)).setText(results[10][2]);
        ((TextView) findViewById(R.id.entry12_3)).setText(results[11][2]);
        //I swear to god this was XMLs fault not mine.
	}

	public String[][] getResults(){
		String[][] results = new String[12][3];
        int  numRight = 0;
        String[] questions = getIntent().getStringArrayExtra("questions");
        String[] responses = getIntent().getStringArrayExtra("responses");

        for (int i = 0; i < questions.length; i++) {
            String question = questions[i];
            String response = responses[i];
            String correctValue = QuestionSolver.solve(question.substring(question.indexOf('.') + 2));
            if(correctValue.equals(response)) numRight++;
            if (response == null || response.equals("")) response = "NONE";
            results[i] = new String[]{question, response, correctValue};
        }
        if(numRight < results.length/2){
            if(Settings.areBlairTalksSoundsEnabled){
                streetFailed.start();
            }
        }
		return results;
	}

    public void onBackPressed(){

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
	
	public void destroyActivity(View v){
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.final_window, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return id == R.id.action_settings || super.onOptionsItemSelected(item);
	}

    public void finish() {
        super.finish();
    }
}