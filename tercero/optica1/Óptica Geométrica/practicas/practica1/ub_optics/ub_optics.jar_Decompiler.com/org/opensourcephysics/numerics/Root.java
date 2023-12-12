package org.opensourcephysics.numerics;

public class Root {
   static final int MAX_ITERATIONS = 15;

   private Root() {
   }

   public static double[] quadraticReal(double var0, double var2, double var4) {
      double[] var6 = new double[2];
      double var7 = -0.5D * (var2 + (var2 < 0.0D ? -1.0D : 1.0D) * Math.sqrt(var2 * var2 - 4.0D * var0 * var4));
      var6[0] = var7 / var0;
      var6[1] = var4 / var7;
      return var6;
   }

   public static double[][] quadratic(double var0, double var2, double var4) {
      double[][] var6 = new double[2][2];
      double var7 = var2 * var2 - 4.0D * var0 * var4;
      if (var7 < 0.0D) {
         var6[1][0] = var6[0][0] = -var2 / 2.0D / var0;
         var6[1][1] -= var6[0][1] = Math.sqrt(-var7) / 2.0D / var0;
         return var6;
      } else {
         double var9 = -0.5D * (var2 + (var2 < 0.0D ? -1.0D : 1.0D) * Math.sqrt(var7));
         var6[0][0] = var9 / var0;
         var6[1][0] = var4 / var9;
         return var6;
      }
   }

   public static double[][] cubic(double var0, double var2, double var4, double var6) {
      double[][] var8 = new double[3][2];
      double var9 = var2 / var0;
      double var11 = var4 / var0;
      double var13 = var6 / var0;
      double var15 = var9 * var9;
      double var17 = (3.0D * var11 - var15) / 9.0D;
      double var19 = (9.0D * var9 * var11 - 27.0D * var13 - 2.0D * var9 * var15) / 54.0D;
      double var21 = var17 * var17 * var17 + var19 * var19;
      double var23;
      if (var21 == 0.0D) {
         var23 = var19 < 0.0D ? -Math.pow(-var19, 0.3333333333333333D) : Math.pow(var19, 0.3333333333333333D);
         var8[0][0] = -var9 / 3.0D + 2.0D * var23;
         var8[2][0] = var8[1][0] = -var9 / 3.0D - var23;
      } else if (var21 > 0.0D) {
         var21 = Math.sqrt(var21);
         var23 = var19 + var21 < 0.0D ? -Math.pow(-var19 - var21, 0.3333333333333333D) : Math.pow(var19 + var21, 0.3333333333333333D);
         double var25 = var19 - var21 < 0.0D ? -Math.pow(-var19 + var21, 0.3333333333333333D) : Math.pow(var19 - var21, 0.3333333333333333D);
         var8[0][0] = -var9 / 3.0D + var23 + var25;
         var8[2][0] = var8[1][0] = -var9 / 3.0D - (var23 + var25) / 2.0D;
         var8[2][1] -= var8[1][1] = Math.sqrt(3.0D) * (var23 - var25) / 2.0D;
      } else {
         var17 = -var17;
         var23 = Math.acos(var19 / Math.sqrt(var17 * var17 * var17)) / 3.0D;
         var17 = 2.0D * Math.sqrt(var17);
         var9 /= 3.0D;
         var8[0][0] = var17 * Math.cos(var23) - var9;
         var8[1][0] = var17 * Math.cos(var23 + 2.0943951023931953D) - var9;
         var8[2][0] = var17 * Math.cos(var23 + 4.1887902047863905D) - var9;
      }

      return var8;
   }

   public static double newton(Function var0, double var1, double var3) {
      for(int var5 = 0; var5 < 15; ++var5) {
         double var6 = var1;
         double var8 = 0.0D;

         try {
            var8 = Derivative.romberg(var0, var1, Math.max(0.001D, 0.001D * Math.abs(var1)), var3 / 10.0D);
         } catch (NumericMethodException var11) {
            return Double.NaN;
         }

         var1 -= var0.evaluate(var1) / var8;
         if (Util.relativePrecision(Math.abs(var1 - var6), var1) < var3) {
            return var1;
         }
      }

      return Double.NaN;
   }

   public static double newton(Function var0, Function var1, double var2, double var4) {
      for(int var6 = 0; var6 < 15; ++var6) {
         double var7 = var2;
         var2 -= var0.evaluate(var2) / var1.evaluate(var2);
         if (Util.relativePrecision(Math.abs(var2 - var7), var2) < var4) {
            return var2;
         }
      }

      return Double.NaN;
   }

   public static double bisection(Function var0, double var1, double var3, double var5) {
      int var7 = 0;
      int var8 = (int)(Math.log(Math.abs(var3 - var1) / var5) / Math.log(2.0D));
      var8 = Math.max(15, var8) + 2;
      double var9 = var0.evaluate(var1);
      double var11 = var0.evaluate(var3);
      if (var9 * var11 > 0.0D) {
         return Double.NaN;
      } else {
         for(; var7 < var8; ++var7) {
            double var13 = (var1 + var3) / 2.0D;
            double var15 = var0.evaluate(var13);
            if (Util.relativePrecision(Math.abs(var1 - var3), var13) < var5) {
               return var13;
            }

            if (var15 * var9 > 0.0D) {
               var1 = var13;
               var9 = var15;
            } else {
               var3 = var13;
            }
         }

         return Double.NaN;
      }
   }
}
