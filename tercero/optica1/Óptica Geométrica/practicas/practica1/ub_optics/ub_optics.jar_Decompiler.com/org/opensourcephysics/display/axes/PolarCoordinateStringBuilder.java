package org.opensourcephysics.display.axes;

import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import org.opensourcephysics.display.DrawingPanel;

public class PolarCoordinateStringBuilder extends CoordinateStringBuilder {
   protected DecimalFormat scientificFormat;
   protected DecimalFormat decimalFormat;
   protected String rLabel;
   protected String phiLabel;
   protected double sin;
   protected double cos;

   public PolarCoordinateStringBuilder() {
      this("r=", "  phi=");
   }

   public PolarCoordinateStringBuilder(String var1, String var2, double var3) {
      this(var1, var2);
      this.sin = -Math.sin(var3);
      this.cos = Math.cos(var3);
   }

   public PolarCoordinateStringBuilder(String var1, String var2) {
      this.scientificFormat = new DecimalFormat("0.###E0");
      this.decimalFormat = new DecimalFormat("0.00");
      this.rLabel = "r=";
      this.phiLabel = " phi=";
      this.sin = 0.0D;
      this.cos = 1.0D;
      this.rLabel = var1;
      this.phiLabel = var2;
   }

   public void setCoordinateLabels(String var1, String var2) {
      this.rLabel = var1;
      this.phiLabel = var2;
   }

   public String getCoordinateString(DrawingPanel var1, MouseEvent var2) {
      double var3 = var1.pixToX(var2.getPoint().x);
      double var5 = var1.pixToY(var2.getPoint().y);
      double var7 = Math.sqrt(var3 * var3 + var5 * var5);
      String var9;
      if (!(var7 > 100.0D) && !(var7 < 0.01D)) {
         var9 = this.rLabel + this.decimalFormat.format((double)((float)var7));
      } else {
         var9 = this.rLabel + this.scientificFormat.format((double)((float)var7));
      }

      var9 = var9 + this.phiLabel + this.decimalFormat.format((double)(180.0F * (float)Math.atan2(var3 * this.sin + var5 * this.cos, var3 * this.cos - var5 * this.sin)) / 3.141592653589793D);
      return var9;
   }
}
