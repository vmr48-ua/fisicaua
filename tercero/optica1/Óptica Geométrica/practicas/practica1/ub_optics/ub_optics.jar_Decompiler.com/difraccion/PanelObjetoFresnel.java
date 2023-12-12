package difraccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;

public class PanelObjetoFresnel extends JPanel {
   int lambda;
   int tipo_objeto;
   double dim_x;
   double dim_y;

   public PanelObjetoFresnel() {
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

   public void putAtributos(int tipo_obj, double tam_x, double tam_y, int l_onda) {
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
      double delta_pix = 42.0D;
      g2.setPaint(ncolor);
      double ladox = this.dim_x * delta_pix;
      double ladoy = this.dim_y * delta_pix;
      double x_cero = (double)dim_Lx / 2.0D - ladox / 2.0D;
      double y_cero = (double)dim_Ly / 2.0D - ladoy / 2.0D;
      if (this.tipo_objeto == 0) {
         g2.fill(new Double(x_cero, y_cero, ladox, ladoy));
      } else if (this.tipo_objeto == 1) {
         g2.fill(new java.awt.geom.Ellipse2D.Double(x_cero, x_cero, ladox, ladox));
      } else if (this.tipo_objeto == 3) {
         g2.fill(new Double(x_cero, 0.0D, ladox, (double)dim_Ly));
      } else {
         g2.fill(new Double(0.0D, 0.0D, (double)dim_Lx / 2.0D, (double)dim_Ly));
      }

   }
}
