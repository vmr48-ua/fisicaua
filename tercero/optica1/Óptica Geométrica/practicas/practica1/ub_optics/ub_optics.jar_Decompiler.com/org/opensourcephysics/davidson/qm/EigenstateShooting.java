package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.numerics.Function;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK45MultiStep;

public class EigenstateShooting implements ODE {
   double tol = 0.01D;
   double energy;
   double[] psi;
   double[] x;
   double maxAmp;
   double xmin;
   double xmax;
   int istart;
   int istop;
   double dx;
   double[] state = new double[3];
   ODESolver solver = new RK45MultiStep(this);
   Function pot;

   public EigenstateShooting(Function var1, int var2, double var3, double var5) {
      this.pot = var1;
      this.psi = new double[var2];
      this.x = new double[var2];
      this.dx = (var5 - var3) / (double)(var2 - 1);
      this.solver.setStepSize(this.dx);
      this.xmin = var3;
      this.xmax = var5;
      this.istart = 0;
      this.istop = var2;
   }

   private void estimateStartStopIndex(double var1) {
      double var3 = this.xmin;
      int var5 = 0;

      for(int var6 = this.psi.length; var5 < var6; ++var5) {
         if (this.pot.evaluate(var3) - var1 < 0.0D) {
            this.istart = var5;
            break;
         }

         var3 += this.dx;
      }

      var3 = this.xmax;

      for(var5 = this.psi.length; var5 > this.istart; --var5) {
         if (this.pot.evaluate(var3) - var1 < 0.0D) {
            this.istop = var5;
            break;
         }

         var3 -= this.dx;
      }

      double var9 = 1.0D;

      double var7;
      for(var3 = this.xmin + (double)this.istart * this.dx; var9 > this.tol && this.istart > 0; var9 *= Math.exp(-var7 * this.dx)) {
         --this.istart;
         var3 -= this.dx;
         var7 = Math.sqrt(2.0D * (this.pot.evaluate(var3) - var1));
      }

      var9 = 1.0D;

      for(var3 = this.xmin + (double)this.istop * this.dx; var9 > this.tol && this.istop < this.psi.length; var9 *= Math.exp(-var7 * this.dx)) {
         ++this.istop;
         var3 += this.dx;
         var7 = Math.sqrt(2.0D * (this.pot.evaluate(var3) - var1));
      }

   }

   int solve(double var1) {
      this.estimateStartStopIndex(var1);
      if (this.istop - this.istart < 3) {
         return 0;
      } else {
         this.energy = var1;
         this.state[0] = 0.0D;
         this.state[1] = 1.0D;
         this.state[2] = this.xmin + (double)this.istart * this.dx;
         int var3 = 0;
         boolean var4 = false;
         this.maxAmp = 0.0D;
         double var5 = 0.0D;
         double[] var7 = new double[3];

         int var8;
         for(var8 = 0; var8 < this.istart; ++var8) {
            this.psi[var8] = 0.0D;
            this.x[var8] = this.xmin + (double)var8 * this.dx;
         }

         int var9;
         label77:
         for(var8 = this.istart; var8 < this.istop; ++var8) {
            this.psi[var8] = this.state[0];
            this.x[var8] = this.state[2];
            var5 += this.state[0] * this.state[0];
            System.arraycopy(this.state, 0, var7, 0, 3);
            this.solver.step();
            if (this.maxAmp < Math.abs(this.state[0])) {
               this.maxAmp = Math.abs(this.state[0]);
            }

            if (this.state[1] <= 0.0D && var7[1] > 0.0D || this.state[1] >= 0.0D && var7[1] < 0.0D) {
               var4 = true;
            }

            if (this.state[0] <= 0.0D && var7[0] > 0.0D) {
               ++var3;
               var4 = false;
            } else if (this.state[0] >= 0.0D && var7[0] < 0.0D) {
               ++var3;
               var4 = false;
            }

            if (Math.abs(this.state[0]) > 1.0E9D) {
               var9 = var8 + 1;

               while(true) {
                  if (var9 >= this.istop) {
                     break label77;
                  }

                  this.psi[var9] = this.state[0];
                  double[] var10000 = this.x;
                  var10000[var8] += this.solver.getStepSize();
                  var5 += this.state[0] * this.state[0];
                  ++var9;
               }
            }
         }

         var8 = this.istop;

         for(var9 = this.psi.length; var8 < var9; ++var8) {
            this.psi[var8] = this.psi[this.istop - 1];
            this.x[var8] = this.xmin + (double)var8 * this.dx;
            var5 += this.psi[var8] * this.psi[var8];
         }

         if (var4 && Math.abs(this.psi[this.psi.length - 1]) <= this.tol * this.maxAmp) {
            ++var3;
         }

         this.rescale(Math.sqrt(var5 * this.dx));
         return var3;
      }
   }

   boolean calcEigenfunction(int var1, double var2, double var4) {
      int var6 = 0;
      double var7 = 0.0D;

      do {
         var7 = Math.abs(var4) + Math.abs(var2) < this.tol ? var4 - var2 : (var4 - var2) / (Math.abs(var4) + Math.abs(var2));
         double var9 = (var4 + var2) / 2.0D;
         int var11 = this.solve(var9);
         if (var11 == var1 && Math.abs(this.psi[this.psi.length - 1]) <= this.tol * this.maxAmp) {
            return true;
         }

         if (var11 == var1 && var7 < 1.0E-6D) {
            return true;
         }

         if (var11 <= var1 && (var11 != var1 || !((double)this.parity(var11) * this.psi[this.psi.length - 1] > 0.0D))) {
            var2 = var9;
         } else {
            var4 = var9;
         }

         ++var6;
      } while(var6 < 32 && var7 > 1.0E-10D);

      return false;
   }

   private int parity(int var1) {
      return var1 % 2 == 0 ? 1 : -1;
   }

   private void rescale(double var1) {
      if (var1 != 0.0D) {
         int var3 = 0;

         for(int var4 = this.psi.length; var3 < var4; ++var3) {
            double[] var10000 = this.psi;
            var10000[var3] /= var1;
            var10000 = this.state;
            var10000[0] /= var1;
            var10000 = this.state;
            var10000[1] /= var1;
         }

         this.maxAmp /= var1;
      }
   }

   public double[] getState() {
      return this.state;
   }

   public void getRate(double[] var1, double[] var2) {
      var2[0] = var1[1];
      var2[1] = (-this.energy + this.pot.evaluate(var1[2])) * var1[0];
      var2[2] = 1.0D;
   }
}
