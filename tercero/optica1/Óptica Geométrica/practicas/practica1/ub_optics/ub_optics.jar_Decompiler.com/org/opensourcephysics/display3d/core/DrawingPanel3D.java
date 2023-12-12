package org.opensourcephysics.display3d.core;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;
import org.opensourcephysics.display3d.core.interaction.InteractionSource;

public interface DrawingPanel3D extends InteractionSource {
   int TARGET_PANEL = 0;
   int BOTTOM_LEFT = 0;
   int BOTTOM_RIGHT = 1;
   int TOP_RIGHT = 2;
   int TOP_LEFT = 3;

   Component getComponent();

   void setPreferredMinMax(double var1, double var3, double var5, double var7, double var9, double var11);

   double getPreferredMinX();

   double getPreferredMaxX();

   double getPreferredMinY();

   double getPreferredMaxY();

   double getPreferredMinZ();

   double getPreferredMaxZ();

   void zoomToFit();

   void setSquareAspect(boolean var1);

   boolean isSquareAspect();

   VisualizationHints getVisualizationHints();

   Camera getCamera();

   BufferedImage render();

   Image render(Image var1);

   void repaint();

   void addElement(Element var1);

   void removeElement(Element var1);

   void removeAllElements();

   ArrayList getElements();

   public abstract static class Loader implements XML.ObjectLoader {
      public abstract Object createObject(XMLControl var1);

      public void saveObject(XMLControl var1, Object var2) {
         DrawingPanel3D var3 = (DrawingPanel3D)var2;
         var1.setValue("preferred x min", var3.getPreferredMinX());
         var1.setValue("preferred x max", var3.getPreferredMaxX());
         var1.setValue("preferred y min", var3.getPreferredMinY());
         var1.setValue("preferred y max", var3.getPreferredMaxY());
         var1.setValue("preferred z min", var3.getPreferredMinZ());
         var1.setValue("preferred z max", var3.getPreferredMaxZ());
         var1.setValue("visualization hints", var3.getVisualizationHints());
         var1.setValue("camera", var3.getCamera());
         var1.setValue("elements", var3.getElements());
      }

      public Object loadObject(XMLControl var1, Object var2) {
         DrawingPanel3D var3 = (DrawingPanel3D)var2;
         double var4 = var1.getDouble("preferred x min");
         double var6 = var1.getDouble("preferred x max");
         double var8 = var1.getDouble("preferred y min");
         double var10 = var1.getDouble("preferred y max");
         double var12 = var1.getDouble("preferred z min");
         double var14 = var1.getDouble("preferred z max");
         var3.setPreferredMinMax(var4, var6, var8, var10, var12, var14);
         Collection var16 = (Collection)var1.getObject("elements");
         if (var16 != null) {
            var3.removeAllElements();
            Iterator var17 = var16.iterator();

            while(var17.hasNext()) {
               var3.addElement((Element)var17.next());
            }
         }

         return var2;
      }
   }
}
