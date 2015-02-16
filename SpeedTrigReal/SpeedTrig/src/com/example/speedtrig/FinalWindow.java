package com.example.speedtrig;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class FinalWindow extends Activity {
	
	public static double incompetenceDiminisher = 0.0001;
	
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

        if(MainMenu.isRegularTrig) {
            for (int i = 0; i < RegularTrig.questionList.length; i++) {
                String question = RegularTrig.questionList[i];
                String response = RegularTrig.responses.get(question);
                String correctValue = getCorrectValue(question.substring(question.indexOf('.') + 2));
                if (response.equals(" ")) response = "NONE";
                results[i] = formatOutput(question, response, correctValue);
            }
        }

        else{
            for (int i = 0; i < InverseTrig.questionList.length; i++) {
                String question = InverseTrig.questionList[i];
                String response = InverseTrig.responses.get(question);
                String correctValue = getCorrectValue(question.substring(question.indexOf('.') + 2));
                if (response.equals(" ")) response = "NONE";
                results[i] = formatOutput(question, response, correctValue);
            }
        }
		return results;
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
			// it's either going to be "3 root 3 over 3" or "2 over root 2"
			if (flipped.length() == 5) flipped = "\u221A3";
			else flipped = "\u221A2";
		}

		if (frac.charAt(0) == '-') flipped = "-" + flipped;

		return flipped;
	}

    public String getCorrectValue(String question){

        if (question.substring(0,3).equals("arc")){
            // I'm an inverse trig problem!!!

            String operation = question.substring(0, 6);
            String operand = question.substring(7, question.length() - 1);

            String correctAnswer = "";

            if((operation.equals("arcsin"))) {

                if(operand.equals("0"))
                    correctAnswer = "0";

                if(operand.equals("1"))
                    correctAnswer = "π/2";

                if(operand.equals("-1"))
                    correctAnswer = "-π/2";

                if(operand.equals("1/2"))
                    correctAnswer = "π/6";

                if(operand.equals("-1/2"))
                    correctAnswer = "-π/6";

                if(operand.equals("√2/2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2/2"))
                    correctAnswer = "-π/4";

                if(operand.equals("√3/2"))
                    correctAnswer = "π/3";

                if(operand.equals("-√3/2"))
                    correctAnswer = "-π/3";
            }

            if((operation.equals("arccos"))) {

                if(operand.equals("0"))
                    correctAnswer = "π/2";

                if(operand.equals("1"))
                    correctAnswer = "0";

                if(operand.equals("-1"))
                    correctAnswer = "π";

                if(operand.equals("1/2"))
                    correctAnswer = "π/3";

                if(operand.equals("-1/2"))
                    correctAnswer = "2π/3";

                if(operand.equals("√2/2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2/2"))
                    correctAnswer = "3π/4";

                if(operand.equals("√3/2"))
                    correctAnswer = "π/6";

                if(operand.equals("-√3/2"))
                    correctAnswer = "5π/6";
            }

            if((operation.equals("arccsc"))) {

                if(operand.equals("1"))
                    correctAnswer = "π/2";

                if(operand.equals("-1"))
                    correctAnswer = "-π/2";

                if(operand.equals("2"))
                    correctAnswer = "π/6";

                if(operand.equals("-2"))
                    correctAnswer = "-π/6";

                if(operand.equals("√2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2"))
                    correctAnswer = "-π/4";

                if(operand.equals("(2√3)/3"))
                    correctAnswer = "π/3";

                if(operand.equals("-(2√3)/3"))
                    correctAnswer = "-π/3";
            }

            if((operation.equals("arcsec"))) {

                if(operand.equals("1"))
                    correctAnswer = "0";

                if(operand.equals("-1"))
                    correctAnswer = "π";

                if(operand.equals("2"))
                    correctAnswer = "π/3";

                if(operand.equals("-2"))
                    correctAnswer = "2π/3";

                if(operand.equals("√2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2"))
                    correctAnswer = "3π/4";

                if(operand.equals("(2√3)/3"))
                    correctAnswer = "π/6";

                if(operand.equals("-(2√3)/3"))
                    correctAnswer = "5π/6";
            }

            if((operation.equals("arctan"))) {

                if(operand.equals("0"))
                    correctAnswer = "0";

                if(operand.equals("1"))
                    correctAnswer = "π/4";

                if(operand.equals("-1"))
                    correctAnswer = "-π/4";

                if(operand.equals("√3"))
                    correctAnswer = "π/3";

                if(operand.equals("-√3"))
                    correctAnswer = "-π/3";

                if(operand.equals("√3/3"))
                    correctAnswer = "π/6";

                if(operand.equals("-√3/3"))
                    correctAnswer = "-π/6";
            }

            if((operation.equals("arccot"))) {

                if(operand.equals("0"))
                    correctAnswer = "π/2";

                if(operand.equals("1"))
                    correctAnswer = "π/4";

                if(operand.equals("-1"))
                    correctAnswer = "3π/4";

                if(operand.equals("√3"))
                    correctAnswer = "π/6";

                if(operand.equals("-√3"))
                    correctAnswer = "5π/6";

                if(operand.equals("√3/3"))
                    correctAnswer = "π/3";

                if(operand.equals("-√3/3"))
                    correctAnswer = "2π/3";
            }

            return  correctAnswer;
        }

        else {

            boolean flip = false;
            double operand;
            double badAnswer = 0;
            String goodAnswer = "";

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

            return goodAnswer;
        }

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
}