package interfero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class Graf_Michelson extends JPanel {
   BorderLayout borderLayout1 = new BorderLayout();
   int lambda;
   double d;
   double n;
   double focal = 50.0D;
   double angle = 0.0D;
   double d1 = 10.0D;
   double d2;
   boolean twyman;

   public Graf_Michelson() {
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

   public void putAtributos(int l, double dist, double n_ref) {
      this.lambda = l;
      this.d = dist;
      this.n = n_ref;
      this.d2 = this.d1 + this.d;
      this.twyman = Interferometros.tipoFuente;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_x = dim.width;
      int dim_y = dim.height;
      double pix = Interferometros.finestra / (double)dim_x * Math.sqrt(2.0D);
      YoungColor ycolores = new YoungColor();
      Color color = ycolores.lambda2RGB(this.lambda);
      double[] valor_graf = new double[dim_x];
      g2.setColor(Color.white);
      g2.drawLine(0, dim_y - 6, dim_x, dim_y - 6);
      g2.drawLine(dim_x / 2, 0, dim_x / 2, dim_y - 6);
      g2.drawString("max = 1", dim_x / 2 + 6, 10);
      g2.drawLine(dim_x / 2 - 6, 10, dim_x / 2 + 6, 10);
      g2.setPaint(color);
      if (dim_x > 0 && dim_y > 0) {
         double cos;
         int i;
         if (!this.twyman) {
            for(i = 0; i < dim_x; ++i) {
               cos = Math.cos(6.283185307179586D * this.n * this.d * Math.cos(Math.atan2(pix * (double)(i - dim_x / 2), this.focal)) / ((double)this.lambda * 1.0E-6D));
               valor_graf[i] = cos * cos;
            }

            double valor_max = valor_graf[0];
            double valor_min = valor_graf[0];

            int i;
            for(i = 0; i < dim_x; ++i) {
               if (valor_graf[i] > valor_max) {
                  valor_max = valor_graf[i];
               }

               if (valor_graf[i] < valor_min) {
                  valor_min = valor_graf[i];
               }
            }

            for(i = 0; i < dim_x; ++i) {
               if (valor_max - valor_min < 1.0E-4D) {
                  valor_graf[i] = (double)(dim_y - 7 - 10);
               } else {
                  valor_graf[i] = (valor_graf[i] - valor_min) / (valor_max - valor_min) * (double)(dim_y - 7 - 10);
               }
            }

            for(i = 0; i < dim_x - 1; ++i) {
               g2.drawLine(i, dim_y - 7 - (int)valor_graf[i], i + 1, dim_y - 7 - (int)valor_graf[i + 1]);
            }
         } else {
            cos = Math.cos(6.283185307179586D * this.n * this.d / ((double)this.lambda * 1.0E-6D));
            cos = cos * cos * (double)(dim_y - 7 - 10);

            for(i = 0; i < dim_x - 1; ++i) {
               g2.drawLine(i, dim_y - 7 - (int)cos, i + 1, dim_y - 7 - (int)cos);
            }

            g2.setColor(Color.white);
            char pto = '.';
            DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
            df_symb.setDecimalSeparator(pto);
            DecimalFormat df = new DecimalFormat("#.###", df_symb);
            df.setDecimalFormatSymbols(df_symb);
            g2.drawString(df.format(cos / (double)(dim_y - 7 - 10)), dim_x / 2 + 6, 40);
         }
      }

   }
}
