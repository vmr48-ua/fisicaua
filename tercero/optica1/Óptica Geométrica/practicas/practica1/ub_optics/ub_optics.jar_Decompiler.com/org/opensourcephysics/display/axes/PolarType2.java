package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.text.DecimalFormat;
import org.opensourcephysics.display.DrawableTextLine;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;

public class PolarType2 implements PolarAxes {
   protected DecimalFormat labelFormat;
   protected double dr;
   protected double dtheta;
   protected Color gridcolor;
   protected Color interiorColor;
   protected DrawableTextLine titleLine;
   protected Font titleFont;
   protected boolean visible;
   protected boolean autoDeltaR;

   public PolarType2(PlottingPanel var1, String var2, String var3, double var4) {
      this.labelFormat = new DecimalFormat("0.0");
      this.dr = 1.0D;
      this.dtheta = 0.39269908169872414D;
      this.gridcolor = Color.lightGray;
      this.interiorColor = Color.white;
      this.titleLine = new DrawableTextLine("", 0.0D, 0.0D);
      this.titleFont = new Font("Dialog", 1, 14);
      this.visible = true;
      this.autoDeltaR = false;
      if (var1 != null) {
         var1.setGutters(25, 25, 25, 25);
         this.titleLine.setJustification(0);
         this.titleLine.setFont(this.titleFont);
         var1.setAxes(this);
         var1.setCoordinateStringBuilder(CoordinateStringBuilder.createPolar(var2, var3, var4));
      }
   }

   public PolarType2(PlottingPanel var1) {
      this(var1, "r=", " phi=", 0.0D);
   }

   public void autoscaleDeltaR(boolean var1) {
      this.autoDeltaR = var1;
   }

   public double getDeltaR() {
      return this.dr;
   }

   public void setDeltaR(double var1) {
      this.dr = var1;
   }

   public double getDeltaTheta() {
      return this.dtheta;
   }

   public void setDeltaTheta(double var1) {
      this.dtheta = Math.abs(var1);
   }

   public void setLabelFormat(String var1) {
      this.labelFormat = new DecimalFormat(var1);
   }

   public void setXLabel(String var1, String var2) {
   }

   public void setYLabel(String var1, String var2) {
   }

   public void setTitle(String var1, String var2) {
      this.titleLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.titleLine.setFont(Font.decode(var2));
      }
   }

   public String getXLabel() {
      return "";
   }

   public String getYLabel() {
      return "";
   }

   public String getTitle() {
      return this.titleLine.getText();
   }

   public void setXLog(boolean var1) {
   }

   public void setYLog(boolean var1) {
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public void setInteriorBackground(Color var1) {
      this.interiorColor = var1;
   }

   public void setShowMajorXGrid(boolean var1) {
   }

   public void setShowMinorXGrid(boolean var1) {
   }

   public void setShowMajorYGrid(boolean var1) {
   }

   public void setShowMinorYGrid(boolean var1) {
   }

   public void resizeFonts(double var1, DrawingPanel var3) {
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      Graphics2D var3 = (Graphics2D)var2;
      Shape var4 = var3.getClip();
      int var5 = var1.getLeftGutter() + var1.getRightGutter();
      int var6 = var1.getTopGutter() + var1.getLeftGutter();
      if (this.interiorColor != null) {
         var2.setColor(this.interiorColor);
         var2.fillRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var5, var1.getHeight() - var6);
         var2.setColor(this.gridcolor);
         var2.drawRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var5, var1.getHeight() - var6);
      }

      var3.clipRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var5, var1.getHeight() - var6);
      double var7 = Math.abs(var1.getXMax()) + Math.abs(var1.getYMax());
      var7 = Math.max(var7, Math.abs(var1.getXMax()) + Math.abs(var1.getYMin()));
      var7 = Math.max(var7, Math.abs(var1.getXMin()) + Math.abs(var1.getYMax()));
      var7 = Math.max(var7, Math.abs(var1.getXMin()) + Math.abs(var1.getYMin()));
      this.drawRings(var7, var1, var2);
      this.drawSpokes(var7, var1, var2);
      var3.setClip(var4);
      this.titleLine.setX((var1.getXMax() + var1.getXMin()) / 2.0D);
      if (var1.getTopGutter() > 20) {
         this.titleLine.setY(var1.getYMax() + 5.0D / var1.getYPixPerUnit());
      } else {
         this.titleLine.setY(var1.getYMax() - 25.0D / var1.getYPixPerUnit());
      }

      this.titleLine.draw(var1, var2);
   }

   public void drawSpokes(double var1, DrawingPanel var3, Graphics var4) {
      var4.setColor(this.gridcolor);

      for(double var5 = 0.0D; var5 < 3.141592653589793D; var5 += this.dtheta) {
         int var7 = var3.xToPix(var1 * Math.cos(var5));
         int var8 = var3.yToPix(var1 * Math.sin(var5));
         int var9 = var3.xToPix(-var1 * Math.cos(var5));
         int var10 = var3.yToPix(-var1 * Math.sin(var5));
         var4.drawLine(var7, var8, var9, var10);
      }

   }

   public void drawRings(double var1, DrawingPanel var3, Graphics var4) {
      double var5 = this.dr;
      if (this.autoDeltaR) {
         double var7 = Math.max(var3.getXMax() - var3.getXMin(), var3.getYMax() - var3.getYMin());
         var5 = 0.1D;
         double var9 = 0.1D;

         while(var7 / var5 > 15.0D) {
            var5 *= 2.0D;
            if (var5 / var9 > 3.5D && var5 / var9 < 4.5D) {
               var5 = 5.0D * var9;
               var9 *= 10.0D;
            }
         }
      }

      int var13 = var3.xToPix(0.0D);
      int var8 = var3.yToPix(0.0D);
      var4.setColor(this.gridcolor);
      int var10000 = (int)(var3.getXPixPerUnit() * var1);
      var10000 = (int)(var3.getYPixPerUnit() * var1);

      for(double var11 = 0.0D; var11 <= var1; var11 += var5) {
         int var14 = var3.xToPix(var11) - var13;
         int var10 = var8 - var3.yToPix(var11);
         var4.drawOval(var13 - var14, var8 - var10, 2 * var14, 2 * var10);
      }

   }
}
