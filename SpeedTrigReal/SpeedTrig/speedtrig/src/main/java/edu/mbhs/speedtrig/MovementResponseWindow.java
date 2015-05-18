package edu.mbhs.speedtrig;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speedtrig.R;

import edu.mbhs.speedtrig.util.QuestionSolver;

import static android.content.Context.*;

public class MovementResponseWindow extends ResponseWindow implements SensorEventListener {
    private SensorManager daMan;
    private Sensor _2ndDerivativometer;
    private boolean stahp = false;//If its true then the app wont keep doing stuff with sensor values
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daMan = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        _2ndDerivativometer = daMan.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        daMan.registerListener(this, _2ndDerivativometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movement_response_window, menu);

        return true;
    }

    /**
     * Called when sensor values have changed.
     * <p>See {@link SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link SensorEvent SensorEvent}.
     * <p/>
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link SensorEvent event}
     * object passed as a parameter and therefore cannot hold on to it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!stahp) {
            double xel = event.values[0];
            double yell = event.values[1];
            double mag = Math.sqrt(xel * xel + yell * yell);
            if (mag >= 7){
                stahp = true;
                xel /= mag;
                yell /= mag;
                double angle;
                if(xel<0 && yell<0){
                    angle = Math.PI + Math.atan(yell/xel);
                }
                else if(yell>0){
                    angle = Math.acos(xel);
                }
                else angle = 2*Math.PI+Math.asin(yell);
                angle /= Math.PI;
                double[] posibilities = {0,1/6.0,1/4.0,1/3.0,1/2.0,2/3.0,3/4.0,5/6.0,1.0,7/6.0,5/4.0,4/3.0,3/2.0,5/3.0,7/4.0,11/6.0,2.0};
                String[] answers = {"0","\u03C0/6","\u03C0/4","\u03C0/3","\u03C0/2","2\u03C0/3","3\u03C0/4","5\u03C0/6","\u03C0","7\u03C0/6","5\u03C0/4","4\u03C0/3","3\u03C0/2","5\u03C0/3","7\u03C0/4","11\u03C0/6","2\u03C0"};
                int mindex = 0;
                double minval = 200;
                for(int i = 0; i<posibilities.length; i++){
                    double temp = Math.abs(posibilities[i] - angle);
                    if(temp < minval){
                        mindex = i;
                        minval = temp;
                    }
                }
                responseCopy.setText(answers[mindex]);
                responses[questionIndex] = responseCopy.getText().toString();
               // Log.d("bluh",answers[mindex]);
            }
            //Log.d("bluh","nybody?");
         //   updateEverything(this.getCurrentFocus());
        }
       // Log.d("bluh","hello?");

    }

    /**
     * Called when the accuracy of the registered sensor has changed.
     * <p/>
     * <p>See the SENSOR_STATUS_* constants in
     * {@link SensorManager SensorManager} for details.
     *
     * @param sensor
     * @param accuracy The new accuracy of this sensor, one of
     *                 {@code SensorManager.SENSOR_STATUS_*}
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void openNextQuestion(View v){
        stahp = false;
        String questionVal = questions[questionIndex];
        String response = responseCopy.getText().toString().trim();
        boolean isCorrect;
        if(response.equals("0")) isCorrect = questionVal.substring(questionVal.indexOf('.') + 2).equals("0");
        else{
            if(!questionVal.contains("?")) isCorrect = false;
            else {
                double resp, ans;
                if((response.charAt(0) + "").equals("?")) resp = Math.PI;
                else resp= Integer.parseInt(response.substring(0, response.indexOf("?"))) * Math.PI ;
                if(!(response.charAt(response.length()-1) + "").equals("?"))
                    resp /= Integer.parseInt(response.substring(response.indexOf("?")+1));

                if((questionVal.charAt(0) + "").equals("?")) ans = Math.PI;
                else ans= Integer.parseInt(questionVal.substring(0, questionVal.indexOf("?"))) * Math.PI ;
                if(!(questionVal.charAt(questionVal.length()-1) + "").equals("?"))
                    ans /= Integer.parseInt(questionVal.substring(questionVal.indexOf("?")+1));
                isCorrect = Math.abs(Math.cos(resp) - Math.cos(ans)) < 0.01 && Math.abs(Math.sin(resp) - Math.sin(ans)) < 0.01;
            }
    }
      //  String correct = QuestionSolver.solve(questionVal.substring(questionVal.indexOf('.') + 2)).trim();
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
            question.setText("#" + questions[questionIndex]);
            responseCopy.setText(responses[questionIndex]);
//            back_button.setVisibility(TextView.VISIBLE);
            if (questionIndex == questions.length - 1) {
                next_button.setVisibility(TextView.INVISIBLE);
                submit_button.setVisibility(TextView.VISIBLE);
            }
        }

        if(!response.equals(""))
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
