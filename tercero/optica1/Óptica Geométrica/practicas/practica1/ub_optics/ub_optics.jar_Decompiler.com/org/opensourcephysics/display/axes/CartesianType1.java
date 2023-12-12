package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import org.opensourcephysics.display.Dimensioned;
import org.opensourcephysics.display.DrawableTextLine;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.PlottingPanel;
import org.opensourcephysics.tools.FontSizer;

public class CartesianType1 implements CartesianAxes, Dimensioned {
   double yMax;
   double yMin;
   double xMax;
   double xMin;
   boolean xlog = false;
   boolean ylog = false;
   static final double LOG10SCALE = 1.0D / Math.log(10.0D);
   Color background;
   Color foreground;
   double ytickMax;
   double ytickMin;
   double xtickMax;
   double xtickMin;
   int yExponent;
   int xExponent;
   Font labelFont;
   Font superscriptFont;
   Font titleFont;
   FontMetrics labelFontMetrics;
   FontMetrics superscriptFontMetrics;
   FontMetrics titleFontMetrics;
   int gridCurJuke;
   double gridBase;
   protected DrawableTextLine xLine;
   protected DrawableTextLine yLine;
   protected DrawableTextLine titleLine;
   ArrayList xticks;
   ArrayList xticklabels;
   ArrayList yticks;
   ArrayList yticklabels;
   boolean visible;
   NumberFormat numberFormat;
   NumberFormat scientificFormat;
   boolean drawMajorXGrid;
   boolean drawMinorXGrid;
   boolean drawMajorYGrid;
   boolean drawMinorYGrid;
   int topGutter;
   int bottomGutter;
   int leftGutter;
   int rightGutter;
   int defaultTopGutter;
   int defaultBottomGutter;
   int defaultLeftGutter;
   int defaultRightGutter;
   int tickLength;
   private boolean adjustGutters;

   public CartesianType1(PlottingPanel var1) {
      this.background = Color.white;
      this.foreground = Color.black;
      this.labelFont = new Font("Dialog", 0, 12);
      this.superscriptFont = new Font("Dialog", 0, 9);
      this.titleFont = new Font("Dialog", 1, 14);
      this.labelFontMetrics = null;
      this.superscriptFontMetrics = null;
      this.titleFontMetrics = null;
      this.gridCurJuke = 0;
      this.xLine = new DrawableTextLine("x", 0.0D, 0.0D);
      this.yLine = new DrawableTextLine("y", 0.0D, 0.0D);
      this.titleLine = new DrawableTextLine("", 0.0D, 0.0D);
      this.xticks = null;
      this.xticklabels = null;
      this.yticks = null;
      this.yticklabels = null;
      this.visible = true;
      this.numberFormat = NumberFormat.getInstance();
      this.scientificFormat = new DecimalFormat("0.0E0");
      this.drawMajorXGrid = true;
      this.drawMinorXGrid = false;
      this.drawMajorYGrid = true;
      this.drawMinorYGrid = false;
      this.topGutter = 25;
      this.bottomGutter = 45;
      this.leftGutter = 45;
      this.rightGutter = 25;
      this.defaultTopGutter = this.topGutter;
      this.defaultBottomGutter = this.bottomGutter;
      this.defaultLeftGutter = this.leftGutter;
      this.defaultRightGutter = this.rightGutter;
      this.tickLength = 5;
      this.adjustGutters = true;
      this.xLine.setJustification(0);
      this.xLine.setFont(this.labelFont);
      this.xLine.setPixelXY(true);
      this.yLine.setJustification(0);
      this.yLine.setFont(this.labelFont);
      this.yLine.setTheta(1.5707963267948966D);
      this.yLine.setPixelXY(true);
      this.titleLine.setJustification(0);
      this.titleLine.setFont(this.titleFont);
      this.titleLine.setPixelXY(true);
      if (var1 != null) {
         var1.setGutters(this.leftGutter, this.topGutter, this.rightGutter, this.bottomGutter);
         this.measureFonts(var1);
         var1.setAxes(this);
         var1.setCoordinateStringBuilder(CoordinateStringBuilder.createCartesian());
      }
   }

   private int xToPix(double var1, DrawingPanel var3) {
      double[] var4 = var3.getPixelMatrix();
      double var5 = var4[0] * var1 + var4[4];
      if (var5 > 2.147483647E9D) {
         return Integer.MAX_VALUE;
      } else {
         return var5 < -2.147483648E9D ? Integer.MIN_VALUE : (int)Math.floor((double)((float)var5));
      }
   }

   private int yToPix(double var1, DrawingPanel var3) {
      double[] var4 = var3.getPixelMatrix();
      double var5 = var4[3] * var1 + var4[5];
      if (var5 > 2.147483647E9D) {
         return Integer.MAX_VALUE;
      } else {
         return var5 < -2.147483648E9D ? Integer.MIN_VALUE : (int)Math.floor((double)((float)var5));
      }
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   private int getLeftGutter(DrawingPanel var1) {
      int var2 = 40;
      if (this.ylog) {
         return var2 + 10;
      } else {
         int var3 = var1.getHeight() - this.topGutter - this.bottomGutter;
         int var4 = 2 + var3 / (this.labelFontMetrics.getHeight() + 10);
         double var5 = this.roundUp((this.ytickMax - this.ytickMin) / (double)var4);
         double var7 = var5 * Math.ceil(this.ytickMin / var5);
         int var9 = this.numFracDigits(var5);
         double var10 = Math.abs(var5 / 100.0D);
         if (var5 != 0.0D && !(Math.abs((this.ytickMax - var7) / var5) > 50.0D)) {
            double var12 = var7;

            for(int var14 = 0; var14 <= var4; ++var14) {
               String var15 = this.formatNum(var12, var9, var10);
               var2 = Math.max(this.labelFontMetrics.stringWidth(var15) + 25, var2);
               var12 += var5;
            }

            return Math.min(var2, var1.getWidth());
         } else {
            return var2;
         }
      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible) {
         this.topGutter = var1.getTopGutter();
         this.bottomGutter = var1.getBottomGutter();
         this.leftGutter = var1.getLeftGutter();
         this.rightGutter = var1.getRightGutter();
         this.yMax = var1.getYMax();
         this.yMin = var1.getYMin();
         this.xMax = var1.getXMax();
         this.xMin = var1.getXMin();
         double var3;
         if (this.xMax < this.xMin) {
            var3 = this.xMax;
            this.xMax = this.xMin;
            this.xMin = var3;
         }

         if (this.yMax < this.yMin) {
            var3 = this.yMax;
            this.yMax = this.yMin;
            this.yMin = var3;
         }

         this.setXRange(this.xMin, this.xMax);
         this.setYRange(this.yMin, this.yMax);
         if (this.adjustGutters && var1.isAutoscaleY()) {
            this.leftGutter = this.getLeftGutter(var1);
            var1.setGutters(this.leftGutter, this.topGutter, this.rightGutter, this.bottomGutter);
         }

         this.drawPlot(var1, var2);
      }
   }

   public void addXTick(String var1, double var2) {
      if (this.xticks == null) {
         this.xticks = new ArrayList();
         this.xticklabels = new ArrayList();
      }

      this.xticks.add(new Double(var2));
      this.xticklabels.add(var1);
   }

   public void addYTick(String var1, double var2) {
      if (this.yticks == null) {
         this.yticks = new ArrayList();
         this.yticklabels = new ArrayList();
      }

      this.yticks.add(new Double(var2));
      this.yticklabels.add(var1);
   }

   public void setLabelFont(String var1) {
      if (var1 != null && !var1.equals("")) {
         this.labelFont = Font.decode(var1);
      }
   }

   public void setTitle(String var1, String var2) {
      this.titleLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.titleLine.setFont(Font.decode(var2));
         this.setTitleFont(var2);
      }
   }

   public void setTitleFont(String var1) {
      if (var1 != null && !var1.equals("")) {
         this.titleFont = Font.decode(var1);
         this.titleLine.setFont(this.titleFont);
      }
   }

   public void setXLabel(String var1, String var2) {
      this.xLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.xLine.setFont(Font.decode(var2));
         this.setLabelFont(var2);
      }
   }

   public void setXLog(boolean var1) {
      this.xlog = var1;
   }

   public void setYLabel(String var1, String var2) {
      this.yLine.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.yLine.setFont(Font.decode(var2));
         this.setLabelFont(var2);
      }
   }

   public void setYLog(boolean var1) {
      this.ylog = var1;
   }

   public String getTitle() {
      return this.titleLine.getText();
   }

   public String getXLabel() {
      return this.xLine.getText();
   }

   public boolean isXLog() {
      return this.xlog;
   }

   public String getYLabel() {
      return this.yLine.getText();
   }

   public boolean isYLog() {
      return this.ylog;
   }

   public void resizeFonts(double var1, DrawingPanel var3) {
      this.labelFont = FontSizer.getResizedFont(this.labelFont, var1);
      this.superscriptFont = FontSizer.getResizedFont(this.superscriptFont, var1);
      this.titleFont = FontSizer.getResizedFont(this.titleFont, var1);
      this.xLine.setFont(this.labelFont);
      this.yLine.setFont(this.labelFont);
      this.titleLine.setFont(this.titleFont);
      int var4 = (int)((double)this.defaultLeftGutter * var1);
      int var5 = (int)((double)this.defaultBottomGutter * var1);
      int var6 = (int)((double)this.defaultTopGutter * var1);
      int var7 = (int)((double)this.defaultRightGutter * var1);
      var3.setGutters(var4, var6, var7, var5);
      this.measureFonts(var3);
   }

   protected void drawPlot(DrawingPanel var1, Graphics var2) {
      int var3 = var1.getHeight();
      int var4 = var1.getWidth();
      Shape var5 = var2.getClip();
      Font var6 = var2.getFont();
      var2.clipRect(0, 0, var4, var3);
      var2.setFont(this.labelFont);
      var2.setColor(this.foreground);
      int var7 = var4 - this.rightGutter;
      int var8 = var3 - this.bottomGutter;
      int var9 = this.titleFontMetrics.getHeight();
      int var11 = this.labelFontMetrics.getHeight();
      int var12 = var11 / 2;
      int var13 = var3 - 5;
      int var14 = var4 - 5;
      if (this.xlog) {
         this.xExponent = (int)Math.floor(this.xtickMin);
      }

      if (this.xExponent != 0 && this.xticks == null) {
         String var15 = Integer.toString(this.xExponent);
         var14 -= this.superscriptFontMetrics.stringWidth(var15);
         var2.setFont(this.superscriptFont);
         if (!this.xlog) {
            var2.drawString(var15, var14, var13 - var12);
            var14 -= this.labelFontMetrics.stringWidth("x 10");
            var2.setFont(this.labelFont);
            var2.drawString("x 10", var14, var13);
         }
      }

      int var45 = var3 - this.topGutter - this.bottomGutter;
      int var16 = 2 + var45 / (var11 + 10);
      double var17 = this.roundUp((this.ytickMax - this.ytickMin) / (double)var16);
      double var19 = var17 * Math.ceil(this.ytickMin / var17);
      int var21 = var4 - this.rightGutter - this.leftGutter;
      if (this.background != null) {
         var2.setColor(this.background);
         var2.fillRect(this.leftGutter, this.topGutter, var21, var45);
      }

      int var22 = this.leftGutter + this.tickLength;
      int var23 = var7 - this.tickLength;
      int var24 = this.numFracDigits(var17);
      double var30;
      int var31;
      int var32;
      double var33;
      double var34;
      boolean var36;
      double var52;
      ArrayList var59;
      if (this.yticks == null) {
         ArrayList var25 = null;
         double var26 = var19;
         if (this.ylog) {
            var25 = this.gridInit(var19, var17, true, (ArrayList)null);
            var26 = this.gridStep(var25, var19, var17, this.ylog);
         }

         boolean var28 = this.ylog;
         boolean var29 = true;
         var2.setColor(this.foreground);
         var30 = Math.abs(var17 / 100.0D);
         var32 = var16;

         int var38;
         for(var33 = var26; var33 <= this.ytickMax; var33 = this.gridStep(var25, var33, var17, this.ylog)) {
            --var32;
            if (var32 < 0) {
               break;
            }

            String var35 = null;
            if (this.ylog) {
               var35 = this.formatLogNum(var33, var24);
               if (var35.indexOf(101) != -1) {
                  var28 = false;
               }
            } else {
               var35 = this.formatNum(var33, var24, var30);
            }

            var36 = false;
            int var64;
            if ((this.ylog || this.yExponent <= 0) && this.yExponent >= 0) {
               var64 = this.yToPix(var33, var1);
            } else {
               var64 = this.yToPix(var33 * Math.pow(10.0D, (double)this.yExponent), var1);
            }

            int var37 = var11 / 4;
            if (var29 && !this.ylog) {
               var29 = false;
               var37 = 0;
            }

            var2.drawLine(this.leftGutter, var64, var22, var64);
            var2.drawLine(var21 + this.leftGutter - 1, var64, var23, var64);
            if (this.drawMajorYGrid && var64 >= this.topGutter && var64 <= var8) {
               var2.setColor(Color.lightGray);
               var2.drawLine(var22, var64, var23, var64);
               var2.setColor(this.foreground);
            }

            var38 = this.labelFontMetrics.stringWidth(var35);
            var2.drawString(var35, this.leftGutter - var38 - 4, var64 + var37);
         }

         if (this.ylog || this.drawMinorYGrid) {
            var59 = this.gridInit(var19, var17, false, var25);
            if (var59.size() > 0) {
               var34 = var17 > 1.0D ? 1.0D : var17;

               for(double var65 = this.gridStep(var59, var19, var34, this.ylog); var65 <= this.ytickMax; var65 = this.gridStep(var59, var65, var34, this.ylog)) {
                  var38 = this.yToPix(var65, var1);
                  if (var38 != this.topGutter && var38 != var8) {
                     var2.setColor(Color.lightGray);
                     var2.drawLine(this.leftGutter + 1, var38, var7 - 1, var38);
                     var2.setColor(this.foreground);
                  }
               }
            }

            if (var28) {
               this.yExponent = (int)Math.floor(var26);
            } else {
               this.yExponent = 0;
            }
         }

         if (this.yExponent != 0) {
            var2.drawString("x 10", 2, var9);
            var2.setFont(this.superscriptFont);
            var2.drawString(Integer.toString(this.yExponent), this.labelFontMetrics.stringWidth("x 10") + 2, var9 - var12);
            var2.setFont(this.labelFont);
         }
      } else {
         Iterator var47 = this.yticks.iterator();
         Iterator var49 = this.yticklabels.iterator();

         while(var49.hasNext()) {
            String var27 = (String)var49.next();
            var52 = (Double)((Double)var47.next());
            if (!(var52 > this.yMax) && !(var52 < this.yMin)) {
               int var55 = this.yToPix(var52 * Math.pow(10.0D, (double)this.yExponent), var1);
               var31 = 0;
               if (var52 < (double)(var8 - var11)) {
                  var31 = var12;
               }

               var2.drawLine(this.leftGutter, var55, var22, var55);
               var2.drawLine(var21 + this.leftGutter - 1, var55, var23, var55);
               if (this.drawMajorYGrid && var55 >= this.topGutter && var55 <= var8) {
                  var2.setColor(Color.lightGray);
                  var2.drawLine(var22, var55, var23, var55);
                  var2.setColor(this.foreground);
               }

               var2.drawString(var27, this.leftGutter - this.labelFontMetrics.stringWidth(var27) - 3, var55 + var31);
            }
         }
      }

      int var48 = this.topGutter + this.tickLength;
      int var50 = var8 - this.tickLength;
      int var51 = this.labelFontMetrics.stringWidth("8");
      int var63;
      if (this.xticks == null) {
         int var53 = 10;
         double var54 = 0.0D;
         boolean var46 = false;
         if (this.xlog) {
            var53 = 4 + var21 / (var51 * 6 + 10);
         } else {
            var31 = 0;

            while(var31++ <= 10) {
               var54 = this.roundUp((this.xtickMax - this.xtickMin) / (double)var53);
               var24 = this.numFracDigits(var54);
               var32 = this.numIntDigits(this.xtickMax);
               int var61 = this.numIntDigits(this.xtickMin);
               if (var32 < var61) {
                  var32 = var61;
               }

               int var62 = var51 * (var24 + 2 + var32);
               var63 = var53;
               var53 = 2 + var21 / (var62 + 10);
               if (var53 - var63 <= 1 || var63 - var53 <= 1) {
                  break;
               }
            }
         }

         var54 = this.xlog ? this.roundUp(0.8D * (this.xtickMax - this.xtickMin) / (double)var53) : this.roundUp((this.xtickMax - this.xtickMin) / (double)var53);
         var24 = this.numFracDigits(var54);
         double var58 = var54 * Math.ceil(this.xtickMin / var54);
         var59 = null;
         var34 = var58;
         if (this.xlog) {
            var58 = var54 * Math.floor(this.xtickMin / var54);
            var59 = this.gridInit(var58, var54, true, (ArrayList)null);
            var34 = this.gridRoundUp(var59, var58);
         }

         var36 = this.xlog;
         var2.setColor(this.foreground);
         double var66 = Math.abs(var17 / 100.0D);
         int var39 = var53;

         double var40;
         for(var40 = var34; var40 <= this.xtickMax; var40 = this.gridStep(var59, var40, var54, this.xlog)) {
            --var39;
            if (var39 < 0) {
               break;
            }

            String var42 = null;
            boolean var43 = false;
            if (this.xlog) {
               var42 = this.formatLogNum(var40, var24);
               if (var42.indexOf(101) != -1) {
                  var36 = false;
                  var43 = true;
               }
            } else {
               var42 = this.formatNum(var40, var24, var66);
            }

            if ((this.xlog || this.xExponent <= 1) && this.xExponent >= -1) {
               var22 = this.xToPix(var40, var1);
            } else {
               var22 = this.xToPix(var40 * Math.pow(10.0D, (double)this.xExponent), var1);
            }

            var2.drawLine(var22, this.topGutter, var22, var48);
            var2.drawLine(var22, var45 + this.topGutter - 1, var22, var50);
            if (this.drawMajorXGrid && var22 >= this.leftGutter && var22 <= var7) {
               var2.setColor(Color.lightGray);
               var2.drawLine(var22, var48, var22, var50);
               var2.setColor(this.foreground);
            }

            int var44 = var22 - this.labelFontMetrics.stringWidth(var42) / 2;
            if (var43) {
               var2.drawString(var42, var44 + 7, var8 + 3 + var11);
            } else {
               var2.drawString(var42, var44, var8 + 3 + var11);
            }
         }

         if (this.xlog || this.drawMinorXGrid) {
            var40 = var54 > 1.0D ? 1.0D : var54;
            var34 = var40 * Math.ceil(this.xtickMin / var40);
            ArrayList var67 = this.gridInit(var34, var40, false, var59);
            if (var67.size() > 0) {
               for(double var68 = this.gridStep(var67, var34, var40, this.xlog); var68 <= this.xtickMax; var68 = this.gridStep(var67, var68, var40, this.xlog)) {
                  var22 = this.xToPix(var68, var1);
                  if (var22 != this.leftGutter && var22 != var7) {
                     var2.setColor(Color.lightGray);
                     var2.drawLine(var22, this.topGutter + 1, var22, var8 - 1);
                     var2.setColor(this.foreground);
                  }
               }
            }

            if (var36) {
               this.xExponent = (int)Math.floor(var34);
               var2.setFont(this.superscriptFont);
               var2.drawString(Integer.toString(this.xExponent), var14, var13 - var12);
               var14 -= this.labelFontMetrics.stringWidth("x 10");
               var2.setFont(this.labelFont);
               var2.drawString("x 10", var14, var13);
            } else {
               this.xExponent = 0;
            }
         }
      } else {
         Iterator var57 = this.xticks.iterator();
         Iterator var56 = this.xticklabels.iterator();
         var30 = 0.0D;

         while(var56.hasNext()) {
            String var60 = (String)var56.next();
            var33 = (Double)((Double)var57.next());
            if (!(var33 > this.xMax) && !(var33 < this.xMin)) {
               var22 = this.xToPix(var33 * Math.pow(10.0D, (double)this.xExponent), var1);
               var63 = var22 - this.labelFontMetrics.stringWidth(var60) / 2;
               if ((double)var63 > var30) {
                  var30 = (double)(var22 + this.labelFontMetrics.stringWidth(var60) / 2 + 10);
                  var2.drawString(var60, var63, var8 + 3 + var11);
                  var2.drawLine(var22, this.topGutter, var22, var48);
                  var2.drawLine(var22, var45 + this.topGutter - 1, var22, var50);
                  if (this.drawMajorXGrid && var22 >= this.leftGutter && var22 <= var7) {
                     var2.setColor(Color.lightGray);
                     var2.drawLine(var22, var48, var22, var50);
                     var2.setColor(this.foreground);
                  }
               }
            }
         }
      }

      var2.setColor(this.foreground);
      if (this.titleLine != null) {
         this.titleLine.setX((double)(var1.getLeftGutter() / 2 + (var1.getWidth() - var1.getRightGutter()) / 2));
         if (var1.getTopGutter() > 12) {
            this.titleLine.setY((double)(var1.getTopGutter() / 2 + 5));
         } else {
            this.titleLine.setY(25.0D);
         }

         this.titleLine.draw(var1, var2);
      }

      if (this.xLine != null) {
         var52 = (double)this.leftGutter / 2.0D + (double)(var1.getWidth() - this.rightGutter) / 2.0D;
         this.xLine.setX(var52);
         this.xLine.setY((double)(var1.getHeight() - 8));
         this.xLine.draw(var1, var2);
      }

      if (this.yLine != null) {
         var52 = (double)this.topGutter / 2.0D + (double)((var1.getHeight() - this.bottomGutter) / 2);
         this.yLine.setY(var52);
         this.yLine.setX(15.0D);
         this.yLine.draw(var1, var2);
      }

      var2.setColor(this.foreground);
      var2.drawRect(this.leftGutter, this.topGutter, var21 - 1, var45 - 1);
      var2.setFont(var6);
      var2.setClip(var5);
   }

   private String formatLogNum(double var1, int var3) {
      int var5 = (int)var1;
      String var4;
      if (var5 >= 0 && var5 < 10) {
         var4 = "0" + var5;
      } else if (var5 < 0 && var5 > -10) {
         var4 = "-0" + -var5;
      } else {
         var4 = Integer.toString(var5);
      }

      if (var1 >= 0.0D) {
         if (var1 - (double)((int)var1) < 0.001D) {
            var4 = "1e" + var4;
         } else {
            var4 = this.formatNum(Math.pow(10.0D, var1 - (double)((int)var1)), var3, 1.401298464324817E-45D);
         }
      } else if (-var1 - (double)((int)(-var1)) < 0.001D) {
         var4 = "1e" + var4;
      } else {
         var4 = this.formatNum(Math.pow(10.0D, var1 - (double)((int)var1)) * 10.0D, var3, 1.401298464324817E-45D);
      }

      return var4;
   }

   private String formatNum(double var1, int var3, double var4) {
      NumberFormat var6;
      if (Math.abs(var1) < 0.01D && Math.abs(var1) > var4) {
         var6 = this.scientificFormat;
      } else {
         var6 = this.numberFormat;
         var6.setMinimumFractionDigits(var3);
         var6.setMaximumFractionDigits(var3);
      }

      return var6.format(var1);
   }

   private ArrayList gridInit(double var1, double var3, boolean var5, ArrayList var6) {
      ArrayList var7 = new ArrayList(10);
      double var8 = Math.pow(10.0D, var3);
      byte var10 = 1;
      if (var5) {
         if (var8 <= 3.5D) {
            if (var8 > 2.0D) {
               var10 = 2;
            } else if (var8 > 1.26D) {
               var10 = 5;
            } else if (var8 > 1.125D) {
               var10 = 10;
            } else {
               int var17 = (int)Math.rint(1.0D / var3);
               var10 = 10;
            }
         }
      } else if (var8 > 10.0D) {
         var10 = 1;
      } else if (var8 > 3.0D) {
         var10 = 2;
      } else if (var8 > 2.0D) {
         var10 = 5;
      } else if (var8 > 1.125D) {
         var10 = 10;
      } else {
         var10 = 100;
      }

      int var11 = 0;

      for(int var12 = 0; var12 < var10; ++var12) {
         double var13 = (double)var12 * 1.0D / (double)var10 * 10.0D;
         double var15 = LOG10SCALE * Math.log(var13);
         if (var15 == Double.NEGATIVE_INFINITY) {
            var15 = 0.0D;
         }

         if (var6 != null && var11 < var6.size()) {
            while(var11 < var6.size() && (Double)var6.get(var11) < var15) {
               ++var11;
            }

            if (var11 < var6.size()) {
               if (Math.abs((Double)var6.get(var11) - var15) > 1.0E-5D) {
                  var7.add(new Double(var15));
               }
            } else {
               var7.add(new Double(var15));
            }
         } else {
            var7.add(new Double(var15));
         }
      }

      this.gridCurJuke = 0;
      if (var1 == -0.0D) {
         var1 = 0.0D;
      }

      this.gridBase = Math.floor(var1);
      double var18 = var1 - this.gridBase;

      for(this.gridCurJuke = -1; this.gridCurJuke + 1 < var7.size() && var18 >= (Double)var7.get(this.gridCurJuke + 1); ++this.gridCurJuke) {
      }

      return var7;
   }

   private double gridRoundUp(ArrayList var1, double var2) {
      double var4 = var2 - Math.floor(var2);

      int var6;
      for(var6 = 0; var6 < var1.size() && var4 >= (Double)var1.get(var6); ++var6) {
      }

      return var6 >= var1.size() ? var2 : Math.floor(var2) + (Double)var1.get(var6);
   }

   private double gridStep(ArrayList var1, double var2, double var4, boolean var6) {
      if (var4 == 0.0D) {
         var4 = 1.0D;
      }

      if (var6) {
         if (++this.gridCurJuke >= var1.size()) {
            this.gridCurJuke = 0;
            this.gridBase += Math.ceil(var4);
         }

         return this.gridCurJuke >= var1.size() ? var2 + var4 : this.gridBase + (Double)var1.get(this.gridCurJuke);
      } else if (var2 + var4 != var2) {
         return var2 + var4;
      } else {
         while(var2 + var4 == var2) {
            var4 *= 2.0D;
         }

         return var2 + var4;
      }
   }

   private void measureFonts(JPanel var1) {
      this.labelFontMetrics = var1.getFontMetrics(this.labelFont);
      this.superscriptFontMetrics = var1.getFontMetrics(this.superscriptFont);
      this.titleFontMetrics = var1.getFontMetrics(this.titleFont);
   }

   private int numFracDigits(double var1) {
      int var3;
      for(var3 = 0; var3 <= 15 && var1 != Math.floor(var1); ++var3) {
         var1 *= 10.0D;
      }

      return var3;
   }

   private int numIntDigits(double var1) {
      int var3;
      for(var3 = 0; var3 <= 15 && (double)((int)var1) != 0.0D; ++var3) {
         var1 /= 10.0D;
      }

      return var3;
   }

   private double roundUp(double var1) {
      int var3 = (int)Math.floor(Math.log(var1) * LOG10SCALE);
      var1 *= Math.pow(10.0D, (double)(-var3));
      if (var1 > 5.0D) {
         var1 = 10.0D;
      } else if (var1 > 2.0D) {
         var1 = 5.0D;
      } else if (var1 > 1.0D) {
         var1 = 2.0D;
      } else {
         var1 = 1.0D;
      }

      var1 *= Math.pow(10.0D, (double)var3);
      return var1;
   }

   private void setXRange(double var1, double var3) {
      double var5 = Math.max(Math.abs(this.xMin), Math.abs(this.xMax));
      double var7 = Math.abs(this.xMax - this.xMin);
      if (this.xMin >= 0.0D && this.xMax <= 1000.0D && var7 > 0.1D && !this.xlog) {
         this.xExponent = 0;
      } else {
         this.xExponent = (int)Math.floor(Math.log(var5) * LOG10SCALE);
      }

      if (this.xExponent <= 1 && this.xExponent >= -1) {
         this.xtickMin = this.xMin;
         this.xtickMax = this.xMax;
         this.xExponent = 0;
      } else {
         double var9 = 1.0D / Math.pow(10.0D, (double)this.xExponent);
         this.xtickMin = this.xMin * var9;
         this.xtickMax = this.xMax * var9;
      }

   }

   private void setYRange(double var1, double var3) {
      double var5 = Math.max(Math.abs(this.yMin), Math.abs(this.yMax));
      if (this.yMin >= 0.0D && this.yMax <= 1000.0D && !this.ylog) {
         this.yExponent = 0;
      } else {
         this.yExponent = (int)Math.floor(Math.log(var5) * LOG10SCALE);
      }

      if (this.yExponent <= 1 && this.yExponent >= -1) {
         this.ytickMin = this.yMin;
         this.ytickMax = this.yMax;
         this.yExponent = 0;
      } else {
         double var7 = 1.0D / Math.pow(10.0D, (double)this.yExponent);
         this.ytickMin = this.yMin * var7;
         this.ytickMax = this.yMax * var7;
      }

   }

   public void setInteriorBackground(Color var1) {
      this.background = var1;
   }

   public void setShowMajorXGrid(boolean var1) {
      this.drawMajorXGrid = var1;
      if (!var1) {
         this.drawMinorXGrid = var1;
      }

   }

   public void setShowMinorXGrid(boolean var1) {
      this.drawMinorXGrid = var1;
   }

   public void setShowMajorYGrid(boolean var1) {
      this.drawMajorYGrid = var1;
      if (!var1) {
         this.drawMinorYGrid = var1;
      }

   }

   public void setShowMinorYGrid(boolean var1) {
      this.drawMinorYGrid = var1;
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

   public Dimension getInterior(DrawingPanel var1) {
      if (var1.getDimensionSetter() == null) {
         this.adjustGutters = true;
      } else {
         this.adjustGutters = false;
      }

      return null;
   }
}
