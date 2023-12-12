package org.opensourcephysics.displayejs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Interactive;
import org.opensourcephysics.numerics.Transformation;

public class InteractiveArrow extends AbstractInteractiveElement implements Body {
   public static final int ARROW = 0;
   public static final int SEGMENT = 1;
   public static final int BOX = 2;
   protected int arrowType;
   protected Transformation transformation;
   protected double originx;
   protected double originy;
   protected double originz;
   protected boolean originIsRelative;
   private int div;
   protected double xmin;
   protected double xmax;
   protected double ymin;
   protected double ymax;
   protected double zmin;
   protected double zmax;
   private double[] coordinates;
   private double[] pixel;
   private Object3D[] objects;
   private int[] aCoord;
   private int[] bCoord;
   private double[][] points;
   protected double[] pixelOrigin;
   protected double[] pixelEndpoint;
   private static final double ARROW_CST = 0.35D;
   private static final double ARROW_MAX = 25.0D;
   private int headPoints;
   private int[] headA;
   private int[] headB;

   public InteractiveArrow() {
      this(0);
   }

   public InteractiveArrow(int var1) {
      this.arrowType = 0;
      this.transformation = null;
      this.originx = 0.0D;
      this.originy = 0.0D;
      this.originz = 0.0D;
      this.originIsRelative = true;
      this.div = -1;
      this.xmin = Double.NaN;
      this.xmax = Double.NaN;
      this.ymin = Double.NaN;
      this.ymax = Double.NaN;
      this.zmin = Double.NaN;
      this.zmax = Double.NaN;
      this.coordinates = new double[3];
      this.pixel = new double[3];
      this.objects = null;
      this.aCoord = null;
      this.bCoord = null;
      this.points = (double[][])null;
      this.pixelOrigin = new double[3];
      this.pixelEndpoint = new double[3];
      this.headPoints = 0;
      this.headA = new int[10];
      this.headB = new int[10];
      this.setArrowType(var1);
      this.div = -1;
   }

   public void copyFrom(InteractiveElement var1) {
      super.copyFrom(var1);
      if (var1 instanceof InteractiveArrow) {
         InteractiveArrow var2 = (InteractiveArrow)var1;
         this.setArrowType(var2.arrowType);
         this.setOrigin(var2.originx, var2.originy, var2.originz, var2.originIsRelative);
         this.setTransformation(var2.transformation);
      }

   }

   public void setArrowType(int var1) {
      this.arrowType = var1;
      super.panelWithValidProjection = null;
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      if (!super.visible) {
         return null;
      } else {
         if (super.hasChanged) {
            this.computeDivisions();
            this.projectPoints(var1);
         } else if (var1 != super.panelWithValidProjection) {
            this.projectPoints(var1);
         }

         if (super.sizeEnabled && Math.abs(this.pixelEndpoint[0] - (double)var2) < 5.0D && Math.abs(this.pixelEndpoint[1] - (double)var3) < 5.0D) {
            return new InteractionTargetElementSize(this);
         } else {
            return super.positionEnabled && Math.abs(this.pixelOrigin[0] - (double)var2) < 5.0D && Math.abs(this.pixelOrigin[1] - (double)var3) < 5.0D ? new InteractionTargetElementPosition(this) : null;
         }
      }
   }

   public Object3D[] getObjects3D(DrawingPanel3D var1) {
      if (!super.visible) {
         return null;
      } else {
         if (super.hasChanged) {
            this.computeDivisions();
            this.projectPoints(var1);
         } else if (var1 != super.panelWithValidProjection) {
            this.projectPoints(var1);
         }

         return this.objects;
      }
   }

   public void draw(DrawingPanel3D var1, Graphics2D var2, int var3) {
      Color var4 = var1.projectColor(super.style.edgeColor, this.objects[var3].distance);
      if (var3 >= this.div - 1 && this.arrowType != 1) {
         Object var5 = super.style.fillPattern;
         if (var5 instanceof Color) {
            var5 = var1.projectColor((Color)var5, this.objects[var3].distance);
         }

         this.drawHead(var1, var2, this.aCoord[var3], this.bCoord[var3], var4, (Paint)var5);
      } else {
         var2.setStroke(super.style.edgeStroke);
         var2.setColor(var4);
         var2.drawLine(this.aCoord[var3], this.bCoord[var3], this.aCoord[var3 + 1], this.bCoord[var3 + 1]);
      }
   }

   public synchronized void drawQuickly(DrawingPanel3D var1, Graphics2D var2) {
      if (super.visible) {
         if (super.hasChanged) {
            this.computeDivisions();
            this.projectPoints(var1);
         } else if (var1 != super.panelWithValidProjection) {
            this.projectPoints(var1);
         }

         this.drawHead(var1, var2, this.aCoord[0], this.bCoord[0], super.style.edgeColor, super.style.fillPattern);
      }
   }

   public synchronized void draw(DrawingPanel var1, Graphics var2) {
      if (super.visible) {
         if (super.hasChanged) {
            this.computeDivisions();
            this.projectPoints(var1);
         } else {
            this.projectPoints(var1);
         }

         this.drawHead(var1, (Graphics2D)var2, this.aCoord[0], this.bCoord[0], super.style.edgeColor, super.style.fillPattern);
      }
   }

   public void setOrigin(double var1, double var3, double var5, boolean var7) {
      this.originx = var1;
      this.originy = var3;
      this.originz = var5;
      this.originIsRelative = var7;
      super.hasChanged = true;
   }

   public void setTransformation(Transformation var1) {
      if (var1 == null) {
         this.transformation = null;
      } else {
         this.transformation = (Transformation)var1.clone();
      }

      super.hasChanged = true;
   }

   public void toSpaceFrame(double[] var1) {
      if (this.transformation != null) {
         this.transformation.direct(var1);
      }

      var1[0] += super.x;
      var1[1] += super.y;
      var1[2] += super.z;
   }

   public void toBodyFrame(double[] var1) throws UnsupportedOperationException {
      var1[0] -= super.x;
      var1[1] -= super.y;
      var1[2] -= super.z;
      if (this.transformation != null) {
         this.transformation.inverse(var1);
      }

   }

   public double getXMin() {
      if (super.hasChanged) {
         this.computeDivisions();
         this.computeExtrema();
      } else if (Double.isNaN(this.xmin)) {
         this.computeExtrema();
      }

      return this.xmin;
   }

   public double getXMax() {
      if (super.hasChanged) {
         this.computeDivisions();
         this.computeExtrema();
      } else if (Double.isNaN(this.xmax)) {
         this.computeExtrema();
      }

      return this.xmax;
   }

   public double getYMin() {
      if (super.hasChanged) {
         this.computeDivisions();
         this.computeExtrema();
      } else if (Double.isNaN(this.ymin)) {
         this.computeExtrema();
      }

      return this.ymin;
   }

   public double getYMax() {
      if (super.hasChanged) {
         this.computeDivisions();
         this.computeExtrema();
      } else if (Double.isNaN(this.ymax)) {
         this.computeExtrema();
      }

      return this.ymax;
   }

   public double getZMin() {
      if (super.hasChanged) {
         this.computeDivisions();
         this.computeExtrema();
      } else if (Double.isNaN(this.zmin)) {
         this.computeExtrema();
      }

      return this.zmin;
   }

   public double getZMax() {
      if (super.hasChanged) {
         this.computeDivisions();
         this.computeExtrema();
      } else if (Double.isNaN(this.zmax)) {
         this.computeExtrema();
      }

      return this.zmax;
   }

   protected void computeExtrema() {
      this.xmin = this.ymin = this.zmin = Double.MAX_VALUE;
      this.xmax = this.ymax = this.zmax = -1.7976931348623157E308D;

      for(int var1 = 0; var1 <= this.div; ++var1) {
         double var2 = this.points[var1][0];
         if (var2 < this.xmin) {
            this.xmin = var2;
         }

         if (var2 > this.xmax) {
            this.xmax = var2;
         }

         var2 = this.points[var1][1];
         if (var2 < this.ymin) {
            this.ymin = var2;
         }

         if (var2 > this.ymax) {
            this.ymax = var2;
         }

         var2 = this.points[var1][2];
         if (var2 < this.zmin) {
            this.zmin = var2;
         }

         if (var2 > this.zmax) {
            this.zmax = var2;
         }
      }

      if (super.group != null) {
         this.xmin = super.group.x + this.xmin * super.group.sizex;
         this.xmax = super.group.x + this.xmax * super.group.sizex;
         this.ymin = super.group.y + this.ymin * super.group.sizey;
         this.ymax = super.group.y + this.ymax * super.group.sizey;
         this.zmin = super.group.z + this.zmin * super.group.sizez;
         this.zmax = super.group.z + this.zmax * super.group.sizez;
      }

   }

   protected void projectPoints(DrawingPanel var1) {
      int var2;
      int var3;
      if (super.group == null) {
         this.coordinates[0] = super.x;
         this.coordinates[1] = super.y;
         this.coordinates[2] = super.z;
         this.transformPoint(this.coordinates, false);
         var1.project(this.coordinates, this.pixelOrigin);
         this.coordinates[0] = super.x + super.sizex;
         this.coordinates[1] = super.y + super.sizey;
         this.coordinates[2] = super.z + super.sizez;
         this.transformPoint(this.coordinates, true);
         var1.project(this.coordinates, this.pixelEndpoint);

         for(var2 = 0; var2 < this.div; ++var2) {
            var1.project(this.points[var2], this.pixel);
            this.aCoord[var2] = (int)this.pixel[0];
            this.bCoord[var2] = (int)this.pixel[1];

            for(var3 = 0; var3 < 3; ++var3) {
               this.coordinates[var3] = (this.points[var2][var3] + this.points[var2 + 1][var3]) * 0.5D;
            }

            var1.project(this.coordinates, this.pixel);
            this.objects[var2].distance = this.pixel[2];
         }

         var1.project(this.points[this.div], this.pixel);
         this.aCoord[this.div] = (int)this.pixel[0];
         this.bCoord[this.div] = (int)this.pixel[1];
      } else {
         this.coordinates[0] = super.x;
         this.coordinates[1] = super.y;
         this.coordinates[2] = super.z;
         this.transformPoint(this.coordinates, false);
         this.coordinates[0] = super.group.x + this.coordinates[0] * super.group.sizex;
         this.coordinates[1] = super.group.y + this.coordinates[1] * super.group.sizey;
         this.coordinates[2] = super.group.z + this.coordinates[2] * super.group.sizez;
         var1.project(this.coordinates, this.pixelOrigin);
         this.coordinates[0] = super.x + super.sizex;
         this.coordinates[1] = super.y + super.sizey;
         this.coordinates[2] = super.z + super.sizez;
         this.transformPoint(this.coordinates, true);
         this.coordinates[0] = super.group.x + this.coordinates[0] * super.group.sizex;
         this.coordinates[1] = super.group.y + this.coordinates[1] * super.group.sizey;
         this.coordinates[2] = super.group.z + this.coordinates[2] * super.group.sizez;
         var1.project(this.coordinates, this.pixelEndpoint);

         for(var2 = 0; var2 < this.div; ++var2) {
            this.coordinates[0] = super.group.x + this.points[var2][0] * super.group.sizex;
            this.coordinates[1] = super.group.y + this.points[var2][1] * super.group.sizey;
            this.coordinates[2] = super.group.z + this.points[var2][2] * super.group.sizez;
            var1.project(this.coordinates, this.pixel);
            this.aCoord[var2] = (int)this.pixel[0];
            this.bCoord[var2] = (int)this.pixel[1];

            for(var3 = 0; var3 < 3; ++var3) {
               this.coordinates[var3] = (this.points[var2][var3] + this.points[var2 + 1][var3]) * 0.5D;
            }

            this.coordinates[0] = super.group.x + this.coordinates[0] * super.group.sizex;
            this.coordinates[1] = super.group.y + this.coordinates[1] * super.group.sizey;
            this.coordinates[2] = super.group.z + this.coordinates[2] * super.group.sizez;
            var1.project(this.coordinates, this.pixel);
            this.objects[var2].distance = this.pixel[2];
         }

         this.coordinates[0] = super.group.x + this.points[this.div][0] * super.group.sizex;
         this.coordinates[1] = super.group.y + this.points[this.div][1] * super.group.sizey;
         this.coordinates[2] = super.group.z + this.points[this.div][2] * super.group.sizez;
         var1.project(this.coordinates, this.pixel);
         this.aCoord[this.div] = (int)this.pixel[0];
         this.bCoord[this.div] = (int)this.pixel[1];
      }

      this.computeHead();
      super.panelWithValidProjection = var1;
   }

   protected void computeDivisions() {
      int var1 = 1;
      double var2;
      if (super.resolution != null) {
         switch(super.resolution.type) {
         case 0:
            var1 = Math.max(super.resolution.n1, 1);
            break;
         case 1:
            var2 = Math.sqrt(super.sizex * super.sizex + super.sizey * super.sizey + super.sizez * super.sizez);
            var1 = Math.max((int)Math.round(0.49D + var2 / super.resolution.maxLength), 1);
         }
      }

      if (this.div != var1) {
         this.div = var1;
         this.points = new double[this.div + 1][3];
         this.aCoord = new int[this.div + 1];
         this.bCoord = new int[this.div + 1];
         this.objects = new Object3D[this.div];

         for(int var9 = 0; var9 < this.div; ++var9) {
            this.objects[var9] = new Object3D(this, var9);
         }
      }

      this.points[0][0] = super.x;
      this.points[0][1] = super.y;
      this.points[0][2] = super.z;
      var2 = super.sizex;
      double var4 = super.sizey;
      double var6 = super.sizez;
      this.points[this.div][0] = super.x + var2;
      this.points[this.div][1] = super.y + var4;
      this.points[this.div][2] = super.z + var6;
      var2 /= (double)this.div;
      var4 /= (double)this.div;
      var6 /= (double)this.div;

      for(int var8 = 1; var8 < this.div; ++var8) {
         this.points[var8][0] = super.x + (double)var8 * var2;
         this.points[var8][1] = super.y + (double)var8 * var4;
         this.points[var8][2] = super.z + (double)var8 * var6;
      }

      this.transformDivisions();
      this.xmin = this.xmax = this.ymin = this.ymax = this.zmin = this.zmax = Double.NaN;
      super.hasChanged = false;
   }

   protected void computeAbsoluteDifference(double[] var1) {
      var1[0] = this.originx * super.sizex;
      var1[1] = this.originy * super.sizey;
      var1[2] = this.originz * super.sizez;
   }

   protected void transformPoint(double[] var1, boolean var2) {
      if (var2) {
         double[] var3 = new double[3];
         this.computeAbsoluteDifference(var3);
         var1[0] -= var3[0];
         var1[1] -= var3[1];
         var1[2] -= var3[2];
      }

      if (this.transformation != null) {
         var1[0] -= super.x;
         var1[1] -= super.y;
         var1[2] -= super.z;
         this.transformation.direct(var1);
         var1[0] += super.x;
         var1[1] += super.y;
         var1[2] += super.z;
      }

   }

   protected void transformDivisions() {
      if (this.originIsRelative) {
         this.computeAbsoluteDifference(this.coordinates);
      } else {
         this.coordinates[0] = this.originx;
         this.coordinates[1] = this.originy;
         this.coordinates[2] = this.originz;
      }

      for(int var1 = 0; var1 <= this.div; ++var1) {
         double[] var10000 = this.points[var1];
         var10000[0] -= this.coordinates[0];
         var10000 = this.points[var1];
         var10000[1] -= this.coordinates[1];
         var10000 = this.points[var1];
         var10000[2] -= this.coordinates[2];
         if (this.transformation != null) {
            var10000 = this.points[var1];
            var10000[0] -= super.x;
            var10000 = this.points[var1];
            var10000[1] -= super.y;
            var10000 = this.points[var1];
            var10000[2] -= super.z;
            this.transformation.direct(this.points[var1]);
            var10000 = this.points[var1];
            var10000[0] += super.x;
            var10000 = this.points[var1];
            var10000[1] += super.y;
            var10000 = this.points[var1];
            var10000[2] += super.z;
         }
      }

   }

   private void computeHead() {
      if (this.arrowType == 1) {
         this.headPoints = 0;
      } else {
         double var1 = (double)(this.aCoord[this.div] - this.aCoord[0]);
         double var3 = (double)(this.bCoord[this.div] - this.bCoord[0]);
         double var5 = Math.sqrt(var1 * var1 + var3 * var3);
         if (var5 == 0.0D) {
            this.headPoints = 0;
         } else {
            var1 = 0.35D * var1 / var5;
            var3 = 0.35D * var3 / var5;
            if (var5 > 25.0D) {
               var1 *= 25.0D / var5;
               var3 *= 25.0D / var5;
            }

            int var7 = (int)((double)this.aCoord[this.div] - var1 * var5);
            int var8 = (int)((double)this.bCoord[this.div] - var3 * var5);
            var1 *= var5 / 2.0D;
            var3 *= var5 / 2.0D;
            switch(this.arrowType) {
            case 0:
            default:
               this.headPoints = 6;
               this.headA[0] = var7;
               this.headB[0] = var8;
               this.headA[1] = var7 - (int)var3;
               this.headB[1] = var8 + (int)var1;
               this.headA[2] = this.aCoord[this.div];
               this.headB[2] = this.bCoord[this.div];
               this.headA[3] = var7 + (int)var3;
               this.headB[3] = var8 - (int)var1;
               this.headA[4] = var7;
               this.headB[4] = var8;
               break;
            case 2:
               this.headPoints = 7;
               this.headA[0] = var7;
               this.headB[0] = var8;
               this.headA[1] = var7 - (int)var3;
               this.headB[1] = var8 + (int)var1;
               this.headA[2] = this.aCoord[this.div] - (int)var3;
               this.headB[2] = this.bCoord[this.div] + (int)var1;
               this.headA[3] = this.aCoord[this.div] + (int)var3;
               this.headB[3] = this.bCoord[this.div] - (int)var1;
               this.headA[4] = var7 + (int)var3;
               this.headB[4] = var8 - (int)var1;
               this.headA[5] = var7;
               this.headB[5] = var8;
            }

         }
      }
   }

   private void drawHead(DrawingPanel var1, Graphics2D var2, int var3, int var4, Color var5, Paint var6) {
      var2.setStroke(super.style.edgeStroke);
      if (this.headPoints == 0) {
         var2.setColor(var5);
         var2.drawLine(var3, var4, this.aCoord[this.div], this.bCoord[this.div]);
      } else {
         int var7 = this.headPoints - 1;
         this.headA[var7] = var3;
         this.headB[var7] = var4;
         var2.setPaint(var6);
         var2.fillPolygon(this.headA, this.headB, var7);
         var2.setColor(var5);
         var2.drawPolyline(this.headA, this.headB, this.headPoints);
      }

   }
}
