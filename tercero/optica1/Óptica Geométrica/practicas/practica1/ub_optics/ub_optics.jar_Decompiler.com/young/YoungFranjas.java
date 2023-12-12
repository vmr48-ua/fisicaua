package young;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;

public class YoungFranjas extends JPanel {
   int n_rendijas;
   int lambda;
   double d_rendijas;
   double d_plano;
   double n_refrac;
   boolean label_int_log = false;

   public YoungFranjas() {
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

   public void putAtributos(int rendijas, int l_onda, double a, double plano, double nrefrac, boolean label_int) {
      this.n_rendijas = rendijas;
      this.lambda = l_onda;
      this.d_rendijas = a;
      this.d_plano = plano;
      this.n_refrac = nrefrac;
      this.label_int_log = label_int;
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
         double dist_min;
         if (this.n_rendijas == 2) {
            dist_min = dist_max;
         } else {
            dist_min = dist_max / (double)this.n_rendijas;
         }

         if (dist_max >= 0.5D) {
            pix = 5.0D / (double)dim_x;
         }

         if (dist_max < 0.5D) {
            pix = 2.0D / (double)dim_x;
         }

         if (dist_max <= 0.1D && dist_min >= 0.05D) {
            pix = 1.0D / (double)dim_x;
         }

         if (dist_max <= 0.1D && dist_min < 0.05D) {
            pix = 0.5D / (double)dim_x;
         }

         if (dist_max <= 0.1D && dist_min < 0.02D) {
            pix = 0.25D / (double)dim_x;
         }

         float[] hsb = new float[3];
         hsb = Color.RGBtoHSB(ncolor.getRed(), ncolor.getGreen(), ncolor.getBlue(), hsb);
         double[] brillo = new double[dim_x];

         int i;
         for(i = 0; i < dim_x; ++i) {
            double equis = pix * ((double)i - (double)dim_x / 2.0D);
            double nume = 3.141592653589793D * equis * this.d_rendijas * this.n_refrac;
            double denom = (double)this.lambda * 0.001D * this.d_plano;
            double param = nume / denom;
            if (this.n_rendijas == 2) {
               brillo[i] = Math.cos(param) * Math.cos(param);
            } else if (Math.sin(param) != 0.0D) {
               brillo[i] = Math.sin((double)this.n_rendijas * param) * Math.sin((double)this.n_rendijas * param) / (Math.sin(param) * Math.sin(param));
            } else {
               brillo[i] = (double)this.n_rendijas * (double)this.n_rendijas;
            }
         }

         if (this.label_int_log) {
            for(i = 0; i < dim_x; ++i) {
               brillo[i] = Math.log(brillo[i]);
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
            brillo[i] = (brillo[i] - min_brillo) / (max_brillo - min_brillo);
            hsb[2] = (float)brillo[i];
            Color clin = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            g2.setPaint(clin);
            g2.draw(new Double((double)i, 0.0D, (double)i, (double)dim_y));
         }
      }

   }
}
