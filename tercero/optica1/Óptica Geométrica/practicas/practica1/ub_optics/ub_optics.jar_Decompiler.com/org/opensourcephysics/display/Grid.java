package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D.Double;

public class Grid implements Drawable {
   protected int nx;
   protected int ny;
   protected double xmin;
   protected double xmax;
   protected double ymin;
   protected double ymax;
   protected double dx;
   protected double dy;
   protected Color color;
   protected GeneralPath generalPath;
   protected boolean visible;

   public Grid(int var1) {
      this(var1, var1, 0.0D, (double)var1, 0.0D, (double)var1);
   }

   public Grid(int var1, int var2) {
      this(var1, var2, 0.0D, (double)var1, 0.0D, (double)var2);
   }

   public Grid(int var1, int var2, double var3, double var5, double var7, double var9) {
      this.color = new Color(200, 200, 200, 100);
      this.generalPath = new GeneralPath();
      this.visible = true;
      this.nx = var1;
      this.ny = var2;
      this.setMinMax(var3, var5, var7, var9);
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setColor(Color var1) {
      this.color = var1;
   }

   public Color getColor() {
      return this.color;
   }

   public double getDx() {
      return this.dx;
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

   public double getDy() {
      return this.dy;
   }

   public void setMinMax(double var1, double var3, double var5, double var7) {
      this.generalPath.reset();
      this.xmin = (double)((float)var1);
      this.xmax = (double)((float)var3);
      this.ymin = (double)((float)var5);
      this.ymax = (double)((float)var7);
      if (this.nx > 0) {
         this.dx = (double)((float)((this.xmax - this.xmin) / (double)this.nx));
      } else {
         this.dx = 1.0D;
      }

      if (this.ny > 0) {
         this.dy = (double)((float)((this.ymax - this.ymin) / (double)this.ny));
      } else {
         this.dy = 1.0D;
      }

      if (this.visible) {
         float var9 = (float)this.ymin;
         if (this.ny <= 512) {
            for(int var10 = 0; var10 <= this.ny; ++var10) {
               this.generalPath.moveTo((float)this.xmin, var9);
               this.generalPath.lineTo((float)this.xmax, var9);
               var9 = (float)((double)var9 + this.dy);
            }
         }

         float var12 = (float)this.xmin;
         if (this.nx <= 512) {
            for(int var11 = 0; var11 <= this.nx; ++var11) {
               this.generalPath.moveTo(var12, (float)this.ymin);
               this.generalPath.lineTo(var12, (float)this.ymax);
               var12 = (float)((double)var12 + this.dx);
            }
         }

      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible) {
         if (!(Math.abs(var1.getXPixPerUnit() * (this.xmax - this.xmin) / (double)this.nx) < 4.0D)) {
            if (!(Math.abs(var1.getYPixPerUnit() * (this.ymax - this.ymin) / (double)this.ny) < 4.0D)) {
               Graphics2D var3 = (Graphics2D)var2;
               AffineTransform var4 = var1.getPixelTransform();
               Shape var5 = this.generalPath.createTransformedShape(var4);
               var3.setColor(this.color);
               var3.draw(var5);
               var3.setColor(Color.black);
            }
         }
      }
   }

   public int[] getCellPoint(double var1, double var3) {
      boolean var5 = false;
      int var7 = (int)Math.floor((var1 - this.xmin) / this.dx);
      var7 = Math.max(0, var7);
      var7 = Math.min(this.nx, var7);
      boolean var6 = false;
      int var8 = (int)Math.floor((var3 - this.ymin) / this.dy);
      var8 = Math.max(0, var8);
      Math.min(this.ny, var8);
      return new int[]{var7, var7};
   }

   public Double getClosestGridPoint(double var1, double var3) {
      boolean var5 = false;
      int var10 = (int)Math.round((var1 - this.xmin) / this.dx);
      var10 = Math.max(0, var10);
      var10 = Math.min(this.nx, var10);
      double var6 = this.xmin + this.dx * (double)var10;
      var10 = (int)Math.round((var3 - this.ymin) / this.dy);
      var10 = Math.max(0, var10);
      var10 = Math.min(this.ny, var10);
      double var8 = this.ymin + this.dy * (double)var10;
      return new Double(var6, var8);
   }
}
