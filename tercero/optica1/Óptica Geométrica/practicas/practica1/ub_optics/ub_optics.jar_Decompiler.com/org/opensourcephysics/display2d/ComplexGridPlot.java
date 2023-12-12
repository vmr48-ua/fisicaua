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

public class ComplexGridPlot extends MeasuredImage implements Plot2D {
   boolean autoscaleZ;
   GridData griddata;
   int[] rgbData;
   Grid grid;
   ComplexColorMapper colorMap;
   private int ampIndex;
   private int reIndex;
   private int imIndex;

   public ComplexGridPlot() {
      this((GridData)null);
   }

   public ComplexGridPlot(GridData var1) {
      this.autoscaleZ = true;
      this.ampIndex = 0;
      this.reIndex = 1;
      this.imIndex = 2;
      this.griddata = var1;
      this.colorMap = new ComplexColorMapper(1.0D);
      if (this.griddata != null) {
         this.setGridData(this.griddata);
      }
   }

   public GridData getGridData() {
      return this.griddata;
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

   public void setGridData(GridData var1) {
      this.griddata = var1;
      int var2 = this.griddata.getNx();
      int var3 = this.griddata.getNy();
      this.rgbData = new int[var2 * var3];
      super.image = new BufferedImage(var2, var3, 2);
      Grid var4 = new Grid(var2, var3);
      if (this.grid != null) {
         var4.setColor(this.grid.getColor());
         var4.setVisible(this.grid.isVisible());
      } else {
         var4.setColor(Color.lightGray);
      }

      this.grid = var4;
      this.update();
   }

   public JFrame showLegend() {
      return this.colorMap.showLegend();
   }

   public void setAutoscaleZ(boolean var1, double var2, double var4) {
      this.autoscaleZ = var1;
      if (this.autoscaleZ) {
         this.update();
      } else {
         this.colorMap.setScale(var4);
      }

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
      if (this.grid == null) {
         this.grid = new Grid(1, 1);
      }

      this.grid.setVisible(var1);
   }

   public void update() {
      if (this.autoscaleZ) {
         double[] var1 = this.griddata.getZRange(this.ampIndex);
         this.colorMap.setScale(var1[1]);
      }

      this.recolorImage();
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

         this.grid.setMinMax(super.xmin, super.xmax, super.ymin, super.ymax);
         double[][][] var11 = this.griddata.getData();
         int var2 = this.griddata.getNx();
         int var12 = this.griddata.getNy();
         double[] var4 = new double[3];
         int var5;
         int var6;
         int var7;
         if (this.griddata instanceof GridPointData) {
            var5 = this.ampIndex + 2;
            var6 = this.reIndex + 2;
            var7 = this.imIndex + 2;
            int var8 = 0;

            for(int var9 = 0; var8 < var12; ++var8) {
               for(int var10 = 0; var10 < var2; ++var10) {
                  var4[0] = var11[var10][var8][var5];
                  var4[1] = var11[var10][var8][var6];
                  var4[2] = var11[var10][var8][var7];
                  this.rgbData[var9] = this.colorMap.samplesToColor(var4).getRGB();
                  ++var9;
               }
            }
         } else if (this.griddata instanceof ArrayData) {
            var5 = 0;

            for(var6 = 0; var5 < var12; ++var5) {
               for(var7 = 0; var7 < var2; ++var7) {
                  var4[0] = var11[this.ampIndex][var7][var5];
                  var4[1] = var11[this.reIndex][var7][var5];
                  var4[2] = var11[this.imIndex][var7][var5];
                  this.rgbData[var6] = this.colorMap.samplesToColor(var4).getRGB();
                  ++var6;
               }
            }
         }

         super.image.setRGB(0, 0, var2, var12, this.rgbData, 0, var2);
      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (super.visible && this.griddata != null) {
         super.draw(var1, var2);
         this.grid.draw(var1, var2);
      }
   }

   public void setColorPalette(Color[] var1) {
   }

   public void setPaletteType(int var1) {
   }

   public void setGridLineColor(Color var1) {
      if (this.grid == null) {
         this.grid = new Grid(1, 1);
      }

      this.grid.setColor(var1);
   }

   public void setIndexes(int[] var1) {
      this.ampIndex = var1[0];
      this.reIndex = var1[1];
      this.imIndex = var1[2];
   }

   public static XML.ObjectLoader getLoader() {
      return new Plot2DLoader() {
         public Object createObject(XMLControl var1) {
            return new ComplexGridPlot((GridData)null);
         }
      };
   }
}
