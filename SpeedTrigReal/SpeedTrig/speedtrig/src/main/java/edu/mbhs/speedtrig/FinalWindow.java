package edu.mbhs.speedtrig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.speedtrig.R;

import edu.mbhs.speedtrig.util.QuestionSolver;

public class FinalWindow extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_window);
		String[] results;
		results = getResults();
		((TextView) findViewById(R.id.entry1)).setText(results[0]);
		((TextView) findViewById(R.id.entry2)).setText(results[1]);
		((TextView) findViewById(R.id.entry3)).setText(results[2]);
		((TextView) findViewById(R.id.entry4)).setText(results[3]);
		((TextView) findViewById(R.id.entry5)).setText(results[4]);
		((TextView) findViewById(R.id.entry6)).setText(results[5]);
		((TextView) findViewById(R.id.entry7)).setText(results[6]);
		((TextView) findViewById(R.id.entry8)).setText(results[7]);
		((TextView) findViewById(R.id.entry9)).setText(results[8]);
		((TextView) findViewById(R.id.entry10)).setText(results[9]);
		((TextView) findViewById(R.id.entry11)).setText(results[10]);
		((TextView) findViewById(R.id.entry12)).setText(results[11]);
	}
	
	public String[] getResults(){
		String[] results = new String[12];

        String[] questions = getIntent().getStringArrayExtra("questions");
        String[] responses = getIntent().getStringArrayExtra("responses");

        for (int i = 0; i < questions.length; i++) {
            String question = questions[i];
            String response = responses[i];
            String correctValue = QuestionSolver.solve(question.substring(question.indexOf('.') + 2));
            if (response == null || response.equals("")) response = "NONE";
            results[i] = formatOutput(question, response, correctValue);
        }

		return results;
	}

    public void onBackPressed(){

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

	public String formatOutput(String q, String r, String c){
		String outputString = q;
		String spacing = mult(" ", (15-q.length())*2);
		outputString += spacing + "\t" + r;
		spacing = mult(" ", 24-r.length()*2);
		outputString += spacing + "\t" + c;
		return outputString;
	}
	
	public String mult(String s, int num){
		String endString = "";
		for (int i = 1; i <= num; i++){
			endString += s;
		}
		return endString;
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    public void finish() {
        super.finish();
    }
}