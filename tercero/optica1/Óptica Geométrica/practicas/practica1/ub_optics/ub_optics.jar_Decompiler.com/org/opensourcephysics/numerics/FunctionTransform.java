package org.opensourcephysics.numerics;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;

public final class FunctionTransform extends AffineTransform {
   double m00;
   double m10 = 1.0D;
   double m01 = 1.0D;
   double m11;
   double m02;
   double m12;
   double[] flatmatrix = new double[6];
   InvertibleFunction xFunction;
   InvertibleFunction yFunction;
   boolean applyXFunction = false;
   boolean applyYFunction = false;

   public FunctionTransform() {
   }

   public FunctionTransform(double var1, double var3, double var5, double var7, double var9, double var11) {
      super(var1, var3, var5, var7, var9, var11);
      this.m00 = var1;
      this.m10 = var3;
      this.m01 = var5;
      this.m11 = var7;
      this.m02 = var9;
      this.m12 = var11;
   }

   public void setXFunction(InvertibleFunction var1) {
      if (var1 == null) {
         throw new NullPointerException("x function can not be null.");
      } else {
         this.xFunction = var1;
      }
   }

   public void setYFunction(InvertibleFunction var1) {
      if (var1 == null) {
         throw new NullPointerException("y function can not be null.");
      } else {
         this.yFunction = var1;
      }
   }

   public void setApplyXFunction(boolean var1) {
      this.applyXFunction = var1;
   }

   public void setApplyYFunction(boolean var1) {
      this.applyYFunction = var1;
   }

   public void translate(double var1, double var3) {
      super.translate(var1, var3);
      this.updateMatrix();
   }

   public void rotate(double var1) {
      super.rotate(var1);
      this.updateMatrix();
   }

   public void rotate(double var1, double var3, double var5) {
      super.rotate(var1, var3, var5);
      this.updateMatrix();
   }

   public void scale(double var1, double var3) {
      super.scale(var1, var3);
      this.updateMatrix();
   }

   public void shear(double var1, double var3) {
      super.shear(var1, var3);
      this.updateMatrix();
   }

   public void setToIdentity() {
      super.setToIdentity();
      this.updateMatrix();
   }

   public void setToTranslation(double var1, double var3) {
      super.setToTranslation(var1, var3);
      this.updateMatrix();
   }

   public void setToRotation(double var1) {
      super.setToRotation(var1);
      this.updateMatrix();
   }

   public void setToRotation(double var1, double var3, double var5) {
      super.setToRotation(var1, var3, var5);
      this.updateMatrix();
   }

   public void setToScale(double var1, double var3) {
      super.setToScale(var1, var3);
      this.updateMatrix();
   }

   public void setToShear(double var1, double var3) {
      super.setToShear(var1, var3);
      this.updateMatrix();
   }

   public void setTransform(AffineTransform var1) {
      super.setTransform(var1);
      this.updateMatrix();
   }

   public void setTransform(double var1, double var3, double var5, double var7, double var9, double var11) {
      super.setTransform(var1, var3, var5, var7, var9, var11);
      this.updateMatrix();
   }

   public void concatenate(AffineTransform var1) {
      super.concatenate(var1);
      this.updateMatrix();
   }

   public void preConcatenate(AffineTransform var1) {
      super.preConcatenate(var1);
      this.updateMatrix();
   }

   public AffineTransform createInverse() throws NoninvertibleTransformException {
      AffineTransform var1 = super.createInverse();
      FunctionTransform var2 = new FunctionTransform();
      var2.setTransform(var1);
      InvertibleFunction var3 = new InvertibleFunction() {
         public double evaluate(double var1) {
            return FunctionTransform.this.xFunction.getInverse(var1);
         }

         public double getInverse(double var1) {
            return FunctionTransform.this.xFunction.evaluate(var1);
         }
      };
      InvertibleFunction var4 = new InvertibleFunction() {
         public double evaluate(double var1) {
            return FunctionTransform.this.yFunction.getInverse(var1);
         }

         public double getInverse(double var1) {
            return FunctionTransform.this.yFunction.evaluate(var1);
         }
      };
      var2.setXFunction(var3);
      var2.setYFunction(var4);
      return var2;
   }

   public Point2D transform(Point2D var1, Point2D var2) {
      if (var2 == null) {
         if (var1 instanceof Double) {
            var2 = new Double();
         } else {
            var2 = new Float();
         }
      }

      double var3 = var1.getX();
      double var5 = var1.getY();
      if (this.applyXFunction) {
         var3 = this.xFunction.evaluate(var3);
      }

      if (this.applyYFunction) {
         var5 = this.yFunction.evaluate(var5);
      }

      ((Point2D)var2).setLocation(var3 * this.m00 + var5 * this.m01 + this.m02, var3 * this.m10 + var5 * this.m11 + this.m12);
      return (Point2D)var2;
   }

   public void transform(Point2D[] var1, int var2, Point2D[] var3, int var4, int var5) {
      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         Point2D var6 = var1[var2++];
         double var7 = var6.getX();
         double var9 = var6.getY();
         if (this.applyXFunction) {
            var7 = this.xFunction.evaluate(var7);
         }

         if (this.applyYFunction) {
            var9 = this.yFunction.evaluate(var9);
         }

         Object var11 = var3[var4++];
         if (var11 == null) {
            if (var6 instanceof Double) {
               var11 = new Double();
            } else {
               var11 = new Float();
            }

            var3[var4 - 1] = (Point2D)var11;
         }

         ((Point2D)var11).setLocation(var7 * this.m00 + var9 * this.m01 + this.m02, var7 * this.m10 + var9 * this.m11 + this.m12);
      }
   }

   public void transform(float[] var1, int var2, float[] var3, int var4, int var5) {
      if (var3 == var1 && var4 > var2 && var4 < var2 + var5 * 2) {
         System.arraycopy(var1, var2, var3, var4, var5 * 2);
         var2 = var4;
      }

      double var6 = this.m00;
      double var8 = this.m01;
      double var10 = this.m02;
      double var12 = this.m10;
      double var14 = this.m11;
      double var16 = this.m12;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         double var18 = (double)var1[var2++];
         double var20 = (double)var1[var2++];
         if (this.applyXFunction) {
            var18 = this.xFunction.evaluate(var18);
         }

         if (this.applyYFunction) {
            var20 = this.yFunction.evaluate(var20);
         }

         var3[var4++] = (float)(var6 * var18 + var8 * var20 + var10);
         var3[var4++] = (float)(var12 * var18 + var14 * var20 + var16);
      }
   }

   public void transform(double[] var1, int var2, double[] var3, int var4, int var5) {
      if (var3 == var1 && var4 > var2 && var4 < var2 + var5 * 2) {
         System.arraycopy(var1, var2, var3, var4, var5 * 2);
         var2 = var4;
      }

      double var6 = this.m00;
      double var8 = this.m01;
      double var10 = this.m02;
      double var12 = this.m10;
      double var14 = this.m11;
      double var16 = this.m12;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         double var18 = var1[var2++];
         double var20 = var1[var2++];
         if (this.applyXFunction) {
            var18 = this.xFunction.evaluate(var18);
         }

         if (this.applyYFunction) {
            var20 = this.yFunction.evaluate(var20);
         }

         var3[var4++] = var6 * var18 + var8 * var20 + var10;
         var3[var4++] = var12 * var18 + var14 * var20 + var16;
      }
   }

   public void transform(float[] var1, int var2, double[] var3, int var4, int var5) {
      double var6 = this.m00;
      double var8 = this.m01;
      double var10 = this.m02;
      double var12 = this.m10;
      double var14 = this.m11;
      double var16 = this.m12;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         double var18 = (double)var1[var2++];
         double var20 = (double)var1[var2++];
         if (this.applyXFunction) {
            var18 = this.xFunction.evaluate(var18);
         }

         if (this.applyYFunction) {
            var20 = this.yFunction.evaluate(var20);
         }

         var3[var4++] = var6 * var18 + var8 * var20 + var10;
         var3[var4++] = var12 * var18 + var14 * var20 + var16;
      }
   }

   public void transform(double[] var1, int var2, float[] var3, int var4, int var5) {
      double var6 = this.m00;
      double var8 = this.m01;
      double var10 = this.m02;
      double var12 = this.m10;
      double var14 = this.m11;
      double var16 = this.m12;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         double var18 = var1[var2++];
         double var20 = var1[var2++];
         if (this.applyXFunction) {
            var18 = this.xFunction.evaluate(var18);
         }

         if (this.applyYFunction) {
            var20 = this.yFunction.evaluate(var20);
         }

         var3[var4++] = (float)(var6 * var18 + var8 * var20 + var10);
         var3[var4++] = (float)(var12 * var18 + var14 * var20 + var16);
      }
   }

   public Point2D inverseTransform(Point2D var1, Point2D var2) throws NoninvertibleTransformException {
      if (var2 == null) {
         if (var1 instanceof Double) {
            var2 = new Double();
         } else {
            var2 = new Float();
         }
      }

      return (Point2D)var2;
   }

   public void inverseTransform(double[] var1, int var2, double[] var3, int var4, int var5) throws NoninvertibleTransformException {
      if (var3 == var1 && var4 > var2 && var4 < var2 + var5 * 2) {
         System.arraycopy(var1, var2, var3, var4, var5 * 2);
      }

      double var6 = this.m00 * this.m11 - this.m01 * this.m10;
      if (Math.abs(var6) <= java.lang.Double.MIN_VALUE) {
         throw new NoninvertibleTransformException("Determinant is " + var6);
      }
   }

   public Point2D deltaTransform(Point2D var1, Point2D var2) {
      if (var2 == null) {
         if (var1 instanceof Double) {
            var2 = new Double();
         } else {
            var2 = new Float();
         }
      }

      double var3 = var1.getX();
      double var5 = var1.getY();
      if (this.applyXFunction) {
         var3 = this.xFunction.evaluate(var3);
      }

      if (this.applyYFunction) {
         var5 = this.yFunction.evaluate(var5);
      }

      ((Point2D)var2).setLocation(var3 * this.m00 + var5 * this.m01, var3 * this.m10 + var5 * this.m11);
      return (Point2D)var2;
   }

   public void deltaTransform(double[] var1, int var2, double[] var3, int var4, int var5) {
      if (var3 == var1 && var4 > var2 && var4 < var2 + var5 * 2) {
         System.arraycopy(var1, var2, var3, var4, var5 * 2);
         var2 = var4;
      }

      double var6 = this.m00;
      double var8 = this.m01;
      double var10 = this.m10;
      double var12 = this.m11;

      while(true) {
         --var5;
         if (var5 < 0) {
            return;
         }

         double var14 = var1[var2++];
         double var16 = var1[var2++];
         if (this.applyXFunction) {
            var14 = this.xFunction.evaluate(var14);
         }

         if (this.applyYFunction) {
            var16 = this.yFunction.evaluate(var16);
         }

         var3[var4++] = var14 * var6 + var16 * var8;
         var3[var4++] = var14 * var10 + var16 * var12;
      }
   }

   public boolean equals(Object var1) {
      if (var1 instanceof FunctionTransform) {
         FunctionTransform var2 = (FunctionTransform)var1;
         double[] var3 = new double[6];
         var2.getMatrix(var3);
         if (this.m00 == var3[0] && this.m01 == var3[1] && this.m02 == var3[2] && this.m10 == var3[3] && this.m11 == var3[4] && this.m12 == var3[5] && this.applyXFunction == var2.applyXFunction && this.applyYFunction == var2.applyYFunction) {
            return this.xFunction.getClass() == var2.xFunction.getClass() && this.yFunction.getClass() == var2.yFunction.getClass();
         }
      } else if (var1 instanceof AffineTransform && !this.applyXFunction && !this.applyYFunction) {
         return super.equals(var1);
      }

      return false;
   }

   private void updateMatrix() {
      this.getMatrix(this.flatmatrix);
      this.m00 = this.flatmatrix[0];
      this.m10 = this.flatmatrix[1];
      this.m01 = this.flatmatrix[2];
      this.m11 = this.flatmatrix[3];
      this.m02 = this.flatmatrix[4];
      this.m12 = this.flatmatrix[5];
   }
}
