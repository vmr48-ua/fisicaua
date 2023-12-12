import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

class DibujoPCFC extends Canvas {
   int eje = 105;
   DibujoSistemaFC dibujoSistema;

   public DibujoPCFC(DibujoSistemaFC var1) {
      this.dibujoSistema = var1;
      this.setBounds(30, 0, 770, 180);
   }

   public void paint(Graphics var1) {
      new Color(0, 255, 255);
      float[] var26 = new float[]{4.0F, 4.0F};
      BasicStroke var27 = new BasicStroke(1.0F, 1, 0, 1.0F, var26, 0.0F);
      BasicStroke var28 = new BasicStroke(1.0F);
      Graphics2D var29 = (Graphics2D)var1;
      var29.setColor(Color.white);
      var29.drawLine(0, 0, 800, 0);
      this.dibujoSistema.dibujaElementos(var29);
      FuncionesCardinalesFC.tablaTramosCard();

      int var18;
      for(var18 = 0; var18 < 2; ++var18) {
         FuncionesCardinalesFC.marchaParaxialCard(var18);
      }

      FuncionesCardinalesFC.normalizaCoeficientes();

      for(var18 = 0; var18 < 2; ++var18) {
         FuncionesCardinalesFC.interseccionesRayosCard(var18);
      }

      int var5 = AnteproyectoFC.numTramosCard;
      if (var5 >= 2) {
         var29.setColor(Color.yellow);
         var29.drawLine(0, this.eje, 800, this.eje);
         var29.setStroke(var27);

         int var2;
         int var7;
         int var16;
         int var17;
         for(var18 = 0; var18 < 2; ++var18) {
            if (var18 == 0) {
               var29.setColor(Color.green);
            } else {
               var29.setColor(Color.cyan);
            }

            byte var13 = 0;
            var16 = this.eje - (int)(AnteproyectoFC.bpRayoCard[var18][0] * 2.0D);
            short var14 = 800;
            if (AnteproyectoFC.apRayoCard[var18][0] == 0.0D) {
               var17 = var16;
            } else {
               var17 = this.eje - (int)((AnteproyectoFC.apRayoCard[var18][0] * 1600.0D + AnteproyectoFC.bpRayoCard[var18][0]) * 2.0D);
            }

            var29.drawLine(var13, var16, var14, var17);
            var2 = AnteproyectoFC.tramoCard[var5 - 1];
            var7 = AnteproyectoFC.tramoCard[var5];
            var13 = 0;
            var16 = this.eje - (int)(AnteproyectoFC.bpRayoCard[var18][var2] * 2.0D);
            var14 = 800;
            var17 = this.eje - (int)(AnteproyectoFC.yaRayoCard[var18][var7] * 2.0D);
            var29.drawLine(var13, var16, var14, var17);
         }

         var29.setStroke(var28);

         int var30;
         for(var18 = 0; var18 < 2; ++var18) {
            if (var18 == 0) {
               var29.setColor(Color.green);
            } else {
               var29.setColor(Color.cyan);
            }

            for(int var4 = 0; var4 < var5; ++var4) {
               var2 = AnteproyectoFC.tramoCard[var4];
               var7 = AnteproyectoFC.tramoCard[var4 + 1];
               if (var2 == 0) {
                  var30 = 0;
                  var16 = this.eje - (int)(AnteproyectoFC.bpRayoCard[var18][0] * 2.0D);
               } else {
                  var30 = (int)(AnteproyectoFC.zpRayoCard[var18][var2] / 2.0D);
                  var16 = this.eje - (int)(AnteproyectoFC.ypRayoCard[var18][var2] * 2.0D);
               }

               int var31 = (int)(AnteproyectoFC.zaRayoCard[var18][var7] / 2.0D);
               var17 = this.eje - (int)(AnteproyectoFC.yaRayoCard[var18][var7] * 2.0D);
               var29.drawLine(var30, var16, var31, var17);
            }
         }

         var29.setColor(Color.red);
         var30 = (int)(AnteproyectoFC.zF[1] / 2.0D);
         var29.drawLine(var30, this.eje - 90, var30, this.eje + 90);
         var29.drawString("F", var30 + 5, this.eje + 20);
         var30 = (int)(AnteproyectoFC.zF[0] / 2.0D);
         var29.drawLine(var30, this.eje - 90, var30, this.eje + 90);
         var29.drawString("F'", var30 + 5, this.eje + 20);
         var29.setColor(Color.magenta);
         var30 = (int)(AnteproyectoFC.zH[1] / 2.0D);
         var29.drawLine(var30, this.eje - 90, var30, this.eje + 90);
         var29.drawString("H", var30 + 5, this.eje + 20);
         var30 = (int)(AnteproyectoFC.zH[0] / 2.0D);
         var29.drawLine(var30, this.eje - 90, var30, this.eje + 90);
         var29.drawString("H'", var30 + 5, this.eje + 20);
      }
   }

   public void redraw() {
      this.repaint();
   }
}
