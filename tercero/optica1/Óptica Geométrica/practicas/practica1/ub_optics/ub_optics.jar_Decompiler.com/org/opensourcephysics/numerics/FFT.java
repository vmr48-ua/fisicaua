package org.opensourcephysics.numerics;

public class FFT {
   static final double PI = 3.141592653589793D;
   static final double PI2 = 6.283185307179586D;
   static final int FORWARD = -1;
   static final int BACKWARD = 1;
   int n;
   double[] scratch;
   double norm = 1.0D;
   private int[] factors;
   private double[][][] twiddle;
   private int[] available_factors = new int[]{7, 6, 5, 4, 3, 2};

   public FFT(int var1) {
      this.setN(var1);
   }

   public FFT() {
      this.setN(1);
   }

   public void setN(int var1) {
      if (var1 <= 0) {
         throw new IllegalArgumentException("The transform length must be >0 : " + var1);
      } else {
         this.n = var1;
         this.norm = (double)var1;
         this.scratch = new double[2 * var1];
         this.setup_wavetable(var1);
      }
   }

   public int getN() {
      return this.n;
   }

   public void setNormalization(double var1) {
      this.norm = var1;
   }

   public double getNormalization() {
      return this.norm;
   }

   public double[] transform(double[] var1) {
      if (var1.length != 2 * this.n) {
         if (var1.length % 2 != 0) {
            throw new IllegalArgumentException("Number of points in array is not even");
         }

         this.setN(var1.length / 2);
      }

      this.transform_internal(var1, 0, 2, -1);
      return var1;
   }

   public double[] backtransform(double[] var1) {
      if (var1.length != 2 * this.n) {
         if (var1.length % 2 != 0) {
            throw new IllegalArgumentException("Number of points in array is not even");
         }

         this.setN(var1.length / 2);
      }

      this.transform_internal(var1, 0, 2, 1);
      return var1;
   }

   public double[] inverse(double[] var1) {
      this.backtransform(var1);
      int var2 = 0;

      for(int var3 = 2 * this.n; var2 < var3; ++var2) {
         var1[var2] /= (double)this.n;
      }

      return var1;
   }

   public double[] toNaturalOrder(double[] var1) {
      System.arraycopy(var1, 0, this.scratch, 0, 2 * this.n);
      System.arraycopy(this.scratch, this.n + this.n % 2, var1, 0, this.n - this.n % 2);
      System.arraycopy(this.scratch, 0, var1, this.n - this.n % 2, this.n + this.n % 2);
      if (this.norm == 1.0D) {
         return var1;
      } else {
         int var2 = 0;

         for(int var3 = 2 * this.n; var2 < var3; ++var2) {
            var1[var2] /= this.norm;
         }

         return var1;
      }
   }

   public double[] toWrapAroundOrder(double[] var1) {
      System.arraycopy(var1, 0, this.scratch, 0, 2 * this.n);
      System.arraycopy(this.scratch, this.n + this.n % 2, var1, 0, this.n - this.n % 2);
      System.arraycopy(this.scratch, 0, var1, this.n - this.n % 2, this.n + this.n % 2);
      if (this.norm == 1.0D) {
         return var1;
      } else {
         int var2 = 0;

         for(int var3 = 2 * this.n; var2 < var3; ++var2) {
            var1[var2] *= (double)this.n;
         }

         return var1;
      }
   }

   public double[] getWrappedModes() {
      double[] var1 = new double[this.n];

      for(int var2 = 0; var2 < this.n; ++var2) {
         var1[var2] = var2 < (this.n + 1) / 2 ? (double)var2 : (double)(var2 - this.n);
      }

      return var1;
   }

   public double[] getWrappedOmega(double var1) {
      return this.getWrappedFreq(var1 / 6.283185307179586D);
   }

   public double[] getWrappedOmega(double var1, double var3) {
      return this.getWrappedFreq((var3 - var1) / (double)(this.n - this.n % 2) / 6.283185307179586D);
   }

   public double[] getWrappedFreq(double var1) {
      double[] var3 = new double[this.n];
      double var4 = -0.5D / var1;
      double var6 = -2.0D * var4 / (double)(this.n - this.n % 2);

      for(int var8 = 0; var8 < this.n; ++var8) {
         var3[var8] = var8 < (this.n + 1) / 2 ? (double)var8 * var6 : (double)(var8 - this.n) * var6;
      }

      return var3;
   }

   public double[] getWrappedFreq(double var1, double var3) {
      return this.getNaturalFreq((var3 - var1) / (double)(this.n - this.n % 2));
   }

   public double[] getNaturalFreq(double var1) {
      double[] var3 = new double[this.n];
      double var4 = -0.5D / var1;
      double var6 = -2.0D * var4 / (double)(this.n - this.n % 2);

      for(int var8 = 0; var8 < this.n; ++var8) {
         var3[var8] = var4;
         var4 += var6;
      }

      return var3;
   }

   public double[] getNaturalFreq(double var1, double var3) {
      return this.getNaturalFreq((var3 - var1) / (double)(this.n - this.n % 2));
   }

   public double[] getNaturalOmega(double var1) {
      return this.getNaturalFreq(var1 / 6.283185307179586D);
   }

   public double[] getNaturalOmega(double var1, double var3) {
      return this.getNaturalFreq((var3 - var1) / (double)(this.n - this.n % 2) / 6.283185307179586D);
   }

   public double[] getNaturalModes() {
      double[] var1 = new double[this.n];
      double var2 = (double)(-(this.n - this.n % 2) / 2);

      for(int var4 = 0; var4 < this.n; ++var4) {
         var1[var4] = var2++;
      }

      return var1;
   }

   private void setup_wavetable(int var1) {
      if (var1 <= 0) {
         throw new Error("length must be positive integer : " + var1);
      } else {
         this.n = var1;
         this.factors = factor(var1, this.available_factors);
         double var2 = -6.283185307179586D / (double)var1;
         int var4 = 1;
         this.twiddle = new double[this.factors.length][][];

         for(int var5 = 0; var5 < this.factors.length; ++var5) {
            int var6 = this.factors[var5];
            int var7 = var4;
            var4 *= var6;
            int var8 = var1 / var4;
            this.twiddle[var5] = new double[var8 + 1][2 * (var6 - 1)];
            double[][] var9 = this.twiddle[var5];

            int var10;
            for(var10 = 1; var10 < var6; ++var10) {
               var9[0][2 * (var10 - 1)] = 1.0D;
               var9[0][2 * (var10 - 1) + 1] = 0.0D;
            }

            for(var10 = 1; var10 <= var8; ++var10) {
               int var11 = 0;

               for(int var12 = 1; var12 < var6; ++var12) {
                  var11 += var10 * var7;
                  var11 %= var1;
                  double var13 = var2 * (double)var11;
                  var9[var10][2 * (var12 - 1)] = Math.cos(var13);
                  var9[var10][2 * (var12 - 1) + 1] = Math.sin(var13);
               }
            }
         }

      }
   }

   void transform_internal(double[] var1, int var2, int var3, int var4) {
      if (this.n != 1) {
         double[] var5 = new double[2 * this.n];
         int var6 = 1;
         boolean var7 = false;

         int var14;
         for(var14 = 0; var14 < this.factors.length; ++var14) {
            int var15 = this.factors[var14];
            var6 *= var15;
            double[] var8;
            double[] var9;
            int var10;
            int var11;
            int var12;
            int var13;
            if (!var7) {
               var8 = var1;
               var12 = var2;
               var10 = var3;
               var9 = var5;
               var13 = 0;
               var11 = 2;
               var7 = true;
            } else {
               var8 = var5;
               var12 = 0;
               var10 = 2;
               var9 = var1;
               var13 = var2;
               var11 = var3;
               var7 = false;
            }

            switch(var15) {
            case 2:
               this.pass_2(var14, var8, var12, var10, var9, var13, var11, var4, var6);
               break;
            case 3:
               this.pass_3(var14, var8, var12, var10, var9, var13, var11, var4, var6);
               break;
            case 4:
               this.pass_4(var14, var8, var12, var10, var9, var13, var11, var4, var6);
               break;
            case 5:
               this.pass_5(var14, var8, var12, var10, var9, var13, var11, var4, var6);
               break;
            case 6:
               this.pass_6(var14, var8, var12, var10, var9, var13, var11, var4, var6);
               break;
            case 7:
               this.pass_7(var14, var8, var12, var10, var9, var13, var11, var4, var6);
               break;
            default:
               this.pass_n(var14, var8, var12, var10, var9, var13, var11, var4, var15, var6);
            }
         }

         if (var7) {
            for(var14 = 0; var14 < this.n; ++var14) {
               var1[var2 + var3 * var14] = var5[2 * var14];
               var1[var2 + var3 * var14 + 1] = var5[2 * var14 + 1];
            }
         }

      }
   }

   void pass_2(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9) {
      byte var12 = 2;
      int var13 = this.n / var12;
      int var14 = this.n / var9;
      int var15 = var9 / var12;
      int var16 = var4 * var13;
      int var17 = var7 * var15;
      int var18 = var3;
      int var19 = var6;

      for(int var10 = 0; var10 < var14; ++var10) {
         double[] var24 = this.twiddle[var1][var10];
         double var25 = var24[0];
         double var27 = (double)(-var8) * var24[1];

         for(int var11 = 0; var11 < var15; ++var11) {
            double var29 = var2[var18];
            double var31 = var2[var18 + 1];
            double var33 = var2[var18 + var16];
            double var35 = var2[var18 + var16 + 1];
            var18 += var4;
            var5[var19] = var29 + var33;
            var5[var19 + 1] = var31 + var35;
            double var20 = var29 - var33;
            double var22 = var31 - var35;
            var5[var19 + var17] = var25 * var20 - var27 * var22;
            var5[var19 + var17 + 1] = var25 * var22 + var27 * var20;
            var19 += var7;
         }

         var19 += (var12 - 1) * var17;
      }

   }

   void pass_3(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9) {
      byte var12 = 3;
      int var13 = this.n / var12;
      int var14 = this.n / var9;
      int var15 = var9 / var12;
      double var16 = (double)var8 * Math.sqrt(3.0D) / 2.0D;
      int var18 = var4 * var13;
      int var19 = var7 * var15;
      int var20 = var3;
      int var21 = var6;

      for(int var10 = 0; var10 < var14; ++var10) {
         double[] var26 = this.twiddle[var1][var10];
         double var27 = var26[0];
         double var29 = (double)(-var8) * var26[1];
         double var31 = var26[2];
         double var33 = (double)(-var8) * var26[3];

         for(int var11 = 0; var11 < var15; ++var11) {
            double var35 = var2[var20];
            double var37 = var2[var20 + 1];
            double var39 = var2[var20 + var18];
            double var41 = var2[var20 + var18 + 1];
            double var43 = var2[var20 + 2 * var18];
            double var45 = var2[var20 + 2 * var18 + 1];
            var20 += var4;
            double var47 = var39 + var43;
            double var49 = var41 + var45;
            double var51 = var35 - var47 / 2.0D;
            double var53 = var37 - var49 / 2.0D;
            double var55 = var16 * (var39 - var43);
            double var57 = var16 * (var41 - var45);
            var5[var21] = var35 + var47;
            var5[var21 + 1] = var37 + var49;
            double var22 = var51 - var57;
            double var24 = var53 + var55;
            var5[var21 + var19] = var27 * var22 - var29 * var24;
            var5[var21 + var19 + 1] = var27 * var24 + var29 * var22;
            var22 = var51 + var57;
            var24 = var53 - var55;
            var5[var21 + 2 * var19] = var31 * var22 - var33 * var24;
            var5[var21 + 2 * var19 + 1] = var31 * var24 + var33 * var22;
            var21 += var7;
         }

         var21 += (var12 - 1) * var19;
      }

   }

   void pass_4(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9) {
      byte var12 = 4;
      int var13 = this.n / var12;
      int var14 = this.n / var9;
      int var15 = var9 / var12;
      int var16 = var3;
      int var17 = var6;
      int var18 = var4 * var13;
      int var19 = var7 * var15;

      for(int var10 = 0; var10 < var14; ++var10) {
         double[] var24 = this.twiddle[var1][var10];
         double var25 = var24[0];
         double var27 = (double)(-var8) * var24[1];
         double var29 = var24[2];
         double var31 = (double)(-var8) * var24[3];
         double var33 = var24[4];
         double var35 = (double)(-var8) * var24[5];

         for(int var11 = 0; var11 < var15; ++var11) {
            double var37 = var2[var16];
            double var39 = var2[var16 + 1];
            double var41 = var2[var16 + var18];
            double var43 = var2[var16 + var18 + 1];
            double var45 = var2[var16 + 2 * var18];
            double var47 = var2[var16 + 2 * var18 + 1];
            double var49 = var2[var16 + 3 * var18];
            double var51 = var2[var16 + 3 * var18 + 1];
            var16 += var4;
            double var53 = var37 + var45;
            double var55 = var39 + var47;
            double var57 = var41 + var49;
            double var59 = var43 + var51;
            double var61 = var37 - var45;
            double var63 = var39 - var47;
            double var65 = (double)var8 * (var41 - var49);
            double var67 = (double)var8 * (var43 - var51);
            var5[var17] = var53 + var57;
            var5[var17 + 1] = var55 + var59;
            double var20 = var61 - var67;
            double var22 = var63 + var65;
            var5[var17 + var19] = var25 * var20 - var27 * var22;
            var5[var17 + var19 + 1] = var25 * var22 + var27 * var20;
            var20 = var53 - var57;
            var22 = var55 - var59;
            var5[var17 + 2 * var19] = var29 * var20 - var31 * var22;
            var5[var17 + 2 * var19 + 1] = var29 * var22 + var31 * var20;
            var20 = var61 + var67;
            var22 = var63 - var65;
            var5[var17 + 3 * var19] = var33 * var20 - var35 * var22;
            var5[var17 + 3 * var19 + 1] = var33 * var22 + var35 * var20;
            var17 += var7;
         }

         var17 += (var12 - 1) * var19;
      }

   }

   void pass_5(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9) {
      byte var12 = 5;
      int var13 = this.n / var12;
      int var14 = this.n / var9;
      int var15 = var9 / var12;
      double var16 = Math.sqrt(5.0D) / 4.0D;
      double var18 = (double)var8 * Math.sin(1.2566370614359172D);
      double var20 = (double)var8 * Math.sin(0.6283185307179586D);
      int var22 = var3;
      int var23 = var6;
      int var24 = var4 * var13;
      int var25 = var7 * var15;

      for(int var10 = 0; var10 < var14; ++var10) {
         double[] var30 = this.twiddle[var1][var10];
         double var31 = var30[0];
         double var33 = (double)(-var8) * var30[1];
         double var35 = var30[2];
         double var37 = (double)(-var8) * var30[3];
         double var39 = var30[4];
         double var41 = (double)(-var8) * var30[5];
         double var43 = var30[6];
         double var45 = (double)(-var8) * var30[7];

         for(int var11 = 0; var11 < var15; ++var11) {
            double var47 = var2[var22];
            double var49 = var2[var22 + 1];
            double var51 = var2[var22 + var24];
            double var53 = var2[var22 + var24 + 1];
            double var55 = var2[var22 + 2 * var24];
            double var57 = var2[var22 + 2 * var24 + 1];
            double var59 = var2[var22 + 3 * var24];
            double var61 = var2[var22 + 3 * var24 + 1];
            double var63 = var2[var22 + 4 * var24];
            double var65 = var2[var22 + 4 * var24 + 1];
            var22 += var4;
            double var67 = var51 + var63;
            double var69 = var53 + var65;
            double var71 = var55 + var59;
            double var73 = var57 + var61;
            double var75 = var51 - var63;
            double var77 = var53 - var65;
            double var79 = var55 - var59;
            double var81 = var57 - var61;
            double var83 = var67 + var71;
            double var85 = var69 + var73;
            double var87 = var16 * (var67 - var71);
            double var89 = var16 * (var69 - var73);
            double var91 = var47 - var83 / 4.0D;
            double var93 = var49 - var85 / 4.0D;
            double var95 = var91 + var87;
            double var97 = var93 + var89;
            double var99 = var91 - var87;
            double var101 = var93 - var89;
            double var103 = var18 * var75 + var20 * var79;
            double var105 = var18 * var77 + var20 * var81;
            double var107 = var20 * var75 - var18 * var79;
            double var109 = var20 * var77 - var18 * var81;
            var5[var23] = var47 + var83;
            var5[var23 + 1] = var49 + var85;
            double var26 = var95 - var105;
            double var28 = var97 + var103;
            var5[var23 + var25] = var31 * var26 - var33 * var28;
            var5[var23 + var25 + 1] = var31 * var28 + var33 * var26;
            var26 = var99 - var109;
            var28 = var101 + var107;
            var5[var23 + 2 * var25] = var35 * var26 - var37 * var28;
            var5[var23 + 2 * var25 + 1] = var35 * var28 + var37 * var26;
            var26 = var99 + var109;
            var28 = var101 - var107;
            var5[var23 + 3 * var25] = var39 * var26 - var41 * var28;
            var5[var23 + 3 * var25 + 1] = var39 * var28 + var41 * var26;
            var26 = var95 + var105;
            var28 = var97 - var103;
            var5[var23 + 4 * var25] = var43 * var26 - var45 * var28;
            var5[var23 + 4 * var25 + 1] = var43 * var28 + var45 * var26;
            var23 += var7;
         }

         var23 += (var12 - 1) * var25;
      }

   }

   void pass_6(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9) {
      byte var12 = 6;
      int var13 = this.n / var12;
      int var14 = this.n / var9;
      int var15 = var9 / var12;
      double var16 = (double)var8 * Math.sqrt(3.0D) / 2.0D;
      int var18 = var3;
      int var19 = var6;
      int var20 = var4 * var13;
      int var21 = var7 * var15;

      for(int var10 = 0; var10 < var14; ++var10) {
         double[] var26 = this.twiddle[var1][var10];
         double var27 = var26[0];
         double var29 = (double)(-var8) * var26[1];
         double var31 = var26[2];
         double var33 = (double)(-var8) * var26[3];
         double var35 = var26[4];
         double var37 = (double)(-var8) * var26[5];
         double var39 = var26[6];
         double var41 = (double)(-var8) * var26[7];
         double var43 = var26[8];
         double var45 = (double)(-var8) * var26[9];

         for(int var11 = 0; var11 < var15; ++var11) {
            double var47 = var2[var18];
            double var49 = var2[var18 + 1];
            double var51 = var2[var18 + var20];
            double var53 = var2[var18 + var20 + 1];
            double var55 = var2[var18 + 2 * var20];
            double var57 = var2[var18 + 2 * var20 + 1];
            double var59 = var2[var18 + 3 * var20];
            double var61 = var2[var18 + 3 * var20 + 1];
            double var63 = var2[var18 + 4 * var20];
            double var65 = var2[var18 + 4 * var20 + 1];
            double var67 = var2[var18 + 5 * var20];
            double var69 = var2[var18 + 5 * var20 + 1];
            var18 += var4;
            double var71 = var55 + var63;
            double var73 = var57 + var65;
            double var75 = var47 - var71 / 2.0D;
            double var77 = var49 - var73 / 2.0D;
            double var79 = var16 * (var55 - var63);
            double var81 = var16 * (var57 - var65);
            double var83 = var47 + var71;
            double var85 = var49 + var73;
            double var87 = var75 - var81;
            double var89 = var77 + var79;
            double var91 = var75 + var81;
            double var93 = var77 - var79;
            double var95 = var67 + var51;
            double var97 = var69 + var53;
            double var99 = var59 - var95 / 2.0D;
            double var101 = var61 - var97 / 2.0D;
            double var103 = var16 * (var67 - var51);
            double var105 = var16 * (var69 - var53);
            double var107 = var59 + var95;
            double var109 = var61 + var97;
            double var111 = var99 - var105;
            double var113 = var101 + var103;
            double var115 = var99 + var105;
            double var117 = var101 - var103;
            var5[var19] = var83 + var107;
            var5[var19 + 1] = var85 + var109;
            double var22 = var87 - var111;
            double var24 = var89 - var113;
            var5[var19 + var21] = var27 * var22 - var29 * var24;
            var5[var19 + var21 + 1] = var27 * var24 + var29 * var22;
            var22 = var91 + var115;
            var24 = var93 + var117;
            var5[var19 + 2 * var21] = var31 * var22 - var33 * var24;
            var5[var19 + 2 * var21 + 1] = var31 * var24 + var33 * var22;
            var22 = var83 - var107;
            var24 = var85 - var109;
            var5[var19 + 3 * var21] = var35 * var22 - var37 * var24;
            var5[var19 + 3 * var21 + 1] = var35 * var24 + var37 * var22;
            var22 = var87 + var111;
            var24 = var89 + var113;
            var5[var19 + 4 * var21] = var39 * var22 - var41 * var24;
            var5[var19 + 4 * var21 + 1] = var39 * var24 + var41 * var22;
            var22 = var91 - var115;
            var24 = var93 - var117;
            var5[var19 + 5 * var21] = var43 * var22 - var45 * var24;
            var5[var19 + 5 * var21 + 1] = var43 * var24 + var45 * var22;
            var19 += var7;
         }

         var19 += (var12 - 1) * var21;
      }

   }

   void pass_7(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9) {
      byte var12 = 7;
      int var13 = this.n / var12;
      int var14 = this.n / var9;
      int var15 = var9 / var12;
      double var16 = Math.cos(0.8975979010256552D);
      double var18 = Math.cos(1.7951958020513104D);
      double var20 = Math.cos(2.6927937030769655D);
      double var22 = (double)(-var8) * Math.sin(0.8975979010256552D);
      double var24 = (double)(-var8) * Math.sin(1.7951958020513104D);
      double var26 = (double)(-var8) * Math.sin(2.6927937030769655D);
      int var28 = var3;
      int var29 = var6;
      int var30 = var4 * var13;
      int var31 = var7 * var15;

      for(int var10 = 0; var10 < var14; ++var10) {
         double[] var36 = this.twiddle[var1][var10];
         double var37 = var36[0];
         double var39 = (double)(-var8) * var36[1];
         double var41 = var36[2];
         double var43 = (double)(-var8) * var36[3];
         double var45 = var36[4];
         double var47 = (double)(-var8) * var36[5];
         double var49 = var36[6];
         double var51 = (double)(-var8) * var36[7];
         double var53 = var36[8];
         double var55 = (double)(-var8) * var36[9];
         double var57 = var36[10];
         double var59 = (double)(-var8) * var36[11];

         for(int var11 = 0; var11 < var15; ++var11) {
            double var61 = var2[var28];
            double var63 = var2[var28 + 1];
            double var65 = var2[var28 + var30];
            double var67 = var2[var28 + var30 + 1];
            double var69 = var2[var28 + 2 * var30];
            double var71 = var2[var28 + 2 * var30 + 1];
            double var73 = var2[var28 + 3 * var30];
            double var75 = var2[var28 + 3 * var30 + 1];
            double var77 = var2[var28 + 4 * var30];
            double var79 = var2[var28 + 4 * var30 + 1];
            double var81 = var2[var28 + 5 * var30];
            double var83 = var2[var28 + 5 * var30 + 1];
            double var85 = var2[var28 + 6 * var30];
            double var87 = var2[var28 + 6 * var30 + 1];
            var28 += var4;
            double var89 = var65 + var85;
            double var91 = var67 + var87;
            double var93 = var65 - var85;
            double var95 = var67 - var87;
            double var97 = var69 + var81;
            double var99 = var71 + var83;
            double var101 = var69 - var81;
            double var103 = var71 - var83;
            double var105 = var77 + var73;
            double var107 = var79 + var75;
            double var109 = var77 - var73;
            double var111 = var79 - var75;
            double var113 = var97 + var89;
            double var115 = var99 + var91;
            double var117 = var109 + var101;
            double var119 = var111 + var103;
            double var121 = var61 + var113 + var105;
            double var123 = var63 + var115 + var107;
            double var125 = ((var16 + var18 + var20) / 3.0D - 1.0D) * (var113 + var105);
            double var127 = ((var16 + var18 + var20) / 3.0D - 1.0D) * (var115 + var107);
            double var129 = (2.0D * var16 - var18 - var20) / 3.0D * (var89 - var105);
            double var131 = (2.0D * var16 - var18 - var20) / 3.0D * (var91 - var107);
            double var133 = (var16 - 2.0D * var18 + var20) / 3.0D * (var105 - var97);
            double var135 = (var16 - 2.0D * var18 + var20) / 3.0D * (var107 - var99);
            double var137 = (var16 + var18 - 2.0D * var20) / 3.0D * (var97 - var89);
            double var139 = (var16 + var18 - 2.0D * var20) / 3.0D * (var99 - var91);
            double var141 = (var22 + var24 - var26) / 3.0D * (var117 + var93);
            double var143 = (var22 + var24 - var26) / 3.0D * (var119 + var95);
            double var145 = (2.0D * var22 - var24 + var26) / 3.0D * (var93 - var109);
            double var147 = (2.0D * var22 - var24 + var26) / 3.0D * (var95 - var111);
            double var149 = (var22 - 2.0D * var24 - var26) / 3.0D * (var109 - var101);
            double var151 = (var22 - 2.0D * var24 - var26) / 3.0D * (var111 - var103);
            double var153 = (var22 + var24 + 2.0D * var26) / 3.0D * (var101 - var93);
            double var155 = (var22 + var24 + 2.0D * var26) / 3.0D * (var103 - var95);
            double var157 = var121 + var125;
            double var159 = var123 + var127;
            double var161 = var129 + var133;
            double var163 = var131 + var135;
            double var165 = var137 - var133;
            double var167 = var139 - var135;
            double var169 = -var129 - var137;
            double var171 = -var131 - var139;
            double var173 = var145 + var149;
            double var175 = var147 + var151;
            double var177 = var153 - var149;
            double var179 = var155 - var151;
            double var181 = -var153 - var145;
            double var183 = -var155 - var147;
            double var185 = var157 + var161;
            double var187 = var159 + var163;
            double var189 = var157 + var165;
            double var191 = var159 + var167;
            double var193 = var157 + var169;
            double var195 = var159 + var171;
            double var197 = var173 + var141;
            double var199 = var175 + var143;
            double var201 = var177 + var141;
            double var203 = var179 + var143;
            double var205 = var181 + var141;
            double var207 = var183 + var143;
            var5[var29] = var121;
            var5[var29 + 1] = var123;
            double var32 = var185 + var199;
            double var34 = var187 - var197;
            var5[var29 + var31] = var37 * var32 - var39 * var34;
            var5[var29 + var31 + 1] = var37 * var34 + var39 * var32;
            var32 = var193 + var207;
            var34 = var195 - var205;
            var5[var29 + 2 * var31] = var41 * var32 - var43 * var34;
            var5[var29 + 2 * var31 + 1] = var41 * var34 + var43 * var32;
            var32 = var189 - var203;
            var34 = var191 + var201;
            var5[var29 + 3 * var31] = var45 * var32 - var47 * var34;
            var5[var29 + 3 * var31 + 1] = var45 * var34 + var47 * var32;
            var32 = var189 + var203;
            var34 = var191 - var201;
            var5[var29 + 4 * var31] = var49 * var32 - var51 * var34;
            var5[var29 + 4 * var31 + 1] = var49 * var34 + var51 * var32;
            var32 = var193 - var207;
            var34 = var195 + var205;
            var5[var29 + 5 * var31] = var53 * var32 - var55 * var34;
            var5[var29 + 5 * var31 + 1] = var53 * var34 + var55 * var32;
            var32 = var185 - var199;
            var34 = var187 + var197;
            var5[var29 + 6 * var31] = var57 * var32 - var59 * var34;
            var5[var29 + 6 * var31 + 1] = var57 * var34 + var59 * var32;
            var29 += var7;
         }

         var29 += (var12 - 1) * var31;
      }

   }

   void pass_n(int var1, double[] var2, int var3, int var4, double[] var5, int var6, int var7, int var8, int var9, int var10) {
      boolean var11 = false;
      boolean var12 = false;
      int var15 = this.n / var9;
      int var16 = this.n / var10;
      int var17 = var10 / var9;
      int var18 = (var9 - 1) * var17;

      int var37;
      for(var37 = 0; var37 < var15; ++var37) {
         var5[var6 + var7 * var37] = var2[var3 + var4 * var37];
         var5[var6 + var7 * var37 + 1] = var2[var3 + var4 * var37 + 1];
      }

      int var19;
      int var22;
      for(var19 = 1; var19 < (var9 - 1) / 2 + 1; ++var19) {
         for(var37 = 0; var37 < var15; ++var37) {
            int var21 = var37 + var19 * var15;
            var22 = var37 + (var9 - var19) * var15;
            var5[var6 + var7 * var21] = var2[var3 + var4 * var21] + var2[var3 + var4 * var22];
            var5[var6 + var7 * var21 + 1] = var2[var3 + var4 * var21 + 1] + var2[var3 + var4 * var22 + 1];
            var5[var6 + var7 * var22] = var2[var3 + var4 * var21] - var2[var3 + var4 * var22];
            var5[var6 + var7 * var22 + 1] = var2[var3 + var4 * var21 + 1] - var2[var3 + var4 * var22 + 1];
         }
      }

      for(var37 = 0; var37 < var15; ++var37) {
         var2[var3 + var4 * var37] = var5[var6 + var7 * var37];
         var2[var3 + var4 * var37 + 1] = var5[var6 + var7 * var37 + 1];
      }

      int var20;
      for(var20 = 1; var20 < (var9 - 1) / 2 + 1; ++var20) {
         for(var37 = 0; var37 < var15; ++var37) {
            var2[var3 + var4 * var37] += var5[var6 + var7 * (var37 + var20 * var15)];
            var2[var3 + var4 * var37 + 1] += var5[var6 + var7 * (var37 + var20 * var15) + 1];
         }
      }

      double[] var39 = this.twiddle[var1][var16];

      for(var19 = 1; var19 < (var9 - 1) / 2 + 1; ++var19) {
         var22 = var19;
         int var27 = var19 * var15;
         int var28 = (var9 - var19) * var15;

         for(var37 = 0; var37 < var15; ++var37) {
            var2[var3 + var4 * (var37 + var27)] = var5[var6 + var7 * var37];
            var2[var3 + var4 * (var37 + var27) + 1] = var5[var6 + var7 * var37 + 1];
            var2[var3 + var4 * (var37 + var28)] = var5[var6 + var7 * var37];
            var2[var3 + var4 * (var37 + var28) + 1] = var5[var6 + var7 * var37 + 1];
         }

         for(var20 = 1; var20 < (var9 - 1) / 2 + 1; ++var20) {
            double var23;
            double var25;
            if (var22 == 0) {
               var23 = 1.0D;
               var25 = 0.0D;
            } else {
               var23 = var39[2 * (var22 - 1)];
               var25 = (double)(-var8) * var39[2 * (var22 - 1) + 1];
            }

            for(var37 = 0; var37 < var15; ++var37) {
               double var29 = var23 * var5[var6 + var7 * (var37 + var20 * var15)];
               double var31 = var25 * var5[var6 + var7 * (var37 + (var9 - var20) * var15) + 1];
               double var33 = var23 * var5[var6 + var7 * (var37 + var20 * var15) + 1];
               double var35 = var25 * var5[var6 + var7 * (var37 + (var9 - var20) * var15)];
               var2[var3 + var4 * (var37 + var27)] += var29 - var31;
               var2[var3 + var4 * (var37 + var27) + 1] += var33 + var35;
               var2[var3 + var4 * (var37 + var28)] += var29 + var31;
               var2[var3 + var4 * (var37 + var28) + 1] += var33 - var35;
            }

            var22 += var19;
            var22 %= var9;
         }
      }

      var11 = false;
      var12 = false;

      int var14;
      for(var14 = 0; var14 < var17; ++var14) {
         var5[var6 + var7 * var14] = var2[var3 + var4 * var14];
         var5[var6 + var7 * var14 + 1] = var2[var3 + var4 * var14 + 1];
      }

      for(var20 = 1; var20 < var9; ++var20) {
         for(var14 = 0; var14 < var17; ++var14) {
            var5[var6 + var7 * (var14 + var20 * var17)] = var2[var3 + var4 * (var14 + var20 * var15)];
            var5[var6 + var7 * (var14 + var20 * var17) + 1] = var2[var3 + var4 * (var14 + var20 * var15) + 1];
         }
      }

      var37 = var17;
      int var38 = var10;

      int var13;
      for(var13 = 1; var13 < var16; ++var13) {
         for(var14 = 0; var14 < var17; ++var14) {
            var5[var6 + var7 * var38] = var2[var3 + var4 * var37];
            var5[var6 + var7 * var38 + 1] = var2[var3 + var4 * var37 + 1];
            ++var37;
            ++var38;
         }

         var38 += var18;
      }

      var37 = var17;
      var38 = var10;

      for(var13 = 1; var13 < var16; ++var13) {
         var39 = this.twiddle[var1][var13];

         for(var14 = 0; var14 < var17; ++var14) {
            for(var20 = 1; var20 < var9; ++var20) {
               double var40 = var2[var3 + var4 * (var37 + var20 * var15)];
               double var24 = var2[var3 + var4 * (var37 + var20 * var15) + 1];
               double var26 = var39[2 * (var20 - 1)];
               double var41 = (double)(-var8) * var39[2 * (var20 - 1) + 1];
               var5[var6 + var7 * (var38 + var20 * var17)] = var26 * var40 - var41 * var24;
               var5[var6 + var7 * (var38 + var20 * var17) + 1] = var26 * var24 + var41 * var40;
            }

            ++var37;
            ++var38;
         }

         var38 += var18;
      }

   }

   public static int[] factor(int var0, int[] var1) {
      int[] var2 = new int[64];
      int var3 = 0;
      int var4 = var0;
      if (var0 <= 0) {
         throw new Error("Number (" + var0 + ") must be positive integer");
      } else {
         int var5;
         int var6;
         for(var6 = 0; var6 < var1.length && var4 != 1; ++var6) {
            for(var5 = var1[var6]; var4 % var5 == 0; var2[var3++] = var5) {
               var4 /= var5;
            }
         }

         for(byte var8 = 2; var4 % var8 == 0 && var4 != 1; var2[var3++] = var8) {
            var4 /= var8;
         }

         for(var5 = 3; var4 != 1; var2[var3++] = var5) {
            while(var4 % var5 != 0) {
               var5 += 2;
            }

            var4 /= var5;
         }

         var6 = 1;

         for(int var7 = 0; var7 < var3; ++var7) {
            var6 *= var2[var7];
         }

         if (var6 != var0) {
            throw new Error("factorization failed for " + var0);
         } else {
            int[] var9 = new int[var3];
            System.arraycopy(var2, 0, var9, 0, var3);
            return var9;
         }
      }
   }
}
