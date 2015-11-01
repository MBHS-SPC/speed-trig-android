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
import android.widget.Toast;

import com.example.speedtrig.R;

public class UnitCircle extends Activity implements SurfaceHolder.Callback {

    public static final String TAG = "Interactive Unit Circle";

    SurfaceView surfaceView;

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
                    float x = event.getX();
                    float y = event.getY();
                    Toast.makeText(UnitCircle.this, x + " " + y, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
        Log.i(TAG, "Trying to draw...");

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw onto null canvas");
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
        float bigRadius = Math.min(canvas.getWidth(), canvas.getHeight()) * 3 / 8;
        float cx = canvas.getWidth() / 2, cy = canvas.getHeight() / 2;
        canvas.drawCircle(cx, cy, bigRadius, paint);
        // line radius is 7/8 of the smaller of the two dimensions
        float lineRadius = bigRadius * 7 / 6;
        canvas.drawLine(cx - lineRadius, cy, cx + lineRadius, cy, paint);
        canvas.drawLine(cx, cy - lineRadius, cx, cy + lineRadius, paint);
    }
}
