package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.numerics.Function;

public class ScaleFunction implements Function {
   double min;
   double max;
   double delta;

   public ScaleFunction(double var1, double var3) {
      this.setMinMax(var1, var3);
   }

   public void setMinMax(double var1, double var3) {
      this.min = var1;
      this.max = var3;
   }

   public double evaluate(double var1) {
      return var1;
   }

   public static ScaleFunction getSqrtScale(double var0, double var2) {
      return new SqrtIntensityScale(var0, var2);
   }

   public static ScaleFunction getLogScale(double var0, double var2) {
      return new LogIntensityScale(var0, var2);
   }

   public static ScaleFunction getExpScale(double var0, double var2) {
      return new ExpIntensityScale(var0, var2);
   }
}
