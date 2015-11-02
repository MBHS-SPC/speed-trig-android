package edu.mbhs.speedtrig;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import edu.mbhs.speedtrig.util.QuestionSolver;
import edu.mbhs.speedtrig.util.Util;

public class UnitCircle extends Activity implements SurfaceHolder.Callback {

    public static final String TAG = "Interactive Unit Circle";

    SurfaceView surfaceView;
    float cx, cy, bigRadius, buttonRadius;
    float thetaSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_circle);

        surfaceView = (SurfaceView) findViewById(R.id.interactive_unit_circle);

        surfaceView.getHolder().addCallback(this);

        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

                    double theta = angleClosestTo(event.getX(), event.getY());
                    if (theta == -1) return true;

                    thetaSelected = (float) theta;
                    attemptDraw(surfaceView.getHolder());
                    updateValues();
                }
                return true;
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        updateValues();
        attemptDraw(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        attemptDraw(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void attemptDraw(SurfaceHolder holder) {

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw on null canvas");
        }
        else {
            drawCircle(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawCircle(final Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();


        // Draw unit circle outline

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        // we want the diameter to be 3/4 of the smaller of the two dimensions
        bigRadius = Math.min(canvas.getWidth(), canvas.getHeight()) * 3 / 8;
        cx = canvas.getWidth() / 2; cy = canvas.getHeight() / 2;
        canvas.drawCircle(cx, cy, bigRadius, paint);
        // line radius is 7/8 of the smaller of the two dimensions
        float lineRadius = bigRadius * 7 / 6;
        canvas.drawLine(cx - lineRadius, cy, cx + lineRadius, cy, paint);
        canvas.drawLine(cx, cy - lineRadius, cx, cy + lineRadius, paint);


        // Draw tiny circle "buttons"

        // first angle (default) will already be "selected"
        buttonRadius = bigRadius / 15;
        float buttonX, buttonY;

        // pi/6, pi/3, pi/2, 2pi/3, 5pi/6, pi, 7pi/6, 4pi/3, 3pi/2, 5pi/3, 11pi/6
        for (float theta = 0; Math.abs(theta - 2 * Math.PI) > 0.001; theta += Math.PI / 6) {
            buttonX = cx + (float) Math.cos(theta) * bigRadius;
            buttonY = cy - (float) Math.sin(theta) * bigRadius;
            // draw filled in circle first
            if (Math.abs(theta - thetaSelected) < 0.001)    // if this angle is selected
                paint.setColor(Color.CYAN);
            else
                paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(buttonX, buttonY, buttonRadius-2.5f, paint);
            // then draw outline circle
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(buttonX, buttonY, buttonRadius, paint);
        }

        // pi/4, 3pi/4, 5pi/4, 7pi/4
        for (float theta = (float)(Math.PI / 4); theta < 2 * Math.PI; theta += Math.PI / 2) {
            buttonX = cx + (float) Math.cos(theta) * bigRadius;
            buttonY = cy - (float) Math.sin(theta) * bigRadius;
            // draw filled in circle first
            if (Math.abs(theta - thetaSelected) < 0.001)    // if this angle is selected
                paint.setColor(Color.CYAN);
            else
                paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(buttonX, buttonY, buttonRadius-2.5f, paint);
            // then draw outline circle
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(buttonX, buttonY, buttonRadius, paint);
        }
    }

    /**
     * Return the angle on the unit circle closest to the given point, or -1 if the point is
     * farther than 2 * (the button radius) from any valid angle on the unit circle
     * @param x x coordinate
     * @param y y coordinate
     * @return The closest valid angle [0, 2pi) on the drawn unit circle or -1
     */
    private double angleClosestTo(float x, float y) {
        // if the distance is 50 or more, they probably just touched the screen without wanting
        // to change the angle
        double minDist = buttonRadius * 2;
        double closestAngle = -1;
        double circleX, circleY, dist;

        // pi/6, pi/3, pi/2, 2pi/3, 5pi/6, pi, 7pi/6, 4pi/3, 3pi/2, 5pi/3, 11pi/6
        for (double theta = 0; Math.abs(theta - 2 * Math.PI) > 0.001; theta += Math.PI / 6) {
            circleX = cx + Math.cos(theta)*bigRadius;
            circleY = cy - Math.sin(theta)*bigRadius;
            dist = Math.hypot(circleX - x, circleY - y);
            if (dist < minDist) {
                minDist = dist;
                closestAngle = theta;
            }
        }

        // pi/4, 3pi/4, 5pi/4, 7pi/4
        for (float theta = (float)(Math.PI / 4); theta < 2 * Math.PI; theta += Math.PI / 2) {
            circleX = cx + Math.cos(theta)*bigRadius;
            circleY = cy - Math.sin(theta)*bigRadius;
            dist = Math.hypot(circleX - x, circleY - y);
            if (dist < minDist) {
                minDist = dist;
                closestAngle = theta;
            }
        }

        return closestAngle;
    }

    /**
     * Update the TextViews with the function values corresponding to thetaSelected
     */
    private void updateValues() {
        // update angle TextView
        ((TextView) findViewById(R.id.unit_text_angle)).setText(String.format("%s%s",
                getString(R.string.unit_circle_angle_default),
                Util.getPiFraction(thetaSelected))
        );

        final double theta = thetaSelected;
        TextView function;
        double functionValue;

        /*
        For each function, get the corresponding TextView, evaluate the function normally,
        then get convert the answer to a nice String using QuestionSolver.getClosestAnswer()
         */

        // sin
        function = (TextView) findViewById(R.id.unit_text_sin);
        functionValue = Math.sin(theta);
        function.setText(String.format("%s%s",
                getString(R.string.unit_circle_sin_default),
                QuestionSolver.getClosestAnswer(functionValue, false))
        );

        // csc
        function = (TextView) findViewById(R.id.unit_text_csc);
        function.setText(String.format("%s%s",
                getString(R.string.unit_circle_csc_default),
                QuestionSolver.getClosestAnswer(functionValue, true))
        );

        // cos
        function = (TextView) findViewById(R.id.unit_text_cos);
        functionValue = Math.cos(theta);
        function.setText(String.format("%s%s",
                getString(R.string.unit_circle_cos_default),
                QuestionSolver.getClosestAnswer(functionValue, false))
        );

        // sec
        function = (TextView) findViewById(R.id.unit_text_sec);
        function.setText(String.format("%s%s",
                getString(R.string.unit_circle_sec_default),
                QuestionSolver.getClosestAnswer(functionValue, true))
        );

        // tan
        function = (TextView) findViewById(R.id.unit_text_tan);
        functionValue = Math.tan(theta);
        function.setText(String.format("%s%s",
                getString(R.string.unit_circle_tan_default),
                QuestionSolver.getClosestAnswer(functionValue, false))
        );

        // cot
        function = (TextView) findViewById(R.id.unit_text_cot);
        function.setText(String.format("%s%s",
                getString(R.string.unit_circle_cot_default),
                QuestionSolver.getClosestAnswer(functionValue, true))
        );


    }
}
