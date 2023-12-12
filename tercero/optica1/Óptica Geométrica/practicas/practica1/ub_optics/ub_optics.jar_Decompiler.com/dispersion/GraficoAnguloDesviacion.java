package dispersion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class GraficoAnguloDesviacion extends JPanel {
   double ang_prisma;
   int[] lambdas = new int[7];
   double[] index = new double[7];
   double nd;
   int lang;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;
   String[][] textograf;

   public GraficoAnguloDesviacion() {
      this.df = new DecimalFormat("#.##", this.df_symb);
      this.textograf = new String[][]{{"Ángulo de incidencia", "Angle d'incidència", "Angle of incidence"}, {"ang. in. desv. min", "ang. in. desv. min", "min. dev. ang. in"}, {"ang. min. no tran.", "ang. min. no tran.", "min. ang. no tran."}, {"ang", "ang", "ang"}, {"desv", "desv", "dev"}};

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.gray);
      this.setMinimumSize(new Dimension(353, 218));
      this.setPreferredSize(new Dimension(353, 218));
   }

   public void putAtributos(double angprisma, int[] longonda, double[] indices, double n_d, int idioma) {
      this.ang_prisma = angprisma;

      for(int i = 0; i < 7; ++i) {
         this.lambdas[i] = longonda[i];
         this.index[i] = indices[i];
      }

      this.nd = n_d;
      this.lang = idioma;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Lx = dim.width;
      int dim_Ly = dim.height;
      Font fuente = new Font("Dialog", 1, 12);
      g2.setFont(fuente);
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      g2.setColor(Color.white);
      g2.drawLine(30, 10, 30, dim_Ly - 30);
      g2.drawLine(30, dim_Ly - 30, dim_Lx - 10, dim_Ly - 30);
      g2.drawLine(dim_Lx - 10, 10, dim_Lx - 10, dim_Ly - 30);
      g2.drawLine(30, 10, dim_Lx - 10, 10);
      g2.drawString("0º", 20, dim_Ly - 15);
      g2.drawString("90º", dim_Lx - 20, dim_Ly - 15);
      g2.drawString(this.textograf[3][this.lang], 3, dim_Ly / 2 - 14);
      g2.drawString(this.textograf[4][this.lang], 3, dim_Ly / 2);
      g2.drawString(this.textograf[0][this.lang], dim_Lx / 2 - 50, dim_Ly - 10);
      double delta_ang = 90.0D / (double)(dim_Lx - 40);
      double max_ang = 90.0D;
      double min_ang = 0.0D;
      max_ang = (Math.asin(Math.sqrt(this.index[0] * this.index[0] - 1.0D) * Math.sin(this.ang_prisma) - Math.cos(this.ang_prisma)) + 1.5707963267948966D - this.ang_prisma) * 57.29577951308232D;
      if (Double.isNaN(max_ang)) {
         max_ang = 0.0D;
      }

      min_ang = (2.0D * Math.asin(this.index[0] * Math.sin(this.ang_prisma / 2.0D)) - this.ang_prisma) * 57.29577951308232D;
      if (Double.isNaN(min_ang)) {
         min_ang = 0.0D;
      }

      for(int i = 0; i < 7; ++i) {
         double max_angi = (Math.asin(Math.sqrt(this.index[i] * this.index[i] - 1.0D) * Math.sin(this.ang_prisma) - Math.cos(this.ang_prisma)) + 1.5707963267948966D - this.ang_prisma) * 57.29577951308232D;
         if (Double.isNaN(max_angi)) {
            max_angi = 0.0D;
         }

         if (max_ang < max_angi) {
            max_ang = max_angi;
         }

         double min_angi = (2.0D * Math.asin(this.index[i] * Math.sin(this.ang_prisma / 2.0D)) - this.ang_prisma) * 57.29577951308232D;
         if (Double.isNaN(min_angi)) {
            min_angi = 0.0D;
         }

         if (min_ang > min_angi) {
            min_ang = min_angi;
         }
      }

      g2.setFont(fuente);
      g2.drawString(this.df.format(max_ang) + "º", 0, 15);
      g2.drawString(this.df.format(min_ang) + "º", 0, dim_Ly - 30);
      YoungColor ycolores = new YoungColor();

      Color ncolor;
      for(int k = 0; k < 7; ++k) {
         ncolor = ycolores.lambda2RGB(this.lambdas[k]);
         g2.setPaint(ncolor);
         double ang_limite = Math.asin(1.0D / this.index[k]);

         for(int i = 30; i < dim_Lx - 10; ++i) {
            double eps1 = (double)(i - 30) * delta_ang * 0.017453292519943295D;
            double eps1prima = Math.asin(Math.sin(eps1) / this.index[k]);
            double eps2 = this.ang_prisma - eps1prima;
            double angdesv;
            if (eps2 < ang_limite) {
               double sineps2prima = this.index[k] * Math.sin(eps2);
               double eps2prima = Math.asin(sineps2prima);
               if (Double.isNaN(eps2prima)) {
                  angdesv = 0.0D;
               } else {
                  angdesv = (eps1 - this.ang_prisma + eps2prima) * 57.29577951308232D;
               }
            } else {
               angdesv = 0.0D;
            }

            double eps1B = (double)(i - 30 + 1) * delta_ang * 0.017453292519943295D;
            double eps1primaB = Math.asin(Math.sin(eps1B) / this.index[k]);
            double eps2B = this.ang_prisma - eps1primaB;
            double angdesvB;
            if (eps2B < ang_limite) {
               double sineps2primaB = this.index[k] * Math.sin(eps2B);
               double eps2primaB = Math.asin(sineps2primaB);
               if (Double.isNaN(eps2primaB)) {
                  angdesvB = 0.0D;
               } else {
                  angdesvB = (eps1B - this.ang_prisma + eps2primaB) * 57.29577951308232D;
               }
            } else {
               angdesvB = 0.0D;
            }

            if (angdesv != 0.0D && angdesvB != 0.0D) {
               double posA = (double)(dim_Ly - 40) * (angdesv - min_ang) / (max_ang - min_ang);
               double posB = (double)(dim_Ly - 40) * (angdesvB - min_ang) / (max_ang - min_ang);
               g2.drawLine(i, (int)((double)(dim_Ly - 30) - posA), i + 1, (int)((double)(dim_Ly - 30) - posB));
            }
         }
      }

      double angdesvmin = Math.asin(this.nd * Math.sin(this.ang_prisma / 2.0D)) * 57.29577951308232D;
      if (!Double.isNaN(angdesvmin)) {
         double pos_angdesvmin = angdesvmin / delta_ang + 30.0D;
         ncolor = ycolores.lambda2RGB(588);
         g2.setPaint(ncolor);
         g2.drawLine((int)pos_angdesvmin, dim_Ly - 30, (int)pos_angdesvmin, 10);
         if (angdesvmin < 45.0D) {
            g2.drawString(this.textograf[1][this.lang] + " (nd) =" + this.df.format(angdesvmin) + "º", (int)pos_angdesvmin + 5, 30);
         } else {
            g2.drawString(this.textograf[1][this.lang] + " (nd) =" + this.df.format(angdesvmin) + "º", (int)pos_angdesvmin - 150, 30);
         }
      }

      double ang_limite_nd = Math.asin(1.0D / this.nd) * 57.29577951308232D;
      double e1_anglim_nd = Math.asin(this.nd * Math.sin(this.ang_prisma - Math.asin(1.0D / this.nd))) * 57.29577951308232D;
      if (!Double.isNaN(e1_anglim_nd) && e1_anglim_nd > 0.0D) {
         double pos_anglim_nd = e1_anglim_nd / delta_ang + 30.0D;
         g2.setPaint(Color.white);
         g2.drawLine((int)pos_anglim_nd, dim_Ly - 30, (int)pos_anglim_nd, 10);
         if (e1_anglim_nd < 45.0D) {
            g2.drawString(this.textograf[2][this.lang] + " (nd)=" + this.df.format(e1_anglim_nd) + "º", (int)pos_anglim_nd + 5, 45);
         } else {
            g2.drawString(this.textograf[2][this.lang] + " (nd)=" + this.df.format(e1_anglim_nd) + "º", (int)pos_anglim_nd - 160 + 5, 45);
         }
      }

   }
}
