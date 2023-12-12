package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BandedSampleModel;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import javax.swing.JFrame;
import org.opensourcephysics.controls.OSPLog;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Grid;
import org.opensourcephysics.display.MeasuredImage;

public class ComplexInterpolatedPlot extends MeasuredImage implements Plot2D {
   GridData griddata;
   byte[][] rgbData;
   Grid grid;
   ComplexColorMapper colorMap;
   boolean autoscaleZ = true;
   int ampIndex = 0;
   int reIndex = 1;
   int imIndex = 2;
   int leftPix;
   int rightPix;
   int topPix;
   int bottomPix;
   int ixsize;
   int iysize;
   double top;
   double left;
   double bottom;
   double right;

   public ComplexInterpolatedPlot(GridData var1) {
      this.griddata = var1;
      this.colorMap = new ComplexColorMapper(1.0D);
      if (this.griddata == null) {
         this.grid = new Grid(1, 1, super.xmin, super.xmax, super.ymin, super.ymax);
      } else {
         this.grid = new Grid(this.griddata.getData().length, this.griddata.getData()[0].length, super.xmin, super.xmax, super.ymin, super.ymax);
      }

      this.grid.setColor(Color.lightGray);
      this.grid.setVisible(false);
      this.update();
   }

   public int xToIndex(double var1) {
      return this.griddata.xToIndex(var1);
   }

   public int yToIndex(double var1) {
      return this.griddata.yToIndex(var1);
   }

   public double indexToX(int var1) {
      return this.griddata.indexToX(var1);
   }

   public double indexToY(int var1) {
      return this.griddata.indexToY(var1);
   }

   public void setAll(Object var1) {
      double[][][] var2 = (double[][][])((double[][][])var1);
      this.copyComplexData(var2);
      this.update();
   }

   public void setAll(Object var1, double var2, double var4, double var6, double var8) {
      double[][][] var10 = (double[][][])((double[][][])var1);
      this.copyComplexData(var10);
      if (this.griddata.isCellData()) {
         this.griddata.setCellScale(var2, var4, var6, var8);
      } else {
         this.griddata.setScale(var2, var4, var6, var8);
      }

      this.update();
   }

   private void copyComplexData(double[][][] var1) {
      if (this.griddata != null && !(this.griddata instanceof ArrayData)) {
         throw new IllegalStateException("SetAll only supports ArrayData for data storage.");
      } else {
         if (this.griddata == null || this.griddata.getNx() != var1[0].length || this.griddata.getNy() != var1[0][0].length) {
            this.griddata = new ArrayData(var1[0].length, var1[0][0].length, 3);
            this.setGridData(this.griddata);
         }

         double[][] var2 = this.griddata.getData()[0];
         double[][] var3 = this.griddata.getData()[1];
         double[][] var4 = this.griddata.getData()[2];
         int var5 = var1[0][0].length;
         int var6 = 0;

         for(int var7 = var1[0].length; var6 < var7; ++var6) {
            System.arraycopy(var1[0][var6], 0, var3[var6], 0, var5);
            System.arraycopy(var1[1][var6], 0, var4[var6], 0, var5);

            for(int var8 = 0; var8 < var5; ++var8) {
               var2[var6][var8] = Math.sqrt(var1[0][var6][var8] * var1[0][var6][var8] + var1[1][var6][var8] * var1[1][var6][var8]);
            }
         }

      }
   }

   public GridData getGridData() {
      return this.griddata;
   }

   public void setGridData(GridData var1) {
      this.griddata = var1;
      if (this.griddata != null) {
         Grid var2 = new Grid(this.griddata.getNx(), this.griddata.getNy());
         var2.setColor(Color.lightGray);
         if (this.grid != null) {
            var2.setColor(this.grid.getColor());
            var2.setVisible(this.grid.isVisible());
         } else {
            var2.setColor(Color.lightGray);
         }

         this.grid = var2;
      }
   }

   public void setIndexes(int[] var1) {
      this.ampIndex = var1[0];
      this.reIndex = var1[1];
      this.imIndex = var1[2];
   }

   public void setAutoscaleZ(boolean var1, double var2) {
      this.autoscaleZ = var1;
      if (this.autoscaleZ) {
         this.update();
      } else {
         this.colorMap.setScale(var2);
      }

   }

   public void setAutoscaleZ(boolean var1, double var2, double var4) {
      this.setAutoscaleZ(var1, var4);
   }

   public boolean isAutoscaleZ() {
      return this.autoscaleZ;
   }

   public double getFloor() {
      return 0.0D;
   }

   public double getCeiling() {
      return this.colorMap.getCeil();
   }

   public void setFloorCeilColor(Color var1, Color var2) {
      this.colorMap.setCeilColor(var2);
   }

   public void setShowGridLines(boolean var1) {
      this.grid.setVisible(var1);
   }

   public void setGridLineColor(Color var1) {
      this.grid.setColor(var1);
   }

   public void update() {
      if (this.autoscaleZ && this.griddata != null) {
         double[] var1 = this.griddata.getZRange(this.ampIndex);
         this.colorMap.setScale(var1[1]);
      }

      this.recolorImage();
   }

   protected void checkImage(DrawingPanel var1) {
      int var2;
      int var3;
      int var4;
      int var5;
      if (this.griddata.isCellData()) {
         double var6 = this.griddata.getDx();
         double var8 = this.griddata.getDy();
         var2 = var1.xToPix(this.griddata.getLeft() - var6 / 2.0D);
         var3 = var1.xToPix(this.griddata.getRight() + var6 / 2.0D);
         var4 = var1.yToPix(this.griddata.getBottom() + var8 / 2.0D);
         var5 = var1.yToPix(this.griddata.getTop() - var8 / 2.0D);
      } else {
         var2 = var1.xToPix(this.griddata.getLeft());
         var3 = var1.xToPix(this.griddata.getRight());
         var4 = var1.yToPix(this.griddata.getBottom());
         var5 = var1.yToPix(this.griddata.getTop());
      }

      this.leftPix = Math.min(var2, var3);
      this.rightPix = Math.max(var2, var3);
      this.bottomPix = Math.max(var4, var5);
      this.topPix = Math.min(var4, var5);
      this.ixsize = this.rightPix - this.leftPix + 1;
      this.iysize = this.bottomPix - this.topPix + 1;
      this.leftPix = Math.max(0, this.leftPix);
      this.rightPix = Math.min(this.rightPix, var1.getWidth());
      this.topPix = Math.max(0, this.topPix);
      this.bottomPix = Math.min(this.bottomPix, var1.getHeight());
      int var13 = this.bottomPix - this.topPix + 1;
      int var7 = this.rightPix - this.leftPix + 1;
      if (super.image == null || super.image.getWidth() != var7 || super.image.getHeight() != var13 || this.left != var1.pixToX(this.leftPix) || this.top != var1.pixToY(this.topPix) || this.bottom != var1.pixToX(this.bottomPix) || this.right != var1.pixToY(this.rightPix)) {
         this.left = var1.pixToX(this.leftPix);
         this.top = var1.pixToY(this.topPix);
         this.bottom = var1.pixToX(this.bottomPix);
         this.right = var1.pixToY(this.rightPix);
         if (super.image != null && super.image.getWidth() == var7 && super.image.getHeight() == var13) {
            this.recolorImage();
         } else {
            int var14 = var13 * var7;
            if (var14 < 4) {
               super.image = null;
            } else {
               OSPLog.finer("ComplexInterpolatedPlot image created with row=" + var13 + " and col=" + var7);
               ComponentColorModel var9 = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8}, false, false, 1, 0);
               BandedSampleModel var10 = new BandedSampleModel(0, var7, var13, var7, new int[]{0, 1, 2}, new int[]{0, 0, 0});
               this.rgbData = new byte[3][var14];
               DataBufferByte var11 = new DataBufferByte(this.rgbData, var14);
               WritableRaster var12 = Raster.createWritableRaster(var10, var11, new Point(0, 0));
               super.image = new BufferedImage(var9, var12, false, (Hashtable)null);
               this.update();
            }
         }
      }
   }

   protected void recolorImage() {
      GridData var1 = this.griddata;
      byte[][] var2 = this.rgbData;
      BufferedImage var3 = super.image;
      if (var1 != null) {
         if (var1.isCellData()) {
            double var4 = var1.getDx();
            double var6 = var1.getDy();
            super.xmin = var1.getLeft() - var4 / 2.0D;
            super.xmax = var1.getRight() + var4 / 2.0D;
            super.ymin = var1.getBottom() + var6 / 2.0D;
            super.ymax = var1.getTop() - var6 / 2.0D;
         } else {
            super.xmin = var1.getLeft();
            super.xmax = var1.getRight();
            super.ymin = var1.getBottom();
            super.ymax = var1.getTop();
         }

         this.grid.setMinMax(super.xmin, super.xmax, super.ymin, super.ymax);
         if (var3 != null) {
            if (var2[0].length == var3.getWidth() * var3.getHeight()) {
               byte[] var20 = new byte[3];
               double var5 = this.top;
               int var7 = var3.getWidth();
               double var8 = (super.xmax - super.xmin) / (double)(this.ixsize - 1);
               double var10 = (super.ymin - super.ymax) / (double)(this.iysize - 1);
               if (var1.getDx() < 0.0D) {
                  var8 = -var8;
               }

               if (var1.getDy() > 0.0D) {
                  var10 = -var10;
               }

               double[] var12 = new double[3];
               int[] var13 = new int[]{this.ampIndex, this.reIndex, this.imIndex};
               int var14 = 0;

               for(int var15 = var3.getHeight(); var14 < var15; ++var14) {
                  double var16 = this.left;

                  for(int var18 = 0; var18 < var7; ++var18) {
                     this.colorMap.samplesToComponents(var1.interpolate(var16, var5, var13, var12), var20);
                     int var19 = var10 < 0.0D ? var14 * var7 + var18 : (var15 - var14 - 1) * var7 + var18;
                     var2[0][var19] = var20[0];
                     var2[1][var19] = var20[1];
                     var2[2][var19] = var20[2];
                     var16 += var8;
                  }

                  var5 += var10;
               }

            }
         }
      }
   }

   public void setPaletteType(int var1) {
   }

   public void setColorPalette(Color[] var1) {
   }

   public JFrame showLegend() {
      return this.colorMap.showLegend();
   }

   public boolean isMeasured() {
      return true;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (super.visible && this.griddata != null) {
         this.checkImage(var1);
         if (super.image != null) {
            var2.drawImage(super.image, this.leftPix, this.topPix, var1);
         }

         this.grid.draw(var1, var2);
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new Plot2DLoader() {
         public Object createObject(XMLControl var1) {
            return new ComplexInterpolatedPlot((GridData)null);
         }
      };
   }
}
