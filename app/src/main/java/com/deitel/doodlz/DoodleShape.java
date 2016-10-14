package com.deitel.doodlz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by deb on 10/14/16.
 */

public interface DoodleShape {

    void clear(); // clear the shape from the canvas
    void draw(Canvas canvas, Paint paint); // draw the shape on a canvas
    void touchStarted(float x, float y, int lineID);
    void touchMoved(MotionEvent event);
    // called when the user finishes a touch
    void touchEnded(int lineID, Canvas canvas);
}
