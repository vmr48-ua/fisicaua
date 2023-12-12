package appletlamines;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class MostraPanel extends JPanel {
   Mostra mostra1;
   double lambda_cur;
   double xmax;
   double ymax;
   double xfact = 1.0D;
   double yfact = 1.0D;
   public int mode = 0;
   String[] Descript1 = new String[]{"d=diferència de fase entre raigs transmesos", "d=diferencia de fase entre rayos transmitidos", "d=Phase difference between transmitted rays"};
   String[] Descript2 = new String[]{"θ=angle dels raigs a l'interior de la làmina", "θ=angulo de los rayos en el interior de la lámina", "θ=ray angle inside the thin film"};

   public MostraPanel() {
      this.mostra1 = new Mostra();

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public MostraPanel(Mostra m) {
      this.mostra1 = m;

      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void drawArrowDown(Graphics g, Color c, int x, int y, double T) {
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = x + (int)(this.xfact * (1.5D - T));
      Y[0] = y;
      X[1] = x + (int)(this.xfact * (1.5D - T));
      Y[1] = y + (int)(this.yfact * 2.0D);
      X[2] = x + (int)(this.xfact * (1.25D - T));
      Y[2] = y + (int)(this.yfact * 2.0D);
      X[3] = x + (int)(this.xfact * 1.5D);
      Y[3] = y + (int)(this.yfact * 3.0D);
      X[4] = x + (int)(this.xfact * (1.75D + T));
      Y[4] = y + (int)(this.yfact * 2.0D);
      X[5] = x + (int)(this.xfact * (1.5D + T));
      Y[5] = y + (int)(this.yfact * 2.0D);
      X[6] = x + (int)(this.xfact * (1.5D + T));
      Y[6] = y;
      X[7] = X[0];
      Y[7] = Y[0];
      g.setColor(c);
      g.fillPolygon(X, Y, 8);
      g.setColor(Color.white);
      g.drawPolygon(X, Y, 8);
   }

   private void drawArrowsDownBot(Graphics g, Color c, int x, int y, double T, double angle) {
      Graphics2D g2d = (Graphics2D)g;
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = 0 - (int)(this.xfact * 0.15D);
      Y[0] = 0 - (int)(this.yfact * 0.15D);
      X[1] = 0;
      Y[1] = 0;
      X[2] = 0 + (int)(this.xfact * 0.15D);
      Y[2] = 0 - (int)(this.yfact * 0.15D);
      X[3] = 0;
      Y[3] = 0;
      X[4] = 0;
      Y[4] = -((int)(this.yfact * 3.0D));

      for(int i = 0; i < 5; ++i) {
         int x0 = x + i * (int)(this.xfact * 0.8D);
         g2d.translate(x0, y);
         g2d.rotate(-angle);
         g.setColor(c);
         g.drawPolyline(X, Y, 5);
         g2d.rotate(angle);
         g2d.translate(-x0, -y);
      }

   }

   private void drawArrowsUpBot(Graphics g, Color c, int x, int y, double T, double angle) {
      Graphics2D g2d = (Graphics2D)g;
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = 0;
      Y[0] = 0;
      X[1] = 0;
      Y[1] = -((int)(this.yfact * 3.0D));
      X[2] = 0 - (int)(this.xfact * 0.15D);
      Y[2] = -((int)(this.yfact * 2.85D));
      X[3] = 0;
      Y[3] = -((int)(this.yfact * 3.0D));
      X[4] = 0 + (int)(this.xfact * 0.15D);
      Y[4] = 0 - (int)(this.yfact * 2.85D);

      for(int i = 0; i < 5; ++i) {
         int x0 = x + i * (int)(this.xfact * 0.8D);
         g2d.translate(x0, y);
         g2d.rotate(angle);
         g.setColor(c);
         g.drawPolyline(X, Y, 5);
         g2d.rotate(-angle);
         g2d.translate(-x0, -y);
      }

   }

   private void drawArrowUpBot(Graphics g, Color c, int x, int y, double llarg, double angle) {
      Graphics2D g2d = (Graphics2D)g;
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = 0;
      Y[0] = 0;
      X[1] = 0;
      Y[1] = -((int)(this.yfact * llarg));
      X[2] = 0 - (int)(this.xfact * 0.15D);
      Y[2] = -((int)(this.yfact * (llarg - 0.15D)));
      X[3] = 0;
      Y[3] = -((int)(this.yfact * llarg));
      X[4] = 0 + (int)(this.xfact * 0.15D);
      Y[4] = 0 - (int)(this.yfact * (llarg - 0.15D));
      g2d.translate(x, y);
      g2d.rotate(angle);
      g.setColor(c);
      g.drawPolyline(X, Y, 5);
      g2d.rotate(-angle);
      g2d.translate(-x, -y);
   }

   private void drawArrowsDownTop(Graphics g, Color c, int x, int y, double T, double angle) {
      Graphics2D g2d = (Graphics2D)g;
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = 0;
      Y[0] = 0;
      X[1] = 0;
      Y[1] = (int)(this.yfact * 3.0D);
      X[2] = 0 - (int)(this.xfact * 0.15D);
      Y[2] = (int)(this.yfact * 2.85D);
      X[3] = 0;
      Y[3] = (int)(this.yfact * 3.0D);
      X[4] = 0 + (int)(this.xfact * 0.15D);
      Y[4] = 0 + (int)(this.yfact * 2.85D);

      for(int i = 0; i < 5; ++i) {
         int x0 = x + i * (int)(this.xfact * 0.8D);
         g2d.translate(x0, y);
         g2d.rotate(-angle);
         g.setColor(c);
         g.drawPolyline(X, Y, 5);
         g2d.rotate(angle);
         g2d.translate(-x0, -y);
      }

   }

   private void drawArrowDownTop(Graphics g, Color c, int x, int y, double llarg, double angle) {
      Graphics2D g2d = (Graphics2D)g;
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = 0;
      Y[0] = 0;
      X[1] = 0;
      Y[1] = (int)(this.yfact * llarg);
      X[2] = 0 - (int)(this.xfact * 0.15D);
      Y[2] = (int)(this.yfact * (llarg - 0.15D));
      X[3] = 0;
      Y[3] = (int)(this.yfact * llarg);
      X[4] = 0 + (int)(this.xfact * 0.15D);
      Y[4] = 0 + (int)(this.yfact * (llarg - 0.15D));
      g2d.translate(x, y);
      g2d.rotate(-angle);
      g.setColor(c);
      g.drawPolyline(X, Y, 5);
      g2d.rotate(angle);
      g2d.translate(-x, -y);
   }

   private void drawArrowUp(Graphics g, Color c, int x, int y, double R) {
      int[] X = new int[8];
      int[] Y = new int[8];
      X[0] = x + (int)(this.xfact * 1.5D);
      Y[0] = y;
      X[1] = x + (int)(this.xfact * (1.75D + R));
      Y[1] = y + (int)this.yfact;
      X[2] = x + (int)(this.xfact * (1.5D + R));
      Y[2] = y + (int)this.yfact;
      X[3] = x + (int)(this.xfact * (1.5D + R));
      Y[3] = y + (int)(this.yfact * 3.0D);
      X[4] = x + (int)(this.xfact * (1.5D - R));
      Y[4] = y + (int)(this.yfact * 3.0D);
      X[5] = x + (int)(this.xfact * (1.5D - R));
      Y[5] = y + (int)this.yfact;
      X[6] = x + (int)(this.xfact * (1.25D - R));
      Y[6] = y + (int)this.yfact;
      X[7] = X[0];
      Y[7] = Y[0];
      g.setColor(c);
      g.fillPolygon(X, Y, 8);
      g.setColor(Color.white);
      g.drawPolygon(X, Y, 8);
   }

   public void paintComponent(Graphics g) {
      if (this.mode == 0) {
         this.paintComponentSample(g);
      } else {
         this.paintComponentPathDif(g);
      }

   }

   public void paintComponentPathDif(Graphics g) {
      super.paintComponent(g);
      DecimalFormat df1 = new DecimalFormat("#.##E0");
      DecimalFormat df2 = new DecimalFormat("#.##");
      Graphics2D g2d = (Graphics2D)g;
      Dimension d = this.getSize();
      this.xfact = d.getWidth() / 14.0D;
      this.yfact = d.getHeight() / 10.0D;
      double thfact = 2.0D;
      double ns = this.mostra1.ns(this.mostra1.lambda);
      double ks = this.mostra1.ks(this.mostra1.lambda);
      double nl = this.mostra1.n(this.mostra1.lambda);
      double kl = this.mostra1.k(this.mostra1.lambda);
      BasicStroke MyStroke = new BasicStroke(3.0F, 1, 1);
      g.setColor(Color.white);
      g2d.setStroke(MyStroke);
      g.drawLine((int)(1.0D * this.xfact), (int)(3.0D * this.yfact), (int)(13.0D * this.xfact), (int)(3.0D * this.yfact));
      g.drawLine((int)(1.0D * this.xfact), (int)(5.0D * this.yfact), (int)(13.0D * this.xfact), (int)(5.0D * this.yfact));
      g2d.translate((int)(3.0D * this.xfact), (int)(3.0D * this.yfact));
      g2d.rotate(-this.mostra1.angle);
      g.drawLine(0, 0, 0, -((int)(3.0D * this.yfact)));
      g.drawLine(0, -((int)(1.5D * this.yfact)), (int)(0.25D * this.xfact), -((int)(1.75D * this.yfact)));
      g.drawLine(0, -((int)(1.5D * this.yfact)), -((int)(0.25D * this.xfact)), -((int)(1.75D * this.yfact)));
      g2d.rotate(this.mostra1.angle);
      g2d.translate(-((int)(3.0D * this.xfact)), -((int)(3.0D * this.yfact)));
      double theta = Math.asin(1.0D / this.mostra1.n(this.mostra1.lambda) * Math.sin(this.mostra1.angle));
      this.drawArrowUpBot(g, Color.white, (int)(3.0D * this.xfact), (int)(3.0D * this.yfact), 2.5D, this.mostra1.angle);

      for(int i = 0; i < 5; ++i) {
         g.drawLine((int)((3.0D + (double)(2 * i) * thfact * Math.tan(theta)) * this.xfact), (int)(3.0D * this.yfact), (int)((3.0D + (double)(2 * i + 1) * thfact * Math.tan(theta)) * this.xfact), (int)((3.0D + thfact) * this.yfact));
         g.drawLine((int)((3.0D + (double)(2 * i + 1) * thfact * Math.tan(theta)) * this.xfact), (int)((3.0D + thfact) * this.yfact), (int)((3.0D + (double)(2 * i + 2) * thfact * Math.tan(theta)) * this.xfact), (int)(3.0D * this.yfact));
         this.drawArrowDownTop(g, Color.white, (int)((3.0D + (double)(2 * i + 1) * thfact * Math.tan(theta)) * this.xfact), (int)((3.0D + thfact) * this.yfact), 2.0D, Math.asin(Math.sin(this.mostra1.angle) / ns));
         this.drawArrowUpBot(g, Color.white, (int)((3.0D + (double)(2 * i + 2) * thfact * Math.tan(theta)) * this.xfact), (int)(3.0D * this.yfact), 2.5D, this.mostra1.angle);
      }

      float[] dash = new float[]{4.0F, 4.0F};
      g2d.setStroke(new BasicStroke(1.0F, 1, 1, 1.0F, dash, 0.0F));
      g.drawLine((int)(3.0D * this.xfact), (int)(1.5D * this.yfact), (int)(3.0D * this.xfact), (int)(5.5D * this.yfact));
      g2d.setStroke(new BasicStroke(1.0F));
      g.drawArc((int)(2.0D * this.xfact), (int)(2.0D * this.yfact), (int)(2.0D * this.xfact), (int)(2.0D * this.yfact), 270, (int)(theta / 3.141592653589793D * 180.0D));
      g.setColor(Color.white);
      FontRenderContext FRC = g2d.getFontRenderContext();
      Font F = new Font("Helvetica", 0, 18);
      g.setFont(F);
      char[] str = new char[258];
      new String();
      String S = "d = 2·p/l·2·n·d·cos(q)+p";
      S.getChars(0, S.length(), str, 0);
      str[0] = 916;
      str[6] = 960;
      str[8] = 955;
      str[20] = 952;
      str[23] = str[6];
      str[24] = 0;
      if (this.mostra1.ns(this.mostra1.lambda) > this.mostra1.n(this.mostra1.lambda)) {
         S = String.copyValueOf(str, 0, 24);
      } else {
         S = String.copyValueOf(str, 0, 22);
      }

      double fase = 4.0D * nl * this.mostra1.thick / this.mostra1.lambda * Math.cos(theta);
      if (this.mostra1.ns(this.mostra1.lambda) > this.mostra1.n(this.mostra1.lambda)) {
         ++fase;
      }

      S = S + " = " + df2.format(fase) + "π";
      int ampletext = (int)g2d.getFont().getStringBounds(S, FRC).getWidth();
      g.drawString(S, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(7.7D * this.yfact));
      S = this.Descript1[Lamines.lang];
      S.getChars(0, S.length(), str, 0);
      str[0] = 916;
      S = String.copyValueOf(str, 0, S.length());
      F = new Font("Helvetica", 0, 12);
      g.setFont(F);
      ampletext = (int)g2d.getFont().getStringBounds(S, FRC).getWidth();
      g.drawString(S, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(8.7D * this.yfact));
      S = this.Descript2[Lamines.lang];
      ampletext = (int)g2d.getFont().getStringBounds(S, FRC).getWidth();
      g.drawString(S, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(9.7D * this.yfact));
      F = new Font("Dialog", 0, 12);
      g.setFont(F);
      S = "n_s=" + df2.format(ns);
      g.drawString(S, (int)(0.3D * this.xfact), (int)(6.0D * this.yfact));
      S = "n_l=" + df2.format(nl);
      g.drawString(S, (int)(0.3D * this.xfact), (int)(3.8D * this.yfact));
      S = "k_l=" + df1.format(kl);
      g.drawString(S, (int)(0.3D * this.xfact), (int)(4.6D * this.yfact));
      S = "θ";
      g.drawString(S, (int)(3.1D * this.xfact), (int)(4.5D * this.yfact));
   }

   public void paintComponentSample(Graphics g) {
      super.paintComponent(g);
      Dimension d = this.getSize();
      this.xfact = d.getWidth() / 14.0D;
      this.yfact = d.getHeight() / 10.0D;
      if (this.mostra1.flag_substrate) {
         g.setColor(Color.blue);
         g.fillRect((int)this.xfact, (int)(4.5D * this.yfact), (int)(12.0D * this.xfact), (int)(2.0D * this.yfact));
         g.setColor(Color.white);
         g.drawRect((int)this.xfact, (int)(4.5D * this.yfact), (int)(12.0D * this.xfact), (int)(2.0D * this.yfact));
      }

      g.setColor(Color.yellow);
      g.fillRect((int)(2.0D * this.xfact), (int)((4.5D - this.mostra1.thick / this.mostra1.thick_max) * this.yfact), (int)(10.0D * this.xfact), (int)(this.mostra1.thick / this.mostra1.thick_max * this.yfact));
      g.setColor(Color.white);
      g.drawRect((int)(2.0D * this.xfact), (int)((4.5D - this.mostra1.thick / this.mostra1.thick_max) * this.yfact), (int)(10.0D * this.xfact), (int)(this.mostra1.thick / this.mostra1.thick_max * this.yfact));
      double[] RT = new double[2];
      double[] RTs = new double[2];
      double[] RTp = new double[2];
      this.mostra1.getRT(RTs, 's');
      this.mostra1.getRT(RTp, 'p');
      RT[0] = 0.5D * (RTs[0] + RTp[0]);
      RT[1] = 0.5D * (RTs[1] + RTp[1]);
      DecimalFormat df1 = new DecimalFormat("#.0");
      DecimalFormat df2 = new DecimalFormat("#.##");
      this.drawArrowsDownBot(g, Color.green, (int)(this.xfact * 2.5D), (int)((4.3D - this.mostra1.thick / this.mostra1.thick_max) * this.yfact), 1.0D, this.mostra1.angle);
      this.drawArrowsUpBot(g, Color.magenta, (int)(this.xfact * 4.0D), (int)((4.3D - this.mostra1.thick / this.mostra1.thick_max) * this.yfact), RT[0], this.mostra1.angle);
      g.setColor(Color.magenta);
      g.drawString("R( " + df1.format(this.mostra1.lambda * 1.0E9D) + " ) = " + df2.format(RT[0]), (int)(this.xfact * 10.0D), (int)(1.25D * this.yfact));
      if (this.mostra1.flag_substrate) {
         this.drawArrowsDownTop(g, Color.cyan, (int)(this.xfact * 3.5D), (int)(this.yfact * 6.75D), RT[0], this.mostra1.angle);
      } else {
         this.drawArrowsDownTop(g, Color.cyan, (int)(this.xfact * 3.5D), (int)(this.yfact * 4.75D), RT[0], this.mostra1.angle);
      }

      g.setColor(Color.cyan);
      g.drawString("T( " + df1.format(this.mostra1.lambda * 1.0E9D) + " ) = " + df2.format(RT[1]), (int)(10.0D * this.xfact), (int)(7.25D * this.yfact));
   }

   private void jbInit() throws Exception {
      this.setBackground(Color.black);
      this.setEnabled(true);
      this.setMaximumSize(new Dimension(375, 260));
      this.setMinimumSize(new Dimension(375, 260));
      this.setPreferredSize(new Dimension(375, 260));
      this.setToolTipText("");
   }
}
