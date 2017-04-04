package com.example.mjwolf.whichsquare;

import android.app.Activity;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class DrawingActivity extends Activity {
    //be sure to use an Activity so that you don't get an ActionBar

    GameView gameView;
    private GestureDetectorCompat aGesture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // we want this to be full screen. First, drop the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameView = new GameView(this);


        setContentView(gameView);

        aGesture = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.aGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public void onLongPress(MotionEvent event) {
            gameView.toggleReveal(); //only try to show the ball on every other try
            gameView.setTouchPoint(new Point(Float.floatToIntBits(event.getX()), Float.floatToIntBits(event.getY())));
            gameView.update();
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
        }

    }


}
