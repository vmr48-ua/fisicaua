package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public class YAxis extends XYAxis {
   AffineTransform rot90;

   public YAxis() {
      this("Y Axis");
   }

   public YAxis(String var1) {
      this.rot90 = AffineTransform.getRotateInstance(-1.5707963267948966D);
      this.setTitle(var1);
      super.axisLabel.setTheta(1.5707963267948966D);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      int var3 = var1.xToPix(super.location);
      if (var3 < 1) {
         super.location = var1.getXMin();
      }

      if (var3 > var1.getWidth() - 1) {
         super.location = var1.getXMax();
      }

      Graphics2D var4 = (Graphics2D)var2;
      Shape var5 = var4.getClip();
      var4.clipRect(0, 0, var1.getWidth(), var1.getHeight());
      switch(super.locationType) {
      case 0:
      case 2:
         this.drawInsideDisplay(var1, var2);
         break;
      case 1:
         this.drawInsideGutter(var1, var2);
         break;
      default:
         this.drawInsideDisplay(var1, var2);
      }

      var4.setClip(var5);
   }

   private void drawInsideDisplay(DrawingPanel var1, Graphics var2) {
      int var3 = var1.getBottomGutter();
      int var4 = var1.getRightGutter();
      int var5 = var1.getLeftGutter();
      int var6 = var1.getTopGutter();
      FontMetrics var7 = var2.getFontMetrics();
      boolean var8 = false;
      var2.setColor(super.color);
      if (super.locationType == 0) {
         super.location = (var1.getXMax() + var1.getXMin()) / 2.0D;
      }

      int var9 = var1.xToPix(super.location);
      int var10 = var1.getHeight() - var3;
      int var11 = var1.getHeight() - var3 - var6;
      this.calculateLabels(var1.getYMin(), var1.getYMax(), 1 + var11 / 35);
      String[] var12 = super.label_string;
      double[] var13 = super.label_value;
      if (var12.length == var13.length) {
         int var14 = 0;

         for(int var15 = var13.length; var14 < var15; ++var14) {
            int var16;
            int var17;
            if (super.axisType == 0) {
               var16 = var1.yToPix(var13[var14] * super.decade_multiplier);
               if (super.showMajorGrid) {
                  var2.setColor(super.majorGridColor);
                  var2.drawLine(var5, var16, var1.getWidth() - var4 - 2, var16);
                  var2.setColor(super.color);
               }

               var2.drawLine(var9 - 5, var16, var9 + 5, var16);
               var17 = var7.stringWidth(var12[var14]);
               var2.drawString(var12[var14], var9 - var17 - 7, var16 + 5);
            } else {
               var16 = var1.yToPix(Math.pow(10.0D, var13[var14] * super.decade_multiplier));
               if (super.showMajorGrid) {
                  var2.setColor(super.majorGridColor);
                  var2.drawLine(var5, var16, var1.getWidth() - var4 - 2, var16);
                  var2.setColor(super.color);
               }

               var2.drawLine(var9 - 5, var16, var9 + 5, var16);
               var17 = var7.stringWidth(super.logBase);
               this.drawMultiplier(var9 - var17 - 7, var16 + 5, (int)var13[var14], (Graphics2D)var2);
            }
         }

         var2.drawLine(var9, var10, var9, var10 - var11);
         Graphics2D var18 = (Graphics2D)var2;
         if (super.axisType == 0 && super.label_exponent != 0) {
            var18.setColor(Color.red);
            var18.drawString("x10", 5, 18);
            var18.setFont(var18.getFont().deriveFont(0, 9.0F));
            var18.drawString("" + super.label_exponent, 25, 12);
         }

         var18.setColor(Color.black);
         if (super.axisLabel != null) {
            super.axisLabel.setY((var1.getYMax() + var1.getYMin()) / 2.0D);
            super.axisLabel.setX(var1.pixToX(Math.max(var5 / 2 - 10, 18)));
            super.axisLabel.draw(var1, var18);
         }

      }
   }

   private void drawInsideGutter(DrawingPanel var1, Graphics var2) {
      int var3 = var1.getBottomGutter();
      int var4 = var1.getRightGutter();
      int var5 = var1.getLeftGutter();
      int var6 = var1.getTopGutter();
      FontMetrics var7 = var2.getFontMetrics();
      boolean var8 = false;
      var2.setColor(super.color);
      int var9 = var5;
      int var10 = var1.getHeight() - var3 - 1;
      int var11 = var1.getHeight() - var3 - var6;
      var2.drawLine(var5, var10, var5, var10 - var11);
      this.calculateLabels(var1.getYMin(), var1.getYMax(), 1 + var11 / 35);
      String[] var12 = super.label_string;
      double[] var13 = super.label_value;
      if (var12.length == var13.length) {
         int var14 = 0;

         for(int var15 = var13.length; var14 < var15; ++var14) {
            int var16;
            int var17;
            if (super.axisType == 0) {
               var16 = var1.yToPix(var13[var14] * super.decade_multiplier);
               if (super.showMajorGrid) {
                  var2.setColor(super.majorGridColor);
                  var2.drawLine(var9, var16, var1.getWidth() - var4 - 2, var16);
                  var2.setColor(super.color);
               }

               var2.drawLine(var9 - 5, var16, var9, var16);
               var17 = var7.stringWidth(var12[var14]);
               var2.drawString(var12[var14], var9 - var17 - 7, var16 + 5);
            } else {
               var16 = var1.yToPix(Math.pow(10.0D, var13[var14] * super.decade_multiplier));
               if (super.showMajorGrid) {
                  var2.setColor(super.majorGridColor);
                  var2.drawLine(var9, var16, var1.getWidth() - var4 - 2, var16);
                  var2.setColor(super.color);
               }

               var2.drawLine(var9 - 5, var16, var9, var16);
               var17 = var7.stringWidth(super.logBase);
               this.drawMultiplier(var9 - var17 - 14, var16 + 5, (int)var13[var14], (Graphics2D)var2);
            }
         }

         Graphics2D var18 = (Graphics2D)var2;
         if (super.axisType == 0 && super.label_exponent != 0) {
            var18.setColor(Color.red);
            var18.drawString("x10", 5, 18);
            var18.setFont(var18.getFont().deriveFont(0, 9.0F));
            var18.drawString("" + super.label_exponent, 25, 12);
         }

         var18.setColor(Color.black);
         if (super.axisLabel != null) {
            super.axisLabel.setY((var1.getYMax() + var1.getYMin()) / 2.0D);
            super.axisLabel.setX(var1.pixToX(Math.max(var5 / 2 - 10, 18)));
            super.axisLabel.draw(var1, var18);
         }

      }
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      if (!super.enabled) {
         return null;
      } else {
         return Math.abs(var1.xToPix(super.location) - var2) < 2 ? this : null;
      }
   }

   public void setXY(double var1, double var3) {
      super.location = var1;
   }

   public void setX(double var1) {
      super.location = var1;
   }
}
