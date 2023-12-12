package young;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;

public class CoherenciaFranjas extends JPanel {
   int n_rendijas = 2;
   int lambda;
   double d_rendijas;
   double d_plano;
   double n_refrac;
   double d_fuente;
   int tipo_fuente;

   public CoherenciaFranjas() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(350, 230));
   }

   public void putAtributos(int tipofuente, double dfuente, int l_onda, double a, double plano, double nrefrac) {
      this.n_rendijas = 2;
      this.tipo_fuente = tipofuente;
      this.d_fuente = dfuente;
      this.lambda = l_onda;
      this.d_rendijas = a;
      this.d_plano = plano;
      this.n_refrac = nrefrac;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_x = dim.width;
      int dim_y = dim.height;
      if (dim_x > 0 && dim_y > 0) {
         Color blanco = Color.white;
         YoungColor ycolores = new YoungColor();
         Color ncolor = ycolores.lambda2RGB(this.lambda);
         double pix = 5.0D / (double)dim_x;
         double dist_max = (double)this.lambda * this.d_plano * 0.001D / (this.n_refrac * this.d_rendijas);
         if (dist_max >= 0.5D) {
            pix = 5.0D / (double)dim_x;
         }

         if (dist_max < 0.5D) {
            pix = 2.0D / (double)dim_x;
         }

         if (dist_max <= 0.1D) {
            pix = 1.0D / (double)dim_x;
         }

         float[] hsb = new float[3];
         hsb = Color.RGBtoHSB(ncolor.getRed(), ncolor.getGreen(), ncolor.getBlue(), hsb);
         double[] brillo = new double[dim_x];

         int i;
         for(i = 0; i < dim_x; ++i) {
            double equis = pix * ((double)i - (double)dim_x / 2.0D);
            double nume1 = 3.141592653589793D * equis * this.d_rendijas * this.n_refrac;
            double denom1 = (double)this.lambda * 0.001D * this.d_plano;
            double param1 = nume1 / denom1;
            double factor2 = 3.141592653589793D * this.n_refrac / ((double)this.lambda * 1.0E-6D);
            double termino2 = equis * this.d_rendijas / (this.d_plano * 1000.0D) + this.d_fuente * this.d_rendijas / ((7.0D - this.d_plano) * 1000.0D);
            double param2 = factor2 * termino2;
            if (this.tipo_fuente == 0) {
               brillo[i] = Math.cos(param2) * Math.cos(param2);
            }

            if (this.tipo_fuente == 1) {
               brillo[i] = Math.cos(param1) * Math.cos(param1) + Math.cos(param2) * Math.cos(param2);
            }

            if (this.tipo_fuente == 2) {
               if (this.d_fuente == 0.0D) {
                  brillo[i] = Math.cos(param1) * Math.cos(param1);
               } else {
                  double factor3 = 3.141592653589793D * this.d_rendijas * this.n_refrac / ((double)this.lambda * 1.0E-6D);
                  double seno3 = Math.sin(factor3 * (this.d_fuente / ((7.0D - this.d_plano) * 1000.0D)));
                  double coseno3 = Math.cos(2.0D * factor3 * (equis / (this.d_plano * 1000.0D)));
                  brillo[i] = 2.0D * this.d_fuente + 2.0D * ((7.0D - this.d_plano) * 1000.0D / factor3) * seno3 * coseno3;
               }
            }
         }

         double max_brillo = brillo[0];
         double min_brillo = brillo[0];

         for(i = 0; i < dim_x; ++i) {
            if (max_brillo < brillo[i]) {
               max_brillo = brillo[i];
            }

            if (min_brillo > brillo[i]) {
               min_brillo = brillo[i];
            }
         }

         for(i = 0; i < dim_x; ++i) {
            brillo[i] /= max_brillo;
            hsb[2] = (float)brillo[i];
            Color clin = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            g2.setPaint(clin);
            g2.draw(new Double((double)i, 0.0D, (double)i, (double)dim_y));
         }
      }

   }
}
