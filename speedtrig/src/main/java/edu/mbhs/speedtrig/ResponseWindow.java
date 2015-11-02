package edu.mbhs.speedtrig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import edu.mbhs.speedtrig.util.QuestionGenerator;
import edu.mbhs.speedtrig.util.QuestionSolver;

public class ResponseWindow extends Activity /**implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {

	TextView question;
	TextView responseCopy;
    int questionIndex;
    boolean finishCalled = false;
    boolean quizDone = false;

    static boolean submitCalled = false;
    static boolean earlyExit = false;

    long quizTimeRemaining;
    TextView timer;
    CountDownTimer quizTimer;

    String[] questions = new String[12];
    String[] responses = new String[12];

    //private GoogleApiClient mGoogleApiClient;


    //Sounds
    MediaPlayer corSound, wroSound; //, speedTrigQuizTheme;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response_window);
		question = (TextView) findViewById(R.id.question);
		responseCopy = (TextView) findViewById(R.id.response);

        //Sounds
        corSound = MediaPlayer.create(this,R.raw.rose_correct);
        wroSound = MediaPlayer.create(this, R.raw.pham_wrong);
        //speedTrigQuizTheme = MediaPlayer.create(this, R.raw.speed_trig_quiz_theme);

        //speedTrigQuizTheme.setLooping(true);
        //speedTrigQuizTheme.start();

        //quizTimeRemaining = getIntent().getLongExtra(RegularTrig.EXTRA_TIME, Settings.quizDuration);
        if (MainMenu.quizType == MainMenu.QuizType.CUSTOM)
            quizTimeRemaining = Settings.quizDuration;
        else
            quizTimeRemaining = (long)3*60*1000;

        quizTimeRemaining += 100;

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
                timer.setText(R.string.times_up);

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

        questions = generateList();

        questionIndex = 0;
        question.setText(String.format("#%s", questions[questionIndex]));
        responseCopy.setText(responses[0]);
    }

    public String[] generateList(){
        String[] questions = new String[12];

        switch (MainMenu.quizType) {
            case REGULAR:
                for (int i = 0; i <= 11; i++) {
                    String question = QuestionGenerator.genRegular();
                    questions[i] = i + 1 + ". " + question;
                }
                break;
            case INVERSE:
                for (int i = 0; i <= 11; i++) {
                    String question = QuestionGenerator.genInverse();
                    questions[i] = i + 1 + ". " + question;
                }
                break;
            case CUSTOM:
                for (int i = 0; i <= 11; i++) {
                    String question = QuestionGenerator.genCustom();
                    while (!Settings.isFunctionActive(question.substring(0, question.indexOf("(")))) {
                        question = QuestionGenerator.genCustom();
                    }
                    questions[i] = i + 1 + ". " + question;
                }
        }

        return questions;
    }

    public void onBackPressed(){
            startExitDialog();
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

        responses[questionIndex] = responseCopy.getText().toString();
    }

    @SuppressLint("InflateParams")
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
                //if(mGoogleApiClient.isConnected()){
                    //Games.Achievements.unlock(getApiClient(),
                            //getString(R.string.correct_guess_achievement));
                    //Games.Leaderboards.submitScore(mGoogleApiClient,
                            //getString(R.string.leaderboard_regular_trig_time),
                            //quizTimeRemaining);
                //}
                finish();
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();
    }

    @SuppressLint("InflateParams")
    public void startExitDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        adb.setView(adbInflater.inflate(R.layout.checkbox_submit_button, null));
        adb.setTitle("Exit?");
        adb.setMessage("Are you sure you want to end your quiz? You will not receive a quiz score.");
        adb.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                earlyExit = true;
                finish();
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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
            responses[questionIndex] = responseCopy.getText().toString();
        }
	}
	
	public void appendButton(int id) {
        String textToAppend = (String) ((Button) findViewById(id)).getText();
        responseCopy.append(textToAppend);
    }

    public void openNextQuestion(View v) {
        String questionVal = questions[questionIndex];
        String response = responseCopy.getText().toString().trim();
        String correct = QuestionSolver.solve(questionVal.substring(questionVal.indexOf('.') + 2)).trim();
        boolean isCorrect = response.equals(correct);
        String text = "#" + (questionIndex + 1) + " is incorrect!";
        if (isCorrect) text = "#" + (questionIndex + 1) + " is correct!";
        //Sounds
        if(Settings.areBlairTalksSoundsEnabled) {
            if (!response.equals("")) {
                if (isCorrect) {
                    corSound.start();
                } else wroSound.start();
            }
        }

        TextView next_button = (TextView) findViewById(R.id.button11);
        TextView submit_button = (TextView) findViewById(R.id.button15);
        TextView back_button = (TextView) findViewById(R.id.button12);

        // if it's the last question, don't let them go further
        if (questionIndex == questions.length - 1) {
            next_button.setVisibility(TextView.INVISIBLE);
            submit_button.setVisibility(TextView.VISIBLE);
            back_button.setVisibility(TextView.VISIBLE);
        } else {
            questionIndex++;
            question.setText(String.format("#%s", questions[questionIndex]));
            responseCopy.setText(responses[questionIndex]);
            back_button.setVisibility(TextView.VISIBLE);
            if (questionIndex == questions.length - 1) {
                next_button.setVisibility(TextView.INVISIBLE);
                submit_button.setVisibility(TextView.VISIBLE);
            }
        }

        if(!response.equals(""))
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void openPreviousQuestion(View v) {
        TextView next_button = (TextView) findViewById(R.id.button11);
        TextView submit_button = (TextView) findViewById(R.id.button15);
        TextView back_button = (TextView) findViewById(R.id.button12);


        next_button.setVisibility(TextView.VISIBLE);
        submit_button.setVisibility(TextView.INVISIBLE);

        // if it's the first question, don't let them go further
        if (questionIndex == 0)
            back_button.setVisibility(TextView.INVISIBLE);
        else {
            back_button.setVisibility(TextView.VISIBLE);
            questionIndex--;
            question.setText(String.format("#%s", questions[questionIndex]));
            responseCopy.setText(responses[questionIndex]);
            if (questionIndex == 0)
                back_button.setVisibility(TextView.INVISIBLE);
        }

        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
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
            quizTimer.cancel();

            if (!earlyExit) {
                Intent i = new Intent(this, FinalWindow.class);
                i.putExtra("questions", questions);
                i.putExtra("responses", responses);
                startActivity(i);
            }
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