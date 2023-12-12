package org.opensourcephysics.davidson.qm;

class LogIntensityScale extends ScaleFunction {
   static final double CUTOFF = 1.0E-4D;
   static final double LOGCUTOFF = Math.log(1.0E-4D);

   public LogIntensityScale(double var1, double var3) {
      super(var1, var3);
   }

   public void setMinMax(double var1, double var3) {
      super.max = var3;
      super.delta = Math.log(var3) - Math.log(1.0E-4D);
   }

   public double evaluate(double var1) {
      return var1 <= 1.0E-4D ? 0.0D : super.max * (Math.log(var1) - LOGCUTOFF) / super.delta;
   }
}
