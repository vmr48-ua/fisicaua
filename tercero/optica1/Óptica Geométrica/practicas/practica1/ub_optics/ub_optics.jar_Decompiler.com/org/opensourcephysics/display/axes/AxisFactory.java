package org.opensourcephysics.display.axes;

import org.opensourcephysics.display.PlottingPanel;

public abstract class AxisFactory {
   static final String axisProperty = "org.opensourcephysics.display.axes.AxisFactory";
   static final String defaultAxisFactoryInstance = "org.opensourcephysics.display.axes.CartesianType1Factory";

   public static AxisFactory newInstance() {
      String var0 = "org.opensourcephysics.display.axes.CartesianType1Factory";

      try {
         var0 = System.getProperty("org.opensourcephysics.display.axes.AxisFactory");
         if (var0 == null) {
            var0 = "org.opensourcephysics.display.axes.CartesianType1Factory";
         }
      } catch (SecurityException var5) {
      }

      try {
         Class var1 = Class.forName(var0);
         return (AxisFactory)var1.newInstance();
      } catch (ClassNotFoundException var2) {
      } catch (InstantiationException var3) {
      } catch (IllegalAccessException var4) {
      }

      return null;
   }

   public static AxisFactory newInstance(String var0) {
      if (var0 == null) {
         var0 = "org.opensourcephysics.display.axes.CartesianType1Factory";
      }

      try {
         Class var1 = Class.forName(var0);
         return (AxisFactory)var1.newInstance();
      } catch (ClassNotFoundException var2) {
      } catch (InstantiationException var3) {
      } catch (IllegalAccessException var4) {
      }

      return null;
   }

   public abstract DrawableAxes createAxes(PlottingPanel var1);

   public static DrawableAxes createAxesType1(PlottingPanel var0) {
      return new CartesianType1(var0);
   }

   public static DrawableAxes createAxesType2(PlottingPanel var0) {
      return new CartesianType2(var0);
   }

   public static DrawableAxes createAxesType3(PlottingPanel var0) {
      return new CartesianType3(var0);
   }
}
