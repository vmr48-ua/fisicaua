package polar;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class PanelGrafMetalT extends JPanel {
   double kr = 1.0D;
   double lambda = 550.0D;
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;
   BorderLayout borderLayout_GrafMetalT;
   JPanel jPanel_GrafMetalT;

   public PanelGrafMetalT() {
      this.df = new DecimalFormat("#.##", this.df_symb);
      this.borderLayout_GrafMetalT = new BorderLayout();
      this.jPanel_GrafMetalT = new JPanel();

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout_GrafMetalT);
      this.add(this.jPanel_GrafMetalT, "Center");
   }

   public void putAtributos(double v_kr, double v_lambda) {
      this.kr = v_kr;
      this.lambda = v_lambda;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BasicStroke stroke_gordo = new BasicStroke(2.25F);
      BasicStroke stroke_fino = new BasicStroke(1.0F);
      new BasicStroke(1.0F, 1, 1, 0.0F, new float[]{0.0F, 3.0F}, 0.0F);
      BasicStroke stroke_dash = new BasicStroke(1.25F, 1, 1, 0.0F, new float[]{3.0F, 4.0F}, 0.0F);
      Dimension dim = this.getSize();
      int idimx = dim.width;
      int idimy = dim.height;
      double dimx = (double)idimx;
      double dimy = (double)idimy;
      double inix = 20.0D;
      double iniy = 10.0D;
      double finx = 20.0D;
      double finy = 20.0D;
      double long_ejex = dimx - inix - finx;
      double long_ejey = dimy - iniy - finy;
      int npoints = 400;
      double[] x = new double[npoints];
      double[] It = new double[npoints];
      Color color_trans = new Color(0, 150, 255);
      double zmax = 150.0D;
      g2.setPaint(Color.black);
      g2.setStroke(stroke_fino);
      g2.draw(new Double(inix, iniy, long_ejex, long_ejey));
      String s = "0";
      g2.drawString(s, (int)inix - 5, (int)dimy - 5);
      s = "z (nm)";
      g2.drawString(s, (int)(dimx / 2.0D) - 10, (int)dimy - 5);
      s = "1";
      g2.drawString(s, (int)inix - 15, (int)iniy + 5);
      g2.setStroke(stroke_dash);
      if (this.kr != 0.0D) {
         double piel;
         for(piel = this.lambda / 2.0D / 3.141592653589793D / this.kr; piel > zmax; zmax *= 2.0D) {
         }

         double xpiel = long_ejex * piel / zmax + inix;
         double Ipiel = iniy + long_ejey * (1.0D - Math.exp(-12.566370614359172D * this.kr * piel / this.lambda));
         g2.draw(new java.awt.geom.Line2D.Double(xpiel, dimy - finy, xpiel, Ipiel));
         g2.draw(new java.awt.geom.Line2D.Double(xpiel, Ipiel, inix, Ipiel));
         s = "1/e";
         g2.drawString(s, (int)inix - 20, (int)Ipiel + 5);
      }

      String sm = String.valueOf((int)zmax);
      g2.drawString(sm, (int)(dimx - finx) - 10, (int)dimy - 5);
      g2.setStroke(stroke_gordo);
      g2.setPaint(color_trans);
      It[0] = iniy;
      x[0] = inix;

      int i;
      for(i = 1; i < npoints; ++i) {
         x[i] = long_ejex / (double)npoints * (double)i + x[0];
         double z = (double)i / (double)npoints * zmax;
         It[i] = It[0] + long_ejey * (1.0D - Math.exp(-12.566370614359172D * this.kr * z / this.lambda));
      }

      for(i = 0; i < npoints; i += 2) {
         g2.draw(new java.awt.geom.Line2D.Double(x[i], It[i], x[i + 1], It[i + 1]));
      }

   }
}
