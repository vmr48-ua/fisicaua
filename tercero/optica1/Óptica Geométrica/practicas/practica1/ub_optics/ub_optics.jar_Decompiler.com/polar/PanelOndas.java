package polar;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;

public class PanelOndas extends JPanel {
   double a1 = 0.5D;
   double a2 = 0.5D;
   double fase = 0.0D;
   JPanel jPanel_Ondas = new JPanel();
   BorderLayout borderLayout_Ondas = new BorderLayout();

   public PanelOndas() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout_Ondas);
      this.add(this.jPanel_Ondas, "Center");
   }

   public void putAtributos(double v_a1, double v_a2, double v_fase) {
      this.a1 = v_a1;
      this.a2 = v_a2;
      this.fase = v_fase;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BasicStroke stroke = new BasicStroke(1.5F);
      Dimension dim = this.getSize();
      int idimx = dim.width;
      int idimy = dim.height;
      double dimx = (double)idimx;
      double dimy = (double)idimy;
      double dimy_2 = dimy / 2.0D;
      double cos45 = Math.cos(0.7853981633974483D);
      double or_x = 25.0D;
      double or_y = dimy / 2.0D;
      double long_ejex = dimx - 5.0D - or_x;
      double long_ejey = dimy - 10.0D;
      double fasrad = Math.toRadians(this.fase);
      int npoints = 2500;
      int nperiode = 5;
      double[] x1 = new double[npoints];
      double[] y1 = new double[npoints];
      double[] x2 = new double[npoints];
      double[] y2 = new double[npoints];
      g2.setPaint(Color.white);
      g2.draw(new Double(or_x, 5.0D, or_x, or_y));
      g2.draw(new Double(or_x, or_y, dimx - 5.0D, or_y));
      g2.draw(new Double(or_x, or_y, or_x + long_ejey / 2.0D * cos45, or_y - long_ejey / 2.0D * cos45));
      Font fontp = new Font("Dialog", 1, 12);
      g2.setFont(fontp);
      g2.setPaint(Color.yellow);
      String s = "p";
      g2.drawString(s, (int)(or_x + long_ejey / 2.0D * cos45) - 10, (int)(or_y - long_ejey / 2.0D * cos45));
      g2.setPaint(Color.cyan);
      s = "s";
      g2.drawString(s, (int)or_x - 10, 15);

      int i;
      for(i = 0; i < npoints; ++i) {
         double x = long_ejex / (double)npoints * (double)i;
         y1[i] = this.a1 * long_ejey / 2.0D * Math.cos(6.283185307179586D * x / (long_ejex / (double)nperiode));
         y2[i] = this.a2 * long_ejey / 2.0D * Math.cos(6.283185307179586D * x / (long_ejex / (double)nperiode) + fasrad);
         x2[i] = or_x + x;
         y2[i] = or_y - y2[i];
         x1[i] = or_x + x + y1[i] * cos45;
         y1[i] = or_y - y1[i] * cos45;
      }

      g2.setStroke(stroke);
      g2.setPaint(Color.yellow);

      for(i = 0; i < npoints; i += 2) {
         g2.draw(new Double(x1[i], y1[i], x1[i + 1], y1[i + 1]));
      }

      g2.setPaint(Color.cyan);

      for(i = 0; i < npoints; i += 2) {
         g2.draw(new Double(x2[i], y2[i], x2[i + 1], y2[i + 1]));
      }

   }
}
