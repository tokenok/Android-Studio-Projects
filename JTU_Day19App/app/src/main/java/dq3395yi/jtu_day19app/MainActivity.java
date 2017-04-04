package dq3395yi.jtu_day19app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    TargetView  targetView;
    private GestureDetectorCompat aGesture;

    int maxTaps = 3;
    int touchCount = 0;
    int totalScore = 0;
    int ringCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        aGesture = new GestureDetectorCompat(this, new MyGestureListener());

        setContentView(R.layout.activity_main);

        Button btn_play = (Button) this.findViewById(R.id.BTN_PLAY);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringCount = Integer.parseInt(((EditText)findViewById(R.id.EDC_NUMRINGS)).getText().toString());
                maxTaps = Integer.parseInt(((EditText)findViewById(R.id.EDC_NUMSHOTS)).getText().toString());
                targetView = new TargetView(MainActivity.this);
                setContentView(targetView);
            }
        });

     //   setContentView(targetView);
    }

    public int getRingCount() { return this.ringCount; }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.aGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (targetView != null) {
                targetView.setTouchPoint(new Point(Float.floatToIntBits(e.getX()), Float.floatToIntBits(e.getY())));
                targetView.getPoints().add(targetView.getTouchPoint());

                targetView.update();

                totalScore += targetView.calcScore();

                if (++touchCount >= maxTaps) {
                    touchCount = 0;
                    targetView.getPoints().clear();

                    Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                    intent.putExtra("totalScore", totalScore);
                    intent.putExtra("ringCount", ringCount);
                    intent.putExtra("maxTaps", maxTaps);
                    totalScore = 0;
                    startActivity(intent);
                }
            }

            return super.onDoubleTap(e);
        }
    }
}
