package org.opensourcephysics.display3d.core;

import java.awt.Color;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public interface Style {
   void setLineColor(Color var1);

   Color getLineColor();

   void setLineWidth(float var1);

   float getLineWidth();

   void setFillColor(Color var1);

   Color getFillColor();

   void setResolution(Resolution var1);

   Resolution getResolution();

   boolean isDrawingFill();

   void setDrawingFill(boolean var1);

   boolean isDrawingLines();

   void setDrawingLines(boolean var1);

   public abstract static class Loader extends XMLLoader {
      public abstract Object createObject(XMLControl var1);

      public void saveObject(XMLControl var1, Object var2) {
         Style var3 = (Style)var2;
         var1.setValue("line color", var3.getLineColor());
         var1.setValue("line width", (double)var3.getLineWidth());
         var1.setValue("fill color", var3.getFillColor());
         var1.setValue("resolution", var3.getResolution());
         var1.setValue("drawing fill", var3.isDrawingFill());
         var1.setValue("drawing lines", var3.isDrawingLines());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Style var3 = (Style)var2;
         var3.setLineColor((Color)var1.getObject("line color"));
         var3.setLineWidth((float)var1.getDouble("line width"));
         var3.setFillColor((Color)var1.getObject("fill color"));
         var3.setResolution((Resolution)var1.getObject("resolution"));
         var3.setDrawingFill(var1.getBoolean("drawing fill"));
         var3.setDrawingLines(var1.getBoolean("drawing lines"));
         return var2;
      }
   }
}
