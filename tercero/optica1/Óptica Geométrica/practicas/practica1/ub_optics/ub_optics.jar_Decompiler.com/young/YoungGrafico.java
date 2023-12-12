package young;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D.Double;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class YoungGrafico extends JPanel {
   int n_rendijas;
   int lambda;
   double d_rendijas;
   double d_plano;
   double n_refrac;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;

   public YoungGrafico() {
      this.df = new DecimalFormat("#.##", this.df_symb);

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(new Color(0, 0, 0));
      this.setMinimumSize(new Dimension(350, 230));
   }

   public void putAtributos(int rendijas, int l_onda, double a, double plano, double nrefrac) {
      this.n_rendijas = rendijas;
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
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      if (dim_x > 0 && dim_y > 0) {
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

         g2.setPaint(Color.white);
         g2.draw(new Double(0.0D, (double)dim_y - 5.0D, (double)dim_x, (double)dim_y - 5.0D));
         g2.draw(new Double((double)dim_x / 2.0D, 0.0D, (double)dim_x / 2.0D, (double)dim_y));
         double equis = 0.0D;
         double nume = 0.0D;
         double denom = 0.0D;
         double param = 0.0D;
         double[] valor = new double[dim_x];
         double equis_0 = -pix * ((double)dim_x / 2.0D);
         double nume_0 = 3.141592653589793D * equis * this.d_rendijas * this.n_refrac;
         double denom_0 = (double)this.lambda * 0.001D * this.d_plano;
         double param_0 = nume / denom;
         double valor_0;
         if (this.n_rendijas == 2) {
            valor_0 = Math.cos(param_0) * Math.cos(param_0);
         } else {
            valor_0 = Math.sin((double)this.n_rendijas * param_0) * Math.sin((double)this.n_rendijas * param_0) / (Math.sin(param_0) * Math.sin(param_0));
         }

         int i;
         for(i = 0; i < dim_x; ++i) {
            equis = pix * ((double)i - (double)dim_x / 2.0D);
            nume = 3.141592653589793D * equis * this.d_rendijas * this.n_refrac;
            denom = (double)this.lambda * 0.001D * this.d_plano;
            param = nume / denom;
            if (this.n_rendijas == 2) {
               valor[i] = Math.cos(param) * Math.cos(param);
            } else if (Math.sin(param) != 0.0D) {
               valor[i] = Math.sin((double)this.n_rendijas * param) * Math.sin((double)this.n_rendijas * param) / (Math.sin(param) * Math.sin(param));
            } else {
               valor[i] = (double)this.n_rendijas * (double)this.n_rendijas;
            }
         }

         double max_valor = valor[0];
         double min_valor = valor[0];

         for(i = 0; i < dim_x; ++i) {
            if (max_valor < valor[i]) {
               max_valor = valor[i];
            }

            if (min_valor > valor[i]) {
               min_valor = valor[i];
            }
         }

         valor_0 = (valor_0 - min_valor) / (max_valor - min_valor);
         if (valor_0 >= 0.0D && valor_0 <= 1.0D) {
            valor_0 = (double)dim_y - 10.0D + 5.0D - valor_0 * (double)(dim_y - 10);
         } else {
            valor_0 = 0.0D;
         }

         for(i = 0; i < dim_x; ++i) {
            valor[i] = (valor[i] - min_valor) / (max_valor - min_valor);
            valor[i] = (double)dim_y - 10.0D + 5.0D - valor[i] * (double)(dim_y - 10);
            g2.setPaint(ncolor);
            g2.draw(new Double((double)(i - 1), valor_0, (double)i, valor[i]));
            valor_0 = valor[i];
         }

         char pto = '.';
         this.df_symb.setDecimalSeparator(pto);
         this.df.setDecimalFormatSymbols(this.df_symb);
         g2.setPaint(Color.white);
         double pos_y_max = (double)dim_y - 10.0D + 5.0D - (double)(dim_y - 10);
         double pos_y_uno = (double)dim_y - 10.0D + 5.0D - (1.0D - min_valor) / (max_valor - min_valor) * (double)(dim_y - 10);
         g2.draw(new Double((double)dim_x / 2.0D - 5.0D, pos_y_max, (double)dim_x / 2.0D + 5.0D, pos_y_max));
         g2.draw(new Double((double)dim_x / 2.0D - 5.0D, pos_y_uno, (double)dim_x / 2.0D + 5.0D, pos_y_uno));
         String s = "max= " + this.df.format(max_valor);
         String uno = "1";
         g2.drawString(s, dim_x / 2 + 6, 10);
         if ((int)pos_y_max != (int)pos_y_uno) {
            g2.drawString(uno, dim_x / 2 + 6, (int)pos_y_uno);
         }
      }

   }
}
