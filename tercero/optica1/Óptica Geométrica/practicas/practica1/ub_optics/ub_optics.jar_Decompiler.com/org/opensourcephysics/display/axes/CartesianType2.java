package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import org.opensourcephysics.display.DrawableTextLine;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;

public class CartesianType2 implements CartesianAxes {
   XAxis xaxis;
   YAxis yaxis;
   DrawableTextLine titleLine = new DrawableTextLine("", 0.0D, 0.0D);
   Font titleFont = new Font("Dialog", 1, 14);
   boolean visible = true;
   Color interiorColor;
   boolean xlog;
   boolean ylog;

   public CartesianType2(PlottingPanel var1) {
      this.interiorColor = Color.white;
      this.xlog = false;
      this.ylog = false;
      this.titleLine.setJustification(0);
      this.titleLine.setFont(this.titleFont);
      this.xaxis = new XAxis();
      this.yaxis = new YAxis();
      this.xaxis.setLocationType(1);
      this.yaxis.setLocationType(1);
      var1.setGutters(50, 25, 25, 50);
      if (var1 != null) {
         var1.setCoordinateStringBuilder(CoordinateStringBuilder.createCartesian());
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

   public String getXLabel() {
      return this.xaxis.axisLabel.getText();
   }

   public String getYLabel() {
      return this.yaxis.axisLabel.getText();
   }

   public String getTitle() {
      return this.titleLine.getText();
   }

   public void setTitle(String var1, String var2) {
      this.titleLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.titleLine.setFont(Font.decode(var2));
      }
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
            int var4 = var1.getTopGutter() + var1.getBottomGutter();
            var2.fillRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var3, var1.getHeight() - var4);
            var2.setColor(Color.lightGray);
            var2.drawRect(var1.getLeftGutter(), var1.getTopGutter(), var1.getWidth() - var3 - 1, var1.getHeight() - var4 - 1);
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

   public void setX(double var1) {
   }

   public void setY(double var1) {
   }

   public double getX() {
      return 0.0D;
   }

   public double getY() {
      return 0.0D;
   }
}
