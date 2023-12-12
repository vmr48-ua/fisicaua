package org.opensourcephysics.display3d.core;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.numerics.Transformation;

public interface Camera {
   int MODE_PLANAR_XY = 0;
   int MODE_PLANAR_XZ = 1;
   int MODE_PLANAR_YZ = 2;
   int MODE_NO_PERSPECTIVE = 10;
   int MODE_PERSPECTIVE = 11;

   void setProjectionMode(int var1);

   int getProjectionMode();

   void reset();

   void setXYZ(double var1, double var3, double var5);

   void setXYZ(double[] var1);

   double getX();

   double getY();

   double getZ();

   void setFocusXYZ(double var1, double var3, double var5);

   void setFocusXYZ(double[] var1);

   double getFocusX();

   double getFocusY();

   double getFocusZ();

   void setRotation(double var1);

   double getRotation();

   void setDistanceToScreen(double var1);

   double getDistanceToScreen();

   void setAzimuth(double var1);

   double getAzimuth();

   void setAltitude(double var1);

   double getAltitude();

   void setAzimuthAndAltitude(double var1, double var3);

   Transformation getTransformation();

   public abstract static class Loader implements XML.ObjectLoader {
      public abstract Object createObject(XMLControl var1);

      public void saveObject(XMLControl var1, Object var2) {
         Camera var3 = (Camera)var2;
         var1.setValue("projection mode", var3.getProjectionMode());
         var1.setValue("x", var3.getX());
         var1.setValue("y", var3.getY());
         var1.setValue("z", var3.getZ());
         var1.setValue("focus x", var3.getFocusX());
         var1.setValue("focus y", var3.getFocusY());
         var1.setValue("focus z", var3.getFocusZ());
         var1.setValue("rotation", var3.getRotation());
         var1.setValue("distance to screen", var3.getDistanceToScreen());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Camera var3 = (Camera)var2;
         var3.setProjectionMode(var1.getInt("projection mode"));
         double var4 = var1.getDouble("x");
         double var6 = var1.getDouble("y");
         double var8 = var1.getDouble("z");
         var3.setXYZ(var4, var6, var8);
         var4 = var1.getDouble("focus x");
         var6 = var1.getDouble("focus y");
         var8 = var1.getDouble("focus z");
         var3.setFocusXYZ(var4, var6, var8);
         var3.setRotation(var1.getDouble("rotation"));
         var3.setDistanceToScreen(var1.getDouble("distance to screen"));
         return var3;
      }
   }
}
