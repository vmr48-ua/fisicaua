package org.opensourcephysics.display3d.core;

import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.controls.XMLLoader;

public interface VisualizationHints {
   int DECORATION_NONE = 0;
   int DECORATION_AXES = 1;
   int DECORATION_CUBE = 2;
   int CURSOR_NONE = 0;
   int CURSOR_XYZ = 1;
   int CURSOR_CUBE = 2;
   int CURSOR_CROSSHAIR = 3;

   void setDecorationType(int var1);

   int getDecorationType();

   void setCursorType(int var1);

   int getCursorType();

   void setRemoveHiddenLines(boolean var1);

   boolean isRemoveHiddenLines();

   void setAllowQuickRedraw(boolean var1);

   boolean isAllowQuickRedraw();

   void setUseColorDepth(boolean var1);

   boolean isUseColorDepth();

   void setShowCoordinates(int var1);

   int getShowCoordinates();

   void setXFormat(String var1);

   String getXFormat();

   void setYFormat(String var1);

   String getYFormat();

   void setZFormat(String var1);

   String getZFormat();

   public abstract static class Loader extends XMLLoader {
      public abstract Object createObject(XMLControl var1);

      public void saveObject(XMLControl var1, Object var2) {
         VisualizationHints var3 = (VisualizationHints)var2;
         var1.setValue("decoration type", var3.getDecorationType());
         var1.setValue("cursor type", var3.getCursorType());
         var1.setValue("remove hidden lines", var3.isRemoveHiddenLines());
         var1.setValue("allow quick redraw", var3.isAllowQuickRedraw());
         var1.setValue("use color depth", var3.isUseColorDepth());
         var1.setValue("show coordinates at", var3.getShowCoordinates());
         var1.setValue("x format", var3.getXFormat());
         var1.setValue("y format", var3.getYFormat());
         var1.setValue("z format", var3.getZFormat());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         VisualizationHints var3 = (VisualizationHints)var2;
         var3.setDecorationType(var1.getInt("decoration type"));
         var3.setCursorType(var1.getInt("cursor type"));
         var3.setRemoveHiddenLines(var1.getBoolean("remove hidden lines"));
         var3.setAllowQuickRedraw(var1.getBoolean("allow quick redraw"));
         var3.setUseColorDepth(var1.getBoolean("use color depth"));
         var3.setShowCoordinates(var1.getInt("show coordinates at"));
         var3.setXFormat(var1.getString("x format"));
         var3.setYFormat(var1.getString("y format"));
         var3.setZFormat(var1.getString("z format"));
         return var2;
      }
   }
}
