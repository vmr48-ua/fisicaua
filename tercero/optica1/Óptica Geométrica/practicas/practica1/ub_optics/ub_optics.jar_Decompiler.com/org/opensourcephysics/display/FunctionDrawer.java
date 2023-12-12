package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.opensourcephysics.numerics.Function;

public class FunctionDrawer implements Drawable, Measurable, Function {
   private double[] xrange;
   private double[] yrange;
   private int numpts;
   private GeneralPath generalPath;
   private Function function;
   private boolean filled;
   private boolean measured;
   public Color color;
   public boolean functionChanged;

   public FunctionDrawer(Function var1) {
      this.xrange = new double[2];
      this.yrange = new double[2];
      this.numpts = 0;
      this.generalPath = new GeneralPath();
      this.filled = false;
      this.measured = false;
      this.color = Color.black;
      this.functionChanged = false;
      this.function = var1;
   }

   public FunctionDrawer(Function var1, double var2, double var4, int var6, boolean var7) {
      this(var1);
      this.initialize(var2, var4, var6, var7);
   }

   public double evaluate(double var1) {
      return this.function.evaluate(var1);
   }

   public void initialize(double var1, double var3, int var5, boolean var6) {
      if (var5 >= 1) {
         this.filled = var6;
         this.xrange[0] = var1;
         this.xrange[1] = var3;
         this.numpts = var5;
         this.generalPath.reset();
         if (var5 >= 1) {
            this.yrange[0] = this.function.evaluate(var1);
            this.yrange[1] = this.yrange[0];
            if (var6) {
               this.generalPath.moveTo((float)this.xrange[0], 0.0F);
               this.generalPath.lineTo((float)this.xrange[0], (float)this.yrange[0]);
            } else {
               this.generalPath.moveTo((float)this.xrange[0], (float)this.yrange[0]);
            }

            double var7 = this.xrange[0];
            double var9 = (var3 - var1) / (double)var5;

            for(int var11 = 0; var11 < var5; ++var11) {
               var7 += var9;
               double var12 = this.function.evaluate(var7);
               this.generalPath.lineTo((float)var7, (float)var12);
               if (var12 < this.yrange[0]) {
                  this.yrange[0] = var12;
               }

               if (var12 > this.yrange[1]) {
                  this.yrange[1] = var12;
               }
            }

            if (var6) {
               this.generalPath.lineTo((float)var7, 0.0F);
               this.generalPath.closePath();
            }

            this.measured = true;
         }
      }
   }

   public GeneralPath getPath() {
      return (GeneralPath)((GeneralPath)this.generalPath.clone());
   }

   public double[] getXRange() {
      return this.xrange;
   }

   public double[] getYRange() {
      return this.yrange;
   }

   void checkRange(DrawingPanel var1) {
      if (this.xrange[0] != var1.getXMin() || this.xrange[1] != var1.getXMax() || this.numpts != var1.getWidth() || this.functionChanged) {
         this.functionChanged = false;
         this.xrange[0] = var1.getXMin();
         this.xrange[1] = var1.getXMax();
         this.numpts = var1.getWidth();
         this.generalPath.reset();
         if (this.numpts >= 1) {
            this.yrange[0] = this.function.evaluate(this.xrange[0]);
            this.yrange[1] = this.yrange[0];
            if (this.filled) {
               this.generalPath.moveTo((float)this.xrange[0], 0.0F);
               this.generalPath.lineTo((float)this.xrange[0], (float)this.yrange[0]);
            } else {
               this.generalPath.moveTo((float)this.xrange[0], (float)this.yrange[0]);
            }

            double var2 = this.xrange[0];
            double var4 = (this.xrange[1] - this.xrange[0]) / (double)this.numpts;

            for(int var6 = 0; var6 < this.numpts; ++var6) {
               var2 += var4;
               double var7 = this.function.evaluate(var2);
               if (!Double.isNaN(var2) && !Double.isNaN(var7)) {
                  var7 = Math.min(var7, 1.0E12D);
                  var7 = Math.max(var7, -1.0E12D);
                  this.generalPath.lineTo((float)var2, (float)var7);
                  if (var7 < this.yrange[0]) {
                     this.yrange[0] = var7;
                  }

                  if (var7 > this.yrange[1]) {
                     this.yrange[1] = var7;
                  }
               }
            }

            if (this.filled) {
               this.generalPath.lineTo((float)var2, 0.0F);
               this.generalPath.closePath();
            }

         }
      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (!this.measured) {
         this.checkRange(var1);
      }

      Graphics2D var3 = (Graphics2D)var2;
      var3.setColor(this.color);
      Shape var4 = this.generalPath.createTransformedShape(var1.getPixelTransform());
      if (this.filled) {
         var3.fill(var4);
         var3.draw(var4);
      } else {
         var3.draw(var4);
      }

   }

   public void setFilled(boolean var1) {
      this.filled = var1;
   }

   public void setColor(Color var1) {
      this.color = var1;
   }

   public boolean isMeasured() {
      return this.measured;
   }

   public double getXMin() {
      return this.xrange[0];
   }

   public double getXMax() {
      return this.xrange[1];
   }

   public double getYMin() {
      return this.yrange[0];
   }

   public double getYMax() {
      return this.yrange[1];
   }
}
