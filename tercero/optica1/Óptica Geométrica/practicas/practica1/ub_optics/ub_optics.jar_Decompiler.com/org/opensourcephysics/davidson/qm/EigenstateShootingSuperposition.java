package org.opensourcephysics.davidson.qm;

import java.awt.Component;
import javax.swing.JOptionPane;
import org.opensourcephysics.display.ComplexDataset;
import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.numerics.Function;

public class EigenstateShootingSuperposition implements QMSuperposition {
   static final int MAX_STATES = 120;
   double[][] states = new double[120][];
   double[] vals = new double[120];
   double[] recoef = new double[120];
   double[] imcoef = new double[120];
   int numstates;
   double energyScale = 1.0D;
   EigenstateShooting qmsystem;
   double[] x;
   double[] rePsi;
   double[] imPsi;
   double[] rho;
   double[] zeroArray;

   public EigenstateShootingSuperposition(Function var1, int var2, double var3, double var5) {
      this.rePsi = new double[var2];
      this.imPsi = new double[var2];
      this.rho = new double[var2];
      this.zeroArray = new double[var2];
      this.x = new double[var2];
      this.qmsystem = new EigenstateShooting(var1, var2, var3, var5);
      double var7 = var3;
      int var9 = 0;

      for(int var10 = this.rePsi.length; var9 < var10; ++var9) {
         this.x[var9] = var7;
         var7 += this.qmsystem.dx;
      }

      this.setCoef((double[])null, (double[])null);
   }

   public void setEnergyScale(double var1) {
      this.energyScale = var1;
   }

   public double getEnergyScale() {
      return this.energyScale;
   }

   public double[] getReCoef() {
      return this.recoef;
   }

   public double[] getImCoef() {
      return this.imcoef;
   }

   public boolean setCoef(double[] var1, double[] var2) {
      if (var1 != null && var1.length > 120 || var2 != null && var2.length > 120) {
         JOptionPane.showMessageDialog((Component)null, "The number of engenstates cannot be larger than 120. You are obviously a theorist who needs help.", "Input Error.", 0);
      }

      if (var1 != null && var2 == null) {
         var2 = new double[var1.length];
      }

      if (var2 != null && var1 == null) {
         var1 = new double[var2.length];
      }

      if (var1 == null && var2 == null) {
         var1 = new double[1];
         var2 = new double[1];
      }

      double[] var3;
      if (var1.length < var2.length) {
         var3 = var1;
         var1 = new double[var2.length];
         System.arraycopy(var3, 0, var1, 0, var3.length);
      }

      if (var2.length < var1.length) {
         var3 = var2;
         var2 = new double[var1.length];
         System.arraycopy(var3, 0, var2, 0, var3.length);
      }

      boolean var11 = true;
      double var4 = Double.MAX_VALUE;
      int var6 = 0;

      for(int var7 = this.x.length; var6 < var7; ++var6) {
         var4 = Math.min(var4, this.qmsystem.pot.evaluate(this.x[var6]));
      }

      this.numstates = Math.min(var1.length, this.recoef.length);
      System.arraycopy(var1, 0, this.recoef, 0, this.numstates);
      System.arraycopy(var2, 0, this.imcoef, 0, this.numstates);

      for(var6 = 0; var6 < this.numstates; ++var6) {
         if (this.states[var6] == null) {
            double var8;
            int var10;
            boolean var12;
            if (var6 > 0) {
               var8 = this.vals[var6 - 1] + (double)var6;

               for(var10 = 0; var10 < 32 && this.qmsystem.solve(var8) < var6 + 1; ++var10) {
                  var8 += var8 - this.vals[var6 - 1];
               }

               var12 = this.qmsystem.calcEigenfunction(var6 + 1, this.vals[var6 - 1], var8);
            } else {
               var8 = var4 + 1.0D;

               for(var10 = 0; var10 < 32 && this.qmsystem.solve(var8) < 1; ++var10) {
                  var8 += var8 - var4;
               }

               var12 = this.qmsystem.calcEigenfunction(var6 + 1, var4, var8);
            }

            if (var12) {
               this.vals[var6] = this.qmsystem.energy;
               this.states[var6] = (double[])((double[])this.qmsystem.psi.clone());
            } else {
               System.out.println("state did not converge. n=" + var6);
               var11 = false;
            }
         }
      }

      return var11;
   }

   public double getEigenValue(int var1) {
      return this.vals[var1] * this.energyScale;
   }

   public void update(double var1) {
      System.arraycopy(this.zeroArray, 0, this.rePsi, 0, this.rePsi.length);
      System.arraycopy(this.zeroArray, 0, this.imPsi, 0, this.imPsi.length);

      for(int var3 = 0; var3 < this.numstates; ++var3) {
         double[] var4 = this.states[var3];
         if (var4 != null) {
            double var5 = this.recoef[var3];
            double var7 = this.imcoef[var3];
            double var9 = -var1 * this.vals[var3] * this.energyScale;
            double var11 = Math.sin(var9);
            double var13 = Math.cos(var9);
            int var15 = 0;

            for(int var16 = this.rePsi.length; var15 < var16; ++var15) {
               double[] var10000 = this.rePsi;
               var10000[var15] += (var5 * var13 - var7 * var11) * var4[var15];
               var10000 = this.imPsi;
               var10000[var15] += (var7 * var13 + var5 * var11) * var4[var15];
            }
         }
      }

      this.rePsi[0] = this.rePsi[this.rePsi.length - 1] = 0.0D;
      this.imPsi[0] = this.imPsi[this.imPsi.length - 1] = 0.0D;
   }

   public Dataset getRho(Dataset var1) {
      if (var1 == null) {
         var1 = new Dataset();
      } else {
         var1.clear();
      }

      int var2 = 0;

      for(int var3 = this.rePsi.length; var2 < var3; ++var2) {
         this.rho[var2] = this.rePsi[var2] * this.rePsi[var2] + this.imPsi[var2] * this.imPsi[var2];
      }

      var1.append(this.x, this.rho);
      return var1;
   }

   public int getNumpts() {
      return this.x.length;
   }

   public double getXMin() {
      return this.x[0];
   }

   public double getXMax() {
      return this.x[this.x.length - 1];
   }

   public double[] getRePsi() {
      return this.rePsi;
   }

   public double[] getImPsi() {
      return this.imPsi;
   }

   public double[] getX() {
      return this.x;
   }

   public double[][] getEigenstates() {
      return this.states;
   }

   public ComplexDataset getPsi(ComplexDataset var1) {
      if (var1 == null) {
         var1 = new ComplexDataset();
      } else {
         var1.clear();
      }

      var1.append(this.x, this.rePsi, this.imPsi);
      return var1;
   }
}
