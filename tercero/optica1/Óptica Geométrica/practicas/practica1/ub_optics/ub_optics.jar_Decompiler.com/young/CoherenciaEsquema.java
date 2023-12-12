package young;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D.Double;
import javax.swing.JPanel;

public class CoherenciaEsquema extends JPanel {
   int n_rendijas = 2;
   int lambda;
   double d_rendijas;
   double d_plano;
   double d_fuente;
   int tipo_fuente;

   public CoherenciaEsquema() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.darkGray);
      this.setMinimumSize(new Dimension(350, 230));
      this.setPreferredSize(new Dimension(350, 230));
   }

   public void putAtributos(int tipofuente, double dfuente, int l_onda, double a, double plano) {
      this.tipo_fuente = tipofuente;
      this.d_fuente = dfuente;
      this.n_rendijas = 2;
      this.lambda = l_onda;
      this.d_rendijas = a;
      this.d_plano = plano;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_x = dim.width;
      int dim_y = dim.height;
      Color blanco = Color.white;
      YoungColor ycolores = new YoungColor();
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      double delta_x = (this.d_plano - 1.0D) * 50.0D;
      g2.setPaint(Color.gray);
      g2.fill(new Double((double)(dim_x - 60) - delta_x, 0.0D, 5.0D, (double)dim_y, 10.0D, 10.0D));
      g2.fill(new Double((double)(dim_x - 10), 0.0D, 7.5D, (double)dim_y, 10.0D, 10.0D));
      g2.fill(new Double(2.5D, 0.0D, 5.0D, (double)dim_y, 10.0D, 10.0D));
      g2.setPaint(ncolor);
      if (this.tipo_fuente == 0) {
         g2.fill(new java.awt.geom.Rectangle2D.Double(2.5D, (double)(dim_y / 2) - 2.5D + this.d_fuente * 4.0D, 5.0D, 5.0D));
      }

      if (this.tipo_fuente == 1) {
         g2.fill(new java.awt.geom.Rectangle2D.Double(2.5D, (double)(dim_y / 2) - 2.5D, 5.0D, 5.0D));
         g2.fill(new java.awt.geom.Rectangle2D.Double(2.5D, (double)(dim_y / 2) - 2.5D + this.d_fuente * 4.0D, 5.0D, 5.0D));
      }

      if (this.tipo_fuente == 2) {
         g2.fill(new java.awt.geom.Rectangle2D.Double(2.5D, (double)(dim_y / 2) - 2.0D * this.d_fuente - 2.5D, 5.0D, 5.0D + 4.0D * this.d_fuente));
      }

      double x = (double)(dim_x - 60) + 2.5D - delta_x;
      double y = (double)dim_y / 2.0D;
      double rectWidth = 5.0D;
      double rectHeight = 5.0D;
      double radio = 2.0D;
      double radio2 = 20.0D;
      double r = 0.0D;
      int rendijas = this.n_rendijas;
      double delta = 7.0D + (this.d_rendijas - 1.0D) * 3.0D;

      for(int j = 0; j < rendijas; ++j) {
         y = (double)dim_y / 2.0D - delta * (double)rendijas / 2.0D + delta * (double)j + delta / 2.0D;
         g2.fill(new java.awt.geom.Ellipse2D.Double(x - 2.5D, y - 2.5D, rectWidth, rectHeight));

         for(int i = 0; i < 200; i += 4) {
            r = (double)i + (double)(this.lambda - 379) * 0.03D;
            g2.draw(new java.awt.geom.Arc2D.Double(x + r * Math.cos(45.0D), y - r * Math.sin(45.0D), 2.0D * r, 2.0D * r, -45.0D, 90.0D, 0));
         }
      }

      y = (double)(dim_y / 2);
      x = 2.5D;

      for(int i = 0; i < 400; i += 6) {
         r = (double)(5 + i) + (double)(this.lambda - 379) * 0.03D;
         if (r + r / 2.0D + 2.0D < (double)dim_x - delta_x - 60.0D) {
            if (this.tipo_fuente == 0) {
               g2.draw(new java.awt.geom.Arc2D.Double(r * Math.cos(90.0D), y + 4.0D * this.d_fuente - r * Math.sin(90.0D), 2.0D * r, 2.0D * r, -90.0D, 180.0D, 0));
            }

            if (this.tipo_fuente == 1) {
               g2.draw(new java.awt.geom.Arc2D.Double(r * Math.cos(90.0D), y + 4.0D * this.d_fuente - r * Math.sin(90.0D), 2.0D * r, 2.0D * r, -90.0D, 180.0D, 0));
               g2.draw(new java.awt.geom.Arc2D.Double(r * Math.cos(90.0D), y - r * Math.sin(90.0D), 2.0D * r, 2.0D * r, -90.0D, 180.0D, 0));
            }

            if (this.tipo_fuente == 2) {
               double liny1 = -((double)(dim_y / 2) * (r - 5.0D) / ((double)dim_x - delta_x - 60.0D - 5.0D)) + (double)(dim_y / 2) - 4.0D * this.d_fuente;
               double liny2 = (double)(dim_y / 2) * (r - 5.0D) / ((double)dim_x - delta_x - 60.0D - 5.0D) + (double)(dim_y / 2) + 4.0D * this.d_fuente;
               g2.draw(new java.awt.geom.QuadCurve2D.Double(r, liny1, r * 2.0D, y, r, liny2));
            }
         }
      }

   }
}
