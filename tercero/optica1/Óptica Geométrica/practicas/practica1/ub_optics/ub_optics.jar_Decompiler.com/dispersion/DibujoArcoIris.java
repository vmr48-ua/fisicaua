package dispersion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D.Double;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JPanel;

public class DibujoArcoIris extends JPanel {
   int lambda;
   int lang;
   String[][] textograf = new String[][]{{"Arco secundario", "Arc secundari", "Secondary rainbow"}, {"Banda oscura de Alejandro", "Banda obscura d'Alexandre", "Alexander Dark Band"}, {"Arco primario", "Arc primari", "Primary rainbow"}, {"Horizonte", "Horitzó", "Horizon"}, {"Luz del Sol", "Llum del Sol", "Sunlight"}};
   DecimalFormatSymbols df_symb = new DecimalFormatSymbols();
   DecimalFormat df;

   public DibujoArcoIris() {
      this.df = new DecimalFormat("#.##", this.df_symb);

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setBackground(new Color(100, 180, 200));
      this.setMinimumSize(new Dimension(365, 248));
      this.setPreferredSize(new Dimension(365, 248));
   }

   public void putAtributos(int longonda, int idioma) {
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
      Font fuente2 = new Font("Dialog", 1, 12);
      char pto = '.';
      this.df_symb.setDecimalSeparator(pto);
      this.df.setDecimalFormatSymbols(this.df_symb);
      double n0 = 1.323585015D;
      double nA = 3878.664771D;
      double nB = -1.132388308E8D;
      double lambda2 = (double)this.lambda * (double)this.lambda;
      double lambda4 = lambda2 * lambda2;
      double n = n0 + nA / lambda2 + nB / lambda4;

      Color ncolor;
      double x;
      double y;
      double w;
      double h;
      for(int i = 0; i < 15; ++i) {
         ncolor = ycolores.lambda2RGB(400 + i * 20);
         g2.setPaint(ncolor);
         x = (double)(150 - i);
         y = (double)(dim_Ly - 150 - i);
         h = (double)(150 + 2 * i);
         w = 150.0D * Math.cos(0.7853981633974483D) + (double)(2 * i);
         g2.draw(new Double(x, y, w, h, -9.0D, 180.0D, 0));
      }

      double x0 = 150.0D * (1.0D + 0.5D * Math.cos(0.7853981633974483D));
      double y0 = (double)(dim_Ly - 75);
      double xf = x0 - 160.0D;
      double yf = y0 + 160.0D * Math.tan(0.17453292519943295D);
      g2.setPaint(Color.white);
      g2.drawLine((int)x0, (int)y0, (int)xf, (int)yf);
      ncolor = ycolores.lambda2RGB(this.lambda);
      double yh = y0 - 75.0D - (double)(this.lambda - 400) / 20.0D;
      g2.setColor(ncolor);
      g2.drawLine((int)x0, (int)yh, (int)xf, (int)yf);
      double zona_alejandro = 67.4D;

      for(int i = 0; i < 27; ++i) {
         ncolor = ycolores.lambda2RGB(700 - i * 11);
         g2.setPaint(ncolor);
         x = 150.0D - zona_alejandro - (double)i;
         y = (double)(dim_Ly - 150) - zona_alejandro - (double)i;
         h = 150.0D + 2.0D * zona_alejandro + (double)(2 * i);
         w = 150.0D * Math.cos(0.7853981633974483D) + 2.0D * zona_alejandro + (double)(2 * i);
         g2.draw(new Double(x, y, w, h, -10.0D, 179.0D, 0));
      }

      x0 = 150.0D * (1.0D + 0.5D * Math.cos(0.7853981633974483D));
      y0 = (double)(dim_Ly - 75);
      xf = x0 - 160.0D;
      yf = y0 + 160.0D * Math.tan(0.17453292519943295D);
      ncolor = ycolores.lambda2RGB(this.lambda);
      yh = y0 - 27.0D - 75.0D - zona_alejandro + (double)(this.lambda - 400) / 11.0D;
      g2.setColor(ncolor);
      g2.drawLine((int)x0, (int)yh, (int)xf, (int)yf);
      g2.setColor(Color.white);
      double x1 = (double)(dim_Lx - 10);
      double y1 = y0 + (x1 - x0) * Math.tan(0.20943951023931953D);
      double x2 = x0 - 75.0D - zona_alejandro - 10.0D;
      double y2 = y0 - (x0 - x2) * Math.tan(0.20943951023931953D);
      g2.drawLine((int)x2, (int)y2, (int)x1, (int)y1);
      g2.setFont(fuente);
      double b_primary = Math.sqrt((4.0D - n * n) / 3.0D);
      double b_secondary = Math.sqrt((9.0D - n * n) / 8.0D);
      double alpha_primary = Math.asin(b_primary);
      double alpha_secondary = Math.asin(b_secondary);
      double beta_primary = Math.asin(b_primary / n);
      double beta_secondary = Math.asin(b_secondary / n);
      double ang_vis_primary = (4.0D * beta_primary - 2.0D * alpha_primary) * 57.29577951308232D;
      double ang_vis_secondary = (-3.141592653589793D - 6.0D * beta_secondary + 2.0D * alpha_secondary + 6.283185307179586D) * 57.29577951308232D;
      g2.drawString(this.df.format(ang_vis_primary) + "º", (int)xf + 35, (int)yf - 12);
      g2.drawString(this.df.format(ang_vis_secondary) + "º", (int)xf - 12, (int)yf - 20);
      g2.setFont(fuente2);
      g2.drawString(this.textograf[0][this.lang], 15, 45);
      g2.drawString(this.textograf[1][this.lang], dim_Lx / 2 - 45, 75);
      g2.drawString(this.textograf[2][this.lang], dim_Lx / 2 - 20, 150);
      g2.drawString(this.textograf[3][this.lang], dim_Lx / 2 + 100, 210);
      double xojo = 20.0D;
      double yojo = yf + 5.0D;
      g2.draw(new Double(xojo - 15.0D, yojo - 15.0D, 30.0D, 30.0D, 0.0D, 42.0D, 0));
      g2.drawLine((int)xojo, (int)yojo, (int)xojo + 20, (int)yojo);
      g2.drawLine((int)xojo, (int)yojo, (int)(xojo + 20.0D * Math.cos(0.7853981633974483D)), (int)(yojo - 20.0D * Math.sin(0.7853981633974483D)));
      g2.fill(new java.awt.geom.Ellipse2D.Double(xojo + 10.0D, yojo - 15.0D * Math.tan(0.39269908169872414D), 5.0D, 5.0D));
      double xsol = 20.0D;
      double ysol = (double)(dim_Ly / 4 + 20);
      double xfsol = xsol + 50.0D;
      double yfsol = ysol - 50.0D * Math.tan(0.17453292519943295D);
      g2.drawLine((int)xsol, (int)ysol, (int)xfsol, (int)yfsol);
      int[] coordxtriang = new int[]{(int)xfsol, (int)xfsol - 5, (int)xfsol};
      int[] coordytriang = new int[]{(int)yfsol, (int)yfsol - 2, (int)yfsol + 5};
      g2.fill(new Polygon(coordxtriang, coordytriang, 3));
      g2.drawString(this.textograf[4][this.lang], (int)xsol - 19, (int)ysol + 12);
   }
}
