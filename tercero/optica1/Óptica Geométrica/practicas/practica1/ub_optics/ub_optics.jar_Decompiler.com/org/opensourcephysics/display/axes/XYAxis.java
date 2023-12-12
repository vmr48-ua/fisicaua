package org.opensourcephysics.display.axes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import org.opensourcephysics.display.DrawableTextLine;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;

public abstract class XYAxis implements Interactive {
   public static final int DRAW_IN_DISPLAY = 0;
   public static final int DRAW_IN_GUTTER = 1;
   public static final int DRAW_AT_LOCATION = 2;
   public static final int LINEAR = 0;
   protected double x = 0.0D;
   protected double y = 0.0D;
   boolean enabled = false;
   public static final int LOG10 = 1;
   int locationType = 0;
   int axisType = 0;
   String logBase = "10";
   DecimalFormat labelFormat = new DecimalFormat("0.0");
   DecimalFormat integerFormat = new DecimalFormat("000");
   Color color;
   double label_step;
   double label_start;
   DrawableTextLine axisLabel;
   Font labelFont;
   int label_exponent;
   String[] label_string;
   double[] label_value;
   double decade_multiplier;
   int label_count;
   double location;
   Font titleFont;
   boolean showMajorGrid;
   Color majorGridColor;

   public XYAxis() {
      this.color = Color.black;
      this.label_step = -14.0D;
      this.label_start = 2.0D;
      this.axisLabel = new DrawableTextLine("x", 0.0D, 0.0D);
      this.labelFont = new Font("Dialog", 0, 12);
      this.label_exponent = 0;
      this.label_string = new String[0];
      this.label_value = new double[0];
      this.decade_multiplier = 1.0D;
      this.label_count = 0;
      this.location = 0.0D;
      this.titleFont = new Font("Dialog", 0, 12);
      this.showMajorGrid = false;
      this.majorGridColor = new Color(0, 0, 0, 32);
      this.axisLabel.setJustification(0);
      this.axisLabel.setFont(this.labelFont);
   }

   public abstract void draw(DrawingPanel var1, Graphics var2);

   public void setLabelFormat(DecimalFormat var1) {
      if (var1 != null) {
         this.labelFormat = var1;
      }

   }

   public void setLabelFormat(String var1) {
      this.labelFormat = new DecimalFormat(var1);
   }

   public void setLocationType(int var1) {
      this.locationType = var1;
   }

   public void setLocation(double var1) {
      this.location = var1;
   }

   public void setAxisType(int var1) {
      this.axisType = var1;
   }

   public void setTitle(String var1, String var2) {
      this.axisLabel.setText(var1);
      if (var2 != null && !var2.equals("")) {
         this.axisLabel.setFont(Font.decode(var2));
      }
   }

   public void setTitle(String var1) {
      this.axisLabel.setText(var1);
   }

   public void setTitleFont(String var1) {
      if (var1 != null && !var1.equals("")) {
         this.titleFont = Font.decode(var1);
      }

   }

   public void setShowMajorGrid(boolean var1) {
      this.showMajorGrid = var1;
   }

   protected void drawMultiplier(int var1, int var2, int var3, Graphics2D var4) {
      Font var5 = var4.getFont();
      var4.drawString("10", var1, var2);
      var4.setFont(var4.getFont().deriveFont(0, 9.0F));
      var4.drawString("" + var3, var1 + 16, var2 - 6);
      var4.setFont(var5);
   }

   public void calculateLabels(double var1, double var3, int var5) {
      var5 = Math.min(19, var5);
      double var6;
      double var8;
      if (var3 < var1) {
         var6 = var3;
         var8 = var1;
      } else {
         var8 = var3;
         var6 = var1;
      }

      switch(this.axisType) {
      case 0:
         this.calculateLinearLabels(var6, var8, var5);
         break;
      case 1:
         this.calculateLogLabels(var6, var8, var5);
         break;
      default:
         this.calculateLinearLabels(var6, var8, var5);
      }

   }

   private void calculateLogLabels(double var1, double var3, int var5) {
      this.label_exponent = 0;
      this.decade_multiplier = 1.0D;
      byte var6 = 1;
      int var7 = (int)Math.ceil(var1);
      if ((double)var7 - var1 > 0.998D) {
         --var7;
      }

      int var8 = var7;
      this.label_count = 1;

      do {
         var8 += var6;
         ++this.label_count;
      } while((double)var8 <= var3 - (double)var6);

      this.label_string = new String[this.label_count];
      this.label_value = new double[this.label_count];

      for(int var9 = 0; var9 < this.label_count; ++var9) {
         var8 = var7 + var9 * var6;
         this.label_string[var9] = this.integerFormat.format((long)var8);
         this.label_value[var9] = (double)var8;
      }

   }

   private void calculateLinearLabels(double var1, double var3, int var5) {
      if (Math.abs(var1) == 0.0D && Math.abs(var3) == 0.0D) {
         var3 = var1 + 1.0E-6D;
      }

      if (Math.abs(var1) > Math.abs(var3)) {
         this.label_exponent = (int)Math.floor(log10(Math.abs(var1)) / 2.0D) * 2;
      } else {
         this.label_exponent = (int)Math.floor(log10(Math.abs(var3)) / 2.0D) * 2;
      }

      if (var3 - var1 > (double)(10 * var5) * Double.MIN_VALUE) {
         this.label_step = this.RoundUp((var3 - var1) / (double)var5);
      } else {
         this.label_step = 1.0D;
      }

      for(this.label_start = Math.floor(var1 / this.label_step) * this.label_step; this.label_step > 0.0D && this.label_start < var1; this.label_start += this.label_step) {
      }

      double var6 = this.label_start;

      for(this.label_count = 1; var6 <= var3 - this.label_step; ++this.label_count) {
         var6 += this.label_step;
      }

      this.label_string = new String[this.label_count];
      this.label_value = new double[this.label_count];

      int var9;
      for(int var8 = 0; var8 < this.label_count; ++var8) {
         var6 = this.label_start + (double)var8 * this.label_step;
         if (this.label_exponent < 0) {
            for(var9 = this.label_exponent; var9 < 0; ++var9) {
               var6 *= 10.0D;
            }
         } else {
            for(var9 = 0; var9 < this.label_exponent; ++var9) {
               var6 /= 10.0D;
            }
         }

         this.label_string[var8] = this.labelFormat.format(var6);
         this.label_value[var8] = var6;
      }

      this.decade_multiplier = 1.0D;
      if (this.label_exponent < 0) {
         for(var9 = this.label_exponent; var9 < 0; ++var9) {
            this.decade_multiplier /= 10.0D;
         }
      } else {
         for(var9 = 0; var9 < this.label_exponent; ++var9) {
            this.decade_multiplier *= 10.0D;
         }
      }

   }

   private double RoundUp(double var1) {
      int var3 = (int)Math.floor(log10(var1));
      int var4;
      if (var3 < 0) {
         for(var4 = var3; var4 < 0; ++var4) {
            var1 *= 10.0D;
         }
      } else {
         for(var4 = 0; var4 < var3; ++var4) {
            var1 /= 10.0D;
         }
      }

      if (var1 > 5.0D) {
         var1 = 10.0D;
      } else if (var1 > 2.0D) {
         var1 = 5.0D;
      } else if (var1 > 1.0D) {
         var1 = 2.0D;
      } else {
         var1 = 1.0D;
      }

      if (var3 < 0) {
         for(var4 = var3; var4 < 0; ++var4) {
            var1 /= 10.0D;
         }
      } else {
         for(var4 = 0; var4 < var3; ++var4) {
            var1 *= 10.0D;
         }
      }

      return var1;
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
      return null;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setXY(double var1, double var3) {
   }

   public void setX(double var1) {
   }

   public void setY(double var1) {
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public static double log10(double var0) throws ArithmeticException {
      if (var0 <= 0.0D) {
         throw new ArithmeticException("range exception");
      } else {
         return Math.log(var0) / 2.302585092994046D;
      }
   }
}
