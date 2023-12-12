package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class DrawableTextLine extends TextLine implements Drawable {
   double x;
   double y;
   double theta = 0.0D;
   protected boolean pixelXY = false;

   public DrawableTextLine(String var1, double var2, double var4) {
      super(var1);
      this.x = var2;
      this.y = var4;
      super.color = Color.BLACK;
   }

   public void setPixelXY(boolean var1) {
      this.pixelXY = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void setTheta(double var1) {
      this.theta = var1;
   }

   public double getX() {
      return this.x;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public double getY() {
      return this.y;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (super.text != null && !super.text.equals("")) {
         Font var3 = var2.getFont();
         if (this.pixelXY) {
            this.drawWithPix(var1, var2);
         } else {
            this.drawWithWorld(var1, var2);
         }

         var2.setFont(var3);
      }
   }

   void drawWithPix(DrawingPanel var1, Graphics var2) {
      if (this.theta != 0.0D) {
         ((Graphics2D)var2).transform(AffineTransform.getRotateInstance(-this.theta, this.x, this.y));
         this.drawText(var2, (int)this.x, (int)this.y);
         ((Graphics2D)var2).transform(AffineTransform.getRotateInstance(this.theta, this.x, this.y));
      } else {
         this.drawText(var2, (int)this.x, (int)this.y);
      }

   }

   void drawWithWorld(DrawingPanel var1, Graphics var2) {
      Double var3 = new Double(this.x, this.y);
      Point2D var4 = var1.getPixelTransform().transform(var3, var3);
      if (this.theta != 0.0D) {
         ((Graphics2D)var2).transform(AffineTransform.getRotateInstance(-this.theta, var4.getX(), var4.getY()));
         this.drawText(var2, (int)var4.getX(), (int)var4.getY());
         ((Graphics2D)var2).transform(AffineTransform.getRotateInstance(this.theta, var4.getX(), var4.getY()));
      } else {
         this.drawText(var2, (int)var4.getX(), (int)var4.getY());
      }

   }

   public static XML.ObjectLoader getLoader() {
      return new DrawableTextLine.DrawableTextLineLoader();
   }

   protected static class DrawableTextLineLoader extends XMLLoader {
      public void saveObject(XMLControl var1, Object var2) {
         DrawableTextLine var3 = (DrawableTextLine)var2;
         var1.setValue("text", var3.getText());
         var1.setValue("x", var3.x);
         var1.setValue("y", var3.y);
         var1.setValue("theta", var3.theta);
         var1.setValue("color", var3.color);
         var1.setValue("pixel position", var3.pixelXY);
      }

      public Object createObject(XMLControl var1) {
         return new DrawableTextLine("", 0.0D, 0.0D);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DrawableTextLine var3 = (DrawableTextLine)var2;
         var3.x = var1.getDouble("x");
         var3.y = var1.getDouble("y");
         var3.theta = var1.getDouble("theta");
         var3.pixelXY = var1.getBoolean("pixel position");
         var3.setText(var1.getString("text"));
         var3.color = (Color)var1.getObject("color");
         return var2;
      }
   }
}
