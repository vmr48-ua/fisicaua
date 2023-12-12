package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import org.opensourcephysics.controls.XML;

public class Circle implements Drawable {
   public Color color;
   public int pixRadius;
   protected double x;
   protected double y;

   public Circle() {
      this(0.0D, 0.0D);
   }

   public Circle(double var1, double var3) {
      this.color = Color.red;
      this.pixRadius = 6;
      this.x = 0.0D;
      this.y = 0.0D;
      this.x = var1;
      this.y = var3;
   }

   public Circle(double var1, double var3, int var5) {
      this.color = Color.red;
      this.pixRadius = 6;
      this.x = 0.0D;
      this.y = 0.0D;
      this.x = var1;
      this.y = var3;
      this.pixRadius = var5;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      int var3 = var1.xToPix(this.x) - this.pixRadius;
      int var4 = var1.yToPix(this.y) - this.pixRadius;
      var2.setColor(this.color);
      var2.fillOval(var3, var4, 2 * this.pixRadius, 2 * this.pixRadius);
   }

   public double getX() {
      return this.x;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public void setXY(double var1, double var3) {
      this.x = var1;
      this.y = var3;
   }

   public String toString() {
      String var1 = this.getClass().getName();
      var1 = var1.substring(1 + var1.lastIndexOf(".")) + '[';
      var1 = var1 + "x=" + this.x;
      var1 = var1 + ",y=" + this.y;
      var1 = var1 + ",r_pix=" + this.pixRadius + ']';
      return var1;
   }

   public static XML.ObjectLoader getLoader() {
      return new CircleLoader();
   }
}
