package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Grid;
import org.opensourcephysics.display.MeasuredImage;

public class GridPlot extends MeasuredImage implements Plot2D {
   boolean autoscaleZ = true;
   GridData griddata;
   int[] rgbData;
   Grid grid;
   ColorMapper colorMap;
   private int ampIndex = 0;

   public GridPlot(GridData var1) {
      this.setGridData(var1);
   }

   public GridPlot() {
   }

   public void setIndexes(int[] var1) {
      this.ampIndex = var1[0];
   }

   public GridData getGridData() {
      return this.griddata;
   }

   public void setGridData(GridData var1) {
      this.griddata = var1;
      if (this.colorMap == null) {
         this.colorMap = new ColorMapper(100, -1.0D, 1.0D, 0);
      }

      if (this.griddata != null) {
         int var2 = this.griddata.getNx();
         int var3 = this.griddata.getNy();
         this.rgbData = new int[var2 * var3];
         super.image = new BufferedImage(var2, var3, 2);
         Grid var4 = new Grid(var2, var3);
         var4.setColor(Color.lightGray);
         if (this.grid != null) {
            var4.setColor(this.grid.getColor());
            var4.setVisible(this.grid.isVisible());
         } else {
            var4.setColor(Color.lightGray);
         }

         this.grid = var4;
         this.update();
      }
   }

   public double indexToX(int var1) {
      return this.griddata.indexToX(var1);
   }

   public double indexToY(int var1) {
      return this.griddata.indexToY(var1);
   }

   public int xToIndex(double var1) {
      return this.griddata.xToIndex(var1);
   }

   public int yToIndex(double var1) {
      return this.griddata.yToIndex(var1);
   }

   public void setAll(Object var1) {
      double[][] var2 = (double[][])((double[][])var1);
      this.copyData(var2);
      this.update();
   }

   public void setAll(Object var1, double var2, double var4, double var6, double var8) {
      double[][] var10 = (double[][])((double[][])var1);
      this.copyData(var10);
      if (this.griddata.isCellData()) {
         this.griddata.setCellScale(var2, var4, var6, var8);
      } else {
         this.griddata.setScale(var2, var4, var6, var8);
      }

      this.setMinMax(var2, var4, var6, var8);
      this.update();
   }

   private void copyData(double[][] var1) {
      if (this.griddata != null && !(this.griddata instanceof ArrayData)) {
         throw new IllegalStateException("SetAll only supports ArrayData for data storage.");
      } else {
         if (this.griddata == null || this.griddata.getNx() != var1.length || this.griddata.getNy() != var1[0].length) {
            this.griddata = new ArrayData(var1.length, var1[0].length, 1);
            this.setGridData(this.griddata);
         }

         double[][] var2 = this.griddata.getData()[0];
         int var3 = var2[0].length;
         int var4 = 0;

         for(int var5 = var2.length; var4 < var5; ++var4) {
            System.arraycopy(var1[var4], 0, var2[var4], 0, var3);
         }

      }
   }

   public JFrame showLegend() {
      return this.colorMap.showLegend();
   }

   public void setAutoscaleZ(boolean var1, double var2, double var4) {
      this.autoscaleZ = var1;
      if (this.autoscaleZ) {
         this.update();
      } else {
         this.colorMap.setScale(var2, var4);
      }

   }

   public boolean isAutoscaleZ() {
      return this.autoscaleZ;
   }

   public double getFloor() {
      return this.colorMap.getFloor();
   }

   public double getCeiling() {
      return this.colorMap.getCeil();
   }

   public void setPaletteType(int var1) {
      this.colorMap.setPaletteType(var1);
   }

   public void setColorPalette(Color[] var1) {
      this.colorMap.setColorPalette(var1);
   }

   public void setFloorCeilColor(Color var1, Color var2) {
      this.colorMap.setFloorCeilColor(var1, var2);
   }

   public void setShowGridLines(boolean var1) {
      this.grid.setVisible(var1);
   }

   public void setGridLineColor(Color var1) {
      this.grid.setColor(var1);
   }

   public void update() {
      if (this.griddata != null) {
         if (this.autoscaleZ) {
            double[] var1 = this.griddata.getZRange(this.ampIndex);
            this.colorMap.setScale(var1[0], var1[1]);
         }

         this.recolorImage();
      }
   }

   protected void recolorImage() {
      if (this.griddata != null) {
         if (this.griddata.isCellData()) {
            double var1 = this.griddata.getDx();
            double var3 = this.griddata.getDy();
            super.xmin = this.griddata.getLeft() - var1 / 2.0D;
            super.xmax = this.griddata.getRight() + var1 / 2.0D;
            super.ymin = this.griddata.getBottom() + var3 / 2.0D;
            super.ymax = this.griddata.getTop() - var3 / 2.0D;
         } else {
            super.xmin = this.griddata.getLeft();
            super.xmax = this.griddata.getRight();
            super.ymin = this.griddata.getBottom();
            super.ymax = this.griddata.getTop();
         }

         if (this.grid != null) {
            this.grid.setMinMax(super.xmin, super.xmax, super.ymin, super.ymax);
         }

         double[][][] var9 = this.griddata.getData();
         int var2 = this.griddata.getNx();
         int var10 = this.griddata.getNy();
         int var4;
         int var5;
         int var6;
         int var7;
         if (this.griddata instanceof GridPointData) {
            var4 = this.ampIndex + 2;
            var5 = 0;

            for(var6 = 0; var5 < var10; ++var5) {
               for(var7 = 0; var7 < var2; ++var7) {
                  this.rgbData[var6] = this.colorMap.doubleToColor(var9[var7][var5][var4]).getRGB();
                  ++var6;
               }
            }

            super.image.setRGB(0, 0, var2, var10, this.rgbData, 0, var2);
         } else if (this.griddata instanceof ArrayData) {
            var4 = 0;

            for(var5 = 0; var4 < var10; ++var4) {
               for(var6 = 0; var6 < var2; ++var6) {
                  this.rgbData[var5] = this.colorMap.doubleToColor(var9[this.ampIndex][var6][var4]).getRGB();
                  ++var5;
               }
            }

            super.image.setRGB(0, 0, var2, var10, this.rgbData, 0, var2);
         } else if (this.griddata instanceof FlatData) {
            var4 = var9[0][0].length / (var2 * var10);
            var5 = 0;

            for(var6 = 0; var5 < var10; ++var5) {
               var7 = var5 * var2 * var4;

               for(int var8 = 0; var8 < var2; ++var8) {
                  this.rgbData[var6] = this.colorMap.doubleToColor(var9[0][0][var7 + var8 * var4 + this.ampIndex]).getRGB();
                  ++var6;
               }
            }

            super.image.setRGB(0, 0, var2, var10, this.rgbData, 0, var2);
         }

      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (super.visible && this.griddata != null) {
         super.draw(var1, var2);
         this.grid.draw(var1, var2);
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new Plot2DLoader() {
         public Object createObject(XMLControl var1) {
            return new GridPlot((GridData)null);
         }

         public void saveObject(XMLControl var1, Object var2) {
            super.saveObject(var1, var2);
            GridPlot var3 = (GridPlot)var2;
            var1.setValue("color map", var3.colorMap);
         }

         public Object loadObject(XMLControl var1, Object var2) {
            super.loadObject(var1, var2);
            GridPlot var3 = (GridPlot)var2;
            var3.colorMap = (ColorMapper)var1.getObject("color map");
            return var3;
         }
      };
   }
}
