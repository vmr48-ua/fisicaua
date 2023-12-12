package org.opensourcephysics.display.axes;

import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.GUIUtils;

public abstract class CoordinateStringBuilder {
   protected DecimalFormat scientificFormat = new DecimalFormat("0.###E0");
   protected DecimalFormat decimalFormat = new DecimalFormat("0.00");
   protected String xLabel = "x=";
   protected String yLabel = "  y=";

   public void setCoordinateLabels(String var1, String var2) {
      this.xLabel = GUIUtils.parseTeX(var1);
      this.yLabel = GUIUtils.parseTeX(var2);
   }

   public abstract String getCoordinateString(DrawingPanel var1, MouseEvent var2);

   public static CoordinateStringBuilder createCartesian() {
      return new CartesianCoordinateStringBuilder();
   }

   public static CoordinateStringBuilder createPolar() {
      return new PolarCoordinateStringBuilder();
   }

   public static CoordinateStringBuilder createPolar(String var0, String var1, double var2) {
      return new PolarCoordinateStringBuilder(var0, var1, var2);
   }
}
