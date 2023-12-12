package org.opensourcephysics.numerics;

import org.opensourcephysics.controls.ControlUtils;

public final class Util {
   static final double SQRT2PI = Math.sqrt(6.283185307179586D);
   public static final double defaultNumericalPrecision = Math.sqrt(Double.MIN_VALUE);
   private static SuryonoParser parser = new SuryonoParser(0);

   private Util() {
   }

   public static String f2(double var0) {
      return ControlUtils.f2(var0);
   }

   public static String f3(double var0) {
      return ControlUtils.f3(var0);
   }

   public static String f4(double var0) {
      return ControlUtils.f4(var0);
   }

   public static double relativePrecision(double var0, double var2) {
      return var2 > defaultNumericalPrecision ? var0 / var2 : var0;
   }

   public static int checkSorting(double[] var0) {
      int var1 = var0[0] <= var0[var0.length - 1] ? 1 : -1;
      int var2 = 1;

      for(int var3 = var0.length; var2 < var3; ++var2) {
         switch(var1) {
         case -1:
            if (var0[var2 - 1] < var0[var2]) {
               return 0;
            }
            break;
         case 1:
            if (var0[var2 - 1] > var0[var2]) {
               return 0;
            }
         }
      }

      return var1;
   }

   public static double[] getRange(Function var0, double var1, double var3, int var5) {
      double var6 = var0.evaluate(var1);
      double var8 = var0.evaluate(var1);
      double var10 = var1;
      double var12 = (var3 - var1) / (double)(var5 - 1);

      for(int var14 = 1; var14 < var5; ++var14) {
         double var15 = var0.evaluate(var10);
         var6 = Math.min(var6, var15);
         var8 = Math.max(var8, var15);
         var10 += var12;
      }

      return new double[]{var6, var8};
   }

   public static double[][] functionFill(Function var0, double var1, double var3, double[][] var5) {
      double var6 = 1.0D;
      int var8 = var5[0].length;
      if (var8 > 1) {
         var6 = (var3 - var1) / (double)(var8 - 1);
      }

      double var9 = var1;

      for(int var11 = 0; var11 < var8; ++var11) {
         var5[0][var11] = var9;
         var5[1][var11] = var0.evaluate(var9);
         var9 += var6;
      }

      return var5;
   }

   public static double[] functionFill(Function var0, double var1, double var3, double[] var5) {
      double var6 = 1.0D;
      int var8 = var5.length;
      if (var8 > 1) {
         var6 = (var3 - var1) / (double)(var8 - 1);
      }

      double var9 = var1;

      for(int var11 = 0; var11 < var8; ++var11) {
         var5[var11] = var0.evaluate(var9);
         var9 += var6;
      }

      return var5;
   }

   public static double computeAverage(double[] var0, int var1, int var2) {
      double var3 = 0.0D;
      int var5 = var1;

      for(int var6 = var1 + var2; var5 < var6; ++var5) {
         var3 += var0[var5];
      }

      return var3 / (double)var2;
   }

   public static Function constantFunction(final double var0) {
      return new Function() {
         public double evaluate(double var1) {
            return var0;
         }
      };
   }

   public static Function linearFunction(final double var0, final double var2) {
      return new Function() {
         public double evaluate(double var1) {
            return var0 * var1 + var2;
         }
      };
   }

   public static Function gaussian(final double var0, final double var2) {
      final double var4 = 2.0D * var2 * var2;
      return new Function() {
         public double evaluate(double var1) {
            return Math.exp(-(var1 - var0) * (var1 - var0) / var4) / var2 / Util.SQRT2PI;
         }
      };
   }

   public static synchronized double evalMath(String var0) {
      try {
         parser.parse(var0);
         return parser.evaluate();
      } catch (ParserException var2) {
         return Double.NaN;
      }
   }
}
