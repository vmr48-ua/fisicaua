package org.opensourcephysics.numerics;

public final class LogBase10Function implements InvertibleFunction {
   static double log10 = Math.log(10.0D);

   public double evaluate(double var1) {
      return Math.log(var1) / log10;
   }

   public double getInverse(double var1) {
      return Math.pow(10.0D, var1);
   }
}
