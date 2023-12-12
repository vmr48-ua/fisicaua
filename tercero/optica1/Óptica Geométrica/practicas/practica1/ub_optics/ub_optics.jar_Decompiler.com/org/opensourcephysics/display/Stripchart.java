package org.opensourcephysics.display;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;

public class Stripchart extends Dataset {
   boolean rightToLeft;
   double xrange;
   double yrange;
   double lastx;
   boolean enabled;

   public Stripchart(double var1, double var3) {
      this.rightToLeft = true;
      this.enabled = true;
      this.xrange = Math.abs(var1);
      this.yrange = Math.abs(var3);
   }

   protected Stripchart() {
      this(1.0D, 10.0D);
   }

   public void setRange(double var1, double var3) {
      this.xrange = Math.abs(var1);
      this.yrange = Math.abs(var3);
   }

   public void enable(boolean var1) {
      this.enabled = var1;
   }

   public void append(double var1, double var3) {
      if (!this.enabled) {
         super.append(var1, var3);
      } else {
         if (super.index != 0 && var1 < this.lastx) {
            this.clear();
         }

         this.lastx = var1;
         super.append(var1, var3);
         this.trim();
      }
   }

   public void append(double[] var1, double[] var2) {
      if (!this.enabled) {
         super.append(var1, var2);
      } else {
         if (super.index != 0 && var1[0] < this.lastx) {
            this.clear();
         }

         int var3 = 1;

         for(int var4 = var1.length; var3 < var4; ++var3) {
            if (var1[var3] < var1[var3 - 1]) {
               this.clear();
               return;
            }
         }

         this.lastx = var1[var1.length - 1];
         super.append(var1, var2);
         this.trim();
      }
   }

   public void clear() {
      super.clear();
      this.lastx = super.xpoints[0];
   }

   private void trim() {
      int var1;
      if (super.index > 0 && super.xpoints[0] < this.lastx - this.xrange) {
         for(var1 = 0; var1 < super.index && super.xpoints[var1] < this.lastx - this.xrange; ++var1) {
         }

         System.arraycopy(super.xpoints, var1, super.xpoints, 0, super.index - var1);
         System.arraycopy(super.ypoints, var1, super.ypoints, 0, super.index - var1);
         super.index -= var1;
      }

      if (this.rightToLeft) {
         super.xmin = this.lastx - this.xrange;
      } else {
         super.xmin = this.lastx;
      }

      if (this.rightToLeft) {
         super.xmax = this.lastx;
      } else {
         super.xmax = this.lastx - this.xrange;
      }

      super.ymin = super.ymax = super.ypoints[0];

      for(var1 = 1; var1 < super.index; ++var1) {
         super.ymin = Math.min(super.ymin, super.ypoints[var1]);
         super.ymax = Math.max(super.ymax, super.ypoints[var1]);
      }

      if (super.ymax - super.ymin < this.yrange) {
         super.ymin = (super.ymax + super.ymin - this.yrange) / 2.0D;
         super.ymax = (super.ymax + super.ymin + this.yrange) / 2.0D;
      }

      this.recalculatePath();
   }

   public static XML.ObjectLoader getLoader() {
      return new Stripchart.StripchartLoader();
   }

   protected static class StripchartLoader extends Dataset.Loader {
      public void saveObject(XMLControl var1, Object var2) {
         super.saveObject(var1, var2);
         Stripchart var3 = (Stripchart)var2;
         var1.setValue("x_range", var3.xrange);
         var1.setValue("y_range", var3.yrange);
         var1.setValue("last_x", var3.lastx);
         var1.setValue("right_to_left", var3.rightToLeft);
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Stripchart var3 = (Stripchart)var2;
         var3.xrange = var1.getDouble("x_range");
         var3.yrange = var1.getDouble("y_range");
         var3.lastx = var1.getDouble("last_x");
         var3.rightToLeft = var1.getBoolean("right_to_left");
         super.loadObject(var1, var2);
         return var2;
      }
   }
}
