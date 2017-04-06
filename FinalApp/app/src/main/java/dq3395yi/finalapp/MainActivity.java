package dq3395yi.finalapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {
    SightingView sightingView;

    public File cacheDir;

    private ScaleGestureDetector mScaleDetector;

    boolean isSightingView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());














        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir = getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        DeerData m = new DeerData(cacheDir);
        boolean result = m.saveObject(m);

        if(result)
            Toast.makeText(this, "Saved object", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error saving object", Toast.LENGTH_LONG).show();

        DeerData c = m.getObject(this);

        if(c!= null)
            Toast.makeText(this, "Retrieved object", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error retrieving object", Toast.LENGTH_LONG).show();






    }

    public void addSighting(View view) {
        if (sightingView == null)
            sightingView = new SightingView(MainActivity.this);
        isSightingView = true;
        setContentView(sightingView);
    }

    public void startNewHunt(View view) {
        sightingView = null;
    }

    public void reviewHunt(View view) {

    }

    @Override
    public void onBackPressed() {
        if (isSightingView) {
            isSightingView = false;
            setContentView(R.layout.activity_main);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        mScaleDetector.onTouchEvent(e);

        final int action = MotionEventCompat.getActionMasked(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                if (sightingView != null) {
                    if (sightingView.getSightingLines().size() <= sightingView.getCurrentHunt()){
                        sightingView.getSightingLines().add(new ArrayList<Point>());
                    }

                    sightingView.setTouchPoint(new Point(Float.floatToIntBits(e.getX()), Float.floatToIntBits(e.getY())));
                    sightingView.getSightingLines().get(sightingView.getCurrentHunt()).add(sightingView.getTouchPoint());

                    sightingView.update();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (sightingView != null) {
                    sightingView.setCurrentHunt(sightingView.getCurrentHunt() + 1);
                }
                break;
            }
        }

        return super.onTouchEvent(e);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    }
}
