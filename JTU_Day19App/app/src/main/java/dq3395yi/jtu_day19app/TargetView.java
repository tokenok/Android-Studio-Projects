package dq3395yi.jtu_day19app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Josh_2 on 3/28/2017.
 */

public class TargetView extends View {
    private Paint paint;
    private Point touchPoint;

    int canvasWidth;
    float centerX;
    float centerY;

    int ringCount = 5;

    ArrayList<Point> points = new ArrayList<>();

    double center = 128;
    double width = 127;
    double phase = 128;

    public void setTouchPoint(Point pt) { this.touchPoint = pt; }
    public Point getTouchPoint() { return touchPoint; }

    public ArrayList<Point> getPoints() { return points; }

    public TargetView(Context context) {
        super(context);
        paint = new Paint();
        touchPoint = null;
        MainActivity mainActivity = (MainActivity)context;
        ringCount = mainActivity.getRingCount();
    }

    public void onDraw (Canvas canvas) {
        canvasWidth = canvas.getWidth();
        centerX = canvasWidth / 2;
        centerY = canvas.getHeight() / 2;
        canvas.drawRGB(194, 183, 158);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(255, 255, 0));

        for (int i = 0; i < ringCount; i++) {
            float radius = calcRadius(i);
            drawShape(canvas, centerX, centerY, radius, getColor(i, ringCount));
        }

        for (int i = 0; i < points.size(); i++){
            drawShape(canvas, Float.intBitsToFloat(points.get(i).x), Float.intBitsToFloat(points.get(i).y), 10, Color.rgb(0, 0, 0));
        }
    }

    int getColor(int i, int max) {
        double frequency = Math.PI * 2 / max;
        int r = (int)(Math.sin(frequency * (max - i) + 0 + phase) * width + center);
        int g = (int)(Math.sin(frequency * (max - i) + 2 + phase) * width + center);
        int b = (int)(Math.sin(frequency * (max - i) + 4 + phase) * width + center);

        return Color.rgb(r, g, b);
    }

    public void update(){
        invalidate();
    }

    public float calcRadius(int ringNum) {
        return ((float)(canvasWidth / 2)) * (((float) ringCount - (float) ringNum) / (float) ringCount);
    }

    private void drawShape(Canvas canvas, float centerX, float centerY, float radius, int color){
        int old_color = paint.getColor();
        paint.setColor(color);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setColor(old_color);
    }

    public int calcScore() {
        for (int i = ringCount - 1; i >= 0; i--) {
            if (ptInCircle(calcRadius(i), touchPoint)){
                return i + 1;
            }
        }
        return 0;
    }

    private boolean ptInCircle(float radius, Point pt){
        if (pt == null) return false;
        return (Math.sqrt(Math.pow((Float.intBitsToFloat(pt.x) - centerX), 2) + Math.pow((Float.intBitsToFloat(pt.y) - centerY), 2))) <= radius;
    }

}
