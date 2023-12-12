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

public class CoherenciaGrafico extends JPanel {
   int n_rendijas = 2;
   int lambda;
   double d_rendijas;
   double d_plano;
   double n_refrac;
   double d_fuente;
   int tipo_fuente;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;

   public CoherenciaGrafico() {
      this.df = new DecimalFormat("#.##", this.df_symb);

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
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      if (dim_x > 0 && dim_y > 0) {
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

         double equis = 0.0D;
         double nume1 = 0.0D;
         double denom1 = 0.0D;
         double param1 = 0.0D;
         double valor_0 = 0.0D;
         double factor2 = 0.0D;
         double termino2 = 0.0D;
         double param2 = 0.0D;
         double[] valor = new double[dim_x];
         double max_valor = 0.0D;
         double equis_0 = -pix * ((double)dim_x / 2.0D);
         double nume1_0 = 3.141592653589793D * equis * this.d_rendijas * this.n_refrac;
         double denom1_0 = (double)this.lambda * 0.001D * this.d_plano;
         double param1_0 = nume1 / denom1;
         double factor2_0 = 3.141592653589793D * this.n_refrac / ((double)this.lambda * 1.0E-6D);
         double termino2_0 = equis_0 * this.d_rendijas / (this.d_plano * 1000.0D) + this.d_fuente * this.d_rendijas / ((7.0D - this.d_plano) * 1000.0D);
         double param2_0 = factor2 * termino2;
         if (this.tipo_fuente == 0) {
            valor_0 = Math.cos(param2_0) * Math.cos(param2_0);
         }

         if (this.tipo_fuente == 1) {
            valor_0 = Math.cos(param2_0) * Math.cos(param2_0) + Math.cos(param1_0) * Math.cos(param1_0);
         }

         if (this.tipo_fuente == 2) {
            if (this.d_fuente == 0.0D) {
               valor_0 = Math.cos(param1_0) * Math.cos(param1_0);
            } else {
               double factor3_0 = 3.141592653589793D * this.d_rendijas * this.n_refrac / ((double)this.lambda * 1.0E-6D);
               double seno3_0 = Math.sin(factor3_0 * (this.d_fuente / ((7.0D - this.d_plano) * 1000.0D)));
               double coseno3_0 = Math.cos(2.0D * factor3_0 * (equis_0 / (this.d_plano * 1000.0D)));
               valor_0 = 2.0D * this.d_fuente + 2.0D * ((7.0D - this.d_plano) * 1000.0D / factor3_0) * seno3_0 * coseno3_0;
            }
         }

         int i;
         for(i = 0; i < dim_x; ++i) {
            equis = pix * ((double)i - (double)dim_x / 2.0D);
            nume1 = 3.141592653589793D * equis * this.d_rendijas * this.n_refrac;
            denom1 = (double)this.lambda * 0.001D * this.d_plano;
            param1 = nume1 / denom1;
            factor2 = 3.141592653589793D * this.n_refrac / ((double)this.lambda * 1.0E-6D);
            termino2 = equis * this.d_rendijas / (this.d_plano * 1000.0D) + this.d_fuente * this.d_rendijas / ((7.0D - this.d_plano) * 1000.0D);
            param2 = factor2 * termino2;
            if (this.tipo_fuente == 0) {
               valor[i] = Math.cos(param2) * Math.cos(param2);
            }

            if (this.tipo_fuente == 1) {
               valor[i] = Math.cos(param2) * Math.cos(param2) + Math.cos(param1) * Math.cos(param1);
            }

            if (this.tipo_fuente == 2) {
               if (this.d_fuente == 0.0D) {
                  valor[i] = Math.cos(param1) * Math.cos(param1);
               } else {
                  double factor3 = 3.141592653589793D * this.d_rendijas * this.n_refrac / ((double)this.lambda * 1.0E-6D);
                  double seno3 = Math.sin(factor3 * (this.d_fuente / ((7.0D - this.d_plano) * 1000.0D)));
                  double coseno3 = Math.cos(2.0D * factor3 * (equis / (this.d_plano * 1000.0D)));
                  valor[i] = 2.0D * this.d_fuente + 2.0D * ((7.0D - this.d_plano) * 1000.0D / factor3) * seno3 * coseno3;
               }
            }
         }

         if (this.tipo_fuente == 0) {
            max_valor = 1.0D;
         }

         if (this.tipo_fuente == 1) {
            max_valor = 2.0D;
         }

         if (this.tipo_fuente == 2) {
            max_valor = 14.0D;
         }

         valor_0 /= max_valor;
         if (valor_0 >= 0.0D && valor_0 <= 1.0D) {
            valor_0 = (double)dim_y - 10.0D + 5.0D - valor_0 * (double)(dim_y - 10);
         } else {
            valor_0 = 0.0D;
         }

         for(i = 0; i < dim_x; ++i) {
            valor[i] /= max_valor;
            valor[i] = (double)dim_y - 10.0D + 5.0D - valor[i] * (double)(dim_y - 10);
            g2.setPaint(ncolor);
            g2.draw(new Double((double)(i - 1), valor_0, (double)i, valor[i]));
            valor_0 = valor[i];
         }

         g2.setPaint(Color.white);
         g2.draw(new Double(0.0D, (double)dim_y - 5.0D, (double)dim_x, (double)dim_y - 5.0D));
         g2.draw(new Double((double)dim_x / 2.0D, 0.0D, (double)dim_x / 2.0D, (double)dim_y));
         char pto = '.';
         this.df_symb.setDecimalSeparator(pto);
         this.df.setDecimalFormatSymbols(this.df_symb);
         g2.setPaint(Color.white);
         double pos_y_max = (double)dim_y - 10.0D + 5.0D - (double)(dim_y - 10);
         double pos_y_uno = (double)dim_y - 10.0D + 5.0D - 1.0D / max_valor * (double)(dim_y - 10);
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
