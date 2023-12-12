package appletfibra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class FieldPanel extends JPanel {
   YoungColor l2rgb = new YoungColor();
   double[] valsx = new double[40];
   double[] valsy = new double[40];
   int l;
   int m;

   public void SetData(double[] vx, double[] vy, int _l, int _m) {
      for(int i = 0; i < 40; ++i) {
         this.valsx[i] = vx[i];
         this.valsy[i] = vy[i];
      }

      this.l = _l;
      this.m = _m;
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      float[] hsb = new float[3];
      int midax = this.getWidth();
      int miday = this.getHeight();
      int mida = midax > miday ? miday : midax;
      double[] aux = new double[mida / 2 + 1];
      double[][] I = new double[mida][mida];
      Color auxColor = this.l2rgb.lambda2RGB((int)AppletFibra.FibraM.lambda);
      Color ncolor = this.l2rgb.lambda2RGB((int)AppletFibra.FibraM.lambda);
      hsb = Color.RGBtoHSB(ncolor.getRed(), ncolor.getGreen(), ncolor.getBlue(), hsb);
      g.setColor(Color.black);
      g.fillRect(0, 0, midax, miday);

      int i;
      for(i = 0; i < mida / 2; ++i) {
         aux[i] = this.valsy[(int)((double)i / (double)(mida / 2 - 1) * 39.0D)];
      }

      BufferedImage BI = new BufferedImage(mida, mida, 1);

      int j;
      for(i = 0; i < mida; ++i) {
         for(j = 0; j < mida; ++j) {
            int i1 = (int)Math.sqrt((double)((i - mida / 2) * (i - mida / 2) + (j - mida / 2) * (j - mida / 2)));
            double phi = Math.atan2((double)(j - mida / 2), (double)(i - mida / 2));
            if (i1 < mida / 2) {
               I[i][j] = aux[i1] * Math.abs(Math.cos((double)this.l * phi));
            } else {
               I[i][j] = 0.0D;
            }
         }
      }

      double max = I[0][0];

      for(i = 0; i < mida; ++i) {
         for(j = 0; j < mida; ++j) {
            if (I[i][j] > max) {
               max = I[i][j];
            }
         }
      }

      if (max != 0.0D) {
         for(i = 0; i < mida; ++i) {
            for(j = 0; j < mida; ++j) {
               I[i][j] /= max;
               BI.setRGB(i, j, Color.getHSBColor(hsb[0], hsb[1], (float)I[i][j]).getRGB());
            }
         }

         g.clearRect(0, 0, 1, 2);
         ((Graphics2D)g).drawImage(BI, new AffineTransformOp(new AffineTransform(), 1), (midax - mida) / 2, 0);
         g.setColor(Color.green);
         g.drawOval((int)((double)((midax - mida) / 2) + (double)mida / 1.2D * 0.1D), (int)((double)mida * 0.1D / 1.2D), (int)((double)mida / 1.2D), (int)((double)mida / 1.2D));
      }
   }
}
