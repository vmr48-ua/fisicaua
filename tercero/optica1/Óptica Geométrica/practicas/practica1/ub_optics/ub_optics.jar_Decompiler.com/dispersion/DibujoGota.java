package dispersion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D.Double;
import javax.swing.JPanel;

public class DibujoGota extends JPanel {
   int lambda;
   double param_imp;
   int lang;
   String[][] textograf = new String[][]{{"Arco primario", "Arc primari", "Primary rainbow"}, {"Arco secundario", "Arc secundari", "Secondary rainbow"}, {"Gotas de agua", "Gotes d'aigua", "Water drops"}};

   public DibujoGota() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setMinimumSize(new Dimension(365, 248));
      this.setPreferredSize(new Dimension(365, 248));
   }

   public void putAtributos(double paramimp, int longonda, int idioma) {
      this.param_imp = paramimp;
      this.lambda = longonda;
      this.lang = idioma;
   }

   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      Dimension dim = this.getSize();
      int dim_Lx = dim.width;
      int dim_Ly = dim.height;
      YoungColor ycolores = new YoungColor();
      Font fuente = new Font("Dialog", 1, 12);
      Font fuente2 = new Font("Dialog", 1, 14);
      BasicStroke stroke2 = new BasicStroke(2.0F);
      g2.setFont(fuente2);
      g2.setColor(Color.white);
      g2.drawString(this.textograf[2][this.lang], 30, 30);
      double n0 = 1.323585015D;
      double nA = 3878.664771D;
      double nB = -1.132388308E8D;
      double lambda2 = (double)this.lambda * (double)this.lambda;
      double lambda4 = lambda2 * lambda2;
      double n = n0 + nA / lambda2 + nB / lambda4;
      double Cx = (double)dim_Lx / 4.0D;
      double Cy = (double)dim_Ly / 2.0D;
      double radio = 40.0D;
      g2.setFont(fuente);
      g2.setColor(Color.white);
      g2.drawString(this.textograf[0][this.lang], (int)Cx - 30, (int)(Cy - radio) - 10);
      double alpha = Math.asin(this.param_imp);
      double Ay = Cy - radio * this.param_imp;
      double Ax = Cx - radio * Math.cos(alpha);
      double Ax_prima = Ax - 20.0D;
      double beta = 2.0D * Math.asin(this.param_imp / n) - alpha;
      double Bx = Cx + radio * Math.cos(beta);
      double By = Cy - radio * Math.sin(beta);
      double delta = 4.0D * Math.asin(this.param_imp / n) - alpha;
      double Dx = Cx - radio * Math.cos(delta);
      double Dy = Cy + radio * Math.sin(delta);
      double l = 75.0D * Math.cos(1.5707963267948966D - alpha);
      double l_prima = 75.0D * Math.sin(1.5707963267948966D - alpha);
      double Dx_prima = Dx - l;
      double Dy_prima = Dy + l_prima;
      g2.setPaint(new Color(230, 255, 255));
      g2.fill(new Double(Cx - radio, Cy - radio, 2.0D * radio, 2.0D * radio));
      Color ncolor = ycolores.lambda2RGB(this.lambda);
      g2.setPaint(ncolor);
      g2.setStroke(stroke2);
      g2.drawLine((int)Ax_prima, (int)Ay, (int)Ax, (int)Ay);
      g2.drawLine((int)Ax, (int)Ay, (int)Bx, (int)By);
      g2.drawLine((int)Bx, (int)By, (int)Dx, (int)Dy);
      g2.drawLine((int)Dx, (int)Dy, (int)Dx_prima, (int)Dy_prima);
      int[] coordxtriang = new int[]{(int)Ax_prima, (int)Ax_prima - 5, (int)Ax_prima - 5};
      int[] coordytriang = new int[]{(int)Ay, (int)Ay - 5, (int)Ay + 5};
      g2.fill(new Polygon(coordxtriang, coordytriang, 3));
      g2.drawLine((int)Ax_prima, (int)Ay, (int)Ax_prima - 15, (int)Ay);
      coordxtriang[0] = (int)Dx_prima;
      coordxtriang[1] = (int)(Dx_prima - 5.0D * Math.cos(2.0D * alpha));
      coordxtriang[2] = (int)(Dx_prima + 5.0D);
      coordytriang[0] = (int)Dy_prima;
      coordytriang[1] = (int)(Dy_prima - 5.0D);
      coordytriang[2] = (int)(Dy_prima - 5.0D * Math.cos(2.0D * alpha));
      g2.fill(new Polygon(coordxtriang, coordytriang, 3));
      double Cx2 = 3.0D * (double)dim_Lx / 4.0D;
      double Cy2 = (double)dim_Ly / 2.0D;
      double radio2 = 40.0D;
      g2.setFont(fuente);
      g2.setColor(Color.white);
      g2.drawString(this.textograf[1][this.lang], (int)Cx2 - 30, (int)(Cy2 - radio2) - 10);
      double alpha2 = Math.asin(this.param_imp);
      double Ay2 = Cy2 + radio2 * this.param_imp;
      double Ax2 = Cx2 - radio2 * Math.cos(alpha2);
      double Ax_prima2 = Ax2 - 20.0D;
      double beta2 = 2.0D * Math.asin(this.param_imp / n) - alpha2;
      double Bx2 = Cx2 + radio2 * Math.cos(beta2);
      double By2 = Cy2 + radio2 * Math.sin(beta2);
      double Dx2 = Cx2 + radio2 * Math.cos(alpha2);
      double Dy2 = Cy2 - radio2 * this.param_imp;
      double Ex2 = Cx2 - radio2 * Math.cos(beta2);
      double Ey2 = Cy2 - radio2 * Math.sin(beta2);
      double l2 = 75.0D * Math.cos(1.5707963267948966D - alpha2);
      double l_prima2 = 75.0D * Math.sin(1.5707963267948966D - alpha2);
      double Ex_prima2 = Ex2 - l2;
      double Ey_prima2 = Ey2 + l_prima2;
      g2.setPaint(new Color(230, 255, 255));
      g2.fill(new Double(Cx2 - radio2, Cy2 - radio2, 2.0D * radio2, 2.0D * radio2));
      g2.setPaint(ncolor);
      g2.drawLine((int)Ax_prima2, (int)Ay2, (int)Ax2, (int)Ay2);
      g2.drawLine((int)Ax2, (int)Ay2, (int)Bx2, (int)By2);
      g2.drawLine((int)Bx2, (int)By2, (int)Dx2, (int)Dy2);
      g2.drawLine((int)Dx2, (int)Dy2, (int)Ex2, (int)Ey2);
      g2.drawLine((int)Ex2, (int)Ey2, (int)Ex_prima2, (int)Ey_prima2);
      int[] coordxtriang2 = new int[]{(int)Ax_prima2, (int)Ax_prima2 - 5, (int)Ax_prima2 - 5};
      int[] coordytriang2 = new int[]{(int)Ay2, (int)Ay2 - 5, (int)Ay2 + 5};
      g2.fill(new Polygon(coordxtriang2, coordytriang2, 3));
      g2.drawLine((int)Ax_prima2, (int)Ay2, (int)Ax_prima2 - 25, (int)Ay2);
      coordxtriang2[0] = (int)Ex_prima2;
      coordxtriang2[1] = (int)(Ex_prima2 - 5.0D * Math.cos(2.0D * alpha2));
      coordxtriang2[2] = (int)(Ex_prima2 + 5.0D);
      coordytriang2[0] = (int)Ey_prima2;
      coordytriang2[1] = (int)(Ey_prima2 - 5.0D);
      coordytriang2[2] = (int)(Ey_prima2 - 5.0D * Math.cos(2.0D * alpha2));
      g2.fill(new Polygon(coordxtriang2, coordytriang2, 3));
   }
}
