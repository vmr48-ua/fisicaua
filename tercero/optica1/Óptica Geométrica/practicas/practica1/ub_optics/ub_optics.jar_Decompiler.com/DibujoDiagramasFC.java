import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class DibujoDiagramasFC extends Canvas {
   static int xc = 45;
   static int yc = 45;
   static int[][][] iD = new int[2][91][91];
   static double fiEje;
   static double fiCampo;

   public void paint(Graphics var1) {
      var1.setColor(Color.yellow);
      var1.drawLine(0, 0, 0, 189);
      var1.drawRect(0, 189, 379, 0);
      var1.drawLine(379, 189, 379, 0);
      var1.drawLine(189, 189, 189, 0);
      if (SistemaFC.existeObjeto[0] != 0) {
         if (ExactoFC.dibuja != 0) {
            this.dibujaDiagramas(var1);
         }
      }
   }

   void dibujaDiagramas(Graphics var1) {
      double[] var18 = new double[2];
      Color var19 = new Color(0, 255, 255);
      Color var20 = new Color(100, 100, 255);
      Color[] var21 = new Color[]{Color.black, Color.green, var20, Color.red, var19, Color.yellow, Color.magenta, Color.white};
      int var22 = ExactoFC.numRayos;
      int var23 = ExactoFC.numColores;
      int var24 = FuncionesExactasFC.numSup;
      int var25 = var22 * var23;
      double[] var30 = new double[2];
      double var31 = 0.03D;
      double var12 = FuncionesExactasFC.imagParax + ExactoFC.valZImag;

      int var2;
      int var3;
      int var5;
      for(var5 = 0; var5 < 2; ++var5) {
         for(var2 = 0; var2 < 91; ++var2) {
            for(var3 = 0; var3 < 91; ++var3) {
               iD[var5][var2][var3] = 0;
            }
         }
      }

      for(var5 = 0; var5 < 2; ++var5) {
         double var28 = 0.0D;

         for(int var4 = 0; var4 < var23; ++var4) {
            var18[var5] = FuncionesExactasFC.cRayo[var5][var4][var24 + 1][0] * var12 + FuncionesExactasFC.dRayo[var5][var4][var24 + 1][0];

            for(var3 = 0; var3 < var22; ++var3) {
               if (FuncionesExactasFC.pasa[var5][var4][var24][var3] != 0) {
                  double var14 = FuncionesExactasFC.aRayo[var5][var4][var24 + 1][var3] * var12 + FuncionesExactasFC.bRayo[var5][var4][var24 + 1][var3];
                  int var8 = (int)(90.0D * var14 / ExactoFC.escala);
                  double var16 = FuncionesExactasFC.cRayo[var5][var4][var24 + 1][var3] * var12 + FuncionesExactasFC.dRayo[var5][var4][var24 + 1][var3] - var18[var5];
                  int var9 = -((int)(90.0D * var16 / ExactoFC.escala));
                  double var26 = var14 * var14 + var16 * var16;
                  var28 += var31 / (var31 + var26);
                  if (var8 >= -xc && var8 <= xc && var9 >= -yc && var9 <= yc) {
                     int var10 = xc + var8;
                     int var11 = yc + var9;
                     if (var23 == 1) {
                        iD[var5][var10][var11] = 1;
                     }

                     if (var23 == 3) {
                        if (var4 == 0) {
                           iD[var5][var10][var11] = 1;
                        }

                        if (var4 == 1) {
                           if (iD[var5][var10][var11] == 0) {
                              iD[var5][var10][var11] = 2;
                           }

                           if (iD[var5][var10][var11] == 1) {
                              iD[var5][var10][var11] = 4;
                           }
                        }

                        if (var4 == 2) {
                           if (iD[var5][var10][var11] == 0) {
                              iD[var5][var10][var11] = 3;
                           }

                           if (iD[var5][var10][var11] == 1) {
                              iD[var5][var10][var11] = 5;
                           }

                           if (iD[var5][var10][var11] == 2) {
                              iD[var5][var10][var11] = 6;
                           }

                           if (iD[var5][var10][var11] == 4) {
                              iD[var5][var10][var11] = 7;
                           }
                        }
                     }

                     iD[var5][xc - var8][var11] = iD[var5][var10][var11];
                  }
               }
            }
         }

         var30[var5] = (double)((int)(1000.0D * var28 / (double)var25)) / 10.0D;
         ExactoFC.labelValMerito[var5].setText(var30[var5] + " %");

         for(var2 = 1; var2 < 180; var2 += 2) {
            for(var3 = 1; var3 < 180; var3 += 2) {
               var1.setColor(var21[iD[var5][var2 / 2][var3 / 2]]);
               var1.drawRect(var2 + var5 * 190, var3, 2, 2);
            }
         }
      }

   }

   public void redraw() {
      this.repaint();
   }
}
