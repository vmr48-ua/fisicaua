package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import org.opensourcephysics.display.DrawableTextLine;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.display.PlottingPanel;

public class CartesianType3 implements CartesianAxes, Interactive {
   XAxis xaxis;
   YAxis yaxis;
   DrawableTextLine titleLine = new DrawableTextLine("", 0.0D, 0.0D);
   Font titleFont = new Font("Dialog", 1, 14);
   boolean visible = true;
   Color interiorColor;
   boolean enabled;
   boolean xlog;
   boolean ylog;

   public CartesianType3(PlottingPanel var1) {
      this.interiorColor = Color.white;
      this.enabled = true;
      this.xlog = false;
      this.ylog = false;
      this.titleLine.setJustification(0);
      this.titleLine.setFont(this.titleFont);
      this.xaxis = new XAxis();
      this.yaxis = new YAxis();
      this.xaxis.setEnabled(true);
      this.xaxis.setLocationType(2);
      this.yaxis.setEnabled(true);
      this.yaxis.setLocationType(2);
      if (var1 != null) {
         var1.setCoordinateStringBuilder(CoordinateStringBuilder.createCartesian());
         var1.setGutters(30, 30, 30, 30);
         var1.setAxes(this);
      }
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public void setXLabel(String var1, String var2) {
      this.xaxis.setTitle(var1, var2);
   }

   public void setYLabel(String var1, String var2) {
      this.yaxis.setTitle(var1, var2);
   }

   public void setTitle(String var1, String var2) {
      this.titleLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.titleLine.setFont(Font.decode(var2));
      }
   }

   public String getXLabel() {
      return this.xaxis.axisLabel.getText();
   }

   public String getYLabel() {
      return this.yaxis.axisLabel.getText();
   }

   public String getTitle() {
      return this.titleLine.getText();
   }

   public void setXLog(boolean var1) {
      this.xlog = var1;
      if (var1) {
         this.xaxis.setAxisType(1);
      } else {
         this.xaxis.setAxisType(0);
      }

   }

   public void setYLog(boolean var1) {
      this.ylog = var1;
      if (var1) {
         this.yaxis.setAxisType(1);
      } else {
         this.yaxis.setAxisType(0);
      }

   }

   public boolean isXLog() {
      return this.xlog;
   }

   public boolean isYLog() {
      return this.ylog;
   }

   public void resizeFonts(double var1, DrawingPanel var3) {
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible) {
         if (this.interiorColor != null) {
            var2.setColor(this.interiorColor);
            int var3 = var1.getLeftGutter() + var1.getRightGutter();
            int var4 = var1.getTopGutter() + var1.getLeftGutter();
            var2.fillRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var3, var1.getHeight() - var4);
            var2.setColor(Color.lightGray);
            var2.drawRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var3, var1.getHeight() - var4);
         }

         this.xaxis.draw(var1, var2);
         this.yaxis.draw(var1, var2);
         this.titleLine.setX((var1.getXMax() + var1.getXMin()) / 2.0D);
         if (var1.getTopGutter() > 20) {
            this.titleLine.setY(var1.getYMax() + 5.0D / var1.getYPixPerUnit());
         } else {
            this.titleLine.setY(var1.getYMax() - 25.0D / var1.getYPixPerUnit());
         }

         this.titleLine.draw(var1, var2);
      }
   }

   public void setInteriorBackground(Color var1) {
      this.interiorColor = var1;
   }

   public void setShowMajorXGrid(boolean var1) {
      this.xaxis.setShowMajorGrid(var1);
      if (!var1) {
         this.setShowMinorXGrid(var1);
      }

   }

   public void setShowMinorXGrid(boolean var1) {
   }

   public void setShowMajorYGrid(boolean var1) {
      this.yaxis.setShowMajorGrid(var1);
      if (!var1) {
         this.setShowMinorYGrid(var1);
      }

   }

   public void setShowMinorYGrid(boolean var1) {
   }

   public void centerAxes(DrawingPanel var1) {
      this.xaxis.setLocation((var1.getYMax() + var1.getYMin()) / 2.0D);
      this.yaxis.setLocation((var1.getXMax() + var1.getXMin()) / 2.0D);
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setXY(double var1, double var3) {
      this.xaxis.setLocation(var3);
      this.yaxis.setLocation(var1);
   }

   public void setX(double var1) {
      this.yaxis.setLocation(var1);
   }

   public void setY(double var1) {
      this.xaxis.setLocation(var1);
   }

   public double getX() {
      return 0.0D;
   }

   public double getY() {
      return 0.0D;
   }

   public double getXMin() {
      return 0.0D;
   }

   public double getXMax() {
      return 0.0D;
   }

   public double getYMin() {
      return 0.0D;
   }

   public double getYMax() {
      return 0.0D;
   }

   public boolean isMeasured() {
      return false;
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      if (!this.visible) {
         return null;
      } else if (this.xaxis.findInteractive(var1, var2, var3) != null) {
         return this.xaxis;
      } else {
         return this.yaxis.findInteractive(var1, var2, var3) != null ? this.yaxis : null;
      }
   }
}
