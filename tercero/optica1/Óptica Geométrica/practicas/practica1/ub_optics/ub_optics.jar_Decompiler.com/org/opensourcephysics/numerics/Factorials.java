package org.opensourcephysics.numerics;

public class Factorials {
   static double[] cof = new double[]{76.18009172947146D, -86.50532032941678D, 24.01409824083091D, -1.231739572450155D, 0.001208650973866179D, -5.395239384953E-6D};
   static long[] fac;

   private Factorials() {
   }

   public static double gammaln(double var0) {
      double var2 = var0;
      double var4 = var0 + 5.5D;
      var4 -= (var0 + 0.5D) * Math.log(var4);
      double var6 = 1.000000000190015D;

      for(int var8 = 0; var8 <= 5; ++var8) {
         var6 += cof[var8] / ++var2;
      }

      return -var4 + Math.log(2.5066282746310007D * var6 / var0);
   }

   public static double factorial(int var0) {
      if (var0 < 0) {
         throw new IllegalArgumentException("Negative value passed to factorial.");
      } else {
         return var0 < fac.length ? (double)fac[var0] : Math.exp(gammaln((double)var0 + 1.0D));
      }
   }

   public static double logFactorial(int var0) {
      if (var0 < 0) {
         throw new IllegalArgumentException("Negative value passed to logFactorial.");
      } else {
         return gammaln((double)var0 + 1.0D);
      }
   }

   public static double poisson(double var0, int var2) {
      return Math.exp((double)var2 * Math.log(var0) - var0 - logFactorial(var2));
   }

   public static double logChoose(int var0, int var1) {
      return logFactorial(var0) - logFactorial(var1) - logFactorial(var0 - var1);
   }

   static {
      long var0 = 1L;

      int var2;
      for(var2 = 1; var0 * (long)var2 >= var0; ++var2) {
         var0 *= (long)var2;
      }

      fac = new long[var2];
      fac[0] = 1L;

      for(int var3 = 1; var3 < var2; ++var3) {
         fac[var3] = fac[var3 - 1] * (long)var3;
      }

   }
}
