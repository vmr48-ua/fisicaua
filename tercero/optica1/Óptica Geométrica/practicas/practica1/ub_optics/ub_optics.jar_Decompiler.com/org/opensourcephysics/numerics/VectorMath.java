package org.opensourcephysics.numerics;

public final class VectorMath {
   private VectorMath() {
   }

   public static double[] plus(double[] var0, double[] var1, double var2) {
      int var4 = var0.length;
      if (var4 != var1.length) {
         throw new UnsupportedOperationException("ERROR: Vectors must be of equal length to add.");
      } else {
         for(int var5 = 0; var5 < var4; ++var5) {
            var0[var5] += var2 * var1[var5];
         }

         return var0;
      }
   }

   public static double[] plus(double[] var0, double[] var1) {
      int var2 = var0.length;
      if (var2 != var1.length) {
         throw new UnsupportedOperationException("ERROR: Vectors must be of equal length to add.");
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            var0[var3] += var1[var3];
         }

         return var0;
      }
   }

   public static double[] normalize(double[] var0) {
      double var1 = magnitude(var0);
      if (var1 == 0.0D) {
         var0[0] = 1.0D;
         return var0;
      } else {
         var0[0] /= var1;
         var0[1] /= var1;
         var0[2] /= var1;
         return var0;
      }
   }

   public static double dot(double[] var0, double[] var1) {
      int var2 = var0.length;
      if (var2 != var1.length) {
         throw new UnsupportedOperationException("ERROR: Vectors must be of equal dimension in dot product.");
      } else {
         double var3 = 0.0D;

         for(int var5 = 0; var5 < var2; ++var5) {
            var3 += var0[var5] * var1[var5];
         }

         return var3;
      }
   }

   public static double[] project(double[] var0, double[] var1) {
      int var2 = var0.length;
      if (var2 != var1.length) {
         throw new UnsupportedOperationException("ERROR: Vectors must be of equal dimension to compute projection.");
      } else {
         double[] var3 = (double[])((double[])var1.clone());
         double var4 = 0.0D;
         double var6 = 0.0D;

         int var8;
         for(var8 = 0; var8 < var2; ++var8) {
            var6 += var0[var8] * var1[var8];
            var4 += var0[var8] * var0[var8];
         }

         var6 /= var4;

         for(var8 = 0; var8 < var2; ++var8) {
            var3[var8] /= var6;
         }

         return var3;
      }
   }

   public static double[] perp(double[] var0, double[] var1) {
      int var2 = var0.length;
      if (var2 != var1.length) {
         throw new UnsupportedOperationException("ERROR: Vectors must be of equal dimension to find the perpendicular component.");
      } else {
         double[] var3 = (double[])((double[])var1.clone());
         double var4 = 0.0D;
         double var6 = 0.0D;

         int var8;
         for(var8 = 0; var8 < var2; ++var8) {
            var6 += var0[var8] * var1[var8];
            var4 += var0[var8] * var0[var8];
         }

         var6 /= var4;

         for(var8 = 0; var8 < var2; ++var8) {
            var3[var8] = var0[var8] - var1[var8] / var6;
         }

         return var3;
      }
   }

   public static double magnitudeSquared(double[] var0) {
      int var1 = var0.length;
      double var2 = 0.0D;

      for(int var4 = 0; var4 < var1; ++var4) {
         var2 += var0[var4] * var0[var4];
      }

      return var2;
   }

   public static double magnitude(double[] var0) {
      double var1 = 0.0D;
      int var3 = 0;

      for(int var4 = var0.length; var3 < var4; ++var3) {
         var1 += var0[var3] * var0[var3];
      }

      return Math.sqrt(var1);
   }

   public static final double[] cross3D(double[] var0, double[] var1) {
      double[] var2 = new double[]{var0[1] * var1[2] - var0[2] * var1[1], var1[0] * var0[2] - var1[2] * var0[0], var0[0] * var1[1] - var0[1] * var1[0]};
      return var2;
   }

   public static double[] cross2D(double[] var0, double var1) {
      if (var0.length != 2) {
         throw new UnsupportedOperationException("ERROR: Cross2D product requires 2 component array.");
      } else {
         double var3 = var0[0];
         var0[0] = var0[1] * var1;
         var0[1] = -var3 * var1;
         return var0;
      }
   }

   public static double cross2D(double[] var0, double[] var1) {
      if (var0.length == 2 && var1.length == 2) {
         return var0[0] * var1[1] - var0[1] * var1[0];
      } else {
         throw new UnsupportedOperationException("ERROR: Cross2D product requires 2 component arrays.");
      }
   }
}
