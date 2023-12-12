package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;
import org.opensourcephysics.display2d.ComplexColorMapper;

public class ComplexDataset extends AbstractTableModel implements Drawable, Measurable {
   static final double PI2 = 6.283185307179586D;
   public static final int AMP_CURVE = 0;
   public static final int RE_IM_CURVE = 1;
   public static final int PHASE_CURVE = 2;
   public static final int PHASE_BAR = 3;
   public static final int PHASE_POST = 4;
   protected double[] xpoints;
   protected double[] re_points;
   protected double[] im_points;
   protected double[] amp_points;
   protected int index;
   private int markerShape = 2;
   private int markerSize = 5;
   private boolean centered = true;
   private boolean showPhase = true;
   private double xmin;
   private double xmax;
   private double ampmin;
   private double ampmax;
   private double remax;
   private double remin;
   private double immax;
   private double immin;
   private boolean sorted = false;
   private boolean connected = true;
   private int initialSize;
   private Color lineColor;
   private GeneralPath ampPath;
   private Trail reTrail;
   private Trail imTrail;
   private String xColumnName;
   private String reColumnName;
   private String imColumnName;
   private int stride;
   private AffineTransform flip;
   private ComplexColorMapper colorMap;
   // $FF: synthetic field
   static Class class$java$lang$Double;

   public ComplexDataset() {
      this.lineColor = Color.black;
      this.reTrail = new Trail();
      this.imTrail = new Trail();
      this.stride = 1;
      this.colorMap = new ComplexColorMapper(1.0D);
      this.reTrail.color = Color.RED;
      this.imTrail.color = Color.BLUE;
      this.initialSize = 10;
      this.xColumnName = "x";
      this.reColumnName = "re";
      this.imColumnName = "im";
      this.ampPath = new GeneralPath();
      this.index = 0;
      this.flip = new AffineTransform(1.0F, 0.0F, 0.0F, -1.0F, 0.0F, 0.0F);
      this.clear();
   }

   public JFrame showLegend() {
      return this.colorMap.showLegend();
   }

   public boolean isMeasured() {
      return this.index >= 1;
   }

   public double getXMin() {
      return this.xmin;
   }

   public double getXMax() {
      return this.xmax;
   }

   public double getYMin() {
      if (this.markerShape == 1) {
         return -this.ampmax;
      } else {
         return !this.centered || this.markerShape != 3 && this.markerShape != 2 ? 0.0D : -this.ampmax / 2.0D;
      }
   }

   public double getYMax() {
      if (this.markerShape == 1) {
         return this.ampmax;
      } else if (!this.centered || this.markerShape != 3 && this.markerShape != 2) {
         return this.markerShape == 4 ? 1.1D * this.ampmax : this.ampmax;
      } else {
         return this.ampmax / 2.0D;
      }
   }

   public double[] getXPoints() {
      double[] var1 = new double[this.index];
      System.arraycopy(this.xpoints, 0, var1, 0, this.index);
      return var1;
   }

   public double[] getYPoints() {
      double[] var1 = new double[this.index];
      System.arraycopy(this.amp_points, 0, var1, 0, this.index);
      return var1;
   }

   public double[][] getPoints() {
      double[][] var1 = new double[this.index][3];

      for(int var2 = 0; var2 < this.index; ++var2) {
         var1[var2] = new double[]{this.xpoints[var2], this.re_points[var2], this.im_points[var2]};
      }

      return var1;
   }

   public void setMarkerShape(int var1) {
      this.markerShape = var1;
   }

   public int getMarkerShape() {
      return this.markerShape;
   }

   public int getMarkerSize() {
      return this.markerSize;
   }

   public void setMarkerSize(int var1) {
      this.markerSize = var1;
   }

   public void setSorted(boolean var1) {
      this.sorted = var1;
      if (this.sorted) {
         this.insertionSort();
      }

   }

   public void setStride(int var1) {
      this.stride = var1;
      this.stride = Math.max(1, this.stride);
   }

   public boolean isSorted() {
      return this.sorted;
   }

   public void setConnected(boolean var1) {
      this.connected = var1;
   }

   public void setCentered(boolean var1) {
      this.centered = var1;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public void setLineColor(Color var1) {
      this.lineColor = var1;
      this.reTrail.color = this.lineColor;
      this.imTrail.color = this.lineColor;
   }

   public void setLineColor(Color var1, Color var2) {
      this.lineColor = var1;
      this.reTrail.color = var1;
      this.imTrail.color = var2;
   }

   public void setXYColumnNames(String var1, String var2, String var3) {
      this.xColumnName = var1;
      this.reColumnName = var2;
      this.imColumnName = var3;
   }

   public void append(double var1, double var3, double var5) {
      if (!Double.isNaN(var1) && !Double.isInfinite(var1) && !Double.isNaN(var3) && !Double.isInfinite(var3) && !Double.isNaN(var5) && !Double.isInfinite(var5)) {
         if (this.index >= this.xpoints.length) {
            this.setCapacity(this.xpoints.length * 2);
         }

         this.xpoints[this.index] = var1;
         this.re_points[this.index] = var3;
         this.im_points[this.index] = var5;
         double var7 = Math.sqrt(var3 * var3 + var5 * var5);
         if (this.index == 0) {
            this.ampPath.moveTo((float)var1, (float)var7);
         } else {
            this.ampPath.lineTo((float)var1, (float)var7);
         }

         this.reTrail.addPoint(var1, var3);
         this.imTrail.addPoint(var1, var5);
         this.xmax = Math.max(var1, this.xmax);
         this.xmin = Math.min(var1, this.xmin);
         this.remin = Math.min(var3, this.remin);
         this.remax = Math.max(var3, this.remax);
         this.immin = Math.min(var5, this.immin);
         this.immax = Math.max(var5, this.immax);
         this.ampmin = Math.min(var7, this.ampmin);
         this.ampmax = Math.max(var7, this.ampmax);
         ++this.index;
         if (this.sorted && this.index > 1 && var1 < this.xpoints[this.index - 2]) {
            this.moveDatum(this.index - 1);
            this.recalculatePath();
         }

      }
   }

   public void append(double[] var1, double[] var2, double[] var3) {
      if (var1 != null) {
         if (var2 != null && var3 != null && var1.length == var2.length && var1.length == var3.length) {
            boolean var4 = false;

            int var5;
            for(var5 = 0; var5 < var1.length; ++var5) {
               if (!Double.isNaN(var1[var5]) && !Double.isInfinite(var1[var5]) && !Double.isNaN(var2[var5]) && !Double.isInfinite(var2[var5]) && !Double.isNaN(var3[var5]) && !Double.isInfinite(var3[var5])) {
                  this.xmax = Math.max(var1[var5], this.xmax);
                  this.xmin = Math.min(var1[var5], this.xmin);
                  this.remin = Math.min(var2[var5], this.remin);
                  this.remax = Math.max(var2[var5], this.remax);
                  this.immin = Math.min(var3[var5], this.immin);
                  this.immax = Math.max(var3[var5], this.immax);
                  double var6 = Math.sqrt(var2[var5] * var2[var5] + var3[var5] * var3[var5]);
                  this.ampmin = Math.min(var6, this.ampmin);
                  this.ampmax = Math.max(var6, this.ampmax);
                  if (this.index == 0 && var5 == 0) {
                     this.ampPath.moveTo((float)var1[var5], (float)var6);
                  } else {
                     this.ampPath.lineTo((float)var1[var5], (float)var6);
                  }

                  this.reTrail.addPoint(var1[var5], var2[var5]);
                  this.imTrail.addPoint(var1[var5], var3[var5]);
               } else {
                  var4 = true;
               }
            }

            var5 = var1.length;
            int var8 = this.xpoints.length - this.index;
            if (var5 > var8) {
               this.setCapacity(this.xpoints.length + var5);
            }

            System.arraycopy(var1, 0, this.xpoints, this.index, var5);
            System.arraycopy(var2, 0, this.re_points, this.index, var5);
            System.arraycopy(var3, 0, this.im_points, this.index, var5);
            this.index += var5;
            if (var4) {
               this.cleanBadData();
            }

            if (this.sorted) {
               this.insertionSort();
            }

         } else {
            throw new IllegalArgumentException("Array lenghts must be equal to append data.");
         }
      }
   }

   public void append(double[] var1, double[] var2) {
      if (var1 != null) {
         if (var2 != null && 2 * var1.length == var2.length) {
            boolean var3 = false;
            int var4 = var1.length;
            int var5 = this.xpoints.length - this.index;
            if (var4 > var5) {
               this.setCapacity(this.xpoints.length + var4);
            }

            for(int var6 = 0; var6 < var1.length; ++var6) {
               if (!Double.isNaN(var1[var6]) && !Double.isInfinite(var1[var6]) && !Double.isNaN(var2[2 * var6]) && !Double.isInfinite(var2[2 * var6]) && !Double.isNaN(var2[2 * var6 + 1]) && !Double.isInfinite(var2[2 * var6 + 1])) {
                  this.xmax = Math.max(var1[var6], this.xmax);
                  this.xmin = Math.min(var1[var6], this.xmin);
                  this.remin = Math.min(var2[2 * var6], this.remin);
                  this.remax = Math.max(var2[2 * var6], this.remax);
                  this.immin = Math.min(var2[2 * var6 + 1], this.immin);
                  this.immax = Math.max(var2[2 * var6 + 1], this.immax);
                  double var7 = Math.sqrt(var2[2 * var6] * var2[2 * var6] + var2[2 * var6 + 1] * var2[2 * var6 + 1]);
                  this.ampmin = Math.min(var7, this.ampmin);
                  this.ampmax = Math.max(var7, this.ampmax);
                  this.xpoints[this.index + var6] = var1[var6];
                  this.re_points[this.index + var6] = var2[2 * var6];
                  this.im_points[this.index + var6] = var2[2 * var6 + 1];
                  if (this.index == 0 && var6 == 0) {
                     this.ampPath.moveTo((float)var1[var6], (float)var7);
                  } else {
                     this.ampPath.lineTo((float)var1[var6], (float)var7);
                  }

                  this.reTrail.addPoint(var1[var6], var2[2 * var6]);
                  this.imTrail.addPoint(var1[var6], var2[2 * var6 + 1]);
               } else {
                  var3 = true;
               }
            }

            this.index += var4;
            if (var3) {
               this.cleanBadData();
            }

            if (this.sorted) {
               this.insertionSort();
            }

         } else {
            throw new IllegalArgumentException("Length of z array must be twice the length of the x array.");
         }
      }
   }

   private void cleanBadData() {
      for(int var1 = 0; var1 < this.index; ++var1) {
         if (Double.isNaN(this.xpoints[var1]) || Double.isInfinite(this.xpoints[var1]) || Double.isNaN(this.re_points[var1]) || Double.isInfinite(this.re_points[var1]) || Double.isNaN(this.im_points[var1]) || Double.isInfinite(this.im_points[var1])) {
            if (this.index == 1 || var1 == this.index - 1) {
               --this.index;
               break;
            }

            System.arraycopy(this.xpoints, var1 + 1, this.xpoints, var1, this.index - var1 - 1);
            System.arraycopy(this.re_points, var1 + 1, this.re_points, var1, this.index - var1 - 1);
            System.arraycopy(this.im_points, var1 + 1, this.im_points, var1, this.index - var1 - 1);
            --this.index;
         }
      }

   }

   private void setCapacity(int var1) {
      double[] var2 = this.xpoints;
      this.xpoints = new double[var1];
      System.arraycopy(var2, 0, this.xpoints, 0, var2.length);
      double[] var3 = this.re_points;
      this.re_points = new double[var1];
      System.arraycopy(var3, 0, this.re_points, 0, var3.length);
      double[] var4 = this.im_points;
      this.im_points = new double[var1];
      System.arraycopy(var4, 0, this.im_points, 0, var4.length);
      double[] var5 = this.amp_points;
      this.amp_points = new double[var1];
      System.arraycopy(var5, 0, this.amp_points, 0, var5.length);
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      Graphics2D var3 = (Graphics2D)var2;
      switch(this.markerShape) {
      case 0:
         this.drawLinePlot(var1, var3);
         break;
      case 1:
         this.drawReImPlot(var1, var3);
         break;
      case 2:
         this.drawPhaseCurve(var1, var3);
         break;
      case 3:
         this.drawPhaseBars(var1, var3);
         break;
      case 4:
         this.drawPhasePosts(var1, var3);
      }

   }

   public void clear() {
      this.index = 0;
      this.xpoints = new double[this.initialSize];
      this.re_points = new double[this.initialSize];
      this.im_points = new double[this.initialSize];
      this.amp_points = new double[this.initialSize];
      this.ampPath.reset();
      this.reTrail.clear();
      this.imTrail.clear();
      this.resetXYMinMax();
   }

   public String toString() {
      if (this.index == 0) {
         return "Dataset empty.";
      } else {
         String var1 = this.xpoints[0] + "\t" + this.re_points[0] + "\t" + this.im_points[0] + "\n";
         StringBuffer var2 = new StringBuffer(this.index * var1.length());

         for(int var3 = 0; var3 < this.index; ++var3) {
            var2.append(this.xpoints[var3]);
            var2.append('\t');
            var2.append(this.re_points[var3]);
            var2.append('\t');
            var2.append(this.im_points[var3]);
            var2.append('\n');
         }

         return var2.toString();
      }
   }

   public int getColumnCount() {
      return 3;
   }

   public int getRowCount() {
      return (this.index + this.stride - 1) / this.stride;
   }

   public String getColumnName(int var1) {
      switch(var1) {
      case 0:
         return this.xColumnName;
      case 1:
         return this.reColumnName;
      case 2:
         return this.imColumnName;
      default:
         return "";
      }
   }

   public Object getValueAt(int var1, int var2) {
      var1 *= this.stride;
      switch(var2) {
      case 0:
         return new Double(this.xpoints[var1]);
      case 1:
         return new Double(this.re_points[var1]);
      case 2:
         return new Double(this.im_points[var1]);
      default:
         return new Double(0.0D);
      }
   }

   public Class getColumnClass(int var1) {
      return class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double;
   }

   private void resetXYMinMax() {
      this.xmin = Double.MAX_VALUE;
      this.xmax = -1.7976931348623157E308D;
      this.remax = -1.7976931348623157E308D;
      this.remin = Double.MAX_VALUE;
      this.immax = -1.7976931348623157E308D;
      this.immin = Double.MAX_VALUE;
      this.ampmax = -1.7976931348623157E308D;
      this.ampmin = Double.MAX_VALUE;

      for(int var1 = 0; var1 < this.index; ++var1) {
         this.xmin = Math.min(this.xpoints[var1], this.xmin);
         this.xmax = Math.max(this.xpoints[var1], this.xmax);
         this.remax = Math.max(this.re_points[var1], this.remax);
         this.remin = Math.min(this.re_points[var1], this.remin);
         this.immax = Math.max(this.im_points[var1], this.immax);
         this.immin = Math.min(this.im_points[var1], this.immin);
         this.ampmax = Math.max(this.amp_points[var1], this.ampmax);
         this.ampmin = Math.min(this.amp_points[var1], this.ampmin);
      }

   }

   protected void insertionSort() {
      boolean var1 = false;
      if (this.index >= 2) {
         for(int var2 = 1; var2 < this.index; ++var2) {
            if (this.xpoints[var2] < this.xpoints[var2 - 1]) {
               var1 = true;
               this.moveDatum(var2);
            }
         }

         if (var1) {
            this.recalculatePath();
         }

      }
   }

   protected void recalculatePath() {
      this.ampPath.reset();
      if (this.index >= 1) {
         float var1 = (float)Math.sqrt(this.re_points[0] * this.re_points[0] + this.im_points[0] * this.im_points[0]);
         this.ampPath.moveTo((float)this.xpoints[0], var1);

         for(int var2 = 1; var2 < this.index; ++var2) {
            var1 = (float)Math.sqrt(this.re_points[var2] * this.re_points[var2] + this.im_points[var2] * this.im_points[var2]);
            this.ampPath.lineTo((float)this.xpoints[var2], var1);
         }

      }
   }

   protected void moveDatum(int var1) {
      if (var1 >= 1) {
         double var2 = this.xpoints[var1];
         double var4 = this.re_points[var1];
         double var6 = this.im_points[var1];

         for(int var8 = 0; var8 < this.index; ++var8) {
            if (this.xpoints[var8] > var2) {
               System.arraycopy(this.xpoints, var8, this.xpoints, var8 + 1, var1 - var8);
               this.xpoints[var8] = var2;
               System.arraycopy(this.re_points, var8, this.re_points, var8 + 1, var1 - var8);
               this.re_points[var8] = var4;
               System.arraycopy(this.im_points, var8, this.im_points, var8 + 1, var1 - var8);
               this.im_points[var8] = var6;
               return;
            }
         }

      }
   }

   protected void drawLinePlot(DrawingPanel var1, Graphics2D var2) {
      AffineTransform var3 = (AffineTransform)((AffineTransform)var1.getPixelTransform().clone());
      Shape var4 = this.ampPath.createTransformedShape(var3);
      var2.setColor(this.lineColor);
      var2.draw(var4);
      if (this.showPhase) {
         var3.concatenate(this.flip);
         var4 = this.ampPath.createTransformedShape(var3);
         var2.draw(var4);
      }

   }

   protected void drawReImPlot(DrawingPanel var1, Graphics2D var2) {
      this.reTrail.draw(var1, var2);
      this.imTrail.draw(var1, var2);
   }

   protected void drawPhaseCurve(DrawingPanel var1, Graphics2D var2) {
      double[] var3 = this.xpoints;
      double[] var4 = this.re_points;
      double[] var5 = this.im_points;
      int var6 = this.index;
      if (var6 >= 1) {
         if (var3.length >= var6 && var3.length == var4.length && var3.length == var5.length) {
            int var7 = var1.yToPix(0.0D);
            int[] var8 = new int[4];
            int[] var9 = new int[4];
            var8[2] = var1.xToPix(var3[0]);
            double var10 = Math.sqrt(var4[0] * var4[0] + var5[0] * var5[0]);
            var9[3] = var1.yToPix(-var10);
            var9[2] = var1.yToPix(var10);
            double var12 = var4[0];
            double var14 = var5[0];

            for(int var16 = 0; var16 < var6; ++var16) {
               double var17 = var4[var16];
               double var19 = var5[var16];
               double var21 = Math.sqrt(var17 * var17 + var19 * var19);
               if (var21 > 0.0D) {
                  var2.setColor(this.colorMap.complexToColor((var12 + var17) / 2.0D, (var19 + var14) / 2.0D));
               }

               var8[0] = var1.xToPix(var3[var16]);
               if (this.centered) {
                  var9[0] = var1.yToPix(-var21 / 2.0D);
                  var9[1] = var1.yToPix(var21 / 2.0D);
               } else {
                  var9[0] = var7;
                  var9[1] = var1.yToPix(var21);
               }

               var8[1] = var8[0];
               var8[3] = var8[2];
               var2.fillPolygon(var8, var9, 4);
               var8[2] = var8[0];
               var9[3] = var9[0];
               var9[2] = var9[1];
               var14 = var19;
               var12 = var17;
            }

         }
      }
   }

   protected void drawPhaseBars(DrawingPanel var1, Graphics2D var2) {
      if (this.index >= 1) {
         double[] var3 = this.xpoints;
         double[] var4 = this.re_points;
         double[] var5 = this.im_points;
         if (var3.length >= this.index && var3.length == var4.length && var3.length == var5.length) {
            int var6 = (int)(0.5D + (double)(var1.xToPix(this.xmax) - var1.xToPix(this.xmin)) / (2.0D * (double)(this.index - 1)));
            var6 = Math.min(this.markerSize, var6);
            int var7 = var1.yToPix(0.0D);

            for(int var8 = 0; var8 < this.index; ++var8) {
               double var9 = var4[var8];
               double var11 = var5[var8];
               double var13 = Math.sqrt(var9 * var9 + var11 * var11);
               var2.setColor(this.colorMap.complexToColor(var9, var11));
               int var15 = var1.xToPix(var3[var8]);
               int var16 = Math.abs(var7 - var1.yToPix(var13));
               if (this.centered) {
                  var2.fillRect(var15 - var6, var7 - var16 / 2, 2 * var6 + 1, var16);
               } else {
                  var2.fillRect(var15 - var6, var7 - var16, 2 * var6 + 1, var16);
               }
            }

         }
      }
   }

   protected void drawPhasePosts(DrawingPanel var1, Graphics2D var2) {
      if (this.index >= 1) {
         double[] var3 = this.xpoints;
         double[] var4 = this.re_points;
         double[] var5 = this.im_points;
         if (var3.length >= this.index && var3.length == var4.length && var3.length == var5.length) {
            int var6 = (int)(0.5D + (double)(var1.xToPix(this.xmax) - var1.xToPix(this.xmin)) / (2.0D * (double)(this.index - 1)));
            var6 = Math.min(this.markerSize, var6);

            for(int var7 = 0; var7 < this.index; ++var7) {
               double var8 = var4[var7];
               double var10 = var5[var7];
               double var12 = Math.sqrt(var8 * var8 + var10 * var10);
               this.drawPost(var1, var2, var3[var7], var12, var6, this.colorMap.complexToColor(var8, var10));
            }

         }
      }
   }

   private void drawPost(DrawingPanel var1, Graphics2D var2, double var3, double var5, int var7, Color var8) {
      Color var9 = Color.BLACK;
      int var10 = var1.xToPix(var3);
      int var11 = var1.yToPix(var5);
      int var12 = var7 * 2 + 1;
      int var13 = Math.min(var1.yToPix(0.0D), var1.yToPix(var1.getYMin()));
      java.awt.geom.Rectangle2D.Double var14 = new java.awt.geom.Rectangle2D.Double((double)(var10 - var7), (double)(var11 - var7), (double)var12, (double)var12);
      var2.setColor(var9);
      var2.drawLine(var10, var11, var10, var13);
      var2.setColor(var8);
      var2.fill(var14);
      var2.setColor(var9);
      var2.draw(var14);
   }

   public static XML.ObjectLoader getLoader() {
      return new ComplexDataset.Loader();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         ComplexDataset var3 = (ComplexDataset)var2;
         var1.setValue("points", var3.getPoints());
         var1.setValue("marker_shape", var3.getMarkerShape());
         var1.setValue("marker_size", var3.getMarkerSize());
         var1.setValue("sorted", var3.isSorted());
         var1.setValue("connected", var3.isConnected());
         var1.setValue("x_name", var3.xColumnName);
         var1.setValue("re_name", var3.reColumnName);
         var1.setValue("im_name", var3.imColumnName);
         var1.setValue("line_color", var3.lineColor);
      }

      public Object createObject(XMLControl var1) {
         return new ComplexDataset();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         ComplexDataset var3 = (ComplexDataset)var2;
         double[][] var4 = (double[][])((double[][])var1.getObject("points"));
         if (var4 != null && var4[0] != null) {
            var3.clear();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               var3.append(var4[var5][0], var4[var5][1], var4[var5][2]);
            }
         }

         double[] var8 = (double[])((double[])var1.getObject("x_points"));
         double[] var6 = (double[])((double[])var1.getObject("y_points"));
         if (var8 != null && var6 != null) {
            var3.clear();
            var3.append(var8, var6);
         }

         if (var1.getPropertyNames().contains("marker_shape")) {
            var3.setMarkerShape(var1.getInt("marker_shape"));
         }

         if (var1.getPropertyNames().contains("marker_size")) {
            var3.setMarkerSize(var1.getInt("marker_size"));
         }

         var3.setSorted(var1.getBoolean("sorted"));
         var3.setConnected(var1.getBoolean("connected"));
         var3.xColumnName = var1.getString("x_name");
         var3.reColumnName = var1.getString("re_name");
         var3.imColumnName = var1.getString("im_name");
         Color var7 = (Color)var1.getObject("line_color");
         if (var7 != null) {
            var3.lineColor = var7;
         }

         return var2;
      }

      // $FF: synthetic method
      Loader(Object var1) {
         this();
      }
   }
}
