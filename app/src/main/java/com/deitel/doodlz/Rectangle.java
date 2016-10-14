package com.deitel.doodlz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.deitel.doodlz.DoodleShape;

/**
 * Created by deb on 10/14/16.
 */

public class Rectangle implements DoodleShape {
    public Rectangle(){} // required for Factory method

    protected Paint paint;
    protected RectF rect; // rectangle to draw
    protected boolean doneDragging = false;

    // Create a Curves object
    public static Rectangle newInstance(Paint paint) {
        Rectangle doodle = new Rectangle();
        doodle.paint = paint;
        return doodle;
    }
    public void clear() {} // clear the shape from the canvas

    // Create a new rect with start and end points the same
    public void touchStarted(float x, float y, int lineID) {
        rect = new RectF(x, y, x, y);
    }
    public void touchMoved(MotionEvent event) {
        // get the pointer ID and pointer index
        int pointerID = event.getPointerId(event.getPointerCount() - 1);
        int pointerIndex = event.findPointerIndex(pointerID);

        rect.right = event.getX(pointerIndex);
        rect.bottom = event.getY(pointerIndex);
    }
    // called when the user finishes a touch
    public void touchEnded(int lineID, Canvas canvas) {
        //canvas.drawRect(rect, paint);
        doneDragging = true;
    }
    public void draw(Canvas canvas, Paint paint) {
        if (doneDragging) {
            canvas.drawRect(rect, paint);
            doneDragging = false;
        }
    }

}
