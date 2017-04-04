package dq3395yi.finalapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Josh_2 on 4/1/2017.
 */

public class SightingView extends View {
    int canvasWidth;
    private Paint paint;
    private Point touchPoint;

    int currentHunt;

    ArrayList<ArrayList<Point>> sightingLines = new ArrayList<ArrayList<Point>>();

    public int getCurrentHunt() {
        return currentHunt;
    }
    public void setCurrentHunt(int currentHunt) {
        this.currentHunt = currentHunt;
    }

    public ArrayList<ArrayList<Point>> getSightingLines() {
        return sightingLines;
    }

    public void setTouchPoint(Point touchPoint) {
        this.touchPoint = touchPoint;
    }
    public Point getTouchPoint() {
        return touchPoint;
    }

    public SightingView(Context context) {
        super(context);
        paint = new Paint();
        touchPoint = null;
        currentHunt = 0;
    }

    public void update(){
        invalidate();
    }

    public void onDraw (Canvas canvas) {
        canvasWidth = canvas.getWidth();

        canvas.drawRGB(194, 183, 158);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(255, 255, 0));

        for (int i = 0; i < sightingLines.size(); i++) {
            for (int j = 0; j < sightingLines.get(i).size() - 1; j++){
                float x1 = Float.intBitsToFloat(sightingLines.get(i).get(j).x);
                float y1 = Float.intBitsToFloat(sightingLines.get(i).get(j).y);

                float x2 = Float.intBitsToFloat(sightingLines.get(i).get(j + 1).x);
                float y2 = Float.intBitsToFloat(sightingLines.get(i).get(j + 1).y);

                paint.setColor(getColor(i));
                paint.setStrokeWidth(5);

                canvas.drawLine(x1, y1, x2, y2, paint);
            }
        }
    }

    int getColor(int i) {
        int max = 25;
        double center = 128;
        double width = 127;
        double phase = 128;

        double frequency = Math.PI * 2 / max;
        int r = (int)(Math.sin(frequency * (max - i) + 0 + phase) * width + center);
        int g = (int)(Math.sin(frequency * (max - i) + 2 + phase) * width + center);
        int b = (int)(Math.sin(frequency * (max - i) + 4 + phase) * width + center);

        return Color.rgb(r, g, b);
    }

}
