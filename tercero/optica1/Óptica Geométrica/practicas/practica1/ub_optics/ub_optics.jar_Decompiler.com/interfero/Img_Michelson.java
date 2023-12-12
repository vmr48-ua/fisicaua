package interfero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Img_Michelson extends JPanel {
   BorderLayout borderLayout1 = new BorderLayout();
   double focal = 50.0D;
   double lambda = 633.0D;
   double n = 1.0D;
   double d = 0.0D;
   double angle = 0.0D;
   double d1 = 10.0D;
   double d2;
   boolean twyman;

   public Img_Michelson() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void jbInit() throws Exception {
      this.setLayout(this.borderLayout1);
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(230, 230));
      this.setPreferredSize(new Dimension(230, 230));
   }

   public void putAtributos(double l, double dist, double nref) {
      this.lambda = l;
      this.n = nref;
      this.d = dist;
      this.d2 = this.d1 + this.d;
      this.twyman = Interferometros.tipoFuente;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_x = dim.width;
      int dim_y = dim.height;
      YoungColor ycolor = new YoungColor();
      Color color_r = ycolor.lambda2RGB((int)this.lambda);
      double r_max_d = Math.sqrt(Math.pow((double)(dim_x / 2), 2.0D) + Math.pow((double)(dim_y / 2), 2.0D));
      int r_max = (int)r_max_d;
      double[] brillo = new double[r_max];
      double cos = 0.0D;
      double x = 0.0D;
      double y = 0.0D;
      double pix = Interferometros.finestra / r_max_d;
      if (dim_x > 0 && dim_y > 0) {
         if (!this.twyman) {
            for(int i = 0; i < r_max; ++i) {
               cos = Math.cos(6.283185307179586D * this.n * this.d * Math.cos(Math.atan2(pix * (double)i, this.focal)) / (this.lambda * 1.0E-6D));
               brillo[i] = cos * cos;
            }

            double brillo_max = brillo[0];
            double brillo_min = brillo[0];

            for(int i = 0; i < r_max; ++i) {
               if (brillo[i] > brillo_max) {
                  brillo_max = brillo[i];
               }

               if (brillo[i] < brillo_min) {
                  brillo_min = brillo[i];
               }
            }

            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(color_r.getRed(), color_r.getGreen(), color_r.getBlue(), hsb);
            new Color(0, 0, 0);

            for(int j = 0; j < r_max; ++j) {
               int i = r_max - j - 1;
               if (brillo_max - brillo_min < 1.0E-5D) {
                  brillo[i] = 1.0D;
               } else {
                  brillo[i] = (brillo[i] - brillo_min) / (brillo_max - brillo_min);
               }

               hsb[2] = (float)brillo[i];
               x = (double)(dim_x / 2 - i);
               y = (double)(dim_y / 2 - i);
               Color clin = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
               g2.setPaint(clin);
               g2.fillOval((int)x, (int)y, 2 * i, 2 * i);
            }
         } else {
            cos = Math.cos(6.283185307179586D * this.n * this.d / (this.lambda * 1.0E-6D));
            cos *= cos;
            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(color_r.getRed(), color_r.getGreen(), color_r.getBlue(), hsb);
            new Color(0, 0, 0);
            hsb[2] = (float)cos;
            Color clin = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            g2.setPaint(clin);
            g2.fillRect(0, 0, dim_x, dim_y);
         }
      }

   }
}
