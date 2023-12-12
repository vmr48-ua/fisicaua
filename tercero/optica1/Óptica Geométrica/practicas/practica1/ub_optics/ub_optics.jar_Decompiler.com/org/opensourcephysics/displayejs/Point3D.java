package org.opensourcephysics.displayejs;

import java.text.DecimalFormat;

public class Point3D {
   protected static final DecimalFormat scientificFormat = new DecimalFormat("0.###E0");
   protected static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
   public double x = 0.0D;
   public double y = 0.0D;
   public double z = 0.0D;

   public Point3D(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
   }

   public void setXYZ(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
   }

   public double[] toArray() {
      return new double[]{this.x, this.y, this.z};
   }

   public String toString() {
      String var1;
      if (!(Math.abs(this.x) > 100.0D) && !(Math.abs(this.x) < 0.01D) && !(Math.abs(this.y) > 100.0D) && !(Math.abs(this.y) < 0.01D)) {
         var1 = "x=" + decimalFormat.format(this.x) + "  y=" + decimalFormat.format(this.y) + "  z=" + decimalFormat.format(this.z);
      } else {
         var1 = "x=" + scientificFormat.format(this.x) + "  y=" + scientificFormat.format(this.y) + "  z=" + scientificFormat.format(this.z);
      }

      return var1;
   }
}
