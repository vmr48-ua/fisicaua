package org.opensourcephysics.numerics;

public class LUPDecomposition {
   private double[][] rows;
   private int[] permutation = null;
   private int parity = 1;

   public LUPDecomposition(double[][] var1) throws IllegalArgumentException {
      int var2 = var1.length;
      if (var1[0].length != var2) {
         throw new IllegalArgumentException("Illegal system: a" + var2 + " by " + var1[0].length + " matrix is not a square matrix");
      } else {
         this.initialize(var1);
      }
   }

   private double[] backwardSubstitution(double[] var1) {
      int var2 = this.rows.length;
      double[] var3 = new double[var2];

      for(int var4 = var2 - 1; var4 >= 0; --var4) {
         var3[var4] = var1[var4];

         for(int var5 = var4 + 1; var5 < var2; ++var5) {
            var3[var4] -= this.rows[var4][var5] * var3[var5];
         }

         var3[var4] /= this.rows[var4][var4];
      }

      return var3;
   }

   private void decompose() {
      int var1 = this.rows.length;
      this.permutation = new int[var1];

      int var2;
      for(var2 = 0; var2 < var1; this.permutation[var2] = var2++) {
      }

      this.parity = 1;

      try {
         for(var2 = 0; var2 < var1; ++var2) {
            this.swapRows(var2, this.largestPivot(var2));
            this.pivot(var2);
         }
      } catch (ArithmeticException var3) {
         this.parity = 0;
      }

   }

   private boolean decomposed() {
      if (this.parity == 1 && this.permutation == null) {
         this.decompose();
      }

      return this.parity != 0;
   }

   public double determinant() {
      if (!this.decomposed()) {
         return Double.NaN;
      } else {
         double var1 = (double)this.parity;

         for(int var3 = 0; var3 < this.rows.length; ++var3) {
            var1 *= this.rows[var3][var3];
         }

         return var1;
      }
   }

   private double[] forwardSubstitution(double[] var1) {
      int var2 = this.rows.length;
      double[] var3 = new double[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = var1[this.permutation[var4]];

         for(int var5 = 0; var5 <= var4 - 1; ++var5) {
            var3[var4] -= this.rows[var4][var5] * var3[var5];
         }
      }

      return var3;
   }

   private void initialize(double[][] var1) {
      int var2 = var1.length;
      this.rows = new double[var2][var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         System.arraycopy(var1[var3], 0, this.rows[var3], 0, var2);
      }

      this.permutation = null;
      this.parity = 1;
   }

   public double[][] inverseMatrixComponents() {
      if (!this.decomposed()) {
         return (double[][])null;
      } else {
         int var1 = this.rows.length;
         double[][] var2 = new double[var1][var1];
         double[] var3 = new double[var1];

         for(int var4 = 0; var4 < var1; ++var4) {
            int var5;
            for(var5 = 0; var5 < var1; ++var5) {
               var3[var5] = 0.0D;
            }

            var3[var4] = 1.0D;
            var3 = this.solve(var3);

            for(var5 = 0; var5 < var1; ++var5) {
               if (Double.isNaN(var3[var5])) {
                  return (double[][])null;
               }

               var2[var5][var4] = var3[var5];
            }
         }

         return var2;
      }
   }

   public static void symmetrizeComponents(double[][] var0) {
      for(int var1 = 0; var1 < var0.length; ++var1) {
         for(int var2 = var1 + 1; var2 < var0.length; ++var2) {
            var0[var1][var2] += var0[var2][var1];
            var0[var1][var2] *= 0.5D;
            var0[var2][var1] = var0[var1][var2];
         }
      }

   }

   private int largestPivot(int var1) {
      double var2 = Math.abs(this.rows[var1][var1]);
      int var6 = var1;

      for(int var7 = var1 + 1; var7 < this.rows.length; ++var7) {
         double var4 = Math.abs(this.rows[var7][var1]);
         if (var4 > var2) {
            var2 = var4;
            var6 = var7;
         }
      }

      return var6;
   }

   private void pivot(int var1) {
      double var2 = 1.0D / this.rows[var1][var1];
      int var4 = var1 + 1;
      int var5 = this.rows.length;

      for(int var6 = var4; var6 < var5; ++var6) {
         double[] var10000 = this.rows[var6];
         var10000[var1] *= var2;

         for(int var7 = var4; var7 < var5; ++var7) {
            var10000 = this.rows[var6];
            var10000[var7] -= this.rows[var6][var1] * this.rows[var1][var7];
         }
      }

   }

   public double[] solve(double[] var1) {
      return this.decomposed() ? this.backwardSubstitution(this.forwardSubstitution(var1)) : null;
   }

   private void swapRows(int var1, int var2) {
      if (var1 != var2) {
         int var5;
         for(var5 = 0; var5 < this.rows.length; ++var5) {
            double var3 = this.rows[var1][var5];
            this.rows[var1][var5] = this.rows[var2][var5];
            this.rows[var2][var5] = var3;
         }

         var5 = this.permutation[var1];
         this.permutation[var1] = this.permutation[var2];
         this.permutation[var2] = var5;
         this.parity = -this.parity;
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      char[] var2 = new char[]{'[', ' '};
      int var3 = this.rows.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         var2[0] = '{';

         for(int var5 = 0; var5 < var3; ++var5) {
            var1.append(var2);
            var1.append(this.rows[var4][var5]);
            var2[0] = ' ';
         }

         var1.append('}');
         var1.append('\n');
      }

      if (this.permutation != null) {
         var1.append((char)(this.parity == 1 ? '+' : '-'));
         var1.append("( " + this.permutation[0]);

         for(var4 = 1; var4 < var3; ++var4) {
            var1.append(", " + this.permutation[var4]);
         }

         var1.append(')');
         var1.append('\n');
      }

      return var1.toString();
   }
}
