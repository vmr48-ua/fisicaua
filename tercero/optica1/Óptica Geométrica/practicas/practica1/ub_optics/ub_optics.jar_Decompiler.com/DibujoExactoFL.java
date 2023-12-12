import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DibujoExactoFL extends Canvas {
   int eje = 105;
   int numColores;
   int numSup;
   int nir;

   public void paint(Graphics var1) {
      this.nir = ExactoFL.nir;
      this.numColores = ExactoFL.numColores;
      this.numSup = FuncionesExactasFL.numSup;
      Graphics2D var2 = (Graphics2D)var1;
      this.dibujaElementos(var2);
      if (SistemaFL.existeObjeto[0] != 0) {
         if (ExactoFL.dibuja != 0) {
            this.dibujaHazRayos(var2);
            this.dibujaPlanoImagen(var2);
         }
      }
   }

   void dibujaElementos(Graphics var1) {
      var1.setColor(Color.yellow);

      for(int var2 = 1; var2 < 7; ++var2) {
         double var9;
         double var23;
         if (SistemaFL.tipoE[var2] == 1) {
            if (SistemaFL.valPE[var2] != 0.0D) {
               double var19 = ExactoFL.radioDib1[var2];
               double var21 = ExactoFL.radioDib2[var2];
               var23 = (double)ExactoFL.diamMax / 2.0D;
               var9 = SistemaFL.valZE[var2];
               double var7 = ExactoFL.espesor[var2];
               double var11 = var9 - var7 / 2.0D;
               double var13 = var9 + var7 / 2.0D;
               double var15 = var11 + ExactoFL.valDib1PE[var2];
               double var17 = var13 + ExactoFL.valDib2PE[var2];
               this.dibujaSuperficie(var1, var11, var19, var23);
               this.dibujaSuperficie(var1, var13, var21, var23);
               int var5 = (int)(var15 / 2.0D);
               int var6 = (int)(var17 / 2.0D);
               int var4 = this.eje - (int)var23 * 2 - 4;
               var1.drawLine(var5, var4, var6, var4);
               var4 = this.eje + (int)var23 * 2 + 4;
               var1.drawLine(var5, var4, var6, var4);
               ExactoFL.dzb1[var2] = var15 - var9;
               ExactoFL.dzb2[var2] = var17 - var9;
            }
         } else if (SistemaFL.valDE[var2] != (double)ExactoFL.diamMax) {
            var9 = SistemaFL.valZE[var2];
            var23 = SistemaFL.valDE[var2] / 2.0D;
            this.dibujaDiafragma(var1, var9, var23);
         }
      }

   }

   void dibujaSuperficie(Graphics var1, double var2, double var4, double var6) {
      double var12 = var2 / 2.0D;
      double var14 = var6 * 2.0D + 4.0D;
      double var16 = var4 * 12.0D;
      double var24 = var12 + var16;
      int var8 = (int)var12;
      if (var4 >= 1.0E10D) {
         int var11 = (int)var14;
         var1.drawLine(var8, this.eje - var11, var8, this.eje + var11);
      } else {
         double var18 = Math.asin(var14 / var16);
         double var20;
         double var22;
         if (var16 > 0.0D) {
            var20 = 3.141592D - var18;
            var22 = var20 + 2.0D * var18;
         } else {
            var20 = var18;
            var22 = var18 - 2.0D * var18;
         }

         this.dibujaArco(var1, var24, var16, var20, var22);
      }
   }

   void dibujaArco(Graphics var1, double var2, double var4, double var6, double var8) {
      int[] var12 = new int[1000];
      int[] var13 = new int[1000];
      double var16 = 2.0D / Math.abs(var4);
      int var10 = 0;
      boolean var11 = false;

      for(double var14 = var6; var14 <= var8 && var10 < 1000; var14 += var16) {
         if (var4 > 0.0D) {
            var12[var10] = (int)(var2 + var4 * Math.cos(var14));
         } else {
            var12[var10] = (int)(var2 - var4 * Math.cos(var14));
         }

         var13[var10] = this.eje - (int)(var4 * Math.sin(var14));
         ++var10;
      }

      int var18 = var10;

      for(var10 = 1; var10 < var18; ++var10) {
         var1.drawLine(var12[var10 - 1], var13[var10 - 1], var12[var10], var13[var10]);
      }

   }

   void dibujaDiafragma(Graphics var1, double var2, double var4) {
      int var6 = (int)(var2 / 2.0D);
      int var7 = (int)(var4 * 2.0D);
      var1.drawLine(var6, this.eje - 73, var6, this.eje - var7);
      var1.drawLine(var6, this.eje + 73, var6, this.eje + var7);
   }

   void dibujaHazRayos(Graphics var1) {
      new Color(255, 128, 0);
      Color[] var27 = new Color[]{Color.green, Color.darkGray, Color.red};
      var1.setColor(Color.white);

      for(int var19 = 0; var19 < 2; ++var19) {
         for(int var4 = this.numColores - 1; var4 >= 0; --var4) {
            for(int var3 = 0; var3 <= this.nir * 2; ++var3) {
               for(int var2 = 0; var2 <= this.numSup; ++var2) {
                  int var17;
                  int var14;
                  if (var2 == 0) {
                     if (this.numColores == 1) {
                        var1.setColor(Color.green);
                     } else {
                        var1.setColor(Color.white);
                     }

                     if (!(SistemaFL.zObjeto[0] < 0.0D) && SistemaFL.real[0] != 0 && !(SistemaFL.zObjeto[0] >= 1.0E10D)) {
                        var14 = (int)(SistemaFL.zObjeto[0] / 2.0D);
                        if (var19 == 0) {
                           var17 = this.eje;
                        } else {
                           var17 = this.eje - (int)(SistemaFL.valYObj[0] * 2.0D);
                        }
                     } else {
                        var14 = 0;
                        var17 = this.eje - (int)(FuncionesExactasFL.dmRayo[var19][var4][var2 + 1][var3] * 2.0D);
                     }
                  } else {
                     if (this.numColores == 1) {
                        var1.setColor(Color.green);
                     } else {
                        var1.setColor(var27[var4]);
                     }

                     if (FuncionesExactasFL.pasam[var19][var4][var2][var3] == 0) {
                        break;
                     }

                     var14 = (int)(FuncionesExactasFL.zmRayo[var19][var4][var2][var3] / 2.0D);
                     var17 = this.eje - (int)(FuncionesExactasFL.ymRayo[var19][var4][var2][var3] * 2.0D);
                     if (var17 >= this.eje) {
                        if (var4 == 1) {
                           ++var17;
                        }

                        if (var4 == 2) {
                           --var17;
                        }
                     } else {
                        if (var4 == 1) {
                           --var17;
                        }

                        if (var4 == 2) {
                           ++var17;
                        }
                     }
                  }

                  int var15 = (int)(FuncionesExactasFL.zmRayo[var19][var4][var2 + 1][var3] / 2.0D);
                  int var18 = this.eje - (int)(FuncionesExactasFL.ymRayo[var19][var4][var2 + 1][var3] * 2.0D);
                  var1.drawLine(var14, var17, var15, var18);
               }
            }
         }
      }

   }

   void dibujaPlanoImagen(Graphics var1) {
      new Color(255, 128, 0);
      byte var6 = 70;
      double var10 = FuncionesExactasFL.imagParax + ExactoFL.valZImag;
      int var7 = (int)(var10 / 2.0D);
      var1.setColor(Color.magenta);
      var1.drawLine(var7, this.eje - var6, var7, this.eje + var6);
   }

   public void redraw() {
      this.repaint();
   }
}
