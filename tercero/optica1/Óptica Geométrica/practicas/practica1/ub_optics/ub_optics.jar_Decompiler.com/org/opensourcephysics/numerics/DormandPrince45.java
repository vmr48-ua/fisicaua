package org.opensourcephysics.numerics;

public class DormandPrince45 implements ODEAdaptiveSolver {
   int error_code = 0;
   static final double[][] a = new double[][]{{0.2D}, {0.075D, 0.225D}, {0.3D, -0.9D, 1.2D}, {0.3100137174211248D, -0.9259259259259259D, 1.2071330589849107D, 0.07544581618655692D}, {-0.6703703703703704D, 2.5D, -0.8956228956228957D, -3.3703703703703702D, 3.4363636363636365D}};
   static final double[] b5 = new double[]{0.08796296296296297D, 0.0D, 0.481000481000481D, -0.5787037037037037D, 0.9204545454545454D, 0.08928571428571429D};
   static final double[] er = new double[]{-0.030555555555555555D, 0.0D, 0.15873015873015872D, -0.7638888888888888D, 0.675D, -0.039285714285714285D};
   static final int numStages = 6;
   private volatile double stepSize = 0.01D;
   private int numEqn = 0;
   private double[] temp_state;
   private double[][] k;
   private double truncErr;
   private ODE ode;
   protected double tol = 1.0E-6D;
   protected boolean enableExceptions = false;

   public DormandPrince45(ODE var1) {
      this.ode = var1;
      this.initialize(this.stepSize);
   }

   public void initialize(double var1) {
      this.stepSize = var1;
      double[] var3 = this.ode.getState();
      if (var3 != null) {
         if (this.numEqn != var3.length) {
            this.numEqn = var3.length;
            this.temp_state = new double[this.numEqn];
            this.k = new double[6][this.numEqn];
         }

      }
   }

   public double step() {
      this.error_code = 0;
      int var1 = 10;
      double var2 = this.stepSize;
      double var4 = 0.0D;
      double[] var6 = this.ode.getState();
      this.ode.getRate(var6, this.k[0]);

      int var7;
      int var8;
      do {
         --var1;
         var2 = this.stepSize;

         for(var7 = 1; var7 < 6; ++var7) {
            for(var8 = 0; var8 < this.numEqn; ++var8) {
               this.temp_state[var8] = var6[var8];

               for(int var9 = 0; var9 < var7; ++var9) {
                  this.temp_state[var8] += this.stepSize * a[var7 - 1][var9] * this.k[var9][var8];
               }
            }

            this.ode.getRate(this.temp_state, this.k[var7]);
         }

         var4 = 0.0D;

         for(var7 = 0; var7 < this.numEqn; ++var7) {
            this.truncErr = 0.0D;

            for(var8 = 0; var8 < 6; ++var8) {
               this.truncErr += this.stepSize * er[var8] * this.k[var8][var7];
            }

            var4 = Math.max(var4, Math.abs(this.truncErr));
         }

         if (var4 <= 1.401298464324817E-45D) {
            var4 = this.tol / 100000.0D;
         }

         double var10;
         if (var4 > this.tol) {
            var10 = 0.9D * Math.pow(var4 / this.tol, -0.25D);
            this.stepSize *= Math.max(var10, 0.1D);
         } else if (var4 < this.tol / 10.0D) {
            var10 = 0.9D * Math.pow(var4 / this.tol, -0.2D);
            if (var10 > 1.0D) {
               this.stepSize *= Math.min(var10, 10.0D);
            }
         }
      } while(var4 > this.tol && var1 > 0);

      for(var7 = 0; var7 < this.numEqn; ++var7) {
         for(var8 = 0; var8 < 6; ++var8) {
            var6[var7] += var2 * b5[var8] * this.k[var8][var7];
         }
      }

      if (var1 == 0) {
         this.error_code = 1;
         if (this.enableExceptions) {
            throw new ODESolverException("DormanPrince45 ODE solver did not converge.");
         }
      }

      return var2;
   }

   public void enableRuntimeExpecptions(boolean var1) {
      this.enableExceptions = var1;
   }

   public void setStepSize(double var1) {
      this.stepSize = var1;
   }

   public double getStepSize() {
      return this.stepSize;
   }

   public void setTolerance(double var1) {
      this.tol = Math.abs(var1);
   }

   public double getTolerance() {
      return this.tol;
   }

   public int getErrorCode() {
      return this.error_code;
   }
}
