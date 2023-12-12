package org.opensourcephysics.displayejs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.display.InteractivePanel;

public class DrawingPanel3D extends InteractivePanel implements InteractionSource {
   public static final int TRANSPARENCY_LEVEL = 100;
   private static final double SCI_MIN = 0.001D;
   private static final double SCI_MAX = 100.0D;
   private static final DecimalFormat scientificFormat = new DecimalFormat("0.#####E0");
   private static final DecimalFormat decimalFormat = new DecimalFormat("0.###");
   private static final int AXIS_DIVISIONS = 10;
   public static final int DISPLAY_PLANAR = 0;
   public static final int DISPLAY_PLANAR_XY = 0;
   public static final int DISPLAY_PLANAR_XZ = 1;
   public static final int DISPLAY_PLANAR_YZ = 2;
   public static final int DISPLAY_3D = 10;
   public static final int DISPLAY_PERSPECTIVE = 10;
   public static final int DISPLAY_NO_PERSPECTIVE = 12;
   public static final int CURSOR_NONE = 0;
   public static final int CURSOR_XYZ = 1;
   public static final int CURSOR_CUBE = 2;
   public static final int CURSOR_CROSSHAIR = 3;
   public static final int DECORATION_NONE = 0;
   public static final int DECORATION_AXES = 1;
   public static final int DECORATION_CUBE = 2;
   protected boolean autoscaleZ;
   protected boolean removeHiddenLines;
   protected boolean respondToMouse;
   protected boolean showPosition;
   protected boolean useColorDepth;
   protected boolean allowQuickRedraw;
   protected int displayMode;
   protected int cursorMode;
   protected int decorationType;
   protected int deltaa;
   protected int deltab;
   protected double alpha;
   protected double beta;
   protected double zoom;
   protected double zmin;
   protected double zmax;
   protected double zminPreferred;
   protected double zmaxPreferred;
   protected double zfloor;
   protected double zceil;
   protected ArrayList listeners;
   private boolean quickRedrawOn;
   private double cosAlpha;
   private double sinAlpha;
   private double cosBeta;
   private double sinBeta;
   private double ratioToPlane;
   private double ratioToCenter;
   private double centerX;
   private double centerY;
   private double centerZ;
   private double aconstant;
   private double bconstant;
   private double viewToPlane;
   private double viewToCenter;
   private int acenter;
   private int bcenter;
   private int trackersVisible;
   private int keyPressed;
   private Point3D trackerPoint;
   private ArrayList list3D;
   private Comparator3D comparator;
   private InteractiveArrow xAxis;
   private InteractiveArrow yAxis;
   private InteractiveArrow zAxis;
   private InteractiveText xText;
   private InteractiveText yText;
   private InteractiveText zText;
   private InteractiveArrow[] boxSides;
   private InteractiveArrow[] trackerLines;
   private float[] crc;
   private int lastX;
   private int lastY;
   private InteractionTarget targetHit;
   private Interactive iad;

   public DrawingPanel3D() {
      this(10);
   }

   public DrawingPanel3D(int var1) {
      this.autoscaleZ = false;
      this.removeHiddenLines = true;
      this.respondToMouse = true;
      this.showPosition = true;
      this.useColorDepth = true;
      this.allowQuickRedraw = true;
      this.displayMode = 10;
      this.cursorMode = 1;
      this.decorationType = 2;
      this.deltaa = 0;
      this.deltab = 0;
      this.alpha = 0.0D;
      this.beta = 0.0D;
      this.zoom = 1.0D;
      this.zmin = -1.0D;
      this.zmax = 1.0D;
      this.zminPreferred = -10.0D;
      this.zmaxPreferred = 10.0D;
      this.zfloor = Double.NaN;
      this.zceil = Double.NaN;
      this.listeners = new ArrayList();
      this.quickRedrawOn = false;
      this.cosAlpha = Math.cos(this.alpha);
      this.sinAlpha = Math.sin(this.alpha);
      this.cosBeta = Math.cos(this.beta);
      this.sinBeta = Math.sin(this.beta);
      this.ratioToPlane = 2.5D;
      this.ratioToCenter = 2.0D;
      this.keyPressed = -1;
      this.trackerPoint = new Point3D(0.0D, 0.0D, 0.0D);
      this.list3D = new ArrayList();
      this.comparator = new Comparator3D();
      this.boxSides = new InteractiveArrow[12];
      this.trackerLines = null;
      this.crc = new float[4];
      this.lastX = 0;
      this.lastY = 0;
      this.targetHit = null;
      this.iad = null;
      super.setSquareAspect(true);
      super.setShowCoordinates(false);
      super.enableInspector(false);
      super.removeOptionController();
      super.autoscaleX = super.autoscaleY = this.autoscaleZ = false;
      this.setPreferredMinMax(-1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D);
      this.addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent var1) {
            DrawingPanel3D.this.computeConstants(1);
         }
      });
      this.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent var1) {
            DrawingPanel3D.this.keyPressed = var1.getKeyCode();
         }

         public void keyReleased(KeyEvent var1) {
            DrawingPanel3D.this.keyPressed = -1;
         }
      });
      Resolution var2 = new Resolution(10);
      int var3 = 0;

      int var4;
      for(var4 = this.boxSides.length; var3 < var4; ++var3) {
         this.boxSides[var3] = new InteractiveArrow(1);
         this.boxSides[var3].setResolution(var2);
         this.boxSides[var3].setEnabled(false);
         this.boxSides[var3].canBeMeasured(false);
      }

      this.xAxis = new InteractiveArrow(0);
      this.xAxis.setResolution(var2);
      this.xAxis.setEnabled(false);
      this.xAxis.canBeMeasured(false);
      this.xText = new InteractiveText("X");
      this.xText.getStyle().setFont(new Font("Dialog", 0, 12));
      this.xText.setEnabled(false);
      this.xText.canBeMeasured(false);
      this.yAxis = new InteractiveArrow(0);
      this.yAxis.setResolution(var2);
      this.yAxis.setEnabled(false);
      this.yAxis.canBeMeasured(false);
      this.yText = new InteractiveText("Y");
      this.yText.getStyle().setFont(new Font("Dialog", 0, 12));
      this.yText.setEnabled(false);
      this.yText.canBeMeasured(false);
      this.zAxis = new InteractiveArrow(0);
      this.zAxis.setResolution(var2);
      this.zAxis.setEnabled(false);
      this.zAxis.canBeMeasured(false);
      this.zText = new InteractiveText("Z");
      this.zText.getStyle().setFont(new Font("Dialog", 0, 12));
      this.zText.setEnabled(false);
      this.zText.canBeMeasured(false);
      this.trackerLines = new InteractiveArrow[9];
      var3 = 0;

      for(var4 = this.trackerLines.length; var3 < var4; ++var3) {
         this.trackerLines[var3] = new InteractiveArrow(1);
         this.trackerLines[var3].setResolution(var2);
         this.trackerLines[var3].setEnabled(false);
         this.trackerLines[var3].setVisible(false);
         this.trackerLines[var3].canBeMeasured(false);
      }

      this.setForeground(this.getForeground());
      this.clear();
      this.displayMode = var1;
      if (this.displayMode < 10) {
         this.setDecorationType(0);
         this.setCursorMode(0);
         this.setUseColorDepth(false);
      } else {
         this.setDecorationType(2);
         this.setCursorMode(1);
         this.setUseColorDepth(true);
      }

      this.setPixelScale();
      this.computeConstants(2);
   }

   public void setSquareAspect(boolean var1) {
      if (var1 != super.squareAspect) {
         super.setSquareAspect(var1);
         this.computeConstants(3);
      }

   }

   public void setAutoscaleZ(boolean var1) {
      this.autoscaleZ = var1;
   }

   public boolean isAutoscaleZ() {
      return this.autoscaleZ;
   }

   public void setRemoveHiddenLines(boolean var1) {
      this.removeHiddenLines = var1;
   }

   public boolean isRemoveHiddenLines() {
      return this.removeHiddenLines;
   }

   public void setAllowQuickRedraw(boolean var1) {
      this.allowQuickRedraw = var1;
   }

   public boolean isAllowQuickRedraw() {
      return this.allowQuickRedraw;
   }

   public void setShowCoordinates(boolean var1) {
      this.showPosition = var1;
   }

   public boolean isShowCoordinates() {
      return this.showPosition;
   }

   public void setUseColorDepth(boolean var1) {
      this.useColorDepth = var1;
   }

   public boolean isUseColorDepth() {
      return this.useColorDepth;
   }

   public void setDisplayMode(int var1) {
      if (this.displayMode != var1) {
         this.displayMode = var1;
         this.setDecorationType(this.decorationType);
         this.computeConstants(4);
      }
   }

   public int getDisplayMode() {
      return this.displayMode;
   }

   public void setCursorMode(int var1) {
      this.cursorMode = var1;
      switch(var1) {
      case 0:
         this.trackersVisible = 0;
         break;
      case 1:
         this.trackersVisible = 3;
         break;
      case 2:
         this.trackersVisible = 9;
         break;
      case 3:
      default:
         this.trackersVisible = 3;
      }

   }

   public int getCursorMode() {
      return this.cursorMode;
   }

   public void setDecorationType(int var1) {
      this.decorationType = var1;
      int var5;
      int var6;
      switch(var1) {
      case 0:
         this.xAxis.setVisible(false);
         this.xText.setVisible(false);
         this.yAxis.setVisible(false);
         this.yText.setVisible(false);
         this.zAxis.setVisible(false);
         this.zText.setVisible(false);
         int var7 = 0;

         for(int var8 = this.boxSides.length; var7 < var8; ++var7) {
            this.boxSides[var7].setVisible(false);
         }

         return;
      case 1:
         boolean var2 = this.displayMode == 0 || this.displayMode == 1 || this.displayMode >= 10;
         boolean var3 = this.displayMode == 0 || this.displayMode == 2 || this.displayMode >= 10;
         boolean var4 = this.displayMode == 2 || this.displayMode == 1 || this.displayMode >= 10;
         this.xAxis.setVisible(var2);
         this.xText.setVisible(var2);
         this.yAxis.setVisible(var3);
         this.yText.setVisible(var3);
         this.zAxis.setVisible(var4);
         this.zText.setVisible(var4);
         var5 = 0;

         for(var6 = this.boxSides.length; var5 < var6; ++var5) {
            this.boxSides[var5].setVisible(false);
         }

         return;
      case 2:
         this.xAxis.setVisible(false);
         this.xText.setVisible(false);
         this.yAxis.setVisible(false);
         this.yText.setVisible(false);
         this.zAxis.setVisible(false);
         this.zText.setVisible(false);
         var5 = 0;

         for(var6 = this.boxSides.length; var5 < var6; ++var5) {
            this.boxSides[var5].setVisible(true);
         }
      }

   }

   public int getDecorationType() {
      return this.decorationType;
   }

   public void setDecorationResolution(Resolution var1) {
      int var2 = 0;

      int var3;
      for(var3 = this.boxSides.length; var2 < var3; ++var2) {
         this.boxSides[var2].setResolution(var1);
      }

      this.xAxis.setResolution(var1);
      this.yAxis.setResolution(var1);
      this.zAxis.setResolution(var1);
      var2 = 0;

      for(var3 = this.trackerLines.length; var2 < var3; ++var2) {
         this.trackerLines[var2].setResolution(var1);
      }

   }

   public void setPan(int var1, int var2) {
      this.deltaa = var1;
      this.deltab = var2;
      this.computeConstants(5);
   }

   public Point getPan() {
      return new Point(this.deltaa, this.deltab);
   }

   public void setAlpha(double var1) {
      this.alpha = var1;
      this.cosAlpha = Math.cos(this.alpha);
      this.sinAlpha = Math.sin(this.alpha);
      this.reportTheNeedToProject(1);
   }

   public double getAlpha() {
      return this.alpha;
   }

   public void setBeta(double var1) {
      this.beta = var1;
      this.cosBeta = Math.cos(this.beta);
      this.sinBeta = Math.sin(this.beta);
      this.reportTheNeedToProject(2);
   }

   public double getBeta() {
      return this.beta;
   }

   public void setAlphaAndBeta(double var1, double var3) {
      this.alpha = var1;
      this.cosAlpha = Math.cos(this.alpha);
      this.sinAlpha = Math.sin(this.alpha);
      this.beta = var3;
      this.cosBeta = Math.cos(this.beta);
      this.sinBeta = Math.sin(this.beta);
      this.reportTheNeedToProject(3);
   }

   public void setZoom(double var1) {
      this.zoom = var1;
      this.computeConstants(6);
   }

   public double getZoom() {
      return this.zoom;
   }

   public void setRatioToCenter(double var1) {
      this.ratioToCenter = var1;
      this.computeConstants(7);
   }

   public double getRatioToCenter() {
      return this.ratioToCenter;
   }

   public void setRatioToPlane(double var1) {
      this.ratioToPlane = var1;
      this.computeConstants(8);
   }

   public double getRatioToPlane() {
      return this.ratioToPlane;
   }

   public void setPreferredMinMax(double var1, double var3, double var5, double var7, double var9, double var11) {
      super.setPreferredMinMax(var1, var3, var5, var7);
      this.setPreferredMinMaxZ(var9, var11);
   }

   public void setPreferredMinMaxZ(double var1, double var3) {
      this.autoscaleZ = false;
      if (var1 == var3) {
         var1 = 0.9D * var1 - 0.5D;
         var3 = 1.1D * var3 + 0.5D;
      }

      this.zminPreferred = var1;
      this.zmaxPreferred = var3;
   }

   public double getPreferredZMax() {
      return this.zmaxPreferred;
   }

   public double getPreferredZMin() {
      return this.zminPreferred;
   }

   public double getZMin() {
      return this.zmin;
   }

   public double getZMax() {
      return this.zmax;
   }

   public void limitAutoscaleZ(double var1, double var3) {
      this.zfloor = var1;
      this.zceil = var3;
   }

   public void scale() {
      super.scale();
      if (this.autoscaleZ) {
         this.scaleZ();
      }

   }

   public void setEnabled(boolean var1) {
      this.respondToMouse = var1;
   }

   public boolean isEnabled() {
      return true;
   }

   public void setEnabled(int var1, boolean var2) {
      this.respondToMouse = var2;
   }

   public boolean isEnabled(int var1) {
      return true;
   }

   public void addListener(InteractionListener var1) {
      if (var1 != null && !this.listeners.contains(var1)) {
         this.listeners.add(var1);
      }
   }

   public void removeListener(InteractionListener var1) {
      this.listeners.remove(var1);
   }

   public void removeAllListeners() {
      this.listeners = new ArrayList();
   }

   public void invokeActions(InteractionEvent var1) {
      Iterator var2 = this.listeners.iterator();

      while(var2.hasNext()) {
         InteractionListener var3 = (InteractionListener)var2.next();
         var3.interactionPerformed(var1);
      }

   }

   public void setForeground(Color var1) {
      super.setForeground(var1);
      if (this.xAxis != null) {
         int var2 = 0;

         int var3;
         for(var3 = this.boxSides.length; var2 < var3; ++var2) {
            this.boxSides[var2].getStyle().setEdgeColor(var1);
         }

         this.xAxis.getStyle().setEdgeColor(var1);
         this.xText.getStyle().setEdgeColor(var1);
         this.yAxis.getStyle().setEdgeColor(var1);
         this.yText.getStyle().setEdgeColor(var1);
         this.zAxis.getStyle().setEdgeColor(var1);
         this.zText.getStyle().setEdgeColor(var1);
         var2 = 0;

         for(var3 = this.trackerLines.length; var2 < var3; ++var2) {
            this.trackerLines[var2].getStyle().setEdgeColor(var1.brighter().brighter());
         }
      }

   }

   public void setPixelScale() {
      double var1 = super.xmin;
      double var3 = super.xmax;
      double var5 = super.ymin;
      double var7 = super.ymax;
      double var9 = this.zmin;
      double var11 = this.zmax;
      super.xmin = super.xminPreferred;
      super.xmax = super.xmaxPreferred;
      super.ymin = super.yminPreferred;
      super.ymax = super.ymaxPreferred;
      this.zmin = this.zminPreferred;
      this.zmax = this.zmaxPreferred;
      if (var1 != super.xmin || var3 != super.xmax || var5 != super.ymin || var7 != super.ymax || var9 != this.zmin || var11 != this.zmax) {
         this.computeConstants(9);
      }

   }

   public void clear() {
      super.clear();
      int var1 = 0;

      int var2;
      for(var2 = this.boxSides.length; var1 < var2; ++var1) {
         this.addDrawable(this.boxSides[var1]);
      }

      this.addDrawable(this.xAxis);
      this.addDrawable(this.xText);
      this.addDrawable(this.yAxis);
      this.addDrawable(this.yText);
      this.addDrawable(this.zAxis);
      this.addDrawable(this.zText);
      var1 = 0;

      for(var2 = this.trackerLines.length; var1 < var2; ++var1) {
         this.addDrawable(this.trackerLines[var1]);
      }

   }

   public void paintDrawableList(Graphics var1, ArrayList var2) {
      Graphics2D var3 = (Graphics2D)var1;
      Iterator var4 = var2.iterator();
      Shape var5 = var3.getClip();
      if (super.clipAtGutter) {
         var3.clipRect(super.leftGutter, super.topGutter, this.getWidth() - super.leftGutter - super.rightGutter, this.getHeight() - super.bottomGutter - super.topGutter);
      }

      Drawable var6;
      if (!this.quickRedrawOn && this.removeHiddenLines) {
         this.list3D.clear();

         label59:
         while(true) {
            Object3D[] var7;
            int var8;
            label52:
            do {
               while(var4.hasNext()) {
                  var6 = (Drawable)var4.next();
                  if (var6 instanceof Drawable3D) {
                     var7 = ((Drawable3D)var6).getObjects3D(this);
                     continue label52;
                  }

                  var6.draw(this, var3);
               }

               if (this.list3D.size() > 0) {
                  Object[] var10 = this.list3D.toArray();
                  Arrays.sort(var10, this.comparator);
                  int var11 = 0;

                  for(var8 = var10.length; var11 < var8; ++var11) {
                     Object3D var12 = (Object3D)var10[var11];
                     var12.drawable3D.draw(this, var3, var12.index);
                  }
               }
               break label59;
            } while(var7 == null);

            var8 = 0;

            for(int var9 = var7.length; var8 < var9; ++var8) {
               if (var7[var8] != null && !Double.isNaN(var7[var8].distance)) {
                  this.list3D.add(var7[var8]);
               }
            }
         }
      } else {
         while(var4.hasNext()) {
            var6 = (Drawable)var4.next();
            if (var6 instanceof Drawable3D) {
               ((Drawable3D)var6).drawQuickly(this, var3);
            } else {
               var6.draw(this, var3);
            }
         }
      }

      var3.setClip(var5);
   }

   public double[] project(double[] var1, double[] var2) {
      double var3 = var1[0] - this.centerX;
      double var5 = var1[1] - this.centerY;
      double var7 = 0.0D;
      switch(var1.length) {
      case 2:
      case 4:
         var7 = 0.0D;
         break;
      case 3:
      case 6:
         var7 = var1[2] - this.centerZ;
         break;
      case 5:
      default:
         throw new IllegalArgumentException("Method project not supported for this length.");
      }

      double var9;
      double var11;
      double var13;
      double var15;
      switch(this.displayMode) {
      case 0:
         var9 = var7;
         var11 = var3;
         var13 = var5;
         var15 = 1.8D;
         break;
      case 1:
         var9 = var5;
         var11 = var3;
         var13 = var7;
         var15 = 1.8D;
         break;
      case 2:
         var9 = var3;
         var11 = var5;
         var13 = var7;
         var15 = 1.8D;
         break;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      default:
         var9 = var3 * this.cosAlpha + var5 * this.sinAlpha;
         var11 = -var3 * this.sinAlpha + var5 * this.cosAlpha;
         var13 = -var9 * this.sinBeta + var7 * this.cosBeta;
         var9 = var9 * this.cosBeta + var7 * this.sinBeta;
         double var17 = this.viewToCenter - var9;
         if (Math.abs(var17) < 0.01D) {
            var17 = 0.01D;
         }

         var15 = this.viewToPlane / var17;
         break;
      case 12:
         var9 = var3 * this.cosAlpha + var5 * this.sinAlpha;
         var11 = -var3 * this.sinAlpha + var5 * this.cosAlpha;
         var13 = -var9 * this.sinBeta + var7 * this.cosBeta;
         var9 = var9 * this.cosBeta + var7 * this.sinBeta;
         var15 = 1.3D;
      }

      var2[0] = (double)this.acenter + var11 * var15 * this.aconstant;
      var2[1] = (double)this.bcenter - var13 * var15 * this.bconstant;
      switch(var1.length) {
      case 2:
      case 3:
         var2[2] = (this.viewToCenter - var9) / this.viewToCenter;
         break;
      case 4:
         var2[2] = var1[2] * var15 * this.aconstant;
         var2[3] = var1[3] * var15 * this.bconstant;
         var2[4] = (this.viewToCenter - var9) / this.viewToCenter;
      case 5:
      default:
         break;
      case 6:
         switch(this.displayMode) {
         case 0:
            var2[2] = var1[3] * var15 * this.aconstant;
            var2[3] = var1[4] * var15 * this.bconstant;
            break;
         case 1:
            var2[2] = var1[3] * var15 * this.aconstant;
            var2[3] = var1[5] * var15 * this.bconstant;
            break;
         case 2:
            var2[2] = var1[4] * var15 * this.aconstant;
            var2[3] = var1[5] * var15 * this.bconstant;
            break;
         default:
            var2[2] = Math.max(var1[3], var1[4]) * var15 * this.aconstant;
            var2[3] = var1[5] * var15 * this.bconstant;
         }

         var2[4] = (this.viewToCenter - var9) / this.viewToCenter;
      }

      return var2;
   }

   public Color projectColor(Color var1, double var2) {
      if (!this.useColorDepth) {
         return var1;
      } else {
         try {
            var1.getRGBComponents(this.crc);

            for(int var4 = 0; var4 < 3; ++var4) {
               float[] var10000 = this.crc;
               var10000[var4] = (float)((double)var10000[var4] / var2);
               this.crc[var4] = (float)Math.max(Math.min((double)this.crc[var4], 1.0D), 0.0D);
            }

            return new Color(this.crc[0], this.crc[1], this.crc[2], this.crc[3]);
         } catch (Exception var5) {
            return var1;
         }
      }
   }

   private Point3D worldPoint(int var1, int var2) {
      double var3 = 1.8D;
      switch(this.displayMode) {
      case 0:
         return new Point3D(this.centerX + (double)(var1 - this.acenter) / (var3 * this.aconstant), this.centerY + (double)(this.bcenter - var2) / (var3 * this.bconstant), this.zmax);
      case 1:
         return new Point3D(this.centerX + (double)(var1 - this.acenter) / (var3 * this.aconstant), super.ymax, this.centerZ + (double)(this.bcenter - var2) / (var3 * this.bconstant));
      case 2:
         return new Point3D(super.xmax, this.centerY + (double)(var1 - this.acenter) / (var3 * this.aconstant), this.centerZ + (double)(this.bcenter - var2) / (var3 * this.bconstant));
      default:
         return new Point3D((super.xmin + super.xmax) / 2.0D, (super.ymin + super.ymax) / 2.0D, (this.zmin + this.zmax) / 2.0D);
      }
   }

   private Point3D worldDistance(int var1, int var2) {
      double var3 = 1.8D;
      switch(this.displayMode) {
      case 0:
         return new Point3D((double)var1 / (var3 * this.aconstant), (double)(-var2) / (var3 * this.bconstant), 0.0D);
      case 1:
         return new Point3D((double)var1 / (var3 * this.aconstant), 0.0D, (double)(-var2) / (var3 * this.bconstant));
      case 2:
         return new Point3D(0.0D, (double)var1 / (var3 * this.aconstant), (double)(-var2) / (var3 * this.bconstant));
      default:
         return new Point3D((double)var1 / (1.3D * this.aconstant), (double)var2 / (1.3D * this.bconstant), 0.0D);
      }
   }

   public boolean isFocusable() {
      return true;
   }

   private boolean mouseDraggedComputations(MouseEvent var1) {
      if (var1.isControlDown()) {
         this.setPan(this.deltaa + (var1.getX() - this.lastX), this.deltab + (var1.getY() - this.lastY));
         return false;
      } else if (var1.isShiftDown()) {
         this.setZoom(this.zoom - (double)(var1.getY() - this.lastY) * 0.01D);
         return false;
      } else if (this.displayMode >= 10 && this.iad == null && !var1.isAltDown()) {
         this.setAlphaAndBeta(this.alpha - (double)(var1.getX() - this.lastX) * 0.01D, this.beta + (double)(var1.getY() - this.lastY) * 0.01D);
         return false;
      } else {
         Point3D var2 = this.worldDistance(var1.getX() - this.lastX, var1.getY() - this.lastY);
         Point3D var10000;
         if (this.displayMode < 10) {
            switch(this.keyPressed) {
            case 88:
               var10000 = this.trackerPoint;
               var10000.x += var2.x;
               break;
            case 89:
               var10000 = this.trackerPoint;
               var10000.y += var2.y;
               break;
            case 90:
               var10000 = this.trackerPoint;
               var10000.z += var2.z;
               break;
            default:
               var10000 = this.trackerPoint;
               var10000.x += var2.x;
               var10000 = this.trackerPoint;
               var10000.y += var2.y;
               var10000 = this.trackerPoint;
               var10000.z += var2.z;
            }
         } else {
            byte var3 = 1;
            if (this.cosBeta < 0.0D) {
               var3 = -1;
            }

            switch(this.keyPressed) {
            case 88:
               if (this.cosAlpha >= 0.0D && Math.abs(this.sinAlpha) < this.cosAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.x += var2.y;
               } else if (this.sinAlpha >= 0.0D && Math.abs(this.cosAlpha) < this.sinAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.x -= var2.x;
               } else if (this.cosAlpha < 0.0D && Math.abs(this.sinAlpha) < -this.cosAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.x -= var2.y;
               } else {
                  var10000 = this.trackerPoint;
                  var10000.x += var2.x;
               }
               break;
            case 89:
               if (this.cosAlpha >= 0.0D && Math.abs(this.sinAlpha) < this.cosAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.y += var2.x;
               } else if (this.sinAlpha >= 0.0D && Math.abs(this.cosAlpha) < this.sinAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.y += var2.y;
               } else if (this.cosAlpha < 0.0D && Math.abs(this.sinAlpha) < -this.cosAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.y -= var2.x;
               } else {
                  var10000 = this.trackerPoint;
                  var10000.y -= var2.y;
               }
               break;
            case 90:
               if (this.cosBeta >= 0.0D) {
                  var10000 = this.trackerPoint;
                  var10000.z -= var2.y;
               } else {
                  var10000 = this.trackerPoint;
                  var10000.z += var2.y;
               }
               break;
            default:
               var10000 = this.trackerPoint;
               var10000.z -= (double)var3 * var2.y;
               if (this.cosAlpha >= 0.0D && Math.abs(this.sinAlpha) < this.cosAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.y += var2.x;
               } else if (this.sinAlpha >= 0.0D && Math.abs(this.cosAlpha) < this.sinAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.x -= var2.x;
               } else if (this.cosAlpha < 0.0D && Math.abs(this.sinAlpha) < -this.cosAlpha) {
                  var10000 = this.trackerPoint;
                  var10000.y -= var2.x;
               } else {
                  var10000 = this.trackerPoint;
                  var10000.x += var2.x;
               }
            }
         }

         return true;
      }
   }

   private void resetInteraction(InteractivePanel var1) {
      this.iad = null;
      this.targetHit = null;
      this.showTrackers(false);
      super.blMessageBox.setText((String)null);
      this.repaint();
   }

   public void handleMouseAction(InteractivePanel var1, MouseEvent var2) {
      switch(var1.getMouseAction()) {
      case 1:
         this.requestFocus();
         if (this.allowQuickRedraw && (var2.getModifiers() & 16) != 0) {
            this.quickRedrawOn = true;
         } else {
            this.quickRedrawOn = false;
         }

         this.lastX = var2.getX();
         this.lastY = var2.getY();
         this.targetHit = null;
         this.iad = var1.getInteractive();
         if (this.iad instanceof InteractionTarget) {
            this.targetHit = (InteractionTarget)this.iad;
            this.trackerPoint = this.targetHit.getHotspot(var1);
            this.targetHit.getSource().invokeActions(new InteractionEvent(this.targetHit.getSource(), 2000, (String)null, this.targetHit));
            this.trackerPoint = this.targetHit.getHotspot(var1);
         } else if (this.iad != null) {
            switch(this.displayMode) {
            case 0:
            default:
               this.trackerPoint.x = this.iad.getX();
               this.trackerPoint.y = this.iad.getY();
               this.trackerPoint.z = this.zmax;
               break;
            case 1:
               this.trackerPoint.x = this.iad.getX();
               this.trackerPoint.z = this.iad.getY();
               this.trackerPoint.y = super.ymax;
               break;
            case 2:
               this.trackerPoint.y = this.iad.getX();
               this.trackerPoint.z = this.iad.getY();
               this.trackerPoint.x = super.xmax;
            }

            this.invokeActions(new InteractionEvent(this, 2000, (String)null, this.iad));
            switch(this.displayMode) {
            case 0:
            default:
               this.trackerPoint.x = this.iad.getX();
               this.trackerPoint.y = this.iad.getY();
               this.trackerPoint.z = this.zmax;
               break;
            case 1:
               this.trackerPoint.x = this.iad.getX();
               this.trackerPoint.z = this.iad.getY();
               this.trackerPoint.y = super.ymax;
               break;
            case 2:
               this.trackerPoint.y = this.iad.getX();
               this.trackerPoint.z = this.iad.getY();
               this.trackerPoint.x = super.xmax;
            }
         } else {
            if (this.displayMode >= 10 && !var2.isAltDown()) {
               this.resetInteraction(var1);
               return;
            }

            this.trackerPoint = this.worldPoint(var2.getX(), var2.getY());
            this.invokeActions(new InteractionEvent(this, 2000, (String)null, this.trackerPoint));
         }

         if (this.showPosition) {
            this.displayPosition(this.trackerPoint.x, this.trackerPoint.y, this.trackerPoint.z);
         }

         this.positionTrackers();
         this.showTrackers(true);
         var1.repaint();
         break;
      case 2:
         if (this.targetHit != null) {
            this.targetHit.getSource().invokeActions(new InteractionEvent(this.targetHit.getSource(), 2002, (String)null, this.targetHit));
         } else if (this.iad != null) {
            this.invokeActions(new InteractionEvent(this, 2002, (String)null, this.iad));
         } else if (this.displayMode < 10 || var2.isAltDown()) {
            this.invokeActions(new InteractionEvent(this, 2002, (String)null, this.trackerPoint));
         }

         this.quickRedrawOn = false;
         this.resetInteraction(var1);
         break;
      case 3:
         boolean var3 = this.mouseDraggedComputations(var2);
         this.lastX = var2.getX();
         this.lastY = var2.getY();
         if (!var3) {
            this.invokeActions(new InteractionEvent(this, 2001, (String)null, (Object)null));
            this.resetInteraction(var1);
            return;
         }

         if (this.targetHit != null) {
            this.targetHit.updateHotspot(var1, this.trackerPoint);
            this.targetHit.getSource().invokeActions(new InteractionEvent(this.targetHit.getSource(), 2001, (String)null, this.targetHit));
            this.trackerPoint = this.targetHit.getHotspot(var1);
         } else if (this.iad != null) {
            this.invokeActions(new InteractionEvent(this, 2001, (String)null, this.iad));
         } else {
            this.invokeActions(new InteractionEvent(this, 2001, (String)null, this.trackerPoint));
         }

         if (this.showPosition) {
            this.displayPosition(this.trackerPoint.x, this.trackerPoint.y, this.trackerPoint.z);
         }

         this.positionTrackers();
         this.showTrackers(true);
         var1.repaint();
      case 4:
      case 5:
      case 6:
      default:
         break;
      case 7:
         if (var1.getInteractive() != null) {
            var1.setMouseCursor(Cursor.getPredefinedCursor(12));
         } else {
            var1.setMouseCursor(Cursor.getPredefinedCursor(1));
         }
      }

   }

   private void reportTheNeedToProject(int var1) {
      ArrayList var2 = (ArrayList)super.drawableList.clone();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var4 instanceof Drawable3D) {
            ((Drawable3D)var4).needsToProject(this);
         }
      }

   }

   private void computeConstants(int var1) {
      int var2 = this.getWidth();
      int var3 = this.getHeight();
      this.acenter = this.deltaa + var2 / 2;
      this.bcenter = this.deltab + var3 / 2;
      if (super.squareAspect) {
         var2 = var3 = Math.min(var2, var3);
      }

      double var4 = super.xmax - super.xmin;
      double var6 = super.ymax - super.ymin;
      double var8 = this.zmax - this.zmin;
      double var10;
      switch(this.displayMode) {
      case 0:
         var10 = Math.max(var4, var6);
         break;
      case 1:
         var10 = Math.max(var4, var8);
         break;
      case 2:
         var10 = Math.max(var6, var8);
         break;
      default:
         var10 = Math.max(Math.max(var4, var6), var8);
      }

      this.centerX = (super.xmax + super.xmin) / 2.0D;
      this.centerY = (super.ymax + super.ymin) / 2.0D;
      this.centerZ = (this.zmax + this.zmin) / 2.0D;
      this.aconstant = 0.5D * this.zoom * (double)var2 / var10;
      this.bconstant = 0.5D * this.zoom * (double)var3 / var10;
      this.viewToPlane = this.ratioToPlane * var10;
      this.viewToCenter = this.ratioToCenter * var10;
      this.resetDecoration(var4, var6, var8);
      this.reportTheNeedToProject(3);
   }

   private void scaleZ() {
      double var1 = Double.MAX_VALUE;
      double var3 = -1.7976931348623157E308D;
      boolean var5 = false;
      ArrayList var6 = this.getDrawables();
      Iterator var7 = var6.iterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         if (var8 instanceof Measurable3D) {
            Measurable3D var9 = (Measurable3D)var8;
            if (var9.isMeasured() && !Double.isNaN(var9.getZMax()) && !Double.isNaN(var9.getZMin())) {
               var1 = Math.min(var1, var9.getZMin());
               var1 = Math.min(var1, var9.getZMax());
               var3 = Math.max(var3, var9.getZMax());
               var3 = Math.max(var3, var9.getZMin());
               var5 = true;
            }
         }
      }

      if (var5) {
         if (var1 == var3) {
            var1 = 0.9D * var1 - 0.5D;
            var3 = 1.1D * var3 + 0.5D;
         }

         double var10 = var3 - var1;
         this.zminPreferred = var1 - super.autoscaleMargin * var10;
         this.zmaxPreferred = var3 + super.autoscaleMargin * var10;
      }

      if (!Double.isNaN(this.zfloor)) {
         this.zminPreferred = Math.min(this.zfloor, this.zminPreferred);
      }

      if (!Double.isNaN(this.zceil)) {
         this.zmaxPreferred = Math.max(this.zceil, this.zmaxPreferred);
      }

   }

   private String formatDouble(String var1, double var2) {
      if (var2 == 0.0D) {
         return var1 + decimalFormat.format(var2);
      } else {
         return !(Math.abs(var2) > 100.0D) && !(Math.abs(var2) < 0.001D) ? var1 + decimalFormat.format(var2) : var1 + scientificFormat.format(var2);
      }
   }

   private void displayPosition(double var1, double var3, double var5) {
      switch(this.displayMode) {
      case 0:
         super.blMessageBox.setText(this.formatDouble("x=", var1) + " " + this.formatDouble("y=", var3));
         break;
      case 1:
         super.blMessageBox.setText(this.formatDouble("x=", var1) + " " + this.formatDouble("z=", var5));
         break;
      case 2:
         super.blMessageBox.setText(this.formatDouble("y=", var3) + " " + this.formatDouble("z=", var5));
         break;
      default:
         super.blMessageBox.setText(this.formatDouble("x=", var1) + " " + this.formatDouble("y=", var3) + " " + this.formatDouble("z=", var5));
      }

   }

   private void showTrackers(boolean var1) {
      int var2 = 0;

      for(int var3 = this.trackerLines.length; var2 < var3; ++var2) {
         if (var2 < this.trackersVisible) {
            this.trackerLines[var2].setVisible(var1);
         } else {
            this.trackerLines[var2].setVisible(false);
         }
      }

   }

   private void positionTrackers() {
      switch(this.cursorMode) {
      case 0:
         return;
      case 1:
      default:
         this.trackerLines[0].setXYZ(this.trackerPoint.x, super.ymin, this.zmin);
         this.trackerLines[0].setSizeXYZ(0.0D, this.trackerPoint.y - super.ymin, 0.0D);
         this.trackerLines[1].setXYZ(super.xmin, this.trackerPoint.y, this.zmin);
         this.trackerLines[1].setSizeXYZ(this.trackerPoint.x - super.xmin, 0.0D, 0.0D);
         this.trackerLines[2].setXYZ(this.trackerPoint.x, this.trackerPoint.y, this.zmin);
         this.trackerLines[2].setSizeXYZ(0.0D, 0.0D, this.trackerPoint.z - this.zmin);
         break;
      case 2:
         this.trackerLines[0].setXYZ(super.xmin, this.trackerPoint.y, this.trackerPoint.z);
         this.trackerLines[0].setSizeXYZ(this.trackerPoint.x - super.xmin, 0.0D, 0.0D);
         this.trackerLines[1].setXYZ(this.trackerPoint.x, super.ymin, this.trackerPoint.z);
         this.trackerLines[1].setSizeXYZ(0.0D, this.trackerPoint.y - super.ymin, 0.0D);
         this.trackerLines[2].setXYZ(this.trackerPoint.x, this.trackerPoint.y, this.zmin);
         this.trackerLines[2].setSizeXYZ(0.0D, 0.0D, this.trackerPoint.z - this.zmin);
         this.trackerLines[3].setXYZ(this.trackerPoint.x, super.ymin, this.zmin);
         this.trackerLines[3].setSizeXYZ(0.0D, this.trackerPoint.y - super.ymin, 0.0D);
         this.trackerLines[4].setXYZ(super.xmin, this.trackerPoint.y, this.zmin);
         this.trackerLines[4].setSizeXYZ(this.trackerPoint.x - super.xmin, 0.0D, 0.0D);
         this.trackerLines[5].setXYZ(this.trackerPoint.x, super.ymin, this.zmin);
         this.trackerLines[5].setSizeXYZ(0.0D, 0.0D, this.trackerPoint.z - this.zmin);
         this.trackerLines[6].setXYZ(super.xmin, super.ymin, this.trackerPoint.z);
         this.trackerLines[6].setSizeXYZ(this.trackerPoint.x - super.xmin, 0.0D, 0.0D);
         this.trackerLines[7].setXYZ(super.xmin, this.trackerPoint.y, this.zmin);
         this.trackerLines[7].setSizeXYZ(0.0D, 0.0D, this.trackerPoint.z - this.zmin);
         this.trackerLines[8].setXYZ(super.xmin, super.ymin, this.trackerPoint.z);
         this.trackerLines[8].setSizeXYZ(0.0D, this.trackerPoint.y - super.ymin, 0.0D);
         break;
      case 3:
         this.trackerLines[0].setXYZ(super.xmin, this.trackerPoint.y, this.trackerPoint.z);
         this.trackerLines[0].setSizeXYZ(super.xmax - super.xmin, 0.0D, 0.0D);
         this.trackerLines[1].setXYZ(this.trackerPoint.x, super.ymin, this.trackerPoint.z);
         this.trackerLines[1].setSizeXYZ(0.0D, super.ymax - super.ymin, 0.0D);
         this.trackerLines[2].setXYZ(this.trackerPoint.x, this.trackerPoint.y, this.zmin);
         this.trackerLines[2].setSizeXYZ(0.0D, 0.0D, this.zmax - this.zmin);
      }

   }

   private void resetDecoration(double var1, double var3, double var5) {
      if (this.boxSides != null && this.boxSides[0] != null) {
         this.boxSides[0].setXYZ(super.xmin, super.ymin, this.zmin);
         this.boxSides[0].setSizeXYZ(var1, 0.0D, 0.0D);
         this.boxSides[1].setXYZ(super.xmax, super.ymin, this.zmin);
         this.boxSides[1].setSizeXYZ(0.0D, var3, 0.0D);
         this.boxSides[2].setXYZ(super.xmin, super.ymax, this.zmin);
         this.boxSides[2].setSizeXYZ(var1, 0.0D, 0.0D);
         this.boxSides[3].setXYZ(super.xmin, super.ymin, this.zmin);
         this.boxSides[3].setSizeXYZ(0.0D, var3, 0.0D);
         this.boxSides[4].setXYZ(super.xmin, super.ymin, this.zmax);
         this.boxSides[4].setSizeXYZ(var1, 0.0D, 0.0D);
         this.boxSides[5].setXYZ(super.xmax, super.ymin, this.zmax);
         this.boxSides[5].setSizeXYZ(0.0D, var3, 0.0D);
         this.boxSides[6].setXYZ(super.xmin, super.ymax, this.zmax);
         this.boxSides[6].setSizeXYZ(var1, 0.0D, 0.0D);
         this.boxSides[7].setXYZ(super.xmin, super.ymin, this.zmax);
         this.boxSides[7].setSizeXYZ(0.0D, var3, 0.0D);
         this.boxSides[8].setXYZ(super.xmin, super.ymin, this.zmin);
         this.boxSides[8].setSizeXYZ(0.0D, 0.0D, var5);
         this.boxSides[9].setXYZ(super.xmax, super.ymin, this.zmin);
         this.boxSides[9].setSizeXYZ(0.0D, 0.0D, var5);
         this.boxSides[10].setXYZ(super.xmax, super.ymax, this.zmin);
         this.boxSides[10].setSizeXYZ(0.0D, 0.0D, var5);
         this.boxSides[11].setXYZ(super.xmin, super.ymax, this.zmin);
         this.boxSides[11].setSizeXYZ(0.0D, 0.0D, var5);
         this.xAxis.setXYZ(super.xmin, super.ymin, this.zmin);
         this.xAxis.setSizeXYZ(var1, 0.0D, 0.0D);
         this.xText.setXYZ(super.xmax + var1 * 0.02D, super.ymin, this.zmin);
         this.yAxis.setXYZ(super.xmin, super.ymin, this.zmin);
         this.yAxis.setSizeXYZ(0.0D, var3, 0.0D);
         this.yText.setXYZ(super.xmin, super.ymax + var1 * 0.02D, this.zmin);
         this.zAxis.setXYZ(super.xmin, super.ymin, this.zmin);
         this.zAxis.setSizeXYZ(0.0D, 0.0D, var5);
         this.zText.setXYZ(super.xmin, super.ymin, this.zmax + var1 * 0.02D);
      }
   }
}
