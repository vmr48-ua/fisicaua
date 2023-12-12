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

public class PanelElipse extends JPanel {
   double a1 = 0.5D;
   double a2 = 0.5D;
   double fase = 90.0D;
   Color color;
   int idio;
   boolean ref_total;
   BorderLayout borderLayout_Elipse;
   JPanel jPanel_Elipse;

   public PanelElipse() {
      this.color = Color.red;
      this.idio = 1;
      this.ref_total = false;
      this.borderLayout_Elipse = new BorderLayout();
      this.jPanel_Elipse = new JPanel();

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout_Elipse);
      this.setBackground(Color.black);
      this.add(this.jPanel_Elipse, "Center");
   }

   public void putAtributos(double v_a1, double v_a2, double v_fase, Color v_color, int idiom, boolean v_ref_total) {
      this.a1 = v_a1;
      this.a2 = v_a2;
      this.fase = v_fase;
      this.color = v_color;
      this.idio = idiom;
      this.ref_total = v_ref_total;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BasicStroke stroke = new BasicStroke(2.0F);
      Dimension dim = this.getSize();
      int idimx = dim.width;
      int idimy = dim.height;
      double dimx = (double)idimx;
      double dimy = (double)idimy;
      double tam = Math.min(dimx, dimy);
      int npoints = 1000;
      double[] x = new double[npoints];
      double[] y = new double[npoints];
      double faserad = Math.toRadians(this.fase);
      double tam_pp = 6.0D;
      double tam_pp2 = tam_pp / 2.0D;
      g2.setPaint(Color.black);
      g2.fillRect(0, 0, (int)tam, (int)tam);
      g2.setPaint(Color.white);
      g2.draw(new Double(tam / 2.0D, 5.0D, tam / 2.0D, tam - 5.0D));
      g2.draw(new Double(5.0D, tam / 2.0D, tam - 5.0D, tam / 2.0D));
      Font fontp = new Font("Dialog", 1, 12);
      g2.setFont(fontp);
      g2.setPaint(Color.yellow);
      String s = "p";
      g2.drawString(s, (int)tam - 15, (int)tam / 2 + 10);
      g2.draw(new Double(tam - 15.0D, tam / 2.0D - 5.0D, tam - 15.0D, tam / 2.0D - 5.0D - tam_pp));
      g2.draw(new Double(tam - 15.0D + tam_pp2, tam / 2.0D - 5.0D, tam - 15.0D + tam_pp2, tam / 2.0D - 5.0D - tam_pp));
      g2.setPaint(Color.cyan);
      s = "s";
      g2.drawString(s, (int)tam / 2 - 10, 15);
      g2.draw(new Double(tam / 2.0D + 5.0D + tam_pp2, 14.0D - tam_pp, tam / 2.0D + 5.0D + tam_pp2, 14.0D));
      g2.draw(new Double(tam / 2.0D + 5.0D, 14.0D, tam / 2.0D + 5.0D + tam_pp, 14.0D));
      if (!this.ref_total) {
         double max_a = Math.max(Math.abs(this.a1), Math.abs(this.a2));
         double pos_label1a = tam / 2.0D + (tam - 10.0D) / 4.0D;
         double pos_label1b = tam / 2.0D - (tam - 10.0D) / 4.0D;
         double pos_label2a = tam / 2.0D + (tam - 10.0D) / 8.0D;
         double pos_label2b = tam / 2.0D - (tam - 10.0D) / 8.0D;
         double aa1 = this.a1;
         double aa2 = this.a2;
         if (max_a > 1.0D) {
            g2.setPaint(Color.white);
            if (max_a <= 2.0D) {
               aa1 /= 2.0D;
               aa2 /= 2.0D;
               s = "1";
            } else {
               aa1 /= 4.0D;
               aa2 /= 4.0D;
               s = "1";
               g2.draw(new Double(pos_label2a, tam / 2.0D - 2.5D, pos_label2a, tam / 2.0D + 2.5D));
               g2.draw(new Double(pos_label2b, tam / 2.0D - 2.5D, pos_label2b, tam / 2.0D + 2.5D));
               g2.drawString(s, (int)(pos_label2a - 2.5D), (int)(tam / 2.0D + 15.0D));
               g2.draw(new Double(tam / 2.0D - 2.5D, pos_label2a, tam / 2.0D + 2.5D, pos_label2a));
               g2.draw(new Double(tam / 2.0D - 2.5D, pos_label2b, tam / 2.0D + 2.5D, pos_label2b));
               g2.drawString(s, (int)(tam / 2.0D - 10.0D), (int)(pos_label2b + 5.0D));
               s = "2";
            }

            g2.draw(new Double(pos_label1a, tam / 2.0D - 2.5D, pos_label1a, tam / 2.0D + 2.5D));
            g2.draw(new Double(pos_label1b, tam / 2.0D - 2.5D, pos_label1b, tam / 2.0D + 2.5D));
            g2.drawString(s, (int)(pos_label1a - 2.5D), (int)(tam / 2.0D + 15.0D));
            g2.draw(new Double(tam / 2.0D - 2.5D, pos_label1a, tam / 2.0D + 2.5D, pos_label1a));
            g2.draw(new Double(tam / 2.0D - 2.5D, pos_label1b, tam / 2.0D + 2.5D, pos_label1b));
            g2.drawString(s, (int)(tam / 2.0D - 10.0D), (int)(pos_label1b + 2.5D));
         }

         g2.setPaint(this.color);

         int i;
         for(i = 0; i < npoints; ++i) {
            double t = (double)i;
            x[i] = tam / 2.0D + aa1 * (tam - 10.0D) / 2.0D * Math.cos(6.283185307179586D * t / (double)npoints);
            y[i] = tam / 2.0D - aa2 * (tam - 10.0D) / 2.0D * Math.cos(6.283185307179586D * t / (double)npoints + faserad);
         }

         g2.setStroke(stroke);

         for(i = 0; i < npoints; i += 2) {
            g2.draw(new Double(x[i], y[i], x[i + 1], y[i + 1]));
         }

         double stokes_S = 2.0D * this.a1 * this.a2 * Math.sin(faserad) / (this.a1 * this.a1 + this.a2 * this.a2);
         Font fontg = new Font("Dialog", 1, 14);
         g2.setFont(fontg);
         if (stokes_S != 0.0D) {
            if (stokes_S > 0.0D) {
               if (this.idio == 2) {
                  s = "R";
               } else {
                  s = "D";
               }

               g2.setPaint(Color.white);
            } else {
               s = "L";
               g2.setPaint(Color.lightGray);
            }
         } else {
            s = " ";
            g2.setPaint(Color.black);
         }

         g2.drawString(s, (int)tam - 20, (int)tam - 10);
      }

   }
}
