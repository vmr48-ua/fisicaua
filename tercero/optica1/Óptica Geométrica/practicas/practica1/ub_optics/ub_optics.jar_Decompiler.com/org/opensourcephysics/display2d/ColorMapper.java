package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.display.axes.XAxis;

public class ColorMapper {
   private static final int CUSTOM = -1;
   public static final int SPECTRUM = 0;
   public static final int GRAYSCALE = 1;
   public static final int DUALSHADE = 2;
   public static final int RED = 3;
   public static final int GREEN = 4;
   public static final int BLUE = 5;
   public static final int BLACK = 6;
   public static final int WIREFRAME = 7;
   public static final int NORENDER = 8;
   public static final int REDBLUE_SHADE = 9;
   private Color[] colors;
   private double floor;
   private double ceil;
   private Color floorColor;
   private Color ceilColor;
   private int numColors;
   private int paletteType;
   private JFrame legendFrame;

   public ColorMapper(int var1, double var2, double var4, int var6) {
      this.floorColor = Color.darkGray;
      this.ceilColor = Color.lightGray;
      this.floor = var2;
      this.ceil = var4;
      this.numColors = var1;
      this.setPaletteType(var6);
   }

   public JFrame showLegend() {
      InteractivePanel var1 = new InteractivePanel();
      var1.setPreferredSize(new Dimension(300, 66));
      var1.setGutters(0, 0, 0, 35);
      var1.setClipAtGutter(false);
      if (this.legendFrame == null) {
         this.legendFrame = new JFrame("Legend");
      }

      this.legendFrame.setResizable(false);
      this.legendFrame.setContentPane(var1);
      GridPointData var2 = new GridPointData(this.numColors + 2, 1, 1);
      double[][][] var3 = var2.getData();
      double var4 = (this.ceil - this.floor) / (double)this.numColors;
      double var6 = this.floor - var4 / 2.0D;
      int var8 = 0;

      for(int var9 = var3.length; var8 < var9; ++var8) {
         var3[var8][0][2] = var6;
         var6 += var4;
      }

      var2.setScale(this.floor - var4, this.ceil + var4, 0.0D, 1.0D);
      GridPlot var10 = new GridPlot(var2);
      var10.setShowGridLines(false);
      var10.setAutoscaleZ(false, this.floor, this.ceil);
      var10.setColorPalette(this.colors);
      var10.update();
      var1.addDrawable(var10);
      XAxis var11 = new XAxis("");
      var11.setLocationType(2);
      var11.setLocation(-0.5D);
      var11.setEnabled(true);
      var1.addDrawable(var11);
      this.legendFrame.pack();
      this.legendFrame.setVisible(true);
      return this.legendFrame;
   }

   public void setScale(double var1, double var3) {
      this.floor = var1;
      this.ceil = var3;
   }

   public byte[] doubleToComponents(double var1, byte[] var3) {
      Color var4 = this.doubleToColor(var1);
      var3[0] = (byte)var4.getRed();
      var3[1] = (byte)var4.getGreen();
      var3[2] = (byte)var4.getBlue();
      return var3;
   }

   public Color doubleToColor(double var1) {
      if ((float)this.floor - (float)var1 > Float.MIN_VALUE) {
         return this.floorColor;
      } else if ((float)var1 - (float)this.ceil > Float.MIN_VALUE) {
         return this.ceilColor;
      } else {
         int var3 = (int)((double)this.colors.length * (var1 - this.floor) / (this.ceil - this.floor));
         var3 = Math.max(0, var3);
         return this.colors[Math.min(var3, this.colors.length - 1)];
      }
   }

   public double getFloor() {
      return this.floor;
   }

   public Color getFloorColor() {
      return this.floorColor;
   }

   public double getCeil() {
      return this.ceil;
   }

   public Color getCeilColor() {
      return this.ceilColor;
   }

   public int getNumColors() {
      return this.numColors;
   }

   public void setFloorCeilColor(Color var1, Color var2) {
      this.floorColor = var1;
      this.ceilColor = var2;
   }

   protected int getPaletteType() {
      return this.paletteType;
   }

   protected void setColorPalette(Color[] var1) {
      this.floorColor = Color.darkGray;
      this.ceilColor = Color.lightGray;
      this.colors = var1;
      this.numColors = this.colors.length;
      this.paletteType = -1;
   }

   protected void setNumberOfColors(int var1) {
      if (var1 != this.numColors) {
         this.numColors = var1;
         if (this.paletteType == -1) {
            Color[] var2 = new Color[this.numColors];
            int var3 = 0;

            for(int var4 = Math.min(this.colors.length, this.numColors); var3 < var4; ++var3) {
               var2[var3] = this.colors[var3];
            }

            for(var3 = this.colors.length; var3 < this.numColors; ++var3) {
               var2[var3] = this.colors[this.colors.length - 1];
            }

            this.colors = var2;
         } else {
            this.setPaletteType(this.paletteType);
         }

      }
   }

   protected void setPaletteType(int var1) {
      this.paletteType = var1;
      this.floorColor = Color.darkGray;
      this.ceilColor = Color.lightGray;
      if (this.paletteType == 1 || this.paletteType == 6) {
         this.floorColor = new Color(64, 64, 128);
         this.ceilColor = new Color(255, 191, 191);
      }

      this.colors = getColorPalette(this.numColors, this.paletteType);
      this.numColors = Math.max(2, this.numColors);
   }

   public static Color[] getColorPalette(int var0, int var1) {
      if (var0 < 2) {
         var0 = 2;
      }

      Color[] var2 = new Color[var0];

      for(int var3 = 0; var3 < var0; ++var3) {
         float var4 = (float)var3 / (float)(var0 - 1) * 0.8F;
         boolean var5 = false;
         boolean var6 = false;
         switch(var1) {
         case 0:
            var4 = 0.8F - var4;
            var2[var3] = Color.getHSBColor(var4, 1.0F, 1.0F);
            break;
         case 1:
         case 6:
            var2[var3] = new Color(var3 * 255 / (var0 - 1), var3 * 255 / (var0 - 1), var3 * 255 / (var0 - 1));
            break;
         case 2:
         case 7:
         case 8:
         default:
            var4 = (float)var3 / (float)(var0 - 1);
            var2[var3] = Color.getHSBColor(0.8F * (1.0F - var4), 1.0F, 0.2F + 1.6F * Math.abs(0.5F - var4));
            break;
         case 3:
            var2[var3] = new Color(var3 * 255 / (var0 - 1), 0, 0);
            break;
         case 4:
            var2[var3] = new Color(0, var3 * 255 / (var0 - 1), 0);
            break;
         case 5:
            var2[var3] = new Color(0, 0, var3 * 255 / (var0 - 1));
            break;
         case 9:
            int var7 = Math.max(0, -var0 - 1 + var3 * 2) * 255 / (var0 - 1);
            int var8 = Math.max(0, var0 - 1 - var3 * 2) * 255 / (var0 - 1);
            var2[var3] = new Color(var7, 0, var8);
         }
      }

      return var2;
   }

   public static XML.ObjectLoader getLoader() {
      return new ColorMapper.ColorMapperLoader();
   }

   private static class ColorMapperLoader extends XMLLoader {
      private ColorMapperLoader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         ColorMapper var3 = (ColorMapper)var2;
         var1.setValue("palette type", var3.paletteType);
         var1.setValue("number of colors", var3.numColors);
         var1.setValue("floor", var3.floor);
         var1.setValue("ceiling", var3.ceil);
         var1.setValue("floor color", var3.floorColor);
         var1.setValue("ceiling color", var3.ceilColor);
         if (var3.paletteType == -1) {
            var1.setValue("colors", var3.colors);
         }

      }

      public Object createObject(XMLControl var1) {
         return new ColorMapper(100, -1.0D, 1.0D, 0);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         ColorMapper var3 = (ColorMapper)var2;
         int var4 = var1.getInt("palette type");
         int var5 = var1.getInt("number of colors");
         double var6 = var1.getDouble("floor");
         double var8 = var1.getDouble("ceiling");
         if (var4 == -1) {
            Color[] var10 = (Color[])((Color[])var1.getObject("colors"));
            var3.setColorPalette(var10);
         } else {
            var3.setPaletteType(var4);
            var3.setNumberOfColors(var5);
         }

         var3.setScale(var6, var8);
         Color var12 = (Color)var1.getObject("floor color");
         Color var11 = (Color)var1.getObject("ceiling color");
         var3.setFloorCeilColor(var12, var11);
         return var2;
      }

      // $FF: synthetic method
      ColorMapperLoader(Object var1) {
         this();
      }
   }
}
