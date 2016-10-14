package com.deitel.doodlz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created by deb on 10/14/16.
 */

public class Oval extends Rectangle implements DoodleShape {

    public Oval(){}
    // Create a Curves object
    public static Oval newInstance(Paint paint) {
        Oval doodle = new Oval();
        doodle.paint = paint;
        return doodle;
    }
    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (doneDragging) {
            canvas.drawOval(rect, paint);
            doneDragging = false;
        }
    }
}
