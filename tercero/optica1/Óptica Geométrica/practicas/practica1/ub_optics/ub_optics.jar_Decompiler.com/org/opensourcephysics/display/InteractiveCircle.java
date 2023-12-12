package org.opensourcephysics.display;

import org.opensourcephysics.controls.XML;
import org.opensourcephysics.controls.XMLControl;

public class InteractiveCircle extends MeasuredCircle implements Interactive {
   boolean enableInteraction;

   public InteractiveCircle(double var1, double var3) {
      super(var1, var3);
      this.enableInteraction = true;
   }

   public InteractiveCircle() {
      this(0.0D, 0.0D);
   }

   public void setEnabled(boolean var1) {
      this.enableInteraction = var1;
   }

   public boolean isEnabled() {
      return this.enableInteraction;
   }

   public boolean isInside(DrawingPanel var1, int var2, int var3) {
      return this.findInteractive(var1, var2, var3) != null;
   }

   public Interactive findInteractive(DrawingPanel var1, int var2, int var3) {
      if (!this.enableInteraction) {
         return null;
      } else {
         int var4 = var1.xToPix(super.x);
         int var5 = var1.yToPix(super.y);
         return Math.abs(var4 - var2) <= super.pixRadius && Math.abs(var5 - var3) <= super.pixRadius ? this : null;
      }
   }

   public static XML.ObjectLoader getLoader() {
      return new InteractiveCircle.InteractiveCircleLoader();
   }

   protected static class InteractiveCircleLoader extends CircleLoader {
      public void saveObject(XMLControl var1, Object var2) {
         super.saveObject(var1, var2);
         InteractiveCircle var3 = (InteractiveCircle)var2;
         var1.setValue("interaction enabled", var3.enableInteraction);
         var1.setValue("measure enabled", var3.enableInteraction);
      }

      public Object createObject(XMLControl var1) {
         return new InteractiveCircle();
      }

      public Object loadObject(XMLControl var1, Object var2) {
         super.loadObject(var1, var2);
         InteractiveCircle var3 = (InteractiveCircle)var2;
         var3.enableInteraction = var1.getBoolean("interaction enabled");
         var3.enableMeasure = var1.getBoolean("measure enabled");
         return var2;
      }
   }
}
