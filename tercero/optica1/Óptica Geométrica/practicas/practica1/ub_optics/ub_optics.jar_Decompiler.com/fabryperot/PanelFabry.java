package fabryperot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D.Double;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class PanelFabry extends JPanel {
   FabryCalc fabry1;
   FabryCalc fabry2;
   double lambda_cur;
   double xmax;
   double ymax;
   double xfact = 1.0D;
   double yfact = 1.0D;
   public int mode = 0;
   YoungColor l2rgb = new YoungColor();
   String[] Descript1 = new String[]{"d=diferència de fase entre raigs transmesos", "d=diferencia de fase entre rayos transmitidos", "d=Phase difference between transmitted rays"};
   String[] Descript2 = new String[]{"A=angle dels raigs a l'interior de la làmina", "A=angulo de los rayos en el interior de la lámina", "A=ray angle inside the thin film"};
   String[] Descript3 = new String[]{"L1, L2 = longituds d'ona; d = gruix", "L1, L2 = longitudes de onda; d = espesor", "L1, L2 = wavelengths; d = thickness"};
   String[] Lent1 = new String[]{"Lent 1", "Lente 1", "Lens 1"};
   String[] Lent2 = new String[]{"Lent 2", "Lente 2", "Lens 2"};

   public PanelFabry() {
      this.fabry1 = new FabryCalc();

      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public PanelFabry(FabryCalc m) {
      this.fabry1 = m;

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
      new DecimalFormat("#.##E0");
      DecimalFormat df2 = new DecimalFormat("#.##");
      Graphics2D g2d = (Graphics2D)g;
      Dimension d = this.getSize();
      this.fabry1 = AppletFabry.fabry1;
      this.fabry2 = AppletFabry.fabry2;
      this.xfact = d.getWidth() / 14.0D;
      this.yfact = d.getHeight() / 10.0D;
      double thfact = 2.0D;
      float[] dash = new float[2];
      BasicStroke MyStroke = new BasicStroke(3.0F, 1, 1);
      g.setColor(Color.white);
      g2d.setStroke(MyStroke);
      g.drawLine((int)(1.0D * this.xfact), (int)(3.0D * this.yfact), (int)(13.0D * this.xfact), (int)(3.0D * this.yfact));
      g.drawLine((int)(1.0D * this.xfact), (int)(5.0D * this.yfact), (int)(13.0D * this.xfact), (int)(5.0D * this.yfact));
      double ang_max = Math.atan(this.fabry1.sizeScreen / this.fabry1.fScreen);
      double theta = AppletFabry.fabry1.angle / ang_max * 3.141592653589793D / 6.0D;
      g2d.setStroke(new BasicStroke(1.0F));
      Color auxColor = this.l2rgb.lambda2RGB((int)AppletFabry.fabry1.lambda);
      g2d.setColor(auxColor);
      g2d.translate((int)(3.0D * this.xfact), (int)(3.0D * this.yfact));
      g2d.rotate(-theta);
      g.drawLine(0, 0, 0, -((int)(3.0D * this.yfact)));
      g.drawLine(0, -((int)(1.5D * this.yfact)), (int)(0.25D * this.xfact), -((int)(1.75D * this.yfact)));
      g.drawLine(0, -((int)(1.5D * this.yfact)), -((int)(0.25D * this.xfact)), -((int)(1.75D * this.yfact)));
      g2d.rotate(theta);
      g2d.translate(-((int)(3.0D * this.xfact)), -((int)(3.0D * this.yfact)));
      if (theta >= 1.5707963267948966D) {
         theta = 1.5697963267948967D;
      }

      this.drawArrowUpBot(g, auxColor, (int)(3.0D * this.xfact), (int)(3.0D * this.yfact), 2.5D, theta);

      for(int i = 0; i < 5; ++i) {
         g.drawLine((int)((3.0D + (double)(2 * i) * thfact * Math.tan(theta)) * this.xfact), (int)(3.0D * this.yfact), (int)((3.0D + (double)(2 * i + 1) * thfact * Math.tan(theta)) * this.xfact), (int)((3.0D + thfact) * this.yfact));
         g.drawLine((int)((3.0D + (double)(2 * i + 1) * thfact * Math.tan(theta)) * this.xfact), (int)((3.0D + thfact) * this.yfact), (int)((3.0D + (double)(2 * i + 2) * thfact * Math.tan(theta)) * this.xfact), (int)(3.0D * this.yfact));
         this.drawArrowDownTop(g, auxColor, (int)((3.0D + (double)(2 * i + 1) * thfact * Math.tan(theta)) * this.xfact), (int)((3.0D + thfact) * this.yfact), 1.5D, theta);
         this.drawArrowUpBot(g, auxColor, (int)((3.0D + (double)(2 * i + 2) * thfact * Math.tan(theta)) * this.xfact), (int)(3.0D * this.yfact), 2.5D, theta);
      }

      dash[0] = 4.0F;
      dash[1] = 4.0F;
      g.setColor(Color.white);
      g2d.setStroke(new BasicStroke(1.0F, 1, 1, 1.0F, dash, 0.0F));
      g.drawLine((int)(3.0D * this.xfact), (int)(1.5D * this.yfact), (int)(3.0D * this.xfact), (int)(5.5D * this.yfact));
      g2d.setStroke(new BasicStroke(1.0F));
      g.drawArc((int)(2.0D * this.xfact), (int)(2.0D * this.yfact), (int)(2.0D * this.xfact), (int)(2.0D * this.yfact), 270, (int)(theta / 3.141592653589793D * 180.0D));
      FontRenderContext FRC = g2d.getFontRenderContext();
      Font F = new Font("Helvetica", 0, 18);
      g.setFont(F);
      char[] str = new char[258];
      new String();
      String S = "D = 2·PI/L1·2·d·cos(A)";
      double fase = 4.0D * this.fabry1.thick / this.fabry1.lambda * Math.cos(AppletFabry.fabry1.angle) * 1000000.0D;
      String S1 = S + " = " + df2.format(fase) + "PI";
      int ampletext = (int)g2d.getFont().getStringBounds(S1, FRC).getWidth();
      g.drawString(S1, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(7.2D * this.yfact));
      fase = 4.0D * this.fabry2.thick / this.fabry2.lambda * Math.cos(AppletFabry.fabry1.angle) * 1000000.0D;
      S = "D = 2·PI/L2·2·d·cos(A)";
      String S2 = S + " = " + df2.format(fase) + "PI";
      ampletext = (int)g2d.getFont().getStringBounds(S2, FRC).getWidth();
      g.drawString(S2, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(8.0D * this.yfact));
      S = this.Descript1[AppletFabry.lang];
      S.getChars(0, S.length(), str, 0);
      str[0] = 'D';
      S = String.copyValueOf(str, 0, S.length());
      F = new Font("Helvetica", 0, 12);
      g.setFont(F);
      ampletext = (int)g2d.getFont().getStringBounds(S, FRC).getWidth();
      g.drawString(S, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(8.7D * this.yfact));
      S = this.Descript3[AppletFabry.lang];
      ampletext = (int)g2d.getFont().getStringBounds(S, FRC).getWidth();
      g.drawString(S, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(9.3D * this.yfact));
      S = this.Descript2[AppletFabry.lang];
      ampletext = (int)g2d.getFont().getStringBounds(S, FRC).getWidth();
      g.drawString(S, (int)(7.0D * this.xfact - (double)ampletext / 2.0D), (int)(9.9D * this.yfact));
      F = new Font("Dialog", 0, 12);
      g.setFont(F);
      S = "A";
      g.drawString(S, (int)(3.1D * this.xfact), (int)(4.5D * this.yfact));
   }

   public void paintComponentSample(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      Dimension d = this.getSize();
      this.xfact = d.getWidth() / 60.0D;
      this.yfact = d.getHeight() / 50.0D;
      this.fabry1 = AppletFabry.fabry1;
      BasicStroke MyStroke = new BasicStroke(3.0F, 1, 1);
      Color auxColor = this.l2rgb.lambda2RGB((int)this.fabry1.lambda);
      g.setColor(auxColor);
      g2d.setStroke(MyStroke);
      double factor = this.fabry1.sizeSource / this.fabry1.sizeSourcemax;
      g.drawLine(5, (int)((25.0D - 5.0D * factor) * this.yfact), 5, (int)((25.0D + 5.0D * factor) * this.yfact));
      factor = (this.fabry1.fSource - this.fabry1.fSourcemin) / (this.fabry1.fSourcemax - this.fabry1.fSourcemin);
      g2d.setStroke(new BasicStroke(1.0F));
      g.setColor(Color.cyan);
      g2d.draw(new Double(15.0D + factor * 10.0D * this.xfact, 20.0D * this.yfact, 9.0D, 10.0D * this.yfact));
      g.drawString(this.Lent1[AppletFabry.lang], (int)(15.0D + factor * 10.0D * this.xfact), (int)(35.0D * this.yfact));
      g2d.setStroke(new BasicStroke(3.0F));
      g.setColor(Color.cyan);
      g.drawLine((int)(20.0D + 12.5D * this.xfact), (int)(5.0D * this.yfact), (int)(20.0D + 12.5D * this.xfact), (int)(45.0D * this.yfact));
      g2d.setStroke(new BasicStroke(1.0F));
      g.setColor(Color.yellow);
      g.drawLine((int)(22.0D + 12.5D * this.xfact), (int)(5.0D * this.yfact), (int)(22.0D + 12.5D * this.xfact), (int)(45.0D * this.yfact));
      factor = this.fabry1.thick / this.fabry1.thick_max;
      g.drawLine((int)(22.0D + (12.5D + 10.0D * factor) * this.xfact), (int)(5.0D * this.yfact), (int)(22.0D + (12.5D + 10.0D * factor) * this.xfact), (int)(45.0D * this.yfact));
      g2d.setStroke(new BasicStroke(3.0F));
      g.setColor(Color.cyan);
      g.drawLine((int)(24.0D + (12.5D + 10.0D * factor) * this.xfact), (int)(5.0D * this.yfact), (int)(24.0D + (12.5D + 10.0D * factor) * this.xfact), (int)(45.0D * this.yfact));
      factor = (this.fabry1.fScreen - this.fabry1.fScreenmin) / (this.fabry1.fScreenmax - this.fabry1.fScreenmin);
      g2d.setStroke(new BasicStroke(1.0F));
      g.setColor(Color.cyan);
      g2d.draw(new Double(28.0D + 25.0D * this.xfact + (1.0D - factor) * 20.0D * this.xfact, 20.0D * this.yfact, 9.0D, 10.0D * this.yfact));
      g.drawString(this.Lent2[AppletFabry.lang], (int)(28.0D + 25.0D * this.xfact + (1.0D - factor) * 20.0D * this.xfact), (int)(35.0D * this.yfact));
      g.setColor(Color.white);
      g2d.setStroke(MyStroke);
      factor = this.fabry1.sizeScreen / this.fabry1.sizeScreenmax;
      g.drawLine((int)(d.getWidth() - 5.0D), (int)((25.0D - 5.0D * factor) * this.yfact), (int)(d.getWidth() - 5.0D), (int)((25.0D + 5.0D * factor) * this.yfact));
      double factor1 = this.fabry1.sizeSource / this.fabry1.sizeSourcemax;
      double factor2 = (this.fabry1.fSource - this.fabry1.fSourcemin) / (this.fabry1.fSourcemax - this.fabry1.fSourcemin);
      double pendent = 5.0D * factor1 * this.yfact / (10.0D * this.xfact * factor2 + 15.0D);
      g.setColor(auxColor);
      g2d.setStroke(new BasicStroke(1.0F));
      g.drawLine(5, (int)((25.0D + 5.0D * factor1) * this.yfact), (int)(18.0D + factor2 * 10.0D * this.xfact), (int)(25.0D * this.yfact));
      g.drawLine(5, (int)((25.0D + 5.0D * factor1) * this.yfact), (int)(18.0D + factor2 * 10.0D * this.xfact), (int)(20.0D * this.yfact));
      g.drawLine(5, (int)((25.0D + 5.0D * factor1) * this.yfact), (int)(18.0D + factor2 * 10.0D * this.xfact), (int)(30.0D * this.yfact));
      int x0 = (int)(18.0D + factor2 * 10.0D * this.xfact);
      int x1 = (int)(20.0D + 12.5D * this.xfact) - 5;
      int y0 = (int)(25.0D * this.yfact);
      int y1 = (int)((double)y0 - (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      y0 = (int)(20.0D * this.yfact);
      y1 = (int)((double)y0 - (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      y0 = (int)(30.0D * this.yfact);
      y1 = (int)((double)y0 - (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      factor = this.fabry1.thick / this.fabry1.thick_max;
      x0 = (int)(24.0D + (12.5D + 10.0D * factor) * this.xfact);
      factor = (this.fabry1.fScreen - this.fabry1.fScreenmin) / (this.fabry1.fScreenmax - this.fabry1.fScreenmin);
      x1 = (int)(33.0D + 25.0D * this.xfact + (1.0D - factor) * 20.0D * this.xfact);
      y1 = (int)(25.0D * this.yfact);
      y0 = (int)((double)y1 + (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      y1 = (int)(20.0D * this.yfact);
      y0 = (int)((double)y1 + (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      y1 = (int)(30.0D * this.yfact);
      y0 = (int)((double)y1 + (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      x0 = x1;
      x1 = (int)(d.getWidth() - 7.0D);
      y0 = (int)(25.0D * this.yfact);
      y1 = (int)((double)y0 - (double)(x1 - x0) * pendent);
      g.drawLine(x0, y0, x1, y1);
      y0 = (int)(30.0D * this.yfact);
      g.drawLine(x0, y0, x1, y1);
      y0 = (int)(20.0D * this.yfact);
      g.drawLine(x0, y0, x1, y1);
      float[] dash = new float[]{5.0F, 5.0F};
      MyStroke = new BasicStroke(1.0F, 1, 1, 10.0F, dash, 0.0F);
      g.setColor(Color.cyan);
      g2d.setStroke(MyStroke);
      g2d.drawLine(5, (int)(25.0D * this.yfact), (int)(d.getWidth() - 5.0D), (int)(25.0D * this.yfact));
   }

   private void jbInit() throws Exception {
      this.setBackground(Color.darkGray);
      this.setEnabled(true);
      this.setMaximumSize(new Dimension(375, 260));
      this.setMinimumSize(new Dimension(375, 260));
      this.setPreferredSize(new Dimension(375, 260));
      this.setToolTipText("FabryCalc");
   }
}
