package org.opensourcephysics.display.axes;

import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import org.opensourcephysics.display.DrawingPanel;

public class CartesianCoordinateStringBuilder extends CoordinateStringBuilder {
   protected DecimalFormat scientificFormat;
   protected DecimalFormat decimalFormat;
   protected String xLabel;
   protected String yLabel;

   public CartesianCoordinateStringBuilder() {
      this("x=", "  y=");
   }

   public CartesianCoordinateStringBuilder(String var1, String var2) {
      this.scientificFormat = new DecimalFormat("0.###E0");
      this.decimalFormat = new DecimalFormat("0.00");
      this.xLabel = "x=";
      this.yLabel = "  y=";
      this.xLabel = var1;
      this.yLabel = var2;
   }

   public void setCoordinateLabels(String var1, String var2) {
      this.xLabel = var1;
      this.yLabel = var2;
   }

   public String getCoordinateString(DrawingPanel var1, MouseEvent var2) {
      double var3 = var1.pixToX(var2.getPoint().x);
      double var5 = var1.pixToY(var2.getPoint().y);
      String var7 = "";
      if (!(Math.abs(var3) > 100.0D) && !(Math.abs(var3) < 0.01D) && !(Math.abs(var5) > 100.0D) && !(Math.abs(var5) < 0.01D)) {
         if (this.xLabel != null) {
            var7 = this.xLabel + this.decimalFormat.format((double)((float)var3));
         }

         if (this.yLabel != null) {
            var7 = var7 + this.yLabel + this.decimalFormat.format((double)((float)var5));
         }
      } else {
         if (this.xLabel != null) {
            var7 = this.xLabel + this.scientificFormat.format((double)((float)var3));
         }

         if (this.yLabel != null) {
            var7 = var7 + this.yLabel + this.scientificFormat.format((double)((float)var5));
         }
      }

      return var7;
   }
}
