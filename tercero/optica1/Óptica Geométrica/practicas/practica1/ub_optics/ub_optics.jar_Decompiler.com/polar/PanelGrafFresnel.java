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

public class PanelGrafFresnel extends JPanel {
   double ni = 1.0D;
   double nr = 1.75D;
   double angif = Math.toRadians(45.0D);
   double angrf;
   double rpf;
   double rsf;
   double tsf;
   double tpf;
   boolean ref_total;
   DecimalFormatSymbols df_symb;
   DecimalFormat df;
   BorderLayout borderLayout_GrafFresnel;
   JPanel jPanel_GrafFresnel;

   public PanelGrafFresnel() {
      this.angrf = Math.asin(this.ni * Math.sin(this.angif) / this.nr);
      this.rpf = Math.tan(this.angrf - this.angif) / Math.tan(this.angrf + this.angif);
      this.rsf = Math.sin(this.angrf - this.angif) / Math.sin(this.angrf + this.angif);
      this.tsf = 2.0D * Math.sin(this.angrf) * Math.cos(this.angif) / Math.sin(this.angrf + this.angif);
      this.tpf = this.tsf / Math.cos(this.angrf - this.angif);
      this.ref_total = false;
      this.df_symb = new DecimalFormatSymbols();
      this.df = new DecimalFormat("#.##", this.df_symb);
      this.borderLayout_GrafFresnel = new BorderLayout();
      this.jPanel_GrafFresnel = new JPanel();

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout_GrafFresnel);
      this.add(this.jPanel_GrafFresnel, "Center");
   }

   public void putAtributos(double v_ni, double v_nr, double v_angi, double v_rp, double v_rs, double v_tp, double v_ts, boolean v_ref_total) {
      this.ni = v_ni;
      this.nr = v_nr;
      this.angif = v_angi;
      this.rpf = v_rp;
      this.rsf = v_rs;
      this.tpf = v_tp;
      this.tsf = v_ts;
      this.ref_total = v_ref_total;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BasicStroke stroke_gordo = new BasicStroke(2.25F);
      BasicStroke stroke_fino = new BasicStroke(1.0F);
      BasicStroke stroke_fino_dot = new BasicStroke(1.0F, 1, 1, 0.0F, new float[]{0.0F, 3.0F}, 0.0F);
      BasicStroke stroke_dash = new BasicStroke(1.25F, 1, 1, 0.0F, new float[]{3.0F, 4.0F}, 0.0F);
      Dimension dim = this.getSize();
      int idimx = dim.width;
      int idimy = dim.height;
      double dimx = (double)idimx;
      double dimy = (double)idimy;
      double dimy_2 = dimy / 2.0D;
      double dimx_2 = dimx / 2.0D;
      double long_ejex = dimx - 40.0D;
      double long_ejey = dimy - 40.0D;
      double origin = dimy - 20.0D;
      double norm = 5.0D;
      double interval = long_ejey / norm;
      double pos_zero = origin - interval;
      int npoints = 1440;
      int npointst = npoints;
      double angL = 0.0D;
      double[] x = new double[npoints];
      double[] rp = new double[npoints];
      double[] rs = new double[npoints];
      double[] tp = new double[npoints];
      double[] ts = new double[npoints];
      Color color_rp = new Color(50, 180, 140);
      Color color_rs = new Color(0, 50, 0);
      Color color_tp = new Color(0, 100, 255);
      Color color_ts = new Color(0, 0, 150);
      Color color_angB = new Color(180, 0, 180);
      Color color_angL = new Color(180, 0, 0);
      g2.setPaint(Color.black);
      g2.setStroke(stroke_fino);
      g2.draw(new Double(20.0D, 20.0D, dimx - 40.0D, dimy - 40.0D));
      g2.setStroke(stroke_fino_dot);
      String s = "-1";
      g2.drawString(s, 0, (int)origin);
      s = "0";
      double pos_labely = origin - interval;
      g2.drawString(s, 0, (int)pos_labely);
      g2.setPaint(Color.darkGray);
      g2.draw(new java.awt.geom.Line2D.Double(20.0D, pos_labely, dimx - 20.0D, pos_labely));
      s = "+1";
      pos_labely -= interval;
      g2.drawString(s, 0, (int)pos_labely);
      g2.setPaint(Color.darkGray);
      g2.draw(new java.awt.geom.Line2D.Double(20.0D, pos_labely, dimx - 20.0D, pos_labely));
      g2.setPaint(Color.black);
      s = "+2";
      pos_labely -= interval;
      g2.drawString(s, 0, (int)pos_labely);
      g2.setPaint(Color.darkGray);
      g2.draw(new java.awt.geom.Line2D.Double(20.0D, pos_labely, dimx - 20.0D, pos_labely));
      g2.setPaint(Color.black);
      s = "+3";
      pos_labely -= interval;
      g2.drawString(s, 0, (int)pos_labely);
      g2.setPaint(Color.darkGray);
      g2.draw(new java.awt.geom.Line2D.Double(20.0D, pos_labely, dimx - 20.0D, pos_labely));
      g2.setPaint(Color.black);
      s = "+4";
      pos_labely -= interval;
      g2.drawString(s, 0, (int)pos_labely);
      g2.setStroke(stroke_fino_dot);
      s = "0 º";
      g2.drawString(s, 10, (int)dimy);
      double ang = 45.0D;
      double pos_angle = ang / 90.0D * long_ejex + 20.0D;
      g2.setPaint(Color.darkGray);
      g2.draw(new java.awt.geom.Line2D.Double(pos_angle, 20.0D, pos_angle, dimy - 20.0D));
      g2.setPaint(Color.black);
      s = "45 º";
      g2.drawString(s, (int)pos_angle - 10, (int)dimy);
      s = "90 º";
      g2.drawString(s, (int)long_ejex + 10, (int)dimy);
      double angB = Math.toDegrees(Math.atan(this.nr / this.ni));
      g2.setStroke(stroke_dash);
      g2.setPaint(color_angB);
      pos_angle = angB / 90.0D * long_ejex + 20.0D;
      g2.draw(new java.awt.geom.Line2D.Double(pos_angle, 20.0D, pos_angle, dimy - 20.0D));
      rp[0] = (this.ni - this.nr) / (this.ni + this.nr);
      rs[0] = rp[0];
      tp[0] = 2.0D * this.ni / (this.ni + this.nr);
      ts[0] = tp[0];
      rp[0] = pos_zero - long_ejey / norm * rp[0];
      rs[0] = pos_zero - long_ejey / norm * rs[0];
      tp[0] = pos_zero - long_ejey / norm * tp[0];
      ts[0] = pos_zero - long_ejey / norm * ts[0];
      x[0] = 20.0D;
      if (this.ni > this.nr) {
         angL = Math.toDegrees(Math.asin(this.nr / this.ni));
         g2.setPaint(color_angL);
         pos_angle = angL / 90.0D * long_ejex + 20.0D;
         g2.draw(new java.awt.geom.Line2D.Double(pos_angle, 20.0D, pos_angle, dimy - 20.0D));
      }

      g2.setStroke(stroke_gordo);

      int i;
      for(i = 1; i < npoints; ++i) {
         double angi = Math.toRadians((double)i / (double)npoints * 90.0D);
         if (this.ni > this.nr && Math.toDegrees(angi) >= angL) {
            rp[i] = -1.0D;
            rs[i] = 1.0D;
         } else {
            double angr = Math.asin(this.ni * Math.sin(angi) / this.nr);
            double sinsum = Math.sin(angr + angi);
            rp[i] = Math.tan(angr - angi) / Math.tan(angr + angi);
            rs[i] = Math.sin(angr - angi) / sinsum;
            ts[i] = 2.0D * Math.sin(angr) * Math.cos(angi) / sinsum;
            tp[i] = ts[i] / Math.cos(angr - angi);
            tp[i] = pos_zero - long_ejey / norm * tp[i];
            ts[i] = pos_zero - long_ejey / norm * ts[i];
            npointst = i;
         }

         rp[i] = pos_zero - long_ejey / norm * rp[i];
         rs[i] = pos_zero - long_ejey / norm * rs[i];
         x[i] = long_ejex / (double)npoints * (double)i + 20.0D;
      }

      g2.setPaint(color_rp);

      for(i = 0; i < npoints; i += 2) {
         g2.draw(new java.awt.geom.Line2D.Double(x[i], rp[i], x[i + 1], rp[i + 1]));
      }

      g2.setPaint(color_rs);

      for(i = 0; i < npoints; i += 2) {
         g2.draw(new java.awt.geom.Line2D.Double(x[i], rs[i], x[i + 1], rs[i + 1]));
      }

      g2.setPaint(color_tp);

      for(i = 0; i < npointst; i += 2) {
         g2.draw(new java.awt.geom.Line2D.Double(x[i], tp[i], x[i + 1], tp[i + 1]));
      }

      g2.setPaint(color_ts);

      for(i = 0; i < npointst; i += 2) {
         g2.draw(new java.awt.geom.Line2D.Double(x[i], ts[i], x[i + 1], ts[i + 1]));
      }

      g2.setPaint(Color.yellow);
      double xf = this.angif / 90.0D * long_ejex + 20.0D;
      double yf = (double)((int)(pos_zero - long_ejey / norm * this.rpf));
      g2.fillRect((int)xf - 2, (int)yf - 2, 4, 4);
      yf = (double)((int)(pos_zero - long_ejey / norm * this.rsf));
      g2.fillRect((int)xf - 2, (int)yf - 2, 4, 4);
      if (!this.ref_total) {
         yf = (double)((int)(pos_zero - long_ejey / norm * this.tpf));
         g2.fillRect((int)xf - 2, (int)yf - 2, 4, 4);
         yf = (double)((int)(pos_zero - long_ejey / norm * this.tsf));
         g2.fillRect((int)xf - 2, (int)yf - 2, 4, 4);
      }

   }
}
