package org.opensourcephysics.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public class Trail implements Drawable, Measurable {
   GeneralPath generalPath = new GeneralPath();
   int numpts = 0;
   boolean connected = true;
   public Color color;
   boolean enableMeasure;
   double xmin;
   double xmax;
   double ymin;
   double ymax;

   public Trail() {
      this.color = Color.black;
      this.enableMeasure = false;
      this.xmin = Double.MAX_VALUE;
      this.xmax = -1.7976931348623157E308D;
      this.ymin = Double.MAX_VALUE;
      this.ymax = -1.7976931348623157E308D;
   }

   public synchronized void addPoint(double var1, double var3) {
      if (!this.connected || this.numpts == 0) {
         this.generalPath.moveTo((float)var1, (float)var3);
      }

      this.generalPath.lineTo((float)var1, (float)var3);
      this.xmin = Math.min(this.xmin, var1);
      this.xmax = Math.max(this.xmax, var1);
      this.ymin = Math.min(this.ymin, var3);
      this.ymax = Math.max(this.ymax, var3);
      ++this.numpts;
   }

   public synchronized void moveToPoint(double var1, double var3) {
      this.generalPath.moveTo((float)var1, (float)var3);
      this.xmin = Math.min(this.xmin, var1);
      this.xmax = Math.max(this.xmax, var1);
      this.ymin = Math.min(this.ymin, var3);
      this.ymax = Math.max(this.ymax, var3);
      ++this.numpts;
   }

   public void setConnected(boolean var1) {
      this.connected = var1;
   }

   public boolean isConnected(boolean var1) {
      return var1;
   }

   public synchronized void clear() {
      this.numpts = 0;
      this.xmin = Double.MAX_VALUE;
      this.xmax = -1.7976931348623157E308D;
      this.ymin = Double.MAX_VALUE;
      this.ymax = -1.7976931348623157E308D;
      this.generalPath.reset();
   }

   public void draw(DrawingPanel var1, Graphics var2) {
      if (this.numpts != 0) {
         Graphics2D var3 = (Graphics2D)var2;
         var3.setColor(this.color);
         Shape var4 = this.generalPath.createTransformedShape(var1.getPixelTransform());
         var3.draw(var4);
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new Trail.Loader();
   }

   public void setMeasured(boolean var1) {
      this.enableMeasure = var1;
   }

   public boolean isMeasured() {
      return this.enableMeasure && this.numpts > 0;
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

   private static class Loader extends XMLLoader {
      private Loader() {
      }

      public void saveObject(XMLControl var1, Object var2) {
         Trail var3 = (Trail)var2;
         var1.setValue("connected", var3.connected);
         var1.setValue("color", var3.color);
         var1.setValue("number of pts", var3.numpts);
         var1.setValue("general path", var3.generalPath);
      }

      public Object createObject(XMLControl var1) {
         return new Trail();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Trail var3 = (Trail)var2;
         var3.connected = var1.getBoolean("connected");
         var3.color = (Color)var1.getObject("color");
         var3.numpts = var1.getInt("number of pts");
         var3.generalPath = (GeneralPath)var1.getObject("general path");
         return var2;
      }

      // $FF: synthetic method
      Loader(Object var1) {
         this();
      }
   }
}
