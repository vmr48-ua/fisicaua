import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

public class Varios {
   static int CalculaTablaXY(int var0, int var1) {
      int var8;
      if (var1 == 45) {
         for(var8 = 0; var8 < 256; Colores.tablaMonitor[var8] = var8++) {
         }
      } else {
         double var2;
         if (var1 == 90) {
            var2 = 10000.0D;
         }

         var2 = Math.tan((double)var1 * 3.141592D / 180.0D);
         double var4;
         double var6;
         if ((double)var0 > 0.0D) {
            var4 = (double)var0 / (var2 - 1.0D);
            var6 = var4 * (-var4 - (double)var0);

            for(var8 = 0; var8 < var0; ++var8) {
               Colores.tablaMonitor[var8] = (int)(var6 / ((double)(var8 - var0) - var4) - var4);
            }
         }

         if ((double)var0 < 255.0D) {
            var4 = var2 * ((double)var0 - 255.0D) / (var2 - 1.0D);
            var6 = var4 * ((double)var0 - 255.0D - var4);

            for(var8 = var0; var8 < 256; ++var8) {
               Colores.tablaMonitor[var8] = (int)(var6 / ((double)(var8 - 255) - var4) + (double)var0 - var4);
            }
         }
      }

      return 1;
   }

   static void DibujaPuntoFijo(double var0, double var2, int var4) {
      int var6 = (int)(50.0D + var0 * 500.0D + 0.5D);
      int var5 = (int)(500.0D - var2 * 500.0D + 0.5D) - 50;

      for(int var9 = -var4; var9 <= var4; ++var9) {
         int var7 = var5 + var9;
         int var10 = (int)(Math.sqrt((double)(var4 * var4 - var9 * var9)) + 0.5D);

         for(int var8 = var6 - var10; var8 <= var6 + var10; ++var8) {
            Diagrama.a[var7][var8 * 3] = 0;
            Diagrama.a[var7][var8 * 3 + 1] = 0;
            Diagrama.a[var7][var8 * 3 + 2] = 255;
         }
      }

   }

   static void DibujaLetra1(int[][] var0, int var1, int var2) {
      for(int var3 = 0; var3 < 9; ++var3) {
         for(int var4 = 0; var4 < 7; ++var4) {
            if (var0[var3][var4] != 0) {
               Diagrama.a[var1 + var3][(var2 + var4) * 3] = var0[var3][var4];
               Diagrama.a[var1 + var3][(var2 + var4) * 3 + 1] = var0[var3][var4];
               Diagrama.a[var1 + var3][(var2 + var4) * 3 + 2] = 0;
            }
         }
      }

   }

   static void DibujaLetra2(int[][] var0, int var1, int var2) {
      for(int var3 = 0; var3 < 9; ++var3) {
         for(int var4 = 0; var4 < 7; ++var4) {
            if (var0[var3][var4] != 0) {
               Diagrama.a[var1 + var3][(var2 + var4) * 3] = var0[var3][var4];
               Diagrama.a[var1 + var3][(var2 + var4) * 3 + 1] = 0;
               Diagrama.a[var1 + var3][(var2 + var4) * 3 + 2] = var0[var3][var4];
            }
         }
      }

   }

   static void DibujaLetra3(int[][] var0, int var1, int var2) {
      for(int var3 = 0; var3 < 9; ++var3) {
         for(int var4 = 0; var4 < 7; ++var4) {
            if (var0[var3][var4] != 0) {
               Diagrama.a[var1 + var3][(var2 + var4) * 3] = 0;
               Diagrama.a[var1 + var3][(var2 + var4) * 3 + 1] = 0;
               Diagrama.a[var1 + var3][(var2 + var4) * 3 + 2] = var0[var3][var4];
            }
         }
      }

   }

   static void DibujaLinea(Graphics var0, double var1, double var3, double var5, double var7) {
      int var11 = Diagrama.a1a - 5;
      int var12 = Diagrama.b1a - 5;
      int var13 = Diagrama.a2a + 5;
      int var14 = Diagrama.b2a + 5;
      int var15 = (int)(500.0D - var3 * 500.0D + 0.5D) - 50;
      int var16 = (int)(50.0D + var1 * 500.0D + 0.5D);
      int var17 = (int)(500.0D - var7 * 500.0D + 0.5D) - 50;
      int var18 = (int)(50.0D + var5 * 500.0D + 0.5D);
      int var19 = Math.abs(var18 - var16);
      int var20 = Math.abs(var17 - var15);
      int var9;
      if (var16 <= var18) {
         var9 = var16;
      } else {
         var9 = var18;
      }

      int var10;
      if (var15 <= var17) {
         var10 = var15;
      } else {
         var10 = var17;
      }

      var0.drawImage(Diagrama.buffImage, var11, var12, var13, var14, var11, var12, var13, var14, Colores.diagrama);
      Diagrama.a1a = var9;
      Diagrama.b1a = var10;
      Diagrama.a2a = var9 + var19;
      Diagrama.b2a = var10 + var20;
      var0.drawLine(var16, var15, var18, var17);
   }

   static void DibujaLineaSB(Graphics var0, double var1, double var3, double var5, double var7) {
      int var11 = (int)(500.0D - var3 * 500.0D + 0.5D) - 50;
      int var12 = (int)(50.0D + var1 * 500.0D + 0.5D);
      int var13 = (int)(500.0D - var7 * 500.0D + 0.5D) - 50;
      int var14 = (int)(50.0D + var5 * 500.0D + 0.5D);
      int var15 = Math.abs(var14 - var12);
      int var16 = Math.abs(var13 - var11);
      if (var12 > var14) {
         ;
      }

      if (var11 > var13) {
         ;
      }

      var0.drawLine(var12, var11, var14, var13);
   }

   static void DibujaPunto(Graphics var0, double var1, double var3, int var5) {
      int var6 = (int)(500.0D - var3 * 500.0D + 0.5D) - 50;
      int var7 = (int)(50.0D + var1 * 500.0D + 0.5D);
      var6 -= var5;
      var7 -= var5;
      int var8 = var5 * 2;
      int var9 = var5 * 2;
      var0.fillArc(var7, var6, var8, var9, 0, 360);
   }

   static void DibujaPuntoHueco(Graphics var0, double var1, double var3, int var5) {
      int var6 = (int)(500.0D - var3 * 500.0D + 0.5D) - 50;
      int var7 = (int)(50.0D + var1 * 500.0D + 0.5D);
      var6 -= var5;
      var7 -= var5;
      int var8 = var5 * 2;
      int var9 = var5 * 2;
      var0.drawArc(var7, var6, var8, var9, 0, 360);
   }

   static int RGBXYZ(DatosColor var0) {
      double var1 = var0.R;
      double var3 = var0.G;
      double var5 = var0.B;
      double var7 = 0.49D;
      double var9 = 0.17697D;
      double var11 = 0.0D;
      double var13 = 0.31D;
      double var15 = 0.8124D;
      double var17 = 0.01D;
      double var19 = 0.2D;
      double var21 = 0.01663D;
      double var23 = 0.99D;
      var0.X = var7 * var1 + var13 * var3 + var19 * var5;
      var0.Y = var9 * var1 + var15 * var3 + var21 * var5;
      var0.Z = var11 * var1 + var17 * var3 + var23 * var5;
      return 1;
   }

   static int RGBxy(DatosColor var0) {
      double var1 = var0.R;
      double var3 = var0.G;
      double var5 = var0.B;
      double var7 = 0.49D;
      double var9 = 0.17697D;
      double var11 = 0.0D;
      double var13 = 0.31D;
      double var15 = 0.8124D;
      double var17 = 0.01D;
      double var19 = 0.2D;
      double var21 = 0.01663D;
      double var23 = 0.99D;
      double var25 = var7 * var1 + var13 * var3 + var19 * var5;
      double var27 = var9 * var1 + var15 * var3 + var21 * var5;
      double var29 = var11 * var1 + var17 * var3 + var23 * var5;
      double var31 = var25 + var27 + var29;
      var0.x = var25 / var31;
      var0.y = var27 / var31;
      return 1;
   }

   static int xyrgb(DatosColor var0) {
      double var1 = var0.x;
      double var3 = var0.y;
      double var5 = 0.49D;
      double var7 = 0.17697D;
      double var9 = 0.0D;
      double var11 = 0.31D;
      double var13 = 0.8124D;
      double var15 = 0.01D;
      double var17 = 0.2D;
      double var19 = 0.01663D;
      double var21 = 0.99D;
      double var39 = 1.0D - var1 - var3;
      double var23 = var5 * var13 * var21 + var9 * var11 * var19 + var7 * var15 * var17 - var9 * var13 * var17 - var7 * var11 * var21 - var5 * var15 * var19;
      double var25 = var1 * var13 * var21 + var39 * var11 * var19 + var3 * var15 * var17 - var39 * var13 * var17 - var3 * var11 * var21 - var1 * var15 * var19;
      double var27 = var5 * var3 * var21 + var9 * var1 * var19 + var7 * var39 * var17 - var9 * var3 * var17 - var7 * var1 * var21 - var5 * var39 * var19;
      double var29 = var5 * var13 * var39 + var9 * var11 * var3 + var7 * var15 * var1 - var9 * var13 * var1 - var7 * var11 * var39 - var5 * var15 * var3;
      double var33 = var25 / var23;
      double var35 = var27 / var23;
      double var37 = var29 / var23;
      double var31 = 255.0D / (var33 + var35 + var37);
      var33 *= var31;
      var35 *= var31;
      var37 *= var31;
      if (var33 < 0.0D) {
         var35 /= 1.0D + var33 * var33 * 3.0E-5D;
         var37 /= 1.0D + var33 * var33 * 3.0E-5D;
         var33 = 0.0D;
      }

      if (var33 > 255.0D) {
         var33 = 255.0D;
      }

      if (var35 < 0.0D) {
         var35 = 0.0D;
      }

      if (var35 > 255.0D) {
         var35 = 255.0D;
      }

      if (var37 < 0.0D) {
         var37 = 0.0D;
      }

      if (var37 > 255.0D) {
         var37 = 255.0D;
      }

      var0.r = var33;
      var0.g = var35;
      var0.b = var37;
      return 1;
   }

   static int XYZRGB(DatosColor var0) {
      double var1 = var0.X;
      double var3 = var0.Y;
      double var5 = var0.Z;
      double var7 = var1 + var3 + var5;
      var0.x = var1 / var7;
      var0.y = var3 / var7;
      xyrgb(var0);
      double var9 = var0.r;
      double var11 = var0.g;
      double var13 = var0.b;
      double var15 = var3 / (0.17697D * var9 + 0.8124D * var11 + 0.01663D * var13);
      var0.R = var15 * var9;
      var0.G = var15 * var11;
      var0.B = var15 * var13;
      return 1;
   }

   static int OndaDominante(DatosColor var0) {
      double var2 = 3.141592D;
      double var4 = var0.x;
      double var6 = var0.y;
      double[] var8 = Colores.tablaXY.xf;
      double[] var9 = Colores.tablaXY.yf;
      int[] var10 = Colores.tablaXY.lambda;
      double var11 = 2.0D * var2;
      double var15 = 0.0D;
      if (var4 == 0.333D && var6 == 0.333D) {
         return 0;
      } else {
         double var19 = Math.atan2(var9[45] - 0.333D, var8[45] - 0.333D);
         double var17 = Math.atan2(var9[0] - 0.333D, var8[0] - 0.333D) - var19 + var11;
         double var13 = Math.atan2(var6 - 0.333D, var4 - 0.333D) - var19;
         if (var13 < 0.0D) {
            var13 += var11;
         }

         int var1;
         double var21;
         double var23;
         double var25;
         double var27;
         double var29;
         double var31;
         double var33;
         double var35;
         double var37;
         double var39;
         if (var13 > var17) {
            double var43 = (var4 - 0.333D) / (var6 - 0.333D);
            double var45 = (var8[45] - var8[0]) / (var9[45] - var9[0]);
            var37 = (var8[0] - 0.333D - var45 * var9[0] + var43 * 0.333D) / (var43 - var45);
            var35 = var43 * (var37 - 0.333D) + 0.333D;
            var13 -= var2;
            var21 = Math.atan2(var9[0] - 0.333D, var8[0] - 0.333D) - var19;
            if (var21 < 0.0D) {
               var21 += var11;
            }

            for(var1 = 1; var1 < 46; ++var1) {
               var15 = Math.atan2(var9[var1] - 0.333D, var8[var1] - 0.333D) - var19;
               if (var15 < 0.0D) {
                  var15 += var11;
               }

               if (var15 <= var13) {
                  break;
               }

               var21 = var15;
            }

            var23 = var21 - var15;
            var25 = (var13 - var15) / var23;
            var31 = var8[var1] - (var8[var1] - var8[var1 - 1]) * var25;
            var33 = var9[var1] - (var9[var1] - var9[var1 - 1]) * var25;
            var39 = (double)(var10[var1] - (int)((double)(var10[var1] - var10[var1 - 1]) * var25));
            var27 = (var4 - 0.333D) * (var4 - 0.333D) + (var6 - 0.333D) * (var6 - 0.333D);
            var29 = (var35 - 0.333D) * (var35 - 0.333D) + (var37 - 0.333D) * (var37 - 0.333D);
         } else {
            var35 = 0.333D;
            var37 = 0.333D;
            var21 = Math.atan2(var9[0] - 0.333D, var8[0] - 0.333D) - var19;
            if (var21 < 0.0D) {
               var21 += var11;
            }

            for(var1 = 1; var1 < 46; ++var1) {
               var15 = Math.atan2(var9[var1] - 0.333D, var8[var1] - 0.333D) - var19;
               if (var15 < 0.0D) {
                  var15 += var11;
               }

               if (var15 <= var13) {
                  break;
               }

               var21 = var15;
            }

            var23 = var21 - var15;
            var25 = (var13 - var15) / var23;
            var31 = var8[var1] - (var8[var1] - var8[var1 - 1]) * var25;
            var33 = var9[var1] - (var9[var1] - var9[var1 - 1]) * var25;
            var39 = (double)(var10[var1] - (int)((double)(var10[var1] - var10[var1 - 1]) * var25));
            var27 = (var4 - 0.333D) * (var4 - 0.333D) + (var6 - 0.333D) * (var6 - 0.333D);
            var29 = (var31 - 0.333D) * (var31 - 0.333D) + (var33 - 0.333D) * (var33 - 0.333D);
         }

         if (!(var31 < 0.0D) && !(var31 > 0.9D) && !(var33 < 0.0D) && !(var33 > 0.9D)) {
            double var41 = Math.sqrt(var27 / var29);
            var0.xd = var31;
            var0.yd = var33;
            var0.xm = var35;
            var0.ym = var37;
            var0.ondaDominante = (int)var39;
            var0.pureza = var41;
            return 1;
         } else {
            return 0;
         }
      }
   }

   static void normalizaRGB(DatosColor var0) {
      double var4 = var0.R;
      double var6 = var0.G;
      double var8 = var0.B;
      int var1;
      if (var4 > 100.0D) {
         var1 = 255;
      } else {
         var1 = (int)(var4 * 2.5D);
      }

      int var2;
      if (var6 > 100.0D) {
         var2 = 255;
      } else {
         var2 = (int)(var6 * 2.5D);
      }

      int var3;
      if (var8 > 100.0D) {
         var3 = 255;
      } else {
         var3 = (int)(var8 * 2.5D);
      }

      var0.RR = var1;
      var0.GG = var2;
      var0.BB = var3;
      var0.VR = Colores.tablaMonitor[var1];
      var0.VG = Colores.tablaMonitor[var2];
      var0.VB = Colores.tablaMonitor[var3];
   }

   static void IluminantexFiltro(DatosEspectro var0, DatosEspectro var1, DatosEspectro var2) {
      int var5 = 0;

      for(int var3 = 0; var3 < var0.numDatosEspectro; ++var3) {
         for(int var4 = 0; var4 < var1.numDatosEspectro; ++var4) {
            if (var0.datoColorL[var3] == var1.datoColorL[var4]) {
               var2.datoColorL[var5] = var0.datoColorL[var3];
               var2.datoColorY[var5] = var0.datoColorY[var3] * var1.datoColorY[var4];
               ++var5;
               break;
            }
         }
      }

      var2.numDatosEspectro = var5;
      CalculaXYZ(var2);
      XYZRGB(var2);
      normalizaRGB(var2);
   }

   static int CalculaXYZ(DatosEspectro var0) {
      double var3 = 0.0D;
      double var5 = 0.0D;
      double var7 = 0.0D;
      double var9 = 0.0D;

      int var1;
      for(var1 = 0; var1 < var0.numDatosEspectro; ++var1) {
         for(int var2 = 0; var2 < 65; ++var2) {
            if (var0.datoColorL[var1] == Colores.tablaXYT.lambda[var2]) {
               var5 += var0.datoColorY[var1] * Colores.tablaXYT.xt[var2];
               var7 += var0.datoColorY[var1] * Colores.tablaXYT.yt[var2];
               var9 += var0.datoColorY[var1] * Colores.tablaXYT.zt[var2];
               var3 += Colores.tablaXYT.yt[var2];
            }
         }
      }

      var0.X = var5 * 100.0D / var3;
      var0.Y = var7 * 100.0D / var3;
      var0.Z = var9 * 100.0D / var3;
      var3 = var5 + var7 + var9;
      var0.x = var5 / var3;
      var0.y = var7 / var3;
      var1 = OndaDominante(var0);
      if (var1 < 1) {
         var0.ondaDominante = 0;
      }

      return 1;
   }

   static int preparaGrafica(DatosEspectro var0) {
      int var1 = 0;

      int var2;
      for(var2 = 0; var1 < var0.numDatosEspectro; ++var1) {
         if (var0.datoColorL[var1] >= 400 && var0.datoColorL[var1] <= 700) {
            var0.xg[var2] = (double)var0.datoColorL[var1];
            var0.yg[var2] = var0.datoColorY[var1];
            ++var2;
         }
      }

      var0.numDatosGraf = var2;
      if (var2 == 0) {
         return 0;
      } else {
         return 1;
      }
   }

   static int leeLineaAscii(BufferedReader var0, double[] var1, int var2) {
      boolean var4 = false;
      int var5 = 0;
      char[] var6 = new char[100];
      char[] var7 = new char[100];

      try {
         String var8 = var0.readLine();
         if (var8 == null) {
            return -1;
         } else {
            while(true) {
               String var9 = var8.trim();
               var6 = var9.toCharArray();
               if (var6.length < 1) {
                  break;
               }

               int var3;
               for(var3 = 0; var3 < var6.length && !Character.isWhitespace(var6[var3]); ++var3) {
                  var7[var3] = var6[var3];
               }

               String var10 = new String(var7, 0, var3);
               var1[var5] = Double.parseDouble(var10);
               ++var5;
               if (var5 > var2 || var6.length <= var3) {
                  break;
               }

               var8 = new String(var6, var3, var6.length - var3);
            }

            return var5;
         }
      } catch (EOFException var13) {
         return -2;
      } catch (IOException var14) {
         return -2;
      }
   }
}
