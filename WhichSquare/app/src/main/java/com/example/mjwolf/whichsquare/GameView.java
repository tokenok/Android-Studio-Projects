package com.example.mjwolf.whichsquare;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by mjwolf on 3/26/17.
 */

public class GameView extends View {
    private Paint paint; //this object holds information about how to paint: color, fill style, ...
    private int redVal;
    private int greenVal;
    private int blueVal;
    private boolean reveal;
    private Point touchPoint;
    private Random rand = new Random();

    public GameView(Context context) {
        super(context);
        redVal = 248; //pick some numbers for the color
        greenVal = 23;
        blueVal = 68;
        paint = new Paint();
        reveal = false;
        touchPoint = null;
    }

    public void toggleReveal() {
       reveal = !reveal;
    }

    public void setTouchPoint(Point p){
        touchPoint = p;
    }

    public void onDraw (Canvas canvas) {
        //INITIALIZE THE CENTER OF THE CANVAS
        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight() / 2;
        //Now set things up so that a square is 1/4 of the narrower dimension
        float halfSquareSide;
        halfSquareSide = canvas.getWidth() > canvas.getHeight() ? canvas.getHeight()/8 : canvas.getWidth()/8;
        int location;
        canvas.drawRGB(194, 183, 158); //make the background beige

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(redVal, greenVal, blueVal));

        if (!reveal) {
            touchPoint = null; //ignore the touch point, draw only squares
            location = 0;
        }else{
            location = rand.nextInt(3)+1; //1-top, 2-center, 3-bottom
        }
        Log.d("GameView: location", Integer.toString(location) );

        //draw the top
        drawShape(canvas, centerX, canvas.getHeight() / 4, halfSquareSide, reveal && location == 1);
        //draw the center
        drawShape(canvas, centerX, centerY, halfSquareSide, reveal && location == 2 );
        //draw the bottom
        drawShape(canvas,centerX,canvas.getHeight() * 3 / 4,halfSquareSide, reveal && location == 3 );
    }

    public void update(){
        invalidate();
    }

    private void drawShape(Canvas canvas, float centerX, float centerY, float halfSquareSide, boolean drawCircle){
        float left = centerX - halfSquareSide;
        float top = centerY - halfSquareSide;
        float right = centerX + halfSquareSide;
        float bottom = centerY + halfSquareSide;

        if (insideSquare(left, top, right, bottom) && drawCircle) {
            // use a different color for the ball
            paint.setColor(Color.rgb(redVal - 100, greenVal, blueVal + 100));
            canvas.drawCircle(centerX, centerY, halfSquareSide * 4 / 5, paint);
            // and set the color back to what it was
            paint.setColor(Color.rgb(redVal, greenVal, blueVal));
        } else {
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private boolean insideSquare(float left, float top, float right, float bottom){
        if (touchPoint == null) return false;
        return (Float.intBitsToFloat(touchPoint.x) >= left
                && Float.intBitsToFloat(touchPoint.x) <= right
                && Float.intBitsToFloat(touchPoint.y) >= top
                && Float.intBitsToFloat(touchPoint.y) <= bottom);
    }

}
