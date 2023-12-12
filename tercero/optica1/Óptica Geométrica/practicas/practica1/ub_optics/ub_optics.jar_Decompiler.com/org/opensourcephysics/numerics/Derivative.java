package org.opensourcephysics.numerics;

public class Derivative {
   private Derivative() {
   }

   public static Function getFirst(final Function var0, final double var1) {
      return new Function() {
         public double evaluate(double var1x) {
            return (var0.evaluate(var1x + var1) - var0.evaluate(var1x - var1)) / var1 / 2.0D;
         }
      };
   }

   public static Function getSecond(final Function var0, final double var1) {
      return new Function() {
         public double evaluate(double var1x) {
            return (var0.evaluate(var1x + var1) - 2.0D * var0.evaluate(var1x) + var0.evaluate(var1x - var1)) / var1 / var1;
         }
      };
   }

   public static double romberg(Function var0, double var1, double var3, double var5) {
      byte var7 = 6;
      double[] var8 = new double[var7];
      var8[0] = (var0.evaluate(var1 + var3) - var0.evaluate(var1 - var3)) / var3 / 2.0D;
      byte var9 = 1;

      for(int var10 = 1; var10 <= var7 - 1; ++var10) {
         var8[var10] = 0.0D;
         double var11 = var8[0];
         double var13 = var3;
         var3 *= 0.5D;
         if (var3 < Util.defaultNumericalPrecision) {
            var9 = 2;
            break;
         }

         var8[0] = (var0.evaluate(var1 + var3) - var0.evaluate(var1 - var3)) / var13;
         int var15 = 4;

         for(int var16 = 1; var16 <= var10; var15 *= 4) {
            double var17 = var8[var16];
            var8[var16] = ((double)var15 * var8[var16 - 1] - var11) / (double)(var15 - 1);
            var11 = var17;
            ++var16;
         }

         if (Math.abs(var8[var10] - var8[var10 - 1]) < var5) {
            return var8[var10];
         }
      }

      throw new NumericMethodException("Derivative did not converge.", var9, var8[0]);
   }

   public static double first(Function var0, double var1, double var3) {
      return (var0.evaluate(var1 + var3) - var0.evaluate(var1 - var3)) / var3 / 2.0D;
   }

   public static double centered(Function var0, double var1, double var3) {
      return (var0.evaluate(var1 + var3) - var0.evaluate(var1 - var3)) / var3 / 2.0D;
   }

   public static double backward(Function var0, double var1, double var3) {
      return (var0.evaluate(var1 - 2.0D * var3) - 4.0D * var0.evaluate(var1 - var3) + 3.0D * var0.evaluate(var1)) / var3 / 2.0D;
   }

   public static double forward(Function var0, double var1, double var3) {
      return (-var0.evaluate(var1 + 2.0D * var3) + 4.0D * var0.evaluate(var1 + var3) - 3.0D * var0.evaluate(var1)) / var3 / 2.0D;
   }

   public static double firstPartial(MultiVarFunction var0, double[] var1, int var2, double var3) {
      double[] var5 = new double[var1.length];
      System.arraycopy(var1, 0, var5, 0, var1.length);
      var5[var2] += var3;
      double[] var6 = new double[var1.length];
      System.arraycopy(var1, 0, var6, 0, var1.length);
      var6[var2] -= var3;
      return (var0.evaluate(var5) - var0.evaluate(var6)) / 2.0D / var3;
   }

   public static double second(Function var0, double var1, double var3) {
      return (var0.evaluate(var1 + var3) - 2.0D * var0.evaluate(var1) + var0.evaluate(var1 - var3)) / var3 / var3;
   }
}
