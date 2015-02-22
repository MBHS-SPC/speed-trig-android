package com.example.speedtrig;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Hashtable;
import java.util.Random;

/**
 * Inverse trig. Added 2/15/15 by AliAnwar7477.
 */

public class InverseTrig extends ListActivity {

    public static String[] questionList;
    public static final String EXTRA_QUESTION = "edu.mbhs.speedtrig.QUESTION";
    public static final String EXTRA_RESPONSE = "edu.mbhs.speedtrig.RESPONSE";
    public static final String EXTRA_TIME = "edu.mbhs.speedtrig.TIME";
    public static final int TIME_REQUEST = 1;
    public static Hashtable<String,String> responses = new Hashtable<String,String>();
    public long millisRemaining;
    public CountDownTimer trigTimer;
    public static boolean entranceButtonClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

        Log.d("InverseTrig", "onCreate finished");
	}

    public String getQuestion(){

        Random generator = new Random();

        boolean getRegularTrigQuestion = generator.nextBoolean();

        if(getRegularTrigQuestion){
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

            if((operation.equals("arcsin")) || (operation.equals("arccos"))) {
                random = generator.nextInt(5);
                switch (random) {
                    case 0:
                        question = "0";
                        break;
                    case 1:
                        if (generator.nextBoolean())
                            question = "1";
                        else
                            question = "-1";
                        break;
                    case 2:
                        if (generator.nextBoolean())
                            question = "1/2";
                        else
                            question = "-1/2";
                        break;
                    case 3:
                        if (generator.nextBoolean())
                            question = "√2/2";
                        else
                            question = "-√2/2";
                        break;
                    case 4:
                        if (generator.nextBoolean())
                            question = "√3/2";
                        else
                            question = "-√3/2";
                        break;
                }
            }

            if((operation.equals("arccsc")) || (operation.equals("arcsec"))){
                random = generator.nextInt(4);
                switch (random) {
                    case 0:
                        if (generator.nextBoolean())
                            question = "1";
                        else
                            question = "-1";
                        break;
                    case 1:
                        if (generator.nextBoolean())
                            question = "2";
                        else
                            question = "-2";
                        break;
                    case 2:
                        if (generator.nextBoolean())
                            question = "√2";
                        else
                            question = "-√2";
                        break;
                    case 3:
                        if (generator.nextBoolean())
                            question = "(2√3)/3";
                        else
                            question = "-(2√3)/3";
                        break;
                }
            }

            if((operation.equals("arctan")) || (operation.equals("arccot"))){
                random = generator.nextInt(4);
                switch (random) {
                    case 0:
                        question = "0";
                        break;
                    case 1:
                        if (generator.nextBoolean())
                            question = "1";
                        else
                            question = "-1";
                        break;
                    case 2:
                        if (generator.nextBoolean())
                            question = "√3";
                        else
                            question = "-√3";
                        break;
                    case 3:
                        if (generator.nextBoolean())
                            question = "√3/3";
                        else
                            question = "-√3/3";
                        break;
                }
            }

            question = operation + "(" + question + ")";

            return question;
        }
    }

    public String[] generateList(){
        String[] questions = new String[12];
        Log.d("generateList", "I totes initialized an array " + questions);
        for (int i = 0; i <= 11; i++){
            String question = getQuestion();
            while(!Settings.isFunctionActive(question.substring(0, question.indexOf("(")))){
                question = getQuestion();
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

        Log.d("time2debug", "main sent: " + millisRemaining);
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
        //Looper.prepare();
        //Toast.makeText(this, "You're finished!", Toast.LENGTH_LONG).show();
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