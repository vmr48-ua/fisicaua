package dispersion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class DibujoLineasPrisma extends JPanel {
   double angmedioprisma;
   double ang_in;
   int[] lambdas = new int[7];
   double[] index = new double[7];
   double[] angdesv = new double[7];
   double[] epsilon2prima = new double[7];
   boolean zoom = false;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;

   public DibujoLineasPrisma() {
      this.df = new DecimalFormat("#.##", this.df_symb);

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(365, 48));
      this.setPreferredSize(new Dimension(365, 48));
   }

   public void putAtributos(double angprisma, double angprisma_in, int[] longonda, double[] indices, double[] angdesviacion, boolean label_zoom, double[] ange2prima_prisma) {
      this.angmedioprisma = angprisma / 2.0D;
      this.ang_in = angprisma_in;

      for(int i = 0; i < 7; ++i) {
         this.lambdas[i] = longonda[i];
         this.index[i] = indices[i];
         this.angdesv[i] = angdesviacion[i];
         this.epsilon2prima[i] = ange2prima_prisma[i];
      }

      this.zoom = label_zoom;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Lx = dim.width;
      int dim_Ly = dim.height;
      YoungColor ycolores = new YoungColor();
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      Font fuente = new Font("Dialog", 1, 12);
      g2.setFont(fuente);
      g2.setColor(Color.white);
      g2.drawLine(0, 1, dim_Lx, 1);
      g2.drawLine(dim_Lx - 1, 1, dim_Lx - 1, dim_Ly - 1);
      g2.drawLine(0, dim_Ly - 1, dim_Lx - 1, dim_Ly - 1);
      g2.drawLine(0, 1, 0, dim_Ly - 1);
      Color ncolor;
      double pos;
      double delta_pix;
      double max_angdesv;
      if (!this.zoom) {
         max_angdesv = 0.0D;

         int i;
         for(i = 0; i < 7; ++i) {
            if (this.epsilon2prima[i] < 3.141592653589793D && max_angdesv < this.angdesv[i]) {
               max_angdesv = this.angdesv[i];
            }
         }

         double max_ang;
         if (max_angdesv > 2.0943951023931953D) {
            max_ang = max_angdesv;
         } else {
            max_ang = 2.0943951023931953D;
            if (max_angdesv < 1.5707963267948966D) {
               max_ang = 1.5707963267948966D;
            }

            if (max_angdesv < 1.3962634015954636D) {
               max_ang = 1.3962634015954636D;
            }

            if (max_angdesv < 1.2217304763960306D) {
               max_ang = 1.2217304763960306D;
            }

            if (max_angdesv < 1.0471975511965976D) {
               max_ang = 1.0471975511965976D;
            }
         }

         delta_pix = (double)dim_Lx / max_ang;

         for(i = 0; i < 7; ++i) {
            if (this.epsilon2prima[i] < 3.141592653589793D) {
               ncolor = ycolores.lambda2RGB(this.lambdas[i]);
               g2.setPaint(ncolor);
               pos = delta_pix * this.angdesv[i];
               g2.drawLine((int)pos, 2, (int)pos, dim_Ly - 2);
            }
         }

         g2.setPaint(Color.white);
         String str_min = "0º";
         String str_max = this.df.format(max_ang * 180.0D / 3.141592653589793D) + "º";
         g2.drawString(str_min, 2, 30);
         g2.drawString(str_max, dim_Lx - 20, 30);
      } else {
         max_angdesv = 0.0D;
         double min_angdesv = 3.141592653589793D;
         boolean label_rayos = false;

         int i;
         for(i = 0; i < 7; ++i) {
            if (this.epsilon2prima[i] < 3.141592653589793D) {
               if (max_angdesv < this.angdesv[i]) {
                  max_angdesv = this.angdesv[i];
               }

               if (min_angdesv > this.angdesv[i]) {
                  min_angdesv = this.angdesv[i];
               }

               label_rayos = true;
            }
         }

         if (max_angdesv - min_angdesv > 0.0D) {
            delta_pix = (double)(dim_Lx - 45) / (max_angdesv - min_angdesv);

            for(i = 0; i < 7; ++i) {
               if (this.epsilon2prima[i] < 3.141592653589793D) {
                  ncolor = ycolores.lambda2RGB(this.lambdas[i]);
                  g2.setPaint(ncolor);
                  pos = delta_pix * (this.angdesv[i] - min_angdesv) + 20.0D;
                  g2.fill(new Double(pos, 2.0D, 5.0D, (double)(dim_Ly - 4)));
               }
            }
         } else {
            delta_pix = (double)dim_Lx / 3.141592653589793D;

            for(i = 0; i < 7; ++i) {
               if (this.epsilon2prima[i] < 3.141592653589793D) {
                  ncolor = ycolores.lambda2RGB(this.lambdas[i]);
                  g2.setPaint(ncolor);
                  g2.fill(new Double((double)(dim_Lx / 2), 2.0D, 5.0D, (double)(dim_Ly - 4)));
               }
            }
         }

         if (label_rayos) {
            g2.setPaint(Color.white);
            String str_min_angdesv = this.df.format(min_angdesv * 57.29577951308232D) + "º";
            String str_max_angdesv = this.df.format(max_angdesv * 57.29577951308232D) + "º";
            g2.drawString(str_min_angdesv, 2, 30);
            g2.drawString(str_max_angdesv, dim_Lx - 36, 30);
         }
      }

   }
}
