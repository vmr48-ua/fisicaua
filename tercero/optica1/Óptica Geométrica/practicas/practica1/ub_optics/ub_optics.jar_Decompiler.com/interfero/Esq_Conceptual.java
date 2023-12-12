package interfero;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;

public class Esq_Conceptual extends JPanel {
   BorderLayout borderLayout1 = new BorderLayout();
   int lambda = 633;
   double distancia = 0.0D;
   double indice_n;
   boolean twyman;
   double delta = 0.05D;
   double deltad = 25.0D;
   double angle = 0.0D;
   YoungColor l2rgb = new YoungColor();

   public Esq_Conceptual() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void jbInit() throws Exception {
      this.setLayout(this.borderLayout1);
      this.setBackground(Color.darkGray);
      this.setMinimumSize(new Dimension(350, 350));
      this.setPreferredSize(new Dimension(350, 350));
   }

   void putAtributos(int l, double d, double n) {
      this.lambda = l;
      this.distancia = d;
      this.indice_n = n;
      this.twyman = Interferometros.tipoFuente;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_x = dim.width;
      int dim_y = dim.height;
      int[] puntos_x = new int[]{dim_x / 2 - 30 + 21 - 2, dim_x / 2 - 30 + 21 + 2, dim_x / 2 - 30 - 21 + 2, dim_x / 2 - 30 - 21 - 2};
      int[] var10000 = new int[]{puntos_x[1], puntos_x[1] + 2, puntos_x[2] + 2, puntos_x[2]};
      int[] puntos_y = new int[]{dim_y / 2 - 21 - 2, dim_y / 2 - 21 + 2, dim_y / 2 + 21 + 2, dim_y / 2 + 21 - 2};
      var10000 = new int[]{puntos_y[1], puntos_y[1] + 2, puntos_y[2] + 2, puntos_y[2]};
      double theta = 60.0D;
      if (this.twyman) {
         theta = 0.0D;
      }

      double rayo = 75.0D;
      double punto_a_x = (double)(dim_x / 2) + 5.0D;
      double punto_a_y = (double)(dim_y / 2) - 80.0D;
      double punto_b_y = punto_a_y + this.distancia * this.deltad;
      double punto_c_x = punto_a_x - this.distancia * this.deltad * Math.cos(theta) * Math.sin(theta);
      double punto_c_y = punto_a_y + this.distancia * this.deltad * Math.cos(theta) * Math.cos(theta);
      double punto_d_x = punto_a_x - this.distancia * this.deltad * Math.tan(theta);
      double punto_e_x = punto_d_x - rayo * Math.tan(theta);
      double punto_e_y = punto_c_y + rayo;
      double punto_f_x = punto_a_x - rayo * Math.tan(theta);
      double punto_f_y = punto_b_y + rayo;
      double punto_g_y = punto_b_y + rayo;
      double punto_h_y = punto_a_y + this.distancia * this.deltad / 2.0D;
      double punto_i_x = punto_a_x - Math.tan(theta) * this.distancia * this.deltad / 2.0D - 15.0D;
      double punto_i_y = punto_a_y + this.distancia * this.deltad / 2.0D;
      int[] vertice1_x = new int[]{(int)punto_e_x, (int)punto_e_x - 4, (int)punto_e_x + 10};
      int[] vertice1_y = new int[]{(int)punto_e_y + 1, (int)punto_e_y - 10, (int)punto_e_y - 3};
      int[] vertice2_x = new int[]{(int)punto_f_x, (int)punto_f_x - 4, (int)punto_f_x + 10};
      int[] vertice2_y = new int[]{(int)punto_f_y + 1, (int)punto_f_y - 10, (int)punto_f_y - 3};
      int[] vertice3_x = new int[]{(int)punto_i_x, (int)punto_i_x + 5, (int)punto_i_x - 5};
      int[] vertice3_y = new int[]{(int)punto_i_y, (int)punto_i_y + 10, (int)punto_i_y + 10};
      int[] vertice4_x = new int[]{(int)punto_e_x, (int)punto_e_x - 7, (int)punto_e_x + 8};
      int[] vertice4_y = new int[]{(int)punto_e_y + 1, (int)punto_e_y - 7, (int)punto_e_y - 7};
      int[] vertice5_x = new int[]{dim_x / 2 - 45, dim_x / 2 - 50, dim_x / 2 - 40};
      int[] vertice5_y = new int[]{23, 33, 33};
      g2.setColor(new Color(191, 237, 245));
      g2.fill(new Double(punto_a_x - 80.0D, punto_a_y, 150.0D, 10.0D));
      g2.fill(new Double(punto_a_x - 80.0D, punto_b_y, 150.0D, 10.0D));
      BasicStroke bs = new BasicStroke(2.0F);
      BasicStroke bs2 = new BasicStroke(4.0F);
      g2.setStroke(bs);
      g2.setColor(Color.lightGray);
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x, punto_a_y, punto_a_x, punto_b_y));
      g2.draw(new java.awt.geom.Line2D.Double(punto_c_x, punto_c_y, punto_a_x, punto_b_y));
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x, punto_b_y, punto_a_x, punto_g_y));
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x - 90.0D, punto_a_y + 5.0D, punto_a_x - 90.0D, punto_b_y + 5.0D));
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x - 93.0D, punto_a_y + 5.0D, punto_a_x - 87.0D, punto_a_y + 5.0D));
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x - 93.0D, punto_b_y + 5.0D, punto_a_x - 87.0D, punto_b_y + 5.0D));
      g2.setFont(new Font("Dialog", 1, 16));
      g2.drawString("2d", (int)punto_a_x - 115, (int)punto_h_y + 5);
      g2.drawString(" = 2nd cos", dim_x / 2 - 35, 35);
      g2.drawPolygon(vertice5_x, vertice5_y, 3);
      g2.draw(new java.awt.geom.Ellipse2D.Double((double)(dim_x / 2 + 54), 25.0D, 6.0D, 10.0D));
      g2.draw(new java.awt.geom.Line2D.Double((double)(dim_x / 2 + 55), 30.0D, (double)(dim_x / 2 + 59), 30.0D));
      if (!this.twyman) {
         g2.draw(new java.awt.geom.Ellipse2D.Double(punto_a_x - 13.0D, punto_b_y + rayo * 3.0D / 4.0D, 6.0D, 10.0D));
         g2.draw(new java.awt.geom.Line2D.Double(punto_a_x - 12.0D, punto_b_y + rayo * 3.0D / 4.0D + 5.0D, punto_a_x - 8.0D, punto_b_y + rayo * 3.0D / 4.0D + 5.0D));
      }

      if (this.distancia > 1.0D) {
         g2.drawPolygon(vertice3_x, vertice3_y, 3);
      }

      g2.setColor(this.l2rgb.lambda2RGB(this.lambda));
      g2.draw(new java.awt.geom.Line2D.Double(punto_c_x, punto_c_y, punto_e_x, punto_e_y));
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x, punto_b_y, punto_f_x, punto_f_y));
      g2.setStroke(bs2);
      g2.draw(new java.awt.geom.Line2D.Double(punto_a_x, punto_a_y, punto_c_x, punto_c_y));
      if (!this.twyman) {
         g2.fillPolygon(vertice1_x, vertice1_y, 3);
         g2.fillPolygon(vertice2_x, vertice2_y, 3);
      } else {
         g2.fillPolygon(vertice4_x, vertice4_y, 3);
         g2.fillPolygon(vertice4_x, vertice4_y, 3);
      }

   }
}
