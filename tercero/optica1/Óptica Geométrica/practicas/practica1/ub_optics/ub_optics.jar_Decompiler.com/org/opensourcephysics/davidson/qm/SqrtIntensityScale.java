package org.opensourcephysics.davidson.qm;

class SqrtIntensityScale extends ScaleFunction {
   public SqrtIntensityScale(double var1, double var3) {
      super(var1, var3);
   }

   public void setMinMax(double var1, double var3) {
      super.delta = var3 - var1;
      super.max = var3 - 0.001D * super.delta;
      super.min = var1;
   }

   public double evaluate(double var1) {
      return var1 >= super.max ? super.max : Math.sqrt(var1 * super.max);
   }
}
