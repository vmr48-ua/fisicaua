package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.display.axes.XAxis;

public class ComplexColorMapper {
   static final double PI2 = 6.283185307179586D;
   static final double COLOR_ERR = 1.401298464324817E-43D;
   private double ceil;
   private Color ceilColor;
   private JFrame legendFrame;
   int[] reds;
   int[] greens;
   int[] blues;

   public ComplexColorMapper(double var1) {
      this.ceilColor = Color.lightGray;
      this.reds = new int[256];
      this.greens = new int[256];
      this.blues = new int[256];
      this.ceil = var1;
      this.initColors();
   }

   public static JFrame showPhaseLegend() {
      InteractivePanel var0 = new InteractivePanel();
      var0.setPreferredSize(new Dimension(300, 66));
      var0.setGutters(0, 0, 0, 35);
      var0.setClipAtGutter(false);
      JFrame var1 = new JFrame("Complex Phase");
      var1.setResizable(false);
      var1.setContentPane(var0);
      short var2 = 360;
      GridPointData var3 = new GridPointData(var2, 1, 3);
      double[][][] var4 = var3.getData();
      double var5 = -3.141592653589793D;
      double var7 = 6.283185307179586D / (double)var2;
      int var9 = 0;

      for(int var10 = var4.length; var9 < var10; ++var9) {
         var4[var9][0][2] = 0.999D;
         var4[var9][0][3] = Math.cos(var5);
         var4[var9][0][4] = Math.sin(var5);
         var5 += var7;
      }

      var3.setScale(-3.141592653589793D, 3.141592653589793D, 0.0D, 1.0D);
      ComplexGridPlot var11 = new ComplexGridPlot(var3);
      var11.setShowGridLines(false);
      var11.update();
      var0.addDrawable(var11);
      XAxis var12 = new XAxis("");
      var12.setLocationType(2);
      var12.setLocation(-0.5D);
      var12.setEnabled(true);
      var0.addDrawable(var12);
      var1.pack();
      var1.setVisible(true);
      return var1;
   }

   public JFrame showLegend() {
      InteractivePanel var1 = new InteractivePanel();
      var1.setPreferredSize(new Dimension(300, 66));
      var1.setGutters(0, 0, 0, 35);
      var1.setClipAtGutter(false);
      if (this.legendFrame == null) {
         this.legendFrame = new JFrame("Complex Phase");
      }

      this.legendFrame.setResizable(false);
      this.legendFrame.setContentPane(var1);
      short var2 = 360;
      GridPointData var3 = new GridPointData(var2, 1, 3);
      double[][][] var4 = var3.getData();
      double var5 = -3.141592653589793D;
      double var7 = 6.283185307179586D / (double)var2;
      int var9 = 0;

      for(int var10 = var4.length; var9 < var10; ++var9) {
         var4[var9][0][2] = 0.999D;
         var4[var9][0][3] = Math.cos(var5);
         var4[var9][0][4] = Math.sin(var5);
         var5 += var7;
      }

      var3.setScale(-3.141592653589793D, 3.141592653589793D, 0.0D, 1.0D);
      ComplexGridPlot var11 = new ComplexGridPlot(var3);
      var11.setShowGridLines(false);
      var11.update();
      var1.addDrawable(var11);
      XAxis var12 = new XAxis("");
      var12.setLocationType(2);
      var12.setLocation(-0.5D);
      var12.setEnabled(true);
      var1.addDrawable(var12);
      this.legendFrame.pack();
      this.legendFrame.setVisible(true);
      return this.legendFrame;
   }

   public void setScale(double var1) {
      this.ceil = var1;
   }

   public byte[] samplesToComponents(double[] var1, byte[] var2) {
      Color var3 = this.samplesToColor(var1);
      var2[0] = (byte)var3.getRed();
      var2[1] = (byte)var3.getGreen();
      var2[2] = (byte)var3.getBlue();
      return var2;
   }

   public Color phaseToColor(double var1) {
      float var3 = 1.0F;
      float var4 = (float)((3.141592653589793D + var1) / 6.283185307179586D);
      int var5 = (int)(255.0F * var4);
      return new Color((int)(var3 * (float)this.reds[var5]), (int)(var3 * (float)this.greens[var5]), (int)(var3 * (float)this.blues[var5]));
   }

   public Color complexToColor(double var1, double var3) {
      float var5 = 1.0F;
      float var6 = (float)((3.141592653589793D + Math.atan2(var3, var1)) / 6.283185307179586D);
      int var7 = (int)(255.0F * var6);
      return new Color((int)(var5 * (float)this.reds[var7]), (int)(var5 * (float)this.greens[var7]), (int)(var5 * (float)this.blues[var7]));
   }

   public Color samplesToColor(double[] var1) {
      if (var1[0] <= 0.0D) {
         return Color.black;
      } else if (var1[0] > this.ceil + 1.401298464324817E-43D) {
         return this.ceilColor;
      } else {
         float var2 = (float)(var1[0] / this.ceil);
         float var3 = (float)((3.141592653589793D + Math.atan2(var1[2], var1[1])) / 6.283185307179586D);
         int var4 = (int)(255.0F * var3);
         return new Color((int)(var2 * (float)this.reds[var4]), (int)(var2 * (float)this.greens[var4]), (int)(var2 * (float)this.blues[var4]));
      }
   }

   public Color pointToColor(double[] var1) {
      if (var1[2] <= 0.0D) {
         return Color.black;
      } else if (var1[2] > this.ceil + 1.401298464324817E-43D) {
         return this.ceilColor;
      } else {
         float var2 = (float)(var1[2] / this.ceil);
         float var3 = (float)((3.141592653589793D + Math.atan2(var1[4], var1[3])) / 6.283185307179586D);
         int var4 = (int)(255.0F * var3);
         return new Color((int)(var2 * (float)this.reds[var4]), (int)(var2 * (float)this.greens[var4]), (int)(var2 * (float)this.blues[var4]));
      }
   }

   public double getCeil() {
      return this.ceil;
   }

   public Color getCeilColor() {
      return this.ceilColor;
   }

   public void setCeilColor(Color var1) {
      this.ceilColor = var1;
   }

   private void initColors() {
      double var1 = 3.141592653589793D;

      for(int var3 = 0; var3 < 256; ++var3) {
         double var4 = Math.abs(Math.sin(var1 * (double)var3 / 255.0D));
         this.blues[var3] = (int)(255.0D * var4 * var4);
         var4 = Math.abs(Math.sin(var1 * (double)var3 / 255.0D + var1 / 3.0D));
         this.greens[var3] = (int)(255.0D * var4 * var4 * Math.sqrt(var4));
         var4 = Math.abs(Math.sin(var1 * (double)var3 / 255.0D + 2.0D * var1 / 3.0D));
         this.reds[var3] = (int)(255.0D * var4 * var4);
      }

   }
}
