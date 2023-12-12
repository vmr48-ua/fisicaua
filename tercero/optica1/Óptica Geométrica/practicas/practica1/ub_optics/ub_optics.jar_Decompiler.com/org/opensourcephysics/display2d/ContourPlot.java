package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.DrawingPanel;

public class ContourPlot implements Plot2D {
   private GridData griddata;
   private Color lineColor = new Color(0, 64, 0);
   private boolean visible = true;
   private int contour_lines = 12;
   private boolean showContourLines = true;
   private boolean showColoredLevels = true;
   private double contour_stepz;
   private int[] xpoints = new int[8];
   private int[] ypoints = new int[8];
   private int[] contour_x = new int[8];
   private int[] contour_y = new int[8];
   private double[] delta = new double[4];
   private double[] intersection = new double[4];
   private double[][] contour_vertex = new double[4][3];
   private ContourAccumulator accumulator = new ContourAccumulator();
   private double zmin = 0.0D;
   private double zmax = 1.0D;
   private boolean autoscaleZ = true;
   protected ColorMapper colorMap;
   private Color[] contourColors;
   private double[][] internalData;
   private int ampIndex;
   private int nx;
   private int ny;
   private int maxGridSize;
   protected boolean interpolateLargeGrids;

   public ContourPlot() {
      this.colorMap = new ColorMapper(this.contour_lines, this.zmin, this.zmax, 0);
      this.contourColors = new Color[this.contour_lines + 2];
      this.internalData = new double[1][1];
      this.ampIndex = 0;
      this.nx = 0;
      this.ny = 0;
      this.maxGridSize = 48;
      this.interpolateLargeGrids = true;
   }

   public ContourPlot(GridData var1) {
      this.colorMap = new ColorMapper(this.contour_lines, this.zmin, this.zmax, 0);
      this.contourColors = new Color[this.contour_lines + 2];
      this.internalData = new double[1][1];
      this.ampIndex = 0;
      this.nx = 0;
      this.ny = 0;
      this.maxGridSize = 48;
      this.interpolateLargeGrids = true;
      this.griddata = var1;
      if (this.griddata != null) {
         this.nx = this.griddata.getNx() > this.maxGridSize ? 32 : this.griddata.getNx();
         this.ny = this.griddata.getNy() > this.maxGridSize ? 32 : this.griddata.getNy();
         this.internalData = new double[this.nx][this.ny];
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

   public GridData getGridData() {
      return this.griddata;
   }

   public void setGridData(GridData var1) {
      this.griddata = var1;
      if (this.griddata != null) {
         this.nx = this.griddata.getNx() > this.maxGridSize ? 32 : this.griddata.getNx();
         this.ny = this.griddata.getNy() > this.maxGridSize ? 32 : this.griddata.getNy();
         this.internalData = new double[this.nx][this.ny];
      }
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public JFrame showLegend() {
      return this.colorMap.showLegend();
   }

   public void setShowGridLines(boolean var1) {
      this.showContourLines = var1;
   }

   public void setGridLineColor(Color var1) {
      this.lineColor = var1;
   }

   public synchronized void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible && this.griddata != null) {
         this.accumulator.clearAccumulator();
         this.contour_stepz = (this.zmax - this.zmin) / (double)(this.contour_lines + 1);
         double var3 = this.zmin;

         for(int var5 = 0; var5 < this.contourColors.length; ++var5) {
            this.contourColors[var5] = this.colorMap.doubleToColor(var3);
            var3 += this.contour_stepz;
         }

         double var17 = this.griddata.getLeft();
         double var7 = (this.griddata.getRight() - this.griddata.getLeft()) / (double)(this.nx - 1);
         double var9 = this.griddata.getTop();
         double var11 = -(this.griddata.getTop() - this.griddata.getBottom()) / (double)(this.ny - 1);
         int var13 = 0;

         int var14;
         int var15;
         int var16;
         for(var14 = this.internalData.length - 1; var13 < var14; ++var13) {
            var9 = this.griddata.getTop();
            var15 = 0;

            for(var16 = this.internalData[0].length - 1; var15 < var16; ++var15) {
               this.contour_vertex[0][0] = var17;
               this.contour_vertex[0][1] = var9;
               this.contour_vertex[0][2] = this.internalData[var13][var15];
               this.contour_vertex[1][0] = var17;
               this.contour_vertex[1][1] = var9 + var11;
               this.contour_vertex[1][2] = this.internalData[var13][var15 + 1];
               this.contour_vertex[2][0] = var17 + var7;
               this.contour_vertex[2][1] = var9 + var11;
               this.contour_vertex[2][2] = this.internalData[var13 + 1][var15 + 1];
               this.contour_vertex[3][0] = var17 + var7;
               this.contour_vertex[3][1] = var9;
               this.contour_vertex[3][2] = this.internalData[var13 + 1][var15];
               this.createContour(var1, var2);
               var9 += var11;
            }

            var17 += var7;
         }

         if (this.showContourLines) {
            var2.setColor(this.lineColor);
            this.accumulator.drawAll(var2);
            var13 = var1.xToPix(this.griddata.getLeft());
            var14 = var1.yToPix(this.griddata.getTop());
            var15 = var1.xToPix(this.griddata.getRight());
            var16 = var1.yToPix(this.griddata.getBottom());
            var2.drawRect(Math.min(var13, var15), Math.min(var14, var16), Math.abs(var13 - var15), Math.abs(var14 - var16));
         }

      }
   }

   public void setAutoscaleZ(boolean var1, double var2, double var4) {
      this.autoscaleZ = var1;
      if (this.autoscaleZ) {
         this.update();
      } else {
         this.zmax = var4;
         this.zmin = var2;
         this.colorMap.setScale(this.zmin, this.zmax);
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

   public void update() {
      if (this.griddata != null) {
         if (this.interpolateLargeGrids && this.nx != this.griddata.getNx() && this.ny != this.griddata.getNy()) {
            this.updateInterpolated(this.griddata);
         } else {
            this.updateDirect(this.griddata);
         }

      }
   }

   void updateInterpolated(GridData var1) {
      if (this.autoscaleZ) {
         double[] var2 = var1.getZRange(this.ampIndex);
         this.zmax = var2[1];
         this.zmin = var2[0];
         this.colorMap.setScale(this.zmin, this.zmax);
      }

      double var12 = var1.getLeft();
      double var4 = (var1.getRight() - var1.getLeft()) / (double)(this.nx - 1);
      double var6 = var1.getTop();
      double var8 = -(var1.getTop() - var1.getBottom()) / (double)(this.ny - 1);

      for(int var10 = 0; var10 < this.nx; ++var10) {
         var6 = var1.getTop();

         for(int var11 = 0; var11 < this.ny; ++var11) {
            this.internalData[var10][var11] = var1.interpolate(var12, var6, this.ampIndex);
            var6 += var8;
         }

         var12 += var4;
      }

   }

   void updateDirect(GridData var1) {
      if (var1 != null) {
         if (this.autoscaleZ) {
            double[] var2 = var1.getZRange(this.ampIndex);
            this.zmax = var2[1];
            this.zmin = var2[0];
            this.colorMap.setScale(this.zmin, this.zmax);
         }

         int var3;
         if (var1 instanceof ArrayData) {
            double[][] var7 = var1.getData()[this.ampIndex];

            for(var3 = 0; var3 < this.nx; ++var3) {
               System.arraycopy(var7[var3], 0, this.internalData[var3], 0, this.ny);
            }
         } else if (var1 instanceof GridPointData) {
            double[][][] var8 = var1.getData();
            var3 = 0;

            for(int var4 = var8.length; var3 < var4; ++var3) {
               int var5 = 0;

               for(int var6 = var8[0].length; var5 < var6; ++var5) {
                  this.internalData[var3][var5] = var8[var3][var5][2 + this.ampIndex];
               }
            }
         }

      }
   }

   private final void createContour(DrawingPanel var1, Graphics var2) {
      double var3 = this.zmin;
      this.xpoints[0] = var1.xToPix(this.contour_vertex[0][0]) + 1;
      this.xpoints[2] = var1.xToPix(this.contour_vertex[1][0]) + 1;
      this.xpoints[4] = var1.xToPix(this.contour_vertex[2][0]) + 1;
      this.xpoints[6] = var1.xToPix(this.contour_vertex[3][0]) + 1;
      this.xpoints[1] = this.xpoints[3] = this.xpoints[5] = this.xpoints[7] = -1;
      this.ypoints[0] = var1.yToPix(this.contour_vertex[0][1]) + 1;
      this.ypoints[4] = var1.yToPix(this.contour_vertex[2][1]) + 1;
      this.ypoints[2] = this.ypoints[3] = var1.yToPix(this.contour_vertex[1][1]) + 1;
      this.ypoints[6] = this.ypoints[7] = var1.yToPix(this.contour_vertex[3][1]) + 1;
      int var5 = this.xpoints[0];
      int var6 = this.xpoints[4];

      for(int var7 = 0; var7 <= this.contour_lines + 1; ++var7) {
         int var8;
         int var9;
         int var10;
         for(var8 = 0; var8 < 4; ++var8) {
            var9 = (var8 << 1) + 1;
            var10 = var8 + 1 & 3;
            if (var3 > this.contour_vertex[var8][2]) {
               this.xpoints[var9 - 1] = -2;
               if (var3 > this.contour_vertex[var10][2]) {
                  this.xpoints[var9 + 1 & 7] = -2;
                  this.xpoints[var9] = -2;
               }
            } else if (var3 > this.contour_vertex[var10][2]) {
               this.xpoints[var9 + 1 & 7] = -2;
            }

            if (this.xpoints[var9] != -2) {
               if (this.xpoints[var9] != -1) {
                  double[] var10000 = this.intersection;
                  var10000[var8] += this.delta[var8];
                  if (var9 != 1 && var9 != 5) {
                     this.xpoints[var9] = var1.xToPix(this.intersection[var8]) + 1;
                  } else {
                     this.ypoints[var9] = var1.yToPix(this.intersection[var8]) + 1;
                  }
               } else if (var3 > this.contour_vertex[var8][2] || var3 > this.contour_vertex[var10][2]) {
                  switch(var9) {
                  case 1:
                     this.delta[var8] = (this.contour_vertex[var10][1] - this.contour_vertex[var8][1]) * this.contour_stepz / (this.contour_vertex[var10][2] - this.contour_vertex[var8][2]);
                     this.intersection[var8] = (this.contour_vertex[var10][1] * (var3 - this.contour_vertex[var8][2]) + this.contour_vertex[var8][1] * (this.contour_vertex[var10][2] - var3)) / (this.contour_vertex[var10][2] - this.contour_vertex[var8][2]);
                     this.xpoints[var9] = var5;
                     this.ypoints[var9] = var1.yToPix(this.intersection[var8]) + 1;
                  case 2:
                  case 4:
                  case 6:
                  default:
                     break;
                  case 3:
                     this.delta[var8] = (this.contour_vertex[var10][0] - this.contour_vertex[var8][0]) * this.contour_stepz / (this.contour_vertex[var10][2] - this.contour_vertex[var8][2]);
                     this.intersection[var8] = (this.contour_vertex[var10][0] * (var3 - this.contour_vertex[var8][2]) + this.contour_vertex[var8][0] * (this.contour_vertex[var10][2] - var3)) / (this.contour_vertex[var10][2] - this.contour_vertex[var8][2]);
                     this.xpoints[var9] = var1.xToPix(this.intersection[var8]) + 1;
                     break;
                  case 5:
                     this.delta[var8] = (this.contour_vertex[var8][1] - this.contour_vertex[var10][1]) * this.contour_stepz / (this.contour_vertex[var8][2] - this.contour_vertex[var10][2]);
                     this.intersection[var8] = (this.contour_vertex[var8][1] * (var3 - this.contour_vertex[var10][2]) + this.contour_vertex[var10][1] * (this.contour_vertex[var8][2] - var3)) / (this.contour_vertex[var8][2] - this.contour_vertex[var10][2]);
                     this.xpoints[var9] = var6;
                     this.ypoints[var9] = var1.yToPix(this.intersection[var8]) + 1;
                     break;
                  case 7:
                     this.delta[var8] = (this.contour_vertex[var8][0] - this.contour_vertex[var10][0]) * this.contour_stepz / (this.contour_vertex[var8][2] - this.contour_vertex[var10][2]);
                     this.intersection[var8] = (this.contour_vertex[var8][0] * (var3 - this.contour_vertex[var10][2]) + this.contour_vertex[var10][0] * (this.contour_vertex[var8][2] - var3)) / (this.contour_vertex[var8][2] - this.contour_vertex[var10][2]);
                     this.xpoints[var9] = var1.xToPix(this.intersection[var8]) + 1;
                  }
               }
            }
         }

         var8 = 0;

         for(var9 = 0; var9 < 8; ++var9) {
            if (this.xpoints[var9] >= 0) {
               this.contour_x[var8] = this.xpoints[var9];
               this.contour_y[var8] = this.ypoints[var9];
               ++var8;
            }
         }

         if (this.showColoredLevels && this.colorMap.getPaletteType() != 7) {
            var2.setColor(this.contourColors[var7]);
            if (var8 > 0) {
               var2.fillPolygon(this.contour_x, this.contour_y, var8);
            }
         }

         if (this.showContourLines) {
            var9 = -1;
            var10 = -1;

            for(int var11 = 1; var11 < 8; var11 += 2) {
               if (this.xpoints[var11] >= 0) {
                  if (var9 != -1) {
                     this.accumulator.addLine(var9, var10, this.xpoints[var11], this.ypoints[var11]);
                  }

                  var9 = this.xpoints[var11];
                  var10 = this.ypoints[var11];
               }
            }

            if (this.xpoints[1] > 0 && var9 != -1) {
               this.accumulator.addLine(var9, var10, this.xpoints[1], this.ypoints[1]);
            }
         }

         if (var8 < 3) {
            break;
         }

         var3 += this.contour_stepz;
      }

   }

   public void setColorPalette(Color[] var1) {
      this.colorMap.setColorPalette(var1);
   }

   public void setPaletteType(int var1) {
      this.colorMap.setPaletteType(var1);
   }

   public void setFloorCeilColor(Color var1, Color var2) {
      this.colorMap.setFloorCeilColor(var1, var2);
   }

   public void setIndexes(int[] var1) {
      this.ampIndex = var1[0];
   }

   public void setNumberOfLevels(int var1) {
      this.contour_lines = var1;
      this.colorMap.setNumberOfColors(var1);
      this.contourColors = new Color[this.contour_lines + 2];
   }

   public double getXMin() {
      return this.griddata.getLeft();
   }

   public double getXMax() {
      return this.griddata.getRight();
   }

   public double getYMin() {
      return this.griddata.getBottom();
   }

   public double getYMax() {
      return this.griddata.getTop();
   }

   public boolean isMeasured() {
      return this.griddata != null;
   }

   public static XML.ObjectLoader getLoader() {
      return new Plot2DLoader() {
         public void saveObject(XMLControl var1, Object var2) {
            super.saveObject(var1, var2);
            ContourPlot var3 = (ContourPlot)var2;
            var1.setValue("line color", var3.lineColor);
            var1.setValue("color map", var3.colorMap);
         }

         public Object createObject(XMLControl var1) {
            return new ContourPlot((GridData)null);
         }

         public Object loadObject(XMLControl var1, Object var2) {
            super.loadObject(var1, var2);
            ContourPlot var3 = (ContourPlot)var2;
            var3.lineColor = (Color)var1.getObject("line color");
            var3.colorMap = (ColorMapper)var1.getObject("color map");
            return var3;
         }
      };
   }
}
