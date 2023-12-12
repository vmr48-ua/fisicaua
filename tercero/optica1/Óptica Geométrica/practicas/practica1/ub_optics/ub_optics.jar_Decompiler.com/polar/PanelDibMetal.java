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

public class PanelDibMetal extends JPanel {
   double ni = 1.0D;
   double angi = Math.toRadians(45.0D);
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;
   JPanel jPanel_DibFresnel;
   BorderLayout borderLayout_DibFresnel;

   public PanelDibMetal() {
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

   public void putAtributos(double v_ni, double v_angi) {
      this.ni = v_ni;
      this.angi = Math.toRadians(v_angi);
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BasicStroke stroke_gordo = new BasicStroke(2.0F);
      BasicStroke stroke_fino = new BasicStroke(1.5F);
      BasicStroke stroke_dash = new BasicStroke(2.0F, 1, 1, 0.0F, new float[]{3.0F, 4.0F}, 0.0F);
      Dimension dim = this.getSize();
      int idimx = dim.width;
      int idimy = dim.height;
      double dimx = (double)idimx;
      double dimy = (double)idimy;
      double dimy_2 = dimy / 2.0D;
      double dimx_2 = dimx / 2.0D;
      double long_rayo = Math.min(dimx, dimy) / 2.0D - 10.0D;
      g2.setPaint(Color.black);
      g2.fillRect(4, 4, (int)(dimx - 8.0D), (int)(dimy - 8.0D));
      g2.setPaint(Color.white);
      g2.setStroke(stroke_gordo);
      g2.draw(new Double(10.0D, dimy_2, dimx - 10.0D, dimy_2));
      g2.setStroke(stroke_fino);
      g2.draw(new Double(dimx_2, 10.0D, dimx_2, dimy - 10.0D));
      g2.setPaint(Color.darkGray);
      g2.fillRect(10, (int)dimy_2 + 2, (int)(dimx - 20.0D), (int)(dimy_2 - 6.0D));
      g2.setPaint(Color.white);
      Font font = new Font("Dialog", 1, 14);
      g2.setFont(font);
      String s = "n";
      g2.drawString(s, 20, (int)dimy_2 - 20);
      s = "ñ' = n' - i k";
      g2.drawString(s, 20, (int)dimy_2 + 30);
      g2.setStroke(stroke_gordo);
      g2.setPaint(Color.red);
      g2.draw(new Double(dimx_2, dimy_2, dimx_2 - long_rayo * Math.sin(this.angi), dimy_2 - long_rayo * Math.cos(this.angi)));
      g2.setPaint(Color.green);
      g2.draw(new Double(dimx_2, dimy_2, dimx_2 + long_rayo * Math.sin(this.angi), dimy_2 - long_rayo * Math.cos(this.angi)));
      Color color_trans = new Color(0, 150, 255);
      g2.setPaint(color_trans);
      g2.setStroke(stroke_dash);
      g2.draw(new Double(dimx_2, dimy_2 + 2.0D, dimx_2, dimy_2 + 3.0D * long_rayo / 4.0D));
      int x1 = (int)dimx_2;
      int y1 = (int)(dimy_2 + 3.0D * long_rayo / 4.0D);
      int x2 = x1 - 5;
      int y2 = y1 - 8;
      int x3 = x1 + 5;
      g2.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y2}, 3);
      s = "z";
      g2.drawString(s, x1 + 15, y1);
   }
}
