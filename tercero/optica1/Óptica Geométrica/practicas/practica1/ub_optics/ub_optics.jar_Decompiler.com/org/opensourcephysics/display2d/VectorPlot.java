package org.opensourcephysics.display2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Grid;

public class VectorPlot implements Plot2D {
   public static final int STROKEDARROW = 0;
   public static final int FILLEDARROW = 1;
   private GeneralPath vectorpath;
   private int arrowType;
   private boolean visible;
   private GridData griddata;
   private boolean autoscaleZ;
   private boolean scaleArrowToGrid;
   private VectorColorMapper colorMap;
   private int ampIndex;
   private int aIndex;
   private int bIndex;
   private double xmin;
   private double xmax;
   private double ymin;
   private double ymax;
   Grid grid;

   public VectorPlot() {
      this((GridData)null);
   }

   public VectorPlot(GridData var1) {
      this.arrowType = 0;
      this.visible = true;
      this.autoscaleZ = true;
      this.scaleArrowToGrid = true;
      this.ampIndex = 0;
      this.aIndex = 1;
      this.bIndex = 2;
      this.griddata = var1;
      this.colorMap = new VectorColorMapper(256, 1.0D);
      if (this.griddata != null) {
         this.grid = this.griddata.isCellData() ? new Grid(this.griddata.getNx(), this.griddata.getNy()) : new Grid(this.griddata.getNx() - 1, this.griddata.getNy() - 1);
         this.grid.setColor(Color.lightGray);
         this.grid.setVisible(false);
         this.update();
      }
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
      this.copyVecData(var2);
      this.update();
   }

   public void setAll(Object var1, double var2, double var4, double var6, double var8) {
      double[][][] var10 = (double[][][])((double[][][])var1);
      this.copyVecData(var10);
      if (this.griddata.isCellData()) {
         this.griddata.setCellScale(var2, var4, var6, var8);
      } else {
         this.griddata.setScale(var2, var4, var6, var8);
      }

      this.update();
   }

   private void copyVecData(double[][][] var1) {
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
            for(int var8 = 0; var8 < var5; ++var8) {
               var2[var6][var8] = Math.sqrt(var1[0][var6][var8] * var1[0][var6][var8] + var1[1][var6][var8] * var1[1][var6][var8]);
               var3[var6][var8] = var2[var6][var8] == 0.0D ? 0.0D : var1[0][var6][var8] / var2[var6][var8];
               var4[var6][var8] = var2[var6][var8] == 0.0D ? 0.0D : var1[1][var6][var8] / var2[var6][var8];
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
         Grid var2 = this.griddata.isCellData() ? new Grid(this.griddata.getNx(), this.griddata.getNy()) : new Grid(this.griddata.getNx() - 1, this.griddata.getNy() - 1);
         var2.setColor(Color.lightGray);
         var2.setVisible(false);
         if (this.grid != null) {
            var2.setColor(this.grid.getColor());
            var2.setVisible(this.grid.isVisible());
         }

         this.grid = var2;
      }
   }

   public void setIndexes(int[] var1) {
      this.ampIndex = var1[0];
      this.aIndex = var1[1];
      this.bIndex = var1[2];
   }

   public void setArrowType(int var1) {
      this.arrowType = var1;
   }

   public void setPaletteType(int var1) {
      this.colorMap.setPaletteType(var1);
   }

   public void setColorPalette(Color[] var1) {
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public void setShowGridLines(boolean var1) {
      if (this.grid == null) {
         this.grid = new Grid(0);
      }

      this.grid.setVisible(var1);
   }

   public void setGridLineColor(Color var1) {
      this.grid.setColor(var1);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible && this.griddata != null) {
         if (this.grid.isVisible()) {
            this.grid.draw(var1, var2);
         }

         this.colorMap.checkPallet(var1.getBackground());
         GridData var3 = this.griddata;
         double[][][] var4 = var3.getData();
         double var5 = var3.getDx();
         double var7 = var3.getDy();
         double var9 = var3.getLeft();
         double var11 = var3.getTop();
         double var13 = var1.getAspectRatio();
         float var15 = (float)Math.abs(var1.getYPixPerUnit());
         if (this.scaleArrowToGrid) {
            var15 = Math.max(1.0F, (float)var1.getSize().width / (float)var4.length / (float)var13 - 1.0F);
            var15 = Math.min(18.0F, var15 * 0.72F);
         }

         switch(this.arrowType) {
         case 0:
            this.vectorpath = createVectorPath(var15);
            break;
         case 1:
            this.vectorpath = createFilledVectorPath(var15);
            break;
         default:
            this.vectorpath = createVectorPath(var15);
         }

         byte var16 = var1.getXPixPerUnit() < 0.0D ? (var16 = -1) : 1;
         byte var17 = var1.getYPixPerUnit() < 0.0D ? (var17 = -1) : 1;
         double var18 = 0.0D;
         double var20 = 0.0D;
         double var22 = 0.0D;
         double var24 = 0.0D;
         double var26 = 0.0D;
         Color var28 = var1.getBackground();
         int var29 = 0;

         for(int var30 = var3.getNx(); var29 < var30; ++var29) {
            int var31 = 0;

            for(int var32 = var3.getNy(); var31 < var32; ++var31) {
               if (var3 instanceof GridPointData) {
                  var24 = var4[var29][var31][0];
                  var26 = var4[var29][var31][1];
                  var18 = var4[var29][var31][this.ampIndex + 2];
                  var20 = var4[var29][var31][this.aIndex + 2];
                  var22 = var4[var29][var31][this.bIndex + 2];
               } else if (var3 instanceof ArrayData) {
                  var24 = var9 + (double)var29 * var5;
                  var26 = var11 + (double)var31 * var7;
                  var18 = var4[this.ampIndex][var29][var31];
                  var20 = var4[this.aIndex][var29][var31];
                  var22 = var4[this.bIndex][var29][var31];
               }

               Graphics2D var33 = (Graphics2D)var2;
               Color var34 = this.colorMap.doubleToColor(var18);
               if (var28 != var34) {
                  var33.setColor(this.colorMap.doubleToColor(var18));
                  AffineTransform var35 = new AffineTransform((double)var16 * var13 * var20, (double)(-var17) * var22, (double)var16 * var13 * var22, (double)var17 * var20, (double)var1.xToPix(var24), (double)var1.yToPix(var26));
                  Shape var36 = this.vectorpath.createTransformedShape(var35);
                  switch(this.arrowType) {
                  case 0:
                     var33.draw(var36);
                     break;
                  case 1:
                     var33.fill(var36);
                     break;
                  default:
                     var33.draw(var36);
                  }
               }
            }
         }

      }
   }

   public void scaleArrowLenghToGrid(boolean var1) {
      this.scaleArrowToGrid = var1;
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
      return this.colorMap.getCeiling();
   }

   public void setFloorCeilColor(Color var1, Color var2) {
   }

   public JFrame showLegend() {
      return this.colorMap.showLegend();
   }

   public void update() {
      if (this.griddata != null) {
         if (this.autoscaleZ) {
            double[] var1 = this.griddata.getZRange(this.ampIndex);
            this.colorMap.setScale(var1[1]);
         }

         if (this.griddata.isCellData()) {
            double var5 = this.griddata.getDx();
            double var3 = this.griddata.getDy();
            this.xmin = this.griddata.getLeft() - var5 / 2.0D;
            this.xmax = this.griddata.getRight() + var5 / 2.0D;
            this.ymin = this.griddata.getBottom() + var3 / 2.0D;
            this.ymax = this.griddata.getTop() - var3 / 2.0D;
         } else {
            this.xmin = this.griddata.getLeft();
            this.xmax = this.griddata.getRight();
            this.ymin = this.griddata.getBottom();
            this.ymax = this.griddata.getTop();
         }

         this.grid.setMinMax(this.xmin, this.xmax, this.ymin, this.ymax);
      }
   }

   private void drawLine(Graphics2D var1, double[] var2, DrawingPanel var3) {
      var1.setColor(this.colorMap.doubleToColor(var2[2]));
      int var4 = var3.xToPix(var2[0]);
      int var5 = var3.yToPix(var2[1]);
      int var6 = var3.xToPix(var2[0] + var2[3]);
      int var7 = var3.yToPix(var2[1] + var2[4]);
      var1.drawLine(var4, var5, var6, var7);
   }

   static GeneralPath createVectorPath(float var0) {
      float var1 = Math.min(15.0F, 1.0F + var0 / 5.0F);
      GeneralPath var2 = new GeneralPath();
      var2.moveTo(-var0 / 2.0F, 0.0F);
      var2.lineTo(var0 / 2.0F, 0.0F);
      var2.lineTo(var0 / 2.0F - var1, 2.0F * var1 / 3.0F);
      var2.lineTo(var0 / 2.0F, 0.0F);
      var2.lineTo(var0 / 2.0F - var1, -2.0F * var1 / 3.0F);
      return var2;
   }

   static GeneralPath createFilledVectorPath(float var0) {
      float var1 = Math.min(15.0F, 1.0F + var0 / 5.0F);
      GeneralPath var2 = new GeneralPath();
      var2.moveTo(-var0 / 2.0F, 1.0F);
      var2.lineTo(var0 / 2.0F - var1, 1.0F);
      var2.lineTo(var0 / 2.0F - var1, 2.0F * var1 / 3.0F);
      var2.lineTo(var0 / 2.0F, 0.0F);
      var2.lineTo(var0 / 2.0F - var1, -2.0F * var1 / 3.0F);
      var2.lineTo(var0 / 2.0F - var1, -1.0F);
      var2.moveTo(-var0 / 2.0F, -1.0F);
      return var2;
   }

   public double getXMin() {
      return this.xmin;
   }

   public double getXMax() {
      return this.xmax;
   }

   public double getYMin() {
      return this.ymin;
   }

   public double getYMax() {
      return this.ymax;
   }

   public boolean isMeasured() {
      return this.griddata != null;
   }

   public static XML.ObjectLoader getLoader() {
      return new Plot2DLoader() {
         public Object createObject(XMLControl var1) {
            return new VectorPlot((GridData)null);
         }
      };
   }
}
