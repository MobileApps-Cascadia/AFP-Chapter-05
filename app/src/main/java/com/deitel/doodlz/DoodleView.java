// DoodleView.java
// Main View for the Doodlz app.
package com.deitel.doodlz;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

// custom View for drawing
public class DoodleView extends View {
   // used to determine what a touch draws: a curve, rectangle, or oval
   public enum ShapeEnum {
      Curve, Rectangle, Oval
   }
   private ShapeEnum shapeEnum; // keep track of the current shape
   private DoodleShape shape; // hold the current doodle shape
   // used to determine whether user moved a finger enough to draw again
   public static final float TOUCH_TOLERANCE = 10;

   private Bitmap bitmap; // drawing area for displaying or saving
   private Canvas bitmapCanvas; // used to to draw on the bitmap
   private final Paint paintLine; // used to draw lines onto bitmap
   private int backgroundColor = Color.WHITE; // background color when a new drawing is started
   private int defaultBackgroundColor = Color.WHITE; // default background color when a new drawing is started

   // DoodleView constructor initializes the DoodleView
   public DoodleView(Context context, AttributeSet attrs) {
      super(context, attrs); // pass context to View's constructor

      // set the initial display settings for the painted line
      paintLine = new Paint();
      paintLine.setAntiAlias(true); // smooth edges of drawn line
      paintLine.setColor(Color.BLACK); // default color is black
      paintLine.setStyle(Paint.Style.STROKE); // solid line
      paintLine.setStrokeWidth(5); // set the default line width
      paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends

      shape = Curves.newInstance(paintLine);
   }

   // Set the shape for the drawing and update the menu
   public void setShape(ShapeEnum shapeEnum) {
      this.shapeEnum = shapeEnum;
      switch (shapeEnum) {
         case Curve:
            shape = Curves.newInstance(paintLine);
            break;
         case Rectangle:
            shape = Rectangle.newInstance(paintLine);
            break;
         case Oval:
            shape = Oval.newInstance(paintLine);
            break;
      }
   }

   // creates Bitmap and Canvas based on View's size
   @Override
   public void onSizeChanged(int w, int h, int oldW, int oldH) {
      bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
         Bitmap.Config.ARGB_8888);
      bitmapCanvas = new Canvas(bitmap);
      bitmap.eraseColor(Color.TRANSPARENT); // erase the Bitmap with white
   }

   // clear the painting
   public void clear() {
      shape.clear(); // remove all paths
      bitmap.eraseColor(Color.TRANSPARENT); // clear the bitmap
      backgroundColor = defaultBackgroundColor;
      setBackgroundColor(backgroundColor);
      invalidate(); // refresh the screen
   }

   // set the painted line's color
   public void setDrawingColor(int color) {
      paintLine.setColor(color);
   }

   // return the painted line's color
   public int getDrawingColor() {
      return paintLine.getColor();
   }

   // set the painted line's width
   public void setLineWidth(int width) {
      paintLine.setStrokeWidth(width);
   }

   // return the painted line's width
   public int getLineWidth() {
      return (int) paintLine.getStrokeWidth();
   }

   // return the current background color
   public int getBackgroundColor() { return backgroundColor; }

   // set the background color and save it
   @Override
   public void setBackgroundColor(int color) {
      super.setBackgroundColor(color);
      backgroundColor = color;
   }

   // return the current default background color
   public int getDefaultBackgroundColor() { return defaultBackgroundColor; }

   // set the background default color
   public void setDefaultBackgroundColor(int color) { defaultBackgroundColor = color; }

   // perform custom drawing when the DoodleView is refreshed on screen
   @Override
   protected void onDraw(Canvas canvas) {
      // draw the background screen
      canvas.drawBitmap(bitmap, 0, 0, null);

      // for each path currently being drawn
      shape.draw(bitmapCanvas, paintLine); // draw line
   }

   // handle touch event
   @Override
   public boolean onTouchEvent(MotionEvent event) {
      int action = event.getActionMasked(); // event type
      int actionIndex = event.getActionIndex(); // pointer (i.e., finger)

      // determine whether touch started, ended or is moving
      if (action == MotionEvent.ACTION_DOWN ||
         action == MotionEvent.ACTION_POINTER_DOWN) {
         shape.touchStarted(event.getX(actionIndex), event.getY(actionIndex),
            event.getPointerId(actionIndex));
      }
      else if (action == MotionEvent.ACTION_UP ||
         action == MotionEvent.ACTION_POINTER_UP) {
         shape.touchEnded(event.getPointerId(actionIndex), bitmapCanvas);
      }
      else {
         shape.touchMoved(event);
      }

      invalidate(); // redraw
      return true;
   }

   // save the current image to the Gallery
   public void saveImage() {
      // use "Doodlz" followed by current time as the image name
      final String name = "Doodlz" + System.currentTimeMillis() + ".jpg";

      // insert the image on the device
      String location = MediaStore.Images.Media.insertImage(
         getContext().getContentResolver(), addBackground(), name,
         "Doodlz Drawing");

      if (location != null) {
         // display a message indicating that the image was saved
         Toast message = Toast.makeText(getContext(),
            R.string.message_saved,
            Toast.LENGTH_SHORT);
         message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
            message.getYOffset() / 2);
         message.show();
      }
      else {
         // display a message indicating that there was an error saving
         Toast message = Toast.makeText(getContext(),
            R.string.message_error_saving, Toast.LENGTH_SHORT);
         message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
            message.getYOffset() / 2);
         message.show();
      }
   }
   // Create a bitmap that includes the background for exporting to a jpg or printing
   private Bitmap addBackground() {
      Bitmap merged = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
              Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(merged);
      merged.eraseColor(backgroundColor); // put the background color on the bitmap
      canvas.drawBitmap(bitmap, 0, 0, null);
      return merged;
   }

   // print the current image
   public void printImage() {
      if (PrintHelper.systemSupportsPrint()) {
         // use Android Support Library's PrintHelper to print image
         PrintHelper printHelper = new PrintHelper(getContext());

         // fit image in page bounds and print the image
         printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
         printHelper.printBitmap("Doodlz Image", addBackground());
      }
      else {
         // display message indicating that system does not allow printing
         Toast message = Toast.makeText(getContext(),
            R.string.message_error_printing, Toast.LENGTH_SHORT);
         message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
            message.getYOffset() / 2);
         message.show();
      }
   }
}

/**************************************************************************
 * (C) Copyright 1992-2016 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
