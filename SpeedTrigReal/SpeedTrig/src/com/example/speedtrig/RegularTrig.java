package com.example.speedtrig;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RegularTrig extends ListActivity {
	
	public static String[] questionList;
	public static final String EXTRA_QUESTION = "edu.mbhs.speedtrig.QUESTION";
    public static final String EXTRA_RESPONSE = "edu.mbhs.speedtrig.RESPONSE";
	public static Hashtable<String,String> responses = new Hashtable<String,String>();
	public Timer trigTimer = new Timer();
	public static boolean entranceButtonClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regular_trig);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		if (entranceButtonClicked)
			questionList = generateList();
		ListView lv = getListView();
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionList));
		lv.setTextFilterEnabled(true);
		if (entranceButtonClicked)
			trigTimer.schedule(new EndTrigTime(this), 60000);	// 3 minutes starting now!
		entranceButtonClicked = false;

        // start on first question

        Intent i = new Intent(this, ResponseWindow.class);
        String question = questionList[0];
        i.putExtra(EXTRA_QUESTION, question);
        startActivity(i);
	}
	
	public String getQuestion(){
		int denominator, numerator, random;
		String operation = "", question;
		
		random = (int)(Math.random()*10);
		if (random == 0){
			// generate random number from 2*rand_denom to 20
			random = (int)(Math.random()*5)+1;
			denominator = random == 5 ? 6 : random;
			numerator = (int)(Math.random()*(21-2*denominator))+2*denominator;
			int num = numerator;
			numerator = simplify(num, denominator)[0];
			denominator = simplify(num, denominator)[1];
		}
		else {
			// generate nice unit circle value
			int hand = (int)(Math.random()*12);
			numerator = simplify(hand, 6)[0];
			denominator = simplify(hand, 6)[1];
		}
		
		random = (int)(Math.random()*6);
		switch(random){
		case 0:
			operation = "sin";
			break;
		case 1:
			operation = "cos";
			break;
		case 2:
			operation = "tan";
			break;
		case 3:
			operation = "csc";
			break;
		case 4:
			operation = "sec";
			break;
		case 5:
			operation = "cot";
			break;
		}
		
		question = operation + "(";
		if (numerator != 1) question += numerator;
		if (numerator != 0){
			question += "\u03C0";
			if (denominator != 1) question += "/" + denominator;
		}
		question += ")";
	 	
		return question;
	}
	
	public int[] simplify (int numer, int denom){
		for (int i = Math.min(numer, denom); i > 1; i--){
			if ((double)numer/i == (int)(numer/i) && (double)denom/i == (int)(denom/i)){
				numer /= i;
				denom /= i;
			}
		}
		return new int[]{numer, denom};
	}
	
	public String[] generateList(){
		String[] questions = new String[12];
		for (int i = 0; i <= 11; i++){
			questions[i] = i+1 + ". " + getQuestion();
		}
		for (String s : questions){
			// The numbers preceding the question prevent bad things from happening
			responses.put(s, " ");
		}
		return questions;
	}
	
	public void onListItemClick (ListView l, View v, int position, long id){
		Intent i = new Intent(this, ResponseWindow.class);
		// pass the question to the response window
		String question = questionList[position];
		i.putExtra(EXTRA_QUESTION, question);
        if (responses.get("question") != null)
            i.putExtra(EXTRA_RESPONSE, responses.get("question"));
        else
            i.putExtra(EXTRA_RESPONSE, "");
		startActivity(i);
	}
	
	public void stopQuiz(){
		Looper.prepare();		// LogCat told me to put this here
		Toast.makeText(this, "You're finished!", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, FinalWindow.class);
		startActivity(i);
		finish();
	}

    public void finish(){
        trigTimer.cancel();
        super.finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regular_trig, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_regular_trig,
					container, false);
			return rootView;
		}
	}

}

class EndTrigTime extends TimerTask {

	RegularTrig activity;
	
	public EndTrigTime(RegularTrig rt){
		activity = rt;		// LogCat told me to put this here
	}
	
	@Override
	public void run() {
		activity.stopQuiz();
	}
	
}