package interfero;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;

public class Esq_Michelson extends JPanel {
   BorderLayout borderLayout1 = new BorderLayout();
   int lambda = 633;
   double distancia = 0.0D;
   double indice_n;
   double delta = 0.05D;
   double deltad = 5.0D;
   double angle = 0.0D;
   YoungColor l2rgb = new YoungColor();

   public Esq_Michelson() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void jbInit() throws Exception {
      this.setBackground(Color.darkGray);
      this.setLayout(this.borderLayout1);
      this.setMinimumSize(new Dimension(350, 350));
      this.setPreferredSize(new Dimension(350, 350));
   }

   void putAtributos(int l, double d, double n) {
      this.lambda = l;
      this.distancia = d;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_x = dim.width;
      int dim_y = dim.height;
      int[] puntos_x = new int[]{dim_x / 2 - 30 + 21 - 2, dim_x / 2 - 30 + 21 + 2, dim_x / 2 - 30 - 21 + 2, dim_x / 2 - 30 - 21 - 2};
      int[] puntos2_x = new int[]{puntos_x[1], puntos_x[1] + 2, puntos_x[2] + 2, puntos_x[2]};
      int[] puntos_y = new int[]{dim_y / 2 - 21 - 2, dim_y / 2 - 21 + 2, dim_y / 2 + 21 + 2, dim_y / 2 + 21 - 2};
      int[] puntos2_y = new int[]{puntos_y[1], puntos_y[1] + 2, puntos_y[2] + 2, puntos_y[2]};
      g2.setColor(Color.lightGray);
      g2.fill(new Double(5.0D, (double)(dim_y / 2) - 10.0D, 20.0D, 20.0D));
      g2.setColor(this.l2rgb.lambda2RGB(this.lambda));
      g2.fill(new Double(25.0D, (double)(dim_y / 2) - 10.0D, 5.0D, 20.0D));
      g2.setColor(new Color(191, 237, 245));
      g2.fillOval(60, dim_y / 2 - 30, 5, 60);
      g2.fill(new Double((double)(dim_x / 2) - 60.0D, 10.0D, 60.0D, 5.0D));
      g2.setColor(new Color(52, 0, 164));
      g2.fill(new Double((double)(dim_x / 2) - 60.0D, 5.0D, 60.0D, 5.0D));
      double mov = this.distancia * this.deltad;
      g2.setColor(new Color(191, 237, 245));
      int vertice_x = dim_x - 65 + (int)mov;
      int vertice_y = dim_y / 2 - 30 + 8;
      BasicStroke bs = new BasicStroke(5.0F);
      g2.setStroke(bs);
      g2.draw(new java.awt.geom.Line2D.Double((double)vertice_x, (double)vertice_y, (double)vertice_x + 60.0D * Math.sin(this.angle), (double)vertice_y + 60.0D * Math.cos(this.angle)));
      g2.setColor(new Color(52, 0, 164));
      g2.draw(new java.awt.geom.Line2D.Double((double)(vertice_x + 5), (double)vertice_y, (double)vertice_x + 60.0D * Math.sin(this.angle) + 5.0D, (double)vertice_y + 60.0D * Math.cos(this.angle)));
      g2.setColor(new Color(191, 237, 245));
      BasicStroke bs2 = new BasicStroke();
      g2.setStroke(bs2);
      g2.fill(new Polygon(puntos_x, puntos_y, 4));
      g2.setColor(new Color(0, 82, 194));
      g2.fill(new Polygon(puntos2_x, puntos2_y, 4));

      int x;
      for(x = 0; x < 4; ++x) {
         puntos_x[x] += dim_x / 4 - 15;
         puntos2_x[x] += dim_x / 4 - 15;
      }

      g2.setColor(new Color(191, 237, 245));
      g2.fill(new Polygon(puntos_x, puntos_y, 4));
      g2.fillOval(dim_x / 2 - 60 + 5, dim_y - 40, 60, 5);
      g2.setColor(Color.white);
      g2.drawLine(dim_x / 2 - 60 + 5, dim_y - 10, dim_x / 2 + 5, dim_y - 10);
      g2.setColor(this.l2rgb.lambda2RGB(this.lambda));
      g2.drawLine(30, dim_y / 2, dim_x / 2 - 30 - 5, dim_y / 2);
      g2.drawLine(dim_x / 2 - 30 - 5, dim_y / 2, dim_x / 2 - 30 + 5, dim_y / 2 + 5);
      g2.drawLine(dim_x / 2 - 30, dim_y / 2 - 5, dim_x / 2 - 30 + 5, dim_y / 2 + 5);
      g2.drawLine(dim_x / 2 - 30, 15, dim_x / 2 - 30, dim_y / 2 - 5);
      g2.drawLine(dim_x / 2 - 30 + 5, dim_y / 2 + 5, dim_x / 2 + 5 + 27, dim_y / 2 + 5);
      g2.drawLine(dim_x / 2 + 5 + 27, dim_y / 2 + 5, dim_x / 2 + 5 + 32, dim_y / 2 + 8);
      g2.draw(new java.awt.geom.Line2D.Double((double)(dim_x / 2 + 5 + 32), (double)(dim_y / 2 + 8), (double)(dim_x - 68) + mov + 30.0D * Math.sin(this.angle), (double)(dim_y / 2 + 8)));
      g2.drawLine(dim_x / 2 - 30 + 5, dim_y / 2 + 5, dim_x / 2 - 30 + 5, dim_y - 11);
      x = dim_x / 4 - 15 + (int)((double)this.lambda * this.delta);
      int[] vertices_x = new int[]{x, x - 8, x - 8};
      int[] vertices_y = new int[]{dim_y / 2, dim_y / 2 - 8, dim_y / 2 + 8};
      g2.fill(new Polygon(vertices_x, vertices_y, 3));

      int i;
      for(i = 0; i < vertices_x.length; ++i) {
         vertices_x[i] += dim_x / 2 - 90;
         vertices_y[i] += 5;
      }

      g2.fill(new Polygon(vertices_x, vertices_y, 3));

      for(i = 0; i < vertices_x.length; ++i) {
         vertices_y[i] += 3;
      }

      x = dim_x - 70 - (int)((double)this.lambda * this.delta) + (int)mov;
      int[] vertices2_x = new int[]{x, x + 8, x + 8};
      g2.fill(new Polygon(vertices2_x, vertices_y, 3));
      int y = dim_y / 4 - 40 + (int)((double)this.lambda * this.delta);
      int[] verticesv_x = new int[]{dim_x / 2 - 30, dim_x / 2 - 30 - 8, dim_x / 2 - 30 + 8};
      int[] verticesv_y = new int[]{y, y - 8, y - 8};
      g2.fill(new Polygon(verticesv_x, verticesv_y, 3));

      int i;
      for(i = 0; i < verticesv_x.length; ++i) {
         verticesv_x[i] += 5;
         verticesv_y[i] += dim_x / 2 - 5;
      }

      g2.fill(new Polygon(verticesv_x, verticesv_y, 3));

      for(i = 0; i < verticesv_x.length; ++i) {
         verticesv_y[i] -= 15 + (int)mov;
      }

      g2.fill(new Polygon(verticesv_x, verticesv_y, 3));
      y = dim_y / 2 - 10 - (int)((double)this.lambda * this.delta);
      int[] verticesv2_y = new int[]{y, y + 8, y + 8};

      for(int i = 0; i < verticesv_x.length; ++i) {
         verticesv_x[i] -= 5;
      }

      g2.fill(new Polygon(verticesv_x, verticesv2_y, 3));
   }
}
