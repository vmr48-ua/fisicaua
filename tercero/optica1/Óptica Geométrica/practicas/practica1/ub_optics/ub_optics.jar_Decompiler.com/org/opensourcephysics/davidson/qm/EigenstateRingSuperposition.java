package org.opensourcephysics.davidson.qm;

import org.opensourcephysics.display.ComplexDataset;
import org.opensourcephysics.display.Dataset;

public class EigenstateRingSuperposition implements QMSuperposition {
   static final double PISQR = 9.869604401089358D;
   double[] recoef = new double[0];
   double[] imcoef = new double[0];
   double[][] eigenstates;
   double L;
   double[] x;
   double[] rePsi;
   double[] imPsi;
   double[] rho;
   double[] zeroArray;
   double energyScale = 1.0D;

   public EigenstateRingSuperposition(int var1, double var2, double var4) {
      this.L = Math.abs(var4 - var2);
      this.rePsi = new double[var1];
      this.imPsi = new double[var1];
      this.rho = new double[var1];
      this.x = new double[var1];
      this.zeroArray = new double[var1];
      double var6 = var2;
      double var8 = (var4 - var2) / (double)(var1 - 1);
      int var10 = 0;

      for(int var11 = var1; var10 < var11; ++var10) {
         this.x[var10] = var6;
         var6 += var8;
      }

      this.eigenstates = new double[0][var1];
      this.setCoef(new double[0], new double[0]);
   }

   public Dataset getRho(Dataset var1) {
      if (var1 == null) {
         var1 = new Dataset();
      } else {
         var1.clear();
      }

      int var2 = 0;

      for(int var3 = this.x.length; var2 < var3; ++var2) {
         this.rho[var2] = this.rePsi[var2] * this.rePsi[var2] + this.imPsi[var2] * this.imPsi[var2];
      }

      var1.append(this.x[0], 0.0D);
      var1.append(this.x, this.rho);
      var1.append(this.x[this.x.length - 1], 0.0D);
      return var1;
   }

   public double[][] getEigenstates() {
      return this.eigenstates;
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

      int var17 = var1.length + 1;
      var17 += var17 % 2;
      int var4 = this.x.length;
      this.recoef = new double[var17];
      this.imcoef = new double[var17];
      System.arraycopy(var1, 0, this.recoef, 1, var1.length);
      System.arraycopy(var2, 0, this.imcoef, 1, var2.length);
      this.eigenstates = new double[2 * var17][var4];
      double var5 = Math.sqrt(1.0D / this.L);

      for(int var7 = 0; var7 < var17; var7 += 2) {
         double[] var8 = this.eigenstates[2 * var7];
         double[] var9 = this.eigenstates[2 * var7 + 1];
         double[] var10 = this.eigenstates[2 * var7 + 2];
         double[] var11 = this.eigenstates[2 * var7 + 3];
         double var12 = (double)(var7 / 2 * 2) * 3.141592653589793D / this.L;

         for(int var14 = 0; var14 < var4; ++var14) {
            double var15 = var12 * this.x[var14];
            var8[var14] = var5 * Math.cos(var15);
            var9[var14] = var5 * Math.sin(var15);
            var10[var14] = var7 == 0 ? 0.0D : var5 * Math.cos(-var15);
            var11[var14] = var7 == 0 ? 0.0D : var5 * Math.sin(-var15);
         }
      }

      double[] var10000 = this.recoef;
      var10000[0] += this.recoef[1];
      var10000 = this.imcoef;
      var10000[0] += this.imcoef[1];
      this.recoef[1] = 0.0D;
      this.imcoef[1] = 0.0D;
      return true;
   }

   public double getEigenValue(int var1) {
      return (double)(var1 / 2 * (var1 / 2) * 4) * this.energyScale * 9.869604401089358D / this.L / this.L;
   }

   public void update(double var1) {
      System.arraycopy(this.zeroArray, 0, this.rePsi, 0, this.rePsi.length);
      System.arraycopy(this.zeroArray, 0, this.imPsi, 0, this.imPsi.length);
      if (this.eigenstates.length != 0) {
         double var3 = -4.0D * var1 * this.energyScale * 9.869604401089358D / this.L / this.L;
         int var5 = 0;

         for(int var6 = this.recoef.length; var5 < var6; ++var5) {
            double var7 = this.recoef[var5];
            double var9 = this.imcoef[var5];
            double var11 = var3 * (double)(var5 / 2) * (double)(var5 / 2);
            double var13 = Math.sin(var11);
            double var15 = Math.cos(var11);
            double[] var17 = this.eigenstates[2 * var5];
            double[] var18 = this.eigenstates[2 * var5 + 1];
            int var19 = 0;

            for(int var20 = this.x.length; var19 < var20; ++var19) {
               double var21 = var7 * var15 - var9 * var13;
               double var23 = var9 * var15 + var7 * var13;
               double[] var10000 = this.rePsi;
               var10000[var19] += var21 * var17[var19] - var23 * var18[var19];
               var10000 = this.imPsi;
               var10000[var19] += var23 * var17[var19] + var21 * var18[var19];
            }
         }

      }
   }
}
