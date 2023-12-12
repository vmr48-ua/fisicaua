package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D.Double;
import org.opensourcephysics.controls.XML;

public class Arrow implements Drawable {
   protected float headSize = 8.0F;
   protected Color color;
   protected double x;
   protected double y;
   protected double a;
   protected double b;

   public Arrow(double var1, double var3, double var5, double var7) {
      this.color = Color.black;
      this.x = 0.0D;
      this.y = 0.0D;
      this.a = 0.0D;
      this.b = 0.0D;
      this.x = var1;
      this.y = var3;
      this.a = var5;
      this.b = var7;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double var1) {
      this.setXY(var1, this.y);
   }

   public double getY() {
      return this.y;
   }

   public void setY(double var1) {
      this.setXY(this.x, var1);
   }

   public void setXY(double var1, double var3) {
      this.x = var1;
      this.y = var3;
   }

   public void setColor(Color var1) {
      this.color = var1;
   }

   public void setXlength(double var1) {
      this.a = var1;
   }

   public void setYlength(double var1) {
      this.b = var1;
   }

   public double getXlength() {
      return this.a;
   }

   public double getYlength() {
      return this.b;
   }

   public float getHeadSize() {
      return this.headSize;
   }

   public void setHeadSize(float var1) {
      this.headSize = var1;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      Graphics2D var3 = (Graphics2D)var2;
      AffineTransform var4 = var1.getPixelTransform();
      var3.setPaint(this.color);
      var3.draw(var4.createTransformedShape(new Double(this.x, this.y, this.x + this.a, this.y + this.b)));
      java.awt.geom.Point2D.Double var5 = new java.awt.geom.Point2D.Double(this.x + this.a, this.y + this.b);
      Point2D var10 = var4.transform(var5, var5);
      double var6 = var1.isSquareAspect() ? 1.0D : -var4.getScaleX() / var4.getScaleY();
      Shape var8 = this.getHead(Math.atan2(this.b, var6 * this.a));
      Shape var9 = AffineTransform.getTranslateInstance(var10.getX(), var10.getY()).createTransformedShape(var8);
      var3.fill(var9);
      var3.setPaint(Color.BLACK);
   }

   protected Shape getHead(double var1) {
      GeneralPath var3 = new GeneralPath();
      var3.moveTo(1.0F, 0.0F);
      var3.lineTo(-this.headSize, -this.headSize / 2.0F);
      var3.lineTo(-this.headSize, this.headSize / 2.0F);
      var3.closePath();
      AffineTransform var4 = AffineTransform.getRotateInstance(-var1);
      Shape var5 = var4.createTransformedShape(var3);
      return var5;
   }

   public static XML.ObjectLoader getLoader() {
      return new ArrowLoader();
   }
}
