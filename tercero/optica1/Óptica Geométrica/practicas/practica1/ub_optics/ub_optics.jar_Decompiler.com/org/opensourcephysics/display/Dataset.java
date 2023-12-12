package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.table.AbstractTableModel;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class Dataset extends AbstractTableModel implements Measurable {
   protected int datasetID;
   public static final int NO_MARKER = 0;
   public static final int CIRCLE = 1;
   public static final int SQUARE = 2;
   public static final int AREA = 5;
   public static final int PIXEL = 6;
   public static final int BAR = 7;
   public static final int POST = 8;
   public static final int CUSTOM = -1;
   protected double[] xpoints;
   protected double[] ypoints;
   protected GeneralPath generalPath;
   protected double xmax;
   protected double ymax;
   protected double xmin;
   protected double ymin;
   protected int index;
   protected boolean sorted;
   private int initialSize;
   private int markerSize;
   private int markerShape;
   private Color lineColor;
   private Color fillColor;
   private Color edgeColor;
   private Color errorBarColor;
   private boolean connected;
   private String name;
   private String xColumnName;
   private String yColumnName;
   private boolean[] visible;
   private int stride;
   protected int maxPoints;
   protected ArrayList errorBars;
   protected Shape customMarker;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$org$opensourcephysics$display$Dataset;

   public Dataset() {
      this(Color.black, Color.black, false);
   }

   public Dataset(Color var1) {
      this(var1, Color.black, false);
   }

   public Dataset(Color var1, Color var2, boolean var3) {
      this.datasetID = this.hashCode();
      this.sorted = false;
      this.markerSize = 2;
      this.markerShape = 2;
      this.name = null;
      this.visible = new boolean[2];
      this.stride = 1;
      this.maxPoints = 16384;
      this.errorBars = new ArrayList();
      this.customMarker = new Double((double)(-this.markerSize / 2), (double)(-this.markerSize / 2), (double)this.markerSize, (double)this.markerSize);
      this.fillColor = var1;
      this.edgeColor = var1;
      this.errorBarColor = var1;
      this.lineColor = var2;
      this.connected = var3;
      this.markerSize = 2;
      this.initialSize = 10;
      this.xColumnName = "x";
      this.yColumnName = "y";
      this.generalPath = new GeneralPath();
      this.index = 0;
      Arrays.fill(this.visible, true);
      this.clear();
   }

   public void setID(int var1) {
      this.datasetID = var1;
   }

   public int getID() {
      return this.datasetID;
   }

   public void setSorted(boolean var1) {
      this.sorted = var1;
      if (this.sorted) {
         this.insertionSort();
      }

   }

   public void setConnected(boolean var1) {
      this.connected = var1;
      if (this.connected) {
         this.recalculatePath();
      }

   }

   public void setMarkerColor(Color var1) {
      this.fillColor = var1;
      this.edgeColor = var1;
      this.errorBarColor = var1;
   }

   public void setMarkerColor(Color var1, Color var2) {
      this.fillColor = var1;
      this.edgeColor = var2;
      this.errorBarColor = var2;
   }

   public void setMarkerColor(Color var1, Color var2, Color var3) {
      this.fillColor = var1;
      this.edgeColor = var2;
      this.errorBarColor = var3;
   }

   public Color getFillColor() {
      return this.fillColor;
   }

   public Color getEdgeColor() {
      return this.edgeColor;
   }

   public Color getLineColor() {
      return this.lineColor;
   }

   public void setCustomMarker(Shape var1) {
      this.customMarker = var1;
      if (this.customMarker == null) {
         this.markerShape = 2;
         this.customMarker = new Double((double)(-this.markerSize / 2), (double)(-this.markerSize / 2), (double)this.markerSize, (double)this.markerSize);
      } else {
         this.markerShape = -1;
      }

   }

   public void setMarkerShape(int var1) {
      this.markerShape = var1;
   }

   public int getMarkerShape() {
      return this.markerShape;
   }

   public void setMarkerSize(int var1) {
      this.markerSize = var1;
   }

   public void setMaximumPoints(int var1) {
      this.maxPoints = var1;
   }

   public int getMarkerSize() {
      return this.markerSize;
   }

   public void setLineColor(Color var1) {
      this.lineColor = var1;
   }

   public void setXYColumnNames(String var1, String var2) {
      this.xColumnName = GUIUtils.parseTeX(var1);
      this.yColumnName = GUIUtils.parseTeX(var2);
   }

   public void setXYColumnNames(String var1, String var2, String var3) {
      this.setXYColumnNames(var1, var2);
      this.name = var3;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public String getName() {
      return this.name;
   }

   public boolean isMeasured() {
      return this.ymin < java.lang.Double.MAX_VALUE;
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

   public double[][] getPoints() {
      double[][] var1 = new double[this.index][2];

      for(int var2 = 0; var2 < this.index; ++var2) {
         var1[var2] = new double[]{this.xpoints[var2], this.ypoints[var2]};
      }

      return var1;
   }

   public double[] getXPoints() {
      double[] var1 = new double[this.index];
      System.arraycopy(this.xpoints, 0, var1, 0, this.index);
      return var1;
   }

   public double[] getYPoints() {
      double[] var1 = new double[this.index];
      System.arraycopy(this.ypoints, 0, var1, 0, this.index);
      return var1;
   }

   public double[] getValidXPoints() {
      return this.getValidPoints(this.getXPoints());
   }

   public double[] getValidYPoints() {
      return this.getValidPoints(this.getYPoints());
   }

   public boolean isSorted() {
      return this.sorted;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public int getColumnCount() {
      return countColumnsVisible(this.visible);
   }

   public int getIndex() {
      return this.index;
   }

   public int getRowCount() {
      return (this.index + this.stride - 1) / this.stride;
   }

   public String getColumnName(int var1) {
      var1 = convertTableColumnIndex(this.visible, var1);
      return var1 == 0 ? this.xColumnName : this.yColumnName;
   }

   public Object getValueAt(int var1, int var2) {
      var2 = convertTableColumnIndex(this.visible, var2);
      var1 *= this.stride;
      if (var2 == 0) {
         return new java.lang.Double(this.xpoints[var1]);
      } else {
         return java.lang.Double.isNaN(this.ypoints[var1]) ? null : new java.lang.Double(this.ypoints[var1]);
      }
   }

   public Class getColumnClass(int var1) {
      return class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double;
   }

   public void append(double var1, double var3, double var5, double var7) {
      this.errorBars.add(new Dataset.ErrorBar(var1, var3, var5, var7));
      this.append(var1, var3);
   }

   public void append(double var1, double var3) {
      if (!java.lang.Double.isNaN(var1) && !java.lang.Double.isInfinite(var1) && !java.lang.Double.isInfinite(var3)) {
         if (this.index >= this.xpoints.length) {
            this.increaseCapacity(this.xpoints.length * 2);
         }

         this.xpoints[this.index] = var1;
         this.ypoints[this.index] = var3;
         if (!java.lang.Double.isNaN(var3)) {
            Point2D var5 = this.generalPath.getCurrentPoint();
            if (var5 == null) {
               this.generalPath.moveTo((float)var1, (float)var3);
            } else {
               this.generalPath.lineTo((float)var1, (float)var3);
            }

            this.ymax = Math.max(var3, this.ymax);
            this.ymin = Math.min(var3, this.ymin);
         }

         this.xmax = Math.max(var1, this.xmax);
         this.xmin = Math.min(var1, this.xmin);
         ++this.index;
         if (this.sorted && this.index > 1 && var1 < this.xpoints[this.index - 2]) {
            this.moveDatum(this.index - 1);
            this.recalculatePath();
         }

      }
   }

   public void append(double[] var1, double[] var2, double[] var3, double[] var4) {
      int var5 = 0;

      for(int var6 = var1.length; var5 < var6; ++var5) {
         this.errorBars.add(new Dataset.ErrorBar(var1[var5], var2[var5], var3[var5], var4[var5]));
      }

      this.append(var1, var2);
   }

   public void append(double[] var1, double[] var2) {
      boolean var3 = false;

      int var4;
      for(var4 = 0; var4 < var1.length; ++var4) {
         if (!java.lang.Double.isNaN(var1[var4]) && !java.lang.Double.isInfinite(var1[var4]) && !java.lang.Double.isInfinite(var2[var4])) {
            this.xmax = Math.max(var1[var4], this.xmax);
            this.xmin = Math.min(var1[var4], this.xmin);
            if (!java.lang.Double.isNaN(var2[var4])) {
               this.ymax = Math.max(var2[var4], this.ymax);
               this.ymin = Math.min(var2[var4], this.ymin);
               Point2D var5 = this.generalPath.getCurrentPoint();
               if (var5 == null) {
                  this.generalPath.moveTo((float)var1[var4], (float)var2[var4]);
               } else {
                  this.generalPath.lineTo((float)var1[var4], (float)var2[var4]);
               }
            }
         } else {
            var3 = true;
         }
      }

      var4 = var1.length;
      int var6 = this.xpoints.length - this.index;
      if (var4 > var6) {
         this.increaseCapacity(this.xpoints.length + var4);
      }

      System.arraycopy(var1, 0, this.xpoints, this.index, var4);
      System.arraycopy(var2, 0, this.ypoints, this.index, var4);
      this.index += var4;
      if (var3) {
         this.removeBadData();
      }

      if (this.sorted) {
         this.insertionSort();
      }

   }

   public void read(String var1) {
      try {
         BufferedReader var2 = new BufferedReader(new FileReader(var1));

         String var3;
         while((var3 = var2.readLine()) != null) {
            var3 = var3.trim();
            if (var3.charAt(0) != '#') {
               StringTokenizer var4 = new StringTokenizer(var3, "\t");
               switch(var4.countTokens()) {
               case 0:
                  break;
               case 2:
                  this.append(java.lang.Double.parseDouble(var4.nextToken()), java.lang.Double.parseDouble(var4.nextToken()));
                  break;
               default:
                  throw new IOException();
               }
            }
         }
      } catch (FileNotFoundException var5) {
         System.err.println("File " + var1 + " not found.");
      } catch (IOException var6) {
         System.err.println("Error reading file " + var1);
      } catch (NumberFormatException var7) {
         System.err.println("Error reading file " + var1);
      }

   }

   public void write(String var1) {
      try {
         PrintWriter var2 = new PrintWriter(new BufferedWriter(new FileWriter(var1)));

         for(int var3 = 0; var3 < this.index; ++var3) {
            var2.println(this.xpoints[var3] + "\t" + this.ypoints[var3]);
         }

         var2.close();
      } catch (FileNotFoundException var4) {
         System.err.println("File " + var1 + " not found.");
      } catch (IOException var5) {
         System.err.println("Error writing file " + var1);
      }

   }

   public void draw(DrawingPanel var1, Graphics var2) {
      try {
         Graphics2D var3 = (Graphics2D)var2;
         if (this.markerShape != 0) {
            this.drawScatterPlot(var1, var3);
         }

         if (this.connected) {
            this.drawLinePlot(var1, var3);
         }
      } catch (Exception var4) {
      }

   }

   public void clear() {
      this.index = 0;
      this.xpoints = new double[this.initialSize];
      this.ypoints = new double[this.initialSize];
      this.generalPath.reset();
      this.errorBars.clear();
      this.resetXYMinMax();
   }

   public String toString() {
      if (this.index == 0) {
         return "No data in dataset.";
      } else {
         String var1 = this.xpoints[0] + " " + this.ypoints[0] + "\n";
         StringBuffer var2 = new StringBuffer(this.index * var1.length());

         for(int var3 = 0; var3 < this.index; ++var3) {
            var2.append(this.xpoints[var3]);
            String var4 = "\n";

            try {
               var4 = System.getProperty("line.separator", "\n");
            } catch (SecurityException var6) {
            }

            var2.append(" ");
            if (java.lang.Double.isNaN(this.ypoints[var3])) {
               var2.append("null");
            } else {
               var2.append(this.ypoints[var3]);
            }

            var2.append(var4);
         }

         return var2.toString();
      }
   }

   public static int countColumnsVisible(boolean[] var0) {
      int var1 = 0;

      for(int var2 = 0; var2 < var0.length; ++var2) {
         if (var0[var2]) {
            ++var1;
         }
      }

      return var1;
   }

   public static int convertTableColumnIndex(boolean[] var0, int var1) {
      if (var1 == 0 && !var0[0]) {
         ++var1;
      } else if (var1 == 1 && !var0[1]) {
         --var1;
      }

      return var1;
   }

   public void setXColumnVisible(boolean var1) {
      this.visible[0] = var1;
   }

   public void setYColumnVisible(boolean var1) {
      this.visible[1] = var1;
   }

   public void setStride(int var1) {
      this.stride = var1;
   }

   public boolean isXColumnVisible() {
      return this.visible[0];
   }

   public boolean isYColumnVisible() {
      return this.visible[1];
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
      this.generalPath.reset();
      if (this.index >= 1) {
         int var1;
         for(var1 = 0; var1 < this.index; ++var1) {
            if (!java.lang.Double.isNaN(this.ypoints[var1])) {
               this.generalPath.moveTo((float)this.xpoints[var1], (float)this.ypoints[var1]);
               break;
            }
         }

         for(int var2 = var1 + 1; var2 < this.index; ++var2) {
            if (!java.lang.Double.isNaN(this.ypoints[var2])) {
               this.generalPath.lineTo((float)this.xpoints[var2], (float)this.ypoints[var2]);
            }
         }

      }
   }

   protected void moveDatum(int var1) {
      if (var1 >= 1) {
         double var2 = this.xpoints[var1];
         double var4 = this.ypoints[var1];

         for(int var6 = 0; var6 < this.index; ++var6) {
            if (this.xpoints[var6] > var2) {
               System.arraycopy(this.xpoints, var6, this.xpoints, var6 + 1, var1 - var6);
               this.xpoints[var6] = var2;
               System.arraycopy(this.ypoints, var6, this.ypoints, var6 + 1, var1 - var6);
               this.ypoints[var6] = var4;
               return;
            }
         }

      }
   }

   protected void drawLinePlot(DrawingPanel var1, Graphics2D var2) {
      boolean var3 = true;

      for(int var4 = 0; var4 < this.index; ++var4) {
         var3 = java.lang.Double.isNaN(this.ypoints[var4]);
         if (!var3) {
            break;
         }
      }

      if (!var3) {
         AffineTransform var6 = var1.getPixelTransform();
         Shape var5 = this.generalPath.createTransformedShape(var6);
         var2.setColor(this.lineColor);
         var2.draw(var5);
      }
   }

   protected void drawFilledPlot(DrawingPanel var1, Graphics2D var2) {
      boolean var3 = true;

      for(int var4 = 0; var4 < this.index; ++var4) {
         var3 = java.lang.Double.isNaN(this.ypoints[var4]);
         if (!var3) {
            break;
         }
      }

      if (!var3) {
         AffineTransform var6 = var1.getPixelTransform();
         Shape var5 = this.generalPath.createTransformedShape(var6);
         var2.setColor(this.fillColor);
         var2.fill(var5);
         var2.setColor(this.edgeColor);
         var2.draw(var5);
      }
   }

   protected void drawScatterPlot(DrawingPanel var1, Graphics2D var2) {
      if (this.markerShape == 5) {
         this.drawFilledPlot(var1, var2);
      } else {
         double var3 = 0.0D;
         double var5 = 0.0D;
         Double var7 = null;
         int var8 = this.markerSize * 2 + 1;
         Shape var9 = var2.getClip();
         var2.setClip(var1.leftGutter - this.markerSize - 1, var1.topGutter - this.markerSize - 1, var1.getWidth() - var1.leftGutter - var1.rightGutter + 2 + 2 * this.markerSize, var1.getHeight() - var1.bottomGutter - var1.topGutter + 2 + 2 * this.markerSize);
         Rectangle var10 = var1.getViewRect();
         if (var10 != null) {
            var2.clipRect(var10.x, var10.y, var10.x + var10.width, var10.y + var10.height);
         }

         for(int var11 = 0; var11 < this.index; ++var11) {
            if (!java.lang.Double.isNaN(this.ypoints[var11])) {
               var3 = (double)var1.xToPix(this.xpoints[var11]);
               var5 = (double)var1.yToPix(this.ypoints[var11]);
               double var12;
               switch(this.markerShape) {
               case -1:
                  Shape var16 = AffineTransform.getTranslateInstance(var3, var5).createTransformedShape(this.customMarker);
                  var2.setColor(this.fillColor);
                  var2.fill(var16);
                  if (this.edgeColor != this.fillColor) {
                     var2.setColor(this.edgeColor);
                     var2.draw(var16);
                  }
                  break;
               case 0:
               case 3:
               case 4:
               case 5:
               default:
                  var7 = new Double(var3 - (double)this.markerSize, var5 - (double)this.markerSize, (double)var8, (double)var8);
                  var2.setColor(this.fillColor);
                  var2.fill(var7);
                  if (this.edgeColor != this.fillColor) {
                     var2.setColor(this.edgeColor);
                     var2.draw(var7);
                  }
                  break;
               case 1:
                  java.awt.geom.Ellipse2D.Double var17 = new java.awt.geom.Ellipse2D.Double(var3 - (double)this.markerSize, var5 - (double)this.markerSize, (double)var8, (double)var8);
                  var2.setColor(this.fillColor);
                  var2.fill(var17);
                  if (this.edgeColor != this.fillColor) {
                     var2.setColor(this.edgeColor);
                     var2.draw(var17);
                  }
                  break;
               case 2:
                  var7 = new Double(var3 - (double)this.markerSize, var5 - (double)this.markerSize, (double)var8, (double)var8);
                  var2.setColor(this.fillColor);
                  var2.fill(var7);
                  if (this.edgeColor != this.fillColor) {
                     var2.setColor(this.edgeColor);
                     var2.draw(var7);
                  }
                  break;
               case 6:
                  var7 = new Double(var3, var5, 0.0D, 0.0D);
                  var2.draw(var7);
                  break;
               case 7:
                  var12 = (double)Math.min(var1.yToPix(0.0D), var1.yToPix(var1.getYMin()));
                  double var14 = var12 - var5;
                  if (var14 > 0.0D) {
                     var7 = new Double(var3 - (double)this.markerSize, var5, (double)var8, var14);
                  } else {
                     var7 = new Double(var3 - (double)this.markerSize, var12, (double)var8, -var14);
                  }

                  var2.setColor(this.fillColor);
                  var2.fill(var7);
                  if (this.edgeColor != this.fillColor) {
                     var2.setColor(this.edgeColor);
                     var2.draw(var7);
                  }
                  break;
               case 8:
                  var12 = (double)Math.min(var1.yToPix(0.0D), var1.yToPix(var1.getYMin()));
                  var7 = new Double(var3 - (double)this.markerSize, var5 - (double)this.markerSize, (double)var8, (double)var8);
                  var2.setColor(this.edgeColor);
                  var2.drawLine((int)var3, (int)var5, (int)var3, (int)var12);
                  var2.setColor(this.fillColor);
                  var2.fill(var7);
                  if (this.edgeColor != this.fillColor) {
                     var2.setColor(this.edgeColor);
                     var2.draw(var7);
                  }
               }
            }
         }

         Iterator var18 = this.errorBars.iterator();

         while(var18.hasNext()) {
            ((Dataset.ErrorBar)var18.next()).draw(var1, var2);
         }

         var2.setClip(var9);
      }
   }

   private void removeBadData() {
      for(int var1 = 0; var1 < this.index; ++var1) {
         if (java.lang.Double.isNaN(this.xpoints[var1]) || java.lang.Double.isInfinite(this.xpoints[var1]) || java.lang.Double.isInfinite(this.ypoints[var1])) {
            if (this.index == 1 || var1 == this.index - 1) {
               --this.index;
               break;
            }

            System.arraycopy(this.xpoints, var1 + 1, this.xpoints, var1, this.index - var1 - 1);
            System.arraycopy(this.ypoints, var1 + 1, this.ypoints, var1, this.index - var1 - 1);
            --this.index;
         }
      }

   }

   private synchronized void increaseCapacity(int var1) {
      int var2 = var1 - this.xpoints.length;
      var1 = Math.min(var1, this.maxPoints);
      int var3 = Math.min(this.index, 3 * var1 / 4);
      var3 = Math.min(var3, var1 - var2);
      double[] var4 = this.xpoints;
      this.xpoints = new double[var1];
      System.arraycopy(var4, this.index - var3, this.xpoints, 0, var3);
      double[] var5 = this.ypoints;
      this.ypoints = new double[var1];
      System.arraycopy(var5, this.index - var3, this.ypoints, 0, var3);
      if (this.index != var3) {
         this.index = var3;
         this.resetXYMinMax();
         this.recalculatePath();
      }

      this.index = var3;
   }

   private void resetXYMinMax() {
      this.xmax = -1.7976931348623157E308D;
      this.ymax = -1.7976931348623157E308D;
      this.xmin = java.lang.Double.MAX_VALUE;
      this.ymin = java.lang.Double.MAX_VALUE;

      for(int var1 = 0; var1 < this.index; ++var1) {
         if (!java.lang.Double.isNaN(this.xpoints[var1]) && !java.lang.Double.isInfinite(this.xpoints[var1]) && !java.lang.Double.isInfinite(this.ypoints[var1])) {
            this.xmax = Math.max(this.xpoints[var1], this.xmax);
            this.xmin = Math.min(this.xpoints[var1], this.xmin);
            if (!java.lang.Double.isNaN(this.ypoints[var1])) {
               this.ymax = Math.max(this.ypoints[var1], this.ymax);
               this.ymin = Math.min(this.ypoints[var1], this.ymin);
            }
         }
      }

   }

   private double[] getValidPoints(double[] var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         if (var2 > 0) {
            var1[var3 - var2] = var1[var3];
         }

         if (java.lang.Double.isNaN(this.ypoints[var3])) {
            ++var2;
         }
      }

      if (var2 == 0) {
         return var1;
      } else {
         double[] var4 = new double[this.index - var2];
         System.arraycopy(var1, 0, var4, 0, this.index - var2);
         return var4;
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new Dataset.Loader();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected static class Loader extends XMLLoader {
      public void saveObject(XMLControl var1, Object var2) {
         Dataset var3 = (Dataset)var2;
         var1.setValue("points", var3.getPoints());
         var1.setValue("index", var3.index);
         var1.setValue("marker_shape", var3.getMarkerShape());
         var1.setValue("marker_size", var3.getMarkerSize());
         var1.setValue("sorted", var3.isSorted());
         var1.setValue("connected", var3.isConnected());
         var1.setValue("name", var3.name);
         var1.setValue("x_name", var3.xColumnName);
         var1.setValue("y_name", var3.yColumnName);
         var1.setValue("line_color", var3.lineColor);
         var1.setValue("fill_color", var3.fillColor);
         var1.setValue("edge_color", var3.edgeColor);
         var1.setValue("errorbar_color", var3.errorBarColor);
         var1.setValue("datasetID", var3.datasetID);
      }

      public Object createObject(XMLControl var1) {
         Class var2 = var1.getObjectClass();
         if ((Dataset.class$org$opensourcephysics$display$Dataset == null ? (Dataset.class$org$opensourcephysics$display$Dataset = Dataset.class$("org.opensourcephysics.display.Dataset")) : Dataset.class$org$opensourcephysics$display$Dataset).isAssignableFrom(var2) && !(Dataset.class$org$opensourcephysics$display$Dataset == null ? (Dataset.class$org$opensourcephysics$display$Dataset = Dataset.class$("org.opensourcephysics.display.Dataset")) : Dataset.class$org$opensourcephysics$display$Dataset).equals(var2)) {
            try {
               return var2.newInstance();
            } catch (InstantiationException var4) {
            } catch (IllegalAccessException var5) {
            }
         }

         return new Dataset();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Dataset var3 = (Dataset)var2;
         double[][] var4 = (double[][])((double[][])var1.getObject("points"));
         if (var4 != null && var4.length > 0 && var4[0] != null) {
            var3.clear();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               var3.append(var4[var5][0], var4[var5][1]);
            }
         }

         double[] var8 = (double[])((double[])var1.getObject("x_points"));
         double[] var6 = (double[])((double[])var1.getObject("y_points"));
         if (var8 != null && var6 != null) {
            var3.clear();
            var3.append(var8, var6);
         }

         var3.index = var1.getInt("index");
         if (var1.getPropertyNames().contains("marker_shape")) {
            var3.setMarkerShape(var1.getInt("marker_shape"));
         }

         if (var1.getPropertyNames().contains("marker_size")) {
            var3.setMarkerSize(var1.getInt("marker_size"));
         }

         var3.setSorted(var1.getBoolean("sorted"));
         var3.setConnected(var1.getBoolean("connected"));
         var3.name = var1.getString("name");
         var3.xColumnName = var1.getString("x_name");
         var3.yColumnName = var1.getString("y_name");
         Color var7 = (Color)var1.getObject("line_color");
         if (var7 != null) {
            var3.lineColor = var7;
         }

         var7 = (Color)var1.getObject("fill_color");
         if (var7 != null) {
            var3.fillColor = var7;
         }

         var7 = (Color)var1.getObject("edge_color");
         if (var7 != null) {
            var3.edgeColor = var7;
         }

         var7 = (Color)var1.getObject("errorbar_color");
         if (var7 != null) {
            var3.errorBarColor = var7;
         }

         var3.setID(var1.getInt("datasetID"));
         return var2;
      }
   }

   class ErrorBar implements Drawable {
      double x;
      double y;
      double delx;
      double dely;
      int tick = 3;

      ErrorBar(double var2, double var4, double var6, double var8) {
         this.x = var2;
         this.y = var4;
         this.delx = var6;
         this.dely = var8;
      }

      public void draw(DrawingPanel var1, Graphics var2) {
         if (!java.lang.Double.isNaN(this.y)) {
            int var3 = var1.xToPix(this.x);
            int var4 = var1.xToPix(this.x - this.delx);
            int var5 = var1.xToPix(this.x + this.delx);
            int var6 = var1.yToPix(this.y);
            int var7 = var1.yToPix(this.y - this.dely);
            int var8 = var1.yToPix(this.y + this.dely);
            var2.setColor(Dataset.this.errorBarColor);
            var2.drawLine(var4, var6, var5, var6);
            var2.drawLine(var3, var7, var3, var8);
            var2.drawLine(var4, var6 - this.tick, var4, var6 + this.tick);
            var2.drawLine(var5, var6 - this.tick, var5, var6 + this.tick);
            var2.drawLine(var3 - this.tick, var7, var3 + this.tick, var7);
            var2.drawLine(var3 - this.tick, var8, var3 + this.tick, var8);
         }
      }
   }
}
