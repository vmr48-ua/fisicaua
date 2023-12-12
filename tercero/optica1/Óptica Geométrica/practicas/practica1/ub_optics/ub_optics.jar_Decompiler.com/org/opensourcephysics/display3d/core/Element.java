package org.opensourcephysics.display3d.core;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display3d.core.interaction.InteractionSource;
import org.opensourcephysics.numerics.Transformation;

public interface Element extends InteractionSource {
   int TARGET_POSITION = 0;
   int TARGET_SIZE = 1;

   void setName(String var1);

   String getName();

   void setX(double var1);

   double getX();

   void setY(double var1);

   double getY();

   void setZ(double var1);

   double getZ();

   void setXYZ(double var1, double var3, double var5);

   void setXYZ(double[] var1);

   void setSizeX(double var1);

   double getSizeX();

   void setSizeY(double var1);

   double getSizeY();

   void setSizeZ(double var1);

   double getSizeZ();

   void setSizeXYZ(double var1, double var3, double var5);

   void setSizeXYZ(double[] var1);

   void setVisible(boolean var1);

   boolean isVisible();

   Style getStyle();

   void setTransformation(Transformation var1);

   Transformation getTransformation();

   double[] toSpaceFrame(double[] var1);

   double[] toBodyFrame(double[] var1) throws UnsupportedOperationException;

   void loadUnmutableObjects(XMLControl var1);

   public abstract static class Loader implements XML.ObjectLoader {
      public abstract Object createObject(XMLControl var1);

      public void saveObject(XMLControl var1, Object var2) {
         Element var3 = (Element)var2;
         if (var3.getName().length() > 0) {
            var1.setValue("name", var3.getName());
         }

         var1.setValue("x", var3.getX());
         var1.setValue("y", var3.getY());
         var1.setValue("z", var3.getZ());
         var1.setValue("x size", var3.getSizeX());
         var1.setValue("y size", var3.getSizeY());
         var1.setValue("z size", var3.getSizeZ());
         var1.setValue("visible", var3.isVisible());
         var1.setValue("style", var3.getStyle());
         var1.setValue("transformation", var3.getTransformation());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         Element var3 = (Element)var2;
         String var4 = var1.getString("name");
         if (var4 != null) {
            var3.setName(var4);
         }

         var3.setXYZ(var1.getDouble("x"), var1.getDouble("y"), var1.getDouble("z"));
         var3.setSizeXYZ(var1.getDouble("x size"), var1.getDouble("y size"), var1.getDouble("z size"));
         var3.setVisible(var1.getBoolean("visible"));
         var3.setTransformation((Transformation)var1.getObject("transformation"));
         var3.loadUnmutableObjects(var1);
         return var2;
      }
   }
}
