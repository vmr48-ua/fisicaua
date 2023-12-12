package org.opensourcephysics.davidson.qm;

class ExpIntensityScale extends ScaleFunction {
   public ExpIntensityScale(double var1, double var3) {
      super(var1, var3);
   }

   public void setMinMax(double var1, double var3) {
      super.max = var3;
   }

   public double evaluate(double var1) {
      return super.max * (1.0D - Math.exp(-var1 / super.max));
   }
}
