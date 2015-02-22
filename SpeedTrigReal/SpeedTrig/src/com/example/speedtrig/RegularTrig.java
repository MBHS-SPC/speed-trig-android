package com.example.speedtrig;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.TimerTask;

public class RegularTrig extends ListActivity {
	
	public static String[] questionList;
	public static final String EXTRA_QUESTION = "edu.mbhs.speedtrig.QUESTION";
    public static final String EXTRA_RESPONSE = "edu.mbhs.speedtrig.RESPONSE";
    public static final String EXTRA_TIME = "edu.mbhs.speedtrig.TIME";
    public static final int TIME_REQUEST = 1;
	public static Hashtable<String,String> responses = new Hashtable<String,String>();
    public long millisRemaining;
	public CountDownTimer trigTimer;
	public static boolean entranceButtonClicked;
    public boolean responseWindowOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regular_trig);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

        Log.d("regularTrig", "onCreate started");

        if (entranceButtonClicked)
			questionList = generateList();

        ListView lv = getListView();
        Log.d("msg", this+"");
        Log.d("msg", android.R.layout.simple_list_item_1+"");
        Log.d("msg", questionList+"");
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionList));
		lv.setTextFilterEnabled(true);

		if (entranceButtonClicked) {
            trigTimer = new CountDownTimer(Settings.quizDuration, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    millisRemaining = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    stopQuiz();
                }
            };
            trigTimer.start();
            millisRemaining = Settings.quizDuration;
            ResponseWindow.newQuizStarted = true;
        }
		entranceButtonClicked = false;

        // start on first question
        Intent i = new Intent(this, ResponseWindow.class);
        String question = questionList[0];
        i.putExtra(EXTRA_QUESTION, question);
        i.putExtra(EXTRA_TIME, millisRemaining);
        trigTimer.cancel();
        startActivityForResult(i, TIME_REQUEST);

        Log.d("RegularTrig", "onCreate finished");
	}
	
	public String getQuestion(){
		int denominator, numerator, random;
		String operation = "", question;
		
		random = (int)(Math.random()*10);
		if (random == 0) {
			// generate random number from 2*rand_denom to 20
			random = (int)(Math.random() * 5) + 1;
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
		switch(random) {
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
			if ((double)numer/i == (numer/i) && (double)denom/i == (denom/i)){
				numer /= i;
				denom /= i;
			}
		}
		return new int[]{numer, denom};
	}
	
	public String[] generateList(){
		String[] questions = new String[12];
        Log.d("generateList", "I totes initialized an array "+questions);
		for (int i = 0; i <= 11; i++){
            String question = getQuestion();
            while(!Settings.isFunctionActive(question.substring(0, question.indexOf("(")))){
                question = getQuestion();
                Log.d("infinite", "loop");
            }

			questions[i] = i+1 + ". " + question;
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

        Log.d("time2debug", "main sent: "+millisRemaining);
        i.putExtra("millis remaining", millisRemaining);
		i.putExtra(EXTRA_QUESTION, question);
        if (responses.get(question) != null)
            i.putExtra(EXTRA_RESPONSE, responses.get(question));
        else
            i.putExtra(EXTRA_RESPONSE, "");
        i.putExtra(EXTRA_TIME, millisRemaining);
        trigTimer.cancel();
		startActivityForResult(i, TIME_REQUEST);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIME_REQUEST && resultCode == RESULT_OK) {
            // it doesn't matter how the activity was ended
            millisRemaining = data.getLongExtra(EXTRA_TIME, millisRemaining);
            Log.d("time2debug", "main received: "+millisRemaining);
            trigTimer = new CountDownTimer(millisRemaining, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    millisRemaining = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    stopQuiz();
                }
            };

            // start up the timer again
            trigTimer.start();
        }
    }

	public void stopQuiz(){
        Looper.prepareMainLooper();
		Toast.makeText(this, "You're finished!", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, FinalWindow.class);
		startActivity(i);
		finish();
	}

    @Override
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

class EndTrigTimeRegular extends TimerTask {

	RegularTrig activity;

	public EndTrigTimeRegular(RegularTrig rt){
		activity = rt;		// LogCat told me to put this here
	}

	@Override
	public void run() {

	}

}