import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DibujoSistemaFC extends Canvas {
   int eje = 105;

   public void paint(Graphics var1) {
      Color var25 = new Color(255, 128, 0);
      int var26 = AnteproyectoFC.numUltimaLente;
      int var27 = AnteproyectoFC.numUltimoElemento;
      float[] var28 = new float[]{4.0F, 4.0F};
      BasicStroke var29 = new BasicStroke(1.0F, 1, 0, 1.0F, var28, 0.0F);
      BasicStroke var30 = new BasicStroke(1.0F);
      Graphics2D var31 = (Graphics2D)var1;
      this.dibujaElementos(var31);

      for(int var18 = 0; var18 < 2; ++var18) {
         int var5 = AnteproyectoFC.numTramos[var18];
         if (var5 < 2) {
            break;
         }

         if (SistemaFC.existeObjeto[var18] != 0) {
            int var2;
            int var3;
            int var4;
            int var7;
            int var13;
            int var14;
            int var16;
            int var17;
            for(var3 = 0; var3 < 22 && (SistemaFC.valYObj[var18] != 0.0D || var3 <= 10); ++var3) {
               if (var18 == 0 && var3 == 0) {
                  var31.setColor(Color.green);
               }

               if (var18 == 0 && var3 == 11) {
                  var31.setColor(Color.cyan);
               }

               if (var18 == 1 && var3 == 0) {
                  var31.setColor(Color.yellow);
               }

               if (var18 == 1 && var3 == 11) {
                  var31.setColor(var25);
               }

               for(var4 = 0; var4 < var5; ++var4) {
                  var2 = AnteproyectoFC.tramo[var18][var4];
                  var7 = AnteproyectoFC.tramo[var18][var4 + 1];
                  if (var2 == 0) {
                     if (!(SistemaFC.zObjeto[var18] < 0.0D) && SistemaFC.real[var18] != 0 && !(SistemaFC.zObjeto[var18] >= 1.0E10D)) {
                        var13 = (int)(SistemaFC.zObjeto[var18] / 2.0D);
                        if (var3 <= 10) {
                           var16 = this.eje;
                        } else {
                           var16 = this.eje - (int)(SistemaFC.valYObj[var18] * 2.0D);
                        }
                     } else {
                        var13 = 0;
                        var16 = this.eje - (int)(AnteproyectoFC.bpRayo[var18][0][var3] * 2.0D);
                     }
                  } else {
                     if (AnteproyectoFC.pasa[var18][var2][var3] == 0) {
                        break;
                     }

                     var13 = (int)(AnteproyectoFC.zpRayo[var18][var2][var3] / 2.0D);
                     var16 = this.eje - (int)(AnteproyectoFC.ypRayo[var18][var2][var3] * 2.0D);
                  }

                  var14 = (int)(AnteproyectoFC.zaRayo[var18][var7][var3] / 2.0D);
                  var17 = this.eje - (int)(AnteproyectoFC.yaRayo[var18][var7][var3] * 2.0D);
                  var31.drawLine(var13, var16, var14, var17);
               }
            }

            var7 = AnteproyectoFC.tramo[var18][1];
            if (AnteproyectoFC.verObjetoVirtual == 1 && SistemaFC.real[var18] == 0 && var18 == 0 && SistemaFC.existeObjeto[1] == 0 && SistemaFC.zObjeto[0] > AnteproyectoFC.zaRayo[var18][var7][0]) {
               var31.setStroke(var29);
               var14 = (int)(SistemaFC.zObjeto[var18] / 2.0D);
               var17 = this.eje - (int)(SistemaFC.valYObj[var18] * 2.0D);
               var31.setColor(new Color(0, 100, 255));
               var31.drawLine(var14, this.eje, var14, var17);
               var7 = AnteproyectoFC.tramo[var18][1];
               var31.setColor(Color.green);

               for(var3 = 0; var3 < 11; ++var3) {
                  var13 = (int)(AnteproyectoFC.zaRayo[var18][var7][var3] / 2.0D);
                  var16 = this.eje - (int)(AnteproyectoFC.yaRayo[var18][var7][var3] * 2.0D);
                  var31.drawLine(var13, var16, var14, this.eje);
               }

               if (SistemaFC.campo[0] != 0.0D) {
                  var31.setColor(Color.cyan);

                  for(var3 = 11; var3 < 22; ++var3) {
                     var13 = (int)(AnteproyectoFC.zaRayo[var18][var7][var3] / 2.0D);
                     var16 = this.eje - (int)(AnteproyectoFC.yaRayo[var18][var7][var3] * 2.0D);
                     var31.drawLine(var13, var16, var14, var17);
                  }
               }

               var31.setStroke(var30);
            }

            if (var18 == 0) {
               var31.setColor(new Color(0, 100, 255));
            } else {
               var31.setColor(Color.magenta);
            }

            for(var4 = 0; var4 < var5; ++var4) {
               var2 = AnteproyectoFC.tramo[var18][var4];
               var7 = AnteproyectoFC.tramo[var18][var4 + 1];
               var13 = (int)(AnteproyectoFC.zImag[var18][var2] / 2.0D);
               var16 = this.eje - (int)(AnteproyectoFC.yImag[var18][var2] * 2.0D);
               if (AnteproyectoFC.zImag[var18][var2] > SistemaFC.valZE[var2] && AnteproyectoFC.zImag[var18][var2] < SistemaFC.valZE[var7]) {
                  var31.drawLine(var13, this.eje, var13, var16);
               }

               if (AnteproyectoFC.verImagenVirtual == 1 && var4 == var5 - 1 && var18 == 0 && SistemaFC.existeObjeto[1] == 0 && AnteproyectoFC.zImag[var18][var27] < SistemaFC.valZE[var26]) {
                  var31.setStroke(var29);
                  var31.drawLine(var13, this.eje, var13, var16);
                  var31.setColor(Color.green);

                  for(var3 = 0; var3 < 11; ++var3) {
                     if (AnteproyectoFC.pasa[var18][var26][var3] != 0) {
                        var14 = (int)(AnteproyectoFC.zpRayo[var18][var26][var3] / 2.0D);
                        var17 = this.eje - (int)(AnteproyectoFC.ypRayo[var18][var26][var3] * 2.0D);
                        var31.drawLine(var13, this.eje, var14, var17);
                     }
                  }

                  if (SistemaFC.campo[0] != 0.0D) {
                     var31.setColor(Color.cyan);

                     for(var3 = 11; var3 < 22; ++var3) {
                        if (AnteproyectoFC.pasa[var18][var26][var3] != 0) {
                           var14 = (int)(AnteproyectoFC.zpRayo[var18][var26][var3] / 2.0D);
                           var17 = this.eje - (int)(AnteproyectoFC.ypRayo[var18][var26][var3] * 2.0D);
                           var31.drawLine(var13, var16, var14, var17);
                        }
                     }
                  }

                  var31.setStroke(var30);
                  var31.setColor(new Color(0, 100, 255));
               }
            }
         }
      }

      if (SistemaFC.existeObjeto[0] != 0) {
         if (SistemaFC.existeObjeto[1] != 1) {
            var31.setColor(Color.white);
            int var32 = (int)(SistemaFC.valZE[AnteproyectoFC.numDiafragma] / 2.0D);
            var31.drawString("D.A.", var32 - 10, this.eje - 75);
            var31.setColor(Color.magenta);
            int var34 = (int)(AnteproyectoFC.zPE / 2.0D) - 1;
            int var33 = (int)AnteproyectoFC.DPE;
            if (var33 < 100) {
               var31.drawLine(var34, this.eje + var33, var34, this.eje + 100);
               var31.drawLine(var34, this.eje - var33, var34, this.eje - 90);
            }

            var31.drawString("P.E.", var34 - 10, this.eje - 90);
            int var35 = (int)(AnteproyectoFC.zPS / 2.0D) + 1;
            var33 = (int)AnteproyectoFC.DPS;
            if (var33 < 100) {
               var31.drawLine(var35, this.eje + var33, var35, this.eje + 90);
               var31.drawLine(var35, this.eje - var33, var35, this.eje - 100);
            }

            var31.drawString("P.S.", var35 - 10, this.eje + 100);
         }
      }
   }

   void dibujaElementos(Graphics var1) {
      var1.setColor(Color.white);

      for(int var2 = 1; var2 < 7; ++var2) {
         double var9;
         double var21;
         if (SistemaFC.tipoE[var2] == 1) {
            if (SistemaFC.valPE[var2] != 0.0D) {
               double var19 = SistemaFC.radioDib[var2];
               var21 = (double)AnteproyectoFC.diamMax / 2.0D;
               var9 = SistemaFC.valZE[var2];
               double var7 = SistemaFC.espesor[var2];
               double var11 = var9 - var7 / 2.0D;
               double var13 = var9 + var7 / 2.0D;
               double var15 = var11 + SistemaFC.valDibPE[var2];
               double var17 = var13 - SistemaFC.valDibPE[var2];
               this.dibujaSuperficie(var1, var11, var19, var21);
               this.dibujaSuperficie(var1, var13, -var19, var21);
               if (SistemaFC.valPE[var2] < 0.0D) {
                  int var5 = (int)(var15 / 2.0D);
                  int var6 = (int)(var17 / 2.0D);
                  int var4 = this.eje - (int)var21 * 2 - 4;
                  var1.drawLine(var5, var4, var6, var4);
                  var4 = this.eje + (int)var21 * 2 + 4;
                  var1.drawLine(var5, var4, var6, var4);
               }
            }
         } else if (SistemaFC.valDE[var2] != (double)AnteproyectoFC.diamMax) {
            var9 = SistemaFC.valZE[var2];
            var21 = SistemaFC.valDE[var2] / 2.0D;
            this.dibujaDiafragma(var1, var9, var21);
         }
      }

   }

   void dibujaSuperficie(Graphics var1, double var2, double var4, double var6) {
      double var12 = var2 / 2.0D;
      double var14 = var6 * 2.0D + 4.0D;
      double var16 = var4 * 6.0D;
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

   public void redraw() {
      this.repaint();
   }
}
