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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class PanelDibFresnel extends JPanel {
   double ni = 1.0D;
   double nr = 1.75D;
   double angi = Math.toRadians(45.0D);
   double angr;
   int signo_rp;
   int signo_rs;
   boolean ref_total;
   boolean brewster;
   DecimalFormatSymbols df_symb;
   DecimalFormat df;
   JPanel jPanel_DibFresnel;
   BorderLayout borderLayout_DibFresnel;

   public PanelDibFresnel() {
      this.angr = Math.asin(this.ni * Math.sin(this.angi) / this.nr);
      this.signo_rp = 1;
      this.signo_rs = 1;
      this.ref_total = false;
      this.brewster = false;
      this.df_symb = new DecimalFormatSymbols();
      this.df = new DecimalFormat("#.##", this.df_symb);
      this.jPanel_DibFresnel = new JPanel();
      this.borderLayout_DibFresnel = new BorderLayout();

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout_DibFresnel);
      this.setBackground(Color.black);
      this.add(this.jPanel_DibFresnel, "Center");
   }

   public void putAtributos(double v_ni, double v_nr, double v_angi, double v_angr, int v_signrp, int v_signrs, boolean v_ref_total, boolean v_brewster) {
      this.ni = v_ni;
      this.nr = v_nr;
      this.angi = Math.toRadians(v_angi);
      this.angr = Math.toRadians(v_angr);
      this.signo_rp = v_signrp;
      this.signo_rs = v_signrs;
      this.ref_total = v_ref_total;
      this.brewster = v_brewster;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BasicStroke stroke_gordo = new BasicStroke(2.0F);
      BasicStroke stroke_fino = new BasicStroke(1.5F);
      Dimension dim = this.getSize();
      int idimx = dim.width;
      int idimy = dim.height;
      double dimx = (double)idimx;
      double dimy = (double)idimy;
      double dimy_2 = dimy / 2.0D;
      double dimx_2 = dimx / 2.0D;
      double borde = 10.0D;
      double long_rayo = Math.min(dimx, dimy) / 2.0D - borde;
      double long_vector = long_rayo / 3.0D;
      double long_tri = long_vector / 3.0D;
      double h_tri = long_tri / 2.0D * Math.tan(Math.toRadians(60.0D));
      double cosi = Math.cos(this.angi);
      double sini = Math.sin(this.angi);
      double cosr = Math.cos(this.angr);
      double sinr = Math.sin(this.angr);
      boolean incid_rasante = false;
      int angi_1dec = Math.round((float)Math.toDegrees(this.angi) * 10.0F);
      if (angi_1dec == 900) {
         incid_rasante = true;
      }

      g2.setPaint(Color.black);
      g2.fillRect(4, 4, (int)(dimx - 8.0D), (int)(dimy - 8.0D));
      g2.setPaint(Color.white);
      g2.setStroke(stroke_gordo);
      g2.draw(new Double(borde, dimy_2, dimx - borde, dimy_2));
      g2.setStroke(stroke_fino);
      g2.draw(new Double(dimx_2, borde, dimx_2, dimy - borde));
      Font font = new Font("Dialog", 1, 14);
      g2.setFont(font);
      String s = "n";
      g2.drawString(s, (int)(borde * 2.0D), (int)dimy_2 - (int)(borde * 2.0D));
      s = "n'";
      g2.drawString(s, (int)(borde * 2.0D), (int)dimy_2 + (int)(borde * 3.0D));
      g2.setStroke(stroke_gordo);
      g2.setPaint(Color.red);
      g2.draw(new Double(dimx_2, dimy_2, dimx_2 - long_rayo * sini, dimy_2 - long_rayo * cosi));
      g2.setStroke(stroke_fino);
      double vinix = dimx_2 - 2.0D * long_rayo / 3.0D * sini;
      double viniy = dimy_2 - 2.0D * long_rayo / 3.0D * cosi;
      double vfinx = vinix + long_vector * cosi;
      double vfiny = viniy - long_vector * sini;
      g2.draw(new Double(vinix, viniy, vfinx, vfiny));
      int x1 = (int)vfinx;
      int y1 = (int)vfiny;
      int x2 = (int)(vfinx - h_tri * cosi - long_tri / 2.0D * sini);
      int y2 = (int)(vfiny + h_tri * sini - long_tri / 2.0D * cosi);
      int x3 = (int)(vfinx - h_tri * cosi + long_tri / 2.0D * sini);
      int y3 = (int)(vfiny + h_tri * sini + long_tri / 2.0D * cosi);
      g2.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
      g2.drawArc((int)(vinix - 7.0D), (int)(viniy - 7.0D), 14, 14, 0, 360);
      g2.drawArc((int)vinix - 2, (int)viniy - 2, 4, 4, 0, 360);
      if (this.ni != this.nr) {
         g2.setStroke(stroke_gordo);
         g2.setPaint(Color.green);
         g2.draw(new Double(dimx_2, dimy_2, dimx_2 + long_rayo * sini, dimy_2 - long_rayo * cosi));
         vinix = dimx_2 + 5.0D * long_rayo / 6.0D * sini;
         viniy = dimy_2 - 5.0D * long_rayo / 6.0D * cosi;
         g2.setStroke(stroke_fino);
         if (!this.brewster) {
            vfinx = vinix + (double)this.signo_rp * long_vector * cosi;
            vfiny = viniy + (double)this.signo_rp * long_vector * sini;
            g2.draw(new Double(vinix, viniy, vfinx, vfiny));
            x1 = (int)vfinx;
            y1 = (int)vfiny;
            x2 = (int)(vfinx - (double)this.signo_rp * h_tri * cosi - long_tri / 2.0D * sini);
            y2 = (int)(vfiny - (double)this.signo_rp * h_tri * sini + long_tri / 2.0D * cosi);
            x3 = (int)(vfinx - (double)this.signo_rp * h_tri * cosi + long_tri / 2.0D * sini);
            y3 = (int)(vfiny - (double)this.signo_rp * h_tri * sini - long_tri / 2.0D * cosi);
            g2.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
         }

         g2.drawArc((int)(vinix - 7.0D), (int)(viniy - 7.0D), 14, 14, 0, 360);
         if (this.signo_rs == 1) {
            g2.drawArc((int)vinix - 2, (int)viniy - 2, 4, 4, 0, 360);
         } else if (this.signo_rs == -1) {
            g2.draw(new Double(vinix - 7.0D, viniy, vinix + 7.0D, viniy));
            g2.draw(new Double(vinix, viniy - 7.0D, vinix, viniy + 7.0D));
         }
      }

      if (!incid_rasante && !this.ref_total) {
         g2.setStroke(stroke_fino);
         Color color_trans = new Color(0, 150, 255);
         g2.setPaint(color_trans);
         g2.draw(new Double(dimx_2, dimy_2, dimx_2 + long_rayo * Math.sin(this.angr), dimy_2 + long_rayo * Math.cos(this.angr)));
         g2.setStroke(stroke_fino);
         vinix = dimx_2 + 2.0D * long_rayo / 3.0D * Math.sin(this.angr);
         viniy = dimy_2 + 2.0D * long_rayo / 3.0D * Math.cos(this.angr);
         vfinx = vinix + long_vector * Math.cos(this.angr);
         vfiny = viniy - long_vector * Math.sin(this.angr);
         g2.draw(new Double(vinix, viniy, vfinx, vfiny));
         x1 = (int)vfinx;
         y1 = (int)vfiny;
         x2 = (int)(vfinx - h_tri * cosr - long_tri / 2.0D * sinr);
         y2 = (int)(vfiny + h_tri * sinr - long_tri / 2.0D * cosr);
         x3 = (int)(vfinx - h_tri * cosr + long_tri / 2.0D * sinr);
         y3 = (int)(vfiny + h_tri * sinr + long_tri / 2.0D * cosr);
         g2.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
         g2.drawArc((int)(vinix - 7.0D), (int)(viniy - 7.0D), 14, 14, 0, 360);
         g2.drawArc((int)vinix - 2, (int)viniy - 2, 4, 4, 0, 360);
      }

   }
}
