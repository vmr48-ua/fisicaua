package org.opensourcephysics.display;

public interface Interactive extends Measurable {
   Interactive findInteractive(DrawingPanel var1, int var2, int var3);

   void setEnabled(boolean var1);

   boolean isEnabled();

   void setXY(double var1, double var3);

   void setX(double var1);

   void setY(double var1);

   double getX();

   double getY();
}
