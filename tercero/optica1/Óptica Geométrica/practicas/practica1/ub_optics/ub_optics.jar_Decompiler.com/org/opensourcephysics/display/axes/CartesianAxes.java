package org.opensourcephysics.display.axes;

public interface CartesianAxes extends DrawableAxes {
   void setX(double var1);

   void setY(double var1);

   double getX();

   double getY();

   boolean isXLog();

   boolean isYLog();

   void setXLog(boolean var1);

   void setYLog(boolean var1);
}
