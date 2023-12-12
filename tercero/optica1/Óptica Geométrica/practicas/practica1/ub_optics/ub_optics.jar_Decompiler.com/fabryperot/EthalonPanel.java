package fabryperot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D.Double;
import javax.swing.JPanel;

public class EthalonPanel extends JPanel {
   YoungColor l2rgb = new YoungColor();

   public void UpdateFabry(FabryCalc fabry) {
      AppletFabry.fabry1 = fabry;
   }

   public void paintComponent(Graphics g) {
      float[] hsb = new float[3];
      int midax = this.getWidth();
      int miday = this.getHeight();
      float[] aux = new float[midax / 2 + 1];
      Color auxColor = this.l2rgb.lambda2RGB((int)AppletFabry.fabry1.lambda);
      Color ncolor = this.l2rgb.lambda2RGB((int)AppletFabry.fabry1.lambda);
      double[][] IT1 = AppletFabry.fabry1.getIT();
      double[][] IT2 = AppletFabry.fabry2.getIT();
      hsb = Color.RGBtoHSB(ncolor.getRed(), ncolor.getGreen(), ncolor.getBlue(), hsb);
      g.setColor(Color.black);
      g.fillRect(0, 0, midax, miday);

      int i;
      for(i = midax / 2; i >= 0; --i) {
         double r = (double)i / (double)midax * AppletFabry.fabry1.sizeScreen * 2.0D;
         int i1 = 0;

         boolean var11;
         for(var11 = false; IT1[0][i1] < r && i1 < AppletFabry.fabry1.numint; ++i1) {
         }

         int i2 = i1 - 1;
         if (i2 < 0) {
            i2 = 0;
            i1 = 1;
         }

         if (i1 < AppletFabry.fabry1.numint && i2 < AppletFabry.fabry1.numint) {
            try {
               aux[i] = (float)((IT1[1][i2] * (r - IT1[0][i1]) + IT1[1][i1] * (IT1[0][i2] - r)) / (IT1[0][i2] - IT1[0][i1]));
            } catch (Exception var23) {
               aux[i] = 0.0F;
            }
         } else {
            aux[i] = 0.0F;
         }

         i1 = 0;

         for(var11 = false; IT2[0][i1] < r && i1 < AppletFabry.fabry2.numint; ++i1) {
         }

         i2 = i1 - 1;
         if (i2 < 0) {
            i2 = 0;
            i1 = 1;
         }

         if (i1 < AppletFabry.fabry2.numint && i2 < AppletFabry.fabry2.numint) {
            try {
               aux[i] += (float)((IT2[1][i2] * (r - IT2[0][i1]) + IT2[1][i1] * (IT2[0][i2] - r)) / (IT2[0][i2] - IT2[0][i1]));
            } catch (Exception var22) {
               aux[i] += 0.0F;
            }
         } else {
            aux[i] = 0.0F;
         }
      }

      double x = (double)aux[0];

      for(i = 1; i <= midax / 2; ++i) {
         if ((double)aux[i] > x) {
            x = (double)aux[i];
         }
      }

      if (x > 1.0D) {
         for(i = 0; i <= midax / 2; ++i) {
            aux[i] /= (float)x;
         }
      }

      for(i = midax / 2; i >= 0; --i) {
         auxColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], aux[i]));
         g.setColor(auxColor);
         ((Graphics2D)g).fill(new Double((double)midax / 2.0D - (double)i, (double)miday / 2.0D - (double)i, (double)(2 * i), (double)(2 * i)));
      }

   }
}
