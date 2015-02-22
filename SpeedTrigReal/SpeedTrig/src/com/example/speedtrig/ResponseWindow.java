package com.example.speedtrig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ResponseWindow extends Activity {

    public static double incompetenceDiminisher = 0.0001;

	TextView question;
	TextView responseCopy;
	String questionVal;
    boolean finishCalled = false;
    boolean quizDone = false;

    static boolean newQuizStarted = false;

    static long quizDuration = 180000;
    long quizTimeRemaining;
    TextView timer;
    CountDownTimer quizTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response_window);
		question = (TextView) findViewById(R.id.question);
		responseCopy = (TextView) findViewById(R.id.response);

        quizTimeRemaining = getIntent().getLongExtra(RegularTrig.EXTRA_TIME, Settings.quizDuration);
        Log.d("time2debug", "sub received"+quizTimeRemaining);

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
                timer.setText("Quiz Over");

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

        if(MainMenu.isRegularTrig) {
            questionVal = getIntent().getStringExtra(RegularTrig.EXTRA_QUESTION);
            question.setText(questionVal);
            responseCopy.setText(getIntent().getStringExtra(RegularTrig.EXTRA_RESPONSE));
        }
        else{
            questionVal = getIntent().getStringExtra(InverseTrig.EXTRA_QUESTION);
            question.setText(questionVal);
            responseCopy.setText(getIntent().getStringExtra(InverseTrig.EXTRA_RESPONSE));
        }
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
    }
	
	public void updateEverything(View v){
		appendButton(v.getId());
        if(MainMenu.isRegularTrig) {
            RegularTrig.responses.remove(questionVal);
            RegularTrig.responses.put(questionVal, responseCopy.getText().toString());
        }
        else{
            InverseTrig.responses.remove(questionVal);
            InverseTrig.responses.put(questionVal, responseCopy.getText().toString());
        }
	}
	
	public void appendButton(int id) {
        String textToAppend = (String) ((Button) findViewById(id)).getText();
        responseCopy.append(textToAppend);
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
            String response = responseCopy.getText().toString().trim();
            String correct = getCorrectValue(questionVal.substring(questionVal.indexOf('.') + 2)).trim();
            boolean isCorrect = response.equals(correct);
            String text = "incorrect";
            if (isCorrect) text = "correct";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            quizTimer.cancel();
            Intent i = new Intent();
            i.putExtra(RegularTrig.EXTRA_TIME, quizTimeRemaining);
            setResult(RESULT_OK, i);
            Log.d("time2debug", "sub sent: "+quizTimeRemaining);
        }
        finishCalled = true;
        super.finish();
    }

}