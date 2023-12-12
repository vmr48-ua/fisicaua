package local.fourier;

import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;

public class Fft2 {
   public static void fft2r2(int var0, int var1, int var2, double[] var3, double[] var4) {
      double[] var5 = new double[var0 * var1];
      double[] var6 = new double[var0 * var1];
      double[] var7 = new double[var0 * var1];
      double[] var8 = new double[var0 * var1];
      int[] var26 = new int[1024];
      if (var0 < var1) {
         ;
      }

      int var21 = var0 / 2;
      int var22 = var1 / 2;
      double var9 = 1.0D / Math.sqrt((double)var0);
      double var11 = 1.0D / Math.sqrt((double)var1);
      double var13 = var9 * var11;
      int var23 = (int)(Math.log((double)var0) / Math.log(2.0D) + 0.1D);
      int var24 = (int)(Math.log((double)var1) / Math.log(2.0D) + 0.1D);
      double var17 = (double)var2 * 6.283185D / (double)var1;
      int var25 = var1;

      int var27;
      for(var27 = 0; var27 < var24; ++var27) {
         var25 /= 2;
         var26[var27] = var25;
      }

      double var19;
      for(var27 = 0; var27 < var1; ++var27) {
         var19 = (double)var27 * var17;
         var8[var27] = Math.cos(var19);
         var7[var27] = Math.sin(var19);
      }

      int var28;
      int var29;
      int var30;
      int var31;
      int var32;
      for(var29 = 0; var29 < var0; ++var29) {
         var31 = var29 * var1;

         for(var27 = 0; var27 < var22; ++var27) {
            var32 = var31 + var27;
            var28 = var27 + var22;
            var30 = var31 + var28;
            var5[var27] = var3[var30];
            var6[var27] = var4[var30];
            var5[var28] = var3[var32];
            var6[var28] = var4[var32];
         }

         fftr2(var1, var24, var2, var5, var6, var26, var7, var8);

         for(var27 = 0; var27 < var22; ++var27) {
            var32 = var31 + var27;
            var28 = var27 + var22;
            var30 = var31 + var28;
            var3[var30] = var5[var27];
            var4[var30] = var6[var27];
            var3[var32] = var5[var28];
            var4[var32] = var6[var28];
         }
      }

      if (var0 != var1) {
         var17 = (double)var2 * 6.283185D / (double)var0;
         var25 = var0;

         for(var27 = 0; var27 < var23; ++var27) {
            var25 /= 2;
            var26[var27] = var25;
         }

         for(var27 = 0; var27 < var0; ++var27) {
            var19 = (double)var27 * var17;
            var8[var27] = Math.cos(var19);
            var7[var27] = Math.sin(var19);
         }
      }

      var31 = var21 * var1;

      for(var30 = 0; var30 < var1; ++var30) {
         for(var27 = 0; var27 < var21; ++var27) {
            var28 = var27 + var21;
            var29 = var27 * var1 + var30;
            var32 = var29 + var31;
            var5[var27] = var3[var32];
            var6[var27] = var4[var32];
            var5[var28] = var3[var29];
            var6[var28] = var4[var29];
         }

         fftr2(var0, var23, var2, var5, var6, var26, var7, var8);

         for(var27 = 0; var27 < var21; ++var27) {
            var28 = var27 + var21;
            var29 = var27 * var1 + var30;
            var32 = var29 + var31;
            var3[var32] = var5[var27] * var13;
            var4[var32] = var6[var27] * var13;
            var3[var29] = var5[var28] * var13;
            var4[var29] = var6[var28] * var13;
         }
      }

   }

   public static void fftr2(int var0, int var1, int var2, double[] var3, double[] var4, int[] var5, double[] var6, double[] var7) {
      int var20 = 0;
      int var15 = 1;

      int var12;
      int var16;
      int var19;
      for(int var13 = 1; var13 <= var1; ++var13) {
         int var14 = var15;
         var15 += var15;
         int var9 = var0 / var14;
         int var10 = var9 / 2;
         var16 = 0;

         for(int var17 = 0; var17 < var14; ++var17) {
            int var11 = var9 * var17;

            for(var12 = 0; var12 < var10; ++var12) {
               int var18 = var12 + var11;
               var19 = var18 + var10;
               double var26 = var3[var19] * var7[var16] - var4[var19] * var6[var16];
               double var28 = var3[var19] * var6[var16] + var4[var19] * var7[var16];
               var3[var19] = var3[var18] - var26;
               var4[var19] = var4[var18] - var28;
               var3[var18] += var26;
               var4[var18] += var28;
            }

            for(var12 = 1; var12 < var1; ++var12) {
               var20 = var12;
               if (var5[var12] > var16) {
                  break;
               }

               var16 -= var5[var12];
            }

            var16 += var5[var20];
         }
      }

      var16 = 0;

      for(var19 = 0; var19 < var0; ++var19) {
         if (var16 > var19) {
            double var30 = var3[var16];
            double var32 = var4[var16];
            var3[var16] = var3[var19];
            var4[var16] = var4[var19];
            var3[var19] = var30;
            var4[var19] = var32;
         }

         for(var12 = 0; var12 < var1; ++var12) {
            var20 = var12;
            if (var5[var12] > var16) {
               break;
            }

            var16 -= var5[var12];
         }

         var16 += var5[var20];
      }

   }

   public static void correlacioMf(int var0, int var1, double[] var2, double[] var3, double[] var4, double[] var5) {
      fft2r2(var0, var1, 1, var2, var3);
      fft2r2(var0, var1, 1, var4, var5);
      double var6 = 0.0D;
      double var8 = 0.0D;
      double var10 = 0.0D;
      double var12 = 0.0D;

      for(int var14 = 0; var14 < var0 * var1; ++var14) {
         var6 = var2[var14];
         var10 = var3[var14];
         var8 = var4[var14];
         var12 = var5[var14];
         var2[var14] = var6 * var8 + var10 * var12;
         var3[var14] = -var6 * var12 + var10 * var8;
      }

      fft2r2(var0, var1, -1, var2, var3);
   }

   public static void correlacioPof(int var0, int var1, double[] var2, double[] var3, double[] var4, double[] var5) {
      fft2r2(var0, var1, 1, var2, var3);
      fft2r2(var0, var1, 1, var4, var5);
      double var6 = 0.0D;
      double var8 = 0.0D;
      double var10 = 0.0D;
      double var12 = 0.0D;

      for(int var14 = 0; var14 < var0 * var1; ++var14) {
         var6 = var2[var14];
         var10 = var3[var14];
         var8 = var4[var14];
         var12 = var5[var14];
         var2[var14] = (var6 * var8 + var10 * var12) / Math.sqrt(var8 * var8 + var12 * var12 + 1.0E-8D);
         var3[var14] = (-var6 * var12 + var10 * var8) / Math.sqrt(var8 * var8 + var12 * var12 + 1.0E-8D);
      }

      fft2r2(var0, var1, -1, var2, var3);
   }

   public static void correlacioIf(int var0, int var1, double[] var2, double[] var3, double[] var4, double[] var5) {
      fft2r2(var0, var1, 1, var2, var3);
      fft2r2(var0, var1, 1, var4, var5);
      double var6 = 0.0D;
      double var8 = 0.0D;
      double var10 = 0.0D;
      double var12 = 0.0D;

      for(int var14 = 0; var14 < var0 * var1; ++var14) {
         var6 = var2[var14];
         var10 = var3[var14];
         var8 = var4[var14];
         var12 = var5[var14];
         var2[var14] = (var6 * var8 + var10 * var12) / (var8 * var8 + var12 * var12 + 1.0E-8D);
         var3[var14] = (-var6 * var12 + var10 * var8) / (var8 * var8 + var12 * var12 + 1.0E-8D);
      }

      fft2r2(var0, var1, -1, var2, var3);
   }

   public static void convolucio(int var0, int var1, double[] var2, double[] var3, double[] var4, double[] var5) {
      fft2r2(var0, var1, 1, var2, var3);
      fft2r2(var0, var1, 1, var4, var5);
      double var6 = 0.0D;
      double var8 = 0.0D;
      double var10 = 0.0D;
      double var12 = 0.0D;

      for(int var14 = 0; var14 < var0 * var1; ++var14) {
         var6 = var2[var14];
         var10 = var3[var14];
         var8 = var4[var14];
         var12 = var5[var14];
         var2[var14] = var6 * var8 - var10 * var12;
         var3[var14] = var6 * var12 + var10 * var8;
      }

      fft2r2(var0, var1, -1, var2, var3);
   }

   public static void HologramaFresnel(double var0, double var2, double[] var4, double[] var5, double[] var6, double var7, double var9, double var11, double var13) {
      double var15 = 0.0D;
      double var17 = 0.0D;
      double var19 = 0.0D;
      double var21 = 0.0D;
      double var23 = 0.0D;
      Frame var25 = new Frame("Progres càlcul holograma");
      TextArea var26 = new TextArea("Progres: ", 380, 100, 0);
      var25.setSize(400, 120);
      var26.setFont(new Font("Dialog", 1, 12));
      var26.setEditable(false);
      var25.add(var26);
      var25.show();

      for(int var27 = 0; (double)var27 < var13; ++var27) {
         int var28 = (int)(100.0D * (double)var27 / var13);
         var26.replaceRange("Progres: " + var28 + " %. Processant fila " + var27 + "/" + (int)var13 + "    \n", 0, 100);
         var21 = ((double)var27 - 0.5D * var13) * var11 / var13;

         for(int var29 = 0; (double)var29 < var13; ++var29) {
            if (var4[var27 * (int)var13 + var29] != 0.0D) {
               var19 = ((double)var29 - 0.5D * var13) * var11 / var13;

               for(int var30 = 0; (double)var30 < var9; ++var30) {
                  var17 = ((double)var30 - 0.5D * var9) * var7 / var9;

                  for(int var31 = 0; (double)var31 < var9; ++var31) {
                     var15 = ((double)var31 - 0.5D * var9) * var7 / var9;
                     var23 = 6.283185307179586D * var0 / var2 + 3.141592653589793D * ((var15 - var19) * (var15 - var19) + (var17 - var21) * (var17 - var21)) / (var2 * var0);
                     var5[var30 * (int)var9 + var31] += var4[var27 * (int)var13 + var29] / var0 * Math.cos(var23);
                     var6[var30 * (int)var9 + var31] += var4[var27 * (int)var13 + var29] / var0 * Math.sin(var23);
                  }
               }
            }
         }
      }

      var25.hide();
   }

   public static void CorbaFase(double[] var0, double[] var1, int[] var2, int var3, int var4) {
      for(int var5 = 0; var5 < var3 * var4; ++var5) {
         var2[var5] = (int)(254.0D * (Math.atan2(var1[var5], var0[var5]) + 3.141592653589793D) / 6.283185307179586D);
         var2[var5] |= -16777216 | var2[var5] << 16 | var2[var5] << 8;
      }

   }

   public static void Fresnel(int var0, int var1, double var2, double var4, double[] var6, double[] var7, double[] var8, int[] var9, int[] var10, double var11, double var13, double var15) {
      double var17 = 0.0D;
      double var19 = 0.0D;
      double var21 = 0.0D;
      double var23 = 0.0D;
      fft2r2(var0, var1, 1, var6, var7);

      int var26;
      for(int var25 = 0; var25 < var0; ++var25) {
         for(var26 = 0; var26 < var1; ++var26) {
            var21 = ((double)var25 - (double)var0 / 2.0D) / var15;
            var23 = ((double)var26 - (double)var1 / 2.0D) / var15;
            var17 = var6[var25 * var0 + var26];
            var19 = var7[var25 * var0 + var26];
            var6[var25 * var0 + var26] = var17 * Math.cos(3.141592653589793D * var13 * var11 * (var21 * var21 + var23 * var23)) + var19 * Math.sin(3.141592653589793D * var13 * var11 * (var21 * var21 + var23 * var23));
            var7[var25 * var0 + var26] = var19 * Math.cos(3.141592653589793D * var13 * var11 * (var21 * var21 + var23 * var23)) - var17 * Math.sin(3.141592653589793D * var13 * var11 * (var21 * var21 + var23 * var23));
         }
      }

      fft2r2(var0, var1, -1, var6, var7);
      normaliza(var0, var1, var2, var4, var6, var7, var9, var10);

      for(var26 = 0; var26 < var0 * var1; ++var26) {
         var8[var26] = var6[var26] * var6[var26] + var7[var26] * var7[var26];
      }

   }

   public static void normaliza(int var0, int var1, double var2, double var4, double[] var6, double[] var7, int[] var8, int[] var9) {
      double var10 = 0.0D;

      for(int var12 = 0; var12 < var0 * var1; ++var12) {
         var10 = var6[var12] * var6[var12] + var7[var12] * var7[var12];
         if (var2 < var10) {
            var2 = var10;
         }

         if (var4 > var10) {
            var4 = var10;
         }
      }

      for(int var13 = 0; var13 < var0 * var1; ++var13) {
         var9[var13] = var8[var13] = (int)(254.0D * (var6[var13] * var6[var13] + var7[var13] * var7[var13] - var4) / (var2 - var4));
         var8[var13] |= -16777216 | var8[var13] << 16 | var8[var13] << 8;
      }

   }
}
