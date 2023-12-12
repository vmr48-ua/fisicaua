package org.opensourcephysics.display;

import org.opensourcephysics.controls.XML;

public class MeasuredCircle extends Circle implements Measurable {
   boolean enableMeasure = true;

   public MeasuredCircle(double var1, double var3) {
      super(var1, var3);
   }

   public void setMeasured(boolean var1) {
      this.enableMeasure = var1;
   }

   public boolean isMeasured() {
      return this.enableMeasure;
   }

   public double getXMin() {
      return super.x;
   }

   public double getXMax() {
      return super.x;
   }

   public double getYMin() {
      return super.y;
   }

   public double getYMax() {
      return super.y;
   }

   public static XML.ObjectLoader getLoader() {
      return new CircleLoader();
   }
}
