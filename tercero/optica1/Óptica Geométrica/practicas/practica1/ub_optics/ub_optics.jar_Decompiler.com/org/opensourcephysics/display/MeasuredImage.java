package org.opensourcephysics.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MeasuredImage implements Measurable {
   protected BufferedImage image;
   protected double xmin;
   protected double xmax;
   protected double ymin;
   protected double ymax;
   protected boolean visible;

   public MeasuredImage() {
      this((BufferedImage)null, 0.0D, 0.0D, 0.0D, 0.0D);
   }

   public MeasuredImage(BufferedImage var1) {
      this(var1, 0.0D, (double)var1.getWidth(), 0.0D, (double)var1.getHeight());
   }

   public MeasuredImage(BufferedImage var1, double var2, double var4, double var6, double var8) {
      this.visible = true;
      this.image = var1;
      this.xmin = var2;
      this.xmax = var4;
      this.ymin = var6;
      this.ymax = var8;
   }

   public void setImage(BufferedImage var1) {
      this.image = var1;
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.visible) {
         if (this.image == null) {
            var1.setMessage("No image");
         } else {
            Graphics2D var3 = (Graphics2D)var2;
            AffineTransform var4 = var3.getTransform();
            RenderingHints var5 = var3.getRenderingHints();
            var3.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
            var3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            var3.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            double var6 = (this.xmax - this.xmin) * var1.xPixPerUnit / (double)this.image.getWidth();
            double var8 = (this.ymax - this.ymin) * var1.yPixPerUnit / (double)this.image.getHeight();
            var3.transform(AffineTransform.getTranslateInstance((double)var1.leftGutter + var1.xPixPerUnit * (this.xmin - var1.xmin), (double)var1.topGutter + var1.yPixPerUnit * (var1.ymax - this.ymax)));
            var3.transform(AffineTransform.getScaleInstance(var6, var8));
            var3.drawImage(this.image, 0, 0, var1);
            var3.setTransform(var4);
            var3.setRenderingHints(var5);
         }
      }
   }

   public boolean isMeasured() {
      return this.image != null;
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

   public void setXMin(double var1) {
      this.xmin = var1;
   }

   public void setXMax(double var1) {
      this.xmax = var1;
   }

   public void setYMin(double var1) {
      this.ymin = var1;
   }

   public void setYMax(double var1) {
      this.ymax = var1;
   }

   public void setMinMax(double var1, double var3, double var5, double var7) {
      this.xmin = var1;
      this.xmax = var3;
      this.ymin = var5;
      this.ymax = var7;
   }
}
