package difraccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;

public class PerfilFresnel extends JPanel {
   int lambda;
   int dim_x;
   int dim_y;
   int dim_Lx = 256;
   int dim_Ly = 256;
   int dim_Total;
   double[] valores;

   public PerfilFresnel() {
      this.dim_Total = this.dim_Lx * this.dim_Ly;
      this.valores = new double[this.dim_Lx];

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      for(int i = 0; i < this.dim_Lx; ++i) {
         this.valores[i] = 0.0D;
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(256, 256));
      this.setPreferredSize(new Dimension(256, 256));
   }

   public void putAtributos(int tam_x, int tam_y, double[] matriz, int l_onda) {
      this.lambda = l_onda;
      this.dim_x = tam_x;
      this.dim_y = tam_y;

      for(int i = 0; i < this.dim_x; ++i) {
         this.valores[i] = matriz[i + this.dim_y / 2 * this.dim_x];
      }

   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      double[] brillo = new double[this.dim_Lx];

      int i;
      for(i = 0; i < this.dim_Lx; ++i) {
         brillo[i] = this.valores[i];
      }

      double brillo_0 = brillo[0];
      double max_brillo = brillo[0];
      double min_brillo = brillo[0];

      for(i = 0; i < this.dim_x; ++i) {
         if (max_brillo < brillo[i]) {
            max_brillo = brillo[i];
         }

         if (min_brillo > brillo[i]) {
            min_brillo = brillo[i];
         }
      }

      brillo_0 = (brillo_0 - min_brillo) / (max_brillo - min_brillo);
      if (brillo_0 >= 0.0D && brillo_0 <= 1.0D) {
         brillo_0 = (double)this.dim_Ly - 10.0D + 5.0D - brillo_0 * (double)(this.dim_Ly - 10);
      } else {
         brillo_0 = 0.0D;
      }

      for(i = 0; i < this.dim_Lx; ++i) {
         brillo[i] = (brillo[i] - min_brillo) / (max_brillo - min_brillo);
         brillo[i] = (double)this.dim_Ly - 10.0D + 5.0D - brillo[i] * (double)(this.dim_Ly - 10);
      }

      g2.setPaint(ncolor);

      for(i = 1; i < this.dim_Lx; ++i) {
         g2.draw(new Double((double)(i - 1), brillo_0, (double)i, brillo[i]));
         brillo_0 = brillo[i];
      }

   }
}
