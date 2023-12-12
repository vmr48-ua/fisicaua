package difraccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;

public class PanelObjeto extends JPanel {
   int lambda;
   int tipo_objeto;
   double dim_x;
   double dim_y;
   int num_objetos;

   public PanelObjeto() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(256, 256));
      this.setPreferredSize(new Dimension(256, 256));
   }

   public void putAtributos(int tipo_obj, double tam_x, double tam_y, int num_obj, int l_onda) {
      this.num_objetos = num_obj;
      this.lambda = l_onda;
      this.tipo_objeto = tipo_obj;
      this.dim_x = tam_x;
      this.dim_y = tam_y;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Lx = dim.width;
      int dim_Ly = dim.height;
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      double delta_pix = 6.0D;
      double dim_x_max = 3.0D;
      double dist_separa = 0.0D;
      if (this.num_objetos < 5) {
         dist_separa = 2.0D * dim_x_max * delta_pix;
      } else if (this.num_objetos > 4 && this.num_objetos < 9) {
         dist_separa = dim_x_max * delta_pix / 2.0D;
      } else if (this.num_objetos > 8 && this.num_objetos < 11) {
         dist_separa = dim_x_max * delta_pix / 4.0D;
      }

      g2.setPaint(ncolor);
      double x_cero = (double)dim_Lx / 2.0D - this.dim_x * delta_pix / 2.0D - (double)(this.num_objetos - 1) / 2.0D * (dist_separa + dim_x_max * delta_pix);
      double ladox = this.dim_x * delta_pix;
      double ladoy = this.dim_y * delta_pix;
      double x;
      double y;
      int i;
      if (this.tipo_objeto == 0) {
         for(i = 0; i < this.num_objetos; ++i) {
            x = x_cero + (dist_separa + dim_x_max * delta_pix) * (double)i;
            y = (double)dim_Ly / 2.0D - ladoy / 2.0D;
            g2.fill(new Double(x, y, ladox, ladoy));
         }
      } else if (this.tipo_objeto == 1) {
         for(i = 0; i < this.num_objetos; ++i) {
            x = x_cero + (dist_separa + dim_x_max * delta_pix) * (double)i;
            y = (double)dim_Ly / 2.0D - ladox / 2.0D;
            g2.fill(new java.awt.geom.Ellipse2D.Double(x, y, ladox, ladox));
         }
      } else {
         for(i = 0; i < this.num_objetos; ++i) {
            x = x_cero + (dist_separa + dim_x_max * delta_pix) * (double)i;
            y = 0.0D;
            g2.fill(new Double(x, y, ladox, (double)dim_Ly));
         }
      }

   }
}
