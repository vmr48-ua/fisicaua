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

public class GraficoDesviacionArcoIris extends JPanel {
   int lambda;
   double param_b;
   int lang;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;
   String[][] textograf;

   public GraficoDesviacionArcoIris() {
      this.df = new DecimalFormat("#.##", this.df_symb);
      this.textograf = new String[][]{{"Parámetro de impacto", "Paràmetre d'impacte", "Impact parameter"}, {"ang. in. desv. min", "ang. in. desv. min", "min. dev. ang. in"}, {"ang. min. no tran.", "ang. min. no tran.", "min. ang. no tran."}, {"ang", "ang", "ang"}, {"desv", "desv", "dev"}};

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
   }

   public void putAtributos(double param_imp, int longonda, int idioma) {
      this.lambda = longonda;
      this.param_b = param_imp;
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
      g2.drawString("0", 25, dim_Ly - 15);
      g2.drawString("1", dim_Lx - 15, dim_Ly - 15);
      g2.drawString(this.textograf[3][this.lang], 3, dim_Ly / 2 - 14);
      g2.drawString(this.textograf[4][this.lang], 3, dim_Ly / 2);
      g2.drawString(this.textograf[0][this.lang], dim_Lx / 2 - 50, dim_Ly - 10);
      double delta_b = 1.0D / (double)(dim_Lx - 40);
      double delta_ang = 200.0D / (double)(dim_Ly - 40);
      double max_ang = 180.0D;
      double min_ang = 0.0D;
      g2.setFont(fuente);
      g2.drawString(this.df.format(max_ang) + "º", 5, 15);
      g2.drawString(this.df.format(min_ang) + "º", 5, dim_Ly - 30);
      YoungColor ycolores = new YoungColor();
      double n0 = 1.323585015D;
      double nA = 3878.664771D;
      double nB = -1.132388308E8D;
      double lambda2 = (double)this.lambda * (double)this.lambda;
      double lambda4 = lambda2 * lambda2;
      double n = n0 + nA / lambda2 + nB / lambda4;
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      g2.setPaint(ncolor);

      int i;
      for(i = 30; i < dim_Lx - 10; ++i) {
         double b = (double)(i - 30) * delta_b;
         double alpha = Math.asin(b);
         double beta = Math.asin(b / n);
         double desv_p = (3.141592653589793D + 2.0D * alpha - 4.0D * beta) * 57.29577951308232D;
         double desv_s = (-2.0D * alpha + 6.0D * beta) * 57.29577951308232D;
         double b2 = (double)(i - 30 + 1) * delta_b;
         double alpha2 = Math.asin(b2);
         double beta2 = Math.asin(b2 / n);
         double desv_p2 = (3.141592653589793D + 2.0D * alpha2 - 4.0D * beta2) * 57.29577951308232D;
         double desv_s2 = (-2.0D * alpha2 + 6.0D * beta2) * 57.29577951308232D;
         if (desv_p != 0.0D && desv_p2 != 0.0D) {
            double posA = (double)(dim_Ly - 40) * (desv_p - min_ang) / (max_ang - min_ang);
            double posB = (double)(dim_Ly - 40) * (desv_p2 - min_ang) / (max_ang - min_ang);
            g2.drawLine(i, (int)((double)(dim_Ly - 30) - posA), i + 1, (int)((double)(dim_Ly - 30) - posB));
         }

         if (desv_s != 0.0D && desv_s2 != 0.0D) {
            double posA2 = (double)(dim_Ly - 40) * (desv_s - min_ang) / (max_ang - min_ang);
            double posB2 = (double)(dim_Ly - 40) * (desv_s2 - min_ang) / (max_ang - min_ang);
            g2.drawLine(i, (int)((double)(dim_Ly - 30) - posA2), i + 1, (int)((double)(dim_Ly - 30) - posB2));
         }
      }

      g2.setPaint(Color.white);
      i = (int)(this.param_b / delta_b) + 30;
      double b_min_primary = Math.sqrt((4.0D - n * n) / 3.0D);
      double b_min_secondary = Math.sqrt((9.0D - n * n) / 8.0D);
      if (!(Math.abs(this.param_b - b_min_primary) < 0.01D) && !(Math.abs(this.param_b - b_min_secondary) < 0.01D)) {
         g2.setPaint(Color.white);
      } else {
         g2.setPaint(ncolor);
      }

      g2.drawLine(i, 10, i, dim_Ly - 30);
   }
}
