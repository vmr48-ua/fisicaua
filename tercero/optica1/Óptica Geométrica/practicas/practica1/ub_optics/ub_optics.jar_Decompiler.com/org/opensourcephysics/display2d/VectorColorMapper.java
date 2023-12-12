package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.display.axes.XAxis;

public class VectorColorMapper {
   public static final int SPECTRUM = 0;
   public static final int RED = 1;
   public static final int BLUE = 2;
   public static final int GREEN = 3;
   public static final int BLACK = 4;
   private Color background;
   private Color[] colors;
   private double ceil;
   private double floor;
   private int numColors;
   private int paletteType;
   private JFrame legendFrame;

   public VectorColorMapper(int var1, double var2) {
      this.background = Color.WHITE;
      this.ceil = var2;
      this.numColors = var1;
      this.floor = this.numColors < 2 ? 0.0D : this.ceil / (double)(this.numColors - 1);
      this.createVectorfieldPalette();
   }

   public double getFloor() {
      return this.floor;
   }

   public double getCeiling() {
      return this.ceil;
   }

   protected void setPaletteType(int var1) {
      this.paletteType = var1;
   }

   protected void checkPallet(Color var1) {
      if (this.background != var1) {
         this.background = var1;
         this.createVectorfieldPalette();
      }
   }

   public void setScale(double var1) {
      this.ceil = var1;
   }

   public Color doubleToColor(double var1) {
      if (var1 <= this.floor) {
         return this.background;
      } else {
         double var3 = 1.0D - Math.abs(var1 / this.ceil);
         int var5 = 255 - (int)(var3 * (double)this.background.getRed());
         int var6 = 255 - (int)(var3 * (double)this.background.getGreen());
         int var7 = 255 - (int)(var3 * (double)this.background.getBlue());
         switch(this.paletteType) {
         case 1:
            if (var1 >= this.ceil) {
               return Color.red;
            }

            return new Color(var5, 0, 0);
         case 2:
            if (var1 >= this.ceil) {
               return Color.blue;
            }

            return new Color(0, 0, var7);
         case 3:
            if (var1 >= this.ceil) {
               return Color.green;
            }

            return new Color(0, var6, 0);
         case 4:
            if (var1 >= this.ceil) {
               return Color.black;
            }

            return new Color(0, 0, 0);
         default:
            if (var1 >= this.ceil) {
               return new Color((int)(255.0D * this.ceil / var1), 0, 0);
            } else {
               int var8 = (int)((double)(this.numColors - 1) * var1 / this.ceil);
               return this.colors[var8];
            }
         }
      }
   }

   private void createVectorfieldPalette() {
      this.colors = new Color[this.numColors];
      int var1 = this.numColors / 3;
      var1 = Math.max(1, var1);
      int var2 = this.background.getRed();
      int var3 = this.background.getGreen();
      int var4 = this.background.getBlue();

      int var5;
      for(var5 = 0; var5 < var1; ++var5) {
         int var6 = var2 - var2 * var5 / var1;
         int var7 = var3 - var3 * var5 / var1;
         this.colors[var5] = new Color(var6, var7, var4);
      }

      for(var5 = var1; var5 < this.numColors; ++var5) {
         double var17 = (double)var1 / 1.2D;
         double var8 = (double)(var5 - var1) / var17;
         double var10 = (double)(var5 - 2 * var1) / var17;
         double var12 = (double)(var5 - this.numColors) / var17;
         int var14 = (int)(255.0D * Math.exp(-var8 * var8));
         int var15 = (int)(255.0D * Math.exp(-var10 * var10));
         int var16 = (int)(255.0D * Math.exp(-var12 * var12));
         var16 = Math.min(255, var16);
         var14 = Math.min(255, var14);
         var15 = Math.min(255, var15);
         this.colors[var5] = new Color(var16, var15, var14);
      }

   }

   public JFrame showLegend() {
      double var1 = 0.0D;
      double var3 = this.ceil * 2.0D;
      InteractivePanel var5 = new InteractivePanel();
      var5.setPreferredSize(new Dimension(300, 120));
      var5.setGutters(0, 0, 0, 35);
      var5.setClipAtGutter(false);
      var5.setSquareAspect(false);
      if (this.legendFrame == null) {
         this.legendFrame = new JFrame("Legend");
      }

      this.legendFrame.setResizable(true);
      this.legendFrame.setContentPane(var5);
      byte var6 = 30;
      GridPointData var7 = new GridPointData(var6, 2, 3);
      double[][][] var8 = var7.getData();
      double var9 = 1.5D * var3 / (double)var6;
      double var11 = var1 - var9 / 2.0D;
      int var13 = 0;

      for(int var14 = var8.length; var13 < var14; ++var13) {
         var8[var13][1][2] = var11;
         var8[var13][1][3] = 0.0D;
         var8[var13][1][4] = 4.0D;
         var11 += var9;
      }

      var7.setScale(0.0D, 1.5D * var3 + var9, 0.0D, 1.0D);
      VectorPlot var15 = new VectorPlot(var7);
      var15.setAutoscaleZ(false, 0.5D * var3, var3);
      var15.update();
      var5.addDrawable(var15);
      XAxis var16 = new XAxis("");
      var16.setLocationType(2);
      var16.setLocation(-0.0D);
      var16.setEnabled(true);
      var5.addDrawable(var16);
      this.legendFrame.pack();
      this.legendFrame.setVisible(true);
      return this.legendFrame;
   }
}
