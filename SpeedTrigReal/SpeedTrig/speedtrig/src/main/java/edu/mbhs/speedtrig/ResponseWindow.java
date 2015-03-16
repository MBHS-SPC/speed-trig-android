package edu.mbhs.speedtrig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import com.example.speedtrig.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ResponseWindow extends Activity /**implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {

    public static double incompetenceDiminisher = 0.0001;

	TextView question;
	TextView responseCopy;
	String questionVal;
    boolean finishCalled = false;
    boolean quizDone = false;

    static boolean newQuizStarted = false;
    static boolean submitCalled = false;

    long quizTimeRemaining;
    TextView timer;
    CountDownTimer quizTimer;

    private GoogleApiClient mGoogleApiClient;


    //Sounds
    MediaPlayer corSound, wroSound;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response_window);
		question = (TextView) findViewById(R.id.question);
		responseCopy = (TextView) findViewById(R.id.response);

        //Sounds
        corSound = MediaPlayer.create(this,R.raw.rose_correct);
        wroSound = MediaPlayer.create(this, R.raw.pham_wrong);

        //quizTimeRemaining = getIntent().getLongExtra(RegularTrig.EXTRA_TIME, Settings.quizDuration);
        quizTimeRemaining = Settings.quizDuration;
        quizTimeRemaining += 100;
        Log.d("time2debug", "sub received"+quizTimeRemaining);

        TextView back_button = (TextView) findViewById(R.id.button12);
        back_button.setVisibility(TextView.INVISIBLE);

        timer = (TextView) findViewById(R.id.timerTextView);

        quizTimer = new CountDownTimer(quizTimeRemaining, 1000) {

            public void onTick(long millisUntilFinished) {

                quizTimeRemaining = millisUntilFinished;

                long formattedMillisUntilFinished = (((millisUntilFinished - (millisUntilFinished % 1000)) / 1000));
                String formattedMillisUntilFinishedString;

                if((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) >= 10)) {
                    formattedMillisUntilFinishedString = String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                    );
                }
                else{
                    formattedMillisUntilFinishedString = String.format("%d:0%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                    );
                }

                if(formattedMillisUntilFinished <= 30)   // 30 seconds left!
                    timer.setTextColor(Color.RED);

                //timer.setText((millisUntilFinished / 60000) + ":" + (millisUntilFinished % 60000));
                timer.setText(formattedMillisUntilFinishedString);

            }

            public void onFinish() {
                timer.setText("Time's Up!");

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // this code will be executed after 5 seconds
                        finish();
                    }
                }, 5000);
            }
        };

        quizTimer.start();

        /**
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != 0)
            GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this), this, 0);

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();
         */

        switch (MainMenu.quizType) {
            case REGULAR:
                questionVal = getIntent().getStringExtra(RegularTrig.EXTRA_QUESTION);
                question.setText("#" + questionVal);
                responseCopy.setText(getIntent().getStringExtra(RegularTrig.EXTRA_RESPONSE));
                break;

            case INVERSE:
                questionVal = getIntent().getStringExtra(InverseTrig.EXTRA_QUESTION);
                question.setText("#" + questionVal);
                responseCopy.setText(getIntent().getStringExtra(InverseTrig.EXTRA_RESPONSE));
                break;

            case CUSTOM:
                questionVal = getIntent().getStringExtra(CustomTrig.EXTRA_QUESTION);
                question.setText("#" + questionVal);
                responseCopy.setText(getIntent().getStringExtra(CustomTrig.EXTRA_RESPONSE));
                break;
        }
	}

    public void onBackPressed(){

            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
    }

    public void removeLast(View v){
        CharSequence text = responseCopy.getText();

        // Don't try to delete what's not there
        if (text.length() == 0)
            return;

        char last = text.charAt(text.length()-1);
        if (last == 'E')    // Basically if the last thing inputted was "DNE"
            responseCopy.setText(text.subSequence(0,text.length()-3));
        else
            responseCopy.setText(text.subSequence(0,text.length()-1));

        switch (MainMenu.quizType) {
            case REGULAR:
                RegularTrig.responses.remove(questionVal);
                RegularTrig.responses.put(questionVal, responseCopy.getText().toString());
                break;

            case INVERSE:
                InverseTrig.responses.remove(questionVal);
                InverseTrig.responses.put(questionVal, responseCopy.getText().toString());
                break;

            case CUSTOM:
                CustomTrig.responses.remove(questionVal);
                CustomTrig.responses.put(questionVal, responseCopy.getText().toString());
                break;
        }
    }

    public void startSubmitDialog(View v){
        //Toast.makeText(this, "Ready!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Set!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Go!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        adb.setView(adbInflater.inflate(R.layout.checkbox_submit_button, null));
        adb.setTitle("Submit?");
        adb.setMessage("Are you sure you want to submit your answers early and receive a quiz score?");
        adb.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                submitCalled = true;
                //Games.Leaderboards.submitScore(mGoogleApiClient, "RegularTrigTimeLeaderboard", quizTimeRemaining);
                //startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        //"RegularTrigTimeLeaderboard"), 0);
                if(mGoogleApiClient.isConnected()){
                    //Games.Achievements.unlock(getApiClient(),
                            //getString(R.string.correct_guess_achievement));
                    Games.Leaderboards.submitScore(mGoogleApiClient,
                            getString(R.string.leaderboard_regular_trig_time),
                            quizTimeRemaining);
                }
                finish();
                return;
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        adb.show();
    }
	
	public void updateEverything(View v){
        if(responseCopy.getText().toString().length() >= 8) {
            Toast.makeText(this, "Character Limit Reached", Toast.LENGTH_SHORT).show();
        }
        else {
            appendButton(v.getId());
            switch (MainMenu.quizType) {
                case REGULAR:
                    RegularTrig.responses.remove(questionVal);
                    RegularTrig.responses.put(questionVal, responseCopy.getText().toString());
                    break;

                case INVERSE:
                    InverseTrig.responses.remove(questionVal);
                    InverseTrig.responses.put(questionVal, responseCopy.getText().toString());
                    break;

                case CUSTOM:
                    CustomTrig.responses.remove(questionVal);
                    CustomTrig.responses.put(questionVal, responseCopy.getText().toString());
                    break;
            }
        }
	}
	
	public void appendButton(int id) {
        String textToAppend = (String) ((Button) findViewById(id)).getText();
        responseCopy.append(textToAppend);
    }

    public void openNextQuestion(View v) {
        String response = responseCopy.getText().toString().trim();
        String correct = getCorrectValue(questionVal.substring(questionVal.indexOf('.') + 2)).trim();
        boolean isCorrect = response.equals(correct);
        String text = "#" + questionVal.substring(0, questionVal.indexOf('.')) + " is incorrect!";
        if (isCorrect) text = "#" + questionVal.substring(0, questionVal.indexOf('.')) + " is correct!";

        //Sounds
        if (isCorrect) {
            corSound.start();
        }else wroSound.start();

        int questionIndex = Integer.parseInt(questionVal.substring(0, questionVal.indexOf('.')))-1;

        TextView next_button = (TextView) findViewById(R.id.button11);
        TextView submit_button = (TextView) findViewById(R.id.button15);
        TextView back_button = (TextView) findViewById(R.id.button12);

        switch(MainMenu.quizType) {
            case REGULAR:
                // if it's the last question, don't let them go further
                if (questionIndex == RegularTrig.questionList.length - 1) {
                    next_button.setVisibility(TextView.INVISIBLE);
                    submit_button.setVisibility(TextView.VISIBLE);
                    back_button.setVisibility(TextView.VISIBLE);
                } else {
                    questionVal = RegularTrig.questionList[questionIndex + 1];
                    question.setText("#" + questionVal);
                    responseCopy.setText(RegularTrig.responses.get(questionVal));
                    back_button.setVisibility(TextView.VISIBLE);
                    if (questionIndex + 1 == RegularTrig.questionList.length - 1) {
                        next_button.setVisibility(TextView.INVISIBLE);
                        submit_button.setVisibility(TextView.VISIBLE);
                    }
                }
                break;

            case INVERSE:
                // if it's the last question, don't let them go further
                if (questionIndex == InverseTrig.questionList.length - 1) {
                    next_button.setVisibility(TextView.INVISIBLE);
                    submit_button.setVisibility(TextView.VISIBLE);
                    back_button.setVisibility(TextView.VISIBLE);
                } else {
                    questionVal = InverseTrig.questionList[questionIndex + 1];
                    question.setText("#" + questionVal);
                    responseCopy.setText(InverseTrig.responses.get(questionVal));
                    back_button.setVisibility(TextView.VISIBLE);
                    if (questionIndex + 1 == InverseTrig.questionList.length - 1) {
                        next_button.setVisibility(TextView.INVISIBLE);
                        submit_button.setVisibility(TextView.VISIBLE);
                    }
                }
                break;

            case CUSTOM:
                // if it's the last question, don't let them go further
                if (questionIndex == CustomTrig.questionList.length - 1) {
                    next_button.setVisibility(TextView.INVISIBLE);
                    submit_button.setVisibility(TextView.VISIBLE);
                    back_button.setVisibility(TextView.VISIBLE);
                } else {
                    questionVal = CustomTrig.questionList[questionIndex + 1];
                    question.setText("#" + questionVal);
                    responseCopy.setText(CustomTrig.responses.get(questionVal));
                    back_button.setVisibility(TextView.VISIBLE);
                    if (questionIndex + 1 == CustomTrig.questionList.length - 1) {
                        next_button.setVisibility(TextView.INVISIBLE);
                        submit_button.setVisibility(TextView.VISIBLE);
                    }
                }
                break;
        }

        if(!response.equals(""))
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void openPreviousQuestion(View v) {
        String response = responseCopy.getText().toString().trim();
        //String correct = getCorrectValue(questionVal.substring(questionVal.indexOf('.') + 2)).trim();
        //boolean isCorrect = response.equals(correct);
        //String text = "Incorrect!";
        //if (isCorrect) text = "Correct!";

        int questionIndex = Integer.parseInt(questionVal.substring(0, questionVal.indexOf('.')))-1;

        TextView next_button = (TextView) findViewById(R.id.button11);
        TextView submit_button = (TextView) findViewById(R.id.button15);
        TextView back_button = (TextView) findViewById(R.id.button12);

        switch(MainMenu.quizType) {
            case REGULAR:
                next_button.setVisibility(TextView.VISIBLE);
                submit_button.setVisibility(TextView.INVISIBLE);

                // if it's the second question, don't let them go further
                if (questionIndex == 0)
                    back_button.setVisibility(TextView.INVISIBLE);
                else {
                    back_button.setVisibility(TextView.VISIBLE);
                    questionVal = RegularTrig.questionList[questionIndex - 1];
                    question.setText("#" + questionVal);
                    responseCopy.setText(RegularTrig.responses.get(questionVal));
                    if (questionIndex - 1 == 0)
                        back_button.setVisibility(TextView.INVISIBLE);
                }
                break;

            case INVERSE:
                next_button.setVisibility(TextView.VISIBLE);
                submit_button.setVisibility(TextView.INVISIBLE);

                // if it's the first question, don't let them go further
                if (questionIndex == 0)
                    back_button.setVisibility(TextView.INVISIBLE);
                else {
                    back_button.setVisibility(TextView.VISIBLE);
                    questionVal = InverseTrig.questionList[questionIndex - 1];
                    question.setText("#" + questionVal);
                    responseCopy.setText(InverseTrig.responses.get(questionVal));
                    if (questionIndex - 1 == 0)
                        back_button.setVisibility(TextView.INVISIBLE);
                }
                break;

            case CUSTOM:
                next_button.setVisibility(TextView.VISIBLE);
                submit_button.setVisibility(TextView.INVISIBLE);

                // if it's the second question, don't let them go further
                if (questionIndex == 0)
                    back_button.setVisibility(TextView.INVISIBLE);
                else {
                    back_button.setVisibility(TextView.VISIBLE);
                    questionVal = CustomTrig.questionList[questionIndex - 1];
                    question.setText("#" + questionVal);
                    responseCopy.setText(CustomTrig.responses.get(questionVal));
                    if (questionIndex - 1 == 0)
                        back_button.setVisibility(TextView.INVISIBLE);
                }
                break;
        }

        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public String flipFraction(String frac){
        String flipped = "";

        if (frac.equals("0"))
            return "DNE";
        else if (frac.equals("DNE"))
            return "0";
        else if (frac.equals("-1") || frac.equals("1"))
            return frac;

        if (frac.contains("/"))
            flipped += frac.substring(frac.indexOf('/')+1);
        if (frac.contains("\u221A")){
            char coolChar = frac.charAt(frac.indexOf("\u221A")+1);
            flipped += "\u221A" + coolChar + "/" + coolChar;
        }

        if (flipped.charAt(0) == flipped.charAt(flipped.length()-1) && flipped.contains("/")){
            // it's either going to be "2 root 3 over 3" or "2 over root 2"
            if (flipped.length() == 5) flipped = "\u221A3";
            else flipped = "\u221A2";
        }

        if (frac.charAt(0) == '-') flipped = "-" + flipped;

        return flipped;
    }

    public String getCorrectValue(String question){
        boolean flip = false;
        double operand;
        double badAnswer = 0;
        String goodAnswer = "";

        if (question.substring(0,3).equals("arc")){
            // I'm an inverse trig problem!!!
            return "0";
        }

        else {
            String operation = question.substring(0,3);
            String strOperand = question.substring(4,question.length()-1);

            // some mathing skills <-- yes that's a word
            //takes the character in second place
            switch (operation.charAt(1)){
                case 'e'://secant
                    operation = "cos";
                    flip = true;
                    break;
                case 's'://cosecant
                    operation = "sin";
                    flip = true;
                    break;
                case 'o'://cotangent
                    if (operation.charAt(2) == 's') break; // because cot and cos have the same second letter
                    operation = "tan";
                    flip = true;
                    break;
            }

            if (!strOperand.contains("\u03C0"))
                operand = 0;
            else if (!strOperand.contains("/")){
                int number;
                if (strOperand.substring(0,strOperand.length()-1).equals("")) number = 1;
                else number = Integer.parseInt(strOperand.substring(0,strOperand.length()-1));
                operand = Math.PI * number;
            }
            else {	// Note: operator contains both a pi and a fraction ('/')
                int numerator;
                if (strOperand.substring(0,strOperand.indexOf('\u03C0')).equals("")) numerator = 1;
                else numerator = Integer.parseInt(strOperand.substring(0,strOperand.indexOf('\u03C0')));
                operand = Math.PI * numerator /
                        Integer.parseInt(strOperand.substring(strOperand.indexOf('/')+1));
            }
            //GET THA FIRST CHAR
            switch (operation.charAt(0)){
                case 's'://sin
                    badAnswer = Math.sin(operand);
                    break;
                case 'c'://cos
                    badAnswer = Math.cos(operand);
                    break;
                case 't'://tan
                    badAnswer = Math.tan(operand);
                    break;
            }
            // I'm sorry, lots of bad code... It's a chiseled unit circle
            // +- 1
            if (Math.abs(badAnswer-1) < incompetenceDiminisher ||
                    Math.abs(badAnswer+1) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "1";
                // +- root 3 over 2
            else if (Math.abs(badAnswer-Math.sqrt(3)/2) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(3)/2) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "\u221A3/2";
                // +- root 2 over 2
            else if (Math.abs(badAnswer-Math.sqrt(2)/2) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(2)/2) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "\u221A2/2";
                // +- 1/2
            else if (Math.abs(badAnswer-0.5) < incompetenceDiminisher ||
                    Math.abs(badAnswer+0.5) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "1/2";
                // 0
            else if (Math.abs(badAnswer) < incompetenceDiminisher)
                goodAnswer = "0";
                // +- root 3 over 3
            else if (Math.abs(badAnswer-Math.sqrt(3)/3) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(3)/3) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "\u221A3/3";
                // +- root 3
            else if (Math.abs(badAnswer-Math.sqrt(3)) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(3)) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "\u221A3";
                // DNE = tan(pi/2)
            else if (badAnswer-10 > 0)
                goodAnswer = "DNE";
            else
                goodAnswer = "Java is incompetent";

            if (flip)
                goodAnswer = flipFraction(goodAnswer);
        }

        return goodAnswer;
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

    public void submitData(View view){
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        quizDone = true;
        finish();
    }

    @Override
    public void finish(){
        if (!finishCalled && !quizDone) {
            // Looper.prepare();
            String response = responseCopy.getText().toString().trim();
            String correct = getCorrectValue(questionVal.substring(questionVal.indexOf('.') + 2)).trim();
            boolean isCorrect = response.equals(correct);
            String text = "Incorrect!";
            if (isCorrect) text = "Correct!";
            //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            quizTimer.cancel();
            startActivity(new Intent(this, FinalWindow.class));
            //Intent i = new Intent();
            //i.putExtra(RegularTrig.EXTRA_TIME, quizTimeRemaining);
            //setResult(RESULT_OK, i);
            //Log.d("time2debug", "sub sent: " + quizTimeRemaining);
        }
        finishCalled = true;
        super.finish();
    }

    /**
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    */
}