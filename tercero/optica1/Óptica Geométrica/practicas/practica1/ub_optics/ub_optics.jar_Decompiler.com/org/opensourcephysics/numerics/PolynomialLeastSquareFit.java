package org.opensourcephysics.numerics;

public class PolynomialLeastSquareFit extends Polynomial {
   double[][] systemMatrix;
   double[] systemConstants;

   public PolynomialLeastSquareFit(double[] var1, double[] var2, int var3) {
      super(new double[var3 + 1]);
      int var4 = var3 + 1;
      this.systemMatrix = new double[var4][var4];
      this.systemConstants = new double[var4];
      this.fitData(var1, var2);
   }

   public PolynomialLeastSquareFit(double[] var1) {
      super(var1);
      int var2 = var1.length;
      this.systemMatrix = new double[var2][var2];
      this.systemConstants = new double[var2];
   }

   public void fitData(double[] var1, double[] var2) {
      if (var1.length != var2.length) {
         throw new IllegalArgumentException("Arrays must be of equal length.");
      } else if (var1.length >= this.degree() + 1) {
         int var3;
         int var4;
         for(var3 = 0; var3 < this.systemConstants.length; ++var3) {
            this.systemConstants[var3] = 0.0D;

            for(var4 = 0; var4 < this.systemConstants.length; ++var4) {
               this.systemMatrix[var3][var4] = 0.0D;
            }
         }

         var3 = 0;

         for(var4 = var1.length; var3 < var4; ++var3) {
            double var5 = 1.0D;

            for(int var7 = 0; var7 < this.systemConstants.length; ++var7) {
               double[] var10000 = this.systemConstants;
               var10000[var7] += var5 * var2[var3];
               double var8 = var5;

               for(int var10 = 0; var10 <= var7; ++var10) {
                  var10000 = this.systemMatrix[var7];
                  var10000[var10] += var8;
                  var8 *= var1[var3];
               }

               var5 *= var1[var3];
            }
         }

         this.computeCoefficients();
      }
   }

   protected void computeCoefficients() {
      for(int var1 = 0; var1 < this.systemConstants.length; ++var1) {
         for(int var2 = var1 + 1; var2 < this.systemConstants.length; ++var2) {
            this.systemMatrix[var1][var2] = this.systemMatrix[var2][var1];
         }
      }

      LUPDecomposition var3 = new LUPDecomposition(this.systemMatrix);
      double[][] var4 = var3.inverseMatrixComponents();
      LUPDecomposition.symmetrizeComponents(var4);
      super.coefficients = var3.solve(this.systemConstants);
   }
}
