package com.example.speedtrig;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Timer;
import java.util.TimerTask;

import java.util.Random;

/**
 * Inverse trig. Added 2/15/15 by AliAnwar7477.
 */

public class InverseTrig extends ListActivity {

    public static String[] questionList;
    public static final String EXTRA_QUESTION = "edu.mbhs.speedtrig.QUESTION";
    public static final String EXTRA_RESPONSE = "edu.mbhs.speedtrig.RESPONSE";
    public static Hashtable<String,String> responses = new Hashtable<String,String>();
    public Timer trigTimer = new Timer();
    public static boolean entranceButtonClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        System.out.println("CODE WORKED");
		setContentView(R.layout.activity_inverse_trig);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
        Log.d("inverseTrig", "onCreate started");
        if (entranceButtonClicked)
            questionList = generateList();
        ListView lv = getListView();
        Log.d("msg", this+"");
        Log.d("msg", android.R.layout.simple_list_item_1+"");
        Log.d("msg", questionList+"");
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionList));
        lv.setTextFilterEnabled(true);
        if (entranceButtonClicked)
            trigTimer.schedule(new EndTrigTimeInverse(this), 20000);	// 3 minutes starting now!
        entranceButtonClicked = false;

        // start on first question

        Intent i = new Intent(this, ResponseWindow.class);
        String question = questionList[0];
        i.putExtra(EXTRA_QUESTION, question);
        startActivity(i);
        Log.d("InverseTrig", "onCreate finished");
	}

    public String getQuestion(){

        Random generator = new Random();

        boolean getRegularQuestion = generator.nextBoolean();

        System.out.println("CODE WORKED");

        if(getRegularQuestion){
            RegularTrig rt = new RegularTrig();
            return rt.getQuestion();
        }

        else{

            int random;
            String operation = "", question = "";

            random = generator.nextInt(6);
            switch(random) {
                case 0:
                    operation = "arcsin";
                    break;
                case 1:
                    operation = "arccos";
                    break;
                case 2:
                    operation = "arctan";
                    break;
                case 3:
                    operation = "arccsc";
                    break;
                case 4:
                    operation = "arcsec";
                    break;
                case 5:
                    operation = "arccot";
                    break;
            }

            random = generator.nextInt(5);
            switch(random) {
                case 0:
                    question = "0";
                    break;
                case 1:
                    question = "1";
                    break;
                case 2:
                    if(generator.nextBoolean())
                        question = "1/2";
                    else
                        question = "-1/2";
                    break;
                case 3:
                    if(generator.nextBoolean())
                        question = "√2/2";
                    else
                        question = "-√2/2";
                    break;
                case 4:
                    if(generator.nextBoolean())
                        question = "√3/2";
                    else
                        question = "-√3/2";
                    break;
            }

            question = operation + "(" + question + ")";

            return question;
        }
    }

    public String[] generateList(){
        String[] questions = new String[12];
        Log.d("generateList", "I totes initialized an array " + questions);
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
        if (responses.get(question) != null)
            i.putExtra(EXTRA_RESPONSE, responses.get(question));
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

    @Override
    public void finish(){
        trigTimer.cancel();
        super.finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inverse_trig, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_inverse_trig,
					container, false);
			return rootView;
		}
	}

}

class EndTrigTimeInverse extends TimerTask {

    InverseTrig activity;

    public EndTrigTimeInverse(InverseTrig it){
        activity = it;		// LogCat told me to put this here
    }

    @Override
    public void run() {
        activity.stopQuiz();
    }

}