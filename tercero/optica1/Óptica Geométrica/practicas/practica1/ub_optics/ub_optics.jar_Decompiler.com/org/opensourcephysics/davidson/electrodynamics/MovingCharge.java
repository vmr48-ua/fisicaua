package org.opensourcephysics.davidson.electrodynamics;

import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.numerics.Derivative;
import org.opensourcephysics.numerics.Function;
import org.opensourcephysics.numerics.ParsedFunction;
import org.opensourcephysics.numerics.ParserException;
import org.opensourcephysics.numerics.VectorMath;

public class MovingCharge implements Drawable {
   int pixRadius = 5;
   Color color;
   double t;
   double dt;
   int numPts;
   double[][] path;
   double[] r;
   double[] v;
   double[] u;
   double[] a;
   double[] temp;
   Function xpos;
   Function ypos;

   public MovingCharge() {
      this.color = Color.red;
      this.t = 0.0D;
      this.dt = 1.0E-6D;
      this.numPts = 0;
      this.path = new double[3][1024];
      this.r = new double[2];
      this.v = new double[2];
      this.u = new double[2];
      this.a = new double[2];
      this.temp = new double[2];
      this.xpos = new MovingCharge.XFunction();
      this.ypos = new MovingCharge.YFunction();
      this.reset();
   }

   private void resizePath() {
      int var1 = this.path[0].length;
      if (var1 > 32768) {
         System.arraycopy(this.path[0], var1 / 2, this.path[0], 0, var1 / 2);
         System.arraycopy(this.path[1], var1 / 2, this.path[1], 0, var1 / 2);
         System.arraycopy(this.path[2], var1 / 2, this.path[2], 0, var1 / 2);
         this.numPts = var1 / 2;
      } else {
         double[][] var2 = new double[3][2 * var1];
         System.arraycopy(this.path[0], 0, var2[0], 0, var1);
         System.arraycopy(this.path[1], 0, var2[1], 0, var1);
         System.arraycopy(this.path[2], 0, var2[2], 0, var1);
         this.path = var2;
      }
   }

   void step() {
      this.t += this.dt;
      if (this.numPts >= this.path[0].length) {
         this.resizePath();
      }

      this.path[0][this.numPts] = this.t;
      this.path[1][this.numPts] = this.xpos.evaluate(this.t);
      this.path[2][this.numPts] = this.ypos.evaluate(this.t);
      ++this.numPts;
   }

   void reset() {
      this.numPts = 0;
      this.t = 0.0D;
      this.path = new double[3][1024];
      this.path[0][this.numPts] = this.t;
      this.path[1][this.numPts] = this.xpos.evaluate(this.t);
      this.path[2][this.numPts] = this.ypos.evaluate(this.t);
      ++this.numPts;
   }

   void calcStationaryField(double var1, double var3, double[] var5) {
      double var6 = var1 - this.path[1][0];
      double var8 = var3 - this.path[2][0];
      double var10 = var6 * var6 + var8 * var8;
      double var12 = var10 * Math.sqrt(var10);
      double var14 = var6 / var12;
      double var16 = var8 / var12;
      double var18 = Math.sqrt(var14 * var14 + var16 * var16);
      var5[0] = var14 / var18;
      var5[1] = var16 / var18;
      var5[2] = var18;
      var5[3] = 0.0D;
   }

   double getMetric(int var1, double var2, double var4, double var6) {
      double var8 = var2 - this.path[0][var1];
      double var10 = var4 - this.path[1][var1];
      double var12 = var6 - this.path[2][var1];
      return Math.sqrt(var10 * var10 + var12 * var12) - var8;
   }

   synchronized void calcRetardedField(double var1, double var3, double[] var5) {
      int var6 = 0;
      int var7 = this.numPts - 1;
      double var8 = this.getMetric(var6, this.t, var1, var3);
      if (var8 >= 0.0D) {
         this.calcStationaryField(var1, var3, var5);
      } else {
         while(var8 < 0.0D && var7 - var6 > 1) {
            int var10 = var6 + (var7 - var6) / 2;
            double var11 = this.getMetric(var10, this.t, var1, var3);
            if (var11 <= 0.0D) {
               var8 = var11;
               var6 = var10;
            } else {
               var7 = var10;
            }
         }

         double var28 = this.path[0][var6];
         this.r[0] = var1 - this.xpos.evaluate(var28);
         this.r[1] = var3 - this.ypos.evaluate(var28);
         this.v[0] = Derivative.centered(this.xpos, var28, this.dt);
         this.v[1] = Derivative.centered(this.ypos, var28, this.dt);
         this.a[0] = Derivative.second(this.xpos, var28, this.dt);
         this.a[1] = Derivative.second(this.ypos, var28, this.dt);
         double var12 = VectorMath.magnitude(this.r);
         this.u[0] = this.r[0] / var12 - this.v[0];
         this.u[1] = this.r[1] / var12 - this.v[1];
         double var14 = VectorMath.dot(this.r, this.u);
         double var16 = var12 / var14 / var14 / var14;
         double var18 = VectorMath.cross2D(this.u, this.a);
         this.temp[0] = this.r[0];
         this.temp[1] = this.r[1];
         this.temp = VectorMath.cross2D(this.temp, var18);
         double var20 = 1.0D - VectorMath.magnitudeSquared(this.v);
         double var22 = var16 * (this.u[0] * var20 + this.temp[0]);
         double var24 = var16 * (this.u[1] * var20 + this.temp[1]);
         this.temp[0] = var22;
         this.temp[1] = var24;
         double var26 = Math.sqrt(var22 * var22 + var24 * var24);
         var5[0] = var22 / var26;
         var5[1] = var24 / var26;
         var5[2] = var26;
         var5[3] = var16 * VectorMath.cross2D(this.temp, this.r) / var12;
      }
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      int var3 = var1.xToPix(this.xpos.evaluate(this.t)) - this.pixRadius;
      int var4 = var1.yToPix(this.ypos.evaluate(this.t)) - this.pixRadius;
      var2.setColor(this.color);
      var2.fillOval(var3, var4, 2 * this.pixRadius, 2 * this.pixRadius);
   }

   String setXFunction(String var1) {
      try {
         this.xpos = new ParsedFunction(var1, "t");
         return null;
      } catch (ParserException var3) {
         System.err.println(var3.getMessage());
         this.xpos = new MovingCharge.XFunction();
         return var3.getMessage();
      }
   }

   String setYFunction(String var1) {
      try {
         this.ypos = new ParsedFunction(var1, "t");
         return null;
      } catch (ParserException var3) {
         System.err.println(var3.getMessage());
         this.ypos = new MovingCharge.YFunction();
         return var3.getMessage();
      }
   }

   class YFunction implements Function {
      public double evaluate(double var1) {
         return 0.0D;
      }
   }

   class XFunction implements Function {
      public double evaluate(double var1) {
         return 5.0D * Math.cos(var1 * 0.9D / 5.0D);
      }
   }
}
