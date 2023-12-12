package org.opensourcephysics.display;

public interface Measurable extends Drawable {
   double getXMin();

   double getXMax();

   double getYMin();

   double getYMax();

   boolean isMeasured();
}
